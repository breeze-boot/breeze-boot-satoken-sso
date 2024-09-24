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

import cn.dev33.satoken.context.SaHolder;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.sso.client.security.jwt.BreezeJwsTokenProvider;
import com.breeze.boot.security.sso.client.security.model.LoginInfo;
import com.breeze.boot.security.sso.client.util.SsoRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

/**
 * SSO Client端 Controller
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SsoClientController {

    private final AuthenticationManager authenticationManager;

    private final BreezeJwsTokenProvider jwsTokenProvider;

    /**
     * SSO-Client端：首页
     */
    @RequestMapping("/")
    public String index() {
        return "仅支持前后端分离";
    }

    /**
     * SSO-Client端：单点登录地址
     *
     * @param ticket   票
     * @param back     back
     * @param request  请求
     * @param response 响应
     * @param session  阶段
     * @return {@link Object }
     */
    @SneakyThrows
    @RequestMapping("/sso/login")
    public Object ssoLogin(String ticket, @RequestParam(defaultValue = "/") String back, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 如果已经登录，则直接返回
        if (session.getAttribute("userId") != null) {
            response.sendRedirect(back);
            return null;
        }

        /*
         * 此时有两种情况:
         * 情况1：ticket无值，说明此请求是Client端访问，需要重定向至SSO认证中心
         * 情况2：ticket有值，说明此请求从SSO认证中心重定向而来，需要根据ticket进行登录，改为前后端分离模式
         */
        if (ticket == null) {
            String currUrl = request.getRequestURL().toString();
            String clientLoginUrl = currUrl + "?back=" + SsoRequestUtil.encodeUrl(back) + "&" + X_TENANT_ID + "=" + Optional.ofNullable(SaHolder.getRequest().getHeader(X_TENANT_ID)).orElse("");
            String serverAuthUrl = SsoRequestUtil.authUrl + "?redirect=" + clientLoginUrl;
            response.sendRedirect(serverAuthUrl);
            return null;
        }
        // 将 sso-server 回应的消息作为异常抛出
        throw new RuntimeException();
    }

    /**
     * SSO-Client端：单点注销地址
     *
     * @param back     < 返回
     * @param response 响应
     * @param session  阶段
     * @return {@link Object }
     * @throws IOException IOException
     */
    @RequestMapping("/sso/logout")
    public Object ssoLogout(@RequestParam(defaultValue = "/") String back, HttpServletResponse response, HttpSession session) throws IOException {
        // 如果未登录，则无需注销
        if (session.getAttribute("userId") == null) {
            response.sendRedirect(back);
            return null;
        }

        // 调用 sso-server 认证中心单点注销API 
        Object loginId = session.getAttribute("userId");  // 账号id 
        String XTenantId = "1";  // 租户ID
        String timestamp = String.valueOf(System.currentTimeMillis());    // 时间戳
        String nonce = SsoRequestUtil.getRandomString(20);        // 随机字符串
        String sign = SsoRequestUtil.getSign(XTenantId, loginId, timestamp, nonce);    // 参数签名

        String url = SsoRequestUtil.sloUrl + "?loginId=" + loginId + "&timestamp=" + timestamp + "&nonce=" + nonce + "&sign=" + sign;
        Result<?> result = SsoRequestUtil.request(url);
        // 校验响应状态码，200 代表成功
        if (result.getCode().equals("0000")) {

            // 极端场景下，sso-server 中心的单点注销可能并不会通知到此 client 端，所以这里需要再补一刀
            session.removeAttribute("userId");
            // 返回 back 地址
            response.sendRedirect(back);
            return null;

        }
        // 将 sso-server 回应的消息作为异常抛出
        throw new RuntimeException(result.getMessage());
    }

    /**
     * SSO-Client端：单点注销回调地址
     */
    @RequestMapping("/sso/logoutCall")
    public Object ssoLogoutCall(String loginId, String autoLogout, String timestamp, String nonce, String sign) {
        // 校验签名
        String calcSign = SsoRequestUtil.getSignByLogoutCall(loginId, autoLogout, timestamp, nonce);
        if (!calcSign.equals(sign)) {
            log.error("无效签名，拒绝应答：" + sign);
            return Result.fail("无效签名，拒绝应答" + sign);
        }

        // 注销这个账号id TODO
        return Result.ok(null, "账号id=" + loginId + " 注销成功");
    }

    /**
     * 查询我的账号信息
     *
     * 调用此接口的前提是 sso-server 的 /sso/userinfo
     *
     * @param session   session
     * @param XTenantId 扩展租户ID
     * @return {@link Result }<{@link ? }>
     */
    @RequestMapping("/sso/userInfo")
    public Result<?> userInfo(HttpSession session, @RequestHeader(value = X_TENANT_ID, required = false, defaultValue = "1") String XTenantId) {
        // 如果尚未登录
//        if (session.getAttribute("userId") == null) {
//            return Result.fail("尚未登录，无法获取");
//        }
        //TODO

        // 组织 url 参数 
        Object loginId = "1111111111111111111";  // 账号id
        String timestamp = String.valueOf(System.currentTimeMillis());    // 时间戳
        String nonce = SsoRequestUtil.getRandomString(20);        // 随机字符串
        String sign = SsoRequestUtil.getSign(XTenantId, loginId, timestamp, nonce);    // 参数签名

        String url = SsoRequestUtil.getDataUrl
                + "?loginId=" + loginId
                + "&timestamp=" + timestamp
                + "&nonce=" + nonce
                + "&sign=" + sign
                + "&client=sso-client1"
                + "&" + X_TENANT_ID + "=" + XTenantId;
        // 返回给前端
        return SsoRequestUtil.request(url);
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link LoginInfo }
     */
    @RequestMapping("/auth/login")
    public LoginInfo login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.toLowerCase().trim(), password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String accessToken = jwsTokenProvider.createJwtToken(authentication);
        return LoginInfo.builder().tokenType("Bearer").accessToken(accessToken).build();
    }

    /**
     * 全局异常拦截
     *
     * @param e e
     * @return {@link Result }<{@link ? }>
     */
    @ExceptionHandler
    public Result<?> handlerException(Exception e) {
        log.error("", e);
        return Result.fail(e.getMessage());
    }

}
