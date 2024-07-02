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

package com.breeze.boot.modules.flow.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 流程资源参数
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程设计参数")
public class FlowDesignXmlStringParam {

    @Schema(description = "流程部署Key")
    @NotBlank(message = "流程部署Key不能为空")
    private String definitionKey;

    /**
     * 名字
     */
    @Schema(description = "流程部署名称")
    @NotBlank(message = "流程部署名称不能为空")
    private String definitionName;

    /**
     * 类别
     */
    @Schema(description = "流程部署分类")
    @NotBlank(message = "流程部署分类不能为空")
    private String categoryCode;

    /**
     * xml
     */
    @Schema(description = "流程部署xml")
    @NotBlank(message = "xml不能为空")
    private String xml;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

}
