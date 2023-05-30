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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

import java.io.IOException;
import java.util.Set;

/**
 * oauth2授权同意反序列化器
 *
 * @author gaoweixuan
 * @date 2023/05/05
 */
public class OAuth2AuthorizationConsentDeserializer extends JsonDeserializer<OAuth2AuthorizationConsent> {

    /**
     * 简单授权类型引用
     */
    private static final TypeReference<Set<SimpleGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_TYPE_REFERENCE = new TypeReference<Set<SimpleGrantedAuthority>>() {
    };

    /**
     * 反序列化
     *
     * @param jp      JsonParser
     * @param context 上下文
     * @return {@link OAuth2AuthorizationConsent}
     * @throws IOException ioexception
     */
    @Override
    public OAuth2AuthorizationConsent deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        // @formatter:off

        String registeredClientId = readJsonNode(jsonNode, "registeredClientId").asText();
        String principalName = readJsonNode(jsonNode, "principalName").asText();
        Set<SimpleGrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"), SIMPLE_GRANTED_AUTHORITY_TYPE_REFERENCE);

        return OAuth2AuthorizationConsent.withId(registeredClientId, principalName)
                .authorities(grantedAuthorities -> grantedAuthorities.addAll(authorities))
                .build();
        // @formatter:on
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

}
