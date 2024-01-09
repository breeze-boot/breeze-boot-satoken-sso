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

package com.breeze.boot.modules.system.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.domain.SysMenu;
import com.breeze.boot.modules.system.domain.SysPlatform;
import com.breeze.boot.modules.system.domain.SysUser;
import com.breeze.boot.modules.system.domain.params.FileParam;
import com.breeze.boot.modules.system.domain.query.DeptQuery;
import com.breeze.boot.modules.system.service.*;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CoreConstants.ROOT;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    /**
     * 菜单服务
     */
    private final SysMenuService menuService;

    /**
     * 平台服务
     */
    private final SysPlatformService platformService;

    /**
     * 用户服务
     */
    private final SysUserService userService;

    /**
     * 平台服务
     */
    private final SysDeptService deptService;

    /**
     * 角色服务
     */
    private final SysRoleService roleService;

    /**
     * 租户服务
     */
    private final SysTenantService tenantService;

    /**
     * 岗位服务
     */
    private final SysPostService postService;

    /**
     * 元数据服务
     */
    private final MateService mateService;

    /**
     * 数据权限服务
     */
    private final SysPermissionService permissionService;

    /**
     * 文件服务
     */
    private final SysFileService sysFileService;

    /**
     * 菜单树形下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> selectMenu(Long id) {
        List<SysMenu> menuList = this.menuService.list(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getType, 2));
        List<TreeNode<Long>> treeNodeList = menuList.stream().map(
                menu -> {
                    TreeNode<Long> treeNode = new TreeNode<>();
                    treeNode.setId(menu.getId());
                    treeNode.setParentId(menu.getParentId());
                    treeNode.setName(menu.getName());
                    Map<String, Object> leafMap = Maps.newHashMap();
                    if (Objects.equals(menu.getId(), id)) {
                        leafMap.put("disabled", Boolean.TRUE);
                    }
                    leafMap.put("value", menu.getTitle());
                    leafMap.put("key", menu.getId());
                    treeNode.setExtra(leafMap);
                    return treeNode;
                }
        ).collect(Collectors.toList());
        return Result.ok(TreeUtil.build(treeNodeList, ROOT));
    }

    /**
     * 平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPlatform() {
        List<SysPlatform> platformList = this.platformService.list();
        List<Map<String, Object>> collect = platformList.stream().map(sysPlatform -> {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("key", sysPlatform.getId());
            map.put("value", sysPlatform.getPlatformName());
            return map;
        }).collect(Collectors.toList());
        return Result.ok(collect);
    }

    /**
     * 部门下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<?>> selectDept(Long id) {
        return Result.ok(this.deptService.listDept(DeptQuery.builder().id(id).build()));
    }

    /**
     * 用户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    @Operation(summary = "用户下拉框", description = "下拉框接口")
    @GetMapping("/selectUser")
    public Result<List<SysUser>> selectUser() {
        return Result.ok(this.userService.list());
    }

    /**
     * 角色下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectRole() {
        return Result.ok(this.roleService.list().stream().map(sysRole -> {
            Map<@Nullable String, @Nullable Object> roleMap = Maps.newHashMap();
            roleMap.put("key", sysRole.getId());
            roleMap.put("value", sysRole.getRoleName());
            return roleMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 租户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectTenant() {
        return Result.ok(this.tenantService.list().stream().map(tanent -> {
            Map<@Nullable String, @Nullable Object> tenantMap = Maps.newHashMap();
            tenantMap.put("key", tanent.getId());
            tenantMap.put("value", tanent.getTenantName());
            return tenantMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 岗位下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPost() {
        return Result.ok(this.postService.list().stream().map(post -> {
            Map<@Nullable String, @Nullable Object> tenantMap = Maps.newHashMap();
            tenantMap.put("key", post.getId());
            tenantMap.put("value", post.getPostName());
            return tenantMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 表名下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectTable() {
        return Result.ok(this.mateService.selectTable());
    }

    /**
     * 字段下拉框
     *
     * @param tableName 表名
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectColumn(String tableName) {
        return Result.ok(this.mateService.selectColumn(tableName));
    }

    /**
     * 数据权限下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPermission() {
        return Result.ok(this.permissionService.list().stream().map(permission -> {
            Map<@Nullable String, @Nullable Object> tenantMap = Maps.newHashMap();
            tenantMap.put("key", permission.getId());
            tenantMap.put("value", permission.getPermissionName());
            return tenantMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 上传minio s3
     *
     * @param fileParam 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public Result<Map<String, Object>> uploadMinioS3(FileParam fileParam,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return this.sysFileService.uploadMinioS3(fileParam, request, response);
    }

    /**
     * 文件上传到本地存储
     *
     * @param fileParam 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public Result<Map<String, Object>> uploadLocalStorage(FileParam fileParam,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        return this.sysFileService.uploadLocalStorage(fileParam, request, response);
    }
}
