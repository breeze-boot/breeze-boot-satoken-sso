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

package com.breeze.boot.modules.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.form.UserOpenForm;
import com.breeze.boot.modules.system.model.form.EmailForm;
import com.breeze.boot.modules.system.model.form.EmailOpenForm;
import com.breeze.boot.modules.system.model.query.EmailQuery;
import com.breeze.boot.modules.system.model.vo.EmailVO;
import com.breeze.boot.modules.system.service.SysEmailService;
import com.breeze.boot.security.annotation.JumpAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 系统邮箱控制器
 *
 * @author gaoweixuan
 * @since 2024-07-13 22:03:39
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/email")
@Tag(name = "系统邮箱管理模块", description = "SysEmailController")
public class SysEmailController {

    /**
     * 系统邮箱服务
     */
    private final SysEmailService sysEmailService;

    /**
     * 列表
     *
     * @param emailQuery 邮箱查询
     * @return {@link Result}<{@link Page}<{@link EmailVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:email:list')")
    public Result<Page<EmailVO>> list(EmailQuery emailQuery) {
        return Result.ok(this.sysEmailService.listPage(emailQuery));
    }

    /**
     * 详情
     *
     * @param emailId 邮箱id
     * @return {@link Result}<{@link EmailVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{emailId}")
    @PreAuthorize("hasAnyAuthority('sys:email:info')")
    public Result<EmailVO> info(@Parameter(description = "邮箱ID") @PathVariable("emailId") Long emailId) {
        return Result.ok(this.sysEmailService.getInfoById(emailId));
    }

    /**
     * 创建
     *
     * @param emailForm 邮箱表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys:email:create')")
    @BreezeSysLog(description = "邮箱信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody EmailForm emailForm) {
        return Result.ok(this.sysEmailService.saveEmail(emailForm));
    }

    /**
     * 修改
     *
     * @param emailForm 邮箱表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('sys:email:modify')")
    @BreezeSysLog(description = "邮箱信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "邮箱ID") @PathVariable Long id, @Valid @RequestBody EmailForm emailForm) {
        return Result.ok(this.sysEmailService.modifyEmail(id, emailForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:email:delete')")
    @BreezeSysLog(description = "邮箱信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "邮箱IDS") @NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysEmailService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 开启关闭锁定
     *
     * @param emailOpenForm 邮箱开关表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "邮箱锁定开关")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:email:modify')")
    @BreezeSysLog(description = "邮箱锁定开关", type = LogType.EDIT)
    public Result<Boolean> open(@Valid @RequestBody EmailOpenForm emailOpenForm) {
        return Result.ok(this.sysEmailService.open(emailOpenForm));
    }

}
