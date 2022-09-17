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

package com.breeze.boot.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.admin.dto.RoleDTO;
import com.breeze.boot.admin.entity.SysMenuRoleEntity;
import com.breeze.boot.admin.entity.SysRoleEntity;
import com.breeze.boot.admin.mapper.SysRoleMapper;
import com.breeze.boot.admin.service.SysMenuRoleService;
import com.breeze.boot.admin.service.SysRoleService;
import com.breeze.boot.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色服务impl
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysMenuRoleService sysMenuRoleService;

    /**
     * 用户角色列表
     *
     * @param id id
     * @return {@link List}<{@link SysRoleEntity}>
     */
    @Override
    public List<SysRoleEntity> listUserRole(Long id) {
        return this.baseMapper.listUserRole(id);
    }

    /**
     * 列表页面
     *
     * @param roleDTO 角色dto
     * @return {@link Page}<{@link SysRoleEntity}>
     */
    @Override
    public Page<SysRoleEntity> listPage(RoleDTO roleDTO) {
        Page<SysRoleEntity> platformEntityPage = new Page<>(roleDTO.getCurrent(), roleDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(roleDTO.getRoleName()), SysRoleEntity::getRoleName, roleDTO.getRoleName())
                .like(StrUtil.isAllNotBlank(roleDTO.getRoleCode()), SysRoleEntity::getRoleCode, roleDTO.getRoleCode())
                .page(platformEntityPage);
    }

    @Override
    public Result deleteByIds(List<Long> ids) {
        List<SysRoleEntity> roleEntityList = this.listByIds(ids);
        if (CollUtil.isEmpty(roleEntityList)) {
            return Result.fail(Boolean.FALSE, "角色不存在");
        }
        boolean remove = this.removeByIds(ids);
        if (remove) {
            // 删除用户角色关系
            this.sysMenuRoleService.remove(Wrappers.<SysMenuRoleEntity>lambdaQuery()
                    .eq(SysMenuRoleEntity::getRoleId, roleEntityList.stream().map(SysRoleEntity::getId).collect(Collectors.toList())));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}
