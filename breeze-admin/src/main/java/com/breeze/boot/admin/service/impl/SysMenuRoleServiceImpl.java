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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.admin.dto.MenuPermissionDTO;
import com.breeze.boot.admin.entity.SysMenuRole;
import com.breeze.boot.admin.mapper.SysMenuRoleMapper;
import com.breeze.boot.admin.service.SysMenuRoleService;
import com.breeze.boot.core.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单角色服务impl
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysMenuRoleServiceImpl extends ServiceImpl<SysMenuRoleMapper, SysMenuRole> implements SysMenuRoleService {

    /**
     * 编辑权限
     *
     * @param menuPermissionDTO 菜单权限dto
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> editPermission(MenuPermissionDTO menuPermissionDTO) {
        boolean remove = this.remove(Wrappers.<SysMenuRole>lambdaQuery().eq(SysMenuRole::getRoleId, menuPermissionDTO.getRoleId()));
        if (remove) {
            List<SysMenuRole> sysMenuRoleList = menuPermissionDTO.getPermissionIds().stream().map(menuId -> {
                SysMenuRole sysMenuRole = new SysMenuRole();
                sysMenuRole.setMenuId(menuId);
                sysMenuRole.setRoleId(menuPermissionDTO.getRoleId());
                return sysMenuRole;
            }).collect(Collectors.toList());
            this.saveBatch(sysMenuRoleList);
            return Result.ok(Boolean.TRUE);
        }
        return Result.ok(Boolean.FALSE, "修改失败");
    }

}
