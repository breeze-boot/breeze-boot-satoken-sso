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
 * 系统日志
 * 日志
 *
 * @author gaoweixuan
 * @date 2022-10-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_log")
@Schema(description = "系统日志实体")
public class SysLog extends BaseModel<SysLog> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统模块
     */
    @Schema(description = "系统模块")
    private String systemModule;

    /**
     * 日志标题
     */
    @Schema(description = "日志标题")
    private String logTitle;

    /**
     * 日志类型 0 普通日志 1 登录日志
     */
    @Schema(description = "日志类型 0 普通日志 1 登录日志")
    private Integer logType;

    /**
     * 请求类型 GET POST PUT DELETE
     */
    @Schema(description = "请求类型 GET POST PUT DELETE")
    private String requestType;

    /**
     * IP
     */
    @Schema(description = "ip")
    private String ip;

    /**
     * 操作类型 0 添加 1 删除 2 修改 3 查询
     */
    @Schema(description = "操作类型 0 添加 1 删除 2 修改 3 查询")
    private Integer doType;

    /**
     * 浏览器名称
     */
    @Schema(description = "浏览器名称")
    private String browser;

    /**
     * 操作系统类型
     */
    @Schema(description = "操作系统类型")
    private String system;

    /**
     * 入参
     */
    @Schema(description = "入参")
    private String paramContent;

    /**
     * 结果 0 失败 1 成功
     */
    @Schema(description = "结果 0 失败 1 成功")
    private Integer result;

    /**
     * 结果信息
     */
    @Schema(description = "结果信息")
    private String resultMsg;

    /**
     * 方法执行时间
     */
    @Schema(description = "方法执行时间")
    private String time;

}
