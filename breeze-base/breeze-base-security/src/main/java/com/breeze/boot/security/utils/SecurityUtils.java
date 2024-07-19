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

package com.breeze.boot.security.utils;

import cn.hutool.core.bean.BeanUtil;
import com.breeze.boot.core.base.UserInfoDTO;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

/**
 * 安全工具
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
public class SecurityUtils {
    private static final String USER_PROPERTY_KEY = "userProperty";

    /**
     * 获取当前JWT用户
     *
     * @return {@link UserInfoDTO}
     * @throws OAuth2AuthenticationException 如果用户未登录或JWT验证失败
     */
    @SneakyThrows
    public static UserInfoDTO getCurrentUser() throws OAuth2AuthenticationException {
        Map<String, Object> claims = getClaims();

        Object userPropertyObj = claims.get(USER_PROPERTY_KEY);
        if(Objects.isNull(userPropertyObj)){
            throw new SystemServiceException(ResultCode.exception("请重新登录"));
        }
        if (!(userPropertyObj instanceof LinkedTreeMap)) {
            throw new SystemServiceException(ResultCode.exception("请重新登录"));
        }

        LinkedTreeMap<?, ?> map = (LinkedTreeMap<?, ?>) userPropertyObj;
        UserInfoDTO user = new UserInfoDTO();
        BeanUtil.fillBeanWithMap(map, user, true);
        return user;
    }

    @NotNull
    private static Map<String, Object> getClaims() throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SystemServiceException(ResultCode.exception("请重新登录"));
        }

        Object principalObj = authentication.getPrincipal();
        if (!(principalObj instanceof Jwt)) {
            throw new SystemServiceException(ResultCode.exception("请重新登录"));
        }

        Jwt jwt = (Jwt) principalObj;
        Map<String, Object> claims = jwt.getClaims();
        if (claims == null) {
            throw new SystemServiceException(ResultCode.exception("请重新登录"));
        }
        return claims;
    }

    /**
     * 获取用户名
     *
     * @return {@link String}
     */
    public static String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
