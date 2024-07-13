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

package com.breeze.boot.modules.auth.model.form;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 注册客户端参数
 *
 * @author gaoweixuan
 * @since 2023-05-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "注册客户端参数")
public class RegisteredClientForm implements Serializable {

    private Long id;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端ID发表在
     */
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户秘密
     */
    private String clientSecret;

    /**
     * 客户秘密到期
     */
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端身份验证方法
     */
    private Set<String> clientAuthenticationMethods;

    /**
     * 授权批准类型
     */
    private Set<String> authorizationGrantTypes;

    /**
     * 重定向uri
     */
    private Set<String> redirectUris;

    /**
     * 作用域
     */
    private Set<String> scopes;

    /**
     * 客户端设置
     */
    private ClientSettings clientSettings;

    /**
     * 令牌设置
     */
    private TokenSettings tokenSettings;

    public String getClientSecret() {
        return StrUtil.isAllBlank(this.clientSecret) ? null : this.clientSecret;
    }

    /**
     * 令牌设置
     *
     * @author gaoweixuan
     * @since 2023/05/15
     */
    @Data
    public static class TokenSettings {
        /**
         * 标识牌签名算法
         */
        @JsonAlias({"settings.token.id-token-signature-algorithm", "idTokenSignatureAlgorithm"}) // 反序列化的别名 接收前端json的别名
        @JsonProperty("settings.token.id-token-signature-algorithm") // 序列化反序列化的别名
        private String idTokenSignatureAlgorithm;
        /**
         * 访问令牌格式
         */
        @JsonAlias({"settings.token.access-token-format", "accessTokenFormat"})
        @JsonProperty("settings.token.access-token-format")
        private String accessTokenFormat;
        /**
         * 授权代码时间
         */
        @JsonAlias({"settings.token.authorization-code-time-to-live", "authorizationCodeTimeToLive"})
        @JsonProperty("settings.token.authorization-code-time-to-live")
        private Integer authorizationCodeTimeToLive;
        /**
         * 访问令牌时间有效时间
         */
        @JsonAlias({"settings.token.access-token-time-to-live", "accessTokenTimeToLive"})
        @JsonProperty("settings.token.access-token-time-to-live")
        private Integer accessTokenTimeToLive;
        /**
         * 刷新令牌有效时间
         */
        @JsonAlias({"settings.token.refresh-token-time-to-live", "refreshTokenTimeToLive"})
        @JsonProperty("settings.token.refresh-token-time-to-live")
        private Integer refreshTokenTimeToLive;
        /**
         * 是否使用刷新令牌
         */
        @JsonAlias({"settings.token.reuse-refresh-tokens", "reuseRefreshTokens"})
        @JsonProperty("settings.token.reuse-refresh-tokens")
        private boolean reuseRefreshTokens;
    }

    /**
     * 客户端设置
     *
     * @author gaoweixuan
     * @since 2023/05/15
     */
    @Data
    public static class ClientSettings {
        /**
         * 开启PKCE
         */
        @JsonAlias({"settings.client.require-proof-key", "requireProofKey"})
        @JsonProperty("settings.client.require-proof-key")
        private Boolean requireProofKey;
        /**
         * 是否需要授权同意
         */
        @JsonAlias({"settings.client.require-authorization-consent", "requireAuthorizationConsent"})
        @JsonProperty("settings.client.require-authorization-consent")
        private Boolean requireAuthorizationConsent;
        /**
         * jwk设置url
         */
        @JsonAlias({"settings.client.jwk-set-url", "jwkSetUrl"})
        @JsonProperty("settings.client.jwk-set-url")
        private String jwkSetUrl;
        /**
         * 令牌端点认证签名算法
         */
        @JsonAlias({"settings.client.token-endpoint-authentication-signing-algorithm", "tokenEndpointAuthenticationSigningAlgorithm"})
        @JsonProperty("settings.client.token-endpoint-authentication-signing-algorithm")
        private String tokenEndpointAuthenticationSigningAlgorithm;
    }

}
