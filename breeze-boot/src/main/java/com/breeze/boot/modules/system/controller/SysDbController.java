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

package com.breeze.boot.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.model.entity.SysDb;
import com.breeze.boot.modules.system.model.query.DbQuery;
import com.breeze.boot.modules.system.service.SysDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 系统数据源管理模块
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/db")
@Tag(name = "", description = "SysDbController")
public class SysDbController {

    /**
     * 系统数据源服务
     */
    private final SysDbService sysDbService;

    /**
     * 列表
     *
     * @param dbQuery 数据源查询
     * @return {@link Result}<{@link IPage}<{@link SysDb}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:db:list')")
    public Result<IPage<SysDb>> list(DbQuery dbQuery) {
        return Result.ok(this.sysDbService.listPage(dbQuery));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}<{@link SysDb}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:db:info')")
    public Result<SysDb> info(@NotNull(message = "不能为空") @PathVariable Long id) {
        return Result.ok(this.sysDbService.getById(id));
    }

    /**
     * 创建
     *
     * @param db 数据源
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys:db:create')")
    @BreezeSysLog(description = "数据源信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysDb db) {
        return Result.ok(this.sysDbService.saveDb(db));
    }

    /**
     * 修改
     *
     * @param db 信息
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('sys:db:modify')")
    @BreezeSysLog(description = "数据源信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody SysDb db) {
        return Result.ok(this.sysDbService.updateDbById(db));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:db:delete')")
    @BreezeSysLog(description = "数据源信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysDbService.removeDbByIds(Arrays.asList(ids)));
    }

}
