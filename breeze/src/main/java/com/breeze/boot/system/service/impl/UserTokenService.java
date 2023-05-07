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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.config.JwtPasswordConfiguration;
import com.breeze.boot.security.ext.CurrentLoginUser;
import com.breeze.boot.security.ext.LoginUser;
import com.breeze.boot.security.params.AuthLoginParam;
import com.breeze.boot.security.params.WxLoginParam;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.service.SysUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户令牌服务
 *
 * @author gaoweixuan
 * @date 2022-10-19
 */
@Service
public class UserTokenService {

    /**
     * jwt配置
     */
    @Autowired
    private JwtPasswordConfiguration jwtPasswordConfiguration;

    /**
     * 用户令牌缓存服务
     */
    @Autowired
    private UserTokenCacheService userTokenCacheService;

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 加载用户用户名
     *
     * @param username 用户名
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser loadUserByUsername(@NotNull String username) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        LoginUser loginUser = this.userTokenCacheService.getLoginUser(sysUser);
        return this.convertResponseUserData(loginUser);
    }

    /**
     * 加载用户通过电子邮件
     *
     * @param email 电子邮件
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser loadUserByEmail(@NotNull String email) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, email));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("邮箱不存在");
        }
        LoginUser loginUser = this.userTokenCacheService.getLoginUser(sysUser);
        return this.convertResponseUserData(loginUser);
    }

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser loadUserByPhone(@NotNull String phone) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("电话不存在");
        }
        LoginUser loginUser = this.userTokenCacheService.getLoginUser(sysUser);
        return this.convertResponseUserData(loginUser);
    }

    /**
     * 创建或者加载用户
     *
     * @param wxLoginParam wx登录消息体
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser createOrLoadUser(WxLoginParam wxLoginParam) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getOpenId, wxLoginParam.getOpenId()));
        if (Objects.isNull(sysUser)) {
            // 不存在就去创建
            sysUser = this.sysUserService.registerUser(SysUser.builder()
                    .openId(wxLoginParam.getOpenId())
                    .phone(wxLoginParam.getPhone())
                    .amountName(wxLoginParam.getNickName())
                    .sex(wxLoginParam.getSex())
                    .phone(wxLoginParam.getEmail())
                    .username(wxLoginParam.getOpenId().substring(0, 5) + RandomUtil.randomString(6))
                    .deptId(1L)
                    .build(), "ROLE_MINI");
        }
        LoginUser loginUser = this.userTokenCacheService.getLoginUser(sysUser);
        return this.convertResponseUserData(loginUser);
    }

    /**
     * 创建或者加载用户
     *
     * @param authLoginParam auth三方登录消息体
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser createOrLoadUser(AuthLoginParam authLoginParam) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, authLoginParam.getPhone()));
        if (Objects.isNull(sysUser)) {
            // 不存在就去创建
            sysUser = this.sysUserService.registerUser(SysUser.builder()
                    .phone(authLoginParam.getPhone())
                    .amountName(authLoginParam.getAppUserName())
                    .sex(authLoginParam.getSex())
                    .email(authLoginParam.getEmail())
                    .tenantId(authLoginParam.getTenantId())
                    .username(authLoginParam.getAppUserName() + RandomUtil.randomString(5))
                    .deptId(1L)
                    .build(), "ROLE_AUTH");
        }
        LoginUser loginUser = this.userTokenCacheService.getLoginUser(sysUser);
        return this.convertResponseUserData(loginUser);
    }

    /**
     * 创建或者加载用户通过微信获取的手机号
     *
     * @param phone phone
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser createOrLoadUserByWxPhone(@NotNull String phone) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone));
        if (Objects.isNull(sysUser)) {
            // 不存在就去创建
            sysUser = this.sysUserService.registerUser(SysUser.builder()
                    .phone(phone)
                    .username(phone + RandomUtil.randomString(6))
                    .deptId(1L)
                    .build(), "ROLE_MINI");
        }
        LoginUser loginUser = this.userTokenCacheService.getLoginUser(sysUser);
        return this.convertResponseUserData(loginUser);
    }

    /**
     * 刷新用户
     *
     * @param username 用户名
     */
    public void refreshUser(String username) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名错误或不存在");
        }
        this.userTokenCacheService.getLoginUser(sysUser);
    }

    /**
     * 获取登录用户
     *
     * @param loginUser 登录用户
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser convertResponseUserData(LoginUser loginUser) {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        Optional.ofNullable(loginUser.getAuthorities()).ifPresent(auth -> getAuthorityList(auth, authorities));
        Optional.ofNullable(loginUser.getUserRoleCodes()).ifPresent(roleCode -> getAuthorityList(roleCode, authorities));
        return new CurrentLoginUser(loginUser,
                Objects.equals(loginUser.getIsLock(), 0),
                true,
                true,
                Objects.equals(loginUser.getIsLock(), 0),
                authorities);
    }

    /**
     * 获得权限列表
     *
     * @param auth        身份验证
     * @param authorities 当局
     */
    private void getAuthorityList(Set<String> auth, List<GrantedAuthority> authorities) {
        authorities.addAll(AuthorityUtils.createAuthorityList(auth.toArray(new String[0])));
    }

    /**
     * 创建jwt牌
     *
     * @param expiry         到期
     * @param authentication 身份验证
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public Map<String, Object> createJwtToken(long expiry, Authentication authentication) {
        Instant now = Instant.now();
        CurrentLoginUser currentLoginUser = (CurrentLoginUser) authentication.getPrincipal();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        LoginUser loginUser = currentLoginUser.getLoginUser();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("self")
                .issueTime(new Date(now.toEpochMilli()))
                .expirationTime(new Date(now.plusSeconds(expiry).toEpochMilli()))
                .subject(authentication.getName())
                .claim("userId", loginUser.getId())
                .claim("tenantId", loginUser.getTenantId())
                .claim("username", loginUser.getUsername())
                .claim("userCode", loginUser.getUserCode())
                .claim("scope", scope)
                .build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT signedJwt = new SignedJWT(header, claims);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("userInfo", loginUser);
        resultMap.put("accessToken", jwtPasswordConfiguration.sign(signedJwt).serialize());
        return resultMap;
    }

    public Result<Boolean> logout(String username, HttpServletRequest request) {
        return Result.ok(this.userTokenCacheService.clearUserInfo(username, request));
    }
}
