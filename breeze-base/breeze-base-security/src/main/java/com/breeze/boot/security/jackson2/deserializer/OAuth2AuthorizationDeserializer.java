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

package com.breeze.boot.security.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.oauth2.server.authorization.OAuth2Authorization.*;

/**
 * oauth2授权反序列化器
 *
 * @author gaoweixuan
 * @date 2023/05/05
 */
public class OAuth2AuthorizationDeserializer extends JsonDeserializer<OAuth2Authorization> {

    /**
     * Map类型引用
     */
    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {
    };
    /**
     * Set类型引用
     */
    private static final TypeReference<Set<String>> SET_TYPE_REFERENCE = new TypeReference<Set<String>>() {
    };
    /**
     * 授权批准类型类型引用
     */
    private static final TypeReference<AuthorizationGrantType> AUTHORIZATION_GRANT_TYPE_TYPE_REFERENCE = new TypeReference<AuthorizationGrantType>() {
    };
    /**
     * 注册客户端库
     */
    private final RegisteredClientRepository registeredClientRepository;

    /**
     * 构造方法 oauth2授权反序列化器
     *
     * @param registeredClientRepository 注册客户端库
     */
    public OAuth2AuthorizationDeserializer(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    /**
     * 反序列化
     *
     * @param jp      JsonParser
     * @param context 上下文
     * @return {@link OAuth2Authorization}
     * @throws IOException ioexception
     */
    @Override
    public OAuth2Authorization deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        // @formatter:off
        Set<String> authorizedScopes = mapper.convertValue(jsonNode.get("authorizedScopes"), SET_TYPE_REFERENCE);
        Map<String, Object> attributes = mapper.convertValue(jsonNode.get("attributes"), MAP_TYPE_REFERENCE);
        Map<String, Object> tokens = mapper.convertValue(jsonNode.get("tokens"), MAP_TYPE_REFERENCE);
        AuthorizationGrantType grantType = mapper.convertValue(jsonNode.get("authorizationGrantType"), AUTHORIZATION_GRANT_TYPE_TYPE_REFERENCE);

        String id = readJsonNode(jsonNode, "id").asText();
        String registeredClientId = readJsonNode(jsonNode, "registeredClientId").asText();
        String principalName = readJsonNode(jsonNode, "principalName").asText();

        RegisteredClient registeredClient = registeredClientRepository.findById(registeredClientId);
        Assert.notNull(registeredClient, "Registered client must not be null");

        Builder builder = withRegisteredClient(registeredClient)
                .id(id)
                .principalName(principalName)
                .authorizationGrantType(grantType)
                .authorizedScopes(authorizedScopes)
                .attributes(map -> map.putAll(attributes));

        Optional.ofNullable(tokens.get(OAuth2AuthorizationCode.class.getName())).ifPresent(token -> this.addToken((Token) token, builder));
        Optional.ofNullable(tokens.get(OAuth2AccessToken.class.getName())).ifPresent(token -> this.addToken((Token) token, builder));
        Optional.ofNullable(tokens.get(OAuth2RefreshToken.class.getName())).ifPresent(token -> this.addToken((Token) token, builder));
        Optional.ofNullable(tokens.get(OidcIdToken.class.getName())).ifPresent(token -> this.addToken((Token) token, builder));
        // @formatter:on
        return builder.build();
    }

    /**
     * 添加标记
     *
     * @param token   令牌
     * @param builder 构建器
     */
    public void addToken(Token<OAuth2Token> token, Builder builder) {
        builder.token(token.getToken(), map -> map.putAll(token.getMetadata()));
    }

    /**
     * 读取json节点
     *
     * @param jsonNode json节点
     * @param field    场
     * @return {@link JsonNode}
     */
    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

}
