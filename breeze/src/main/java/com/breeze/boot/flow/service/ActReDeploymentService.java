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

package com.breeze.boot.flow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.flow.domain.ActReDeployment;
import com.breeze.boot.flow.query.ProcessDeploymentQuery;
import com.breeze.boot.flow.vo.DeploymentVO;
import org.springframework.stereotype.Service;

/**
 * 流程部署服务
 *
 * @author gaoweixuan
 * @date 2023-03-08
 */
@Service
public interface ActReDeploymentService extends IService<ActReDeployment> {

    /**
     * 列表页面
     *
     * @param processDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link DeploymentVO}>
     */
    Page<DeploymentVO> listPage(ProcessDeploymentQuery processDeploymentQuery);

}
