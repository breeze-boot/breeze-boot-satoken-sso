/*
 * Copyright 2020 the original author or authors.
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

package com.breeze.boot.system.controller.login;

import com.breeze.boot.core.Result;
import com.breeze.boot.security.annotation.JoinWhiteList;
import com.breeze.boot.security.config.JwtConfig;
import com.breeze.boot.security.email.EmailCodeAuthenticationToken;
import com.breeze.boot.security.entity.CurrentLoginUser;
import com.breeze.boot.security.entity.EmailLoginBody;
import com.breeze.boot.security.entity.SmsLoginBody;
import com.breeze.boot.security.entity.UserLoginBody;
import com.breeze.boot.security.sms.SmsCodeAuthenticationToken;
import com.google.common.collect.Maps;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 登录控制器
 *
 * @author breeze
 * @date 2022-08-31
 */
@JoinWhiteList
@RestController
@RequestMapping("/jwt")
@Tag(name = "jwt登录", description = "LoginController")
public class LoginController {

    /**
     * jwt配置
     */
    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 身份验证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用户名登录
     *
     * @param userLoginBody 登录
     * @return {@link Result}
     */
    @Operation(security = {@SecurityRequirement(name = "bearer")}, summary = "用户名登录")
    @PostMapping("/login")
    public Result login(@Validated @RequestBody UserLoginBody userLoginBody) {
        Instant now = Instant.now();
        // 用户验证
        long expiry = 36000L;
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(userLoginBody.getUsername(), userLoginBody.getPassword());
        Map<String, Object> resultMap = createJWTToken(now, expiry, authenticationManager.authenticate(usernamePasswordAuthenticationToken));
        return Result.ok(resultMap);
    }

    /**
     * 手机登录
     *
     * @param smsLoginBody 手机登录
     * @return {@link Result}
     */
    @PostMapping("/sms")
    @Operation(summary = "手机登录")
    public Result sms(@Validated @RequestBody SmsLoginBody smsLoginBody) {
        Instant now = Instant.now();
        // 用户验证
        long expiry = 36000L;
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = SmsCodeAuthenticationToken.unauthenticated(smsLoginBody.getPhone(), smsLoginBody.getCode());
        Map<String, Object> resultMap = createJWTToken(now, expiry, authenticationManager.authenticate(smsCodeAuthenticationToken));
        return Result.ok(resultMap);
    }

    /**
     * 用户邮箱登录
     *
     * @param emailLoginBody 登录
     * @return {@link Result}
     */
    @Operation(summary = "邮箱登录")
    @PostMapping("/email")
    public Result email(@Validated @RequestBody EmailLoginBody emailLoginBody) {
        Instant now = Instant.now();
        // 用户验证
        long expiry = 36000L;
        EmailCodeAuthenticationToken emailCodeAuthenticationToken = EmailCodeAuthenticationToken.unauthenticated(emailLoginBody.getEmail(), emailLoginBody.getCode());
        Map<String, Object> resultMap = createJWTToken(now, expiry, authenticationManager.authenticate(emailCodeAuthenticationToken));
        return Result.ok(resultMap);
    }

    private Map<String, Object> createJWTToken(Instant now, long expiry, Authentication authentication) {
        CurrentLoginUser currentLoginUser = (CurrentLoginUser) authentication.getPrincipal();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("self")
                .issueTime(new Date(now.toEpochMilli()))
                .expirationTime(new Date(now.plusSeconds(expiry).toEpochMilli()))
                .subject(authentication.getName())
                .claim("userId", currentLoginUser.getUserId())
                .claim("tenantId", currentLoginUser.getTenantId())
                .claim("username", currentLoginUser.getUsername())
                .claim("userCode", currentLoginUser.getUserCode())
                .claim("userRoleCodes", Optional.ofNullable(currentLoginUser.getUserRoleCodes())
                        .orElseGet(HashSet::new).stream().collect(Collectors.joining(",")))
                .claim("scope", scope)
                .build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT signedJWT = new SignedJWT(header, claims);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("access_token", jwtConfig.sign(signedJWT).serialize());
        resultMap.put("permissions", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return resultMap;
    }

}
