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

package com.breeze.boot.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 授权同意控制器
 *
 * @author gaoweixuan
 * @since 2023-04-14
 */
@Controller
@RequiredArgsConstructor
public class AuthorizationConsentController {

    /**
     * 注册客户端库
     */
    private final RegisteredClientRepository registeredClientRepository;

    /**
     * 授权同意服务
     */
    private final OAuth2AuthorizationConsentService authorizationConsentService;

    /**
     * 转换 ScopeWithDescription
     *
     * @param scopes 作用域
     * @return {@link Set}<{@link ScopeWithDescription}>
     */
    private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
        return scopes.stream().map(ScopeWithDescription::new).collect(Collectors.toSet());
    }

    /**
     * 同意
     *
     * @param principal 主要
     * @param model     模型
     * @param clientId  客户端ID
     * @param scope     范围
     * @param state     状态
     * @return {@link String}
     */
    @GetMapping(value = "/oauth2/consent")
    public String consent(Principal principal, Model model,
                          @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                          @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                          @RequestParam(OAuth2ParameterNames.STATE) String state) {

        // 移除已经授权过的scope
        Set<String> scopesToApprove = new HashSet<>();
        Set<String> previouslyApprovedScopes = new HashSet<>();
        //获取客户端注册信息
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        OAuth2AuthorizationConsent currentAuthorizationConsent =
                this.authorizationConsentService.findById(registeredClient.getId(), principal.getName());

        // Client下用户已经授权的scope
        Set<String> authorizedScopes = Optional.ofNullable(currentAuthorizationConsent).map(OAuth2AuthorizationConsent::getScopes).orElse(Collections.emptySet());
        for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
            if (authorizedScopes.contains(requestedScope)) {
                previouslyApprovedScopes.add(requestedScope);
            } else {
                scopesToApprove.add(requestedScope);
            }
        }

        // 给consent页面赋值
        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("scopes", withDescription(scopesToApprove));
        model.addAttribute("previouslyApprovedScopes", withDescription(previouslyApprovedScopes));
        model.addAttribute("principalName", principal.getName());

        return "consent";
    }

    /**
     * 范围和描述
     *
     * @author gaoweixuan
     * @since 2023-04-14
     */
    public static class ScopeWithDescription {
        private static final String DEFAULT_DESCRIPTION = "未知范围-我们无法提供有关此权限的信息，请在授予此权限时小心。";
        private static final Map<String, String> SCOPE_DESCRIPTIONS = new HashMap<>();

        static {
            SCOPE_DESCRIPTIONS.put("oidc", "应用的OIDC认证");
            SCOPE_DESCRIPTIONS.put("profile", "获取您的身份信息");
            SCOPE_DESCRIPTIONS.put("phone", "获取您的手机号");
            SCOPE_DESCRIPTIONS.put("email", "获取您的邮箱信息");
            SCOPE_DESCRIPTIONS.put("message.read", "获取信息");
            SCOPE_DESCRIPTIONS.put("message.write", "添加新消息或者编辑和删除现有的信息");
            SCOPE_DESCRIPTIONS.put("other.scope", "其他");
        }

        public final String scope;
        public final String description;

        ScopeWithDescription(String scope) {
            this.scope = scope;
            this.description = SCOPE_DESCRIPTIONS.getOrDefault(scope, DEFAULT_DESCRIPTION);
        }
    }
}
