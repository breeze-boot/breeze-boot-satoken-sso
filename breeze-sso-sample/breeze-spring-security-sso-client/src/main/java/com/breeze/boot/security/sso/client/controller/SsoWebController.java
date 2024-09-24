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

package com.breeze.boot.security.sso.client.controller;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.sso.client.util.SsoRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

/**
 * 前后台分离SSO
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Slf4j
@RestController
public class SsoWebController {

    // 当前是否登录
    @RequestMapping("/sso/isLogin")
    public Result<Boolean> isLogin() {
        return Result.ok(Boolean.TRUE);
    }

    // 返回SSO认证中心登录地址
    @RequestMapping("/sso/getSsoAuthUrl")
    public Result<String> getSsoAuthUrl(String clientLoginUrl) {
        //http://sa-sso-server.com:9000/sso/auth?client=sso-client1&redirect=http://localhost:3000/#/sso-login?back=http%3A%2F%2Flocalhost%3A3000%2F%23%2Fsso%3Fredirect%3D%2Fhome
        String serverAuthUrl = SsoRequestUtil.buildServerAuthUrl(clientLoginUrl, "");
        return Result.ok(serverAuthUrl);
    }

    /**
     * 根据ticket进行登录
     *
     * @param ticket   票
     * @param request  请求
     * @param response 响应
     * @return {@link Result }<{@link ? }>
     */
    @SneakyThrows
    @RequestMapping("/sso/doLoginByTicket")
    public Result<?> doLoginByTicket(String ticket,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        // 获取当前 client 端的单点注销回调地址
        String ssoLogoutCall = "";
        if (SsoRequestUtil.isSlo) {
            ssoLogoutCall = request.getRequestURL().toString().replace("/sso/login", "/sso/logoutCall");
        }

        // 校验 ticket
        String timestamp = String.valueOf(System.currentTimeMillis());    // 时间戳
        String nonce = SsoRequestUtil.getRandomString(20);        // 随机字符串
        String sign = SsoRequestUtil.getSignByTicket(ticket, ssoLogoutCall, timestamp, nonce);    // 参数签名
        String checkUrl = SsoRequestUtil.checkTicketUrl +
                "?timestamp=" + timestamp +
                "&client=sso-client1" +
                "&nonce=" + nonce +
                "&sign=" + sign +
                "&ticket=" + ticket +
                "&ssoLogoutCall=" + ssoLogoutCall +
                "&" + X_TENANT_ID + "=" + Optional.ofNullable(request.getHeader(X_TENANT_ID)).orElse("");

        Result<?> result = SsoRequestUtil.request(checkUrl);

        if (result.getCode().equals("200") && !SsoRequestUtil.isEmpty(result.getData())) {
            // 登录
            Object loginId = result.getData();
            return Result.ok(loginId);
        }
        throw new RuntimeException(result.getMessage());
    }


}
