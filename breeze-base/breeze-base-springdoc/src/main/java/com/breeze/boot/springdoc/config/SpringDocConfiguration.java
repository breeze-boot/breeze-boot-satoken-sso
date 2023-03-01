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

package com.breeze.boot.springdoc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

/**
 * spring文档配置
 *
 * @author gaoweixuan
 * @date 2022/09/24
 */
public class SpringDocConfiguration {

    /**
     * 安全名称
     */
    private static final String SECURITY_SCHEME_NAME = "Bearer";

    /**
     * 系统模块 api
     *
     * @return {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi sysApi() {
        return GroupedOpenApi.builder()
                .group("用户")
                .pathsToMatch("/sys/**")
                .build();
    }

    /**
     * 测试模块 api
     *
     * @return {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi testApi() {
        return GroupedOpenApi.builder()
                .group("测试")
                .pathsToMatch("/test/**")
                .build();
    }

    /**
     * 登录模块 api
     *
     * @return {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
                .group("Security Jwt登录")
                .pathsToMatch("/breeze/**")
                .build();
    }

    @Bean
    public OpenAPI breezeOpenAPI() {
        return new OpenAPI().info(new Info().title("晴风咸蛋小项目 API 文档")
                        .description("晴风咸蛋小项目")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("")))
                .externalDocs(new ExternalDocumentation()
                        .description("晴风咸蛋小项目 文档")
                        .url("https://github.com/Memory1998/breeze-boot.git"))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")));
    }

}
