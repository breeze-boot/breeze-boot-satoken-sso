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

import cn.hutool.http.HttpUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.annotation.JumpAuth;
import com.breeze.boot.security.auth.AuthAuthenticationToken;
import com.breeze.boot.security.email.EmailCodeAuthenticationToken;
import com.breeze.boot.security.ext.LoginUser;
import com.breeze.boot.security.params.*;
import com.breeze.boot.security.properties.OauthLoginProperties;
import com.breeze.boot.security.properties.WxLoginProperties;
import com.breeze.boot.security.sms.SmsAuthenticationToken;
import com.breeze.boot.security.utils.WxHttpInterfaces;
import com.breeze.boot.security.wx.WxCodeAuthenticationToken;
import com.breeze.boot.security.wxphone.WxPhoneAuthenticationToken;
import com.breeze.boot.system.service.impl.UserTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Controller
@JumpAuth
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
     * wx登录属性
     */
    @Autowired
    private WxLoginProperties wxLoginProperties;

    /**
     * wx登录属性
     */
    @Autowired
    private OauthLoginProperties oauthLoginProperties;

    /**
     * 对象映射器
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 用户名登录
     *
     * @param userLoginParam 登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @ResponseBody
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
    @ResponseBody
    @PostMapping("/sms")
    @Operation(summary = "手机登录")
    public Result<Map<String, Object>> sms(@Valid @RequestBody SmsLoginParam smsLoginParam) {
        SmsAuthenticationToken smsAuthenticationToken = SmsAuthenticationToken.unauthenticated(smsLoginParam.getPhone(), smsLoginParam.getCode());
        return Result.ok(this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(smsAuthenticationToken)));
    }

    /**
     * 用户邮箱登录
     *
     * @param emailLoginParam 登录
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    @GetMapping("/logout")
    public Result<Boolean> logout(@RequestParam("username") String username, HttpServletRequest request) {
        return this.userTokenService.logout(username, request);
    }

    /**
     * 返回授权地址
     *
     * @param appName 应用程序名称
     * @return {@link Result}<{@link String}>
     */
    @ResponseBody
    @GetMapping("/auth/url/{appName}")
    public Result<String> getAuthorizationUrl(@PathVariable String appName) {
        if ("gitee".equals(appName)) {
            String url = String.format("https://gitee.com/oauth/authorize?response_type=code&" +
                            "client_id=%s&redirect_uri=%s"
                    , oauthLoginProperties.getGitee().getClientId(), oauthLoginProperties.getGitee().getRedirectUri());
            return Result.ok(url);
        } else if ("github".equals(appName)) {
            String url = String.format("", oauthLoginProperties.getGithub().getClientId(), oauthLoginProperties.getGithub().getRedirectUri());
            return Result.ok(url);
        }
        return Result.fail("未获取到URI");
    }

    /**
     * 回调
     *
     * @param code  代码
     * @param model 模型
     * @return {@link String }
     */
    @SneakyThrows
    @GetMapping("/auth/gitee/callback")
    public String callback(String code, ModelMap model) {
        String accessTokenUrl = String.format("https://gitee.com/oauth/token?grant_type=authorization_code&" +
                        "code=%s&client_id=%s&redirect_uri=%s&client_secret=%s", code
                , oauthLoginProperties.getGitee().getClientId()
                , oauthLoginProperties.getGitee().getRedirectUri()
                , oauthLoginProperties.getGitee().getClientSecret());
        // 获得 access_token，并解析
        String accessTokenStr = HttpUtil.post(accessTokenUrl, "");
        Map accessTokenMap = objectMapper.readValue(accessTokenStr, Map.class);
        // 去资源服务器获取用户资源，携带 access_token
        String userInfoStr = HttpUtil.get("https://gitee.com/api/v5/user?access_token=" + accessTokenMap.get("access_token").toString());
        // 获取到了用户资源
        Map userInfo = objectMapper.readValue(userInfoStr, Map.class);
        AuthLoginParam authLoginParam = new AuthLoginParam();
        authLoginParam.setEmail(null != userInfo.get("email") ? userInfo.get("email").toString() : "");
        authLoginParam.setAppToken(accessTokenMap.get("access_token").toString());
        authLoginParam.setAppName("gitee");
        authLoginParam.setAppUserName(userInfo.get("name").toString());
        authLoginParam.setLogin(userInfo.get("login").toString());
        authLoginParam.setAvatarUrl(userInfo.get("avatar_url").toString());
        authLoginParam.setId(userInfo.get("id").toString());
        authLoginParam.setScope(accessTokenMap.get("scope").toString());
        authLoginParam.setTenantId(1L);
        model.addAttribute(authLoginParam);
        return "register";
    }

    /**
     * 创建用户
     *
     * @param response       响应
     * @param authLoginParam 身份验证登录参数
     */
    @SneakyThrows
    @PostMapping("/auth/create")
    public void callback(HttpServletResponse response, @ModelAttribute AuthLoginParam authLoginParam) {
        // 创建自己应用的 token
        AuthAuthenticationToken authenticationToken = AuthAuthenticationToken.unauthenticated(authLoginParam, "");
        Map<String, Object> tokenMap = this.userTokenService.createJwtToken(36000L, authenticationManager.authenticate(authenticationToken));
        LoginUser loginUser = (LoginUser) tokenMap.get("userInfo");
        // 转发给前端信息
        response.sendRedirect("http://localhost:8081/#/auth-redirect?accessToken=" + tokenMap.get("accessToken") + "&username=" + loginUser.getUsername() + "&tenantId=" + authLoginParam.getTenantId());
    }

}
