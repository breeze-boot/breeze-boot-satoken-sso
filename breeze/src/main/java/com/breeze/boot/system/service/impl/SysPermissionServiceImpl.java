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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.Result;
import com.breeze.boot.security.entity.PermissionDTO;
import com.breeze.boot.system.domain.SysPermission;
import com.breeze.boot.system.dto.PermissionDiv;
import com.breeze.boot.system.dto.SysPermissionDTO;
import com.breeze.boot.system.mapper.SysPermissionMapper;
import com.breeze.boot.system.service.SysDeptService;
import com.breeze.boot.system.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统数据权限服务 impl
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    /**
     * 系统部门服务
     */
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表分页
     *
     * @param permissionDTO 许可dto
     * @return {@link Page}<{@link SysPermission}>
     */
    @Override
    public Page<SysPermission> listPage(PermissionDTO permissionDTO) {
        return this.baseMapper.listPage(new Page<>(permissionDTO.getCurrent(), permissionDTO.getSize()), permissionDTO);
    }

    /**
     * 保存许可
     *
     * @param permission 许可
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> savePermission(SysPermissionDTO permission) {
        SysPermission sysPermission = SysPermission.builder().build();
        BeanUtil.copyProperties(permission, sysPermission);
        sysPermission.setPermissions(String.join(",", permission.getPermissions()));
        if (Objects.equals("999999", permission.getPermissionType())) {
            // 自定义
            List<PermissionDiv> divList = permission.getPermissionDiv();
            StrBuilder strBuilder = new StrBuilder();
            divList.forEach(div -> strBuilder
                    .append(" OR ( ")
                    .append(div.getColumn())
                    .append(" ")
                    .append(div.getCompare())
                    .append(" ")
                    .append(div.getConditions())
                    .append(" ) "));
            String sql = strBuilder.toString();
            sysPermission.setSql(sql.replaceFirst("OR", ""));
        } else if (Objects.equals("1", permission.getPermissionType())) {
            // 本级部门及其以下部门
            List<Long> selectDeptId = this.sysDeptService.selectDeptById(String.join(",", permission.getPermissions()));
            sysPermission.setPermissions(selectDeptId.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        return Result.ok(this.save(sysPermission));
    }

}




