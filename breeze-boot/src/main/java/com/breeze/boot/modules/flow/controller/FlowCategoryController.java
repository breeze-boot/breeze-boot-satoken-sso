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

package com.breeze.boot.modules.flow.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.flow.model.entity.FlowCategory;
import com.breeze.boot.modules.flow.model.query.FlowCategoryQuery;
import com.breeze.boot.modules.flow.service.IFlowCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * 流程资源管理控制器
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/flow/v1/category")
@Tag(name = "流程分类管理模块", description = "FlowCategoryController")
public class FlowCategoryController {

    /**
     * 流程分类服务
     */
    private final IFlowCategoryService processCategoryService;

    /**
     * 列表
     *
     * @param processCategory 流程类别
     * @return {@link Result}<{@link IPage}<{@link FlowCategory}>>
     */
    @DS("flowable")
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('flow:category:list')")
    public Result<IPage<FlowCategory>> list(FlowCategoryQuery processCategory) {
        return Result.ok(this.processCategoryService.listPage(processCategory));
    }

    /**
     * 详情
     *
     * @param categoryId 流程分类id
     * @return {@link Result}<{@link FlowCategory}>
     */
    @DS("flowable")
    @Operation(summary = "详情")
    @GetMapping("/info/{categoryId}")
    @PreAuthorize("hasAnyAuthority('auth:dict:info')")
    public Result<FlowCategory> info(@PathVariable("categoryId") Long categoryId) {
        return Result.ok(this.processCategoryService.getById(categoryId));
    }

    /**
     * 校验流程分类编码是否重复
     *
     * @param categoryCode 流程分类编码
     * @param categoryId   流程分类ID
     * @return {@link Result}<{@link FlowCategory}>
     */
    @DS("flowable")
    @Operation(summary = "校验流程分类编码是否重复")
    @GetMapping("/checkCategoryCode")
    @PreAuthorize("hasAnyAuthority('flow:category:list')")
    public Result<Boolean> checkFlowCategoryCode(@NotBlank(message = "编码不能为空") @RequestParam("categoryCode") String categoryCode,
                                                 @NotNull(message = "ID不能为空") @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return Result.ok(Objects.isNull(this.processCategoryService.getOne(Wrappers.<FlowCategory>lambdaQuery()
                .ne(Objects.nonNull(categoryId), FlowCategory::getId, categoryId)
                .eq(FlowCategory::getCategoryCode, categoryCode))));
    }

    /**
     * 创建
     *
     * @param flowCategory 流程分类实体入参
     * @return {@link Result}<{@link Boolean}>
     */
    @DS("flowable")
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('flow:category:create')")
    @BreezeSysLog(description = "流程分类信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody FlowCategory flowCategory) {
        return Result.ok(this.processCategoryService.save(flowCategory));
    }

    /**
     * 修改
     *
     * @param flowCategory 流程分类实体
     * @return {@link Result}<{@link Boolean}>
     */
    @DS("flowable")
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('flow:category:modify')")
    @BreezeSysLog(description = "流程分类信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody FlowCategory flowCategory) {
        return Result.ok(this.processCategoryService.updateById(flowCategory));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @DS("flowable")
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('flow:category:delete')")
    @BreezeSysLog(description = "流程分类信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.processCategoryService.removeByIds(Arrays.asList(ids)));
    }

}
