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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysMenuMapper;
import com.breeze.boot.modules.auth.model.bo.SysMenuBO;
import com.breeze.boot.modules.auth.model.bo.UserRoleBO;
import com.breeze.boot.modules.auth.model.entity.SysMenu;
import com.breeze.boot.modules.auth.model.entity.SysRoleMenu;
import com.breeze.boot.modules.auth.model.form.MenuForm;
import com.breeze.boot.modules.auth.model.mappers.SysMenuMapStruct;
import com.breeze.boot.modules.auth.model.query.MenuQuery;
import com.breeze.boot.modules.auth.service.SysMenuService;
import com.breeze.boot.modules.auth.service.SysRoleMenuService;
import com.breeze.boot.sso.utils.BreezeStpUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CoreConstants.ROOT;

/**
 * 系统菜单服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapStruct sysMenuMapStruct;

    /**
     * 系统角色菜单服务
     */
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 用户菜单权限列表
     *
     * @param userRoleBOList 用户角色列表
     * @return {@link List}<{@link String}>
     */
    @Override
    public Set<String> listUserMenuPermission(List<UserRoleBO> userRoleBOList) {
        return Optional.ofNullable(this.baseMapper.listUserMenuPermission(userRoleBOList)).orElseGet(HashSet::new);
    }

    /**
     * 树菜单列表
     *
     * @param platformCode 平台代码
     * @param i18n         国际化
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> listTreeMenu(String platformCode, String i18n) {
        Set<Long> roleIds = BreezeStpUtil.getUser().getUserRoleIds();
        if (CollUtil.isEmpty(roleIds)) {
            return Result.ok();
        }

        // 查询角色下的菜单信息
        List<SysMenuBO> menuList = this.baseMapper.selectMenusByRoleId(roleIds, platformCode);
        return Result.ok(this.buildTrees(menuList));
    }

    /**
     * 权限数据列表树选中数据
     *
     * @param type 类型
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    private Result<List<Tree<Long>>> listTreeRolePermission(List<Integer> type) {
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getType, type).orderByAsc(SysMenu::getSort));
        if (CollUtil.isEmpty(menuList)) {
            return Result.ok();
        }
        List<SysMenuBO> sysMenuBOList = this.sysMenuMapStruct.entity2BO(menuList);
        return Result.ok(this.buildTrees(sysMenuBOList));
    }

    /**
     * 菜单列表
     *
     * @param menuQuery 菜单查询
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> listMenu(MenuQuery menuQuery) {
        List<SysMenuBO> sysMenuBOList = this.baseMapper.listMenu(menuQuery);
        if (StrUtil.isAllNotBlank(menuQuery.getName()) || StrUtil.isAllNotBlank(menuQuery.getTitle())) {
            return Result.ok(sysMenuBOList);
        }
        // 查询数据
        List<Tree<Long>> build = this.buildTrees(sysMenuBOList);
        return Result.ok(build);
    }

    /**
     * 树形权限列表
     *
     * @param type 类型
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> listTreePermission(List<Integer> type) {
        Set<String> roleCodes = BreezeStpUtil.getUser().getUserRoleCodes();
        if (CollUtil.isEmpty(roleCodes)) {
            return Result.ok();
        }
        return this.listTreeRolePermission(type);
    }

    /**
     * 删除通过ID
     *
     * @param id id
     * @return {@link Result}
     */
    @Override
    public Result<Boolean> deleteById(Long id) {
        List<SysMenu> menuEntityList = this.list(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, id));
        if (CollUtil.isNotEmpty(menuEntityList)) {
            log.warn("存在子菜单, 不可删除");
            throw new BreezeBizException(ResultCode.IS_USED);
        }
        boolean remove = this.removeById(id);
        if (!remove) {
            throw new BreezeBizException(ResultCode.FAIL);
        }
        // 删除已经关联的角色的菜单
        this.sysRoleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getMenuId, id));
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 保存菜单
     *
     * @param menuForm 菜单表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> saveMenu(MenuForm menuForm) {
        SysMenu sysMenu = this.getById(menuForm.getParentId());
        if (!Objects.equals(ROOT, menuForm.getParentId()) && Objects.isNull(sysMenu)) {
            throw new BreezeBizException(ResultCode.NOT_FOUND);
        }
        return Result.ok(this.save(sysMenuMapStruct.form2Entity(menuForm)));
    }

    /**
     * 修改菜单
     *
     * @param id          id
     * @param menuForm 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> modifyMenu(Long id, MenuForm menuForm) {
        SysMenu sysMenu = sysMenuMapStruct.form2Entity(menuForm);
        sysMenu.setId(id);
        return Result.ok(this.updateById(sysMenu));
    }

    /**
     * 获取树形数据
     *
     * @param menuEntityList 菜单实体列表
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    private List<Tree<Long>> buildTrees(List<SysMenuBO> menuEntityList) {
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
            leafMap.put("keepAlive", menu.getKeepAlive());
            leafMap.put("hidden", menu.getHidden());
            leafMap.put("href", menu.getHref());
            leafMap.put("permission", menu.getPermission());
            leafMap.put("platformId", menu.getPlatformId());
            leafMap.put("sort", menu.getSort());
            leafMap.put("type", menu.getType());
            node.setExtra(leafMap);
            return node;
        }).collect(Collectors.toList());
        return TreeUtil.build(collect, com.breeze.boot.core.constants.CoreConstants.ROOT);
    }
}
