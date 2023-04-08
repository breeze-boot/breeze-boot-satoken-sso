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

package com.breeze.boot.sys.mapper;

import com.breeze.boot.sys.domain.SysRoleDataPermission;
import com.breeze.database.mapper.BreezeBaseMapper;
import com.breeze.security.userextension.DataPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 系统角色权限映射器
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Mapper
public interface SysRoleDataPermissionMapper extends BreezeBaseMapper<SysRoleDataPermission> {

    /**
     * 角色数据权限列表
     *
     * @param roleIdSet 角色ID Set
     * @return {@link List}<{@link DataPermission}>
     */
    List<DataPermission> listRoleDataPermissionByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);

}



