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

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

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
@Schema(description = "流程审批参数")
public class FlowApprovalParam {

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private String taskId;

    /**
     * 意见
     */
    @Schema(description = "意见")
    private String comment;

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    private String procInsId;

    /**
     * 通过OR驳回
     */
    @Schema(description = "通过OR驳回")
    private Boolean isPass;

    /**
     * 变量
     */
    @Schema(description = "变量")
    private Map<String, Object> variables = Maps.newHashMap();

    /**
     * 租户ID
     */
    @NotBlank(message = "租户ID不能为空")
    @Schema(description = "租户ID")
    private String tenantId;
}
