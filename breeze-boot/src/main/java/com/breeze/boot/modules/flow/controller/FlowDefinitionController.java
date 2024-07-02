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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.flow.model.params.FlowDefinitionDeleteParam;
import com.breeze.boot.modules.flow.model.params.FlowDesignXmlFileParam;
import com.breeze.boot.modules.flow.model.params.FlowDesignXmlStringParam;
import com.breeze.boot.modules.flow.model.query.FlowDefinitionQuery;
import com.breeze.boot.modules.flow.model.query.FlowDeploymentQuery;
import com.breeze.boot.modules.flow.model.query.FlowHistoryDefinitionQuery;
import com.breeze.boot.modules.flow.model.vo.DefinitionVO;
import com.breeze.boot.modules.flow.model.vo.FlowDefinitionVO;
import com.breeze.boot.modules.flow.service.IFlowDefinitionService;
import com.breeze.boot.xss.annotation.JumpXss;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 流程定义控制器
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/flow/v1/definition")
@Tag(name = "流程定义管理模块", description = "FlowDefinitionController")
public class FlowDefinitionController {

    private final IFlowDefinitionService flowDefinitionService;

    /**
     * 部署
     *
     * @param flowDesignXmlStringParam 流程设计参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存设计V1")
    @PostMapping("/v1/design")
    @JumpXss
    @PreAuthorize("hasAnyAuthority('flow:definition:design')")
    public Result<String> deploy(@Valid @RequestBody FlowDesignXmlStringParam flowDesignXmlStringParam) {
        return this.flowDefinitionService.deploy(flowDesignXmlStringParam);
    }

    /**
     * 部署
     *
     * @param flowDesignXmlFileParam 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "保存设计V2")
    @PostMapping("/v2/design")
    @PreAuthorize("hasAnyAuthority('flow:definition:design')")
    @JumpXss
    public Result<String> deploy(@Valid @RequestBody FlowDesignXmlFileParam flowDesignXmlFileParam) {
        return this.flowDefinitionService.deploy(flowDesignXmlFileParam);
    }

    /**
     * 列表
     *
     * @param flowDeploymentQuery 流程部署查询
     * @return {@link Result}<{@link IPage}<{@link DefinitionVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('flow:definition:list')")
    public Result<Page<DefinitionVO>> list(FlowDeploymentQuery flowDeploymentQuery) {
        return Result.ok(this.flowDefinitionService.listPage(flowDeploymentQuery));
    }

    /**
     * 详情
     *
     * @param definitionId 流程定义ID
     * @return {@link Result }<{@link DefinitionVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{definitionId}")
    @PreAuthorize("hasAnyAuthority('flow:definition:list')")
    public Result<DefinitionVO> info(@PathVariable(value = "definitionId") String definitionId) {
        return Result.ok(this.flowDefinitionService.getInfo(definitionId));
    }

    /**
     * 流程定义版本列表
     *
     * @param flowDeploymentQuery 流程部署查询
     * @return {@link Result}<{@link Page}<{@link FlowDefinitionVO}>>
     */
    @Operation(summary = "流程定义版本列表")
    @PostMapping("/listVersion")
    @PreAuthorize("hasAnyAuthority('flow:definition:list')")
    public Result<Page<FlowDefinitionVO>> listVersion(@RequestBody FlowDeploymentQuery flowDeploymentQuery) {
        return Result.ok(this.flowDefinitionService.listVersionPage(flowDeploymentQuery));
    }

    /**
     * 获取流程定义xml
     *
     * @param definitionKey 流程Key
     * @param tenantId      租户ID
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取流程定义xml")
    @GetMapping("/getProcessDefinitionXml")
    @PreAuthorize("hasAnyAuthority('flow:definition:info')")
    public Result<String> getProcessDefinitionXml(@NotBlank(message = "流程定义Key不能为空") @Schema(description = "流程定义Key") @RequestParam String definitionKey, @NotBlank(message = "租户ID不能为空") @Schema(description = "租户ID") @RequestParam String tenantId) {
        return Result.ok(this.flowDefinitionService.getProcessDefinitionXml(definitionKey, tenantId));
    }

    /**
     * 获取流程定义png
     *
     * @param flowDefinitionQuery 流程定义查询参数
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取流程定义png")
    @GetMapping("/getProcessDefinitionPng")
    @PreAuthorize("hasAnyAuthority('flow:definition:info')")
    public Result<?> getProcessDefinitionPng(@RequestParam FlowDefinitionQuery flowDefinitionQuery) {
        return this.flowDefinitionService.getProcessDefinitionPng(flowDefinitionQuery);
    }

    /**
     * 获取各个版本流程定义png
     *
     * @param flowHistoryDefinitionQuery 流程历史定义查询参数
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "获取各个版本流程定义png")
    @GetMapping("/getHistoryProcessDefinitionPng")
    @PreAuthorize("hasAnyAuthority('flow:definition:info')")
    public Result<?> getHistoryProcessDefinitionPng(@RequestParam FlowHistoryDefinitionQuery flowHistoryDefinitionQuery) {
        return this.flowDefinitionService.getHistoryProcessDefinitionPng(flowHistoryDefinitionQuery);
    }

    /**
     * 获取各个版本流程定义xml
     *
     * @param flowHistoryDefinitionQuery 流程历史定义查询参数
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取各个版本流程定义xml")
    @GetMapping("/getHistoryProcessDefinitionXml")
    @PreAuthorize("hasAnyAuthority('flow:definition:info')")
    public Result<?> getHistoryProcessDefinitionXml(@RequestParam FlowHistoryDefinitionQuery flowHistoryDefinitionQuery) {
        return this.flowDefinitionService.getHistoryProcessDefinitionXml(flowHistoryDefinitionQuery);
    }

    /**
     * 挂起/激活
     *
     * @param definitionId 流程定义ID
     * @param tenantId     租户ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "挂起/激活")
    @PutMapping("/isSuspended")
    @PreAuthorize("hasAnyAuthority('flow:definition:suspended')")
    public Result<Boolean> isSuspended(@NotBlank(message = "流程定义ID不能为空") @Schema(description = "流程定义ID") @RequestParam String definitionId, @NotBlank(message = "租户ID不能为空") @Schema(description = "租户ID") @RequestParam String tenantId) {
        return Result.ok(this.flowDefinitionService.isSuspended(definitionId, tenantId));
    }

    /**
     * 删除
     *
     * @param flowDefinitionDeleteParamList 流定义删除参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('flow:definition:delete')")
    public Result<Boolean> delete(@RequestBody List<FlowDefinitionDeleteParam> flowDefinitionDeleteParamList) {
        return Result.ok(this.flowDefinitionService.delete(flowDefinitionDeleteParamList));
    }

}
