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

package com.breeze.boot.websocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 用户消息传输类
 *
 * @author gaoweixuan
 * @since 2022-11-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户消息DTO")
public class UserMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<MsgBody> msgBodyList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    @Schema(description = "用户消息体")
    public static class MsgBody implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 消息标题
         */
        private String title;

        /**
         * 消息编码
         */
        private String code;

        /**
         * 消息ID
         */
        private Long msgId;

        /**
         * 消息类型
         */
        private Integer type;

        /**
         * 消息等级
         */
        private String level;

        /**
         * 内容
         */
        private String content;

        /**
         * 部门ID
         */
        private Long deptId;

        /**
         * 用户ID
         */
        private Long userId;
    }

}
