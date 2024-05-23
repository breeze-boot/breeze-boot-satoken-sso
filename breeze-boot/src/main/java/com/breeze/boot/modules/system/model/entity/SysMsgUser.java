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

package com.breeze.boot.modules.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.base.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 系统用户消息实体
 *
 * @author gaoweixuan
 * @since 2022-11-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_msg_user")
@Schema(description = "系统用户消息实体")
public class SysMsgUser extends BaseModel<SysMsgUser> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private Long msgId;

    /**
     * 标记已读 0 未读 1 已读
     */
    @Schema(description = "标记已读 0 未读 1 已读")
    private Integer isRead;

    /**
     * 标记关闭 0 未关闭 1 已关闭
     */
    @Schema(description = "标记关闭 0 未关闭 1 已关闭")
    private Integer isClose;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    private Long userId;

}
