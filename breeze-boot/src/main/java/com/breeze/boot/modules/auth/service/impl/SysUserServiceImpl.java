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

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.core.enums.DataPermissionType;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.jackson.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.AesUtil;
import com.breeze.boot.core.utils.EasyExcelExport;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysUserMapper;
import com.breeze.boot.modules.auth.model.bo.FlowUserBO;
import com.breeze.boot.modules.auth.model.bo.SysDeptBO;
import com.breeze.boot.modules.auth.model.bo.UserBO;
import com.breeze.boot.modules.auth.model.bo.UserRoleBO;
import com.breeze.boot.modules.auth.model.entity.*;
import com.breeze.boot.modules.auth.model.excel.UserExcel;
import com.breeze.boot.modules.auth.model.form.UserForm;
import com.breeze.boot.modules.auth.model.form.UserOpenForm;
import com.breeze.boot.modules.auth.model.form.UserResetForm;
import com.breeze.boot.modules.auth.model.form.UserRolesForm;
import com.breeze.boot.modules.auth.model.mappers.SysUserMapStruct;
import com.breeze.boot.modules.auth.model.query.UserQuery;
import com.breeze.boot.modules.auth.model.vo.UserVO;
import com.breeze.boot.modules.auth.service.*;
import com.breeze.boot.modules.system.service.SysFileService;
import com.breeze.boot.sso.model.UserInfoDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

import static com.breeze.boot.core.enums.ResultCode.FAIL;

