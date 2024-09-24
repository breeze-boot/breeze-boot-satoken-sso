/*
 * Copyright (c) 2024, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.sso.client.config;

import com.breeze.boot.core.utils.MessageUtil;
import com.breeze.boot.security.sso.client.security.exception.BreezeAccessDeniedHandler;
import com.breeze.boot.security.sso.client.security.exception.BreezeAuthenticationEntryPoint;
import com.breeze.boot.security.sso.client.security.jwt.BreezeJwsTokenProvider;
import com.breeze.boot.security.sso.client.security.jwt.JwsTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 权限配置
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Import(MessageUtil.class)
public class SecurityConfig {

    private final BreezeJwsTokenProvider breezeJwsTokenProvider;
    private final BreezeAuthenticationEntryPoint authenticationEntryPoint;
    private final BreezeAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry.requestMatchers("/auth/login", "/sso/*", "/").permitAll()
                        .anyRequest().authenticated())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler))

        ;
        // session
        http.sessionManagement(configurer -> {
            // Session的并发控制,这里设置最多一个【只允许一个用户登录】,如果同一个账户两次登录,那么第一个账户将被踢下线
            configurer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false);
        });


        // 默认是开通session fixation防护的,
        // 防护的原理为，每当用户认证过后，就会重新生成一个新的session，并抛弃旧的session
        http.sessionManagement(configurer -> {
            configurer.sessionFixation().migrateSession().disable();
        });

        // JWT 校验过滤器
        http.addFilterBefore(new JwsTokenFilter(breezeJwsTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 不走过滤器链的放行配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/webjars/**", "/doc.html", "/swagger-resources/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/ws/**", "/ws-app/**");
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * AuthenticationManager 手动注入
     *
     * @param authenticationConfiguration 认证配置
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
