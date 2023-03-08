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
import com.breeze.boot.system.domain.SysTenant;
import com.breeze.boot.system.dto.TenantSearchDTO;
import com.breeze.boot.system.service.SysTenantService;
import com.breeze.core.utils.Result;
import com.breeze.log.annotation.BreezeSysLog;
import com.breeze.log.config.LogType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/tenant")
@Tag(name = "系统租户管理模块", description = "SysTenantController")
public class SysTenantController {

    /**
     * 系统租户服务
     */
    @Autowired
    private SysTenantService sysTenantService;

    /**
     * 列表
     *
     * @param tenantSearchDTO 租户搜索dto
     * @return {@link Result}<{@link IPage}<{@link SysTenant}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:tenant:list')")
    public Result<IPage<SysTenant>> list(@RequestBody TenantSearchDTO tenantSearchDTO) {
        return Result.ok(this.sysTenantService.listPage(tenantSearchDTO));
    }

    /**
     * 创建
     *
     * @param tenant 平台实体入参
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('sys:tenant:create')")
    @BreezeSysLog(description = "租户信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysTenant tenant) {
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
    public Result<Boolean> checkTenantCode(@RequestParam("tenantCode") String tenantCode,
                                           @RequestParam(value = "tenantId", required = false) Long tenantId) {
        return Result.ok(Objects.isNull(this.sysTenantService.getOne(Wrappers.<SysTenant>lambdaQuery()
                .ne(Objects.nonNull(tenantId), SysTenant::getId, tenantId)
                .eq(SysTenant::getTenantCode, tenantCode))));
    }

    /**
     * 修改
     *
     * @param sysPost 平台实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('sys:tenant:modify')")
    @BreezeSysLog(description = "租户信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Validated @RequestBody SysTenant sysPost) {
        return Result.ok(this.sysTenantService.updateById(sysPost));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:tenant:delete')")
    @BreezeSysLog(description = "租户信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysTenantService.removeTenantByIds(ids);
    }

}
