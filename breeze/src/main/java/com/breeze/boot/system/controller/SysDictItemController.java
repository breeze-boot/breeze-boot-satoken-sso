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
import com.breeze.boot.system.domain.SysDictItem;
import com.breeze.boot.system.domain.SysFile;
import com.breeze.boot.system.dto.DictDTO;
import com.breeze.boot.system.service.SysDictItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统字典控制器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Tag(name = "字典管理模块", description = "字典管理模块")
@RestController
@RequestMapping("/sys/dictItem")
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
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result<List<SysDictItem>> list(@RequestBody DictDTO dictDTO) {
        return Result.ok(this.sysDictItemService.listDictItem(dictDTO));
    }

    /**
     * 保存
     *
     * @param dictItemEntity 字典项保存DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:dict:save')")
    public Result<Boolean> save(@RequestBody SysDictItem dictItemEntity) {
        return Result.ok(sysDictItemService.save(dictItemEntity));
    }

    /**
     * 更新
     *
     * @param dictItemEntity 字典项更新 实体
     * @return {@link Result}<{@link Boolean}>
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:dict:update')")
    public Result<Boolean> update(@RequestBody SysDictItem dictItemEntity) {
        return Result.ok(this.sysDictItemService.updateById(dictItemEntity));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:dict:delete')")
    public Result<Boolean> delete(@RequestBody Long[] ids) {
        return Result.ok(this.sysDictItemService.removeByIds(Arrays.asList(ids)));
    }

}
