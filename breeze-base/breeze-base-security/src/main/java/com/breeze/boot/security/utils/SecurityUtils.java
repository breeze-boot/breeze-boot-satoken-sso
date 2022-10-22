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

package com.breeze.boot.security.utils;

import com.breeze.boot.security.entity.CurrentLoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 安全跑龙套
 *
 * @author breeze
 * @date 2022-08-31
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户
     * 获取用户
     * * @return {@link CurrentLoginUser}
     */
    public static CurrentLoginUser getCurrentLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (CurrentLoginUser) authentication.getPrincipal();
    }

    /**
     * 获取用户代码
     *
     * @return {@link String}
     */
    public static String getUserCode() {
        CurrentLoginUser currentLoginUser = SecurityUtils.getCurrentLoginUser();
        if (Objects.isNull(currentLoginUser)) {
            return "";
        }
        return currentLoginUser.getUserCode();
    }

    /**
     * 获得用户名
     *
     * @return {@link String}
     */
    public static String getUserName() {
        CurrentLoginUser currentLoginUser = SecurityUtils.getCurrentLoginUser();
        if (Objects.isNull(currentLoginUser)) {
            return "";
        }
        return currentLoginUser.getUsername();
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

}
