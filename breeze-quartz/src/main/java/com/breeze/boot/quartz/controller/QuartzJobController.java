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

package com.breeze.boot.quartz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.query.JobQuery;
import com.breeze.boot.quartz.service.SysQuartzJobService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * Quartz任务控制器
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@RestController
@RequestMapping("/job")
public class QuartzJobController {

    /**
     * quartz 任务服务
     */
    @Autowired
    private SysQuartzJobService sysQuartzJobService;

    /**
     * 列表页面
     *
     * @param jobQuery 任务查询
     * @return {@link Result}<{@link Page}<{@link SysQuartzJob}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:job:list')")
    public Result<Page<SysQuartzJob>> listPage(@RequestBody JobQuery jobQuery) {
        return Result.ok(this.sysQuartzJobService.listPage(jobQuery));
    }

    /**
     * 保存
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('sys:job:create')")
    @BreezeSysLog(description = "保存任务", type = LogType.EDIT)
    public Result<Boolean> save(@Valid @RequestBody SysQuartzJob sysQuartzJob) {
        return this.sysQuartzJobService.saveJob(sysQuartzJob);
    }

    /**
     * 修改
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/modify")
    @PreAuthorize("hasAnyAuthority('sys:job:modify')")
    @BreezeSysLog(description = "修改任务", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody SysQuartzJob sysQuartzJob) {
        return this.sysQuartzJobService.updateJobById(sysQuartzJob);
    }

    /**
     * 开启或关闭
     *
     * @param jobId  任务id
     * @param status 状态
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "开启或关闭")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:job:modify')")
    @BreezeSysLog(description = "开启或关闭任务", type = LogType.EDIT)
    public Result<Boolean> open(@NotNull(message = "任务ID不能为空") @RequestParam("jobId") Long jobId,
                                @NotNull(message = "状态不能为空") @RequestParam("status") Integer status) {
        return Objects.equals(1, status) ? this.sysQuartzJobService.resumeJob(jobId) : this.sysQuartzJobService.pauseJob(jobId);
    }

    /**
     * 删除
     *
     * @param jobIds 任务ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:job:delete')")
    @BreezeSysLog(description = "删除任务", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] jobIds) {
        return this.sysQuartzJobService.deleteJob(Arrays.asList(jobIds));
    }

    /**
     * 立刻运行
     *
     * @param jobId 任务ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "立刻运行")
    @GetMapping("/runJobNow")
    @PreAuthorize("hasAnyAuthority('sys:job:run')")
    public Result<Boolean> runJobNow(@NotNull(message = "参数不能为空") @RequestParam Long jobId) {
        return this.sysQuartzJobService.runJobNow(jobId);
    }
}
