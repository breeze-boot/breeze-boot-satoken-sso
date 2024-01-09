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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.domain.SysRole;
import com.breeze.boot.modules.system.domain.dto.UserRole;
import com.breeze.boot.modules.system.domain.query.RoleQuery;

import java.util.List;
import java.util.Set;

/**
 * 系统角色服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 用户角色列表
     *
     * @param userId 用户ID
     * @return {@link List}<{@link SysRole}>
     */
    Set<UserRole> listRoleByUserId(Long userId);

    /**
     * 列表页面
     *
     * @param roleQuery 角色查询
     * @return {@link Page}<{@link SysRole}>
     */
    Page<SysRole> listPage(RoleQuery roleQuery);

    /**
     * 删除由ids
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> deleteByIds(List<Long> ids);

    /**
     * 获取用户角色列表
     *
     * @param userId 用户Id
     * @return {@link Result}<{@link List}<{@link Long}>>
     */
    List<Long> listUserRoles(Long userId);

}

