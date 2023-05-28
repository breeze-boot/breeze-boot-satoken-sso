
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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysPermission;
import com.breeze.boot.system.domain.SysRolePermission;
import com.breeze.boot.system.mapper.SysPermissionMapper;
import com.breeze.boot.system.params.DataPermissionParam;
import com.breeze.boot.system.query.DataPermissionQuery;
import com.breeze.boot.system.service.SysPermissionService;
import com.breeze.boot.system.service.SysRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.breeze.boot.core.enums.DataPermissionCode.DEPT_LEVEL;


/**
 * 系统数据权限服务 impl
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    /**
     * 系统角色数据权限服务
     */
    private final SysRolePermissionService sysRolePermissionService;

    /**
     * 列表页面
     *
     * @param permissionQuery 权限查询
     * @return {@link Page}<{@link SysPermission}>
     */
    @Override
    public Page<SysPermission> listPage(DataPermissionQuery permissionQuery) {
        return this.baseMapper.listPage(new Page<>(permissionQuery.getCurrent(), permissionQuery.getSize()), permissionQuery);
    }

    /**
     * 保存数据权限
     *
     * @param permissionParam 数据权限参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> savePermission(DataPermissionParam permissionParam) {
        SysPermission sysPermission = SysPermission.builder().build();
        BeanUtil.copyProperties(permissionParam, sysPermission);
        if (StrUtil.equals(DEPT_LEVEL.name(), permissionParam.getPermissionCode()) && CollUtil.isEmpty(permissionParam.getPermissions())) {
            return Result.warning("请选择部门查看的范围");
        }
        sysPermission.setPermissions(String.join(",", permissionParam.getPermissions()));
        return Result.ok(this.save(sysPermission));
    }

    /**
     * 删除数据权限通过IDs
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removePermissionByIds(List<Long> ids) {
        List<SysRolePermission> rolePermissionList = this.sysRolePermissionService
                .list(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getPermissionId, ids));
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            return Result.warning(Boolean.FALSE, "该数据权限已被使用");
        }
        return Result.ok(this.removeBatchByIds(ids));
    }

    @Override
    public Result<Boolean> modifyPermission(DataPermissionParam permissionParam) {
        SysPermission sysPermission = SysPermission.builder().build();
        BeanUtil.copyProperties(permissionParam, sysPermission);
        return Result.ok(sysPermission.updateById());
    }

}
