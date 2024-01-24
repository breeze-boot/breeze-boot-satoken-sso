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

package com.breeze.boot.modules.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.base.BaseLoginUser;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.EasyExcelExport;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.domain.SysDept;
import com.breeze.boot.modules.auth.domain.SysRole;
import com.breeze.boot.modules.auth.domain.SysUser;
import com.breeze.boot.modules.auth.domain.SysUserRole;
import com.breeze.boot.modules.auth.service.*;
import com.breeze.boot.modules.system.domain.*;
import com.breeze.boot.modules.auth.domain.dto.UserRole;
import com.breeze.boot.modules.auth.domain.params.UserOpenParam;
import com.breeze.boot.modules.auth.domain.params.UserResetParam;
import com.breeze.boot.modules.auth.domain.params.UserRolesParam;
import com.breeze.boot.modules.auth.domain.query.UserQuery;
import com.breeze.boot.modules.auth.mapper.SysUserMapper;
import com.breeze.boot.modules.system.service.*;
import com.breeze.boot.security.exception.AccessException;
import com.breeze.boot.security.params.AuthLoginParam;
import com.breeze.boot.security.params.WxLoginParam;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CacheConstants.LOGIN_USER;

/**
 * 系统用户服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 系统角色服务
     */
    private final SysRoleService sysRoleService;

    /**
     * 系统用户角色服务
     */
    private final SysUserRoleService sysUserRoleService;

    /**
     * 系统文件服务
     */
    private final SysFileService sysFileService;

    /**
     * 系统部门服务
     */
    private final SysDeptService sysDeptService;

    /**
     * 系统岗位服务
     */
    private final SysPostService sysPostService;

    /**
     * 密码编码器
     */
    private final SysMenuService sysMenuService;

    /**
     * 系统角色数据权限服务
     */
    private final SysRolePermissionService sysRolePermissionService;

    /**
     * 缓存
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 列表页面
     *
     * @param userQuery 用户查询
     * @return {@link IPage}<{@link SysUser}>
     */
    @Override
    public IPage<SysUser> listPage(UserQuery userQuery) {
        return this.baseMapper.listPage(new Page<>(userQuery.getCurrent(), userQuery.getSize()), userQuery);
    }

    /**
     * 保存用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Result<Boolean> saveUser(SysUser sysUser) {
        if (Objects.isNull(this.sysDeptService.getById(sysUser.getDeptId()))) {
            return Result.fail("部门不存在");
        }
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        boolean save = this.save(sysUser);
        if (save) {
            this.saveUserRole(sysUser);
        }
        return Result.ok();
    }

    /**
     * 通过Id更新用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateUserById(SysUser sysUser) {
        boolean update = this.updateById(sysUser);
        this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
        if (update) {
            this.saveUserRole(sysUser);
        }
        return update;
    }

    /**
     * 保存用户角色
     *
     * @param sysUser 系统用户
     */
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
     * @param userOpenParam 用户打开参数
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(UserOpenParam userOpenParam) {
        boolean update = this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getIsLock, userOpenParam.getIsLock())
                .eq(SysUser::getUsername, userOpenParam.getUsername()));
        return update;
    }

    /**
     * 重置密码
     *
     * @param userResetParam 用户重置密码参数
     * @return {@link Boolean}
     */
    @Override
    public Boolean reset(UserResetParam userResetParam) {
        userResetParam.setPassword(this.passwordEncoder.encode(userResetParam.getPassword()));
        return this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, userResetParam.getPassword()).eq(SysUser::getId, userResetParam.getId()));
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @CacheEvict(cacheNames = LOGIN_USER, key = "#sysUser.username")
    public Result<Boolean> removeUser(SysUser sysUser) {
        boolean remove = this.remove(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUser.getId()));
        if (remove) {
            // 删除用户角色关系
            this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                    .in(SysUserRole::getUserId, sysUser.getId()));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 用户添加角色
     *
     * @param userRolesParam 用户角色参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> setRole(UserRolesParam userRolesParam) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, userRolesParam.getUsername()));
        if (Objects.isNull(sysUser)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
        List<SysUserRole> collect = userRolesParam.getRoleId().stream().map(roleId ->
                SysUserRole.builder().roleId(roleId).userId(sysUser.getId()).build()
        ).collect(Collectors.toList());
        this.sysUserRoleService.saveBatch(collect);
        return Result.ok(Boolean.TRUE, "分配成功");
    }

    /**
     * 通过ID查询用户
     *
     * @param id id
     * @return {@link Result}<{@link SysUser}>
     */
    @Override
    public Result<SysUser> getUserById(Long id) {
        SysUser sysUser = this.getById(id);
        if (Objects.isNull(sysUser)) {
            return Result.fail("用户不存在");
        }
        List<SysRole> roleList = this.sysUserRoleService.getSysRoleByUserId(sysUser.getId());
        sysUser.setRoleNames(roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList()));
        sysUser.setRoleIds(roleList.stream().map(SysRole::getId).collect(Collectors.toList()));
        sysUser.setDeptName(Optional.ofNullable(this.sysDeptService.getById(sysUser.getDeptId())).orElseGet(SysDept::new).getDeptName());
        sysUser.setPostName(Optional.ofNullable(this.sysPostService.getById(sysUser.getPostId())).orElseGet(SysPost::new).getPostName());
        sysUser.setAvatar(this.sysFileService.preview(sysUser.getAvatarFileId()));
        sysUser.setSysRoles(roleList);
        return Result.ok(sysUser);
    }

    /**
     * 注册用户
     *
     * @param registerUser 注册用户
     * @param roleCode     角色编码
     * @return {@link SysUser}
     */
    @Override
    public SysUser registerUser(SysUser registerUser, String roleCode) {
        SysUser sysUser = SysUser.builder()
                .username(registerUser.getUsername())
                .amountName(registerUser.getUsername())
                .password(this.passwordEncoder.encode("123456"))
                .openId(registerUser.getOpenId())
                .phone(registerUser.getPhone())
                .tenantId(registerUser.getTenantId())
                .deptId(1L).build();
        this.save(sysUser);
        // 给用户赋予一个临时角色，临时角色指定接口的权限
        SysRole sysRole = this.sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode, roleCode));
        if (Objects.isNull(sysRole)) {
            throw new AccessException(ResultCode.FORBIDDEN);
        }
        this.sysUserRoleService.save(SysUserRole.builder().userId(sysUser.getId()).roleId(sysRole.getId()).build());
        return sysUser;
    }

    /**
     * 查询用户通过部门id
     *
     * @param deptIds 部门IDS
     * @return {@link List}<{@link SysUser}>
     */
    @Override
    public List<SysUser> listUserByDeptId(List<Long> deptIds) {
        return this.baseMapper.listUserByDeptId(deptIds);
    }

    /**
     * 导出
     *
     * @param response 响应
     */
    @Override
    public void export(HttpServletResponse response) {
        List<SysUser> userList = this.baseMapper.listAllUser();
        try {
            EasyExcelExport.export(response, "用户数据", "用户数据", userList, SysUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载用户通过用户名
     *
     * @param username 用户名
     * @return {@link Result}<{@link BaseLoginUser}>
     */
    @Override
    public Result<BaseLoginUser> loadUserByUsername(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            return Result.fail("未获取到用户");
        }
        return Result.ok(this.buildLoginUserInfo(sysUser));
    }

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link BaseLoginUser}
     */
    @Override
    public Result<BaseLoginUser> loadUserByPhone(String phone) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone));
        if (Objects.isNull(sysUser)) {
            return Result.fail("未获取到用户");
        }
        return Result.ok(this.buildLoginUserInfo(sysUser));

    }

    /**
     * 加载用户通过电子邮件
     *
     * @param email 电子邮件
     * @return {@link BaseLoginUser}
     */
    @Override
    public Result<BaseLoginUser> loadUserByEmail(String email) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, email));
        if (Objects.isNull(sysUser)) {
            return Result.fail("未获取到用户");
        }
        return Result.ok(this.buildLoginUserInfo(sysUser));
    }

    @Override
    public Result<BaseLoginUser> loadRegisterUserByOpenId(WxLoginParam wxLoginParam) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, wxLoginParam.getOpenId()));
        if (Objects.isNull(sysUser)) {
            this.registerUser(SysUser.builder()
                    .phone(wxLoginParam.getPhone())
                    .amountName(wxLoginParam.getNickName() + RandomUtil.randomString(6))
                    .sex(wxLoginParam.getSex())
                    .email(wxLoginParam.getEmail())
                    .tenantId(wxLoginParam.getTenantId())
                    .username(wxLoginParam.getOpenId())
                    .deptId(1L).build(), "ROLE_MINI");
        }
        return Result.ok(this.buildLoginUserInfo(sysUser));
    }

    /**
     * 加载用户通过电话
     *
     * @param authLoginParam auth三方登录消息体
     * @return {@link Result}<{@link BaseLoginUser}>
     */
    @Override
    public Result<BaseLoginUser> loadRegisterUserByPhone(AuthLoginParam authLoginParam) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, authLoginParam.getPhone()));
        if (Objects.isNull(sysUser)) {
            // 不存在就去创建
            sysUser = this.registerUser(SysUser.builder()
                    .phone(authLoginParam.getPhone())
                    .amountName(authLoginParam.getAppUserName())
                    .sex(authLoginParam.getSex())
                    .email(authLoginParam.getEmail())
                    .tenantId(authLoginParam.getTenantId())
                    .username(authLoginParam.getAppUserName() + RandomUtil.randomString(5))
                    .deptId(1L).build(), "ROLE_AUTH");
        }
        return Result.ok(this.buildLoginUserInfo(sysUser));
    }

    /**
     * 加载登录用户
     *
     * @param sysUser 系统用户实体
     * @return {@link BaseLoginUser}
     */
    public BaseLoginUser buildLoginUserInfo(SysUser sysUser) {
        BaseLoginUser baseLoginUser = new BaseLoginUser();
        BeanUtil.copyProperties(sysUser, baseLoginUser);
        // 获取部门名称
        Optional.ofNullable(this.sysDeptService.getById(sysUser.getDeptId())).ifPresent(sysDept -> baseLoginUser.setDeptName(sysDept.getDeptName()));
        // 查询 用户的角色
        Set<UserRole> userRoleSet = this.sysRoleService.listRoleByUserId(sysUser.getId());
        if (CollUtil.isNotEmpty(userRoleSet)) {
            baseLoginUser.setAuthorities(this.sysMenuService.listUserMenuPermission(userRoleSet));
            // 角色CODE
            baseLoginUser.setUserRoleCodes(userRoleSet.stream().map(UserRole::getRoleCode).collect(Collectors.toSet()));
            // 角色ID
            Set<Long> roleIdSet = userRoleSet.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
            baseLoginUser.setUserRoleIds(roleIdSet);
            // 用户的多个数据权限
            List<SysPermission> permissionList = this.sysRolePermissionService.listRolePermissionByRoleIds(roleIdSet);
            if (CollUtil.isNotEmpty(permissionList)) {
                baseLoginUser.setPermissions(permissionList.stream().map(SysPermission::getPermissions).collect(Collectors.joining(", ")));
            }
        }
        // 保存redis
        this.redisTemplate.opsForValue().set(LOGIN_USER + sysUser.getUsername(), baseLoginUser, 24L, TimeUnit.HOURS);
        return baseLoginUser;
    }
}

