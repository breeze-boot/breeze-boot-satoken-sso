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

package com.breeze.boot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.entity.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 系统部门实体
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dept")
@Schema(description = "系统部门实体")
public class SysDept extends BaseModel<SysDept> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编码
     */
    @NotBlank(message = "部门编码不能为空")
    @Schema(description = "部门编码")
    private String deptCode;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 上级部门ID
     */
    @NotNull(message = "上级部门ID不能为空")
    @Schema(description = "上级部门ID")
    private Long parentId;

    /**
     * sys部门名单列表
     */
    @Schema(description = "下级部门")
    @TableField(exist = false)
    private List<SysDept> sysDeptList;

}
