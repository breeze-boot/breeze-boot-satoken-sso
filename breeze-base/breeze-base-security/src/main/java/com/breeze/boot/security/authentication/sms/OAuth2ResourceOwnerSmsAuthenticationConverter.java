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

package com.breeze.boot.security.authentication.sms;

import com.breeze.boot.security.authentication.OAuth2ResourceOwnerAuthenticationConverter;
import com.breeze.boot.security.authentication.OAuth2ResourceOwnerAuthenticationToken;
import com.breeze.boot.security.authentication.OAuthFunction;
import com.breeze.boot.security.constants.CustomOAuth2ParameterNames;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.breeze.boot.security.constants.CustomAuthorizationGrantType.SMS_CODE;
import static com.breeze.boot.security.constants.CustomOAuth2ParameterNames.CODE;
import static com.breeze.boot.security.constants.CustomOAuth2ParameterNames.PHONE;
import static com.breeze.boot.security.utils.OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI;
import static com.breeze.boot.security.utils.OAuth2EndpointUtils.throwError;
import static org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes.INVALID_REQUEST;

/**
 * oauth2资源所有者手机份验证转换器
 *
 * @author gaoweixuan
 * @since 2023-04-21
 */
public class OAuth2ResourceOwnerSmsAuthenticationConverter extends OAuth2ResourceOwnerAuthenticationConverter {

    /**
     * oauth2资源所有者短信验证转换器构造
     */
    public OAuth2ResourceOwnerSmsAuthenticationConverter() {
        super(SMS_CODE, check(), buildSmsAuthenticationToken());
    }

    /**
     * 建立短信认证令牌
     *
     * @return {@link OAuthFunction}<{@link Set}<{@link String}>, {@link Authentication}, {@link Map}<{@link String}, {@link Object}>, {@link OAuth2ResourceOwnerAuthenticationToken}>
     */
    private static OAuthFunction<Set<String>, Authentication, Map<String, Object>, OAuth2ResourceOwnerAuthenticationToken> buildSmsAuthenticationToken() {
        return (requestedScopes, clientPrincipal, additionalParameters) -> new OAuth2ResourceOwnerSmsAuthenticationToken(SMS_CODE, clientPrincipal, requestedScopes, additionalParameters);
    }

    /**
     * 检查传入的参数
     *
     * @return {@link Consumer}<{@link MultiValueMap}<{@link String}, {@link String}>>
     */
    private static Consumer<MultiValueMap<String, String>> check() {
        return (parameters) -> {
            // username (REQUIRED)
            String username = parameters.getFirst(PHONE);
            if (!StringUtils.hasText(username) || parameters.get(PHONE).size() != 1) {
                throwError(INVALID_REQUEST,
                        PHONE,
                        ACCESS_TOKEN_REQUEST_ERROR_URI);
            }

            // password (REQUIRED)
            String password = parameters.getFirst(CustomOAuth2ParameterNames.CODE);
            if (!StringUtils.hasText(password) || parameters.get(CODE).size() != 1) {
                throwError(INVALID_REQUEST,
                        CODE,
                        ACCESS_TOKEN_REQUEST_ERROR_URI);
            }
        };
    }

}
