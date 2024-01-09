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

package com.breeze.boot.config;

import com.breeze.boot.security.service.ISysRegisteredClientService;
import com.breeze.boot.security.service.ISysUserService;
import com.breeze.boot.security.service.impl.RemoteRegisterClientService;
import com.breeze.boot.security.service.impl.UserDetailService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * 资源服务器配置
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Configuration
@RequiredArgsConstructor
public class ResourceServerConfiguration {

    /**
     * 用户令牌服务
     */
    private final ISysRegisteredClientService registeredClientService;

    /**
     * 用户服务
     */
    private final ISysUserService userService;

    /**
     * 注册客户端库
     *
     * @return {@link RegisteredClientRepository}
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new RemoteRegisterClientService(() -> registeredClientService);
    }

    /**
     * 用户服务
     *
     * @return {@link UserDetailService}
     */
    @Bean
    public UserDetailService userDetailService() {
        return new UserDetailService(() -> userService);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Foo API").version(appVersion)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}



