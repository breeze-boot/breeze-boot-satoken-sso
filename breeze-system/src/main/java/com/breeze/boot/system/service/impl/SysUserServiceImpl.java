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
import com.breeze.boot.jwtlogin.entity.LoginUserDTO;
import com.breeze.boot.jwtlogin.entity.UserRoleDTO;
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
     * 负载登录用户名
     *
     * @param username 用户名
     * @return {@link LoginUserDTO}
     */
    @Override
    public LoginUserDTO loadUserByUsername(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("用户名错误或不存在");
        }
        return getLoginUserDTO(sysUser);
    }

    @Override
    public LoginUserDTO loadUserByEmail(String email) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, email));
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("此邮箱未注册");
        }
        return getLoginUserDTO(sysUser);
    }

    @Override
    public LoginUserDTO loadUserByPhone(String phone) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone));
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("此手机号未注册");
        }
        return getLoginUserDTO(sysUser);
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
    public Result<Object> saveUser(SysUser sysUser) {
        SysDept sysDept = this.sysDeptService.getById(sysUser.getDeptId());
        if (Objects.isNull(sysDept)) {
            return Result.fail("部门不存在");
        }
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        return Result.ok(this.save(sysUser));
    }

    /**
     * 更新用户ById
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateUserById(SysUser sysUser) {
        return this.updateById(sysUser);
    }

    /**
     * 开启关闭锁定
     *
     * @param openDTO 打开dto
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(UserOpenDTO openDTO) {
        return this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getIsLock, openDTO.getIsLock())
                .eq(SysUser::getId, openDTO.getId()));
    }

    /**
     * 重置密码
     *
     * @param userEntity 用户实体
     * @return {@link Boolean}
     */
    @Override
    public Boolean resetPass(SysUser userEntity) {
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        return this.update(Wrappers.<SysUser>lambdaUpdate().set(SysUser::getPassword, userEntity.getPassword()).eq(SysUser::getId, userEntity.getId()));
    }

    /**
     * 删除由ids
     *
     * @param ids id
     * @return {@link Result}
     */
    @Override
    public Result deleteByIds(List<Long> ids) {
        List<SysUser> userEntityList = this.listByIds(ids);
        if (CollUtil.isEmpty(userEntityList)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        boolean remove = this.removeByIds(ids);
        if (remove) {
            // 删除用户角色关系
            this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                    .in(SysUserRole::getUserId, userEntityList.stream().map(SysUser::getId).collect(Collectors.toList())));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 获取登录用户dto
     *
     * @param sysUser 系统用户实体
     * @return {@link LoginUserDTO}
     */
    private LoginUserDTO getLoginUserDTO(SysUser sysUser) {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtil.copyProperties(sysUser, loginUserDTO);
        SysDept dept = this.sysDeptService.getById(sysUser.getDeptId());
        // TOOD
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

