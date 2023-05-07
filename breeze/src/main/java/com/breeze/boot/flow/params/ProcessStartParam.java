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

package com.breeze.boot.flow.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 流程资源参数
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程发布启动使用参数")
public class ProcessStartParam {

    /**
     * 流程KEY
     */
    @NotBlank(message = "流程定义KEY不能为空")
    @Schema(description = "流程定义KEY")
    private String processKey;

    /**
     * 租户ID
     */
    @NotBlank(message = "租户ID不能为空")
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 业务KEY
     */
    @Schema(description = "业务KEY")
    private String businessKey;

    /**
     * 变量
     */
    private Map<String, Object> variables;

}
