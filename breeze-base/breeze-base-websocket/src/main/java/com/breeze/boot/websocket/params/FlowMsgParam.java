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

package com.breeze.boot.websocket.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 消息参数
 *
 * @author gaoweixuan
 * @since 2022-11-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程消息参数")
public class FlowMsgParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Ids
     */
    @Schema(description = "用户Ids")
    private List<Long> userIds;

    private String msgCode;

    private Map<String, Object> contentMap;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
