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

package com.breeze.boot.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.security.entity.CurrentLoginUser;
import com.breeze.boot.security.entity.LoginUserDTO;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.service.SysUserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 用户令牌服务
 *
 * @author gaoweixuan
 * @date 2022-10-19
 */
@Service
public class UserTokenService {

    /**
     * 用户令牌缓存服务
     */
    @Autowired
    private UserTokenCacheService userTokenCacheService;

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 加载用户用户名
     *
     * @param username 用户名
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser loadUserByUsername(@NotNull String username) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
    }

    /**
     * 加载用户通过电子邮件
     *
     * @param email 电子邮件
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser loadUserByEmail(@NotNull String email) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, email));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("邮箱不存在");
        }
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
    }

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser loadUserByPhone(@NotNull String phone) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("电话不存在");
        }
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
    }

    /**
     * 创建或者加载用户通过微信OpenId
     *
     * @param openId openId
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser createOrLoadUserByOpenId(@NotNull String openId) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getOpenId, openId));
        if (Objects.isNull(sysUser)) {
            sysUser.setOpenId(openId);
            // 不存在就去创建
            sysUser = this.sysUserService.registerUser(sysUser);
        }
        LoginUserDTO loginUserDTO = this.userTokenCacheService.getLoginUserDTO(sysUser);
        return this.getLoginUser(loginUserDTO);
    }

    /**
     * 刷新用户
     *
     * @param username 用户名
     * @return {@link LoginUserDTO}
     */
    public LoginUserDTO refreshUser(String username) {
        SysUser sysUser = this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名错误或不存在");
        }
        return this.userTokenCacheService.getLoginUserDTO(sysUser);
    }

    /**
     * 获取登录用户
     *
     * @param loginUserDTO 登录用户dto
     * @return {@link CurrentLoginUser}
     */
    public CurrentLoginUser getLoginUser(LoginUserDTO loginUserDTO) {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        Optional.ofNullable(loginUserDTO.getAuthorities()).ifPresent(auth -> getAuthorityList(auth, authorities));
        Optional.ofNullable(loginUserDTO.getUserRoleCodes()).ifPresent(roleCode -> getAuthorityList(roleCode, authorities));
        return new CurrentLoginUser(loginUserDTO,
                Objects.equals(loginUserDTO.getIsLock(), 0),
                true,
                true,
                Objects.equals(loginUserDTO.getIsLock(), 0),
                authorities);
    }

    private void getAuthorityList(Set<String> auth, List<GrantedAuthority> authorities) {
        authorities.addAll(AuthorityUtils.createAuthorityList(auth.toArray(new String[0])));
    }

}
