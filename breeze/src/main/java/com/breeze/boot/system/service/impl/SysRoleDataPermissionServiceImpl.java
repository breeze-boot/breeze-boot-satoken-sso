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

package com.breeze.boot.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.security.entity.DataPermissionDTO;
import com.breeze.boot.system.domain.SysRoleDataPermission;
import com.breeze.boot.system.mapper.SysRoleDataPermissionMapper;
import com.breeze.boot.system.service.SysRoleDataPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 系统角色权限服务impl
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Service
public class SysRoleDataPermissionServiceImpl extends ServiceImpl<SysRoleDataPermissionMapper, SysRoleDataPermission> implements SysRoleDataPermissionService {

    /**
     * 角色数据权限列表
     *
     * @param roleIdSet 角色ID Set
     * @return {@link List}<{@link DataPermissionDTO}>
     */
    @Override
    public List<DataPermissionDTO> listRoleDataPermissionByRoleIds(Set<Long> roleIdSet) {
        return this.baseMapper.listRoleDataPermissionByRoleIds(roleIdSet);
    }

}




