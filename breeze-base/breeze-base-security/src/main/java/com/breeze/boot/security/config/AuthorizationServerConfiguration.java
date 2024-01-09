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

import com.breeze.boot.security.authentication.email.EmailAuthenticationProvider;
import com.breeze.boot.security.authentication.email.OAuth2ResourceOwnerEmailAuthenticationConverter;
import com.breeze.boot.security.authentication.email.OAuth2ResourceOwnerEmailAuthenticationProvider;
import com.breeze.boot.security.authentication.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.breeze.boot.security.authentication.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.breeze.boot.security.authentication.sms.OAuth2ResourceOwnerSmsAuthenticationConverter;
import com.breeze.boot.security.authentication.sms.OAuth2ResourceOwnerSmsAuthenticationProvider;
import com.breeze.boot.security.authentication.sms.SmsAuthenticationProvider;
import com.breeze.boot.security.jose.Jwks;
import com.breeze.boot.security.service.impl.InRedisOAuth2AuthorizationService;
import com.breeze.boot.security.service.impl.UserDetailService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.ACCESS_TOKEN;
import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;

/**
 * 授权服务器配置
 *
 * @author gaoweixuan
 * @since 2023-04-10
 */
@SuppressWarnings("ALL")
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfiguration {

    /**
     * 自定义授权页
     */
    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 用户详细信息服务
     */
    private final UserDetailService userDetailsService;

    /**
     * redis 模板
     */
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 授权服务器安全过滤器链
     *
     * @param http http
     * @return {@link SecurityFilterChain}
     * @throws Exception 异常
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        // @formatter:off

        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {
            tokenEndpoint.accessTokenRequestConverter(
                            new DelegatingAuthenticationConverter(Arrays.asList(
                                    new OAuth2AuthorizationCodeAuthenticationConverter(),
                                    new OAuth2RefreshTokenAuthenticationConverter(),
                                    new OAuth2ClientCredentialsAuthenticationConverter(),
                                    new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
                                    new OAuth2ResourceOwnerSmsAuthenticationConverter(),
                                    new OAuth2ResourceOwnerEmailAuthenticationConverter()
                            )))
                    .errorResponseHandler(new CustomAuthenticationFailureHandler());
        }));

        authorizationServerConfigurer.clientAuthentication(authenticationConfigurer -> {
            authenticationConfigurer.errorResponseHandler(new CustomAuthenticationFailureHandler());
        });

        // 根据需求对 authorizationServerConfigurer 进行一些个性化配置
        authorizationServerConfigurer
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                        .errorResponseHandler(new CustomAuthenticationFailureHandler()));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        DefaultSecurityFilterChain securityFilterChain = http
                // 拦截对授权服务器相关端点的请求
                .requestMatcher(endpointsMatcher)
                // 拦截对授权服务器相关端点的请求
                .authorizeRequests().anyRequest().authenticated()
                // 忽略掉相关端点的 CSRF(跨站请求): 对授权端点的访问可以是跨站的
                .and().csrf().ignoringRequestMatchers(endpointsMatcher)
                .and().exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                // 开启JWT
                .and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                // 应用授权服务器的配置
                .apply(authorizationServerConfigurer)
                .and().build();

        this.addCustomOAuth2ResourceOwnerAuthenticationProvider(http);
        // @formatter:on
        return securityFilterChain;
    }

    /**
     * jwt编辑器
     *
     * @return {@link OAuth2TokenCustomizer}<{@link JwtEncodingContext}>
     */
    @Bean
    @SuppressWarnings("unused")
    OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            JwtClaimsSet.Builder claims = context.getClaims();
            Authentication principal = context.getPrincipal();
            if (context.getTokenType().getValue().equals(ACCESS_TOKEN)) {
                // Customize headers/claims for access_token
                Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                claims.claim("clientId", context.getRegisteredClient().getClientId());
                Set<String> authorizedScopes = context.getAuthorizedScopes();
                authorities.addAll(authorizedScopes);
                claims.claim("scope", authorities);
            } else if (context.getTokenType().getValue().equals(ID_TOKEN)) {
                // Customize headers/claims for id_token
            }
        };
    }

    /**
     * 授权服务
     *
     * @param clientRepository 客户端库
     * @param beanFactory      bean工厂
     * @return {@link OAuth2AuthorizationService}
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(RegisteredClientRepository clientRepository,
                                                           AutowireCapableBeanFactory beanFactory) {
        return new InRedisOAuth2AuthorizationService(redisTemplate, clientRepository, beanFactory);
    }

    /**
     * jwk源
     *
     * @return {@link JWKSource}<{@link SecurityContext}>
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * 授权服务器元信息配置，大多数使用默认配置即可，唯一需要配置的只有授权服务器的地址issuer
     *
     * <p>
     * 在生产中这个地方应该配置为域名
     * </p>
     *
     * @return {@link AuthorizationServerSettings}
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer("http://localhost:9000").build();
    }

    /**
     * jwt译码器
     *
     * @param jwkSource jwk源
     * @return {@link JwtDecoder}
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 添加自定义oauth2资源所有者密码身份验证提供者
     *
     * @param http http
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2ResourceOwnerAuthenticationProvider(HttpSecurity http) {
        // @formatter:off
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = http.getSharedObject(OAuth2TokenGenerator.class);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider
                = new OAuth2ResourceOwnerPasswordAuthenticationProvider(authorizationService, tokenGenerator, authenticationManager);
        http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);

        OAuth2ResourceOwnerSmsAuthenticationProvider resourceOwnerSmsAuthenticationProvider
                = new OAuth2ResourceOwnerSmsAuthenticationProvider(authorizationService,tokenGenerator,authenticationManager);
        http.authenticationProvider(resourceOwnerSmsAuthenticationProvider);
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider(userDetailsService, redisTemplate);
        http.authenticationProvider(smsAuthenticationProvider);

        OAuth2ResourceOwnerEmailAuthenticationProvider resourceOwnerEmailAuthenticationProvider
                = new OAuth2ResourceOwnerEmailAuthenticationProvider(authorizationService, tokenGenerator, authenticationManager);
        http.authenticationProvider(resourceOwnerEmailAuthenticationProvider);
        EmailAuthenticationProvider emailAuthenticationProvider = new EmailAuthenticationProvider(userDetailsService, redisTemplate);
        http.authenticationProvider(emailAuthenticationProvider);
        // @formatter:on
    }

}
