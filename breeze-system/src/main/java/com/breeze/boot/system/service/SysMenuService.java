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

package com.breeze.boot.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.dto.MenuDTO;
import com.breeze.boot.system.entity.SysMenu;
import com.breeze.boot.system.entity.SysRole;
import com.breeze.boot.core.Result;

import java.util.List;

/**
 * 系统菜单服务
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 用户菜单权限列表
     *
     * @param roleEntityList 角色实体列表
     * @return {@link List}<{@link String}>
     */
    List<String> listUserMenuPermission(List<SysRole> roleEntityList);

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
    Result<? extends Object> listMenu(MenuDTO menuDTO);

    /**
     * 列表树许可
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
    Result deleteById(Long id);

    /**
     * 保存菜单
     *
     * @param menuEntity 菜单实体
     * @return {@link Result}
     */
    Result saveMenu(SysMenu menuEntity);

}
