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

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysDictItem;
import com.breeze.boot.system.dto.DictSearchDTO;
import com.breeze.boot.system.service.SysDictItemService;
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
 * 系统字典控制器
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/dictItem")
@Tag(name = "系统字典项管理模块", description = "SysDictItemController")
public class SysDictItemController {

    /**
     * 系统字典服务
     */
    @Autowired
    private SysDictItemService sysDictItemService;

    /**
     * 列表
     *
     * @param dictSearchDTO 字典搜索DTO
     * @return {@link Result}<{@link List}<{@link SysDictItem}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<List<SysDictItem>> list(@RequestBody DictSearchDTO dictSearchDTO) {
        return Result.ok(this.sysDictItemService.listDictItem(dictSearchDTO));
    }

    /**
     * 创建
     *
     * @param sysDictItem 字典项保存DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('sys:dict:create')")
    @BreezeSysLog(description = "字典项信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysDictItem sysDictItem) {
        return Result.ok(sysDictItemService.save(sysDictItem));
    }

    /**
     * 修改
     *
     * @param sysDictItem 字典项 实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('sys:dict:modify')")
    @BreezeSysLog(description = "字典项信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Validated @RequestBody SysDictItem sysDictItem) {
        return Result.ok(this.sysDictItemService.updateById(sysDictItem));
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
    @BreezeSysLog(description = "字典项信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysDictItemService.removeByIds(Arrays.asList(ids)));
    }

}
