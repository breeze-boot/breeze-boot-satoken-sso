package com.breeze.boot.admin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDoc {

    private static final String SECURITY_SCHEME_NAME = "bearer";

    public Info info() {
        return new Info().title("晴风 API 文档")
                .description("SpringDoc API 演示")
                .version("v1.0.0")
                .license(new License().name("Apache 2.0").url(""));
    }

    public ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("")
                .url("http://www..com");
    }

    @Bean
    public GroupedOpenApi sysApi() {
        return GroupedOpenApi.builder()
                .group("用户")
                .pathsToMatch("/sys/**")
                .build();
    }

    @Bean
    public GroupedOpenApi testApi() {
        return GroupedOpenApi.builder()
                .group("测试")
                .pathsToMatch("/test/**")
                .build();
    }

    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
                .group("jwt登录")
                .pathsToMatch("/jwt/**")
                .build();
    }

    @Bean
    public OpenAPI breezeOpenAPI() {
        return new OpenAPI().info(info())
                .externalDocs(externalDocumentation())
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")));
    }
}
