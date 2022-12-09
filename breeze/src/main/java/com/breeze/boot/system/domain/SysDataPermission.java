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
 * 系统数据权限实体
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_data_permission")
@Schema(description = "系统数据权限实体")
public class SysDataPermission extends BaseModel<SysDataPermission> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据权限名称
     */
    @Schema(description = "数据权限名称")
    private String dataPermissionName;

    /**
     * 数据权限编码
     */
    @Schema(description = "数据权限编码")
    private String dataPermissionCode;

    /**
     * 数据权限标识
     */
    @Schema(description = "数据权限标识")
    private String dataPermissionType;

    /**
     * 操作符
     */
    @Schema(description = "操作符")
    private String operator;

    /**
     * sql
     */
    @Schema(description = "自定义SQL")
    private String strSql;

    /**
     * 权限
     */
    @Schema(description = "权限")
    private String dataPermissions;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
