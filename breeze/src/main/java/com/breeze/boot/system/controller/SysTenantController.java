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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.system.domain.SysTenant;
import com.breeze.boot.system.query.TenantQuery;
import com.breeze.boot.system.service.SysTenantService;
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
 * 系统岗位控制器
 *
 * @author gaoweixuan
 * @date 2022-11-06
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/tenant")
@Tag(name = "系统租户管理模块", description = "SysTenantController")
public class SysTenantController {

    /**
     * 系统租户服务
     */
    private final SysTenantService sysTenantService;

    /**
     * 列表
     *
     * @param tenantQuery 租户查询
     * @return {@link Result}<{@link IPage}<{@link SysTenant}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:tenant:list')")
    public Result<IPage<SysTenant>> list(TenantQuery tenantQuery) {
        return Result.ok(this.sysTenantService.listPage(tenantQuery));
    }

    /**
     * 创建
     *
     * @param tenant 平台实体入参
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys:tenant:create')")
    @BreezeSysLog(description = "租户信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysTenant tenant) {
        return Result.ok(this.sysTenantService.save(tenant));
    }

    /**
     * 校验租户编码是否重复
     *
     * @param tenantCode 租户编码
     * @param tenantId   租户ID
     * @return {@link Result}<{@link SysTenant}>
     */
    @Operation(summary = "校验租户编码是否重复")
    @GetMapping("/checkTenantCode")
    @PreAuthorize("hasAnyAuthority('sys:tenant:list')")
    public Result<Boolean> checkTenantCode(@NotBlank(message = "租户编码不能为空") @RequestParam("tenantCode") String tenantCode,
                                           @RequestParam(value = "tenantId", required = false) Long tenantId) {
        return Result.ok(Objects.isNull(this.sysTenantService.getOne(Wrappers.<SysTenant>lambdaQuery()
                .ne(Objects.nonNull(tenantId), SysTenant::getId, tenantId)
                .eq(SysTenant::getTenantCode, tenantCode))));
    }

    /**
     * 修改
     *
     * @param sysTenant 租户实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('sys:tenant:modify')")
    @BreezeSysLog(description = "租户信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody SysTenant sysTenant) {
        return Result.ok(this.sysTenantService.updateById(sysTenant));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:tenant:delete')")
    @BreezeSysLog(description = "租户信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysTenantService.removeTenantByIds(ids);
    }

}
