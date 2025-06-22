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
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.RandomUtil;
import com.breeze.boot.core.utils.BreezeTenantHolder;
import com.dtflys.forest.Forest;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

/**
 * 封装一些 sso 共用方法
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Slf4j
public class SsoRequestUtil {

    /**
     * SSO-server 端主机地址 正式直接使用后端地址，或提前准备好的nginx地址转发
     */
    public static String serverUrl = "http://sa-sso-server.com:9000";

    /**
     * SSO-Server端 统一认证地址
     */
    public static String authUrl = serverUrl + "/sso/auth";

    /**
     * X-Tenant-Id=1
     * &client=sso-client1
     * &msgType=checkTicket
     * &nonce=BXM41m0YR1KHvdZNaY6L
     * &ssoLogoutCall=http://127.0.0.1:9002/sso/doLoginByTicket
     * &ticket=FHdA3G44xjQCxiNMETcFSQRsRMEN0Bh5AQtQo59DPi3SSXj9Rw8Du2gShIHHEClD
     * &timestamp=1750240101933
     * &key=breeze
     * <p>
     * SSO-Server端 ticket校验地址
     */
    public static String checkTicketUrl = serverUrl + "/sso/pushS";

    /**
     * msgType=signout&client=sso-client1&loginId=1111111111111111111
     * &timestamp=1750297085905&nonce=y4TsMtsNGHZm8Gg2dm1Zlu0DJBjqtzdl&sign=bcae81f35a51efbc2a0ae5cf8c36a2d5
     * 单点注销地址
     */
    public static String sloUrl = serverUrl + "/sso/pushS";

    /**
     * SSO-Server端 查询userinfo地址
     */
    public static String getDataUrl = serverUrl + "/sso/pushS";

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
    public static SaResult request(String url) {
        return Forest.post(url).connectTimeout(100000).readTimeout(100000).addHeader(X_TENANT_ID, BreezeTenantHolder.getTenant()).execute(SaResult.class);
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
    public static String getUserInfoSign(String XTenantId, Object loginId, String timestamp, String nonce) {
        return SaSecureUtil.md5("X-Tenant-Id=" + XTenantId + "&client=sso-client1" + "&loginId=" + loginId + "&msgType=userInfo" + "&nonce=" + nonce + "&timestamp=" + timestamp + "&key=" + secretKey);
    }

    /**
     * 根据参数计算签名
     *
     * @param loginId   账号id
     * @param timestamp 当前时间戳，13位
     * @param nonce     随机字符串
     * @return 签名
     */
    public static String getSignoutSign(Object loginId, String timestamp, String nonce) {
        return SaSecureUtil.md5("client=sso-client1" + "&loginId=" + loginId + "&msgType=signout" + "&nonce=" + nonce + "&timestamp=" + timestamp + "&key=" + secretKey);
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
    public static String getLogoutCallSign(Object loginId, String autoLogout, String timestamp, String nonce) {
        return SaSecureUtil.md5("client=sso-client1" + "&autoLogout=" + autoLogout + "&loginId=" + loginId + "&nonce=" + nonce + "&timestamp=" + timestamp + "&key=" + secretKey);
    }

    /**
     * 校验ticket 时构建签名
     *
     * @param ticket        票
     * @param timestamp     时间戳
     * @param nonce         nonce
     * @return {@link String }
     */
    public static String getCheckTicketSign(String ticket, String timestamp, String nonce) {
        return SaSecureUtil.md5("client=sso-client1" + "&msgType=checkTicket" + "&nonce=" + nonce + "&ticket=" + ticket + "&timestamp=" + timestamp + "&key=" + secretKey);
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
