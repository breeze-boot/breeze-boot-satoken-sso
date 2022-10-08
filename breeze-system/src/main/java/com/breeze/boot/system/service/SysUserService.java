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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.dto.UserDTO;
import com.breeze.boot.system.dto.UserOpenDTO;
import com.breeze.boot.system.entity.SysUser;
import com.breeze.boot.core.Result;
import com.breeze.boot.jwtlogin.entity.LoginUserDTO;

import java.util.List;

/**
 * 系统用户服务
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 负载登录用户名
     *
     * @param username 用户名
     * @return {@link LoginUserDTO}
     */
    LoginUserDTO loadUserByUsername(String username);

    /**
     * 列表页面
     *
     * @param userDTO 用户dto
     * @return {@link Page}<{@link SysUser}>
     */
    IPage<SysUser> listPage(UserDTO userDTO);

    /**
     * 保存用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    Result<Object> saveUser(SysUser sysUser);

    /**
     * 更新用户id
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    Boolean updateUserById(SysUser sysUser);

    /**
     * 开启关闭锁定
     *
     * @param openDTO 打开dto
     * @return {@link Boolean}
     */
    Boolean open(UserOpenDTO openDTO);

    /**
     * 加载用户通过电子邮件
     *
     * @param email 电子邮件
     * @return {@link LoginUserDTO}
     */
    LoginUserDTO loadUserByEmail(String email);

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link LoginUserDTO}
     */
    LoginUserDTO loadUserByPhone(String phone);

    /**
     * 重置密码
     *
     * @param userEntity 用户实体
     * @return {@link Boolean}
     */
    Boolean resetPass(SysUser userEntity);

    /**
     * 删除由ids
     *
     * @param ids id
     * @return {@link Result}
     */
    Result deleteByIds(List<Long> ids);

}
