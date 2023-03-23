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

package com.breeze.security.userextension;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 登录用户的扩展类
 *
 * @author gaoweixuan
 * @date 2021/10/1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentLoginUser extends User implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 登录用户信息数据
     */
    @Schema(description = "登录的用户扩展信息 redis存储实体")
    private LoginUser loginUser;

    /**
     * 当前登录用户
     *
     * @param enabled               用户是否可用
     * @param accountNonExpired     账户是否过期
     * @param credentialsNonExpired 凭证是否过期
     * @param accountNonLocked      账户是否锁定
     * @param authorities           用户的权限集， 角色默认需要添加ROLE_ 前缀
     * @param loginUser             登录用户
     */
    public CurrentLoginUser(LoginUser loginUser
            , boolean enabled
            , boolean accountNonExpired
            , boolean credentialsNonExpired
            , boolean accountNonLocked
            , Collection<? extends GrantedAuthority> authorities) {
        super(loginUser.getUsername(), loginUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        // 扩展自定义属性
        this.loginUser = loginUser;
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
     * @param authorities           用户的权限集， 角色默认需要添加ROLE_ 前缀
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
