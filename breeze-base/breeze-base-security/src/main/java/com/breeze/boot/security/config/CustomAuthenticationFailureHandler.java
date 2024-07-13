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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.breeze.boot.core.enums.ResultCode.SC_UNAUTHORIZED;

/**
 * 自定义身份验证失败处理程序
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Map<String, ResultCode> errorCodeMap = new HashMap<>();

    static {
        errorCodeMap.put("invalid_grant", ResultCode.exception(SC_UNAUTHORIZED,"Invalid grant"));
        errorCodeMap.put("invalid_request", ResultCode.exception(SC_UNAUTHORIZED,"Invalid request"));
        errorCodeMap.put("invalid_token", SC_UNAUTHORIZED); // 假设SC_UNAUTHORIZED是一个已经定义好的ResultCode
        errorCodeMap.put("unauthorized_client", ResultCode.exception(SC_UNAUTHORIZED,"Unauthorized client"));
        errorCodeMap.put("unsupported_grant_type", ResultCode.exception(SC_UNAUTHORIZED,"Unsupported grant type"));
        errorCodeMap.put("invalid_scope", ResultCode.exception(SC_UNAUTHORIZED,"Invalid scope"));
        errorCodeMap.put("invalid_client", ResultCode.exception(SC_UNAUTHORIZED,"Invalid client"));
        errorCodeMap.put("access_denied", ResultCode.exception(SC_UNAUTHORIZED,"Access denied"));
        errorCodeMap.put("invalid_redirect_uri", ResultCode.exception(SC_UNAUTHORIZED,"Invalid redirect URI"));
        errorCodeMap.put("invalid_code", ResultCode.exception(SC_UNAUTHORIZED,"Invalid code"));
        errorCodeMap.put("invalid_refresh_token", ResultCode.exception(SC_UNAUTHORIZED,"Invalid refresh token"));
    }

    /**
     * 在身份验证失败
     *
     * @param request   请求
     * @param response  响应
     * @param exception 异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2Error errorMsg = ((OAuth2AuthenticationException) exception).getError();
            // 完善日志记录，添加错误代码
            log.error("[校验 {} - {}] ", errorMsg.getErrorCode(), errorMsg.getDescription(), exception);

            // 使用映射关系来简化错误处理逻辑
            ResultCode resultCode = errorCodeMap.getOrDefault(errorMsg.getErrorCode(), ResultCode.exception(errorMsg.getDescription()));
            ResponseUtil.response(response, resultCode);
        }
    }


}
