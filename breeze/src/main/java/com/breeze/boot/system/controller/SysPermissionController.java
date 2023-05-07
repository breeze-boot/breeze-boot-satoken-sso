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

package com.breeze.boot.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.system.domain.SysPermission;
import com.breeze.boot.system.domain.SysRolePermission;
import com.breeze.boot.system.params.DataPermissionParam;
import com.breeze.boot.system.query.DataPermissionQuery;
import com.breeze.boot.system.service.SysPermissionService;
import com.breeze.boot.system.service.SysRolePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 系统数据权限控制器
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/permission")
@Tag(name = "系统数据权限管理模块", description = "SysPermissionController")
public class SysPermissionController {

    /**
     * 系统数据权限服务
     */
    private final SysPermissionService sysPermissionService;

    /**
     * 系统角色数据权限服务
     */
    private final SysRolePermissionService sysRolePermissionService;

    /**
     * 列表
     *
     * @param permissionQuery 数据权限查询
     * @return {@link Result}<{@link Page}<{@link SysPermission}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:permission:list')")
    public Result<Page<SysPermission>> list(@RequestBody DataPermissionQuery permissionQuery) {
        return Result.ok(this.sysPermissionService.listPage(permissionQuery));
    }

    /**
     * 创建
     *
     * @param permissionParam 数据权限参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('sys:permission:create')")
    @BreezeSysLog(description = "数据权限信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody DataPermissionParam permissionParam) {
        return this.sysPermissionService.savePermission(permissionParam);
    }

    /**
     * 修改
     *
     * @param permissionParam 数据权限参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('sys:permission:modify')")
    @BreezeSysLog(description = "数据权限信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody DataPermissionParam permissionParam) {
        return this.sysPermissionService.modifyPermission(permissionParam);
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:permission:delete')")
    @BreezeSysLog(description = "数据权限信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysPermissionService.removePermissionByIds(Arrays.asList(ids));
    }

    /**
     * 编辑角色数据权限
     *
     * @param rolePermissionList 角色数据权限集合
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "编辑角色数据权限")
    @PostMapping("/editRolePermission")
    @PreAuthorize("hasAnyAuthority('sys:permission:edit')")
    @BreezeSysLog(description = "编辑角色数据权限", type = LogType.EDIT)
    public Result<Boolean> editRolePermission(@RequestBody List<SysRolePermission> rolePermissionList) {
        return this.sysRolePermissionService.editRolePermission(rolePermissionList);
    }

}
