/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统用户实体
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUserEntity extends BaseModel<SysUserEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * 用户code
     */
    private String userCode;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;

    /**
     * 登录账户
     */
    private String amountName;
    /**
     * 类型
     */
    private Integer amountType;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 锁定
     */
    private Integer isLock;
    /**
     * 微信ID
     */
    private Integer openId;
    /**
     * 邮件
     */
    private String email;

}
