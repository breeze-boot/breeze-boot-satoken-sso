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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.flow.model.params.FlowApprovalParam;
import com.breeze.boot.modules.flow.model.params.FlowStartParam;
import com.breeze.boot.modules.flow.model.query.FlowInstanceQuery;
import com.breeze.boot.modules.flow.model.vo.FlowInstanceVO;
import com.breeze.boot.modules.flow.service.IFlowInstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 流程实例控制器
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/flow/v1/instance")
@Tag(name = "流程实例管理模块", description = "FlowInstanceController")
public class FlowInstanceController {

    /**
     * 流程资源服务
     */
    private final IFlowInstanceService flowInstanceService;

    /**
     * 列表
     *
     * @param flowInstanceQuery 流程实例查询
     * @return {@link Result}<{@link Page}<{@link FlowInstanceVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('flow:instance:list')")
    public Result<Page<FlowInstanceVO>> list(FlowInstanceQuery flowInstanceQuery) {
        return Result.ok(this.flowInstanceService.listPage(flowInstanceQuery));
    }

    /**
     * 发起流程
     *
     * @param startParam 流程启动参数
     * @return {@link Result }<{@link String }>
     */
    @Operation(summary = "发起")
    @PostMapping("/start")
    @PreAuthorize("hasAnyAuthority('flow:instance:start')")
    public Result<String> startProcess(@Valid @RequestBody FlowStartParam startParam) {
        return this.flowInstanceService.startProcess(startParam);
    }

    /**
     * 签收任务
     */
    @PostMapping("/claim/{taskId}/{userId}")
    @ResponseBody
    public Result<?> claim(@PathVariable String taskId, @PathVariable String userId) {
        flowInstanceService.claim(taskId, userId);
        return Result.ok();
    }

    /**
     * 审核通过
     */
    @PostMapping("/pass")
    @ResponseBody
    public Result<?> pass(@RequestBody FlowApprovalParam flowApprovalParam) {
        flowInstanceService.complete(flowApprovalParam);
        return Result.ok();
    }

    /**
     * 审核不通过
     */
    @PostMapping("/unPass")
    @ResponseBody
    public Result<?> unPass(@RequestBody FlowApprovalParam flowApprovalParam) {
        flowInstanceService.complete(flowApprovalParam);
        return Result.ok();
    }

    /**
     * 作废
     */
    @PostMapping(value = "/cancelProcess")
    @ResponseBody
    public Result<?> cancelProcess(@RequestBody FlowApprovalParam flowApprovalParam) {
        flowInstanceService.cancel(flowApprovalParam);
        return Result.ok();
    }

}
