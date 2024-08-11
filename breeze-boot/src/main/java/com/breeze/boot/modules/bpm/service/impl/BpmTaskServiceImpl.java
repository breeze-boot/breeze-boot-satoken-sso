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

package com.breeze.boot.modules.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.modules.bpm.model.form.BpmApprovalForm;
import com.breeze.boot.modules.bpm.model.query.UserTaskQuery;
import com.breeze.boot.modules.bpm.model.vo.*;
import com.breeze.boot.modules.bpm.service.IBpmTaskService;
import com.breeze.boot.security.utils.SecurityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FormProperty;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.flowable.identitylink.api.IdentityLinkType.ASSIGNEE;
import static org.flowable.identitylink.api.IdentityLinkType.CANDIDATE;

/**
 * 流程任务管理服务impl
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmTaskServiceImpl implements IBpmTaskService {

    private final RepositoryService repositoryService;

    private final HistoryService historyService;

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final IdentityService identityService;

    private static BpmInfoVO defaultStartButton() {
        BpmInfoVO bpmInfoVO = new BpmInfoVO();
        List<TaskButtonVO> buttonList = new ArrayList<>();
        // @formatter:off
        buttonList.add(TaskButtonVO.builder()
                .name("发起")
                .event("start")
                .key("start")
                .procInstId(null)
                .taskId(null)
                .username(SecurityUtils.getUsername())
                .build());
        // @formatter:on
        bpmInfoVO.setButtons(buttonList);
        return bpmInfoVO;
    }

    private static TaskButtonVO defaultClaimButton(String procInstId, Task task) {
        // @formatter:off
        return (TaskButtonVO.builder()
                .name("签收")
                .event("claim")
                .key("claim")
                .procInstId(procInstId)
                .taskId(task.getId())
                .username( SecurityUtils.getUsername())
                .build());
        // @formatter:on
    }

    private static TaskButtonVO defaultUnClaimButton(String procInstId, Task task) {
        // @formatter:off
        return (TaskButtonVO.builder()
                .name("反签收")
                .event("unClaim")
                .key("unClaim")
                .procInstId(procInstId)
                .taskId(task.getId())
                .username( SecurityUtils.getUsername())
                .build());
        // @formatter:on
    }

    private static TaskButtonVO defaultAgreeButton(String procInstId, Task task) {
        // @formatter:off
        return TaskButtonVO.builder()
                .name("通过")
                .event("agree")
                .key("agree")
                .procInstId(procInstId)
                .taskId(task.getId())
                .username( SecurityUtils.getUsername())
                .build();
        // @formatter:on
    }

    private static TaskButtonVO defaultRejectButton(String procInstId, Task task) {
        // @formatter:off
        return TaskButtonVO.builder()
                .name("驳回")
                .event("reject")
                .key("reject")
                .procInstId(procInstId)
                .taskId(task.getId())
                .username( SecurityUtils.getUsername())
                .build();
        // @formatter:on
    }

    /**
     * 获取用户任务列表
     *
     * @param userTaskQuery 用户任务查询
     * @return {@link List}<{@link UserTaskVO}>
     */
    @Override
    public List<UserTaskVO> listUserTodoTask(UserTaskQuery userTaskQuery) {
        List<UserTaskVO> result = new ArrayList<>();
        // 已签收的任务
        result.addAll(this.getUserTaskList(String.valueOf(SecurityUtils.getUsername()), userTaskQuery.getTaskTitle(), true));
        // 待签收的任务
        result.addAll(this.getUserTaskList(String.valueOf(SecurityUtils.getUsername()), userTaskQuery.getTaskTitle(), false));
        // 按照任务时间倒序排列
        // @formatter:off
        return result.stream()
                .sorted((o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()) * -1)
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(UserTaskVO::getTaskId))), ArrayList::new));
        // @formatter:on
    }

    /**
     * 获取任务详情
     *
     * @param taskId 任务id
     * @return {@link UserTaskVO }
     */
    @Override
    public UserTaskVO getTaskInfo(String taskId) {
        // @formatter:off
        TaskQuery taskQuery = this.taskService.createTaskQuery();
        TaskQuery todoTaskQuery = taskQuery.taskId(taskId)
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .includeIdentityLinks()
                .active();
        return this.buildUserTaskVO(todoTaskQuery.singleResult());
        // @formatter:on
    }

    /**
     * 查询用户已办任务
     *
     * @param userTaskQuery 用户任务查询
     * @return {@link Page }<{@link UserTaskVO }>
     */
    @Override
    public Page<UserTaskVO> listCompletedTask(UserTaskQuery userTaskQuery) {
        // 查询历史任务实例
        // @formatter:off
        List<HistoricTaskInstance> hisTaskInsList = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(SecurityUtils.getUsername())
                .orderByTaskCreateTime().asc()
                .includeIdentityLinks()
                .includeProcessVariables()
                .finished()
                .listPage(userTaskQuery.getOffset(), userTaskQuery.getSize());

        long count = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(SecurityUtils.getUsername())
                .orderByTaskCreateTime().asc()
                .finished()
                .count();
        // @formatter:on

        List<UserTaskVO> userTaskList = new ArrayList<>();
        hisTaskInsList.forEach(hisTask -> {
            // 查询任务对应的历史流程实例列表
            // @formatter:off
            HistoricProcessInstance hisProcInst = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(hisTask.getProcessInstanceId())
                    .singleResult();
            if (Objects.isNull(hisProcInst)) {
                throw new BreezeBizException(ResultCode.PROCESS_NOT_FOUND);
            }
            userTaskList.add(this.buildTaskList(hisProcInst, hisTask));
            // @formatter:on
        });

        Page<UserTaskVO> resultPage = new Page<>(userTaskQuery.getCurrent(), userTaskQuery.getSize(), count);
        resultPage.setRecords(userTaskList);
        return resultPage;
    }

    public UserTaskVO buildTaskList(HistoricProcessInstance hisInst, HistoricTaskInstance hisTask) {
        // @formatter:off
        List<String> userList = Lists.newArrayList();
        hisTask.getIdentityLinks().forEach(identityLink -> {
            if (StrUtil.isNotBlank(identityLink.getGroupId()) && StrUtil.equals(CANDIDATE, identityLink.getType())) {
                for (User user : identityService.createUserQuery().memberOfGroup(identityLink.getGroupId()).list()) {
                    userList.add(user.getId());
                }
            } else {
                User user = this.identityService.createUserQuery().userId(identityLink.getUserId()).singleResult();
                if (Objects.nonNull(user)) {
                    userList.add(user.getId());
                }
            }
        });
        userList.add(hisTask.getAssignee());
        UserTaskVO userTaskVO = UserTaskVO.builder()
                .taskId(hisTask.getId())
                .taskName(hisTask.getName())
                .taskDefKey(hisTask.getTaskDefinitionKey())
                .procDefKey(hisInst.getProcessDefinitionKey())
                .formKey(hisTask.getFormKey())
                .owner(hisTask.getOwner())
                .businessKey(hisInst.getBusinessKey())
                .procDefKey(hisInst.getProcessDefinitionKey())
                .procInstId(hisInst.getSuperProcessInstanceId())
                .procDefId(hisInst.getProcessDefinitionId())
                .userList(userList)
                .assignee(hisTask.getAssignee())
                .assigneeName(hisTask.getAssignee())
                .applyUser(hisInst.getStartUserId())
                .applyUserName(hisInst.getStartUserId())
                .comment(MapUtil.getStr(hisTask.getProcessVariables(), "comment", ""))
                .variable(new UserTaskVO.Variable(hisTask.getProcessVariables()))
                .tenantId(hisInst.getTenantId())
                .createTime(hisTask.getCreateTime())
                .build();
        // @formatter:on

        this.setTaskTitle(hisTask.getProcessVariables(), userTaskVO);

        // 查找流程发起人
        this.setStarUser(userTaskVO, hisInst.getBusinessKey());

        // 设置状态
        if (Objects.isNull(hisTask.getEndTime())) {
            userTaskVO.setStatus("todo");
        } else {
            userTaskVO.setStatus("finish");
            userTaskVO.setEndTime(hisTask.getEndTime());
            // 当前节点拒绝原因为空，则查询审批意见
            if (StrUtil.isEmpty(hisTask.getDeleteReason())) {
                List<Comment> taskComments = taskService.getTaskComments(hisTask.getId());
                taskComments.forEach(comment -> {
                    // 同意
                    userTaskVO.setComment("审核信息:" + comment.getFullMessage());
                });
            } else {
                // 驳回
                userTaskVO.setAssignee("驳回节点:" + hisTask.getAssignee());
                userTaskVO.setComment("驳回原因:" + hisTask.getDeleteReason());
            }
        }
        return userTaskVO;
    }

    /**
     * 获取审批信息列表
     *
     * @param procDefKey  流程定义KEY
     * @param businessKey 业务Key
     * @return {@link List }<{@link TaskApproveInfoVO }>
     */
    @Override
    public List<TaskApproveInfoVO> listFlowApproveInfo(String procDefKey, String businessKey) {
        List<HistoricActivityInstance> historicActivityInstanceList = this.getHistoricActivityInstanceList(procDefKey, businessKey);
        List<TaskApproveInfoVO> resultList = new ArrayList<>();
        for (HistoricActivityInstance instance : historicActivityInstanceList) {
            if (!this.isRelevantActivity(instance)) {
                continue;
            }
            TaskApproveInfoVO taskApproveInfo = this.buildTaskApproveVO(instance);
            this.buildTaskApproveInfoVO(instance, taskApproveInfo);
            resultList.add(taskApproveInfo);
        }
        return resultList;
    }

    /**
     * 是相关活动
     *
     * @param historicActivityInstance 历史活动实例
     * @return boolean
     */
    private boolean isRelevantActivity(HistoricActivityInstance historicActivityInstance) {
        String activityType = historicActivityInstance.getActivityType();
        return "userTask".equals(activityType) || "startEvent".equals(activityType) || "endEvent".equals(activityType);
    }

    /**
     * 获取历史活动实例列表
     *
     * @param procDefKey  流程定义KEY
     * @param businessKey 业务KEY
     * @return {@link List }<{@link HistoricActivityInstance }>
     */
    private List<HistoricActivityInstance> getHistoricActivityInstanceList(String procDefKey, String businessKey) {
        // @formatter:off
        List<HistoricProcessInstance> historicProcessInstanceList = this.historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(procDefKey)
                .processInstanceBusinessKey(businessKey)
                .orderByProcessInstanceStartTime()
                .desc()
                .list();
        // @formatter:on
        if (CollUtil.isEmpty(historicProcessInstanceList)) {
            return Lists.newArrayList();
        }

        HistoricProcessInstance historicProcessInstance = historicProcessInstanceList.get(0);
        if (Objects.isNull(historicProcessInstance)) {
            return Lists.newArrayList();
        }

        // @formatter:off
        return this.historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(historicProcessInstance.getId())
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        // @formatter:off
    }

    /**
     * 确定任务名称
     *
     * @param activityType 活动类型
     * @return {@link String }
     */private String determineTaskName(String activityType) {
        switch (activityType) {
            case "startEvent":
                return "开始";
            case "endEvent":
                return "结束";
            default:
                return activityType;
        }
    }

    /**
     * 构建任务对象
     *
     * @param historicActivityInstance  历史活动实例
     * @return {@link TaskApproveInfoVO }
     */
    private TaskApproveInfoVO buildTaskApproveVO(HistoricActivityInstance historicActivityInstance) {
        return TaskApproveInfoVO.builder()
                .procInstId(historicActivityInstance.getProcessInstanceId())
                .taskId(historicActivityInstance.getTaskId())
                .startTime(historicActivityInstance.getStartTime())
                .endTime(historicActivityInstance.getEndTime())
                .activityId(historicActivityInstance.getActivityId())
                .taskName(this.determineTaskName(historicActivityInstance.getActivityType()))
                .build();
    }

    /**
     * 丰富任务数据
     *
     * @param historicActivityInstance 历史活动实例
     * @param taskApproveInfo          任务审批信息
     */
    private void buildTaskApproveInfoVO(HistoricActivityInstance historicActivityInstance,  TaskApproveInfoVO taskApproveInfo) {
        if ("startEvent".equals(historicActivityInstance.getActivityType())) {
            this.setStarterInfo(historicActivityInstance, taskApproveInfo);
        }
        this.setAssigneeInfo(historicActivityInstance, taskApproveInfo);
        this.setCommentInfo(historicActivityInstance, taskApproveInfo);
        this.setTaskApproveInfo(historicActivityInstance,  taskApproveInfo);
        taskApproveInfo.setTaskId(historicActivityInstance.getTaskId());
        taskApproveInfo.setProcInstId(historicActivityInstance.getProcessInstanceId());
    }

    /**
     * 设置启动器信息
     *
     * @param historicActivityInstance  历史活动实例
     * @param taskApproveInfo           任务审批信息
     */
    private void setStarterInfo(HistoricActivityInstance historicActivityInstance, TaskApproveInfoVO taskApproveInfo) {
        // @formatter:off
        List<HistoricProcessInstance> historicProcessInstanceList = this.historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(historicActivityInstance.getProcessInstanceId())
                .orderByProcessInstanceStartTime()
                .asc()
                .list();
        // @formatter:on
        if (historicProcessInstanceList.isEmpty() || historicProcessInstanceList.get(0).getStartUserId() == null) {
            return;
        }
        User user = identityService.createUserQuery().userId(historicProcessInstanceList.get(0).getStartUserId()).singleResult();
        if (Objects.isNull(user)) {
            return;
        }
        taskApproveInfo.setAssignee(historicActivityInstance.getAssignee());
        taskApproveInfo.setAssigneeName(user.getFirstName());
    }

    /**
     * 设置审批人信息
     *
     * @param historicActivityInstance 历史活动实例
     * @param taskApproveInfo          任务审批信息
     */
    private void setAssigneeInfo(HistoricActivityInstance historicActivityInstance, TaskApproveInfoVO taskApproveInfo) {
        if (Objects.isNull(historicActivityInstance.getAssignee())) {
            return;
        }
        User user = this.identityService.createUserQuery().userId(historicActivityInstance.getAssignee()).singleResult();
        if (Objects.isNull(user)) {
            return;
        }
        taskApproveInfo.setAssignee(historicActivityInstance.getAssignee());
        taskApproveInfo.setAssigneeName(user.getFirstName());
    }

    /**
     * 设置评论信息
     *
     * @param historicActivityInstance 历史活动实例
     * @param taskApproveInfo          任务审批信息
     */
    private void setCommentInfo(HistoricActivityInstance historicActivityInstance, TaskApproveInfoVO taskApproveInfo) {
        if (Objects.isNull(historicActivityInstance.getTaskId())) {
            return;
        }
        List<Comment> commentList = this.taskService.getTaskComments(historicActivityInstance.getTaskId());
        if (CollUtil.isEmpty(commentList)) {
            return;
        }
        // @formatter:off
        taskApproveInfo.setComments(commentList.stream()
                .sorted(Comparator.comparing(Comment::getTime))
                .map(item -> CommentInfo.builder()
                        .message(item.getFullMessage())
                        .time(item.getTime())
                        .userId(item.getUserId())
                        .username(item.getUserId())
                        .build())
                .collect(Collectors.toList()));
        // @formatter:on
    }

    /**
     * 设置任务审批信息
     *
     * @param activityInstance 历史活动实例
     * @param taskApproveInfo  任务审批信息
     */
    private void setTaskApproveInfo(HistoricActivityInstance activityInstance, TaskApproveInfoVO taskApproveInfo) {
        if (Objects.isNull(activityInstance.getTaskId()) || Objects.isNull(activityInstance.getAssignee())) {
            return;
        }

        List<HistoricIdentityLink> identityLinkList = this.historyService.getHistoricIdentityLinksForTask(activityInstance.getTaskId());
        StringBuilder candidateName = new StringBuilder();
        StringBuilder candidate = new StringBuilder();

        StringBuilder assigneeName = new StringBuilder();
        StringBuilder assignee = new StringBuilder();

        StringBuilder groupName = new StringBuilder();

        for (HistoricIdentityLink identityLink : identityLinkList) {
            if (StrUtil.isNotBlank(identityLink.getGroupId()) && StrUtil.equals(CANDIDATE, identityLink.getType())) {
                Group group = this.identityService.createGroupQuery().groupId(identityLink.getGroupId()).singleResult();
                groupName.append(group.getName()).append(" ");
            } else if (StrUtil.equals(CANDIDATE, identityLink.getType())) {
                User user = this.identityService.createUserQuery().userId(identityLink.getUserId()).singleResult();
                if (Objects.nonNull(user)) {
                    this.appendUserInfo(user, candidateName, candidate);
                }
            } else if (StrUtil.equals(ASSIGNEE, identityLink.getType())) {
                User user = this.identityService.createUserQuery().userId(identityLink.getUserId()).singleResult();
                if (Objects.nonNull(user)) {
                    this.appendUserInfo(user, assigneeName, assignee);
                }
            }
        }
        taskApproveInfo.setTaskAssignee(assignee.toString().replaceFirst("->", " "));
        taskApproveInfo.setTaskAssigneeName(assigneeName.toString().replaceFirst("->", " "));

        taskApproveInfo.setTaskCandidate(candidate.toString());
        taskApproveInfo.setTaskCandidateName(candidateName.toString());

        taskApproveInfo.setTaskGroupName(groupName.toString());
    }

    /**
     * 附加用户信息
     *
     * @param user          用户
     * @param assignees     人员
     * @param assigneeNames 人员姓名
     */
    private void appendUserInfo(User user, StringBuilder assignees, StringBuilder assigneeNames) {
        assignees.append("->").append(user.getId());
        assigneeNames.append("->").append(user.getFirstName());
    }

    /**
     * 获取用户任务列表
     *
     * @param username   用户名
     * @param taskTitle  任务标题
     * @param isAssigned 是否分配
     * @return {@link List }<{@link UserTaskVO }>
     */
    private List<UserTaskVO> getUserTaskList(String username, String taskTitle, boolean isAssigned) {
        TaskQuery taskQuery = this.taskService.createTaskQuery();
        TaskQuery todoTaskQuery;
        if (isAssigned) {
            todoTaskQuery = SecurityUtils.isAdmin() ? taskQuery.active().includeProcessVariables() : taskQuery.taskAssignee(username).active().includeProcessVariables();
        } else {
            todoTaskQuery = SecurityUtils.isAdmin() ? taskQuery.active().includeProcessVariables() : taskQuery.taskCandidateUser(username).active().includeProcessVariables();
        }
        if (StringUtils.isNotBlank(taskTitle)) {
            todoTaskQuery.processVariableValueLike("taskTitle", "%" + taskTitle + "%");
        }
        // @formatter:off
        List<Task> taskList = todoTaskQuery
                .orderByTaskCreateTime().desc()
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .includeIdentityLinks()
                .list();
        return taskList.stream().map(this::buildUserTaskVO).collect(Collectors.toList());
        // @formatter:on
    }

    private TaskButtonVO addButtonsForTask(FormProperty prop, Task task, String username) {
        // @formatter:off
        return TaskButtonVO.builder()
                .event(prop.getId())
                .key(prop.getId())
                .name(prop.getName())
                .procInstId(task.getProcessInstanceId())
                .taskId(task.getId())
                .username(username)
                .build();
        // @formatter:on
    }

    /**
     * 查找和添加任务按钮
     *
     * @param buttonList   按钮列表
     * @param userTaskList 用户任务
     * @param task         任务
     * @param username     用户名
     * @return boolean 返回是否获取成功
     */
    private boolean findAndAddButtonsForTask(List<TaskButtonVO> buttonList, Collection<UserTask> userTaskList, Task task, String username) {
        boolean found = false;
        for (UserTask userTask : userTaskList) {
            if (!Objects.equals(userTask.getId(), task.getTaskDefinitionKey())) {
                continue;
            }
            for (FormProperty prop : userTask.getFormProperties()) {
                buttonList.add(this.addButtonsForTask(prop, task, username));
            }
            found = true;
            break;
        }
        return found;
    }

    /**
     * 获取流程任务按钮
     *
     * @param task     任务
     * @param username 用户名
     * @return {@link List }<{@link TaskButtonVO }>
     */
    private List<TaskButtonVO> getFlowTaskButton(Task task, String username) {
        List<TaskButtonVO> buttonList = Lists.newArrayList();
        BpmnModel bpmnModel;
        try {
            bpmnModel = this.repositoryService.getBpmnModel(task.getProcessDefinitionId());
        } catch (NullPointerException e) {
            log.error("Failed to retrieve BPMN model: {}", e.getMessage());
            return Collections.emptyList();
        }

        Process process = bpmnModel != null ? bpmnModel.getProcesses().get(0) : null;
        Collection<UserTask> flowElements = process != null ? process.findFlowElementsOfType(UserTask.class) : Collections.emptyList();

        if (!this.findAndAddButtonsForTask(buttonList, flowElements, task, username)) {
            log.warn("No matching UserTask found for task definition key: {}", task.getTaskDefinitionKey());
        }

        return buttonList;
    }

    /**
     * 流程按钮信息
     *
     * @param procDefKey  流程定义KEY
     * @param businessKey 业务KEY
     * @param procInstId  流程实例ID
     * @return {@link BpmInfoVO }
     */
    @Override
    public BpmInfoVO getFlowButtonInfo(String procDefKey, String businessKey, String procInstId) {
        if (StrUtil.isBlank(procInstId)) {
            // 未发起流程时 展示发起按钮
            return defaultStartButton();
        }

        BpmInfoVO bpmInfoVO = new BpmInfoVO();
        // @formatter:off
        HistoricProcessInstance historicProcessInstance = this.historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(procDefKey)
                .processInstanceBusinessKey(businessKey)
                .processInstanceId(procInstId)
                .singleResult();
        // @formatter:on
        if (Objects.isNull(historicProcessInstance)) {
            throw new BreezeBizException(ResultCode.PROCESS_NOT_FOUND);
        }

        // @formatter:off
        TaskQuery taskAssigneeQuery = this.taskService.createTaskQuery()
                .processDefinitionKey(procDefKey)
                .processInstanceBusinessKey(businessKey)
                .processInstanceId(procInstId);
        TaskQuery taskCandidateQuerySupplier = this.taskService.createTaskQuery()
                .processDefinitionKey(procDefKey)
                .processInstanceBusinessKey(businessKey)
                .processInstanceId(procInstId);
        // @formatter:on
        bpmInfoVO.setButtons(this.findTasksButtons(procInstId, () -> taskAssigneeQuery, () -> taskCandidateQuerySupplier));
        bpmInfoVO.setStartUser(historicProcessInstance.getStartUserId());
        return bpmInfoVO;
    }

    /**
     * 审批任务
     *
     * @param bpmApprovalForm 流程审批参数
     */
    @Override
    public Boolean complete(BpmApprovalForm bpmApprovalForm) {
        String username = SecurityUtils.getUsername();
        String comment = StrUtil.isNotBlank(bpmApprovalForm.getComment()) ? bpmApprovalForm.getComment() : "";
        //设置审批意见
        if (StrUtil.isNotEmpty(comment)) {
            taskService.addComment(bpmApprovalForm.getTaskId(), bpmApprovalForm.getProcInstId(), username + comment);
        }
        Map<String, Object> variables = bpmApprovalForm.getVariables();
        if (CollUtil.isEmpty(variables)) {
            variables = Maps.newHashMap();
        }
        variables.put("pass", bpmApprovalForm.getPass());
        variables.put("taskTitle", bpmApprovalForm.getPass() ? "同意" : "拒绝");
        this.taskService.complete(bpmApprovalForm.getTaskId(), variables);
        return Boolean.TRUE;
    }

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @return {@link Boolean }
     */
    @Override
    public Boolean claim(String taskId) {
        String username = SecurityUtils.getUsername();
        Task task = getTask(taskId);
        taskService.addComment(taskId, task.getProcessInstanceId(), username + "签收任务");
        this.taskService.claim(taskId, username);
        return Boolean.TRUE;
    }

    /**
     * 反签收任务
     *
     * @param taskId 任务ID
     * @return {@link Boolean }
     */
    @Override
    public Boolean unClaim(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (Objects.isNull(task)) {
            throw new BreezeBizException(ResultCode.TASK_NOT_FOUND);
        }
        // 添加审批意见
        taskService.addComment(taskId, task.getProcessInstanceId(), SecurityUtils.getUsername() + "释放签收任务");

        List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskId);
        boolean canClaim = identityLinks.stream().anyMatch(link -> CANDIDATE.equals(link.getType()));

        // 签收
        if (canClaim) {
            taskService.claim(taskId, null);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 转签  任务转办给他人
     *
     * @param taskId   任务ID
     * @param username 加签人员
     * @return {@link Boolean }
     */
    @Override
    public Boolean transferTask(String taskId, String username) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (Objects.nonNull(task)) {
            //直接setAssignee即可
            taskService.setAssignee(taskId, username);
            taskService.addComment(taskId, task.getProcessInstanceId(), SecurityUtils.getUsername() + "转办" + username);
        }
        return Boolean.TRUE;
    }

    /**
     * 完成加签任务
     * <p>
     * 加签任务不能用completeTask来完成,，因为加签的任务并不属于正常流程中的一个节点任务，加签任务完后，任务还会回到加签前的人员手里
     *
     * @param taskId 任务id
     */
    @Override
    public Boolean resolveTask(String taskId) {
        Task task = getTask(taskId);
        taskService.addComment(taskId, task.getProcessInstanceId(), SecurityUtils.getUsername() + "审批加签任务");
        // 通过resolveTask完成加签任务，加签任务没有更改任务的基本信息,只是更新了assignee
        taskService.resolveTask(taskId);
        return Boolean.TRUE;
    }

    /**
     * 废止流程
     *
     * @param bpmApprovalForm bpm审批表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean abolition(BpmApprovalForm bpmApprovalForm) {
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(bpmApprovalForm.getProcInstId()).active().singleResult();
        if (Objects.isNull(processInstance)) {
            throw new BreezeBizException(ResultCode.PROCESS_NOT_FOUND);
        }

        taskService.addComment(bpmApprovalForm.getTaskId(), processInstance.getProcessInstanceId(), SecurityUtils.getUsername() + "废止任务,退回到发起人");
        this.runtimeService.deleteProcessInstance(bpmApprovalForm.getProcInstId(), bpmApprovalForm.getComment());
        return Boolean.TRUE;
    }

    /**
     * 查询用户发起任务
     *
     * @param userTaskQuery 用户任务查询
     * @return {@link Page }<{@link UserTaskVO }>
     */
    @Override
    public Page<UserTaskVO> listApplyUserTask(UserTaskQuery userTaskQuery) {
        // 查询历史任务实例
        // @formatter:off
        List<HistoricProcessInstance> hisProcInstList = historyService.createHistoricProcessInstanceQuery()
                .startedBy(SecurityUtils.getUsername())
                .orderByProcessInstanceStartTime().asc()
                .listPage(userTaskQuery.getOffset(), userTaskQuery.getSize());
        // @formatter:on
        Page<UserTaskVO> resultPage = new Page<>(userTaskQuery.getCurrent(), userTaskQuery.getSize(), 0L);
        List<UserTaskVO> userTaskList = new ArrayList<>();
        hisProcInstList.forEach(hisInst -> userTaskList.addAll(this.buildTaskList(hisInst, resultPage)));
        resultPage.setRecords(userTaskList);
        return resultPage;
    }

    private List<UserTaskVO> buildTaskList(HistoricProcessInstance hisProcInst, Page<UserTaskVO> resultPage) {
        // @formatter:off
        List<HistoricTaskInstance> hisTaskInstList = this.historyService.createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                .processInstanceId(hisProcInst.getId())
                .orderByTaskCreateTime().desc().list();
        long count = this.historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(hisProcInst.getId())
                .orderByTaskCreateTime().desc()
                .count();
        // @formatter:on
        resultPage.setTotal(resultPage.getTotal() + count);
        if (CollUtil.isEmpty(hisTaskInstList)) {
            throw new BreezeBizException(ResultCode.TASK_NOT_FOUND);
        }

        List<UserTaskVO> taskList = Lists.newArrayList();
        for (HistoricTaskInstance historicTaskInstance : hisTaskInstList) {
            taskList.add(buildTaskList(hisProcInst, historicTaskInstance));
        }
        return taskList;
    }

    @NotNull
    private Task getTask(String taskId) {
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        if (Objects.isNull(task)) {
            throw new BreezeBizException(ResultCode.TASK_NOT_FOUND);
        }
        return task;
    }

    /**
     * 加签任务
     *
     * @param taskId   任务ID
     * @param username 加签人员
     */
    @Override
    public Boolean delegateTask(String taskId, String username) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (Objects.nonNull(task)) {
            taskService.addComment(taskId, task.getProcessInstanceId(), SecurityUtils.getUsername() + "加签" + username);
            taskService.delegateTask(taskId, username);
        }
        return Boolean.TRUE;
    }

    /**
     * 填充任务的按钮
     *
     * @param procInstId                 流程实例ID
     * @param taskAssigneeQuerySupplier  任务查询supplier
     * @param taskCandidateQuerySupplier 任务查询supplier
     * @return {@link List }<{@link TaskButtonVO }>
     */
    private List<TaskButtonVO> findTasksButtons(String procInstId, Supplier<TaskQuery> taskAssigneeQuerySupplier, Supplier<TaskQuery> taskCandidateQuerySupplier) {
        List<TaskButtonVO> buttonList = new ArrayList<>();
        String username = SecurityUtils.getUsername();
        // @formatter:off
        Task task = taskAssigneeQuerySupplier.get()
                .taskAssignee(username)
                .active()
                .singleResult();
        // @formatter:on
        if (Objects.nonNull(task)) {
            buttonList.addAll(this.getFlowTaskButton(task, username));
            if (CollUtil.isEmpty(buttonList)) {
                // 默认通过驳回按钮
                buttonList.add(defaultAgreeButton(procInstId, task));
                buttonList.add(defaultRejectButton(procInstId, task));
            }
            if (Objects.nonNull(task.getAssignee())) {
                // 默认反签收按钮
                buttonList.add(defaultUnClaimButton(procInstId, task));
            }
            return buttonList;
        }
        // @formatter:off
        task = taskCandidateQuerySupplier.get()
                .taskCandidateUser(username)
                .active()
                .singleResult();
        // @formatter:on

        if (Objects.nonNull(task) && Objects.isNull(task.getAssignee())) {
            // 默认签收按钮
            buttonList.add(defaultClaimButton(procInstId, task));
        }

        return buttonList;
    }

    private UserTaskVO buildUserTaskVO(Task task) {
        if (Objects.isNull(task)) {
            throw new BreezeBizException(ResultCode.TASK_NOT_FOUND);
        }

        // 查询流程实例
        // @formatter:off
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        if (Objects.isNull(processInstance)) {
            throw new BreezeBizException(ResultCode.PROCESS_NOT_FOUND);
        }

        List<String> userList = Lists.newArrayList();
        task.getIdentityLinks().forEach(identityLink -> {
            if (StrUtil.isNotBlank(identityLink.getGroupId()) && StrUtil.equals(CANDIDATE, identityLink.getType())) {
                for (User user : identityService.createUserQuery().memberOfGroup(identityLink.getGroupId()).list()) {
                    userList.add(user.getId());
                }
            } else {
                User user = this.identityService.createUserQuery().userId(identityLink.getUserId()).singleResult();
                if (Objects.nonNull(user)) {
                    userList.add(user.getId());
                }
            }
        });
        userList.add(task.getAssignee());
        UserTaskVO userTaskVO = UserTaskVO.builder()
                .taskId(task.getId())
                .taskName(task.getName())
                .taskDefKey(task.getTaskDefinitionKey())
                .procDefKey(processInstance.getProcessDefinitionKey())
                .formKey(task.getFormKey())
                .owner(task.getOwner())
                .delegationState(Objects.isNull(task.getDelegationState()) ? "nan" : task.getDelegationState().name())
                .businessKey(processInstance.getBusinessKey())
                .procDefKey(processInstance.getProcessDefinitionKey())
                .procInstId(processInstance.getProcessInstanceId())
                .procDefId(processInstance.getProcessDefinitionId())
                .userList(userList)
                .assignee(task.getAssignee())
                .assigneeName(task.getAssignee())
                .applyUser(processInstance.getStartUserId())
                .applyUserName(processInstance.getStartUserId())
                .comment(MapUtil.getStr(task.getProcessVariables(), "comment", ""))
                .variable(new UserTaskVO.Variable(task.getProcessVariables()))
                .tenantId(processInstance.getTenantId())
                .createTime(task.getCreateTime())
                .build();
        // @formatter:on

        this.setTaskTitle(task.getProcessVariables(), userTaskVO);

        // 查找流程发起人
        this.setStarUser(userTaskVO, processInstance.getBusinessKey());

        return userTaskVO;
    }

    private void setStarUser(UserTaskVO userTaskVO, String processInstance) {
        // @formatter:off
        List<HistoricTaskInstance> historicTaskInstanceList = this.historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(userTaskVO.getProcInstId())
                .processInstanceBusinessKey(processInstance)
                //
                .orderByTaskCreateTime()
                .asc()
                .list();
        // @formatter:on
        if (CollUtil.isNotEmpty(historicTaskInstanceList) && StrUtil.isNotBlank(historicTaskInstanceList.get(0).getAssignee())) {
            User user = this.identityService.createUserQuery().userId(historicTaskInstanceList.get(0).getAssignee()).singleResult();
            if (Objects.nonNull(user) && StrUtil.isNotBlank(user.getId())) {
                userTaskVO.setApplyUserName(user.getFirstName());
                userTaskVO.setApplyUser(user.getId());
            }
        }
    }

    private void setTaskTitle(Map<String, Object> processVariableMap, UserTaskVO userTaskVO) {
        // @formatter:off
        Optional.ofNullable(processVariableMap.get("taskTitle"))
                .filter(v -> v instanceof String).map(Object::toString)
                .ifPresent(userTaskVO::setTaskTitle);
        // @formatter:on
    }
}
