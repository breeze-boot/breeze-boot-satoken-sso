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

import com.breeze.boot.core.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysDictItem;
import com.breeze.boot.system.domain.SysFile;
import com.breeze.boot.system.dto.DictDTO;
import com.breeze.boot.system.service.SysDictItemService;
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
 * @author breeze
 * @date 2022-09-02
 */
@RestController
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
     * @param dictDTO 字典dto
     * @return {@link Result}<{@link List}<{@link SysFile}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<List<SysDictItem>> list(@RequestBody DictDTO dictDTO) {
        return Result.ok(this.sysDictItemService.listDictItem(dictDTO));
    }

    /**
     * 保存
     *
     * @param sysDictItem 字典项保存DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:dict:save')")
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
    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('sys:dict:edit')")
    @BreezeSysLog(description = "字典项信息修改", type = LogType.EDIT)
    public Result<Boolean> edit(@Validated @RequestBody SysDictItem sysDictItem) {
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
