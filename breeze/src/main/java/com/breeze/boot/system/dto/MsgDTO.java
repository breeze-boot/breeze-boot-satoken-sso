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

package com.breeze.boot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 消息VO
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
     * 部门IDs
     */
    @Schema(description = "部门IDs")
    private List<Long> deptIds;

    /**
     * 用户IDs
     */
    @Schema(description = "用户IDs")
    private List<Long> userIds;

    /**
     * 消息类型 1 紧急消息（error多次提醒） 2 一般消息（info提醒） 3 警示消息（warning） 4 正常消息（success提醒） 0 临时消息
     */
    @Schema(description = "消息类型")
    private Integer type;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private Long msgId;

    /**
     * 临时消息
     */
    @Schema(description = "临时消息")
    private String msg;

}
