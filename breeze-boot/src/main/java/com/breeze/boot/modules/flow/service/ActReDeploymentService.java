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
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.modules.flow.model.entity.ActReDeployment;
import com.breeze.boot.modules.flow.model.query.FlowDeploymentQuery;
import com.breeze.boot.modules.flow.model.vo.DefinitionVO;
import org.springframework.stereotype.Service;

/**
 * 流程部署服务
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Service
public interface ActReDeploymentService extends IService<ActReDeployment> {

    /**
     * 列表页面
     *
     * @param flowDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link DefinitionVO}>
     */
    Page<DefinitionVO> listPage(FlowDeploymentQuery flowDeploymentQuery);

    /**
     * 详情
     *
     * @param definitionId 定义id
     * @return {@link DefinitionVO }
     */
    DefinitionVO getInfo(String definitionId);

}
