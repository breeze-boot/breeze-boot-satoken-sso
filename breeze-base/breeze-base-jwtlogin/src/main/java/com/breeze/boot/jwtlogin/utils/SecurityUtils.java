/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.jwtlogin.utils;

import com.breeze.boot.jwtlogin.entity.CurrentLoginUser;
import com.breeze.boot.jwtlogin.ex.AcessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        try {
            return (CurrentLoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AcessException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 得到认证
     * 获取Authentication
     *
     * @return {@link Authentication}
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
