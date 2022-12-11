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

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.annotation.NoAuthentication;
import com.breeze.boot.security.email.EmailCodeAuthenticationToken;
import com.breeze.boot.security.entity.EmailLoginBody;
import com.breeze.boot.security.entity.SmsLoginBody;
import com.breeze.boot.security.entity.UserLoginBody;
import com.breeze.boot.security.entity.WxLoginBody;
import com.breeze.boot.security.sms.SmsCodeAuthenticationToken;
import com.breeze.boot.security.wx.WxCodeAuthenticationToken;
import com.breeze.boot.system.service.impl.UserTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
     * 微信登录
     *
     * @param wxLoginBody 微信登录参数
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @PostMapping("/wxLogin")
    public Result<Map<String, Object>> wxLogin(@RequestBody WxLoginBody wxLoginBody) {
        WxCodeAuthenticationToken wxCodeAuthenticationToken = WxCodeAuthenticationToken.unauthenticated(wxLoginBody.getCode(), "");
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(wxCodeAuthenticationToken)));
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
