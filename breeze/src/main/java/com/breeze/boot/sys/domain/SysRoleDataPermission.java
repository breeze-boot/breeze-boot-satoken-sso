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

package com.breeze.boot.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.core.base.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 系统角色数据权限实体
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_role_data_permission")
@Schema(description = "角色权限实体")
public class SysRoleDataPermission extends BaseModel<SysRoleDataPermission> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据权限id
     */
    @Schema(description = "数据权限ID")
    private Long dataPermissionId;

    /**
     * 角色id
     */
    @Schema(description = "角色ID")
    private Long roleId;

}
