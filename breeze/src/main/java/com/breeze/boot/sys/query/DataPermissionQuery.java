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

package com.breeze.boot.sys.query;

import com.breeze.core.base.PageQuery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 数据权限查询
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "数据权限查询")
public class DataPermissionQuery extends PageQuery {

    /**
     * 数据权限名称
     */
    @JsonIgnore
    @Schema(description = "数据权限名称")
    private String dataPermissionName;

    /**
     * 数据权限代码
     */
    @Schema(description = "数据权限代码")
    private String dataPermissionCode;

    /**
     * 数据限类型
     */
    @Schema(description = "数据限类型")
    private String dataPermissionType;

    /**
     * 操作符
     */
    @Schema(description = "操作符")
    private String operator;

    /**
     * 权限过滤 sql
     */
    @Schema(description = "权限过滤 sql")
    private String strSql;

    /**
     * 权限 部门ID
     */
    @Schema(description = "权限 部门ID")
    private String dataPermissions;
}
