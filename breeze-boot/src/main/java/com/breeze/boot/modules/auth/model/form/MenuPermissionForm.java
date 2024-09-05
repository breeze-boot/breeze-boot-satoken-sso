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
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * 菜单权限表单
 *
 * @author gaoweixuan
 * @since 2022-09-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "菜单权限表单")
public class MenuPermissionForm {

    /**
     * 角色Id
     */
    @Schema(description = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 权限IDs
     */
    @Schema(description = "权限IDs")
    @NotNull(message = "权限IDs不能为空")
    private List<Long> permissionIds;

}


