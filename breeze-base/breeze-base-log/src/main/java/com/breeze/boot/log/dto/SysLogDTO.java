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

package com.breeze.boot.log.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统日志dto
 *
 * @author gaoweixuan
 * @date 2022-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统模块
     */
    private String systemModule;

    /**
     * 日志标题
     */
    private String logTitle;

    /**
     * 日志类型 0 普通日志 1 登录日志
     */
    private Integer logType;

    /**
     * 请求类型 GET POST PUT DELETE
     */
    private String requestType;

    /**
     * IP
     */
    private String ip;

    /**
     * 操作类型 0 添加 1 删除 2 修改 3 查询
     */
    private Integer doType;

    /**
     * 浏览器名称
     */
    private String browser;

    /**
     * 操作系统类型
     */
    private String system;

    /**
     * 入参
     */
    private String paramContent;

    /**
     * 结果 0 失败 1 成功
     */
    private Integer result;

    /**
     * 时间
     */
    private String time;

    /**
     * 执行结果
     */
    private String resultMsg;

}
