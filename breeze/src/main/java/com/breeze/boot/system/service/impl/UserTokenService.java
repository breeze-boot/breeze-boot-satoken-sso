/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.config.JwtConfiguration;
import com.breeze.boot.security.entity.CurrentLoginUser;
import com.breeze.boot.security.entity.LoginUserDTO;
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
    private JwtConfiguration jwtConfiguration;

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
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
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
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
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
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
    }

    /**
     * 创建或者加载用户通过微信OpenId
     *
     * @param openId openId
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser createOrLoadUserByOpenId(@NotNull String openId) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getOpenId, openId));
        if (Objects.isNull(sysUser)) {
            // 不存在就去创建
            sysUser = this.sysUserService.registerUser(SysUser.builder().openId(openId).build());
        }
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
    }

    /**
     * 刷新用户
     *
     * @param username 用户名
     * @return {@link LoginUserDTO}
     */
    public LoginUserDTO refreshUser(String username) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名错误或不存在");
        }
        return this.userTokenCacheService.getLoginUserDTO(sysUser);
    }

    /**
     * 获取登录用户
     *
     * @param loginUserDTO 登录用户dto
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser getLoginUser(LoginUserDTO loginUserDTO) {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        Optional.ofNullable(loginUserDTO.getAuthorities()).ifPresent(auth -> getAuthorityList(auth, authorities));
        Optional.ofNullable(loginUserDTO.getUserRoleCodes()).ifPresent(roleCode -> getAuthorityList(roleCode, authorities));
        return new CurrentLoginUser(loginUserDTO,
                Objects.equals(loginUserDTO.getIsLock(), 0),
                true,
                true,
                Objects.equals(loginUserDTO.getIsLock(), 0),
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
        LoginUserDTO loginUserDTO = currentLoginUser.getLoginUserDTO();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("self")
                .issueTime(new Date(now.toEpochMilli()))
                .expirationTime(new Date(now.plusSeconds(expiry).toEpochMilli()))
                .subject(authentication.getName())
                .claim("userId", loginUserDTO.getId())
                .claim("tenantId", loginUserDTO.getTenantId())
                .claim("username", loginUserDTO.getUsername())
                .claim("userCode", loginUserDTO.getUserCode())
                .claim("scope", scope)
                .build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT signedJwt = new SignedJWT(header, claims);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("user_info", loginUserDTO);
        resultMap.put("access_token", jwtConfiguration.sign(signedJwt).serialize());
        return resultMap;
    }

    public Result<Boolean> logout(String username, HttpServletRequest request) {
        return Result.ok(this.userTokenCacheService.clearUserInfo(username, request));
    }
}
