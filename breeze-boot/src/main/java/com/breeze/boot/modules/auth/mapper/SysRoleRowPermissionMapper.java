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

package com.breeze.boot.modules.auth.mapper;

import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.entity.SysRoleRowPermission;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 系统角色行级权限映射器
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Mapper
public interface SysRoleRowPermissionMapper extends BreezeBaseMapper<SysRoleRowPermission> {

    /**
     * 查询角色的行级数据权限列表
     *
     * @param roleIdSet 角色ID Set
     * @return {@link SysRowPermission}
     */
    List<SysRowPermission> listRowPermissionData(@Param("roleIdSet") Set<Long> roleIdSet);

}
