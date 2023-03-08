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

package com.breeze.boot.process.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程执行实例
 *
 * @author breeze
 * @date 2023-03-08
 */
@Data
@TableName(value = "act_ru_execution")
public class ActRuExecution implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 牧师
     */
    private Integer rev;
    /**
     * proc本月id
     */
    private String procInstId;
    /**
     * 业务关键
     */
    private String businessKey;
    /**
     * 父id
     */
    private String parentId;
    /**
     * proc def id
     */
    private String procDefId;
    /**
     * 超级执行
     */
    private String superExec;
    /**
     * 根proc本月id
     */
    private String rootProcInstId;
    /**
     * 行动id
     */
    private String actId;
    /**
     * 是活跃
     */
    private Integer isActive;
    /**
     * 是并发
     */
    private Integer isConcurrent;
    /**
     * 是范围
     */
    private Integer isScope;
    /**
     * 是事件范围
     */
    private Integer isEventScope;
    /**
     * 是米根
     */
    private Integer isMiRoot;
    /**
     * 暂停状态
     */
    private Integer suspensionState;
    /**
     * 缓存ent状态
     */
    private Integer cachedEntState;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 名字
     */
    private String name;
    /**
     * 开始行动id
     */
    private String startActId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 开始用户id
     */
    private String startUserId;
    /**
     * 锁定时间
     */
    private Date lockTime;
    /**
     * 锁拥有者
     */
    private String lockOwner;
    /**
     * 计数启用
     */
    private Integer isCountEnabled;
    /**
     * evt subscr计数
     */
    private Integer evtSubscrCount;
    /**
     * 任务数
     */
    private Integer taskCount;
    /**
     * 工作数
     */
    private Integer jobCount;
    /**
     * 定时器工作数
     */
    private Integer timerJobCount;
    /**
     * susp工作数
     */
    private Integer suspJobCount;
    /**
     * deadletter工作数
     */
    private Integer deadletterJobCount;
    /**
     * 外部工人工作数
     */
    private Integer externalWorkerJobCount;
    /**
     * var计算
     */
    private Integer varCount;
    /**
     * id链接数
     */
    private Integer idLinkCount;
    /**
     * 回调id
     */
    private String callbackId;
    /**
     * 回调函数类型
     */
    private String callbackType;
    /**
     * 引用id
     */
    private String referenceId;
    /**
     * 引用类型
     */
    private String referenceType;
    /**
     * 本月id传播阶段
     */
    private String propagatedStageInstId;
    /**
     * 业务状况
     */
    private String businessStatus;

}