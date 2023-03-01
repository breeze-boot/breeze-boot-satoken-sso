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

package com.breeze.boot.flowable.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.flowable.dto.FlowRepositoryDTO;
import org.flowable.engine.repository.ProcessDefinition;

/**
 * 工作流资源管理服务服务接口
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
public interface IFlowRepositoryService {

    /**
     * 列表页面
     *
     * @param flowRepositoryDTO 流程资源库dto
     * @return {@link IPage}<{@link ProcessDefinition}>
     */
    IPage<ProcessDefinition> listPage(FlowRepositoryDTO flowRepositoryDTO);

}
