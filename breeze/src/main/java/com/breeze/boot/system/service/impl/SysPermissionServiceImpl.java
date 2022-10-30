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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.security.entity.PermissionDTO;
import com.breeze.boot.system.domain.SysPermission;
import com.breeze.boot.system.mapper.SysPermissionMapper;
import com.breeze.boot.system.service.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 * 系统数据权限服务 impl
 *
 * @author breeze
 * @date 2022-10-30
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    /**
     * 列表分页
     *
     * @param permissionDTO 许可dto
     * @return {@link Page}<{@link SysPermission}>
     */
    @Override
    public Page<SysPermission> listPage(PermissionDTO permissionDTO) {
        return null;
    }

}




