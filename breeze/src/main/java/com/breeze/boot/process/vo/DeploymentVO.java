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

package com.breeze.boot.process.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程定义资源VO
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentVO {

    /**
     * id
     */
    private String id;
    /**
     * 关键
     */
    private String key;
    /**
     * 名字
     */
    private String name;
    /**
     * 类别名称
     */
    private String categoryName;
    /**
     * 承租者id
     */
    private String tenantId;
    /**
     * 部署时
     */
    private Date deploymentTime;
    /**
     * 版本
     */
    private String version;
    /**
     * 暂停
     */
    private Integer suspended;
    /**
     * 父部署id
     */
    private String parentDeploymentId;

    public boolean getSuspended() {
        return this.suspended == 1;
    }

}
