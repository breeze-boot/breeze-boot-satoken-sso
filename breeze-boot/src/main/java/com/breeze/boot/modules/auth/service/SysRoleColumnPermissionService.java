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

package com.breeze.boot.modules.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysRoleColumnPermission;
import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.form.ColumnPermissionForm;
import com.breeze.boot.modules.auth.model.form.DeleteColumnPermissionForm;
import com.breeze.boot.modules.auth.model.vo.RoleColumnPermissionVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统列字段级数据权限服务
 *
 * @author gaoweixuan
 * @since 2024-02-18
 */
public interface SysRoleColumnPermissionService extends IService<SysRoleColumnPermission> {

    /**
     * 查询列字段级数据权限列表数据
     *
     * @param roleIdSet 角色ID Set
     * @return {@link SysRowPermission}
     */
    List<SysRoleColumnPermission> listRoleColumnExcludeData(Set<Long> roleIdSet);

    /**
     * 修改列字段级数据权限
     *
     * @param permissionParam 权限参数
     * @return {@link Page}<{@link SysRowPermission}>
     */
    Result<Boolean> setColumnPermission(ColumnPermissionForm permissionParam);

    /**
     * 获取列字段级数据权限列表
     *
     * @param roleId 角色ID
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link RoleColumnPermissionVO}>>>
     */
    Result<Map<String, List<RoleColumnPermissionVO>>> listSetColumnPermission(String roleId);

    /**
     * 删除列字段级数据权限
     *
     * @param deleteColumnPermissionForm 权限参数
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeColumnPermission(DeleteColumnPermissionForm deleteColumnPermissionForm);

}
