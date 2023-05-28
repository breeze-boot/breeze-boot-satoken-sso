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

package com.breeze.boot.auth.service.impl;

import com.breeze.boot.auth.domain.UserPrincipal;
import com.breeze.boot.auth.service.IUserDetailService;
import com.breeze.boot.core.base.BaseLoginUser;
import com.breeze.boot.core.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户主体服务
 *
 * @author gaoweixuan
 * @date 2023-04-21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailService implements IUserDetailService {

    private final static String PASSWORD = "password";
    private final static String GRANT_TYPE = "grant_type";
    private final AesSecretProperties aesSecretProperties;
    /**
     * 用户服务接口
     */
    private final SysUserService userService;

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
        return this.getLoginUserInfo(this.userService.loadUserByUsername(username));
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
        Result<BaseLoginUser> loginUserResult = this.userService.loadUserByPhone(phone);
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
        Result<BaseLoginUser> loginUserResult = this.userService.loadUserByEmail(email);
        return this.getLoginUserInfo(loginUserResult);
    }

}
