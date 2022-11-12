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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.Result;
import com.breeze.boot.security.utils.SecurityUtils;
import com.breeze.boot.system.domain.SysDept;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.domain.SysUserRole;
import com.breeze.boot.system.dto.UserDTO;
import com.breeze.boot.system.dto.UserOpenDTO;
import com.breeze.boot.system.dto.UserResetPasswordDTO;
import com.breeze.boot.system.dto.UserRolesDTO;
import com.breeze.boot.system.mapper.SysUserMapper;
import com.breeze.boot.system.service.SysDeptService;
import com.breeze.boot.system.service.SysUserRoleService;
import com.breeze.boot.system.service.SysUserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统用户服务impl
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 密码编码器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 系统用户角色服务
     */
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 系统部门服务
     */
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 用户token服务
     */
    @Autowired
    private UserTokenService userTokenService;

    /**
     * 列表页面
     *
     * @param userDTO 用户dto
     * @return {@link Page}<{@link SysUser}>
     */
    @Override
    public IPage<SysUser> listPage(UserDTO userDTO) {
        return this.baseMapper.listPage(new Page<>(userDTO.getCurrent(), userDTO.getSize()), userDTO);
    }

    /**
     * 保存用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Result<Boolean> saveUser(SysUser sysUser) {
        SysDept sysDept = this.sysDeptService.getById(sysUser.getDeptId());
        if (Objects.isNull(sysDept)) {
            return Result.fail("部门不存在");
        }
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        saveUserRole(sysUser);
        boolean save = this.save(sysUser);
        if (save) {
            // 刷新菜单权限
            this.userTokenService.refreshUser(SecurityUtils.getUsername());
        }
        return Result.ok();
    }

    /**
     * 更新用户ById
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateUserById(SysUser sysUser) {
        boolean update = this.updateById(sysUser);
        this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
        this.saveUserRole(sysUser);
        if (update) {
            // 刷新菜单权限
            this.userTokenService.refreshUser(SecurityUtils.getUsername());
        }
        return update;
    }

    private void saveUserRole(SysUser sysUser) {
        List<SysUserRole> userRoleList = Optional.ofNullable(sysUser.getRoleIds())
                .orElseGet(Lists::newArrayList).stream().map(id -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(id);
                    return sysUserRole;
                }).collect(Collectors.toList());
        this.sysUserRoleService.saveBatch(userRoleList);
    }

    /**
     * 开启关闭锁定
     *
     * @param userOpen 用户开关 DTO
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(UserOpenDTO userOpen) {
        boolean update = this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getIsLock, userOpen.getIsLock())
                .eq(SysUser::getUsername, userOpen.getUsername()));
        if (update) {
            // 刷新菜单权限
            this.userTokenService.refreshUser(userOpen.getUsername());
        }
        return update;
    }

    /**
     * 重置密码
     *
     * @param userResetPasswordDTO 用户重置密码dto
     * @return {@link Boolean}
     */
    @Override
    public Boolean resetPass(UserResetPasswordDTO userResetPasswordDTO) {
        userResetPasswordDTO.setPassword(this.passwordEncoder.encode(userResetPasswordDTO.getPassword()));
        boolean update = this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, userResetPasswordDTO.getPassword()).eq(SysUser::getId, userResetPasswordDTO.getId()));
        if (update) {
            // 刷新菜单权限
            this.userTokenService.refreshUser(SecurityUtils.getUsername());
        }
        return update;
    }

    /**
     * 按用户名名单删除
     *
     * @param sysUser 用户
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @CacheEvict(cacheNames = "sys:login_user", key = "#sysUser.username")
    public Result<Boolean> removeUser(SysUser sysUser) {
        boolean remove = this.remove(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUser.getId()));
        if (remove) {
            // 删除用户角色关系
            this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                    .in(SysUserRole::getUserId, sysUser.getId()));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    @Override
    public Result<Boolean> userAddRole(UserRolesDTO userRolesDTO) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, userRolesDTO.getUsername()));
        if (Objects.isNull(sysUser)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
        List<SysUserRole> collect = userRolesDTO.getRoleId().stream().map(roleId ->
                SysUserRole.builder().roleId(roleId).userId(sysUser.getId()).build()
        ).collect(Collectors.toList());
        this.sysUserRoleService.saveBatch(collect);
        return Result.ok(Boolean.TRUE, "分配成功");
    }

    @Override
    public Result<SysUser> getUserById(Long id) {
        SysUser sysUser = this.getById(id);
        if (Objects.isNull(sysUser)) {
            return Result.fail("用户不存在");
        }
        sysUser.setSysRoles(this.sysUserRoleService.getSysRoleByUserId(sysUser.getId()));
        return Result.ok(sysUser);
    }

}

