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

package com.breeze.boot.process.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.process.domain.ProcessCategory;
import com.breeze.boot.process.dto.ProcessCategoryDTO;
import com.breeze.boot.process.service.IProcessCategoryService;
import com.breeze.core.utils.Result;
import com.breeze.log.annotation.BreezeSysLog;
import com.breeze.log.config.LogType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * 流程资源管理控制器
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/category")
@Tag(name = "流程分类管理模块", description = "ProcessCategoryController")
public class ProcessCategoryController {

    /**
     * 流程分类服务
     */
    @Autowired
    private IProcessCategoryService processCategoryService;

    /**
     * 列表
     *
     * @param processCategory 流程类别
     * @return {@link Result}<{@link IPage}<{@link ProcessCategory}>>
     */
    @DS("flowable")
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('process:category:list')")
    public Result<IPage<ProcessCategory>> list(@RequestBody ProcessCategoryDTO processCategory) {
        return Result.ok(this.processCategoryService.listPage(processCategory));
    }

    /**
     * 校验流程分类编码是否重复
     *
     * @param categoryCode 流程分类编码
     * @param categoryId   流程分类ID
     * @return {@link Result}<{@link ProcessCategory}>
     */
    @DS("flowable")
    @Operation(summary = "校验流程分类编码是否重复")
    @GetMapping("/checkCategoryCode")
    @PreAuthorize("hasAnyAuthority('process:category:list')")
    public Result<Boolean> checkFlowCategoryCode(@RequestParam("categoryCode") String categoryCode,
                                                 @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return Result.ok(Objects.isNull(this.processCategoryService.getOne(Wrappers.<ProcessCategory>lambdaQuery()
                .ne(Objects.nonNull(categoryId), ProcessCategory::getId, categoryId)
                .eq(ProcessCategory::getCategoryCode, categoryCode))));
    }

    /**
     * 创建
     *
     * @param processCategory 流程分类实体入参
     * @return {@link Result}<{@link Boolean}>
     */
    @DS("flowable")
    @Operation(summary = "保存")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('process:category:create')")
    @BreezeSysLog(description = "流程分类信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody ProcessCategory processCategory) {
        return Result.ok(this.processCategoryService.save(processCategory));
    }

    /**
     * 修改
     *
     * @param processCategory 流程分类实体
     * @return {@link Result}<{@link Boolean}>
     */
    @DS("flowable")
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('process:category:modify')")
    @BreezeSysLog(description = "流程分类信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Validated @RequestBody ProcessCategory processCategory) {
        return Result.ok(this.processCategoryService.updateById(processCategory));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @DS("flowable")
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('process:category:delete')")
    @BreezeSysLog(description = "流程分类信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.processCategoryService.removeByIds(Arrays.asList(ids)));
    }

}
