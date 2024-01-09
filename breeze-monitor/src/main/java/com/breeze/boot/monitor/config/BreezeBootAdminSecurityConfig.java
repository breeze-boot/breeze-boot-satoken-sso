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

package com.breeze.boot.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 微风启动管理安全配置
 *
 * @author gaoweixuan
 * @since 2022-10-10
 */
@EnableWebSecurity(debug = true)
public class BreezeBootAdminSecurityConfig {

    private final String adminContextPath;

    private final SecurityProperties security;

    private final AuthenticationManagerBuilder auth;

    public BreezeBootAdminSecurityConfig(AdminServerProperties adminServerProperties,
                                         SecurityProperties securityProperties,
                                         AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.adminContextPath = adminServerProperties.getContextPath();
        this.security = securityProperties;
        this.auth = authenticationManagerBuilder;
    }

    /**
     * spring security 的安全策略
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        return http.headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(
                        adminContextPath + "/instances/**",
                        adminContextPath + "/login",
                        adminContextPath + "/assets/**",
                        adminContextPath + "/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
                .and().logout().logoutUrl(adminContextPath + "/logout")
                .and().httpBasic()
                .and()
                .csrf().disable()
                .build();
    }

}
