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

package com.breeze.boot.auth.config;

import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.security.config.CustomAccessDeniedHandler;
import com.breeze.boot.security.config.CustomAuthenticationEntryPoint;
import com.breeze.boot.security.init.BreezeJumpAuthPathInit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * 安全配置
 *
 * @author gaoweixuan
 * @date 2023-04-14
 */
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

    /**
     * 发布系统日志事件
     */
    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    /**
     * redis session仓库
     */
    private final RedisIndexedSessionRepository sessionRepository;

    private final BreezeJumpAuthPathInit jumpAuthPathInit;

    /**
     * 默认安全过滤器链，授权服务器本身的安全配置
     *
     * @param http http
     * @return {@link SecurityFilterChain}
     * @throws Exception 异常
     */
    @Bean
    @Order(0)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
//       http.authorizeRequests(authorizeRequests ->
//               // 开放自定义的需要开放的端点
//               authorizeRequests.antMatchers("/auth/**").permitAll()
//                       .anyRequest().authenticated()
//       );

        //解决不允许显示在iframe的问题
        http.headers()
                .frameOptions()
                .sameOrigin();

        http.csrf().disable();

        // 登录
//        http.formLogin()
//                .loginPage("/auth/login")
//                .loginProcessingUrl("/auth/login")
//                .successHandler(new FormOidcLoginSuccessHandler(publisherSaveSysLogEvent))
//                .failureHandler(new FormLoginFailHandler(publisherSaveSysLogEvent));

        // 退出登录
//        http.logout()
//                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true);

        // session
//        http.sessionManagement()
//                // Session的并发控制,这里设置最多一个【只允许一个用户登录】,如果同一个账户两次登录,那么第一个账户将被踢下线
//                 .maximumSessions(1)
//                 .maxSessionsPreventsLogin(false)
//                 .sessionRegistry(sessionBackedSessionRegistry());

        // 默认是开通session fixation防护的,
        // 防护的原理为，每当用户认证过后，就会重新生成一个新的session，并抛弃旧的session
//         http.sessionManagement().sessionFixation().migrateSession();
         http.sessionManagement().disable();

        // 增加自定义过滤器
//        http.addFilterBefore(new OidcLoginBeforeStoreProcessorFilter(http), ExceptionTranslationFilter.class);
        // @formatter:on
        CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint();
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
        http
                .authorizeRequests()
                // 只有[没有登录]可以访问
                .antMatchers(jumpAuthPathInit.getIgnoreAuthProperties().getIgnoreUrls().toArray(new String[]{})).permitAll()
                // 拦截所有
                .anyRequest().authenticated()
                .and()
                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                // 配置oauth2 资源服务器
                .oauth2ResourceServer()
                // 自定义异常处理
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
        ;
        return http.build();
    }

    /**
     * 从 JWT 的 scope 中获取的权限 取消 SCOPE_ 的前缀
     * <p>
     * 设置从 jwt claim 中那个字段获取权限
     * 若需要同多个字段中获取权限或者是通过url请求获取的权限，则需要自己提供jwtAuthenticationConverter() 方法实现
     *
     * @return JwtAuthenticationConverter
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 去掉 SCOPE_ 的前缀
        authoritiesConverter.setAuthorityPrefix("");
        // 在 jwt claim 中那个字段获取权限，默认是从 scope 或 scp 字段中获取
        authoritiesConverter.setAuthoritiesClaimName("scope");
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    /**
     * 会话支持会话注册表
     *
     * @return {@link SpringSessionBackedSessionRegistry}
     */
    @Bean
    public SpringSessionBackedSessionRegistry<?> sessionBackedSessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    /**
     * 网络安全编辑器
     *
     * @return {@link WebSecurityCustomizer}
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/actuator/**", "/favicon.ico", "/resources/**", "/webjars/**", "/error");
    }

}
