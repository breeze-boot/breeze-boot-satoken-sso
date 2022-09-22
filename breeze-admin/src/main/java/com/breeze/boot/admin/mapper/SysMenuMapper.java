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

package com.breeze.boot.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breeze.boot.admin.dto.MenuDTO;
import com.breeze.boot.admin.entity.SysMenu;
import com.breeze.boot.admin.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单映射器
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 用户菜单权限列表
     *
     * @param roleEntityList 角色实体列表
     * @return {@link List}<{@link String}>
     */
    List<String> listUserMenuPermission(@Param("roleList") List<SysRole> roleEntityList);

    /**
     * 菜单列表
     *
     * @param menuDTO 菜单dto
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> listMenu(@Param("menuDTO") MenuDTO menuDTO);

}
