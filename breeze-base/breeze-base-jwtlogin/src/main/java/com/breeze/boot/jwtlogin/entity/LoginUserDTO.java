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

package com.breeze.boot.jwtlogin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author breeze
 * @date 2022-01-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * userCode
     */
    private String userCode;

    /**
     * 登录后显示的账户名称
     */
    private String amountName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
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
     * 身份证号 备用
     */
    private String idCard;
    /**
     * 类型
     */
    private Integer amountType;
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

    /**
     * 用户角色集合
     */
    private List<UserRoleDTO> userRoleDTOList;

    private List<String> userRoleCodes;

    private List<Long> userRoleIds;

    private List<String> authorities;

}
