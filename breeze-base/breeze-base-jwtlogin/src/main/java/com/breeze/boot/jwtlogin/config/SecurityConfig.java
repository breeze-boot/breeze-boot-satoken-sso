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

package com.breeze.boot.jwtlogin.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

/**
 * 安全配置
 *
 * @author breeze
 * @date 2022-08-31
 */
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    /**
     * jwt身份验证令牌过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 身份验证配置
     */
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * 安全过滤器链
     *
     * @param http http
     * @return {@link SecurityFilterChain}
     * @throws Exception 异常
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                // 不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 只有没有登录可以访问
                .antMatchers("/captcha/**", "/jwt/**").anonymous()
                // 其余的必须登录
                .anyRequest().authenticated()
                .and()
                // 自定义过滤器`
                .addFilterAfter(jwtAuthenticationTokenFilter, ExceptionTranslationFilter.class)
                // 异常处理类
                .exceptionHandling()
                .accessDeniedHandler(new SimpleAccessDeniedHandler())
                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint());
        // 跨域
        http.cors();
        return http.build();
    }

    /**
     * 用户身份验证管理器
     *
     * @return {@link AuthenticationManager}
     */
    @Bean
    @SneakyThrows
    public AuthenticationManager userAuthenticationManager() {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
