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

package com.breeze.boot.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysRole;
import com.breeze.boot.system.domain.SysRoleMenu;
import com.breeze.boot.system.dto.MenuPermissionDTO;
import com.breeze.boot.system.dto.RoleDTO;
import com.breeze.boot.system.service.SysRoleMenuService;
import com.breeze.boot.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统角色控制器
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Tag(name = "角色管理模块", description = "角色管理模块")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    /**
     * 系统角色服务
     */
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 系统角色菜单服务
     */
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     *
     * @param roleDTO 角色dto
     * @return {@link Result}
     */
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:role:list')")
    public Result<Page<SysRole>> list(@RequestBody RoleDTO roleDTO) {
        return Result.ok(this.sysRoleService.listPage(roleDTO));
    }

    /**
     * 获取树形权限列表 选中数据
     *
     * @param roleId 角色id
     * @return {@link Result}
     */
    @GetMapping("/listRolesPermission")
    @PreAuthorize("hasAnyAuthority('sys:role:list')")
    public Result<List<SysRoleMenu>> listRolesPermission(@RequestParam Long roleId) {
        return Result.ok(this.sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId)));
    }

    /**
     * 编辑权限
     *
     * @param menuRoleDTO dto菜单作用
     * @return {@link Result}
     */
    @PutMapping("/editPermission")
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    public Result<Boolean> editPermission(@RequestBody MenuPermissionDTO menuRoleDTO) {
        return this.sysRoleMenuService.editPermission(menuRoleDTO);
    }

    /**
     * 信息
     *
     * @param id id
     * @return {@link Result}
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:role:info')")
    public Result<SysRole> info(@PathVariable("id") Long id) {
        return Result.ok(sysRoleService.getById(id));
    }

    /**
     * 保存
     *
     * @param roleEntity 角色实体
     * @return {@link Result}
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:role:save')")
    public Result<Boolean> save(@RequestBody SysRole roleEntity) {
        return Result.ok(sysRoleService.save(roleEntity));
    }

    /**
     * 更新
     *
     * @param roleEntity 角色实体
     * @return {@link Result}
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    public Result<Boolean> update(@RequestBody SysRole roleEntity) {
        return Result.ok(sysRoleService.updateById(roleEntity));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    public Result<Boolean> delete(@RequestBody Long[] ids) {
        return sysRoleService.deleteByIds(Arrays.asList(ids));
    }

}
