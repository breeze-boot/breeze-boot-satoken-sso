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

package com.breeze.boot.flow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.flow.params.ProcessDeploymentParam;
import com.breeze.boot.flow.query.ProcessDeploymentQuery;
import com.breeze.boot.flow.vo.DeploymentVO;
import com.breeze.boot.flow.vo.ProcessDefinitionVO;


/**
 * 流程资源管理服务接口
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
public interface IProcessDefinitionService {

    /**
     * 部署
     *
     * @param processDeploymentParam 流程部署参数
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> deploy(ProcessDeploymentParam processDeploymentParam);

    /**
     * 列表页面
     *
     * @param processDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link DeploymentVO}>
     */
    Page<DeploymentVO> listPage(ProcessDeploymentQuery processDeploymentQuery);

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
     * @param processDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link ProcessDefinitionVO}>
     */
    Page<ProcessDefinitionVO> listVersionPage(ProcessDeploymentQuery processDeploymentQuery);

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
