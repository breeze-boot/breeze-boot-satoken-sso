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

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.security.entity.PermissionDTO;
import com.breeze.boot.system.domain.SysPermission;
import com.breeze.boot.system.domain.SysRolePermission;
import com.breeze.boot.system.dto.SysPermissionDTO;
import com.breeze.boot.system.service.SysPermissionService;
import com.breeze.boot.system.service.SysRolePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/sys/permission")
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
     * @param permissionDTO 数据权限dto
     * @return {@link Result}
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:permission:list')")
    public Result<Page<SysPermission>> list(@RequestBody PermissionDTO permissionDTO) {
        return Result.ok(this.sysPermissionService.listPage(permissionDTO));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:permission:info')")
    public Result<SysPermission> info(@PathVariable("id") Long id) {
        return Result.ok(this.sysPermissionService.getById(id));
    }

    /**
     * 保存
     *
     * @param permission 数据权限实体入参
     * @return {@link Result}
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:permission:save')")
    @BreezeSysLog(description = "数据权限信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysPermissionDTO permission) {
        return this.sysPermissionService.savePermission(permission);
    }

    /**
     * 修改
     *
     * @param SysPermission 数据权限实体
     * @return {@link Result}
     */
    @Operation(summary = "修改")
    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('sys:permission:edit')")
    @BreezeSysLog(description = "数据权限信息修改", type = LogType.EDIT)
    public Result<Boolean> edit(@Validated @RequestBody SysPermission SysPermission) {
        return Result.ok(this.sysPermissionService.updateById(SysPermission));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:permission:delete')")
    @BreezeSysLog(description = "数据权限信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        List<SysRolePermission> rolePermissionList = this.sysRolePermissionService.list(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getRoleId, ids));
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            return Result.warning(Boolean.FALSE, "该数据权限已被使用");
        }
        return Result.ok(this.sysPermissionService.removeByIds(Arrays.asList(ids)));
    }

}
