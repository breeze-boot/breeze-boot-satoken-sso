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

import com.breeze.boot.security.jackson2.OAuth2AuthorizationModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CacheConstants.*;

/**
 * 在 redis中 oauth2授权服务
 *
 * @author gaoweixuan
 * @since 2023/05/05
 */
@Slf4j
public class InRedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    /**
     * redis 操作
     */
    private final RedisOperations<String, String> redisOperations;

    /**
     * 客户端库
     */
    private final RegisteredClientRepository clientRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 在Redis,oauth2授权服务
     *
     * @param redisOperations  redis操作
     * @param clientRepository 客户端库
     * @param beanFactory      bean工厂, 它的功能主要是为了装配applicationContext管理之外的Bean。
     */
    public InRedisOAuth2AuthorizationService(RedisOperations<String, String> redisOperations,
                                             RegisteredClientRepository clientRepository,
                                             AutowireCapableBeanFactory beanFactory) {
        Assert.notNull(redisOperations, "redisOperations mut not be null");
        this.redisOperations = redisOperations;
        this.clientRepository = clientRepository;

        // 配置mapper
        ClassLoader classLoader = InRedisOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        mapper.registerModules(securityModules);
        mapper.registerModules(new OAuth2AuthorizationServerJackson2Module());
        mapper.registerModules(new OAuth2AuthorizationModule());
        mapper.setHandlerInstantiator(new SpringHandlerInstantiator(beanFactory));
    }

    /**
     * 保存
     *
     * @param authorization 授权
     */
    @Override
    public void save(OAuth2Authorization authorization) {
        String clientId = authorization.getRegisteredClientId();
        RegisteredClient registeredClient = clientRepository.findById(clientId);
        Assert.notNull(registeredClient, "Registered client must not be null");
        TokenSettings tokenSettings = registeredClient.getTokenSettings();

        Ttl ttl = this.getTtl(authorization, tokenSettings);

        // 生成保存clientId和authId的关系的key
        String client2AuthKey = CLIENT_OAUTH + clientId;
        // 获取 [OAuth2Authorization] 的ID
        String authorizationId = authorization.getId();
        // 保存clientId和authId的关系,格式[clientId : authId]
        redisOperations.opsForSet().add(client2AuthKey, authorizationId);
        // 保存clientId和authId的关系数据的过期时间
        redisOperations.expire(client2AuthKey, ttl.max);


        // 获取保存auth数据的Key
        String authorizationKey = AUTHORIZATION + authorizationId;
        // 保存 [OAuth2Authorization] 数据, 格式[authId : OAuth2Authorization]
        redisOperations.opsForValue().set(authorizationKey, this.write(authorization), ttl.max.getSeconds(), TimeUnit.SECONDS);

        Set<String> correlcationsHashSet = new HashSet<>();
        // state和authId的关系维护
        Optional.ofNullable(authorization.getAttribute(OAuth2ParameterNames.STATE)).ifPresent(token -> {
            String state2OAuthKey = STATE_OAUTH + token;
            redisOperations.opsForValue().set(state2OAuthKey, authorizationId, ttl.codeTtl.getSeconds(), TimeUnit.SECONDS);
            correlcationsHashSet.add(state2OAuthKey);
        });
        // code和authId的关系维护
        Optional.ofNullable(authorization.getToken(OAuth2AuthorizationCode.class)).ifPresent(token -> {
            String code2OAuthKey = CODE_OAUTH + token.getToken().getTokenValue();
            redisOperations.opsForValue().set(code2OAuthKey, authorizationId, ttl.codeTtl.getSeconds(), TimeUnit.SECONDS);
            correlcationsHashSet.add(code2OAuthKey);
        });
        // access_token和authId的关系维护
        Optional.ofNullable(authorization.getAccessToken()).ifPresent(token -> {
            String access2OAuthKey = ACCESS_OAUTH + token.getToken().getTokenValue();
            redisOperations.opsForValue().set(access2OAuthKey, authorizationId, ttl.accessTokenTtl.getSeconds(), TimeUnit.SECONDS);
            correlcationsHashSet.add(access2OAuthKey);
        });
        // refresh_token和authId的关系维护
        Optional.ofNullable(authorization.getRefreshToken()).ifPresent(token -> {
            String refresh2OAuthKey = REFRESH_OAUTH + token.getToken().getTokenValue();
            redisOperations.opsForValue().set(refresh2OAuthKey, authorizationId, ttl.refreshTokenTtl.getSeconds(), TimeUnit.SECONDS);
            correlcationsHashSet.add(refresh2OAuthKey);
        });
        //
        if (!CollectionUtils.isEmpty(correlcationsHashSet)) {
            String id2CorrelationsKey = CORRELATIONS + authorizationId;
            redisOperations.opsForSet().add(id2CorrelationsKey, correlcationsHashSet.toArray(new String[]{}));
            redisOperations.expire(id2CorrelationsKey, ttl.max);
        }
    }

    /**
     * 得到ttl
     *
     * @param authorization 授权
     * @param tokenSettings 令牌设置
     * @return {@link Ttl}
     */
    private Ttl getTtl(OAuth2Authorization authorization, TokenSettings tokenSettings) {
        // code 过期时间
        Duration codeTtl = tokenSettings.getAuthorizationCodeTimeToLive().plusMinutes(100);
        // access_token 过期时间
        Duration accessTokenTtl = tokenSettings.getAccessTokenTimeToLive().plusMinutes(100);
        // refresh_token 过期时间
        Duration refreshTokenTtl = tokenSettings.getRefreshTokenTimeToLive().plusMinutes(100);
        // 求三个时间的最大值
        Duration max = authorization.getRefreshToken() != null ? accessTokenTtl : Collections.max(Arrays.asList(accessTokenTtl, refreshTokenTtl));
        return new Ttl(codeTtl, accessTokenTtl, refreshTokenTtl, max);
    }


    /**
     * 删除
     *
     * @param authorization 授权
     */
    @Override
    public void remove(OAuth2Authorization authorization) {
        List<String> keysToRemove = new ArrayList<>();
        // auth信息
        keysToRemove.add(AUTHORIZATION + authorization.getId());
        // 字典目录
        keysToRemove.add(CORRELATIONS + authorization.getId());
        // 获取此次登录存放的key
        Optional.ofNullable(redisOperations.opsForSet().members(CORRELATIONS + authorization.getId())).ifPresent(keysToRemove::addAll);
        // 一起删除
        redisOperations.delete(keysToRemove);

        // 删除 clientId对应authId的关系
        redisOperations.opsForSet().remove(CLIENT_OAUTH + authorization.getRegisteredClientId(), authorization.getId());
    }

    /**
     * 发现通过id
     *
     * @param id id
     * @return {@link OAuth2Authorization}
     */
    @Override
    public OAuth2Authorization findById(String id) {
        // @formatter:off
        return Optional.ofNullable(redisOperations.opsForValue().get(AUTHORIZATION + id))
                .map(this::readValue)
                .orElse(null);
        // @formatter:on
    }

    /**
     * 找到了令牌
     *
     * @param token     令牌
     * @param tokenType 令牌类型
     * @return {@link OAuth2Authorization}
     */
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        if (tokenType == null) {
            return this.findBy(token);
        }
        return this.findBy(token, tokenType);
    }

    /**
     * 找到
     *
     * @param token 令牌
     * @return {@link OAuth2Authorization}
     */
    private OAuth2Authorization findBy(String token) {
        ArrayList<OAuth2Authorization> objects = Lists.newArrayList();
        objects.add(Optional.ofNullable(this.getAuthId(STATE_OAUTH + token)).map(this::findById).orElse(null));
        objects.add(Optional.ofNullable(this.getAuthId(CODE_OAUTH + token)).map(this::findById).orElse(null));
        objects.add(Optional.ofNullable(this.getAuthId(ACCESS_OAUTH + token)).map(this::findById).orElse(null));
        objects.add(Optional.ofNullable(this.getAuthId(REFRESH_OAUTH + token)).map(this::findById).orElse(null));
        List<OAuth2Authorization> result = objects.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * 找到
     *
     * @param token     令牌
     * @param tokenType 令牌类型
     * @return {@link OAuth2Authorization}
     */
    private OAuth2Authorization findBy(String token, OAuth2TokenType tokenType) {
        if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            log.info("[校验 STATE]: {}", token);
            return Optional.ofNullable(this.getAuthId(STATE_OAUTH + token)).map(this::findById).orElse(null);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            log.info("[校验 CODE]: {}", token);
            return Optional.ofNullable(this.getAuthId(CODE_OAUTH + token)).map(this::findById).orElse(null);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            log.info("[校验 ACCESS_TOKEN]: {}", token);
            return Optional.ofNullable(this.getAuthId(ACCESS_OAUTH + token)).map(this::findById).orElse(null);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            log.info("[校验 REFRESH_TOKEN]: {}", token);
            return Optional.ofNullable(this.getAuthId(REFRESH_OAUTH + token)).map(this::findById).orElse(null);
        }
        return null;
    }

    /**
     * 通过key获取authId
     *
     * @param key 键
     * @return authId
     */
    private String getAuthId(String key) {
        return redisOperations.opsForValue().get(key);
    }

    /**
     * 写
     *
     * @param data 数据
     * @return {@link String}
     */
    public String write(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 解析
     *
     * @param data 数据
     */
    public OAuth2Authorization readValue(String data) {
        try {
            return mapper.readValue(data, new TypeReference<OAuth2Authorization>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * ttl
     *
     * @author gaoweixuan
     * @since 2023/05/05
     */
    private static class Ttl {
        /**
         * 代码ttl
         */
        public final Duration codeTtl;
        /**
         * 访问令牌ttl
         */
        public final Duration accessTokenTtl;
        /**
         * 刷新令牌ttl
         */
        public final Duration refreshTokenTtl;
        /**
         * 最大值
         */
        public final Duration max;

        /**
         * ttl
         *
         * @param codeTtl         代码ttl
         * @param accessTokenTtl  访问令牌ttl
         * @param refreshTokenTtl 刷新令牌ttl
         * @param max             马克斯
         */
        public Ttl(Duration codeTtl, Duration accessTokenTtl, Duration refreshTokenTtl, Duration max) {
            this.codeTtl = codeTtl;
            this.accessTokenTtl = accessTokenTtl;
            this.refreshTokenTtl = refreshTokenTtl;
            this.max = max;
        }
    }
}
