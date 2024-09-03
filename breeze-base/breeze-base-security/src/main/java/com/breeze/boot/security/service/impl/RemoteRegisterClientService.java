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

package com.breeze.boot.security.service.impl;

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.security.model.entity.BaseSysRegisteredClient;
import com.breeze.boot.security.service.ISysRegisteredClientService;
import com.breeze.boot.security.utils.OAuth2EndpointUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.*;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.*;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_CLIENT;
import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.CLIENT_ID;
import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.*;

/**
 * 重写auth注册客户端库
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
@Slf4j
public class RemoteRegisterClientService implements RegisteredClientRepository {

    /**
     *
     */
    private final Supplier<ISysRegisteredClientService> sysRegisterClientServiceSupplier;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 构造方法  身份验证注册客户服务
     *
     * @param registerClientService 注册客户端装
     */
    public RemoteRegisterClientService(Supplier<ISysRegisteredClientService> registerClientService) {
        this.sysRegisterClientServiceSupplier = registerClientService;
    }

    /**
     * 保存
     *
     * @param registeredClient 注册客户端
     */
    @Override
    public void save(RegisteredClient registeredClient) {
        throw new BreezeBizException(ResultCode.SYSTEM_EXCEPTION);
    }

    /**
     * 获取客户端通过id
     *
     * @param id id
     * @return {@link RegisteredClient}
     */
    @Override
    public RegisteredClient findById(String id) {
        BaseSysRegisteredClient registeredClient = sysRegisterClientServiceSupplier.get().getById(id);
        return buildRegisteredClient(registeredClient);
    }

    /**
     * 获取客户端由客户端id
     *
     * @param clientId 客户端id
     * @return {@link RegisteredClient}
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        BaseSysRegisteredClient registeredClient = sysRegisterClientServiceSupplier.get().getByClientId(clientId);
        return buildRegisteredClient(registeredClient);
    }

    /**
     * 注册客户端
     *
     * @param registeredClient 注册客户端
     * @return {@link RegisteredClient}
     */
    private RegisteredClient buildRegisteredClient(BaseSysRegisteredClient registeredClient) {
        // @formatter:off
        Optional.ofNullable(registeredClient).orElseThrow(() -> OAuth2EndpointUtils.newError(INVALID_CLIENT,
                CLIENT_ID,
                OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI));

        RegisteredClient.Builder client = RegisteredClient.withId(registeredClient.getId().toString())
                // clientId客户端标识符
                .clientId(registeredClient.getClientId())
                // 名称可不定义
                .clientName(registeredClient.getClientName())
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt().toInstant(ZoneOffset.UTC))
                .clientSecretExpiresAt(Optional.ofNullable(registeredClient.getClientSecretExpiresAt())
                        .map(time -> time.toInstant(ZoneOffset.UTC)).orElse(null))
                // clientSecret客户端密钥
                .clientSecret(registeredClient.getClientSecret())
                // clientAuthenticationMethod 客户端使用的身份验证方法； params: [client_secret_basic, client_secret_post, private_key_jwt, client_secret_jwt, none]
                .clientAuthenticationMethods((authenticationMethods) ->
                        Arrays.asList(registeredClient.getClientAuthenticationMethods().split(","))
                                .forEach(authenticationMethod -> authenticationMethods.add(resolveClientAuthenticationMethod(authenticationMethod)))
                )
                // authorizationGrantType 客户端可以使用的授权类型； params: [authorization_code, client_credentials, refresh_token, sms_code, password]
                .authorizationGrantTypes((grantTypes) ->
                        Arrays.asList(registeredClient.getAuthorizationGrantTypes().split(","))
                                .forEach(grantType -> grantTypes.add(resolveAuthorizationGrantType(grantType))))
                // redirectUri客户端已注册重定向的URI，不在此列将被拒绝，使用IP或者域名，不能使用localhost
                .redirectUris((uris) -> uris.addAll(Arrays.asList(registeredClient.getRedirectUris().split(","))))
                // scope允许客户端请求的范围
                .scopes((scopes) -> scopes.addAll(Arrays.asList(registeredClient.getScopes().split(","))));

        // clientSetting 客户端自定义设置，包括验证密钥或者是否需要授权页面
        Map<String, Object> clientSettingsMap = this.getClientSettingsMap(registeredClient);
        client.clientSettings(ClientSettings.withSettings(clientSettingsMap).build());

        // tokenSetting发布给客户端的 OAuth2 令牌的自定义设置
        Map<String, Object> tokenSettingsMap = this.getTokenSettingsMap(registeredClient);
        client.tokenSettings(TokenSettings.withSettings(tokenSettingsMap).build());
        // @formatter:on
        return client.build();
    }

    /**
     * tokenSetting发布给客户端的 OAuth2 令牌的自定义设置
     * <p>
     * 判空设置默认值，并且转换类型
     *
     * @param registeredClient 注册客户端
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    private Map<String, Object> getTokenSettingsMap(BaseSysRegisteredClient registeredClient) {
        // @formatter:off
        Map<String, Object> tokenSettingsMap = this.readValue(registeredClient.getJsonTokenSettings());
        Optional.ofNullable(tokenSettingsMap.get(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE))
                .map(time -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE, time))
                // 默认一天
                .orElseGet(() -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE, 60 * 12));
        Optional.ofNullable(tokenSettingsMap.get(ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE))
                .map(time -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE, time))
                // 默认两天
                .orElseGet(() -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE, 60 * 12 * 2));
        Optional.ofNullable(tokenSettingsMap.get(ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE))
                .map(time -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE, time))
                // 默认一分钟
                .orElseGet(() -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE, 1));
        /*
          访问令牌access_token支持:
          SELF_CONTAINED（默认）: 自签JWT类型
          REFERENCE: 引用类型（Support opaque access tokens）即生成96位随机字符串，具体claim信息存储在DB中
         */
        Optional.ofNullable(tokenSettingsMap.get(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT))
                .map(accessTokenFormat -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT, accessTokenFormat))
                .orElseGet(() -> this.putValue(tokenSettingsMap, ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT, OAuth2TokenFormat.SELF_CONTAINED));
        Optional.ofNullable(tokenSettingsMap.get(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM))
                .map(signatureAlgorithm -> this.convertSignatureAlgorithm(tokenSettingsMap, signatureAlgorithm))
                .orElseGet(() -> this.convertSignatureAlgorithm(tokenSettingsMap, RS256));
        // @formatter:on
        return tokenSettingsMap;
    }

    /**
     * clientSetting 客户端自定义设置，包括验证密钥或者是否需要授权页面
     * <p>
     * 判空设置默认值，并且转换类型
     *
     * @param registeredClient 注册客户端
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    private Map<String, Object> getClientSettingsMap(BaseSysRegisteredClient registeredClient) {
        // @formatter:off
        Map<String, Object> clientSettingsMap = this.readValue(registeredClient.getJsonClientSettings());
        Optional.ofNullable(clientSettingsMap.get(ConfigurationSettingNames.Client.TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM))
                .map(signatureAlgorithm -> this.convertSignatureAlgorithm(clientSettingsMap, signatureAlgorithm))
                .orElseGet(() -> this.convertSignatureAlgorithm(clientSettingsMap, RS256));
        // @formatter:on
        return clientSettingsMap;
    }

    /**
     * 获取签名算法
     *
     * @param tokenSettingsMap   令牌设置地图
     * @param signatureAlgorithm 签名算法
     * @return {@link SignatureAlgorithm}
     */
    private SignatureAlgorithm convertSignatureAlgorithm(Map<String, Object> tokenSettingsMap, Object signatureAlgorithm) {
        if (signatureAlgorithm instanceof SignatureAlgorithm) {
            return (SignatureAlgorithm) signatureAlgorithm;
        } else if (RS256.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return RS256;
        } else if (RS512.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return RS512;
        } else if (RS384.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return RS384;
        } else if (ES256.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return ES256;
        } else if (ES512.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return ES512;
        } else if (ES384.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return ES384;
        } else if (PS256.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return PS256;
        } else if (PS512.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return PS512;
        } else if (PS384.getName().equals(signatureAlgorithm)) {
            tokenSettingsMap.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, RS256);
            return PS384;
        }
        return RS256;
        // Custom client signature algorithm
    }

    private Object putValue(Map<String, Object> tokenSettingsMap, String key, Object value) {
        if (value instanceof Integer) {
            return tokenSettingsMap.put(key, Duration.ofMinutes((Integer) value));
        } else if (value instanceof String) {
            return tokenSettingsMap.put(key, new OAuth2TokenFormat((String) value));
        } else if (value instanceof OAuth2TokenFormat) {
            return tokenSettingsMap.put(key, value);
        }else if (value instanceof Duration) {
            return tokenSettingsMap.put(key, value);
        }
        return tokenSettingsMap;
    }

    /**
     * 解决授权授予类型
     *
     * @param authorizationGrantType 授权批准类型
     * @return {@link AuthorizationGrantType}
     */
    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AUTHORIZATION_CODE;
        } else if (CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return CLIENT_CREDENTIALS;
        } else if (REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return REFRESH_TOKEN;
        }
        return new AuthorizationGrantType(authorizationGrantType);        // Custom authorization grant type
    }

    /**
     * 解决客户端身份验证方法
     *
     * @param clientAuthenticationMethod 客户端身份验证方法
     * @return {@link ClientAuthenticationMethod}
     */
    private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod) {
        if (CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
            return CLIENT_SECRET_BASIC;
        } else if (CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
            return CLIENT_SECRET_POST;
        } else if (NONE.getValue().equals(clientAuthenticationMethod)) {
            return NONE;
        }
        return new ClientAuthenticationMethod(clientAuthenticationMethod);        // Custom client authentication method
    }

    /**
     * 解析
     *
     * @param data 数据
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public Map<String, Object> readValue(String data) {
        try {
            return mapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
