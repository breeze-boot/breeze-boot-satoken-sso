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

package com.breeze.boot.modules.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysRoleColumnPermission;
import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.form.ColumnPermissionForm;
import com.breeze.boot.modules.auth.model.form.DeleteColumnPermissionForm;
import com.breeze.boot.modules.auth.model.vo.RoleColumnPermissionVO;
import com.breeze.boot.modules.auth.service.SysRoleColumnPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 系统列字段权限管理模块
 *
 * @author gaoweixuan
 * @since 2024-03-18
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/columnPermission")
@Tag(name = "系统列字段权限管理模块", description = "SysColumnPermissionController")
public class SysRoleColumnPermissionController {

    /**
     * 系统f列字段权限服务
     */
    private final SysRoleColumnPermissionService sysRoleColumnPermissionService;

    /**
     * 获取设置的角色列级权限
     *
     * @param roleId 角色ID
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link SysRoleColumnPermission}>>>
     */
    @Operation(summary = "获取设置的角色列级权限")
    @GetMapping("/listSetColumnPermission")
    public Result<Map<String, List<RoleColumnPermissionVO>>> listSetColumnPermission(@Parameter(description = "角色ID")
                                                                                     @NotNull(message = "角色ID不能为空")
                                                                                     @RequestParam String roleId) {
        return this.sysRoleColumnPermissionService.listSetColumnPermission(roleId);
    }

    /**
     * 设置角色列级权限
     *
     * @param permissionParam 字段权限参数
     * @return {@link Result}<{@link Page}<{@link SysRowPermission}>>
     */
    @Operation(summary = "设置角色列级权限")
    @PostMapping("/setColumnPermission")
    @PreAuthorize("hasAnyAuthority('auth:role:column:permission:modify', 'ROLE_ADMIN')")
    public Result<Boolean> setColumnPermission(@RequestBody ColumnPermissionForm permissionParam) {
        return this.sysRoleColumnPermissionService.setColumnPermission(permissionParam);
    }

    /**
     * 删除设置的角色列级权限
     *
     * @param deleteColumnPermissionForm 字段权限参数
     * @return {@link Result}<{@link Page}<{@link SysRowPermission}>>
     */
    @Operation(summary = "删除设置的角色列级权限")
    @DeleteMapping("/removeColumnPermission")
    @PreAuthorize("hasAnyAuthority('auth:role:column:permission:remove', 'ROLE_ADMIN')")
    public Result<Boolean> removeColumnPermission(@RequestBody DeleteColumnPermissionForm deleteColumnPermissionForm) {
        return this.sysRoleColumnPermissionService.removeColumnPermission(deleteColumnPermissionForm);
    }

}
