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

package com.breeze.boot.auth.authentication.email;

import com.breeze.boot.auth.authentication.OAuth2ResourceOwnerAuthenticationConverter;
import com.breeze.boot.auth.authentication.OAuth2ResourceOwnerAuthenticationToken;
import com.breeze.boot.auth.authentication.OAuthFunction;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.breeze.boot.auth.constants.CustomAuthorizationGrantType.EMAIL_CODE;
import static com.breeze.boot.auth.constants.CustomOAuth2ParameterNames.CODE;
import static com.breeze.boot.auth.constants.CustomOAuth2ParameterNames.EMAIL;
import static com.breeze.boot.auth.utils.OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI;
import static com.breeze.boot.auth.utils.OAuth2EndpointUtils.throwError;
import static org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes.INVALID_REQUEST;

/**
 * oauth2资源所有者Email验证转换器
 *
 * @author gaoweixuan
 * @date 2023-04-21
 */
public class OAuth2ResourceOwnerEmailAuthenticationConverter extends OAuth2ResourceOwnerAuthenticationConverter {

    /**
     * oauth2资源所有者电子邮件验证转换器构造
     */
    public OAuth2ResourceOwnerEmailAuthenticationConverter() {
        super(EMAIL_CODE, check(), buildEmailAuthenticationToken());
    }

    /**
     * 建立电子邮件认证令牌
     *
     * @return {@link OAuthFunction}<{@link Set}<{@link String}>, {@link Authentication}, {@link Map}<{@link String}, {@link Object}>, {@link OAuth2ResourceOwnerAuthenticationToken}>
     */
    private static OAuthFunction<Set<String>, Authentication, Map<String, Object>, OAuth2ResourceOwnerAuthenticationToken> buildEmailAuthenticationToken() {
        return (requestedScopes, clientPrincipal, additionalParameters) -> new OAuth2ResourceOwnerEmailAuthenticationToken(EMAIL_CODE, clientPrincipal, requestedScopes, additionalParameters);
    }

    /**
     * 检查传入的参数
     *
     * @return {@link Consumer}<{@link MultiValueMap}<{@link String}, {@link String}>>
     */
    private static Consumer<MultiValueMap<String, String>> check() {
        return (parameters) -> {
            // EMAIL (REQUIRED)
            String email = parameters.getFirst(EMAIL);
            if (!StringUtils.hasText(email) || parameters.get(EMAIL).size() != 1) {
                throwError(
                        INVALID_REQUEST,
                        EMAIL,
                        ACCESS_TOKEN_REQUEST_ERROR_URI);
            }

            // CODE (REQUIRED)
            String code = parameters.getFirst(CODE);
            if (!StringUtils.hasText(code) || parameters.get(CODE).size() != 1) {
                throwError(
                        INVALID_REQUEST,
                        CODE,
                        ACCESS_TOKEN_REQUEST_ERROR_URI);
            }
        };
    }


}
