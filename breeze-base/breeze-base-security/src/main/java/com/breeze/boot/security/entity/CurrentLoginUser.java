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

package com.breeze.boot.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 登录用户的扩展类
 *
 * @author breeze
 * @date 2021/10/1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentLoginUser extends User implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 用户CODE
     */
    private String userCode;

    /**
     * 用户的角色CODE
     */
    private Set<String> userRoleCodes;

    /**
     * 用户的角色ID
     */
    private Set<Long> userRoleIds;

    /**
     * 登录后显示的账户名称
     */
    private String amountName;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门name
     */
    private String deptName;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 数据权限类型
     */
    private Integer permissionType;

    /**
     * 权限
     */
    private List<PermissionDTO> permissions;

    /**
     * 微信OpenId
     */
    private String openId;

    /**
     * 当前登录用户
     *
     * @param enabled               用户是否可用
     * @param accountNonExpired     账户是否过期
     * @param credentialsNonExpired 凭证是否过期
     * @param accountNonLocked      账户是否锁定
     * @param authorities           用户的权限集， 默认需要添加ROLE_ 前缀
     * @param loginUserDTO          登录用户DTO
     */
    public CurrentLoginUser(LoginUserDTO loginUserDTO
            , boolean enabled
            , boolean accountNonExpired
            , boolean credentialsNonExpired
            , boolean accountNonLocked
            , Collection<? extends GrantedAuthority> authorities) {
        super(loginUserDTO.getUsername(), loginUserDTO.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        // 扩展自定义属性
        this.userId = loginUserDTO.getId();
        this.amountName = loginUserDTO.getAmountName();
        this.userCode = loginUserDTO.getUserCode();
        this.userRoleCodes = loginUserDTO.getUserRoleCodes();
        this.userRoleIds = loginUserDTO.getUserRoleIds();
        this.deptId = loginUserDTO.getDeptId();
        this.deptName = loginUserDTO.getDeptName();
        this.email = loginUserDTO.getEmail();
        this.phone = loginUserDTO.getPhone();
        this.openId = loginUserDTO.getOpenId();
        this.tenantId = loginUserDTO.getTenantId();
        this.permissions = loginUserDTO.getPermissions();
        this.permissionType = loginUserDTO.getPermissionType() == null ? 0 : loginUserDTO.getPermissionType();
    }

    /**
     * 当前登录用户
     *
     * @param username              用户名
     * @param password              密码
     * @param enabled               启用
     * @param accountNonExpired     账户不过期
     * @param credentialsNonExpired 凭证不过期
     * @param accountNonLocked      非锁定账户
     * @param authorities           当局
     */
    public CurrentLoginUser(String username,
                            String password,
                            boolean enabled,
                            boolean accountNonExpired,
                            boolean credentialsNonExpired,
                            boolean accountNonLocked,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

}
