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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.model.entity.SysDict;
import com.breeze.boot.modules.system.model.params.DictOpenParam;
import com.breeze.boot.modules.system.model.query.DictQuery;
import com.breeze.boot.modules.system.service.SysDictItemService;
import com.breeze.boot.modules.system.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统字典控制器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/dict")
@Tag(name = "系统字典管理模块", description = "SysDictController")
public class SysDictController {

    /**
     * 系统字典服务
     */
    private final SysDictService sysDictService;

    /**
     * 系统字典项服务
     */
    private final SysDictItemService sysDictItemService;

    /**
     * 列表
     *
     * @param dictQuery 字典查询
     * @return {@link Result}<{@link Page}<{@link SysDict}>>
     */
    @Operation(summary = "列表", description = "分页")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<Page<SysDict>> list(DictQuery dictQuery) {
        return Result.ok(this.sysDictService.listPage(dictQuery));
    }

    /**
     * 详情
     *
     * @param dictId 字典id
     * @return {@link Result}<{@link SysDict}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{dictId}")
    @PreAuthorize("hasAnyAuthority('auth:dict:info')")
    public Result<SysDict> info(@PathVariable("dictId") Long dictId) {
        return Result.ok(this.sysDictService.getById(dictId));
    }

    /**
     * 校验字典编码是否重复
     *
     * @param dictCode 字典编码
     * @param dictId   字典ID
     * @return {@link Result}<{@link SysDict}>
     */
    @Operation(summary = "校验字典编码是否重复")
    @GetMapping("/checkDictCode")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<Boolean> checkDictCode(@RequestParam("dictCode") String dictCode,
                                         @RequestParam(value = "dictId", required = false) Long dictId) {
        return Result.ok(Objects.isNull(this.sysDictService.getOne(Wrappers.<SysDict>lambdaQuery()
                .ne(Objects.nonNull(dictId), SysDict::getId, dictId)
                .eq(SysDict::getDictCode, dictCode))));
    }

    /**
     * 查询字典信息
     *
     * @param dictCodes 字典编码
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link Map}<{@link String}, {@link Object}>>>>
     */
    @Operation(summary = "获取字典")
    @PostMapping("/v1/listDict")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<Map<String, List<Map<String, Object>>>> listDicts(@RequestBody List<String> dictCodes) {
        return this.sysDictItemService.listDictByCodes(dictCodes);
    }

    /**
     * 查询字典信息
     *
     * @param dictCode 字典编码
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link Map}<{@link String}, {@link Object}>>>>
     */
    @Operation(summary = "获取字典")
    @GetMapping("/v2/listDict/{dictCode}")
    public Result<List<Map<String, Object>>> listDict(@PathVariable("dictCode") String dictCode) {
        return this.sysDictItemService.listDictByCode(dictCode);
    }

    /**
     * 创建
     *
     * @param sysDict 字典保存参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys:dict:create')")
    @BreezeSysLog(description = "字典信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysDict sysDict) {
        return Result.ok(sysDictService.save(sysDict));
    }

    /**
     * 修改
     *
     * @param sysDict 字典实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('sys:dict:modify')")
    @BreezeSysLog(description = "字典信息修改", type = LogType.EDIT)
    public Result<Boolean> update(@Valid @RequestBody SysDict sysDict) {
        return Result.ok(this.sysDictService.updateById(sysDict));
    }

    /**
     * 开关
     *
     * @param dictOpenParam 字典开关参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "开关")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:dict:modify')")
    @BreezeSysLog(description = "字典信息开关", type = LogType.EDIT)
    public Result<Boolean> open(@Valid @RequestBody DictOpenParam dictOpenParam) {
        return Result.ok(this.sysDictService.open(dictOpenParam));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:dict:delete')")
    @BreezeSysLog(description = "字典信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysDictService.deleteByIds(Arrays.asList(ids));
    }

}
