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

package com.breeze.boot.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
@Schema(description = "系统数据权限添加DTO")
public class SysDataPermissionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @Schema(description = "权限ID")
    private String id;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    private String dataPermissionName;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码")
    private String dataPermissionCode;

    /**
     * 权限类型
     */
    @Schema(description = "权限类型")
    private String dataPermissionType;

    /**
     * 操作符
     */
    @Schema(description = "操作符")
    private String operator;

    /**
     * 权限
     */
    @Schema(description = "权限")
    private List<String> dataPermissions;

    /**
     * diy权限
     */
    @Schema(description = "DIY权限")
    private List<PermissionDiy> dataPermissionTableSqlDiyData;

}
