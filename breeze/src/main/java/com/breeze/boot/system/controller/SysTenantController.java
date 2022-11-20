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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysTenant;
import com.breeze.boot.system.dto.TenantSearchDTO;
import com.breeze.boot.system.service.SysTenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 系统岗位控制器
 *
 * @author gaoweixuan
 * @date 2022-11-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/tenant")
@Tag(name = "系统租户管理模块", description = "SysTenantController")
public class SysTenantController {

    /**
     * 系统租户服务
     */
    private final SysTenantService sysTenantService;

    /**
     * 列表
     *
     * @param tenantSearchDTO 租户dto
     * @return {@link Result}
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:tenant:list')")
    public Result<IPage<SysTenant>> list(@RequestBody TenantSearchDTO tenantSearchDTO) {
        return Result.ok(this.sysTenantService.listPage(tenantSearchDTO));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:tenant:info')")
    public Result<SysTenant> info(@PathVariable("id") Long id) {
        return Result.ok(this.sysTenantService.getById(id));
    }

    /**
     * 保存
     *
     * @param tenant 平台实体入参
     * @return {@link Result}
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:tenant:save')")
    @BreezeSysLog(description = "租户信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysTenant tenant) {
        return Result.ok(this.sysTenantService.save(tenant));
    }

    /**
     * 修改
     *
     * @param sysPost 平台实体
     * @return {@link Result}
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
