/*
 * Copyright (c) 2024, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.sso.client.controller;

import com.breeze.boot.security.sso.client.security.jwt.BreezeJwsTokenProvider;
import com.breeze.boot.security.sso.client.security.mobile.code.MobileCodeAuthenticationToken;
import com.breeze.boot.security.sso.client.security.mobile.password.MobilePasswordAuthenticationToken;
import com.breeze.boot.security.sso.client.security.model.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地登录控制器
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final BreezeJwsTokenProvider jwsTokenProvider;

    @Value("${spring.security.jwt.expiration:7200}")
    private int expiration;

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link LoginInfo }
     */
    @PostMapping("/login")
    public LoginInfo login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.toLowerCase().trim(), password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String accessToken = jwsTokenProvider.createJwtToken(authentication);
        return LoginInfo.builder().tokenType("Bearer").accessToken(accessToken).expires((long) expiration).build();
    }

    /**
     * 手机号验证码登录
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return {@link LoginInfo }
     */
    @PostMapping("/mobileCodeLogin")
    public LoginInfo mobileCodeLogin(String mobile, String code) {
        MobileCodeAuthenticationToken authenticationToken = new MobileCodeAuthenticationToken(mobile.toLowerCase().trim(), code);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String accessToken = jwsTokenProvider.createJwtToken(authentication);
        return LoginInfo.builder().tokenType("Bearer").accessToken(accessToken).expires((long) expiration).build();
    }

    /**
     * 手机号密码登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @return {@link LoginInfo }
     */
    @PostMapping("/mobilePasswordLogin")
    public LoginInfo mobilePasswordLogin(String mobile, String password) {
        MobilePasswordAuthenticationToken authenticationToken = new MobilePasswordAuthenticationToken(mobile.toLowerCase().trim(), password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String accessToken = jwsTokenProvider.createJwtToken(authentication);
        return LoginInfo.builder().tokenType("Bearer").accessToken(accessToken).expires((long) expiration).build();
    }

}
