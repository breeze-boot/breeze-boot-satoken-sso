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

package com.breeze.boot.modules.flow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.flow.model.params.FlowApprovalParam;
import com.breeze.boot.modules.flow.model.params.FlowStartParam;
import com.breeze.boot.modules.flow.model.query.FlowInstanceQuery;
import com.breeze.boot.modules.flow.model.vo.FlowInstanceVO;

/**
 * 流程实例管理服务接口
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
public interface IFlowInstanceService {

    /**
     * 发起
     *
     * @param startParam 流程启动参数
     * @return {@link Boolean}
     */
    Result<String> startProcess(FlowStartParam startParam);

    /**
     * 完成
     *
     * @param flowApprovalParam 流程审批参数
     */
    void complete(FlowApprovalParam flowApprovalParam);

    /**
     * 列表页面
     *
     * @param flowInstanceQuery 流程实例查询
     * @return {@link Page}<{@link FlowInstanceVO}>
     */
    Page<FlowInstanceVO> listPage(FlowInstanceQuery flowInstanceQuery);

    /**
     * 认领
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void claim(String taskId, String userId);

    void cancel(FlowApprovalParam flowApprovalParam);

}
