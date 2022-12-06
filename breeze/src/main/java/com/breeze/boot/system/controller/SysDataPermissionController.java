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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.security.entity.DataPermissionDTO;
import com.breeze.boot.system.domain.SysDataPermission;
import com.breeze.boot.system.dto.SysDataPermissionDTO;
import com.breeze.boot.system.service.SysDataPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

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
     * 详情
     *
     * @param id id
     * @return {@link Result}<{@link SysDataPermission}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:dataPermission:info')")
    public Result<SysDataPermission> info(@PathVariable("id") Long id) {
        return Result.ok(this.sysDataPermissionService.getById(id));
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
     * @param sysDataPermission 数据权限实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('sys:dataPermission:modify')")
    @BreezeSysLog(description = "数据权限信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Validated @RequestBody SysDataPermission sysDataPermission) {
        return Result.ok(this.sysDataPermissionService.updateById(sysDataPermission));
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

}
