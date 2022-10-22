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
import com.breeze.boot.security.entity.UserRoleDTO;
import com.breeze.boot.system.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统角色映射器
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Mapper
public interface SysRoleMapper extends BreezeBaseMapper<SysRole> {

    /**
     * 用户角色列表
     *
     * @param id id
     * @return {@link List}<{@link UserRoleDTO}>
     */
    List<SysRole> listUserRole(Long id);

}
