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
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.dto.JobDTO;
import com.breeze.boot.quartz.service.SysQuartzJobService;
import com.breeze.core.utils.Result;
import com.breeze.security.annotation.NoAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Quartz任务控制器
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@RestController
@NoAuthentication
@RequestMapping(value = "/quartz/job")
public class QuartzJobController {

    /**
     * quartz 任务服务
     */
    @Autowired
    private SysQuartzJobService sysQuartzJobService;

    /**
     * 列表页面
     *
     * @param jobDTO 任务DTO
     * @return {@link Result}<{@link Page}<{@link SysQuartzJob}>>
     */
    @PostMapping(value = "/listPage")
    public Result<Page<SysQuartzJob>> listPage(@RequestBody JobDTO jobDTO) {
        return Result.ok(this.sysQuartzJobService.listPage(jobDTO));
    }

    /**
     * 保存
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody SysQuartzJob sysQuartzJob) {
        return this.sysQuartzJobService.saveJob(sysQuartzJob);
    }

    /**
     * 修改
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(value = "/modify")
    public Result<Boolean> modify(@RequestBody SysQuartzJob sysQuartzJob) {
        return this.sysQuartzJobService.updateJobById(sysQuartzJob);
    }

    /**
     * 暂停任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(value = "/pauseJob")
    public Result<Boolean> pauseJob(@RequestParam(value = "jobId") Long jobId) {
        return this.sysQuartzJobService.pauseJob(jobId);
    }

    /**
     * 恢复任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(value = "/resumeJob")
    public Result<Boolean> resumeJob(@RequestParam(value = "jobId") Long jobId) {
        return this.sysQuartzJobService.resumeJob(jobId);
    }

    /**
     * 删除
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(value = "/delete")
    public Result<Boolean> delete(@RequestParam(value = "jobId") Long jobId) {
        return this.sysQuartzJobService.deleteJob(jobId);
    }

    /**
     * 立刻运行
     *
     * @param jobId 任务ID
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(value = "/runJobNow")
    public Result<Boolean> runJobNow(@RequestBody Long jobId) {
        return this.sysQuartzJobService.runJobNow(jobId);
    }
}
