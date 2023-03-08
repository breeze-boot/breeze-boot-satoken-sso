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

package com.breeze.boot.process.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.process.dto.ProcessInstanceSearchDTO;
import com.breeze.boot.process.dto.ProcessStartDTO;
import com.breeze.boot.process.vo.ProcessInstanceVO;

/**
 * 工作流运行时管理服务服务接口
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
public interface IProcessInstanceService {

    /**
     * 发起
     *
     * @param startDTO 流程发起DTO
     * @return {@link Boolean}
     */
    Boolean startProcess(ProcessStartDTO startDTO);

    /**
     * 列表页面
     *
     * @param processInstanceSearchDTO 流程实例搜索DTO
     * @return {@link Page}<{@link ProcessInstanceVO}>
     */
    Page<ProcessInstanceVO> listPage(ProcessInstanceSearchDTO processInstanceSearchDTO);

}
