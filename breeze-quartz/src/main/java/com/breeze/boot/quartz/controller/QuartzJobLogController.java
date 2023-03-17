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
import com.breeze.boot.quartz.service.SysQuartzJobLogService;
import com.breeze.core.utils.Result;
import com.breeze.security.annotation.NoAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Quartz任务控制器
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@RestController
@NoAuthentication
@RequestMapping(value = "/quartz/log")
public class QuartzJobLogController {

    /**
     * quartz 任务服务
     */
    @Autowired
    private SysQuartzJobLogService quartzJobLogService;

    /**
     * 列表页面
     *
     * @param jobDTO 任务DTO
     * @return {@link Result}<{@link Page}<{@link SysQuartzJob}>>
     */
    @PostMapping(value = "/listPage")
    public Result<Page<SysQuartzJob>> listPage(@RequestBody JobDTO jobDTO) {
        return Result.ok(this.quartzJobLogService.listPage(jobDTO));
    }

    /**
     * 删除
     *
     * @param logIds 日志Ids
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(value = "/delete")
    public Result<Boolean> delete(@RequestBody List<Long> logIds) {
        return Result.ok(this.quartzJobLogService.deleteLogs(logIds));
    }

    /**
     * 清空
     */
    @PostMapping(value = "/clean")
    public void clean() {
        this.quartzJobLogService.clean();
    }

}
