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
import com.breeze.boot.system.domain.SysDataPermission;
import com.breeze.boot.system.domain.SysRoleDataPermission;
import com.breeze.boot.system.dto.SysDataPermissionDTO;
import com.breeze.boot.system.service.SysDataPermissionService;
import com.breeze.boot.system.service.SysRoleDataPermissionService;
import com.breeze.core.utils.Result;
import com.breeze.log.annotation.BreezeSysLog;
import com.breeze.log.config.LogType;
import com.breeze.security.entity.DataPermissionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/dataPermission")
@Tag(name = "系统数据权限管理模块", description = "SysDataPermissionController")
public class SysDataPermissionController {

    /**
     * 系统数据权限服务
     */
    @Autowired
    private SysDataPermissionService sysDataPermissionService;

    /**
     * 系统角色数据权限服务
     */
    @Autowired
    private SysRoleDataPermissionService sysRoleDataPermissionService;

    /**
     * 列表
     *
     * @param dataPermissionDTO 角色数据DTO
     * @return {@link Result}<{@link Page}<{@link SysDataPermission}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:dataPermission:list')")
    public Result<Page<SysDataPermission>> list(@RequestBody DataPermissionDTO dataPermissionDTO) {
        return Result.ok(this.sysDataPermissionService.listPage(dataPermissionDTO));
    }

    /**
     * 创建
     *
     * @param dataPermissionDTO 数据权限实体入参
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('sys:dataPermission:create')")
    @BreezeSysLog(description = "数据权限信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysDataPermissionDTO dataPermissionDTO) {
        return this.sysDataPermissionService.saveDataPermission(dataPermissionDTO);
    }

    /**
     * 修改
     *
     * @param dataPermissionDTO 数据权限DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('sys:dataPermission:modify')")
    @BreezeSysLog(description = "数据权限信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Validated @RequestBody SysDataPermissionDTO dataPermissionDTO) {
        return this.sysDataPermissionService.modifyDataPermission(dataPermissionDTO);
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:dataPermission:delete')")
    @BreezeSysLog(description = "数据权限信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysDataPermissionService.removePermissionByIds(Arrays.asList(ids));
    }

    /**
     * 编辑角色数据权限
     *
     * @param roleDataPermissionList 角色数据权限list
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "编辑角色数据权限")
    @PostMapping("/editRoleDataPermission")
    @PreAuthorize("hasAnyAuthority('sys:dataPermission:editRoleDataPermission')")
    @BreezeSysLog(description = "编辑角色数据权限", type = LogType.EDIT)
    public Result<Boolean> editRoleDataPermission(@RequestBody List<SysRoleDataPermission> roleDataPermissionList) {
        return this.sysRoleDataPermissionService.editRoleDataPermission(roleDataPermissionList);
    }

}