/**
 * 系统用户服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapStruct sysUserMapStruct;

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
     * 系统角色行数据权限服务
     */
    private final SysRoleRowPermissionService sysRoleRowPermissionService;

    /**
     * 列表页面
     *
     * @param userQuery 用户查询
     * @return {@link Page}<{@link UserVO}>
     */
    @Override
    public Page<UserVO> listPage(UserQuery userQuery) {
        Page<UserBO> userBOPage = this.baseMapper.listPage(new Page<>(userQuery.getCurrent(), userQuery.getSize()), userQuery);
        return this.sysUserMapStruct.pageBO2PageVO(userBOPage);
    }

    /**
     * 通过ID查询用户
     *
     * @param id id
     * @return {@link UserVO }
     */
    @Override
    public UserVO getInfoById(Long id) {
        SysUser sysUser = this.getById(id);
        UserVO userVO = this.sysUserMapStruct.entity2VO(sysUser);
        if (Objects.isNull(sysUser)) {
            throw new BreezeBizException(FAIL);
        }
        List<SysRole> roleList = this.sysUserRoleService.getSysRoleByUserId(sysUser.getId());
        userVO.setRoleNames(roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList()));
        userVO.setRoleIds(roleList.stream().map(SysRole::getId).collect(Collectors.toList()));
        userVO.setDeptName(Optional.ofNullable(this.sysDeptService.getById(userVO.getDeptId())).orElseGet(SysDept::new).getDeptName());
        userVO.setPostName(Optional.ofNullable(this.sysPostService.getById(sysUser.getPostId())).orElseGet(SysPost::new).getPostName());
        if (StrUtil.isBlank(sysUser.getAvatar())) {
            userVO.setAvatar(this.sysFileService.preview(sysUser.getAvatarFileId()));
        }
        userVO.setSysRoles(roleList);
        return userVO;
    }

    /**
     * 保存用户
     *
     * @param userForm 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Result<Boolean> saveUser(UserForm userForm) {
        if (Objects.isNull(this.sysDeptService.getById(userForm.getDeptId()))) {
            return Result.fail("部门不存在");
        }
        userForm.setPassword(BCrypt.hashpw(userForm.getPassword(), BCrypt.gensalt()));
        SysUser sysUser = sysUserMapStruct.form2Entity(userForm);
        boolean save = this.save(sysUser);
        if (save) return Result.ok(this.saveUserRole(userForm, sysUser.getId()));
        return Result.fail("创建失败");
    }

    /**
     * 通过Id更新用户
     *
     * @param userForm 用户表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean modifyUser(Long id, UserForm userForm) {
        SysUser sysUser = sysUserMapStruct.form2Entity(userForm);
        boolean update = this.updateById(sysUser);
        this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
        if (update) {
            this.saveUserRole(userForm, id);
        }
        return update;
    }

    /**
     * 保存用户角色
     *
     * @param userForm 用户表单
     * @param id       userId
     * @return boolean
     */
    private boolean saveUserRole(UserForm userForm, Long id) {
        List<SysUserRole> userRoleList = Optional.ofNullable(userForm.getRoleIds()).orElseGet(Lists::newArrayList).stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(id);
            sysUserRole.setRoleId(roleId);
            return sysUserRole;
        }).collect(Collectors.toList());
        return this.sysUserRoleService.saveBatch(userRoleList);
    }

    /**
     * 开启关闭锁定
     *
     * @param userOpenForm 用户打开表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(UserOpenForm userOpenForm) {
        return this.update(Wrappers.<SysUser>lambdaUpdate().set(SysUser::getIsLock, userOpenForm.getIsLock()).eq(SysUser::getUsername, userOpenForm.getUsername()));
    }

    /**
     * 重置密码
     *
     * @param userResetForm 用户重置密码表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean reset(UserResetForm userResetForm) {
        AesSecretProperties aesSecretProperties = SpringUtil.getBean(AesSecretProperties.class);
        userResetForm.setPassword(BCrypt.hashpw(AesUtil.decryptStr(userResetForm.getPassword(), aesSecretProperties.getAesSecret()), BCrypt.gensalt()));
        return this.update(Wrappers.<SysUser>lambdaUpdate().set(SysUser::getPassword, userResetForm.getPassword()).eq(SysUser::getId, userResetForm.getId()));
    }

    /**
     * 删除用户
     *
     * @param ids 用户
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removeUser(List<Long> ids) {
        List<SysUser> sysUserList = this.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, ids));
        if (CollUtil.isEmpty(sysUserList)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        for (SysUser sysUser : sysUserList) {
            this.removeUser(sysUser);
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    public void removeUser(SysUser sysUser) {
        boolean remove = this.remove(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUser.getId()));
        if (remove) {
            // 删除用户角色关系
            this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, sysUser.getId()));
        }
    }

    /**
     * 用户添加角色
     *
     * @param userRolesForm 用户角色表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> setRole(UserRolesForm userRolesForm) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, userRolesForm.getUserId()));
        if (Objects.isNull(sysUser)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
        List<SysUserRole> collect = userRolesForm.getRoleIds().stream().map(roleId -> SysUserRole.builder().roleId(roleId).userId(sysUser.getId()).build()).collect(Collectors.toList());
        this.sysUserRoleService.saveBatch(collect);
        return Result.ok(Boolean.TRUE, "分配成功");
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
        SysUser sysUser = SysUser.builder().username(registerUser.getUsername()).displayName(registerUser.getUsername()).password(("123456")).openId(registerUser.getOpenId()).phone(registerUser.getPhone()).tenantId(registerUser.getTenantId()).deptId(1L).build();
        this.save(sysUser);
        // 给用户赋予一个临时角色，临时角色指定接口的权限
        SysRole sysRole = this.sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode, roleCode));
        if (Objects.isNull(sysRole)) {
            throw new BreezeBizException(ResultCode.SC_FORBIDDEN);
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
    public List<UserVO> listUserByDeptId(List<Long> deptIds) {
        List<UserBO> userBOList = this.baseMapper.listUserByDeptId(deptIds);
        return this.sysUserMapStruct.boList2VOList(userBOList);
    }

    /**
     * 导出
     *
     * @param userQuery 用户查询
     * @param response  响应
     */
    @Override
    public void export(UserQuery userQuery, HttpServletResponse response) {
        Page<UserVO> userList = this.listPage(userQuery);
        List<UserExcel> userExcels = this.sysUserMapStruct.vo2Excel(userList.getRecords());
        try {
            EasyExcelExport.export(response, "用户数据", "用户数据", userExcels, UserExcel.class);
        } catch (Exception e) {
            log.error("导出用户数据失败", e);
        }
    }

    /**
     * 查询部门用户
     *
     * @param deptId 部门ID
     * @return {@link List}<{@link SysUser}>
     */
    @Override
    public List<SysUser> listDeptsUser(Long deptId) {
        List<Long> deptIdList = this.sysDeptService.listDeptByParentId(deptId);
        if (CollUtil.isEmpty(deptIdList)) {
            throw new BreezeBizException(FAIL);
        }
        if (CollUtil.isNotEmpty(deptIdList)) {
            return this.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getDeptId, deptIdList));
        }
        return Lists.newArrayList();
    }

    /**
     * 获取用户通过角色
     *
     * @param roleCode 角色编码
     * @return {@link List }<{@link SysUser }>
     */
    @Override
    public List<SysUser> listUserByRole(String roleCode) {
        return this.baseMapper.listUserByRole(roleCode);
    }

    @Override
    public void syncFlowableUser() {
        List<SysUser> sysUserList = this.list();
        List<SysRole> roles = this.sysRoleService.list();
        List<FlowUserBO> syncUser = sysUserList.stream().map(item -> {
            FlowUserBO flowUserBO = FlowUserBO.builder()
                    .userId(item.getId())
                    .username(item.getUsername())
                    .displayName(item.getDisplayName())
                    .email(item.getEmail())
                    .build();
            List<SysRole> userRoleList = this.sysUserRoleService.getSysRoleByUserId(item.getId());
            if (CollUtil.isNotEmpty(userRoleList)) {
                List<SysRole> sysRoleList = this.sysRoleService.listByIds(userRoleList.stream().map(SysRole::getId).collect(Collectors.toList()));
                flowUserBO.setRoleList(sysRoleList);
            }
            return flowUserBO;
        }).collect(Collectors.toList());
    }

    /**
     * 加载登录用户
     *
     * @param sysUser 系统用户实体
     * @return {@link UserInfoDTO}
     */
    public UserInfoDTO buildLoginUserInfo(SysUser sysUser) {
        UserInfoDTO userInfo = sysUserMapStruct.entity2BaseLoginUser(sysUser);

        try {
            // 查询用户的角色
            List<UserRoleBO> userRoleBOList = Optional.ofNullable(sysRoleService.listRoleByUserId(sysUser.getId())).orElse(Collections.emptyList());
            if (CollUtil.isEmpty(userRoleBOList)) {
                throw new BreezeBizException(ResultCode.SYSTEM_EXCEPTION);
            }
            // 获取部门名称
            this.setDeptName(sysUser, userInfo);
            // 获取子级部门
            this.setSubDeptId(sysUser, userInfo);
            // 权限
            this.setAuthorities(userRoleBOList, userInfo);
            // 角色CODE
            this.setRoleCode(userRoleBOList, userInfo);
            // 用户的角色ID
            this.setUsersRoleId(userRoleBOList, userInfo);
            // 用户的角色的行数据权限
            this.setRowPermission(userRoleBOList, userInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return userInfo;
    }

    private static void traverseTree(SysDeptBO node, Set<Long> ids) {
        if (Objects.isNull(node)) {
            return;
        }
        // 添加当前节点的 ID 到列表
        ids.add(node.getId());
        // 递归遍历所有子节点
        for (SysDeptBO child : node.getSubDeptList()) {
            traverseTree(child, ids);
        }
    }

    // 递归遍历树形结构并获取所有节点的 ID
    public static Set<Long> getAllNodeIds(SysDeptBO root) {
        Set<Long> ids = Sets.newHashSet();
        traverseTree(root, ids);
        return ids;
    }

    private void setSubDeptId(SysUser sysUser, UserInfoDTO userInfo) {
        Long deptId = sysUser.getDeptId();
        if (Objects.isNull(deptId)) {
            return;
        }
        List<SysDeptBO> sysDeptList = this.sysDeptService.listSubDeptId(deptId);
        if (CollUtil.isEmpty(sysDeptList)) {
            return;
        }
        userInfo.setSubDeptId(getAllNodeIds(sysDeptList.get(0)));
    }

    private void setAuthorities(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        userInfo.setAuthorities(this.sysMenuService.listUserMenuPermission(userRoleBOList));
    }

    private void setDeptName(SysUser sysUser, UserInfoDTO userInfo) {
        Optional.ofNullable(this.sysDeptService.getById(sysUser.getDeptId())).ifPresent(sysDept -> userInfo.setDeptName(sysDept.getDeptName()));
    }

    private Set<Long> getHasNormalPermissionRoleId(List<UserRoleBO> userRoleBOList) {
        // @formatter:off
        return userRoleBOList.stream()
                .filter(permission -> StrUtil.equals(permission.getRowPermissionType(), DataPermissionType.CUSTOMIZES.getType()))
                .map(UserRoleBO::getRoleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    private void setRowPermission(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        userInfo.setPermissionType(this.getMaxPermissionScope(userRoleBOList));
        Set<Long> roleIdSet = this.getHasNormalPermissionRoleId(userRoleBOList);
        Set<String> permissionCodeSet = this.sysRoleRowPermissionService.listRowPermission(roleIdSet);
        userInfo.setRowPermissionCode(permissionCodeSet);
    }

    private void setRoleCode(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        userInfo.setUserRoleCodes(userRoleBOList.stream().map(UserRoleBO::getRoleCode).collect(Collectors.toSet()));
    }

    private void setUsersRoleId(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        Set<Long> roleIds = userRoleBOList.stream().map(UserRoleBO::getRoleId).filter(Objects::nonNull).collect(Collectors.toSet());
        userInfo.setUserRoleIds(roleIds);
    }

    private String getMaxPermissionScope(List<UserRoleBO> userRoleBOList) {
        Set<String> permissionCodeSet = userRoleBOList.stream().map(UserRoleBO::getRowPermissionType)
                // 确保过滤掉null值，避免NullPointerException
                .filter(Objects::nonNull).collect(Collectors.toSet());
        // 初始化resultSet
        Set<DataPermissionType> resultSet = Sets.newHashSet();
        // 遍历DataPermissionCode枚举，匹配权限
        for (DataPermissionType value : DataPermissionType.values()) {
            if (permissionCodeSet.contains(value.getType())) {
                resultSet.add(value);
            }
        }
        return resultSet.stream().min(Comparator.comparingInt(DataPermissionType::getLevel)).map(DataPermissionType::toString).orElse("");
    }

    @Override
    public UserPrincipal loadUserByUserId(String userId) {
        SysUser sysUser = this.getById(userId);
        if (Objects.isNull(sysUser)) {
            throw new BreezeBizException(ResultCode.USER_NOT_FOUND);
        }
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes is null");
        return convertResponseUserInfo(userInfoDTO);
    }

    @Override
    public List<String> loadUserPermissionByUserId(String userId) {
        // 查询用户的角色
        List<UserRoleBO> userRoleBOList = sysRoleService.listRoleByUserId(Long.valueOf(userId));
        if (CollUtil.isEmpty(userRoleBOList)) {
            throw new BreezeBizException(ResultCode.USERS_ROLE_IS_NULL);
        }
        // 权限
        return this.sysMenuService.listUserMenuPermission(userRoleBOList).stream().toList();
    }

    @Override
    public UserPrincipal loadUserByPhone(String phone) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone));
        if (Objects.isNull(sysUser)) {
            throw new BreezeBizException(ResultCode.USER_NOT_FOUND);
        }
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        return convertResponseUserInfo(userInfoDTO);
    }


    @Override
    public UserPrincipal loadUserByUsername(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(sysUser)) {
            throw new BreezeBizException(ResultCode.USER_NOT_FOUND);
        }
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        return convertResponseUserInfo(userInfoDTO);
    }


    @Override
    public UserPrincipal loadUserByEmail(String email) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, email));
        if (Objects.isNull(sysUser)) {
            throw new BreezeBizException(ResultCode.USER_NOT_FOUND);
        }
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        return convertResponseUserInfo(userInfoDTO);
    }

    @Override
    public List<String> loadUserRoleByUserId(String userId) {
        // 查询用户的角色
        List<UserRoleBO> userRoleBOList = sysRoleService.listRoleByUserId(Long.valueOf(userId));
        if (CollUtil.isEmpty(userRoleBOList)) {
            throw new BreezeBizException(ResultCode.USERS_ROLE_IS_NULL);
        }
        return userRoleBOList.stream().map(UserRoleBO::getRoleCode).collect(Collectors.toList());
    }

}
