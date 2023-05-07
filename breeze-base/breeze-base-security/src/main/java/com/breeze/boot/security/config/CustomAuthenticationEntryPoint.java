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

package com.breeze.boot.security.config;

import com.breeze.boot.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求资源 不携带token或者token无效： 401
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 开始
     *
     * @param request  请求
     * @param response 响应
     * @param e        身份验证异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        if (response.isCommitted()) {
            return;
        }

        Throwable trace = e.fillInStackTrace();
        String errMsg = "认证失败";
        if (trace instanceof BadCredentialsException) {
            ResponseUtil.response(response, e.getMessage());
            return;
        }

        Throwable cause = e.getCause();
        if (cause instanceof JwtValidationException) {
            log.error("[JWT Token 过期]", cause);
            errMsg = "无效的token信息";
        } else if (cause instanceof BadJwtException) {
            log.error("[JWT 签名异常]", cause);
            errMsg = "无效的token信息";
        } else if (cause instanceof AccountExpiredException) {
            log.error("[账户已过期]", cause);
            errMsg = "账户已过期";
        } else if (cause instanceof LockedException) {
            log.warn("[账户已经锁定]", cause);
            errMsg = "账户已被锁定";
        } else if (trace instanceof InsufficientAuthenticationException) {
            String message = trace.getMessage();
            if (message.contains("Invalid token does not contain resource id")) {
                log.warn("[未经授权的服务器]", cause);
                errMsg = "未经授权的资源服务器";
            } else if (message.contains("Full authentication is required to access this resource")) {
                log.warn("[访问需要登录]", cause);
                errMsg = "访问需要登录";
            }
        } else {
            log.error("[验证异常]", cause);
            errMsg = "验证异常";
        }
        ResponseUtil.response(response, errMsg);
    }

}
