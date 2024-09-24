/*
 * Copyright (c) 2024, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.sso.client.util;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.util.RandomUtil;
import com.breeze.boot.core.utils.Result;
import com.dtflys.forest.Forest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static cn.dev33.satoken.SaManager.log;

/**
 * 封装一些 sso 共用方法
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
public class SsoRequestUtil {

    /**
     * SSO-Server端主机地址
     */
    public static String serverUrl = "http://sa-sso-server.com:9000";

    /**
     * SSO-Server端 统一认证地址
     */
    public static String authUrl = serverUrl + "/sso/auth";

    /**
     * SSO-Server端 ticket校验地址
     */
    public static String checkTicketUrl = serverUrl + "/sso/checkTicket";

    /**
     * 单点注销地址
     */
    public static String sloUrl = serverUrl + "/sso/signout";

    /**
     * SSO-Server端 查询userinfo地址
     */
    public static String getDataUrl = serverUrl + "/sso/getData";

    /**
     * 打开单点注销功能
     */
    public static boolean isSlo = true;

    /**
     * 接口调用秘钥
     */
    public static String secretKey = "breeze";

    /**
     * 发出请求，并返回 Result 结果
     *
     * @param url 请求地址
     * @return 返回的结果
     */
    public static Result<?> request(String url) {
        log.info(url);
        return Forest.post(url).execute(Result.class);
    }

    /**
     * 根据参数计算签名
     *
     * @param XTenantId 租户
     * @param loginId   账号id
     * @param timestamp 当前时间戳，13位
     * @param nonce     随机字符串
     * @return 签名
     */
    public static String getSign(String XTenantId, Object loginId, String timestamp, String nonce) {
        return SaSecureUtil.md5("X-Tenant-Id=" + XTenantId + "&client=sso-client1" + "&loginId=" + loginId + "&nonce=" + nonce + "&timestamp=" + timestamp + "&key=" + secretKey);
    }

    /**
     * 单点注销回调时构建签名
     *
     * @param loginId    登录id
     * @param autoLogout 自动注销
     * @param timestamp  时间戳
     * @param nonce      nonce
     * @return {@link String }
     */
    public static String getSignByLogoutCall(Object loginId, String autoLogout, String timestamp, String nonce) {
        return SaSecureUtil.md5("client=sso-client1" + "&autoLogout=" + autoLogout + "&loginId=" + loginId + "&nonce=" + nonce + "&timestamp=" + timestamp + "&key=" + secretKey);
    }

    /**
     * 校验ticket 时构建签名
     *
     * @param ticket        票
     * @param ssoLogoutCall sso注销调用
     * @param timestamp     时间戳
     * @param nonce         nonce
     * @return {@link String }
     */
    public static String getSignByTicket(String ticket, String ssoLogoutCall, String timestamp, String nonce) {
        return SaSecureUtil.md5("client=sso-client1" + "&nonce=" + nonce + "&ssoLogoutCall=" + ssoLogoutCall + "&ticket=" + ticket + "&timestamp=" + timestamp + "&key=" + secretKey);
    }

    /**
     * 指定元素是否为null或者空字符串
     *
     * @param str 指定元素
     * @return 是否为null或者空字符串
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 字符串的长度
     * @return 一个随机字符串
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return RandomUtil.randomString(str, length);
    }

    /**
     * URL编码
     */
    public static String encodeUrl(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }

    /**
     * 构建服务器身份验证url
     *
     * @param clientLoginUrl 客户端登录url
     * @param back           < 返回
     * @return {@link String }
     */
    public static String buildServerAuthUrl(String clientLoginUrl, String back) {
        String url = "";
        // 拼接客户端标识
        String client = "sso-client1";
        if (SaFoxUtil.isNotEmpty(client)) {
            url = SaFoxUtil.joinParam(authUrl, "client", client);
        }
        return SaFoxUtil.joinParam(url, "redirect", clientLoginUrl);
    }
}
