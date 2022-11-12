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
import com.breeze.boot.security.annotation.NoAuthentication;
import com.breeze.boot.security.config.JwtConfig;
import com.breeze.boot.security.email.EmailCodeAuthenticationToken;
import com.breeze.boot.security.entity.CurrentLoginUser;
import com.breeze.boot.security.entity.EmailLoginBody;
import com.breeze.boot.security.entity.SmsLoginBody;
import com.breeze.boot.security.entity.UserLoginBody;
import com.breeze.boot.security.sms.SmsCodeAuthenticationToken;
import com.breeze.boot.security.wx.WxCodeAuthenticationToken;
import com.breeze.boot.system.dto.WxLoginDTO;
import com.google.common.collect.Maps;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录控制器
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Slf4j
@NoAuthentication
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
    public Result<Map<String, Object>> login(@Validated @RequestBody UserLoginBody userLoginBody) {
        Instant now = Instant.now();
        // 用户验证
        long expiry = 36000L;
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(userLoginBody.getUsername(), userLoginBody.getPassword());
        return Result.ok(this.createJwtToken(now, expiry, authenticationManager.authenticate(usernamePasswordAuthenticationToken)));
    }

    /**
     * 手机登录
     *
     * @param smsLoginBody 手机登录
     * @return {@link Result}
     */
    @PostMapping("/sms")
    @Operation(summary = "手机登录")
    public Result<Map<String, Object>> sms(@Validated @RequestBody SmsLoginBody smsLoginBody) {
        Instant now = Instant.now();
        // 用户验证
        long expiry = 36000L;
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = SmsCodeAuthenticationToken.unauthenticated(smsLoginBody.getPhone(), smsLoginBody.getCode());
        return Result.ok(this.createJwtToken(now, expiry, authenticationManager.authenticate(smsCodeAuthenticationToken)));
    }

    /**
     * 用户邮箱登录
     *
     * @param emailLoginBody 登录
     * @return {@link Result}
     */
    @Operation(summary = "邮箱登录")
    @PostMapping("/email")
    public Result<Map<String, Object>> email(@Validated @RequestBody EmailLoginBody emailLoginBody) {
        Instant now = Instant.now();
        // 用户验证
        long expiry = 36000L;
        EmailCodeAuthenticationToken emailCodeAuthenticationToken = EmailCodeAuthenticationToken.unauthenticated(emailLoginBody.getEmail(), emailLoginBody.getCode());
        return Result.ok(this.createJwtToken(now, expiry, authenticationManager.authenticate(emailCodeAuthenticationToken)));
    }

    private Map<String, Object> createJwtToken(Instant now, long expiry, Authentication authentication) {
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
                .claim("scope", scope)
                .build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT signedJwt = new SignedJWT(header, claims);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("user_info", currentLoginUser);
        resultMap.put("access_token", jwtConfig.sign(signedJwt).serialize());
        resultMap.put("permissions", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return resultMap;
    }

    /**
     * wx登录
     *
     * @param wxLoginDTO wx登录dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @PostMapping("/wxLogin")
    public Result<Map<String, Object>> wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {
        Map<String, Object> resultMap = Maps.newHashMap();
        // 用户验证
        long expiry = 36000L;
        WxCodeAuthenticationToken wxCodeAuthenticationToken = WxCodeAuthenticationToken.unauthenticated(wxLoginDTO.getCode(), "");
        Instant now = Instant.now();
        resultMap.putAll(this.createJwtToken(now, expiry, authenticationManager.authenticate(wxCodeAuthenticationToken)));
        return Result.ok(resultMap);
    }

}
