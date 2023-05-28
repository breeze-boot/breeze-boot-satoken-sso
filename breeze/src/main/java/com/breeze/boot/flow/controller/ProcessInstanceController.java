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

package com.breeze.boot.flow.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.flow.params.ProcessStartParam;
import com.breeze.boot.flow.query.ProcessInstanceQuery;
import com.breeze.boot.flow.service.IProcessInstanceService;
import com.breeze.boot.flow.vo.ProcessInstanceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 流程实例控制器
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/instance")
@Tag(name = "流程实例管理模块", description = "ProcessInstanceController")
public class ProcessInstanceController {

    /**
     * 流程资源服务
     */
    private final IProcessInstanceService processInstanceService;

    /**
     * 发起流程
     *
     * @param startParam 流程启动参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "发起")
    @PostMapping("/startProcess")
    @PreAuthorize("hasAnyAuthority('process:instance:start')")
    public Result<Boolean> startProcess(@Valid @RequestBody ProcessStartParam startParam) {
        return Result.ok(this.processInstanceService.startProcess(startParam));
    }

    /**
     * 列表
     *
     * @param processInstanceQuery 流程实例查询
     * @return {@link Result}<{@link Page}<{@link ProcessInstanceVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('process:instance:list')")
    public Result<Page<ProcessInstanceVO>> list(@RequestBody ProcessInstanceQuery processInstanceQuery) {
        return Result.ok(this.processInstanceService.listPage(processInstanceQuery));
    }

}
