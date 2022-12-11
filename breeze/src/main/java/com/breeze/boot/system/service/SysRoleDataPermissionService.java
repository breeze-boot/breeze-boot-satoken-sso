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

import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.entity.DataPermissionDTO;
import com.breeze.boot.system.domain.SysRoleDataPermission;

import java.util.List;
import java.util.Set;

/**
 * 系统角色数据权限服务
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
public interface SysRoleDataPermissionService extends IService<SysRoleDataPermission> {

    /**
     * 角色数据权限列表
     *
     * @param roleIdSet 角色ID Set
     * @return {@link List}<{@link DataPermissionDTO}>
     */
    List<DataPermissionDTO> listRoleDataPermissionByRoleIds(Set<Long> roleIdSet);

    /**
     * 编辑角色数据权限
     *
     * @param roleDataPermissionList 角色数据权限列表
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> editRoleDataPermission(List<SysRoleDataPermission> roleDataPermissionList);

}
