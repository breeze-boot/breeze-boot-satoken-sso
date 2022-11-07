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

package com.breeze.boot.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.Result;
import com.breeze.boot.security.entity.UserRoleDTO;
import com.breeze.boot.system.domain.SysMenu;
import com.breeze.boot.system.dto.MenuDTO;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 用户菜单权限列表
     *
     * @param roleDTOList 角色列表
     * @return {@link List}<{@link String}>
     */
    Set<String> listUserMenuPermission(Set<UserRoleDTO> roleDTOList);

    /**
     * 树菜单列表
     *
     * @param platformCode 平台代码
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<Tree<Long>>> listTreeMenu(String platformCode);

    /**
     * 菜单列表
     *
     * @param menuDTO 菜单dto
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    Result<?> listMenu(MenuDTO menuDTO);

    /**
     * 树形权限列表
     *
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<Tree<Long>>> listTreePermission();

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Result}
     */
    Result<Boolean> deleteById(Long id);

    /**
     * 保存菜单
     *
     * @param menuEntity 菜单实体
     * @return {@link Result}
     */
    Result<Boolean> saveMenu(SysMenu menuEntity);

    /**
     * 更新菜单通过id
     *
     * @param menuEntity 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> updateMenuById(SysMenu menuEntity);

}

