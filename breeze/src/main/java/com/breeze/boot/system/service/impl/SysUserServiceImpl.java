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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.Result;
import com.breeze.boot.security.entity.LoginUserDTO;
import com.breeze.boot.security.entity.UserRoleDTO;
import com.breeze.boot.security.utils.SecurityUtils;
import com.breeze.boot.system.domain.SysDept;
import com.breeze.boot.system.domain.SysRole;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.domain.SysUserRole;
import com.breeze.boot.system.dto.UserDTO;
import com.breeze.boot.system.dto.UserOpenDTO;
import com.breeze.boot.system.mapper.SysUserMapper;
import com.breeze.boot.system.service.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统用户服务impl
 *
 * @author breeze
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
     * 系统角色服务
     */
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 系统菜单服务
     */
    @Autowired
    private SysMenuService sysMenuService;

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
     * 刷新用户
     *
     * @param username 用户名
     * @return {@link LoginUserDTO}
     */
    @Override
    @CachePut(cacheNames = "sys:login_user", key = "#username")
    public LoginUserDTO refreshUser(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名错误或不存在");
        }
        return this.getLoginUserDTO(sysUser);
    }

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
        if (update) {
            // 刷新菜单权限
            this.userTokenService.refreshUser(SecurityUtils.getUsername());
        }
        return update;
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
     * @param sysUser 用户实体
     * @return {@link Boolean}
     */
    @Override
    public Boolean resetPass(SysUser sysUser) {
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        boolean update = this.update(Wrappers.<SysUser>lambdaUpdate().set(SysUser::getPassword, sysUser.getPassword()).eq(SysUser::getUsername, sysUser.getUsername()));
        if (update) {
            // 刷新菜单权限
            this.userTokenService.refreshUser(SecurityUtils.getUsername());
        }
        return update;
    }

    /**
     * 按用户名名单删除
     * 删除由ids
     *
     * @param usernameList 用户列表
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @CacheEvict(cacheNames = "sys:login_user", key = "#usernameList")
    public Result<Boolean> deleteByUsernameList(List<String> usernameList) {
        List<SysUser> sysUserList = this.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getUsername, usernameList));
        if (CollUtil.isEmpty(sysUserList)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        boolean remove = this.remove(Wrappers.<SysUser>lambdaQuery().in(SysUser::getUsername, usernameList));
        if (remove) {
            // 删除用户角色关系
            this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                    .in(SysUserRole::getUserId, sysUserList.stream().map(SysUser::getId).collect(Collectors.toList())));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 获取登录用户DTO
     *
     * @param sysUser 系统用户实体
     * @return {@link LoginUserDTO}
     */
    @Override
    @Cacheable(cacheNames = "sys:login_user", key = "#sysUser.username")
    public LoginUserDTO getLoginUserDTO(SysUser sysUser) {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtil.copyProperties(sysUser, loginUserDTO);
        SysDept dept = this.sysDeptService.getById(sysUser.getDeptId());
        // TODO
        loginUserDTO.setDeptName(dept.getDeptName());
        List<SysRole> roleList = this.sysRoleService.listUserRole(sysUser.getId());
        if (CollUtil.isEmpty(roleList)) {
            loginUserDTO.setAuthorities(Lists.newArrayList());
            return loginUserDTO;
        }
        List<UserRoleDTO> roleDTOList = roleList.stream().map(sysRoleEntity -> {
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            BeanUtil.copyProperties(roleList, userRoleDTO);
            userRoleDTO.setRoleId(sysRoleEntity.getId());
            return userRoleDTO;
        }).collect(Collectors.toList());
        loginUserDTO.setUserRoleDTOList(roleDTOList);
        loginUserDTO.setUserRoleIds(roleList.stream().map(SysRole::getId).collect(Collectors.toList()));
        loginUserDTO.setUserRoleCodes(roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
        List<String> permissionList = Optional.ofNullable(this.sysMenuService.listUserMenuPermission(roleList)).orElseGet(ArrayList::new);
        loginUserDTO.setAuthorities(permissionList);
        return loginUserDTO;
    }

}

