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

package com.breeze.boot.system.mapper;

import com.breeze.boot.database.mapper.BreezeBaseMapper;
import com.breeze.boot.system.domain.SysMenu;
import com.breeze.boot.system.domain.SysRole;
import com.breeze.boot.system.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单映射器
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Mapper
public interface SysMenuMapper extends BreezeBaseMapper<SysMenu> {

    /**
     * 用户菜单权限列表
     *
     * @param roleList 角色列表
     * @return {@link Set}<{@link String}>
     */
    Set<String> listUserMenuPermission(@Param("roleList") List<SysRole> roleList);

    /**
     * 菜单列表
     *
     * @param menuDTO 菜单dto
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> listMenu(@Param("menuDTO") MenuDTO menuDTO);

    /**
     * 选择菜单角色id
     *
     * @param roleIds      用户角色id
     * @param platformCode 平台编码
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> selectMenusByRoleId(@Param("roleIds") Set<Long> roleIds, @Param("platformCode") String platformCode);

}
