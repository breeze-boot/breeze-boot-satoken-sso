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

package com.breeze.boot.security.jackson2;

import com.breeze.boot.security.jackson2.mixin.OAuth2AuthorizationConsentMixin;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

/**
 * oauth2授权同意模块
 *
 * @author gaoweixuan
 * @date 2023/05/05
 */
public class OAuth2AuthorizationConsentModule extends SimpleModule {

    public OAuth2AuthorizationConsentModule() {
        super(OAuth2AuthorizationConsentModule.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    /**
     * 设置模块
     *
     * @param context 上下文
     */
    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());

        context.setMixInAnnotations(OAuth2AuthorizationConsent.class, OAuth2AuthorizationConsentMixin.class);
        context.setMixInAnnotations(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
    }

}
