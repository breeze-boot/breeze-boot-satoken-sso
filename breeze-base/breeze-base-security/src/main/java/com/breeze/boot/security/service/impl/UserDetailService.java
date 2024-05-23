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

package com.breeze.boot.security.service.impl;

import com.breeze.boot.core.base.BaseLoginUser;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.model.entity.UserPrincipal;
import com.breeze.boot.security.service.ISysUserService;
import com.breeze.boot.security.service.IUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.function.Supplier;

/**
 * 用户主体服务
 *
 * @author gaoweixuan
 * @since 2023-04-21
 */
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements IUserDetailService {

    /**
     * 用户服务接口
     */
    private final Supplier<ISysUserService> userService;

    /**
     * 加载用户用户名
     *
     * @param username 用户名
     * @return {@link UserPrincipal}
     */
    @Override
    public UserPrincipal loadUserByUsername(String username) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes is null");
        this.getTenantId(requestAttributes);
        return this.getLoginUserInfo(this.userService.get().loadUserByUsername(username));
    }

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link UserDetails}
     */
    @Override
    public UserPrincipal loadUserByPhone(String phone) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes is null");
        this.getTenantId(requestAttributes);
        Result<BaseLoginUser> loginUserResult = this.userService.get().loadUserByPhone(phone);
        return this.getLoginUserInfo(loginUserResult);
    }

    /**
     * 加载用户通过电子邮件
     *
     * @param email 邮箱
     * @return {@link Object}
     */
    @Override
    public UserPrincipal loadUserByEmail(String email) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes is null");
        this.getTenantId(requestAttributes);
        Result<BaseLoginUser> loginUserResult = this.userService.get().loadUserByEmail(email);
        return this.getLoginUserInfo(loginUserResult);
    }

}
