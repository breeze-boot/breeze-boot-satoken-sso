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
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysDict;
import com.breeze.boot.system.dto.DictDTO;
import com.breeze.boot.system.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 系统字典控制器
 *
 * @author breeze
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
     * 列表
     *
     * @param dictDTO 字典dto
     * @return {@link Result}<{@link Page}<{@link SysDict}>>
     */
    @Operation(summary = "列表", description = "分页")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<Page<SysDict>> list(@RequestBody DictDTO dictDTO) {
        return Result.ok(this.sysDictService.listDict(dictDTO));
    }

    /**
     * 保存
     *
     * @param dictEntity 字典保存DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:dict:save')")
    public Result<Boolean> save(@Validated @RequestBody SysDict dictEntity) {
        return Result.ok(sysDictService.save(dictEntity));
    }

    /**
     * 更新
     *
     * @param dictEntity 字典更新 实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "更新")
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:dict:update')")
    public Result<Boolean> update(@RequestBody SysDict dictEntity) {
        return Result.ok(this.sysDictService.updateById(dictEntity));
    }

    /**
     * 开关
     *
     * @param dictDTO 字典dto
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "开关")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:dict:update')")
    public Result<Boolean> open(@RequestBody DictDTO dictDTO) {
        return Result.ok(this.sysDictService.open(dictDTO));
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
    public Result<Boolean> delete(@RequestBody Long[] ids) {
        return this.sysDictService.deleteByIds(Arrays.asList(ids));
    }

}
