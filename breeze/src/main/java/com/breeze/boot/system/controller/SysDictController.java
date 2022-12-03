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
import com.breeze.boot.system.domain.SysDict;
import com.breeze.boot.system.domain.SysDictItem;
import com.breeze.boot.system.dto.DictOpenDTO;
import com.breeze.boot.system.dto.DictSearchDTO;
import com.breeze.boot.system.service.SysDictItemService;
import com.breeze.boot.system.service.SysDictService;
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
 * 系统字典控制器
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@RestController
@RequestMapping("/sys/dict")
@Tag(name = "系统字典管理模块", description = "SysDictController")
public class SysDictController {

    /**
     * 系统字典服务
     */
    @Autowired
    private SysDictService sysDictService;

    /**
     * 系统字典项服务
     */
    @Autowired
    private SysDictItemService sysDictItemService;

    /**
     * 列表
     *
     * @param dictSearchDTO 字典搜索DTO
     * @return {@link Result}<{@link Page}<{@link SysDict}>>
     */
    @Operation(summary = "列表", description = "分页")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<Page<SysDict>> list(@RequestBody DictSearchDTO dictSearchDTO) {
        return Result.ok(this.sysDictService.listPage(dictSearchDTO));
    }

    /**
     * 加载字典信息
     *
     * @param dictCode 字典编码
     * @return {@link Result}<{@link Page}<{@link SysDict}>>
     */
    @Operation(summary = "获取字典")
    @GetMapping("/loadDict/{dictCode}")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<List<SysDictItem>> loadDict(@PathVariable("dictCode") String dictCode) {
        return this.sysDictItemService.loadDictByCode(dictCode);
    }

    /**
     * 保存
     *
     * @param sysDict 字典保存DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:dict:save')")
    @BreezeSysLog(description = "字典信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysDict sysDict) {
        return Result.ok(sysDictService.save(sysDict));
    }

    /**
     * 修改
     *
     * @param sysDict 字典实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('sys:dict:modify')")
    @BreezeSysLog(description = "字典信息修改", type = LogType.EDIT)
    public Result<Boolean> update(@Validated @RequestBody SysDict sysDict) {
        return Result.ok(this.sysDictService.updateById(sysDict));
    }

    /**
     * 开关
     *
     * @param dictOpenDTO 字典开关 DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "开关")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:dict:modify')")
    @BreezeSysLog(description = "字典信息开关", type = LogType.EDIT)
    public Result<Boolean> open(@Validated @RequestBody DictOpenDTO dictOpenDTO) {
        return Result.ok(this.sysDictService.open(dictOpenDTO));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:dict:delete')")
    @BreezeSysLog(description = "字典信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysDictService.deleteByIds(Arrays.asList(ids));
    }

}
