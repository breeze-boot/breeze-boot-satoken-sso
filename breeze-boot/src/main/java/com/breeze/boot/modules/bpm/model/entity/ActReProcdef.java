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

package com.breeze.boot.modules.bpm.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 流程文件
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "act_re_procdef")
@Schema(description = "流程部署资源实体")
public class ActReProcdef implements Serializable {

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
     * 类别
     */
    private String category;

    /**
     * 名字
     */
    private String name;

    /**
     * KEY
     */
    private String key;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 部署id
     */
    private String deploymentId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * dgrm资源名称
     */
    private String dgrmResourceName;

    /**
     * 描述
     */
    private String description;

    /**
     * 开始形成关键
     */
    private Integer hasStartFormKey;

    /**
     * 有图形符号
     */
    private Integer hasGraphicalNotation;

    /**
     * 暂停状态
     */
    private Integer suspensionState;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 引擎版本
     */
    private String engineVersion;

    /**
     * 来自
     */
    private String derivedFrom;

    /**
     * 来自根
     */
    private String derivedFromRoot;

    /**
     * 派生版本
     */
    private Integer derivedVersion;

}
