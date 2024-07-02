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

package com.breeze.boot.modules.flow.model.bo;

import lombok.Data;

import java.util.Map;

@Data
public class FlowCallBackExecutionBO {
    /**
     * 标题
     */
    private String title;
    /**
     * 意见
     */
    private String comment;

    /**
     * 发起人
     */
    private String applyUser;

    /**
     * 处理人
     */
    private String userId;

    /**
     * 变量
     */
    private Map<String, Object> variableMap;

    /**
     * Class名
     */
    private String className;

    /**
     * Bean名
     */
    private String beanName;

    /**
     * Method名
     */
    private String methodName;

    /**
     * 变量
     */
    private String variable;

    private String assignee;

    /**
     * 角色
     */
    private String roleCode;

    /**
     * 业务ID
     */
    private String bizKey;

    /**
     * 流程定义ID
     */
    private String procDefKey;
    /**
     * 流程实例ID
     */
    private String procInsId;
    /**
     * 任务ID
     */
    private String taskId;

}
