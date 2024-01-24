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

package com.breeze.boot.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.domain.SysPermission;
import com.breeze.boot.modules.system.domain.SysRolePermission;
import com.breeze.boot.modules.auth.mapper.SysRolePermissionMapper;
import com.breeze.boot.modules.auth.service.SysRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统角色权限服务impl
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Service
@RequiredArgsConstructor
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    /**
     * 角色数据权限列表
     *
     * @param roleIdSet 角色ID Set
     * @return {@link SysPermission}
     */
    @Override
    public List<SysPermission> listRolePermissionByRoleIds(Set<Long> roleIdSet) {
        return this.baseMapper.listRolePermissionByRoleIds(roleIdSet);
    }

    /**
     * 编辑角色数据权限
     *
     * @param rolePermissionList 角色数据权限列表
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> editRolePermission(List<SysRolePermission> rolePermissionList) {
        Set<Long> roleIdList = rolePermissionList.stream().map(SysRolePermission::getRoleId).collect(Collectors.toSet());
        this.remove(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getRoleId, roleIdList));
        this.saveBatch(rolePermissionList);
        return Result.ok();
    }

}




