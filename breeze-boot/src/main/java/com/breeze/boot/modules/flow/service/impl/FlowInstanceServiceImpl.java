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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.flow.model.params.FlowApprovalParam;
import com.breeze.boot.modules.flow.model.params.FlowStartParam;
import com.breeze.boot.modules.flow.model.query.FlowInstanceQuery;
import com.breeze.boot.modules.flow.model.vo.FlowInstanceVO;
import com.breeze.boot.modules.flow.service.ActRuExecutionService;
import com.breeze.boot.modules.flow.service.IFlowInstanceService;
import com.breeze.boot.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程实例服务实现impl
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowInstanceServiceImpl implements IFlowInstanceService {

    /**
     * 运行时服务
     */
    private final RuntimeService runtimeService;

    /**
     * 任务服务
     */
    private final TaskService taskService;

    /**
     * 流程执行实例服务
     */
    private final ActRuExecutionService actRuExecutionService;

    /**
     * 发起
     *
     * @param startParam 流程启动参数
     * @return {@link Boolean}
     */
    @Override
    public Result<String> startProcess(FlowStartParam startParam) {
        // 添加流程发起人
        startParam.getVariables().put("applyUser", SecurityUtils.getCurrentUser().getUsername());
        // 设置流程发起人
        Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getUsername()));
        List<Execution> executionList = runtimeService.createExecutionQuery().processDefinitionKey(startParam.getDefinitionKey())
                .processInstanceBusinessKey(startParam.getBusinessKey()).list();
        if (CollUtil.isNotEmpty(executionList)) {
            throw new SystemServiceException(ResultCode.exception("该流程实例已经存在,请勿重复发起"));
        }
        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(startParam.getDefinitionKey(), startParam.getBusinessKey(), startParam.getVariables(), startParam.getTenantId());

        // //这个方法最终使用一个ThreadLocal类型的变量进行存储，也就是与当前的线程绑定，所以流程实例启动完毕之后，需要设置为null，防止多线程的时候出问题。
        Authentication.setAuthenticatedUserId(null);

        //自动完成第一个节点
        if (startParam.getIsPassFirstNode()) {
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
            FlowApprovalParam flowApprovalParam = new FlowApprovalParam();
            flowApprovalParam.setTaskId(task.getId());
            flowApprovalParam.setIsPass(Boolean.TRUE);
            flowApprovalParam.setComment("自动完成第一个节点");
            this.complete(flowApprovalParam);
            log.info("[自动完成第一个节点]");
        }
        return Result.ok(processInstance.getId());
    }

    /**
     * 审批任务
     *
     * @param flowApprovalParam 流程审批参数
     */
    @Override
    public void complete(FlowApprovalParam flowApprovalParam) {
        String comment = StrUtil.isNotBlank(flowApprovalParam.getComment()) ? flowApprovalParam.getComment() : "";
        flowApprovalParam.getVariables().put("pass", flowApprovalParam.getIsPass());
        //设置审批意见
        if (StrUtil.isNotEmpty(comment)) {
            taskService.addComment(flowApprovalParam.getTaskId(), flowApprovalParam.getProcInsId(), comment);
        }
        flowApprovalParam.getVariables().put("title", flowApprovalParam.getIsPass() ? "同意" : "拒绝");
        flowApprovalParam.getVariables().put("comment", flowApprovalParam.getComment());
        taskService.complete(flowApprovalParam.getTaskId(), flowApprovalParam.getVariables());
    }

    /**
     * 列表页面
     *
     * @param flowInstanceQuery 流程实例查询
     * @return {@link Page}<{@link FlowInstanceVO}>
     */
    @Override
    public Page<FlowInstanceVO> listPage(FlowInstanceQuery flowInstanceQuery) {
        return this.actRuExecutionService.listPage(flowInstanceQuery);
    }

    /**
     * 认领
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    @Override
    public void claim(String taskId, String userId) {
        taskService.claim(taskId, userId);
    }

    @Override
    public void cancel(FlowApprovalParam flowApprovalParam) {
        String deleteReason = "终止" + (StringUtils.isNotBlank(flowApprovalParam.getComment()) ? flowApprovalParam.getComment() : "");
        // 设置审批意见
        if (StringUtils.isNotEmpty(deleteReason)) {
            // 维护历史任务实例删除原因
            Task task = taskService.createTaskQuery().taskId(flowApprovalParam.getTaskId()).singleResult();
            taskService.addComment(flowApprovalParam.getTaskId(), task.getProcessInstanceId(), deleteReason);
        }
        runtimeService.deleteProcessInstance(flowApprovalParam.getProcInsId(), deleteReason);
    }

}
