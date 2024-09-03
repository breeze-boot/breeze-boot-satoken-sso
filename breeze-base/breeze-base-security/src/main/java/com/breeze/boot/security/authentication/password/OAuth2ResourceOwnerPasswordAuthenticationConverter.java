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

package com.breeze.boot.security.authentication.password;

import cn.hutool.extra.spring.SpringUtil;
import com.breeze.boot.core.jackson.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.AesUtil;
import com.breeze.boot.security.authentication.OAuth2ResourceOwnerAuthenticationConverter;
import com.breeze.boot.security.authentication.OAuth2ResourceOwnerAuthenticationToken;
import com.breeze.boot.security.authentication.OAuthFunction;
import com.breeze.boot.security.utils.OAuth2EndpointUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.PASSWORD;

/**
 * oauth2资源所有者密码身份验证转换器
 *
 * @author gaoweixuan
 * @since 2023-04-21
 */
public class OAuth2ResourceOwnerPasswordAuthenticationConverter extends OAuth2ResourceOwnerAuthenticationConverter {

    /**
     * oauth2资源所有者密码身份验证转换器构造
     */
    public OAuth2ResourceOwnerPasswordAuthenticationConverter() {
        super(PASSWORD, check(), buildPasswordAuthenticationToken());
    }


    /**
     * 建立密码身份验证令牌
     *
     * @return {@link OAuthFunction}<{@link Set}<{@link String}>, {@link Authentication}, {@link Map}<{@link String}, {@link Object}>, {@link OAuth2ResourceOwnerAuthenticationToken}>
     */
    private static OAuthFunction<Set<String>, Authentication, Map<String, Object>, OAuth2ResourceOwnerAuthenticationToken> buildPasswordAuthenticationToken() {
        return (requestedScopes, clientPrincipal, additionalParameters) -> new OAuth2ResourceOwnerPasswordAuthenticationToken(AuthorizationGrantType.PASSWORD, clientPrincipal, requestedScopes, additionalParameters);
    }

    /**
     * 检查传入的参数
     *
     * @return {@link Consumer}<{@link MultiValueMap}<{@link String}, {@link String}>>
     */
    private static Consumer<MultiValueMap<String, String>> check() {
        return (parameters) -> {
            // username (REQUIRED)
            String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
            if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
                OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME, OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
            }

            // password (REQUIRED)
            String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
            if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
                OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD, OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
            }

            AesSecretProperties aesSecretProperties = SpringUtil.getBean(AesSecretProperties.class);
            //私钥解密
            String decryptPassword = AesUtil.decryptStr(password, aesSecretProperties.getAesSecret());
            parameters.set(OAuth2ParameterNames.PASSWORD, decryptPassword);
        };
    }

}
