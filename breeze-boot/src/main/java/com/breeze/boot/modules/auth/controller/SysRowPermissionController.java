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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.params.DataPermissionParam;
import com.breeze.boot.modules.auth.model.query.DataPermissionQuery;
import com.breeze.boot.modules.auth.service.SysRoleRowPermissionService;
import com.breeze.boot.modules.auth.service.SysRowPermissionService;
import com.breeze.boot.modules.system.model.entity.SysTenant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * 系统数据权限控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/permission")
@Tag(name = "系统数据权限管理模块", description = "SysPermissionController")
public class SysRowPermissionController {

    /**
     * 系统数据权限服务
     */
    private final SysRowPermissionService sysRowPermissionService;

    /**
     * 系统角色数据权限服务
     */
    private final SysRoleRowPermissionService sysRoleRowPermissionService;

    /**
     * 列表
     *
     * @param permissionQuery 数据权限查询
     * @return {@link Result}<{@link Page}<{@link SysRowPermission}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('auth:permission:list')")
    public Result<Page<SysRowPermission>> list(DataPermissionQuery permissionQuery) {
        return Result.ok(this.sysRowPermissionService.listPage(permissionQuery));
    }

    /**
     * 详情
     *
     * @param permissionId 权限ID
     * @return {@link Result}<{@link SysRowPermission}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{permissionId}")
    @PreAuthorize("hasAnyAuthority('auth:permission:info')")
    public Result<SysRowPermission> info(@PathVariable("permissionId") Long permissionId) {
        return Result.ok(this.sysRowPermissionService.getById(permissionId));
    }


    /**
     * 校验权限编码是否重复
     *
     * @param permissionCode 权限编码
     * @param permissionId   权限ID
     * @return {@link Result}<{@link SysTenant}>
     */
    @Operation(summary = "校验权限编码是否重复")
    @GetMapping("/checkPermissionCode")
    @PreAuthorize("hasAnyAuthority('auth:permission:list')")
    public Result<Boolean> checkTenantCode(@NotBlank(message = "权限编码不能为空") @RequestParam("permissionCode") String permissionCode,
                                           @RequestParam(value = "permissionId", required = false) Long permissionId) {
        return Result.ok(Objects.isNull(this.sysRowPermissionService.getOne(Wrappers.<SysRowPermission>lambdaQuery()
                .ne(Objects.nonNull(permissionId), SysRowPermission::getId, permissionId)
                .eq(SysRowPermission::getPermissionCode, permissionCode))));
    }

    /**
     * 创建
     *
     * @param permissionParam 数据权限参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('auth:permission:create')")
    @BreezeSysLog(description = "数据权限信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody DataPermissionParam permissionParam) {
        return this.sysRowPermissionService.saveRowPermission(permissionParam);
    }

    /**
     * 修改
     *
     * @param permissionParam 数据权限参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('auth:permission:modify')")
    @BreezeSysLog(description = "数据权限信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody DataPermissionParam permissionParam) {
        return this.sysRowPermissionService.modifyPermission(permissionParam);
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('auth:permission:delete')")
    @BreezeSysLog(description = "数据权限信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysRowPermissionService.removeRowPermissionByIds(Arrays.asList(ids));
    }

}
