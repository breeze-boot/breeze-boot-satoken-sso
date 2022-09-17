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

package com.breeze.boot.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.admin.dto.UserDTO;
import com.breeze.boot.admin.dto.UserOpenDTO;
import com.breeze.boot.admin.entity.SysDeptEntity;
import com.breeze.boot.admin.entity.SysRoleEntity;
import com.breeze.boot.admin.entity.SysRoleUserEntity;
import com.breeze.boot.admin.entity.SysUserEntity;
import com.breeze.boot.admin.mapper.SysUserMapper;
import com.breeze.boot.admin.service.*;
import com.breeze.boot.core.Result;
import com.breeze.boot.jwtlogin.entity.LoginUserDTO;
import com.breeze.boot.jwtlogin.entity.UserRoleDTO;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

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
    private SysRoleUserService sysRoleUserService;

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
        SysUserEntity sysUserEntity = this.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getUsername, username));
        if (Objects.isNull(sysUserEntity)) {
            throw new RuntimeException("用户名错误或不存在");
        }
        return getLoginUserDTO(sysUserEntity);
    }

    @Override
    public LoginUserDTO loadUserByEmail(String email) {
        SysUserEntity sysUserEntity = this.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getEmail, email));
        if (Objects.isNull(sysUserEntity)) {
            throw new RuntimeException("此邮箱未注册");
        }
        return getLoginUserDTO(sysUserEntity);
    }

    @Override
    public LoginUserDTO loadUserByPhone(String phone) {
        SysUserEntity sysUserEntity = this.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getPhone, phone));
        if (Objects.isNull(sysUserEntity)) {
            throw new RuntimeException("此手机号未注册");
        }
        return getLoginUserDTO(sysUserEntity);
    }

    /**
     * 列表页面
     *
     * @param userDTO 用户dto
     * @return {@link Page}<{@link SysUserEntity}>
     */
    @Override
    public IPage<SysUserEntity> listPage(UserDTO userDTO) {
        return this.baseMapper.listPage(new Page<>(userDTO.getCurrent(), userDTO.getSize()), userDTO);
    }

    /**
     * 保存用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Boolean saveUser(SysUserEntity sysUser) {
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        return this.save(sysUser);
    }

    /**
     * 更新用户ById
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateUserById(SysUserEntity sysUser) {
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
        return this.update(Wrappers.<SysUserEntity>lambdaUpdate()
                .set(SysUserEntity::getIsLock, openDTO.getIsLock())
                .eq(SysUserEntity::getId, openDTO.getId()));
    }

    /**
     * 重置密码
     *
     * @param userEntity 用户实体
     * @return {@link Boolean}
     */
    @Override
    public Boolean resetPass(SysUserEntity userEntity) {
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        return this.update(Wrappers.<SysUserEntity>lambdaUpdate().set(SysUserEntity::getPassword, userEntity.getPassword()).eq(SysUserEntity::getId, userEntity.getId()));
    }

    /**
     * 删除由ids
     *
     * @param ids id
     * @return {@link Result}
     */
    @Override
    public Result deleteByIds(List<Long> ids) {
        List<SysUserEntity> userEntityList = this.listByIds(ids);
        if (CollUtil.isEmpty(userEntityList)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        boolean remove = this.removeByIds(ids);
        if (remove) {
            // 删除用户角色关系
            this.sysRoleUserService.remove(Wrappers.<SysRoleUserEntity>lambdaQuery()
                    .eq(SysRoleUserEntity::getUserId, userEntityList.stream().map(SysUserEntity::getId).collect(Collectors.toList())));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 获取登录用户dto
     *
     * @param sysUserEntity 系统用户实体
     * @return {@link LoginUserDTO}
     */
    private LoginUserDTO getLoginUserDTO(SysUserEntity sysUserEntity) {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtil.copyProperties(sysUserEntity, loginUserDTO);
        SysDeptEntity dept = this.sysDeptService.getById(sysUserEntity.getDeptId());
        loginUserDTO.setDeptName(dept.getDeptName());
        List<SysRoleEntity> roleList = this.sysRoleService.listUserRole(sysUserEntity.getId());
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
        loginUserDTO.setUserRoleIds(roleList.stream().map(SysRoleEntity::getId).collect(Collectors.toList()));
        loginUserDTO.setUserRoleCodes(roleList.stream().map(SysRoleEntity::getRoleCode).collect(Collectors.toList()));
        List<String> permissionList = Optional.ofNullable(this.sysMenuService.listUserMenuPermission(roleList)).orElseGet(ArrayList::new);
        loginUserDTO.setAuthorities(permissionList);
        return loginUserDTO;
    }

}

