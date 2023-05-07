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

package com.breeze.boot.flow.service.impl;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.flow.params.ProcessDeploymentParam;
import com.breeze.boot.flow.query.ProcessDeploymentQuery;
import com.breeze.boot.flow.service.ActReDeploymentService;
import com.breeze.boot.flow.service.IProcessDefinitionService;
import com.breeze.boot.flow.vo.DeploymentVO;
import com.breeze.boot.flow.vo.ProcessDefinitionVO;
import com.breeze.boot.security.utils.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 流程资源管理服务impl
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
     * @param processDeploymentParam 流程部署参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deploy(ProcessDeploymentParam processDeploymentParam) {
        Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getId()));
        String xmlName = processDeploymentParam.getName() + "-" + processDeploymentParam.getCategory() + "-" + processDeploymentParam.getId() + ".bpmn20.xml";
        this.repositoryService.createDeployment()
                .addString(xmlName, processDeploymentParam.getXml())
                .name((processDeploymentParam.getName()))
                .key(processDeploymentParam.getId())
                .tenantId(processDeploymentParam.getTenantId())
                .category(processDeploymentParam.getCategory())
                .deploy();
        return Result.ok(Boolean.TRUE, "发布成功");
    }

    /**
     * 列表页面
     *
     * @param processDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link DeploymentVO}>
     */
    @Override
    public Page<DeploymentVO> listPage(ProcessDeploymentQuery processDeploymentQuery) {
        return this.actReDeploymentService.listPage(processDeploymentQuery);
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
     * @param processKey 流程KEY
     * @param tenantId   租户ID
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
     * @param processDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link ProcessDefinitionVO}>
     */
    @Override
    public Page<ProcessDefinitionVO> listVersionPage(ProcessDeploymentQuery processDeploymentQuery) {
        List<ProcessDefinition> processDefinitionList = this.repositoryService.createProcessDefinitionQuery()
                .listPage(processDeploymentQuery.getOffset(), processDeploymentQuery.getLimit());
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

    /**
     * 获取流程定义
     *
     * @param processDefinitionId 流程定义id
     * @param tenantId            租户ID
     * @return {@link ProcessDefinition}
     */
    private ProcessDefinition getDefinition(String processDefinitionId, String tenantId) {
        return this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .processDefinitionTenantId(tenantId)
                .singleResult();
    }

    /**
     * 获取流程定义
     *
     * @param processKey 过程关键
     * @param tenantId   租户ID
     * @return {@link ProcessDefinition}
     */
    private ProcessDefinition getProcessDefinition(String processKey, String tenantId) {
        return this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .processDefinitionTenantId(tenantId)
                .latestVersion()
                .singleResult();
    }

}
