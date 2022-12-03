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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.breeze.boot.security.entity.LoginUserDTO;
import com.breeze.boot.security.entity.UserRoleDTO;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.service.SysDeptService;
import com.breeze.boot.system.service.SysMenuService;
import com.breeze.boot.system.service.SysRoleDataPermissionService;
import com.breeze.boot.system.service.SysRoleService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
     * 获取登录用户DTO
     *
     * @param sysUser 系统用户实体
     * @return {@link LoginUserDTO}
     */
    public LoginUserDTO getLoginUserDTO(SysUser sysUser) {
        LoginUserDTO loginUser = new LoginUserDTO();
        BeanUtil.copyProperties(sysUser, loginUser);
        // 获取部门名称
        Optional.ofNullable(this.sysDeptService.getById(sysUser.getDeptId())).ifPresent(sysDept -> loginUser.setDeptName(sysDept.getDeptName()));
        // 查询 用户的角色
        Set<UserRoleDTO> roleDtoSet = this.sysRoleService.listRoleByUserId(sysUser.getId());
        if (CollUtil.isEmpty(roleDtoSet)) {
            loginUser.setAuthorities(Sets.newHashSet());
            return loginUser;
        }

        loginUser.setUserRoleList(roleDtoSet);
        // 角色ID
        Set<Long> roleIdSet = roleDtoSet.stream().map(UserRoleDTO::getRoleId).collect(Collectors.toSet());
        loginUser.setUserRoleIds(roleIdSet);
        // 角色CODE
        Set<String> roleCodeList = roleDtoSet.stream().map(UserRoleDTO::getRoleCode).collect(Collectors.toSet());
        loginUser.setUserRoleCodes(roleCodeList);
        // 角色权限
        loginUser.setAuthorities(this.sysMenuService.listUserMenuPermission(roleDtoSet));
        // 用户的多个数据权限
        loginUser.setPermissions(this.sysRoleDataPermissionService.listRoleDataPermissionByRoleIds(roleIdSet));
        this.redisTemplate.opsForValue().set("sys:login_user:" + sysUser.getUsername(), loginUser);
        return loginUser;
    }

}
