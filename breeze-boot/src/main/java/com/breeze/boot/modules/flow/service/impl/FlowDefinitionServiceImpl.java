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

package com.breeze.boot.modules.flow.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.flow.model.params.FlowDefinitionDeleteParam;
import com.breeze.boot.modules.flow.model.params.FlowDesignXmlFileParam;
import com.breeze.boot.modules.flow.model.params.FlowDesignXmlStringParam;
import com.breeze.boot.modules.flow.model.query.FlowDefinitionQuery;
import com.breeze.boot.modules.flow.model.query.FlowDeploymentQuery;
import com.breeze.boot.modules.flow.model.query.FlowHistoryDefinitionQuery;
import com.breeze.boot.modules.flow.model.vo.DefinitionVO;
import com.breeze.boot.modules.flow.model.vo.FlowDefinitionVO;
import com.breeze.boot.modules.flow.service.ActReDeploymentService;
import com.breeze.boot.modules.flow.service.IFlowDefinitionService;
import com.breeze.boot.security.utils.SecurityUtils;
import liquibase.pro.packaged.U;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 流程资源管理服务impl
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowDefinitionServiceImpl implements IFlowDefinitionService {

    private final RepositoryService repositoryService;

    private final HistoryService historyService;

    private final ProcessEngine processEngine;

    private final RuntimeService runtimeService;

    private final ActReDeploymentService actReDeploymentService;

    /**
     * 部署
     *
     * @param param 流程设计参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<String> deploy(FlowDesignXmlStringParam param) {
        Long currentUserId = SecurityUtils.getCurrentUser().getId();
        try {
            Authentication.setAuthenticatedUserId(String.valueOf(currentUserId));
            // 构造文件名
            String xmlName = buildXmlFileName(param);
            Deployment deploy = this.repositoryService.createDeployment()
                    .addString(xmlName, param.getXml())
                    .name(param.getDefinitionName())
                    .key(param.getDefinitionKey())
                    .tenantId(param.getTenantId())
                    .category(param.getCategoryCode())
                    .deploy();

            return Result.ok(deploy.getId(), "发布成功");
        } catch (Exception e) {
            log.error("流程部署失败, 用户ID: " + currentUserId, e);
            return Result.fail("发布失败：" + e.getMessage());
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    /**
     * 构造XML文件名
     *
     * @param flowDesignXmlStringParam 流程设计参数
     * @return 文件名
     */
    private String buildXmlFileName(FlowDesignXmlStringParam flowDesignXmlStringParam) {
        return flowDesignXmlStringParam.getDefinitionName() + "-" + flowDesignXmlStringParam.getCategoryCode() + "-" + flowDesignXmlStringParam.getDefinitionKey() + ".bpmn20.xml";
    }

    @Override
    public Result<String> deploy(FlowDesignXmlFileParam param) {
        try {
            Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getUsername()));
            // 生成文件名的逻辑提取到单独方法
            String xmlName = generateXmlName(param);

            Deployment deploy = this.repositoryService.createDeployment()
                    .addInputStream(xmlName, getInputStream(param))
                    .name(param.getDefinitionName())
                    .key(param.getDefinitionKey())
                    .tenantId(param.getTenantId())
                    .category(param.getCategoryCode())
                    .deploy();

            log.info("流程定义成功部署，部署ID: {}", deploy.getId());
            return Result.ok(deploy.getId());
        } catch (Exception e) {
            log.error("流程定义部署失败", e);
            return Result.fail("流程定义部署失败：" + e.getMessage());
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    private String generateXmlName(FlowDesignXmlFileParam param) {
        return param.getDefinitionName() + "-" + param.getCategoryCode() + "-" + param.getDefinitionKey() + ".bpmn20.xml";
    }

    @SneakyThrows
    private InputStream getInputStream(FlowDesignXmlFileParam flowDesignXmlFileParam) {
        // 使用try-with-resources确保资源释放，即使在异常情况下
        try (InputStream inputStream = flowDesignXmlFileParam.getXmlFile().getInputStream()) {
            return inputStream;
        }
    }

    /**
     * 列表页面
     *
     * @param flowDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link DefinitionVO}>
     */
    @Override
    public Page<DefinitionVO> listPage(FlowDeploymentQuery flowDeploymentQuery) {
        return this.actReDeploymentService.listPage(flowDeploymentQuery);
    }

    /**
     * 处理流程定义的挂起或激活状态
     *
     * @param processDefinitionId 流程定义ID
     * @param isSuspended         当前状态（true为挂起，false为激活）
     */
    private void handleProcessDefinitionStatus(String processDefinitionId, boolean isSuspended) {
        if (isSuspended) {
            // 被挂起 => 去激活
            this.repositoryService.activateProcessDefinitionById(processDefinitionId, true, new Date());
        } else {
            // 激活状态 => 去挂起
            this.repositoryService.suspendProcessDefinitionById(processDefinitionId, true, new Date());
        }
    }

    /**
     * 挂起/激活
     *
     * @param processDefinitionId 流程定义ID
     * @param tenantId            租户ID
     * @return {@link Boolean}
     */
    @Override
    public Boolean isSuspended(String processDefinitionId, String tenantId) {
        try {
            Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getId()));
            ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).processDefinitionTenantId(tenantId).singleResult();

            if (Objects.isNull(processDefinition)) {
                log.error("未找到流程定义: {}", processDefinitionId);
                throw new SystemServiceException(ResultCode.exception("未找到指定的流程定义"));
            }

            log.info("状态: {}", processDefinition.isSuspended());
            // 调用单独的方法来处理挂起和激活
            handleProcessDefinitionStatus(processDefinitionId, processDefinition.isSuspended());
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("处理流程定义状态时发生错误", e);
            return Boolean.FALSE;
        }
    }

    /**
     * 获取流程定义xml
     *
     * @param definitionKey 流程KEY
     * @param tenantId      租户ID
     * @return {@link String}
     */
    @Override
    public String getProcessDefinitionXml(String definitionKey, String tenantId) {
        ProcessDefinition processDefinition = this.getProcessDefinition(definitionKey, tenantId);
        try (InputStream resourceAsStream = this.repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName())) {
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("获取流程定义资源失败，Process Key: {}, Tenant ID: {}", definitionKey, tenantId, e);
            throw new SystemServiceException(ResultCode.exception("获取资源失败"));
        }
    }

    /**
     * 获取流程定义png
     *
     * @param flowDefinitionQuery 流程定义查询参数
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> getProcessDefinitionPng(FlowDefinitionQuery flowDefinitionQuery) {
        log.debug("获取流程图 procDefKey： {}； businessKey： {}", flowDefinitionQuery.getDefinitionKey(), flowDefinitionQuery.getBusinessKey());
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstanceByKey(flowDefinitionQuery.getDefinitionKey(), flowDefinitionQuery.getBusinessKey());
        if (Objects.isNull(historicProcessInstance)) {
            log.info("未找到历史流程实例");
            throw new SystemServiceException(ResultCode.exception("未找到历史流程实例"));
        }

        return getImage(historicProcessInstance.getProcessDefinitionId());
    }

    /**
     * 获取历史流程实例
     *
     * @param processDefinitionKey 流程定义KEY
     * @param businessKey          业务Key
     * @return {@link HistoricProcessInstance}
     */
    private HistoricProcessInstance getHistoricProcessInstanceByKey(String processDefinitionKey, String businessKey) {
        return historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().desc().singleResult();
    }

    /**
     * 获取历史流程实例
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey         业务Key
     * @return {@link HistoricProcessInstance}
     */
    private HistoricProcessInstance getHistoricProcessInstanceById(String processDefinitionId, String businessKey) {
        return historyService.createHistoricProcessInstanceQuery().processDefinitionId(processDefinitionId).processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().desc().singleResult();
    }

    /**
     * 获取流程执行实例
     *
     * @param processDefinitionId 流程定义ID
     * @return {@link String}
     */
    private List<String> getActivityIds(String processDefinitionId) {
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processDefinitionId).list();

        List<String> activityIds = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        return activityIds;
    }

    /**
     * 获取流程定义png
     *
     * @param processDefinitionId 流程定义ID
     * @param activityIds         获取流程执行实例ID
     * @return {@link String}
     */
    private String generateProcessDiagram(String processDefinitionId, List<String> activityIds) throws Exception {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, Collections.emptyList(), "宋体", "宋体", "宋体", processEngineConfiguration.getClassLoader(), 1.0, false)) {
            return "data:image/png;base64," + Base64.encode(in);
        } catch (Exception e) {
            log.error("生成流程图失败", e);
            // 重新抛出异常，以便于上层捕获并处理
            throw e;
        }
    }

    /**
     * 版本列表页面
     *
     * @param flowDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link FlowDefinitionVO}>
     */
    @Override
    public Page<FlowDefinitionVO> listVersionPage(FlowDeploymentQuery flowDeploymentQuery) {
        List<ProcessDefinition> processDefinitionList = this.repositoryService.createProcessDefinitionQuery().listPage(flowDeploymentQuery.getOffset(), flowDeploymentQuery.getLimit());
        FlowDefinitionVO flowDefinitionVO = new FlowDefinitionVO();
        Page<FlowDefinitionVO> page = new Page<>();
        page.setRecords(flowDefinitionVO.convertProcessDefinitionVO(processDefinitionList));
        return page;
    }

    /**
     * 获取流程定义png
     *
     * @param flowHistoryDefinitionQuery 流程历史定义查询参数
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> getHistoryProcessDefinitionPng(FlowHistoryDefinitionQuery flowHistoryDefinitionQuery) {
        log.debug("获取流程图 procDefKey： {}； businessKey： {}", flowHistoryDefinitionQuery.getDefinitionId(), flowHistoryDefinitionQuery.getBusinessKey());
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstanceById(flowHistoryDefinitionQuery.getDefinitionId(), flowHistoryDefinitionQuery.getBusinessKey());
        if (Objects.isNull(historicProcessInstance)) {
            log.info("未找到历史流程实例");
            throw new SystemServiceException(ResultCode.exception("未找到历史流程实例"));
        }
        return getImage(historicProcessInstance.getProcessDefinitionId());
    }

    /**
     * 获取流程定义图片
     *
     * @param procDefId 流程定义ID
     * @return {@link Result}<{@link String}>
     */
    private Result<?> getImage(String procDefId) {
        try {
            List<String> activityIds = this.getActivityIds(procDefId);

            if (activityIds.isEmpty()) {
                log.info("未找到活动ID，processDefinitionId: {}", procDefId);
                throw new SystemServiceException(ResultCode.exception("未找到活动ID"));
            }

            String diagramData = this.generateProcessDiagram(procDefId, activityIds);
            if (StrUtil.isBlank(diagramData)) {
                throw new SystemServiceException(ResultCode.exception("未找到活动ID"));
            }
            return Result.ok(diagramData);
        } catch (Exception e) {
            log.error("获取流程定义PNG异常", e);
            throw new SystemServiceException(ResultCode.exception("获取流程定义PNG异常" + e.getMessage()));
        }
    }

    /**
     * 获得版本流程定义xml
     *
     * @param flowHistoryDefinitionQuery 流程历史定义查询参数
     * @return {@link String}
     */
    @Override
    public Result<?> getHistoryProcessDefinitionXml(FlowHistoryDefinitionQuery flowHistoryDefinitionQuery) {
        ProcessDefinition processDefinition = getDefinition(flowHistoryDefinitionQuery.getDefinitionId(), flowHistoryDefinitionQuery.getTenantId());
        try (InputStream resourceAsStream = this.repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName())) {
            return Result.ok(IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("获取资源失败：" + e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param flowDefinitionDeleteParamList 流定义删除参数列表
     * @return {@link Boolean}
     */
    @Override
    public Boolean delete(List<FlowDefinitionDeleteParam> flowDefinitionDeleteParamList) {
        for (FlowDefinitionDeleteParam flowDefinitionDeleteParam : flowDefinitionDeleteParamList) {
            this.repositoryService.deleteDeployment(flowDefinitionDeleteParam.getDeploymentId(), flowDefinitionDeleteParam.getCascade());
        }
        return Boolean.TRUE;
    }

    @Override
    @DS("flowable")
    public DefinitionVO getInfo(String definitionId) {
        DefinitionVO info = this.actReDeploymentService.getInfo(definitionId);
        info.setXml(this.getProcessDefinitionXml(info.getDefinitionKey(), info.getTenantId()));
        return info;
    }

    /**
     * 获取流程定义
     *
     * @param definitionId 流程定义id
     * @param tenantId     租户ID
     * @return {@link ProcessDefinition}
     */
    private ProcessDefinition getDefinition(String definitionId, String tenantId) {
        return this.repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).processDefinitionTenantId(tenantId).singleResult();
    }

    /**
     * 获取流程定义
     *
     * @param processKey 过程关键
     * @param tenantId   租户ID
     * @return {@link ProcessDefinition}
     */
    private ProcessDefinition getProcessDefinition(String processKey, String tenantId) {
        return this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).processDefinitionTenantId(tenantId).latestVersion().singleResult();
    }

}
