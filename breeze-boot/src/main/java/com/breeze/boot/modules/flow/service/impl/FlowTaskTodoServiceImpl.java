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

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.modules.flow.model.query.UserTaskQuery;
import com.breeze.boot.modules.flow.model.vo.CurrentUserTaskVO;
import com.breeze.boot.modules.flow.model.vo.UserTaskVO;
import com.breeze.boot.modules.flow.service.IFlowTaskTodoService;
import com.breeze.boot.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.*;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程任务管理服务impl
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowTaskTodoServiceImpl implements IFlowTaskTodoService {

    private final RepositoryService repositoryService;

    private final HistoryService historyService;

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final IdentityService identityService;

    /**
     * 获取用户任务列表
     *
     * @param map 映射
     * @return {@link List}<{@link UserTaskVO}>
     */
    @Override
    public List<UserTaskVO> getUserTaskList(Map<String, String> map) {
        List<UserTaskVO> result = new ArrayList<>();
        // 已签收的任务
        result.addAll(this.getUserTaskList(String.valueOf(SecurityUtils.getCurrentUser().getUserId()), MapUtil.getStr(map, "title", ""), true));
        // 待签收的任务
        result.addAll(this.getUserTaskList(String.valueOf(SecurityUtils.getCurrentUser().getUserId()), MapUtil.getStr(map, "title", ""), false));
        // 按照任务时间倒序排列
        result.sort((o1, o2) -> o1.getCreatTime().compareTo(o2.getCreatTime()) * -1);
        return result;
    }

    /**
     * 查询用户待办任务
     *
     * @param userTaskQuery 用户任务查询对象
     * @return {@link List }<{@link Task }>
     */
    @Override
    public Page<CurrentUserTaskVO> listTodoTask(UserTaskQuery userTaskQuery) {
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskAssignee(SecurityUtils.getCurrentUser().getUsername())
                .processDefinitionKey(userTaskQuery.getProcessDefinitionKey())
                .active()
                .includeProcessVariables()
                .orderByTaskCreateTime().asc();
        long count = taskQuery.count();
        List<Task> taskList = taskQuery.listPage(userTaskQuery.getOffset(), userTaskQuery.getLimit());

        Page<CurrentUserTaskVO> page = new Page<>(userTaskQuery.getOffset(), userTaskQuery.getLimit());
        page.setRecords(taskList.stream().map(task -> {
            CurrentUserTaskVO currentUserTaskVO = new CurrentUserTaskVO();
            currentUserTaskVO.setId(task.getId());
            currentUserTaskVO.setTitle(task.getName());
            currentUserTaskVO.setApplyUser(task.getOwner());
            currentUserTaskVO.setApplyUserName(task.getOwner());
            currentUserTaskVO.setAssignee(task.getAssignee());
            currentUserTaskVO.setFormKey(task.getFormKey());
            currentUserTaskVO.setProcessDefinitionKey(task.getTaskDefinitionKey());
            currentUserTaskVO.setProcessInstanceId(task.getProcessInstanceId());
            currentUserTaskVO.setProcessDefinitionId(task.getProcessDefinitionId());
            currentUserTaskVO.setProcessVariables(new CurrentUserTaskVO.Variable(task.getProcessVariables()));
            currentUserTaskVO.setTenantId(task.getTenantId());
            currentUserTaskVO.setCreatTime(task.getCreateTime());
            return currentUserTaskVO;
        }).collect(Collectors.toList()));
        page.setTotal(count);
        return page;
    }

    @Override
    public Page<CurrentUserTaskVO> listCompletedTask(UserTaskQuery userTaskQuery) {
        return null;
    }

    private UserTaskVO buildUserTaskVO(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        // 查询流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (Objects.isNull(processInstance)) {
            throw new SystemServiceException(ResultCode.exception("未获取到流程实例"));
        }

        UserTaskVO userTaskVO = new UserTaskVO();
        userTaskVO.setTaskId(task.getId());
        userTaskVO.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
        userTaskVO.setProcessInstanceId(processInstance.getProcessInstanceId());
        userTaskVO.setBusinessKey(processInstance.getBusinessKey());
        userTaskVO.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        userTaskVO.setTaskName(task.getName());
        userTaskVO.setAssignee(task.getAssignee());
        userTaskVO.setAssigneeName(task.getAssignee());
        userTaskVO.setComment(task.getProcessVariables().get("comment").toString());
        userTaskVO.setVariable(new UserTaskVO.Variable(task.getProcessVariables()));
        userTaskVO.setDefinitionKey(task.getTaskDefinitionKey());
        userTaskVO.setTenantId(processInstance.getTenantId());
        userTaskVO.setFormKey(task.getFormKey());
        userTaskVO.setCreatTime(task.getCreateTime());

        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        Optional.ofNullable(processVariables.get("applyUser")).filter(v -> v instanceof String).map(Object::toString).ifPresent(userTaskVO::setApplyUser);
        Optional.ofNullable(processVariables.get("title")).filter(v -> v instanceof String).map(Object::toString).ifPresent(userTaskVO::setTitle);

        // 查找流程发起人
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceId(userTaskVO.getProcessInstanceId()).orderByTaskCreateTime().asc().list();
        if (!historicTaskInstanceList.isEmpty() && StringUtils.isBlank(historicTaskInstanceList.get(0).getAssignee())) {
            User user = identityService.createUserQuery().userId(historicTaskInstanceList.get(0).getAssignee()).singleResult();
            if (Objects.nonNull(user) && StringUtils.isNotBlank(user.getId())) {
                userTaskVO.setApplyUserName(user.getFirstName());
                userTaskVO.setApplyUser(user.getId());
            }
        }

        // 设置状态
        userTaskVO.setStatus(Objects.nonNull(task.getAssignee()) ? "todo" : "claim");
        return userTaskVO;
    }

    private List<UserTaskVO> getUserTaskList(String userId, String title, boolean isAssigned) {
        List<Task> taskList = this.buildTaskQuery(userId, isAssigned, title).orderByTaskCreateTime().desc().list();
        return taskList.stream().map(this::buildUserTaskVO).collect(Collectors.toList());
    }

    private TaskQuery buildTaskQuery(String userId, boolean isAssigned, String title) {
        TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(isAssigned ? userId : null).active().includeProcessVariables();
        if (StringUtils.isNotBlank(title)) {
            todoTaskQuery.processVariableValueLike("title", "%" + title + "%");
        }
        return todoTaskQuery;
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
