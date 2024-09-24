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

package com.breeze.boot.security.sso.client.security.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.breeze.boot.security.sso.client.security.utils.SecurityUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.util.Set;

/**
 * SpringSecurity 权限校验
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Slf4j
@Component("ss")
@RequiredArgsConstructor
public class PermissionService {

    private static final String AUTH_ROLE_KEY = "auth:role:";

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断请求是否拥有操作权限
     */
    public boolean hasPerm(String requiredPerm) {
        if (StrUtil.isBlank(requiredPerm)) {
            return false;
        }

        // 超级管理员放行
        if (SecurityUtils.isRoot()) {
            return true;
        }

        Set<String> roleCodes = SecurityUtils.getRoles();
        if (CollectionUtil.isEmpty(roleCodes)) {
            return false;
        }

        for (String roleCode : roleCodes) {
            Set<String> rolePerms = (Set<String>) redisTemplate.opsForHash().get(AUTH_ROLE_KEY, roleCode);

            if (CollectionUtil.isEmpty(rolePerms)) {
                // 无权限，判断下一个角色
                continue;
            }

            // 匹配权限，支持通配符
            if (rolePerms.stream().anyMatch(rolePerm -> PatternMatchUtils.simpleMatch(rolePerm, requiredPerm))) {
                // 找到匹配权限，直接返回
                return true;
            }
        }

        log.error("用户无操作权限");
        // 没有找到匹配权限
        return false;
    }


    /**
     * 初始化权限缓存
     */
    @PostConstruct
    public void initPermissionCache() {
        refreshPermission();
    }

    /**
     * 刷新权限缓存
     */
    public void refreshPermission() {
        redisTemplate.opsForHash().delete(AUTH_ROLE_KEY, "*");
        // redisTemplate.opsForHash().put(AUTH_ROLE_KEY, roleCode, perms);
    }

}
