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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.process.dto.ProcessInstanceSearchDTO;
import com.breeze.boot.process.dto.ProcessStartDTO;
import com.breeze.boot.process.service.IProcessInstanceService;
import com.breeze.boot.process.vo.ProcessInstanceVO;
import com.breeze.core.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程实例控制器
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/process/instance")
@Tag(name = "流程实例管理模块", description = "ProcessInstanceController")
public class ProcessInstanceController {

    /**
     * 流程资源服务
     */
    @Autowired
    private IProcessInstanceService processInstanceService;

    /**
     * 发起流程
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "发起")
    @PostMapping("/startProcess")
    @PreAuthorize("hasAnyAuthority('process:instance:start')")
    public Result<Boolean> startProcess(@RequestBody ProcessStartDTO startDTO) {
        return Result.ok(this.processInstanceService.startProcess(startDTO));
    }

    /**
     * 列表
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('process:instance:list')")
    public Result<Page<ProcessInstanceVO>> list(@RequestBody ProcessInstanceSearchDTO processInstanceSearchDTO) {
        return Result.ok(this.processInstanceService.listPage(processInstanceSearchDTO));
    }

}