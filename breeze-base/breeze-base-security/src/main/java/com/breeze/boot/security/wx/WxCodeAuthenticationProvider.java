/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.wx;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.breeze.boot.security.config.WxLoginProperties;
import com.breeze.boot.security.service.LocalUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/**
 * 短信密码身份验证提供者
 * <p>
 * 参考：
 * {@link  org.springframework.security.authentication.dao.DaoAuthenticationProvider}
 *
 * @author breeze
 * @date 2022-09-03
 */
@Slf4j
public class WxCodeAuthenticationProvider implements AuthenticationProvider {

    /**
     * 创建失败
     */
    private static final String CREATE_FAIL = "微信用户获取失败";

    /**
     * 用户详细信息服务
     */
    private final LocalUserDetailsService userDetailsService;

    /**
     * wx登录属性
     */
    private final WxLoginProperties wxLoginProperties;

    public WxCodeAuthenticationProvider(LocalUserDetailsService userDetailsService,
                                        WxLoginProperties wxLoginProperties) {
        this.userDetailsService = userDetailsService;
        this.wxLoginProperties = wxLoginProperties;
    }

    /**
     * 进行身份验证
     *
     * @param authentication 身份验证
     * @return {@link Authentication}
     * @throws AuthenticationException 身份验证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxCodeAuthenticationToken authenticationToken = (WxCodeAuthenticationToken) authentication;
        String code = String.valueOf(authenticationToken.getPrincipal());
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HttpResponse response = HttpUtil.createGet(url)
                .form("secret", this.wxLoginProperties.getAppSecret())
                .form("appid", this.wxLoginProperties.getAppId())
                .form("js_code", code)
                .form("grant_type", "authorization_code")
                .execute();
        String formatJsonStr = JSONUtil.formatJsonStr(response.body());
        log.info("\n{}", formatJsonStr);
        JSONObject jsonObj = JSONUtil.parseObj(response.body());
        if (Objects.equals(jsonObj.get("errcode"), 40164) || Objects.equals(41008, jsonObj.get("errcode"))) {
            throw new AccessDeniedException("微信认证失败");
        }

        UserDetails userDetails = this.userDetailsService.createOrLoadUserByOpenId(jsonObj.getObj("openid").toString());
        if (Objects.isNull(userDetails)) {
            throw new InternalAuthenticationServiceException(CREATE_FAIL);
        }
        WxCodeAuthenticationToken wxCodeAuthenticationToken = new WxCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        wxCodeAuthenticationToken.setDetails(userDetails);
        return wxCodeAuthenticationToken;
    }

    /**
     * 支持
     * <p>
     * 此方法标识可以使用传入的当前的PROVIDER可以作为验证
     *
     * @param authentication 身份验证
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return WxCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
