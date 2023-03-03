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

import com.breeze.core.utils.Result;
import com.breeze.security.annotation.NoAuthentication;
import com.breeze.security.config.WxLoginProperties;
import com.breeze.security.email.EmailCodeAuthenticationToken;
import com.breeze.security.entity.EmailLoginBody;
import com.breeze.security.entity.SmsLoginBody;
import com.breeze.security.entity.UserLoginBody;
import com.breeze.security.entity.WxLoginBody;
import com.breeze.security.sms.SmsCodeAuthenticationToken;
import com.breeze.security.utils.WxHttpInterfaces;
import com.breeze.security.wx.WxCodeAuthenticationToken;
import com.breeze.security.wxphone.WxPhoneAuthenticationToken;
import com.breeze.boot.system.service.impl.UserTokenService;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Slf4j
@RestController
@NoAuthentication
@SecurityRequirement(name = "Bearer")
@RequestMapping("/breeze")
@Tag(name = "登录", description = "LoginController")
public class LoginController {

    /**
     * 身份验证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用户令牌服务
     */
    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private WxLoginProperties wxLoginProperties;

    /**
     * 用户名登录
     *
     * @param userLoginBody 登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Validated @RequestBody UserLoginBody userLoginBody) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(userLoginBody.getUsername(), userLoginBody.getPassword());
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(usernamePasswordAuthenticationToken)));
    }

    /**
     * 手机登录
     *
     * @param smsLoginBody 手机登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @PostMapping("/sms")
    @Operation(summary = "手机登录")
    public Result<Map<String, Object>> sms(@Validated @RequestBody SmsLoginBody smsLoginBody) {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = SmsCodeAuthenticationToken.unauthenticated(smsLoginBody.getPhone(), smsLoginBody.getCode());
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(smsCodeAuthenticationToken)));
    }

    /**
     * 用户邮箱登录
     *
     * @param emailLoginBody 登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "邮箱登录")
    @PostMapping("/email")
    public Result<Map<String, Object>> email(@Validated @RequestBody EmailLoginBody emailLoginBody) {
        EmailCodeAuthenticationToken emailCodeAuthenticationToken = EmailCodeAuthenticationToken.unauthenticated(emailLoginBody.getEmail(), emailLoginBody.getCode());
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(emailCodeAuthenticationToken)));
    }

    /**
     * 微信授权登录
     *
     * @param wxLoginBody 微信登录参数
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "微信授权登录")
    @PostMapping("/wxLogin")
    public Result<Map<String, Object>> wxLogin(@RequestBody WxLoginBody wxLoginBody) {
        WxCodeAuthenticationToken wxCodeAuthenticationToken = WxCodeAuthenticationToken.unauthenticated(wxLoginBody, "");
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(wxCodeAuthenticationToken)));
    }

    /**
     * wx手机登录
     *
     * @param wxLoginBody wx登录dto
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "微信授权手机号登录")
    @PostMapping("/wxPhoneLogin")
    public Result<Map<String, Object>> wxPhoneLogin(@RequestBody WxLoginBody wxLoginBody) {
        WxPhoneAuthenticationToken wxPhoneAuthenticationToken = WxPhoneAuthenticationToken.unauthenticated(wxLoginBody.getCode(), "");
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(wxPhoneAuthenticationToken)));
    }

    /**
     * wx授权手机号
     *
     * @param wxLoginBody wx登录dto
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "微信授权手机号")
    @PostMapping("/wxGetPhone")
    public Result<Map<String, Object>> wxGetPhone(@RequestBody WxLoginBody wxLoginBody) {
        String accessToken = WxHttpInterfaces.getAccessToken(this.wxLoginProperties.getAppId(), this.wxLoginProperties.getAppSecret());
        String phoneNumber = WxHttpInterfaces.getPhoneNumber(wxLoginBody.getCode(), accessToken);
        HashMap<@Nullable String, @Nullable Object> resultMap = Maps.newHashMap();
        resultMap.put("phone", phoneNumber);
        return Result.ok(resultMap);
    }

    /**
     * 登出
     *
     * @param username 用户名
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @GetMapping("/logout")
    public Result<Boolean> logout(@RequestParam("username") String username, HttpServletRequest request) {
        return this.userTokenService.logout(username, request);
    }
}
