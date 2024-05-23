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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysRoleColumnPermission;
import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.params.ColumnPermissionParam;
import com.breeze.boot.modules.auth.model.params.DeleteColumnPermissionParam;
import com.breeze.boot.modules.auth.mapper.SysRoleColumnPermissionMapper;
import com.breeze.boot.modules.auth.service.SysRoleColumnPermissionService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统列字段级数据权限服务 impl
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Service
@RequiredArgsConstructor
public class SysRoleColumnPermissionServiceImpl extends ServiceImpl<SysRoleColumnPermissionMapper, SysRoleColumnPermission> implements SysRoleColumnPermissionService {

    /**
     * 查询列字段级数据权限列表数据
     *
     * @param roleIdSet 角色ID Set
     * @return {@link SysRowPermission}
     */
    @Override
    public List<SysRoleColumnPermission> listRoleColumnExcludeData(Set<Long> roleIdSet) {
        return this.baseMapper.listRoleColumnExcludeData(roleIdSet);
    }

    /**
     * 修改列字段级数据权限
     *
     * @param permissionParam 权限参数
     * @return {@link Page}<{@link SysRowPermission}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> setColumnPermission(ColumnPermissionParam permissionParam) {
        try {
            this.removeById(permissionParam.getRoleId());
            Map<String, List<String>> tableColumnNameMap = permissionParam.getTableColumnName();
            tableColumnNameMap.forEach((k, v) -> {
                if (CollUtil.isNotEmpty(v)) {
                    v.forEach(c -> {
                        SysRoleColumnPermission sysRoleColumnPermission = new SysRoleColumnPermission();
                        sysRoleColumnPermission.setTableName(k);
                        sysRoleColumnPermission.setColumnName(c);
                        sysRoleColumnPermission.setRoleId(permissionParam.getRoleId());
                        SysRoleColumnPermission roleColumnPermission = this.getOne(Wrappers.<SysRoleColumnPermission>lambdaQuery().eq(SysRoleColumnPermission::getRoleId, permissionParam.getRoleId()).eq(SysRoleColumnPermission::getColumnName, c));
                        if(Objects.nonNull(roleColumnPermission)){
                            roleColumnPermission.deleteById();
                        }
                        this.save(sysRoleColumnPermission);
                    });
                }
            });
            return Result.ok(Boolean.TRUE);
        } catch (Exception e) {
            log.error("[设列级别数据权限失败]", e);
            return Result.fail(ResultCode.FAIL);
        }
    }

    /**
     * 查询列字段级数据权限列表数据
     *
     * @param roleId 角色ID
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link SysRoleColumnPermission}>>>
     */
    @Override
    public Result<Map<String, List<SysRoleColumnPermission>>> listSetColumnPermission(String roleId) {
        List<SysRoleColumnPermission> roleColumnPermissionList = this.list(Wrappers.<SysRoleColumnPermission>lambdaQuery().eq(SysRoleColumnPermission::getRoleId, roleId));
        if (CollUtil.isEmpty(roleColumnPermissionList)) {
            return Result.ok(Maps.newHashMap());
        }
        return Result.ok(roleColumnPermissionList.stream().collect(Collectors.groupingBy(SysRoleColumnPermission::getTableName, Collectors.toList())));
    }

    @Override
    public Result<Boolean> removeColumnPermission(DeleteColumnPermissionParam deleteColumnPermissionParam) {
        return Result.ok(this.remove(Wrappers.<SysRoleColumnPermission>lambdaQuery().eq(SysRoleColumnPermission::getRoleId, deleteColumnPermissionParam.getRoleId())
                .eq(SysRoleColumnPermission::getColumnName, deleteColumnPermissionParam.getColumnName())));
    }
}
