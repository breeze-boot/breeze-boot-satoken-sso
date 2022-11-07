/*
 * Copyright (c) 2021-2022, gaoweixuan (gaoweixuan@foxmail.com).
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
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysRole;
import com.breeze.boot.system.domain.SysRoleMenu;
import com.breeze.boot.system.dto.MenuPermissionDTO;
import com.breeze.boot.system.dto.RoleDTO;
import com.breeze.boot.system.service.SysRoleMenuService;
import com.breeze.boot.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 系统角色控制器
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@RestController
@RequestMapping("/sys/role")
@Tag(name = "系统角色管理模块", description = "SysRoleController")
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
    @Operation(summary = "列表")
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
    @Operation(summary = "获取树形权限列表", description = "选中的行的回显")
    @GetMapping("/listRolesPermission")
    @PreAuthorize("hasAnyAuthority('sys:role:list')")
    public Result<List<SysRoleMenu>> listRolesPermission(@RequestParam Long roleId) {
        return Result.ok(this.sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId)));
    }

    /**
     * 编辑权限
     *
     * @param menuRoleDTO dto菜单权限
     * @return {@link Result}
     */
    @Operation(summary = "编辑权限")
    @PutMapping("/editPermission")
    @PreAuthorize("hasAnyAuthority('sys:role:edit')")
    @BreezeSysLog(description = "角色权限信息修改", type = LogType.EDIT)
    public Result<Boolean> editPermission(@Validated @RequestBody MenuPermissionDTO menuRoleDTO) {
        return this.sysRoleMenuService.editPermission(menuRoleDTO);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:role:info')")
    public Result<SysRole> info(@PathVariable("id") Long id) {
        return Result.ok(sysRoleService.getById(id));
    }

    /**
     * 保存
     *
     * @param sysRole 角色实体
     * @return {@link Result}
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:role:save')")
    @BreezeSysLog(description = "角色信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysRole sysRole) {
        return Result.ok(sysRoleService.save(sysRole));
    }

    /**
     * 修改
     *
     * @param sysRole 角色实体
     * @return {@link Result}
     */
    @Operation(summary = "修改")
    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('sys:role:edit')")
    @BreezeSysLog(description = "角色信息修改", type = LogType.EDIT)
    public Result<Boolean> edit(@Validated @RequestBody SysRole sysRole) {
        return Result.ok(sysRoleService.updateById(sysRole));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    @BreezeSysLog(description = "角色信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return sysRoleService.deleteByIds(Arrays.asList(ids));
    }

}
