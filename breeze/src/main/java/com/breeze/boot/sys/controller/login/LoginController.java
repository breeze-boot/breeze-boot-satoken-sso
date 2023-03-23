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

package com.breeze.boot.sys.controller.login;

import com.breeze.boot.sys.service.impl.UserTokenService;
import com.breeze.core.utils.Result;
import com.breeze.security.annotation.NoAuthentication;
import com.breeze.security.config.WxLoginProperties;
import com.breeze.security.email.EmailCodeAuthenticationToken;
import com.breeze.security.params.EmailLoginParam;
import com.breeze.security.params.SmsLoginParam;
import com.breeze.security.params.UserLoginParam;
import com.breeze.security.params.WxLoginParam;
import com.breeze.security.sms.SmsCodeAuthenticationToken;
import com.breeze.security.utils.WxHttpInterfaces;
import com.breeze.security.wx.WxCodeAuthenticationToken;
import com.breeze.security.wxphone.WxPhoneAuthenticationToken;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
     * @param userLoginParam 登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody UserLoginParam userLoginParam) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(userLoginParam.getUsername(), userLoginParam.getPassword());
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(usernamePasswordAuthenticationToken)));
    }

    /**
     * 手机登录
     *
     * @param smsLoginParam 手机登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @PostMapping("/sms")
    @Operation(summary = "手机登录")
    public Result<Map<String, Object>> sms(@Valid @RequestBody SmsLoginParam smsLoginParam) {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = SmsCodeAuthenticationToken.unauthenticated(smsLoginParam.getPhone(), smsLoginParam.getCode());
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(smsCodeAuthenticationToken)));
    }

    /**
     * 用户邮箱登录
     *
     * @param emailLoginParam 登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "邮箱登录")
    @PostMapping("/email")
    public Result<Map<String, Object>> email(@Valid @RequestBody EmailLoginParam emailLoginParam) {
        EmailCodeAuthenticationToken emailCodeAuthenticationToken = EmailCodeAuthenticationToken.unauthenticated(emailLoginParam.getEmail(), emailLoginParam.getCode());
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(emailCodeAuthenticationToken)));
    }

    /**
     * 微信授权登录
     *
     * @param wxLoginParam 微信登录参数
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "微信授权登录")
    @PostMapping("/wxLogin")
    public Result<Map<String, Object>> wxLogin(@RequestBody WxLoginParam wxLoginParam) {
        WxCodeAuthenticationToken wxCodeAuthenticationToken = WxCodeAuthenticationToken.unauthenticated(wxLoginParam, "");
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(wxCodeAuthenticationToken)));
    }

    /**
     * wx手机登录
     *
     * @param wxLoginParam wx登录参数
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "微信授权手机号登录")
    @PostMapping("/wxPhoneLogin")
    public Result<Map<String, Object>> wxPhoneLogin(@RequestBody WxLoginParam wxLoginParam) {
        WxPhoneAuthenticationToken wxPhoneAuthenticationToken = WxPhoneAuthenticationToken.unauthenticated(wxLoginParam.getCode(), "");
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(wxPhoneAuthenticationToken)));
    }

    /**
     * wx授权手机号
     *
     * @param wxLoginParam wx登录参数
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "微信授权手机号")
    @PostMapping("/wxGetPhone")
    public Result<Map<String, Object>> wxGetPhone(@RequestBody WxLoginParam wxLoginParam) {
        String accessToken = WxHttpInterfaces.getAccessToken(this.wxLoginProperties.getAppId(), this.wxLoginProperties.getAppSecret());
        String phoneNumber = WxHttpInterfaces.getPhoneNumber(wxLoginParam.getCode(), accessToken);
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
