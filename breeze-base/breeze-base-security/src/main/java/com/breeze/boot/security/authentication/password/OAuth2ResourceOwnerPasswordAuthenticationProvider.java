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

import com.breeze.boot.security.authentication.OAuth2ResourceOwnerAuthenticationProvider;
import com.breeze.boot.security.authentication.OAuth2ResourceOwnerAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Map;
import java.util.function.Function;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.PASSWORD;

/**
 * oauth2资源所有者密码身份验证提供者
 *
 * @author gaoweixuan
 * @since 2023-04-21
 */
@Slf4j
public class OAuth2ResourceOwnerPasswordAuthenticationProvider extends OAuth2ResourceOwnerAuthenticationProvider {

    /**
     * oauth2资源所有者密码身份验证提供者构造方法
     *
     * @param authorizationService  授权服务
     * @param tokenGenerator        令牌生成器
     * @param authenticationManager 身份验证管理器
     */
    public OAuth2ResourceOwnerPasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, AuthenticationManager authenticationManager) {
        super(authorizationService, tokenGenerator, PASSWORD, resourceOwnerAuthentication(authenticationManager));
    }

    private static Function<OAuth2ResourceOwnerAuthenticationToken, Authentication> resourceOwnerAuthentication(AuthenticationManager authenticationManager) {
        return (resourceOwnerAuthentication) -> {
            Map<String, Object> additionalParameters = resourceOwnerAuthentication.getAdditionalParameters();
            String username = (String) additionalParameters.get(OAuth2ParameterNames.USERNAME);
            String password = (String) additionalParameters.get(OAuth2ParameterNames.PASSWORD);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            log.debug("got usernamePasswordAuthenticationToken=" + usernamePasswordAuthenticationToken);
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        };
    }

    /**
     * 支持
     *
     * @param authentication 身份验证
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        boolean supports = OAuth2ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authentication);
        log.debug("supports authentication=" + authentication + " returning " + supports);
        return supports;
    }

}
