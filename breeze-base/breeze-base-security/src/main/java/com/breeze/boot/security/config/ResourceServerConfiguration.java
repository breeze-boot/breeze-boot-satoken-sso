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

package com.breeze.boot.security.config;

import com.breeze.boot.security.init.BreezeJumpAuthPathInit;
import lombok.RequiredArgsConstructor;

/**
 * 资源服务器配置
 *
 * @author gaoweixuan
 * @date 2023-04-20
 */
@RequiredArgsConstructor
public class ResourceServerConfiguration {

    /**
     * 不需要认证切面类
     */
    private final BreezeJumpAuthPathInit jumpAuthPathInit;
//
//    @Bean
//    @Order(-1)
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint();
//        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
//        http
//                .authorizeRequests()
//                // 只有[没有登录]可以访问
//                .antMatchers(jumpAuthPathInit.getIgnoreAuthProperties().getIgnoreUrls().toArray(new String[]{})).permitAll()
//                // 拦截所有
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer
//                        .accessDeniedHandler(customAccessDeniedHandler)
//                        .authenticationEntryPoint(customAuthenticationEntryPoint)
//                )
//                // 配置oauth2 资源服务器
//                .oauth2ResourceServer()
//                // 自定义异常处理
//                .accessDeniedHandler(customAccessDeniedHandler)
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .jwt()
//                .jwtAuthenticationConverter(jwtAuthenticationConverter())
//        ;
//        return http.build();
//    }
//
//    /**
//     * 从 JWT 的 scope 中获取的权限 取消 SCOPE_ 的前缀
//     * <p>
//     * 设置从 jwt claim 中那个字段获取权限
//     * 若需要同多个字段中获取权限或者是通过url请求获取的权限，则需要自己提供jwtAuthenticationConverter() 方法实现
//     *
//     * @return JwtAuthenticationConverter
//     */
//    private JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        // 去掉 SCOPE_ 的前缀
//        authoritiesConverter.setAuthorityPrefix("");
//        // 在 jwt claim 中那个字段获取权限，默认是从 scope 或 scp 字段中获取
//        authoritiesConverter.setAuthoritiesClaimName("scope");
//        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
//        return converter;
//    }
}
