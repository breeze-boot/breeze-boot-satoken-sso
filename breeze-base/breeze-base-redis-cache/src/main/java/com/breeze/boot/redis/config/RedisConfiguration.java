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

package com.breeze.boot.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * redis 配置
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class RedisConfiguration {

    /**
     * redis 配置
     *
     * @param lettuceConnectionFactory redis 连接工厂
     * @return {@link RedisTemplate}<{@link Object}, {@link Object}>
     */
    @Bean
    public RedisTemplate<String, Object> defaultRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        // 设置hashKey和hashValue的序列化规则
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashValueSerializer(this.getJsonRedisSerializer());
        // 设置key和value的序列化规则
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(this.getJsonRedisSerializer());

        // 设置支持事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 自定义缓存管理器
     *
     * @param lettuceConnectionFactory redis 连接工厂
     * @return {@link RedisCacheManager}
     */
    @Bean
    public RedisCacheManager defaultCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        // 创建String和JSON序列化对象，分别对key和value的数据进行类型转换
        RedisSerializer<String> strSerializer = new StringRedisSerializer();

        // 自定义缓存数据序列化方式和有效期限
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 前缀
                .computePrefixWith(name -> name)
                // 设置缓存过期时间为1天
                .entryTtl(Duration.ofDays(1))
                // 使用 strSerializer 对key进行数据类型转换
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(strSerializer))
                // 使用 jacksonSeial 对value的数据类型进行转换
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.getJsonRedisSerializer()))
                ;

        return RedisCacheManager.builder(lettuceConnectionFactory).cacheDefaults(config).build();
    }

    /**
     * 使用jackson作为redis序列化器
     *
     * @return {@link Jackson2JsonRedisSerializer}
     */
    private Jackson2JsonRedisSerializer<Object> getJsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule((new SimpleModule()))
                .registerModule(new Jdk8Module())
                // new module, NOT JSR310Module;
                .registerModule(new JavaTimeModule());

        /*
          PropertyAccessor.ALL表示设置所有属性访问器的可见性，包括字段和getter/setter方法。
          JsonAutoDetect.Visibility.ANY是JsonAutoDetect枚举的一个值，它指示ObjectMapper在处理对象时应该自动检测所有属性，无论它们是通过字段还是getter/setter方法定义的。
          这个设置通常用于简化JSON处理，使得Jackson可以自动处理对象中所有可读和可写属性的序列化和反序列化，而无需显式地标记哪些属性应该被处理。然而，这种设置也可能会导致非预期的属性被序列化，特别是当存在私有字段或不希望被外部访问的属性时，这可能带来潜在的安全风险。因此，在使用时需要谨慎评估并根据实际需求调整属性的可见性设置。
         */
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        // 解决jackson2无法反序列化LocalDateTime的问题
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        serializer.setObjectMapper(mapper);
        return serializer;
    }

}
