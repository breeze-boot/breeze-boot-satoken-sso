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
import com.breeze.boot.modules.flow.model.params.FlowDefinitionDeleteParam;
import com.breeze.boot.modules.flow.model.params.FlowDesignXmlFileParam;
import com.breeze.boot.modules.flow.model.params.FlowDesignXmlStringParam;
import com.breeze.boot.modules.flow.model.query.FlowDefinitionQuery;
import com.breeze.boot.modules.flow.model.query.FlowDeploymentQuery;
import com.breeze.boot.modules.flow.model.query.FlowHistoryDefinitionQuery;
import com.breeze.boot.modules.flow.model.vo.DefinitionVO;
import com.breeze.boot.modules.flow.model.vo.FlowDefinitionVO;

import java.util.List;


/**
 * 流程资源管理服务接口
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
public interface IFlowDefinitionService {

    /**
     * 部署
     *
     * @param flowDesignXmlStringParam 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    Result<String> deploy(FlowDesignXmlStringParam flowDesignXmlStringParam);

    /**
     * 部署
     *
     * @param flowDesignXmlFileParam 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    Result<String> deploy(FlowDesignXmlFileParam flowDesignXmlFileParam);

    /**
     * 列表页面
     *
     * @param flowDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link DefinitionVO}>
     */
    Page<DefinitionVO> listPage(FlowDeploymentQuery flowDeploymentQuery);

    /**
     * 挂起/激活
     *
     * @param definitionId 流程定义ID
     * @return {@link Boolean}
     */
    Boolean isSuspended(String definitionId);

    /**
     * 获取流程定义xml
     *
     * @param definitionKey 流程KEY
     * @return {@link String}
     */
    String getProcessDefinitionXml(String definitionKey);


    /**
     * 获取流程定义png
     *
     * @param flowDefinitionQuery 流程定义查询参数
     * @return {@link Result}<{@link ?}>
     */
    Result<?> getProcessDefinitionPng(FlowDefinitionQuery flowDefinitionQuery);

    /**
     * 版本列表页面
     *
     * @param flowDeploymentQuery 流程部署查询
     * @return {@link Page}<{@link FlowDefinitionVO}>
     */
    Page<FlowDefinitionVO> listVersionPage(FlowDeploymentQuery flowDeploymentQuery);

    /**
     * 获得版本流程定义png
     *
     * @param flowHistoryDefinitionQuery 流程历史定义查询参数
     * @return {@link Result}<{@link ?}>
     */
    Result<?> getHistoryProcessDefinitionPng(FlowHistoryDefinitionQuery flowHistoryDefinitionQuery);

    /**
     * 获得版本流程定义xml
     *
     * @param flowHistoryDefinitionQuery 流程历史定义查询参数
     * @return {@link String}
     */
    Result<?> getHistoryProcessDefinitionXml(FlowHistoryDefinitionQuery flowHistoryDefinitionQuery);

    /**
     * 删除
     *
     * @param flowDefinitionDeleteParamList 流定义删除参数列表
     * @return {@link Boolean}
     */
    Boolean delete(List<FlowDefinitionDeleteParam> flowDefinitionDeleteParamList);

    /**
     * 详情
     *
     * @param definitionId 定义id
     * @return {@link DefinitionVO }
     */
    DefinitionVO getInfo(String definitionId);

}
