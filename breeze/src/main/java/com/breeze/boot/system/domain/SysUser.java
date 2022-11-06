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
 * 系统用户实体
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@Schema(description = "系统角色实体")
public class SysUser extends BaseModel<SysUser> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位ID
     */
    @NotNull(message = "岗位ID不可为空")
    @Schema(description = "岗位ID")
    private Long postId;

    /**
     * 岗位名称
     */
    @TableField(exist = false)
    @Schema(description = "岗位名称")
    private String postName;

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不可为空")
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 登录账户名称
     */
    @NotBlank(message = "登录账户名称不可为空")
    @Schema(description = "登录账户名称")
    private String amountName;

    /**
     * 用户编码
     */
    @Schema(description = "用户编码")
    private String userCode;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不可为空")
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

    /**
     * 性别 0 女性 1 男性
     */
    @Schema(description = "性别 0 女性 1 男性")
    private Integer sex;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCard;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 微信OpenID
     */
    @Schema(description = "微信OpenID")
    private Integer openId;

    /**
     * 邮件
     */
    @Schema(description = "邮件")
    private String email;

    /**
     * 锁定
     */
    @Schema(description = "锁定")
    private Integer isLock;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 用户角色
     * <p>
     * 查询用户详情返回使用
     */
    @TableField(exist = false)
    @Schema(description = "用户角色")
    private List<SysRole> sysRoles;

    /**
     * 用户角色ID
     * <p>
     * 查询用户详情返回使用
     */
    @TableField(exist = false)
    @Schema(description = "用户角色ID")
    private List<Long> roleIds;

}
