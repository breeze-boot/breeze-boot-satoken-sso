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

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 请求资源 不携带token或者token无效： 401
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 异常处理策略接口
     */
    private interface ExceptionHandlerStrategy {
        String handleException(Throwable e);
    }

    /**
     * 异常处理策略枚举
     */
    private enum ExceptionHandler {
        BAD_CREDENTIALS(e -> "认证失败: " + e.getMessage()),
        INVALID_BEARER_TOKEN(e -> {
            Throwable cause = e.getCause();
            if (cause instanceof JwtValidationException) {
                return "JWT Token 过期";
            } else if (cause instanceof BadJwtException) {
                return "登录已过期";
            } else if (cause instanceof AccountExpiredException) {
                return "账户已过期";
            } else if (cause instanceof LockedException) {
                return "账户已经锁定";
            }
            return "认证失败";
        }),
        INSUFFICIENT_AUTHENTICATION(e -> "权限不足: " + e.getMessage());

        private final ExceptionHandlerStrategy strategy;

        ExceptionHandler(ExceptionHandlerStrategy strategy) {
            this.strategy = strategy;
        }

        String handleException(Throwable e) {
            return Objects.nonNull(e.getCause()) ? strategy.handleException(e.getCause()) : e.getMessage();
        }
    }


    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        // 增加边界条件检查
        if (request == null || response == null) {
            throw new IllegalArgumentException("Request and response must not be null.");
        }
        if (response.isCommitted()) {
            // 记录日志，说明响应已提交
            log.info("Response already committed. Skipping authentication process.");
            return;
        }

        ExceptionHandler handler = null;
        if (e instanceof BadCredentialsException) {
            handler = ExceptionHandler.BAD_CREDENTIALS;
        } else if (e instanceof InvalidBearerTokenException) {
            handler = ExceptionHandler.INVALID_BEARER_TOKEN;
        } else if (e instanceof InsufficientAuthenticationException) {
            handler = ExceptionHandler.INSUFFICIENT_AUTHENTICATION;
        }

        if (handler != null) {
            String errMsg = handler.handleException(e);
            log.error(errMsg, e.getCause());
            ResponseUtil.response(response, ResultCode.exception(errMsg));
        } else {
            // 处理未被识别的异常类型
            log.error("认证失败", e);
            ResponseUtil.response(response, ResultCode.exception("认证失败"));
        }
    }
}

