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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.process.dto.ProcessDeploymentDTO;
import com.breeze.boot.process.dto.ProcessSearchDeploymentDTO;
import com.breeze.boot.process.service.IProcessDefinitionService;
import com.breeze.boot.process.vo.DeploymentVO;
import com.breeze.boot.process.vo.ProcessDefinitionVO;
import com.breeze.core.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 流程定义控制器
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/definition")
@Tag(name = "流程定义管理模块", description = "ProcessDefinitionController")
public class ProcessDefinitionController {

    /**
     * 流程资源服务
     */
    @Autowired
    private IProcessDefinitionService processDefinitionService;

    /**
     * 列表
     *
     * @param processSearchDeploymentDTO 流程部署查询DTO
     * @return {@link Result}<{@link IPage}<{@link DeploymentVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('process:definition:list')")
    public Result<Page<DeploymentVO>> list(@RequestBody ProcessSearchDeploymentDTO processSearchDeploymentDTO) {
        return Result.ok(this.processDefinitionService.listPage(processSearchDeploymentDTO));
    }

    /**
     * 流程定义版本列表
     *
     * @param processSearchDeploymentDTO 流程部署查询DTO
     * @return {@link Result}<{@link Page}<{@link ProcessDefinitionVO}>>
     */
    @Operation(summary = "流程定义版本列表")
    @PostMapping("/listVersion")
    @PreAuthorize("hasAnyAuthority('process:definition:list')")
    public Result<Page<ProcessDefinitionVO>> listVersion(@RequestBody ProcessSearchDeploymentDTO processSearchDeploymentDTO) {
        return Result.ok(this.processDefinitionService.listVersionPage(processSearchDeploymentDTO));
    }

    /**
     * 部署
     *
     * @param processDeploymentDTO 流程部署dto
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "部署")
    @PostMapping("/deploy")
    @PreAuthorize("hasAnyAuthority('process:definition:deploy')")
    public Result<Boolean> deploy(@RequestBody ProcessDeploymentDTO processDeploymentDTO) {
        return this.processDefinitionService.deploy(processDeploymentDTO);
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
    public Result<String> getProcessDefinitionXml(@RequestParam String processKey,
                                                  @RequestParam String tenantId) {
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
    public Result<String> getProcessDefinitionPng(@RequestParam String processKey,
                                                  @RequestParam String tenantId) {
        return Result.ok(this.processDefinitionService.getProcessDefinitionPng(processKey, tenantId));
    }

    /**
     * 获取各个版本流程定义png
     *
     * @param tenantId            租户ID
     * @param processDefinitionId 进程id
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取各个版本流程定义png")
    @GetMapping("/getVersionProcessDefinitionPng")
    @PreAuthorize("hasAnyAuthority('process:definition:info')")
    public Result<String> getVersionProcessDefinitionPng(@RequestParam String processDefinitionId,
                                                         @RequestParam String tenantId) {
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
    public Result<String> getVersionProcessDefinitionXml(@RequestParam String processDefinitionId,
                                                         @RequestParam String tenantId) {
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
    public Result<Boolean> isSuspended(@RequestParam String processDefinitionId) {
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
    public Result<Boolean> delete(@RequestParam("deploymentId") String deploymentId, @RequestParam(defaultValue = "false") Boolean cascade) {
        return Result.ok(this.processDefinitionService.delete(deploymentId, cascade));
    }

}
