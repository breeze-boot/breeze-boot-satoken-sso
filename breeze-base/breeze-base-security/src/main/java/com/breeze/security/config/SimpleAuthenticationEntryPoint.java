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

package com.breeze.security.config;

import com.breeze.core.utils.ResponseUtil;
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
 * 匿名用户访问无权限
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Slf4j
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 开始
     *
     * @param request  请求
     * @param response 响应
     * @param e        身份验证异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.error("{}", e.getClass().getName(), e);
        if (response.isCommitted()) {
            return;
        }

        Throwable throwable = e.fillInStackTrace();
        String errMsg = "认证失败";
        if (throwable instanceof BadCredentialsException) {
            errMsg = e.getMessage();
            ResponseUtil.response(response, errMsg);
        }

        Throwable cause = e.getCause();
        if (cause instanceof JwtValidationException) {
            log.error("JWT Token 过期", cause);
            errMsg = "无效的token信息";
        } else if (cause instanceof BadJwtException) {
            log.error("JWT 签名异常", cause);
            errMsg = "无效的token信息";
        } else if (cause instanceof AccountExpiredException) {
            errMsg = "账户已过期";
        } else if (cause instanceof LockedException) {
            errMsg = "账户已被锁定";
        } else if (throwable instanceof InsufficientAuthenticationException) {
            String message = throwable.getMessage();
            if (message.contains("Invalid token does not contain resource id")) {
                errMsg = "未经授权的资源服务器";
            } else if (message.contains("Full authentication is required to access this resource")) {
                errMsg = "访问需要登录";
            }
        } else {
            errMsg = "验证异常";
        }
        ResponseUtil.response(response, errMsg);
    }

}
