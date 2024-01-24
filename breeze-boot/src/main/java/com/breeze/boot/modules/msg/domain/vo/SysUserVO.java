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

package com.breeze.boot.modules.msg.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.breeze.boot.modules.auth.domain.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户VO
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Data
@Builder
@HeadRowHeight(40)
@ContentRowHeight(35)
@ColumnWidth(20)
@HeadStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,
        verticalAlignment = VerticalAlignmentEnum.CENTER,
        borderBottom = BorderStyleEnum.THIN, borderLeft = BorderStyleEnum.THIN,
        borderTop = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,
        verticalAlignment = VerticalAlignmentEnum.CENTER,
        borderBottom = BorderStyleEnum.THIN, borderLeft = BorderStyleEnum.THIN,
        borderTop = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户VO")
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名称")
    @ExcelProperty(value = "用户名称", index = 0)
    private String username;

    /**
     * 用户编码
     */
    @Schema(description = "用户编码")
    @ExcelProperty(value = "用户编码", index = 1)
    private String userCode;

    /**
     * 登录账户名称
     */
    @NotBlank(message = "登录账户名称不可为空")
    @Schema(description = "登录账户名称")
    @ExcelProperty(value = "登录账户名称", index = 2)
    private String amountName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @ExcelProperty(value = "头像地址", index = 3)
    private String avatar;

    /**
     * 岗位ID
     */
    @ExcelIgnore
    @Schema(description = "岗位ID")
    private Long postId;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @ExcelProperty(value = "岗位名称", index = 4)
    private String postName;

    /**
     * 部门ID
     */
    @ExcelIgnore
    @NotNull(message = "部门ID不可为空")
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @ExcelProperty(value = "部门名称", index = 5)
    private String deptName;

    /**
     * 性别 0 女性 1 男性
     */
    @Schema(description = "性别 0 女性 1 男性")
    @ExcelProperty(value = "性别", index = 6)
    private Integer sex;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    @ExcelProperty(value = "身份证号", index = 7)
    private String idCard;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @ExcelProperty(value = "手机号", index = 8)
    private String phone;

    /**
     * 微信OpenID
     */
    @Schema(description = "微信OpenID")
    @ExcelProperty(value = "微信OpenID", index = 9)
    private String openId;

    /**
     * 邮件
     */
    @Schema(description = "邮件")
    @ExcelProperty(value = "邮件", index = 10)
    private String email;

    /**
     * 锁定
     */
    @Schema(description = "锁定")
    @ExcelProperty(value = "锁定", index = 11)
    private Integer isLock;

    /**
     * 租户ID
     */
    @ExcelIgnore
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 用户角色
     * <p>
     * 查询用户详情返回使用
     */
    @ExcelIgnore
    @Schema(description = "用户角色")
    private List<SysRole> sysRoles;

    /**
     * 用户角色ID
     * <p>
     * 查询用户详情返回使用
     */
    @ExcelIgnore
    @Schema(description = "用户角色ID")
    private List<Long> roleIds;

}
