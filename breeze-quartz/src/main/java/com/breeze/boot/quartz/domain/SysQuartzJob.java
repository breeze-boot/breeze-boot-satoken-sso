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

package com.breeze.boot.quartz.domain;

import com.breeze.core.entity.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * quartz任务调度
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@Data
public class SysQuartzJob extends BaseModel<SysQuartzJob> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroupName;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务类名
     */
    private String clazzName;

    /**
     * misfire策略1 立即执行 2 执行一次 3 放弃
     */
    private Integer misfirePolicy;

    /**
     * 是否并发 0 不并发 1 并发
     */
    private Integer concurrent;

    /**
     * 状态 0 关闭 1 开启
     */
    private Integer status;

}
