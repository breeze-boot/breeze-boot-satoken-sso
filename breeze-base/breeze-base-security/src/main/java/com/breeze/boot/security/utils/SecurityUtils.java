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

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.breeze.boot.core.base.BaseLoginUser;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;

import static com.breeze.boot.core.constants.CacheConstants.LOGIN_USER;

/**
 * 安全工具
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
    public static BaseLoginUser getCurrentUser() {
        String name = getName();
        if (StrUtil.isAllBlank(name)) {
            return null;
        }
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(LOGIN_USER);
        if (Objects.isNull(cache)) {
            throw new AccessDeniedException("用户未登录");
        }
        return cache.get(name, BaseLoginUser.class);
    }

    /**
     * 获取用户名
     *
     * @return {@link String}
     */
    public static String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * 租户ID
     *
     * @return long
     */
    public static long getTenantId() {
        BaseLoginUser loginUser = getCurrentUser();
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("JWT验证失败");
        }
        return loginUser.getTenantId();
    }

}
