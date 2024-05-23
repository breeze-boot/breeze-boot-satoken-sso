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

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.entity.SysDept;
import com.breeze.boot.modules.auth.model.query.DeptQuery;
import com.breeze.boot.modules.auth.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * 系统部门控制器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/dept")
@Tag(name = "系统部门管理模块", description = "SysDeptController")
public class SysDeptController {

    /**
     * 系统部门服务
     */
    private final SysDeptService sysDeptService;

    /**
     * 列表
     *
     * @param deptQuery 部门查询
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('auth:dept:list')")
    public Result<List<?>> list(DeptQuery deptQuery) {
        return Result.ok(this.sysDeptService.listDept(deptQuery));
    }

    /**
     * 详情
     *
     * @param deptId 部门ID
     * @return {@link Result}<{@link SysDept}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{deptId}")
    @PreAuthorize("hasAnyAuthority('auth:dept:info')")
    public Result<SysDept> info(@PathVariable("deptId") Long deptId) {
        return Result.ok(this.sysDeptService.getById(deptId));
    }

    /**
     * 校验部门编码是否重复
     *
     * @param deptCode 部门编码
     * @param deptId   部门ID
     * @return {@link Result}<{@link SysDept}>
     */
    @Operation(summary = "校验部门编码是否重复")
    @GetMapping("/checkDeptCode")
    @PreAuthorize("hasAnyAuthority('auth:dept:list')")
    public Result<Boolean> checkDeptCode(@NotBlank(message = "部门编码不能为空") @RequestParam("deptCode") String deptCode,
                                         @RequestParam(value = "deptId", required = false) Long deptId) {
        return Result.ok(Objects.isNull(this.sysDeptService.getOne(Wrappers.<SysDept>lambdaQuery()
                .ne(Objects.nonNull(deptId), SysDept::getId, deptId)
                .eq(SysDept::getDeptCode, deptCode))));
    }

    /**
     * 创建
     *
     * @param sysDept 部门实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('auth:dept:create')")
    @BreezeSysLog(description = "部门信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysDept sysDept) {
        return Result.ok(sysDeptService.save(sysDept));
    }

    /**
     * 修改
     *
     * @param sysDept 部门实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('auth:dept:modify')")
    @BreezeSysLog(description = "部门信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody SysDept sysDept) {
        return Result.ok(this.sysDeptService.updateById(sysDept));
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('auth:dept:delete')")
    @BreezeSysLog(description = "部门信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long id) {
        return this.sysDeptService.deleteById(id);
    }

}
