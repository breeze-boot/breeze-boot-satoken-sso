/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.websocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 消息DTO
 *
 * @author breeze
 * @date 2022-11-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "消息DTO")
public class MsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户IDs
     */
    @Schema(description = "用户IDs")
    private List<Long> userIds;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    @NotNull(message = "消息不能为空")
    private Long msgId;

    /**
     * 消息内容
     */
    @Schema(description = "临时消息")
    private String msg;

}
