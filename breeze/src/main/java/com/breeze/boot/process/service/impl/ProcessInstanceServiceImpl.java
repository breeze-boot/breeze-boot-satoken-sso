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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.process.dto.ProcessInstanceSearchDTO;
import com.breeze.boot.process.dto.ProcessStartDTO;
import com.breeze.boot.process.service.ActRuExecutionService;
import com.breeze.boot.process.service.IProcessInstanceService;
import com.breeze.boot.process.vo.ProcessInstanceVO;
import com.breeze.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程运行时服务impl
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@Slf4j
@Service
public class ProcessInstanceServiceImpl implements IProcessInstanceService {

    /**
     * 运行时服务
     */
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 流程执行实例服务
     */
    @Autowired
    private ActRuExecutionService actRuExecutionService;

    /**
     * 发起
     *
     * @param startDTO 流程发起DTO
     * @return {@link Boolean}
     */
    @Override
    public Boolean startProcess(ProcessStartDTO startDTO) {
        // TODO
        Authentication.setAuthenticatedUserId(String.valueOf(SecurityUtils.getCurrentUser().getId()));
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKeyAndTenantId(startDTO.getProcessKey(), "", startDTO.getVariables(), startDTO.getTenantId());
        return Boolean.TRUE;
    }

    /**
     * 列表页面
     *
     * @param processInstanceSearchDTO 流程实例搜索DTO
     * @return {@link Page}<{@link ProcessInstanceVO}>
     */
    @Override
    public Page<ProcessInstanceVO> listPage(ProcessInstanceSearchDTO processInstanceSearchDTO) {
        return this.actRuExecutionService.listPage(processInstanceSearchDTO);
    }

}
