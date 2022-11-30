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

package com.breeze.boot.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.entity.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 系统用户消息内容
 *
 * @author breeze
 * @date 2022-11-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_user_msg_snapshot")
@Schema(description = "系统用户消息快照实体")
public class SysUserMsgSnapshot extends BaseModel<SysUserMsgSnapshot> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    private Long msgId;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息类型 0 通知 1 公告
     */
    private Integer msgType;

    /**
     * 消息级别 error 紧急消息（多次提醒） info 一般消息 warning 警示消消息 success 正常消息
     */
    private String msgLevel;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 部门ID
     */
    private Long deptId;

}
