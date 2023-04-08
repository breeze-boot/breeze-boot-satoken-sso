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

package com.breeze.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.breeze.boot.sys.domain.SysUser;
import com.breeze.boot.sys.service.SysDeptService;
import com.breeze.boot.sys.service.SysMenuService;
import com.breeze.boot.sys.service.SysRoleDataPermissionService;
import com.breeze.boot.sys.service.SysRoleService;
import com.breeze.security.userextension.LoginUser;
import com.breeze.security.userextension.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.breeze.core.constants.CacheConstants.LOGIN_USER;

/**
 * 用户令牌服务impl
 *
 * @author gaoweixuan
 * @date 2022-11-11
 */
@Service
public class UserTokenCacheService {

    /**
     * 系统角色服务
     */
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 密码编码器
     */
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 系统角色数据权限服务
     */
    @Autowired
    private SysRoleDataPermissionService sysRoleDataPermissionService;

    /**
     * 系统部门服务
     */
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 缓存
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取登录用户
     *
     * @param sysUser 系统用户实体
     * @return {@link LoginUser}
     */
    public LoginUser getLoginUser(SysUser sysUser) {
        LoginUser loginUser = new LoginUser();
        BeanUtil.copyProperties(sysUser, loginUser);
        // 获取部门名称
        Optional.ofNullable(this.sysDeptService.getById(sysUser.getDeptId())).ifPresent(sysDept -> loginUser.setDeptName(sysDept.getDeptName()));
        // 查询 用户的角色
        Set<UserRole> userRoleSet = this.sysRoleService.listRoleByUserId(sysUser.getId());
        if (CollUtil.isNotEmpty(userRoleSet)) {
            loginUser.setAuthorities(this.sysMenuService.listUserMenuPermission(userRoleSet));
            loginUser.setUserRoleList(userRoleSet);
            // 角色CODE
            loginUser.setUserRoleCodes(userRoleSet.stream().map(UserRole::getRoleCode).collect(Collectors.toSet()));
            // 角色ID
            Set<Long> roleIdSet = userRoleSet.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
            loginUser.setUserRoleIds(roleIdSet);
            // 用户的多个数据权限
            loginUser.setDataPermissions(this.sysRoleDataPermissionService.listRoleDataPermissionByRoleIds(roleIdSet));
        }
        // 角色权限
        this.redisTemplate.opsForValue().set(LOGIN_USER + sysUser.getUsername(), loginUser, 24L, TimeUnit.HOURS);
        return loginUser;
    }

    public Boolean clearUserInfo(String username, HttpServletRequest request) {
        // String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 使用的无状态的JWT，使用黑名单机制退出登录
        // this.redisTemplate.opsForValue().set(BLACK_JWT + username, authorization,36000L);
        return this.redisTemplate.delete(LOGIN_USER + username);
    }
}
