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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.flow.params.ProcessDeploymentParam;
import com.breeze.boot.flow.query.ProcessDeploymentQuery;
import com.breeze.boot.flow.service.IProcessDefinitionService;
import com.breeze.boot.flow.vo.DeploymentVO;
import com.breeze.boot.flow.vo.ProcessDefinitionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 流程定义控制器
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/definition")
@Tag(name = "流程定义管理模块", description = "ProcessDefinitionController")
public class ProcessDefinitionController {

    /**
     * 流程资源服务
     */
    private final IProcessDefinitionService processDefinitionService;

    /**
     * 列表
     *
     * @param processDeploymentQuery 流程部署查询
     * @return {@link Result}<{@link IPage}<{@link DeploymentVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('process:definition:list')")
    public Result<Page<DeploymentVO>> list(@RequestBody ProcessDeploymentQuery processDeploymentQuery) {
        return Result.ok(this.processDefinitionService.listPage(processDeploymentQuery));
    }

    /**
     * 流程定义版本列表
     *
     * @param processDeploymentQuery 流程部署查询
     * @return {@link Result}<{@link Page}<{@link ProcessDefinitionVO}>>
     */
    @Operation(summary = "流程定义版本列表")
    @PostMapping("/listVersion")
    @PreAuthorize("hasAnyAuthority('process:definition:list')")
    public Result<Page<ProcessDefinitionVO>> listVersion(@RequestBody ProcessDeploymentQuery processDeploymentQuery) {
        return Result.ok(this.processDefinitionService.listVersionPage(processDeploymentQuery));
    }

    /**
     * 部署
     *
     * @param processDeploymentParam 流程部署参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "部署")
    @PostMapping("/deploy")
    @PreAuthorize("hasAnyAuthority('process:definition:deploy')")
    public Result<Boolean> deploy(@Valid @RequestBody ProcessDeploymentParam processDeploymentParam) {
        return this.processDefinitionService.deploy(processDeploymentParam);
    }


    /**
     * 获取流程定义xml
     *
     * @param processKey 流程Key
     * @param tenantId   租户ID
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取流程定义xml")
    @GetMapping("/getProcessDefinitionXml")
    @PreAuthorize("hasAnyAuthority('process:definition:info')")
    public Result<String> getProcessDefinitionXml(@NotBlank(message = "流程定义Key不能为空") @RequestParam String processKey,
                                                  @NotBlank(message = "租户ID不能为空") @RequestParam String tenantId) {
        return Result.ok(this.processDefinitionService.getProcessDefinitionXml(processKey, tenantId));
    }

    /**
     * 获取流程定义png
     *
     * @param processKey 流程Key
     * @param tenantId   租户ID
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取流程定义png")
    @GetMapping("/getProcessDefinitionPng")
    @PreAuthorize("hasAnyAuthority('process:definition:info')")
    public Result<String> getProcessDefinitionPng(@NotBlank(message = "流程定义Key不能为空") @Schema(description = "流程定义KEY") @RequestParam String processKey,
                                                  @NotBlank(message = "租户ID不能为空") @RequestParam String tenantId) {
        return Result.ok(this.processDefinitionService.getProcessDefinitionPng(processKey, tenantId));
    }

    /**
     * 获取各个版本流程定义png
     *
     * @param processDefinitionId 流程定义id
     * @param tenantId            租户ID
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取各个版本流程定义png")
    @GetMapping("/getVersionProcessDefinitionPng")
    @PreAuthorize("hasAnyAuthority('process:definition:info')")
    public Result<String> getVersionProcessDefinitionPng(@NotBlank(message = "流程定义ID不能为空") @RequestParam String processDefinitionId,
                                                         @NotBlank(message = "租户ID不能为空") @RequestParam String tenantId) {
        return Result.ok(this.processDefinitionService.getVersionProcessDefinitionPng(processDefinitionId, tenantId));
    }


    /**
     * 获取各个版本流程定义xml
     *
     * @param tenantId            租户ID
     * @param processDefinitionId 进程id
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取各个版本流程定义xml")
    @GetMapping("/getVersionProcessDefinitionXml")
    @PreAuthorize("hasAnyAuthority('process:definition:info')")
    public Result<String> getVersionProcessDefinitionXml(@NotBlank(message = "流程定义ID不能为空") @RequestParam String processDefinitionId,
                                                         @NotBlank(message = "租户ID不能为空") @RequestParam String tenantId) {
        return Result.ok(this.processDefinitionService.getVersionProcessDefinitionXml(processDefinitionId, tenantId));
    }

    /**
     * 挂起/激活
     *
     * @param processDefinitionId 流程定义ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "挂起/激活")
    @PutMapping("/isSuspended")
    @PreAuthorize("hasAnyAuthority('process:definition:suspended')")
    public Result<Boolean> isSuspended(@NotBlank(message = "流程定义ID不能为空") @RequestParam String processDefinitionId) {
        return Result.ok(this.processDefinitionService.isSuspended(processDefinitionId));
    }

    /**
     * 删除
     *
     * @param deploymentId 流程部署ID
     * @param cascade      级联
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('process:definition:delete')")
    public Result<Boolean> delete(@NotBlank(message = "部署ID不能为空") @RequestParam("deploymentId") String deploymentId,
                                  @RequestParam(defaultValue = "false") Boolean cascade) {
        return Result.ok(this.processDefinitionService.delete(deploymentId, cascade));
    }

}
