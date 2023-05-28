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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.flow.params.ProcessStartParam;
import com.breeze.boot.flow.query.ProcessInstanceQuery;
import com.breeze.boot.flow.service.ActRuExecutionService;
import com.breeze.boot.flow.service.IProcessInstanceService;
import com.breeze.boot.flow.vo.ProcessInstanceVO;
import com.breeze.boot.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RuntimeService;
import org.springframework.stereotype.Service;

/**
 * 流程实例服务实现impl
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessInstanceServiceImpl implements IProcessInstanceService {

    /**
     * 运行时服务
     */
    private final RuntimeService runtimeService;

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
    public Boolean startProcess(ProcessStartParam startParam) {
        Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getId()));
        this.runtimeService.startProcessInstanceByKeyAndTenantId(startParam.getProcessKey(), startParam.getBusinessKey(), startParam.getVariables(), startParam.getTenantId());
        return Boolean.TRUE;
    }

    /**
     * 列表页面
     *
     * @param processInstanceQuery 流程实例查询
     * @return {@link Page}<{@link ProcessInstanceVO}>
     */
    @Override
    public Page<ProcessInstanceVO> listPage(ProcessInstanceQuery processInstanceQuery) {
        return this.actRuExecutionService.listPage(processInstanceQuery);
    }

}
