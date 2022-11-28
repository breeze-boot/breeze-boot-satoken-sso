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

package com.breeze.websocket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

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
@Schema(description = "消息VO")
public class MsgVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息标题
     */
    @Schema(description = "消息标题")
    private String msgTitle;

    /**
     * 消息Code
     */
    @Schema(description = "消息Code")
    private String msgCode;

    /**
     * 消息类型 1 通知 2 公告
     */
    @Schema(description = "消息类型 1 通知 2 公告")
    private Integer msgType;

    /**
     * 消息级别 error 紧急消息（多次提醒） info 一般消息 warning 警示消消息 success 正常消息
     */
    @Schema(description = "消息级别 error 紧急消息（多次提醒） info 一般消息 warning 警示消消息 success 正常消息")
    private String msgLevel;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

}
