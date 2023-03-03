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

package com.breeze.security.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.breeze.security.config.JwtProperties;
import com.breeze.security.entity.LoginUserDTO;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;

import static com.breeze.core.constants.CacheConstants.LOGIN_USER;

/**
 * 安全跑龙套
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
public class SecurityUtils {

    /**
     * 获取当前jwt
     *
     * @return {@link Jwt}
     */
    public static Jwt getCurrentJwt() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (Objects.equals("anonymousUser", principal)) {
            return null;
        }
        JwtProperties jwtProperties = SpringUtil.getBean(JwtProperties.class);
        Jwt jwt = (Jwt) principal;
        JWTSigner jwtSigner = JWTSignerUtil.rs256(jwtProperties.getRsaPublicKey());
        if (!JWTUtil.verify(jwt.getTokenValue(), jwtSigner)) {
            throw new BadCredentialsException("令牌错误，请重新登录!");
        }
        return jwt;
    }

    /**
     * 获取用户编码
     *
     * @return {@link String}
     */
    public static String getUserCode() {
        Jwt jwt = SecurityUtils.getCurrentJwt();
        if (Objects.isNull(jwt)) {
            return "";
        }
        return jwt.getClaims().get("userCode").toString();
    }

    /**
     * 获得用户名
     *
     * @return {@link String}
     */
    public static String getUsername() {
        Jwt jwt = SecurityUtils.getCurrentJwt();
        if (Objects.isNull(jwt)) {
            return "";
        }
        return jwt.getClaims().get("username").toString();
    }

    /**
     * 获取认证
     * 获取Authentication
     *
     * @return {@link Authentication}
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static LoginUserDTO getCurrentUser() {
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
        LoginUserDTO loginUserDTO = cacheManager.getCache(LOGIN_USER).get(SecurityUtils.getUsername(), LoginUserDTO.class);
        if (Objects.isNull(loginUserDTO)) {
            throw new AccessDeniedException("用户未登录");
        }
        return loginUserDTO;
    }

    /**
     * 租户ID
     *
     * @return long
     */
    public static long getTenantId() {
        Jwt jwt = SecurityUtils.getCurrentJwt();
        if (Objects.isNull(jwt)) {
            throw new BadJwtException("JWT验证失败");
        }
        return (long) jwt.getClaims().get("tenantId");
    }

}
