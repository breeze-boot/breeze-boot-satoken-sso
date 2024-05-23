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

package com.breeze.boot.security.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户的扩展类
 *
 * @author gaoweixuan
 * @since 2021/10/1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "登录的用户信息封装返回前端的实体")
public class UserPrincipal extends User implements UserDetails {

    private static final long serialVersionUID = -4085833706868746460L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

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
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private Integer sex;

    /**
     * 类型
     */
    @Schema(description = "账户类型")
    private Integer amountType;

    /**
     * 锁定
     */
    @Schema(description = "锁定")
    private Integer isLock;

    /**
     * 邮件
     */
    @Schema(description = "邮件")
    private String email;

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
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 数据权限
     */
    @Schema(description = "数据权限")
    private String permissions;

    /**
     * 需要排除的字段数据权限
     */
    @Schema(description = "需要排除的字段数据权限")
    private Set<String> excludeColumn;

    // @formatter:off
    /**
     * 用户主体
     *
     * @param username              用户名
     * @param password              密码
     * @param enabled               启用
     * @param accountNonExpired     账户不过期
     * @param credentialsNonExpired 凭证不过期
     * @param accountNonLocked      非锁定账户
     * @param authorities           权限集合
     * @param id                    用户Id
     * @param deptId                部门Id
     * @param deptName              部门名称
     * @param userCode              用户代码
     * @param amountName            登录显示的用户账户名
     * @param avatar                头像
     * @param phone                 电话
     * @param sex                   性
     * @param amountType            量类型
     * @param isLock                是否锁定
     * @param email                 电子邮件
     * @param userRoleCodes         用户角色代码
     * @param userRoleIds           用户角色Id
     * @param tenantId              租户Id
     * @param permissions           数据权限
     * @param excludeColumn         需要排除的字段数据权限
     */
    public UserPrincipal(String username,
                         String password,
                         boolean enabled,
                         boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities,
                         Long id,
                         Long deptId,
                         String deptName,
                         String userCode,
                         String amountName,
                         String avatar,
                         String phone,
                         Integer sex,
                         Integer amountType,
                         Integer isLock,
                         String email,
                         Set<String> userRoleCodes,
                         Set<Long> userRoleIds,
                         Long tenantId,
                         String permissions,
                         Set<String> excludeColumn) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.deptId = deptId;
        this.deptName = deptName;
        this.userCode = userCode;
        this.amountName = amountName;
        this.avatar = avatar;
        this.phone = phone;
        this.sex = sex;
        this.amountType = amountType;
        this.isLock = isLock;
        this.email = email;
        this.userRoleCodes = userRoleCodes;
        this.userRoleIds = userRoleIds;
        this.tenantId = tenantId;
        this.permissions = permissions;
        this.excludeColumn = excludeColumn;
    }

    /**
     * 用户主体
     *
     * @param username    用户名
     * @param password    密码
     * @param authorities 当局
     */
    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     * 用户主体
     *
     * @param username              用户名
     * @param password              密码
     * @param enabled               启用
     * @param accountNonExpired     账户不过期
     * @param credentialsNonExpired 凭证不过期
     * @param accountNonLocked      非锁定账户
     * @param authorities           当局
     */
    public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    // @formatter:on

}
