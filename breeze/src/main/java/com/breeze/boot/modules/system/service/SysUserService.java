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

package com.breeze.boot.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.domain.SysUser;
import com.breeze.boot.modules.system.domain.params.UserOpenParam;
import com.breeze.boot.modules.system.domain.params.UserResetParam;
import com.breeze.boot.modules.system.domain.params.UserRolesParam;
import com.breeze.boot.modules.system.domain.query.UserQuery;
import com.breeze.boot.security.service.ISysUserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统用户服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface SysUserService extends IService<SysUser>, ISysUserService {

    /**
     * 列表页面
     *
     * @param userQuery 用户查询
     * @return {@link IPage}<{@link SysUser}>
     */
    IPage<SysUser> listPage(UserQuery userQuery);

    /**
     * 保存用户
     *
     * @param sysUser 系统用户
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> saveUser(SysUser sysUser);

    /**
     * 通过id更新用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    Boolean updateUserById(SysUser sysUser);

    /**
     * 开关
     *
     * @param userOpenParam 用户打开参数
     * @return {@link Boolean}
     */
    Boolean open(UserOpenParam userOpenParam);

    /**
     * 重置密码
     *
     * @param userResetParam 用户重置密码参数
     * @return {@link Boolean}
     */
    Boolean reset(UserResetParam userResetParam);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeUser(SysUser sysUser);

    /**
     * 用户分配角色
     *
     * @param userRolesParam 用户角色参数
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> setRole(UserRolesParam userRolesParam);

    /**
     * 通过ID查询用户
     *
     * @param id id
     * @return {@link Result}<{@link SysUser}>
     */
    Result<SysUser> getUserById(Long id);

    /**
     * 注册用户
     *
     * @param sysUser  系统用户
     * @param roleCode 角色代码
     * @return {@link SysUser}
     */
    SysUser registerUser(SysUser sysUser, String roleCode);

    /**
     * 查询用户通过部门id
     *
     * @param deptIds 部门IDS
     * @return {@link List}<{@link SysUser}>
     */
    List<SysUser> listUserByDeptId(List<Long> deptIds);

    /**
     * 导出
     *
     * @param response 响应
     */
    void export(HttpServletResponse response);

}
