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

package com.breeze.boot.security.service;

import com.breeze.boot.core.base.BaseLoginUser;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.params.AuthLoginParam;
import com.breeze.boot.security.params.WxLoginParam;

/**
 * 系统用户服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface ISysUserService {

    /**
     * 加载用户通过用户名
     *
     * @param username 用户名
     * @return {@link Result}<{@link BaseLoginUser}>
     */
    Result<BaseLoginUser> loadUserByUsername(String username);

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link Result}<{@link BaseLoginUser}>
     */
    Result<BaseLoginUser> loadUserByPhone(String phone);

    /**
     * 加载用户通过电子邮件
     *
     * @param email 电子邮件
     * @return {@link Result}<{@link BaseLoginUser}>
     */
    Result<BaseLoginUser> loadUserByEmail(String email);

    /**
     * 加载注册用户通过开放id
     *
     * @param wxLoginParam wx登录参数
     * @return {@link Result}<{@link BaseLoginUser}>
     */
    Result<BaseLoginUser> loadRegisterUserByOpenId(WxLoginParam wxLoginParam);

    /**
     * 加载注册用户通过电话
     *
     * @param authLoginParam 身份验证登录参数
     * @return {@link Result}<{@link BaseLoginUser}>
     */
    Result<BaseLoginUser> loadRegisterUserByPhone(AuthLoginParam authLoginParam);

}
