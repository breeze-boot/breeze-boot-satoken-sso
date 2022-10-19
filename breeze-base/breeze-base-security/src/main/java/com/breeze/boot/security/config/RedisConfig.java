/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 配置
 *
 * @author breeze
 * @date 2022-08-31
 */
@Configuration
public class RedisConfig {

    /**
     * redis 配置
     *
     * @param redisConnectionFactory redis 连接工厂
     * @return {@link RedisTemplate}<{@link Object}, {@link Object}>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置hashKey和hashValue的序列化规则
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        // 设置key和value的序列化规则
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // 设置支持事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
