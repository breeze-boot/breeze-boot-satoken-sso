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

package com.breeze.boot.auth.jackson2;

import com.breeze.boot.auth.jackson2.mixin.*;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;

/**
 * oauth2授权模块
 *
 * @author gaoweixuan
 * @date 2023/05/05
 */
public class OAuth2AuthorizationModule extends SimpleModule {

    public OAuth2AuthorizationModule() {
        super(OAuth2AuthorizationModule.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    /**
     * 设置模块
     *
     * @param context 上下文
     */
    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());

        context.setMixInAnnotations(OAuth2Authorization.class, OAuth2AuthorizationMixin.class);
        context.setMixInAnnotations(OAuth2AuthorizationCode.class, OAuth2AuthorizationCodeMixin.class);
        context.setMixInAnnotations(OAuth2Authorization.Token.class, TokenMixin.class);
        context.setMixInAnnotations(OAuth2AccessToken.class, OAuth2AccessTokenMixin.class);
        context.setMixInAnnotations(OAuth2RefreshToken.class, OAuth2RefreshTokenMixin.class);
        context.setMixInAnnotations(AuthorizationGrantType.class, AuthorizationGrantTypeMixin.class);
        context.setMixInAnnotations(OAuth2AccessToken.TokenType.class, TokenTypeMixin.class);
        context.setMixInAnnotations(OidcIdToken.class, OidcIdTokenMixin.class);
    }

}
