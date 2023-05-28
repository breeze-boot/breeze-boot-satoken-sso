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

import com.breeze.boot.auth.authentication.OAuth2ResourceOwnerAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * oauth2资源所有者Email身份验证令牌
 *
 * @author gaoweixuan
 * @date 2023-04-21
 */
public class OAuth2ResourceOwnerEmailAuthenticationToken extends OAuth2ResourceOwnerAuthenticationToken {

    /**
     * oauth2资源所有者Email身份验证令牌构造
     *
     * @param authorizationGrantType 授权类型
     * @param clientPrincipal        the authenticated client principal
     * @param scopes                 作用域
     * @param additionalParameters   额外参数
     */
    public OAuth2ResourceOwnerEmailAuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }
}
