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

/**
 * spring文档配置
 *
 * @author gaoweixuan
 * @date 2022/09/24
 */
@Configuration
public class SpringDocConfig {

    /**
     * 安全计划名称
     */
    private static final String SECURITY_SCHEME_NAME = "bearer";

    /**
     * 信息
     *
     * @return {@link Info}
     */
    public Info info() {
        return new Info().title("晴风 API 文档")
                .description("SpringDoc API 演示")
                .version("v1.0.0")
                .license(new License().name("Apache 2.0").url(""));
    }

    /**
     * 外部文档
     *
     * @return {@link ExternalDocumentation}
     */
    public ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("")
                .url("http://www..com");
    }

    /**
     * sys api
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
     * 测试api
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
     * 登录api
     *
     * @return {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
                .group("jwt登录")
                .pathsToMatch("/jwt/**")
                .build();
    }

    /**
     * 微风开放api
     *
     * @return {@link OpenAPI}
     */
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
