/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.security.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.Map;
import java.util.Objects;

/**
 * wx http接口
 *
 * @author gaoweixuan
 * @date 2023-02-28
 */
@Slf4j
public class WxHttpInterfaceUtils {

    /**
     * 获取访问令牌
     *
     * @param appId     应用程序id
     * @param appSecret 应用程序秘密
     * @return {@link String}
     */
    public static String getAccessToken(String appId, String appSecret) {
        // 网页授权接口
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        HttpResponse response = HttpUtil.createGet(url)
                .form("grant_type", "client_credential")
                .form("appid", appId)
                .form("secret", appSecret)
                .execute();
        JSONObject info = JSONUtil.parseObj(response.body());
        log.info("\n{}", info);
        if (Objects.isNull(info.get("access_token"))) {
            throw new InternalAuthenticationServiceException("认证失败");
        }
        return String.valueOf(info.get("access_token"));
    }

    /**
     * 获得电话号码
     *
     * @param code        代码
     * @param accessToken 访问令牌
     * @return {@link String}
     */
    public static String getPhoneNumber(String code, String accessToken) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("code", code);
        String param = JSONUtil.toJsonStr(paramMap);
        String getPhoneNumberUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        HttpResponse response = HttpUtil.createPost(getPhoneNumberUrl)
                .body(param, "application/json")
                .execute();
        String info = JSONUtil.formatJsonStr(response.body());
        log.info("\n{}", info);
        JSONObject phoneJsonObject = JSONUtil.parseObj(info);
        if (!Objects.equals(phoneJsonObject.get("errcode"), 0)) {
            throw new AccessDeniedException("微信认证失败");
        }
        Map phoneInfoMap = phoneJsonObject.get("phone_info", Map.class);
        return String.valueOf(phoneInfoMap.get("phoneNumber"));
    }

    /**
     * 得到开放id
     *
     * @param appId     应用程序id
     * @param appSecret 应用程序秘密
     * @param code      代码
     * @return {@link String}
     */
    public static String getOpenId(String appId, String appSecret, String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HttpResponse response = HttpUtil.createGet(url)
                .form("appid", appId)
                .form("secret", appSecret)
                .form("js_code", code)
                .form("grant_type", "authorization_code")
                .execute();
        String formatJsonStr = JSONUtil.formatJsonStr(response.body());
        log.info("\n{}", formatJsonStr);
        JSONObject jsonObj = JSONUtil.parseObj(response.body());
        if (Objects.equals(jsonObj.get("errcode"), 40164) || Objects.equals(41008, jsonObj.get("errcode"))) {
            throw new AccessDeniedException("微信认证失败");
        }
        return String.valueOf(jsonObj.get("openid"));
    }
}
