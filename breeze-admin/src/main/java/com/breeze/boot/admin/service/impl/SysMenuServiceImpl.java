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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.admin.dto.MenuDTO;
import com.breeze.boot.admin.entity.SysMenuEntity;
import com.breeze.boot.admin.entity.SysMenuRoleEntity;
import com.breeze.boot.admin.entity.SysPlatformEntity;
import com.breeze.boot.admin.entity.SysRoleEntity;
import com.breeze.boot.admin.mapper.SysMenuMapper;
import com.breeze.boot.admin.service.SysMenuRoleService;
import com.breeze.boot.admin.service.SysMenuService;
import com.breeze.boot.admin.service.SysPlatformService;
import com.breeze.boot.core.Result;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统菜单服务impl
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    /**
     * 平台服务
     */
    @Autowired
    private SysPlatformService platformService;

    /**
     * 服务菜单作用
     */
    @Autowired
    private SysMenuRoleService menuRoleService;

    /**
     * 用户菜单权限列表
     *
     * @param roleEntityList 角色实体列表
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> listUserMenuPermission(List<SysRoleEntity> roleEntityList) {
        return this.baseMapper.listUserMenuPermission(roleEntityList);
    }

    /**
     * 树菜单列表
     *
     * @param platformCode 平台代码
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> listTreeMenu(String platformCode) {
        com.breeze.boot.jwtlogin.entity.CurrentLoginUser currentLoginUser = com.breeze.boot.jwtlogin.utils.SecurityUtils.getCurrentLoginUser();
        log.info("用户信息 ===> {}", JSONUtil.parse(currentLoginUser));
        if (CollUtil.isEmpty(currentLoginUser.getUserRoleIds())) {
            return Result.ok();
        }
        List<SysMenuRoleEntity> menuRoleList = this.menuRoleService.list(Wrappers.<SysMenuRoleEntity>lambdaQuery()
                .in(SysMenuRoleEntity::getRoleId, currentLoginUser.getUserRoleIds()));
        if (CollUtil.isEmpty(menuRoleList)) {
            return Result.ok();
        }
        List<Long> menuIdList = menuRoleList.stream().map(SysMenuRoleEntity::getMenuId).collect(Collectors.toList());
        // 使用CODE显示前端菜单
        SysPlatformEntity platformEntity = this.platformService.getOne(Wrappers.<SysPlatformEntity>lambdaQuery()
                .eq(SysPlatformEntity::getPlatformCode, platformCode));
        if (Objects.isNull(platformEntity)) {
            return Result.ok();
        }
        return this.listTreeMenuData(menuIdList, platformEntity);
    }

    /**
     * 权限数据列表树作用
     *
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    private Result<List<Tree<Long>>> listTreeRolePermissionData() {
        List<SysMenuEntity> menuList = this.list(Wrappers.<SysMenuEntity>lambdaQuery()
                .in(SysMenuEntity::getType, 1, 2)
                .or()
                .eq(SysMenuEntity::getType, 3)
                .orderByAsc(SysMenuEntity::getSort));
        if (CollUtil.isEmpty(menuList)) {
            return Result.ok();
        }
        return Result.ok(getTrees(menuList, 0L));
    }

    /**
     * 列表树菜单数据
     *
     * @param platformEntity 平台实体
     * @param menuIdList     菜单id列表
     * @return {@link Result}
     */
    private Result listTreeMenuData(List<Long> menuIdList, SysPlatformEntity platformEntity) {
        List<SysMenuEntity> menuList = this.list(Wrappers.<SysMenuEntity>lambdaQuery()
                .in(SysMenuEntity::getId, menuIdList)
                .eq(SysMenuEntity::getPlatformId, platformEntity.getId())
                .in(SysMenuEntity::getType, 1, 2)
                .orderByAsc(SysMenuEntity::getSort));
        if (CollUtil.isEmpty(menuList)) {
            return Result.ok();
        }
        return Result.ok(getTrees(menuList, 0L));
    }

    /**
     * 菜单列表
     *
     * @param menuDTO 菜单dto
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @Override
    public Result<? extends Object> listMenu(MenuDTO menuDTO) {
        if (StrUtil.isAllNotBlank(menuDTO.getName()) || StrUtil.isAllNotBlank(menuDTO.getTitle())) {
            List<SysMenuEntity> entityList = this.baseMapper.listMenu(menuDTO);
            return Result.ok(entityList);
        }
        List<SysMenuEntity> menuEntityList = this.baseMapper.listMenu(menuDTO);
        List<Tree<Long>> build = getTrees(menuEntityList, 0L);
        return Result.ok(build);
    }

    /**
     * 列表树许可
     *
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> listTreePermission() {
        com.breeze.boot.jwtlogin.entity.CurrentLoginUser currentLoginUser = com.breeze.boot.jwtlogin.utils.SecurityUtils.getCurrentLoginUser();
        log.info("用户信息 ===> {}", JSONUtil.parse(currentLoginUser));
        if (CollUtil.isEmpty(currentLoginUser.getUserRoleIds())) {
            return Result.ok();
        }
        return listTreeRolePermissionData();
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Result}
     */
    @Override
    public Result deleteById(Long id) {
        List<SysMenuEntity> menuEntityList = this.list(Wrappers.<SysMenuEntity>lambdaQuery().eq(SysMenuEntity::getParentId, id));
        if (CollUtil.isNotEmpty(menuEntityList)) {
            return Result.warning(Boolean.FALSE, "存在子菜单, 不可删除");
        }
        boolean remove = this.removeById(id);
        if (remove) {
            return Result.ok("删除成功");
        }
        return Result.fail(Boolean.FALSE, "删除失败");
    }

    /**
     * 让树
     *
     * @param menuEntityList 菜单实体列表
     * @param id
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    private List<Tree<Long>> getTrees(List<SysMenuEntity> menuEntityList, Long id) {
        List<TreeNode<Long>> collect = menuEntityList.stream().map(menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort());
            Map<String, Object> leafMap = Maps.newHashMap();
            leafMap.put("title", menu.getTitle());
            leafMap.put("path", menu.getPath());
            leafMap.put("component", menu.getComponent());
            leafMap.put("icon", menu.getIcon());
            leafMap.put("platformName", menu.getPlatformName());
            leafMap.put("permission", menu.getPermission());
            leafMap.put("platformId", menu.getPlatformId());
            leafMap.put("sort", menu.getSort());
            leafMap.put("type", menu.getType());
            node.setExtra(leafMap);
            return node;
        }).collect(Collectors.toList());
        return TreeUtil.build(collect, id);
    }
}
