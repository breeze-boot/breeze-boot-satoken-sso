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

package com.breeze.boot.modules.flow.domain.query;


import com.breeze.boot.core.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 流程定义查询使用
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程部署查询")
public class ProcessDeploymentQuery extends PageQuery {

    /**
     * 流程定义名称
     */
    @Schema(description = "流程定义名称")
    private String name;


    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    private String userId;

    /**
     * 租户ID
     */
    @Schema(description = "租户Id")
    private String tenantId;

}
