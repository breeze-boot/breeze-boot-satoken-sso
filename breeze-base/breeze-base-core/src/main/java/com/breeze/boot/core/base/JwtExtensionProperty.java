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

package com.breeze.boot.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * jwt扩展属性（存储用户信息jwt解析获取）
 *
 * @author gaoweixuan
 * @since 2024-07-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "jwt扩展属性（存储用户信息jwt解析获取）")
public class JwtExtensionProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 用户编码
     */
    @Schema(description = "用户编码")
    private String userCode;

    /**
     * 登录后显示的账户名称
     */
    @Schema(description = "登录显示的用户账户名")
    private String amountName;

    /**
     * 用户名
     */
    @Schema(description = "登录用户名")
    private String username;

    /**
     * 类型
     */
    @Schema(description = "账户类型")
    private Integer amountType;

    /**
     * 微信OpenId
     */
    @Schema(description = "微信OpenId")
    private String openId;

    /**
     * 用户角色代码集合
     */
    @Schema(description = "用户角色编码集合")
    private Set<String> userRoleCodes;

    /**
     * 用户角色Id集合
     */
    @Schema(description = "用户角色Id集合")
    private Set<Long> userRoleIds;

    /**
     * 数据权限规则
     */
    @Schema(description = "数据权限规则")
    private String permissionType;

    /**
     * 数据权限编码
     */
    @Schema(description = "行数据权限编码")
    private Set<String> rowPermissionCode;

    /**
     * 列数据权限
     */
    @Schema(description = "列数据权限")
    private Set<String> columnPermissionCode;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
