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

package com.breeze.boot.process.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.process.dto.ProcessDeploymentDTO;
import com.breeze.boot.process.dto.ProcessDeploymentSearchDTO;
import com.breeze.boot.process.vo.DeploymentVO;
import com.breeze.boot.process.vo.ProcessDefinitionVO;
import com.breeze.core.utils.Result;

/**
 * 工作流资源管理服务服务接口
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
public interface IProcessDefinitionService {

    /**
     * 部署
     *
     * @param processDeploymentDTO 流程部署dto
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> deploy(ProcessDeploymentDTO processDeploymentDTO);

    /**
     * 列表页面
     *
     * @param processDeploymentSearchDTO 流程部署查询dto
     * @return {@link Page}<{@link DeploymentVO}>
     */
    Page<DeploymentVO> listPage(ProcessDeploymentSearchDTO processDeploymentSearchDTO);

    /**
     * 挂起/激活
     *
     * @param processDefinitionId 流程定义ID
     * @return {@link Boolean}
     */
    Boolean isSuspended(String processDefinitionId);

    /**
     * 获取流程定义xml
     *
     * @param processKey 流程KEY
     * @param tenantId   租户ID
     * @return {@link String}
     */
    String getProcessDefinitionXml(String processKey, String tenantId);


    /**
     * 获取流程定义png
     *
     * @param processKey 流程KEY
     * @param tenantId   租户ID
     * @return {@link String}
     */
    String getProcessDefinitionPng(String processKey, String tenantId);


    /**
     * 版本列表页面
     *
     * @param processDeploymentSearchDTO 流程定义搜索DTO
     * @return {@link Page}<{@link ProcessDefinitionVO}>
     */
    Page<ProcessDefinitionVO> listVersionPage(ProcessDeploymentSearchDTO processDeploymentSearchDTO);

    /**
     * 获得版本流程定义png
     *
     * @param processDefinitionId 流程定义id
     * @param tenantId            租户ID
     * @return {@link String}
     */
    String getVersionProcessDefinitionPng(String processDefinitionId, String tenantId);

    /**
     * 获得版本流程定义xml
     *
     * @param processDefinitionId 流程定义id
     * @param tenantId            租户ID
     * @return {@link String}
     */
    String getVersionProcessDefinitionXml(String processDefinitionId, String tenantId);

    /**
     * 删除
     *
     * @param deploymentId 流程部署ID
     * @param cascade      级联
     * @return {@link Boolean}
     */
    Boolean delete(String deploymentId, Boolean cascade);

}
