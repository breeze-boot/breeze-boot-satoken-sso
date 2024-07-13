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

package com.breeze.boot.modules.auth.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * 系统角色表单
 *
 * @author gaoweixuan
 * @since 2021-12-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统角色表单")
public class RoleForm {

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long id;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不可为空")
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不可为空")
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 数据权限编码
     */
    @Schema(description = "数据权限编码")
    private String permissionCode;

    /**
     * 数据权限IDs
     */
    @Schema(description = "数据权限IDs")
    private Set<Long> permissionIds;

}
