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

package com.breeze.boot.system.config;

import com.breeze.boot.security.annotation.EnableSecurityServer;
import com.breeze.boot.security.config.IgnoreUrlProperties;
import com.breeze.boot.security.email.EmailCodeAuthenticationProvider;
import com.breeze.boot.security.service.LocalUserDetailsService;
import com.breeze.boot.security.sms.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * 资源服务器配置
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Configuration
@EnableSecurityServer
public class ResourceServerConfig {

    /**
     * 忽略url属性
     */
    @Autowired
    private IgnoreUrlProperties ignoreUrlProperties;

    /**
     * 用户详细信息服务
     */
    @Autowired
    private LocalUserDetailsService userDetailsService;

    /**
     * redis模板
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 身份验证管理器生成器
     */
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 网络安全编辑器
     * 使用WebSecurity.ignoring()忽略某些URL请求，这些请求将被Spring Security忽略
     *
     * @return {@link WebSecurityCustomizer}
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(ignoreUrlProperties.getExcludeUrls().toArray(new String[]{}));
    }

    /**
     * 短信令牌身份验证提供者 Bean
     *
     * @return {@link SmsCodeAuthenticationProvider}
     */
    @Bean
    SmsCodeAuthenticationProvider smsTokenAuthenticationProvider() {
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider(userDetailsService, redisTemplate);
        authenticationManagerBuilder.authenticationProvider(smsCodeAuthenticationProvider);
        return smsCodeAuthenticationProvider;
    }

    /**
     * 邮箱令牌身份验证提供者 Bean
     *
     * @return {@link EmailCodeAuthenticationProvider}
     */
    @Bean
    EmailCodeAuthenticationProvider emailCodeAuthenticationProvider() {
        EmailCodeAuthenticationProvider emailCodeAuthenticationProvider = new EmailCodeAuthenticationProvider(userDetailsService, redisTemplate);
        authenticationManagerBuilder.authenticationProvider(emailCodeAuthenticationProvider);
        return emailCodeAuthenticationProvider;
    }

}
