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

package com.breeze.boot.flow.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowable.engine.repository.ProcessDefinition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程定义资源VO
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionVO {

    /**
     * id
     */
    private String id;
    /**
     * 关键
     */
    private String key;
    /**
     * 名字
     */
    private String name;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 部署id
     */
    private String deploymentId;
    /**
     * 类别
     */
    private String category;
    /**
     * 开始形成关键
     */
    private Boolean hasStartFormKey;
    /**
     * 图资源名称
     */
    private String diagramResourceName;
    /**
     * 派生版本
     */
    private Integer derivedVersion;
    /**
     * 暂停
     */
    private Boolean suspended;
    /**
     * 承租者id
     */
    private String tenantId;
    /**
     * 版本
     */
    private Integer version;

    public List<ProcessDefinitionVO> convertProcessDefinitionVO(List<ProcessDefinition> processDefinitionList) {
        return processDefinitionList.stream().map(
                        processDefinition -> ProcessDefinitionVO.builder()
                                .id(processDefinition.getId())
                                .key(processDefinition.getKey())
                                .name(processDefinition.getName())
                                .resourceName(processDefinition.getResourceName())
                                .deploymentId(processDefinition.getDeploymentId())
                                .category(processDefinition.getCategory())
                                .hasStartFormKey(processDefinition.hasStartFormKey())
                                .diagramResourceName(processDefinition.getDiagramResourceName())
                                .derivedVersion(processDefinition.getDerivedVersion())
                                .suspended(processDefinition.isSuspended())
                                .tenantId(processDefinition.getTenantId())
                                .version(processDefinition.getVersion())
                                .build())
                .collect(Collectors.toList());
    }

}
