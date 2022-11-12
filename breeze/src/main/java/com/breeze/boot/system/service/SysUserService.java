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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.UserDTO;
import com.breeze.boot.system.dto.UserOpenDTO;
import com.breeze.boot.system.dto.UserResetPasswordDTO;
import com.breeze.boot.system.dto.UserRolesDTO;

/**
 * 系统用户服务
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
public interface SysUserService extends IService<SysUser> {

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
    Result<Boolean> saveUser(SysUser sysUser);

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
     * 重置密码
     *
     * @param userResetPasswordDTO 用户重置密码dto
     * @return {@link Boolean}
     */
    Boolean resetPass(UserResetPasswordDTO userResetPasswordDTO);

    /**
     * 删除由ids
     *
     * @param sysUser 用户
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeUser(SysUser sysUser);

    /**
     * 用户分配角色
     *
     * @param userRolesDTO 用户角色dto
     * @return {@link Boolean}
     */
    Result<Boolean> userAddRole(UserRolesDTO userRolesDTO);

    /**
     * 通过ID查询用户
     *
     * @param id id
     * @return {@link SysUser}
     */
    Result<SysUser> getUserById(Long id);

    /**
     * 注册用户
     *
     * @param sysUser 系统用户
     * @return {@link SysUser}
     */
    SysUser registerUser(SysUser sysUser);

}
