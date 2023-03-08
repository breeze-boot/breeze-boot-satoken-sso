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

package com.breeze.boot.process.service.impl;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.process.dto.ProcessDeploymentDTO;
import com.breeze.boot.process.dto.ProcessSearchDeploymentDTO;
import com.breeze.boot.process.service.ActReDeploymentService;
import com.breeze.boot.process.service.IProcessDefinitionService;
import com.breeze.boot.process.vo.DeploymentVO;
import com.breeze.boot.process.vo.ProcessDefinitionVO;
import com.breeze.core.utils.Result;
import com.breeze.security.utils.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 流程资源服务impl
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@Slf4j
@Service
public class ProcessDefinitionServiceImpl implements IProcessDefinitionService {

    /**
     * 流程资源库服务
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 流程部署服务
     */
    @Autowired
    private ActReDeploymentService actReDeploymentService;

    /**
     * 部署
     *
     * @param processDeploymentDTO 流程部署dto
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deploy(ProcessDeploymentDTO processDeploymentDTO) {
        Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getId()));
        Deployment deploy = this.repositoryService.createDeployment()
                .addString(processDeploymentDTO.getName() + "-" + processDeploymentDTO.getCategory() + "-" + processDeploymentDTO.getId() + ".bpmn20.xml", processDeploymentDTO.getXml())
                .name((processDeploymentDTO.getName()))
                .key(processDeploymentDTO.getId())
                .tenantId(processDeploymentDTO.getTenantId())
                .category(processDeploymentDTO.getCategory())
                .deploy();
        List<ProcessDefinition> processDefinitionList = this.repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).list();
        return Result.ok();
    }

    /**
     * 列表页面
     *
     * @param processSearchDeploymentDTO 流程部署查询dto
     * @return {@link Page}<{@link DeploymentVO}>
     */
    @Override
    public Page<DeploymentVO> listPage(ProcessSearchDeploymentDTO processSearchDeploymentDTO) {
        return this.actReDeploymentService.listPage(processSearchDeploymentDTO);
    }

    /**
     * 挂起/激活
     *
     * @param processDefinitionId 流程定义ID
     * @return {@link Boolean}
     */
    @Override
    public Boolean isSuspended(String processDefinitionId) {
        Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getId()));
        ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .processDefinitionTenantId("1")
                .singleResult();
        log.info("状态: {}", processDefinition.isSuspended());
        if (processDefinition.isSuspended()) {
            // 被挂起 => 去激活
            this.repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        } else {
            // 激活状态 => 去挂起
            this.repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取流程定义xml
     *
     * @param processKey 流程KEY     * @param tenantId   租户ID
     * @return {@link String}
     */
    @SneakyThrows
    @Override
    public String getProcessDefinitionXml(String processKey, String tenantId) {
        ProcessDefinition processDefinition = this.getProcessDefinition(processKey, tenantId);
        try (InputStream resourceAsStream = this.repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName())) {
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("获取资源失败：" + e.getMessage());
        }
    }

    /**
     * 获取流程定义png
     *
     * @param processKey 流程KEY
     * @param tenantId   租户ID
     * @return {@link String}
     */
    @Override
    public String getProcessDefinitionPng(String processKey, String tenantId) {
        ProcessDefinition processDefinition = this.getProcessDefinition(processKey, tenantId);
        try (InputStream resourceAsStream = this.repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName())) {
            return "data:image/png;base64," + Base64.encode(resourceAsStream);
        } catch (Exception e) {
            throw new RuntimeException("获取资源失败：" + e.getMessage());
        }
    }

    /**
     * 版本列表页面
     *
     * @param processSearchDeploymentDTO 流程定义搜索DTO
     * @return {@link Page}<{@link ProcessDefinitionVO}>
     */
    @Override
    public Page<ProcessDefinitionVO> listVersionPage(ProcessSearchDeploymentDTO processSearchDeploymentDTO) {
        List<ProcessDefinition> processDefinitionList = this.repositoryService.createProcessDefinitionQuery()
                .listPage(processSearchDeploymentDTO.getOffset(), processSearchDeploymentDTO.getLimit());
        ProcessDefinitionVO processDefinitionVO = new ProcessDefinitionVO();
        Page<ProcessDefinitionVO> page = new Page<>();
        page.setRecords(processDefinitionVO.convertProcessDefinitionVO(processDefinitionList));
        return page;
    }

    /**
     * 获得版本流程定义png
     *
     * @param processDefinitionId 流程定义id
     * @param tenantId            租户ID
     * @return {@link String}
     */
    @Override
    public String getVersionProcessDefinitionPng(String processDefinitionId, String tenantId) {
        ProcessDefinition processDefinition = getDefinition(processDefinitionId, tenantId);
        try (InputStream resourceAsStream = this.repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName())) {
            return "data:image/png;base64," + Base64.encode(resourceAsStream);
        } catch (Exception e) {
            throw new RuntimeException("获取资源失败：" + e.getMessage());
        }
    }

    /**
     * 获得版本流程定义xml
     *
     * @param processDefinitionId 流程定义id
     * @param tenantId            租户ID
     * @return {@link String}
     */
    @Override
    public String getVersionProcessDefinitionXml(String processDefinitionId, String tenantId) {
        ProcessDefinition processDefinition = getDefinition(processDefinitionId, tenantId);
        try (InputStream resourceAsStream = this.repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName())) {
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("获取资源失败：" + e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param deploymentId 流程部署ID
     * @param cascade      级联
     * @return {@link Boolean}
     */
    @Override
    public Boolean delete(String deploymentId, Boolean cascade) {
        repositoryService.deleteDeployment(deploymentId, cascade);
        return Boolean.TRUE;
    }

    private ProcessDefinition getDefinition(String processDefinitionId, String tenantId) {
        return this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .processDefinitionTenantId(tenantId)
                .singleResult();
    }

    private ProcessDefinition getProcessDefinition(String processKey, String tenantId) {
        return this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .processDefinitionTenantId(tenantId)
                .latestVersion()
                .singleResult();
    }

}
