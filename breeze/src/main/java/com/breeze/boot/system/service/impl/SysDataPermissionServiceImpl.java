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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.entity.DataPermissionDTO;
import com.breeze.boot.system.domain.SysDataPermission;
import com.breeze.boot.system.domain.SysRoleDataPermission;
import com.breeze.boot.system.dto.PermissionDiy;
import com.breeze.boot.system.dto.SysDataPermissionDTO;
import com.breeze.boot.system.mapper.SysDataPermissionMapper;
import com.breeze.boot.system.service.SysDataPermissionService;
import com.breeze.boot.system.service.SysDeptService;
import com.breeze.boot.system.service.SysRoleDataPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统数据权限服务 impl
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Service
public class SysDataPermissionServiceImpl extends ServiceImpl<SysDataPermissionMapper, SysDataPermission> implements SysDataPermissionService {

    /**
     * 系统部门服务
     */
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 系统角色数据权限服务
     */
    @Autowired
    private SysRoleDataPermissionService sysRoleDataPermissionService;

    /**
     * 列表页面
     *
     * @param dataPermissionDTO 权限DTO
     * @return {@link Page}<{@link SysDataPermission}>
     */
    @Override
    public Page<SysDataPermission> listPage(DataPermissionDTO dataPermissionDTO) {
        return this.baseMapper.listPage(new Page<>(dataPermissionDTO.getCurrent(), dataPermissionDTO.getSize()), dataPermissionDTO);
    }

    /**
     * 保存数据权限
     *
     * @param dataPermissionDTO 权限
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> saveDataPermission(SysDataPermissionDTO dataPermissionDTO) {
        SysDataPermission sysDataPermission = SysDataPermission.builder().build();
        BeanUtil.copyProperties(dataPermissionDTO, sysDataPermission);
        sysDataPermission.setPermissions(String.join(",", dataPermissionDTO.getPermissions()));
        if (StrUtil.equals(dataPermissionDTO.getDataPermissionType(), "DIY")) {
            // 自定义
            List<PermissionDiy> divList = dataPermissionDTO.getPermissionDiy();
            StrBuilder strBuilder = new StrBuilder();
            divList.forEach(div -> strBuilder
                    .append(" OR ( a.")
                    .append(div.getColumn())
                    .append(" ")
                    .append(div.getCompare())
                    .append(" ")
                    .append(div.getConditions())
                    .append(" ) "));
            String sql = strBuilder.toString();
            sysDataPermission.setStrSql(sql.replaceFirst("OR", ""));
        } else if (StrUtil.equals("DEPT_AND_LOWER_LEVEL", dataPermissionDTO.getDataPermissionType())) {
            // 本级部门及其以下部门
            List<Long> selectDeptId = this.sysDeptService.selectDeptById(String.join(",", dataPermissionDTO.getPermissions()));
            sysDataPermission.setPermissions(selectDeptId.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        return Result.ok(this.save(sysDataPermission));
    }

    /**
     * 删除数据权限通过IDs
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removePermissionByIds(List<Long> ids) {
        List<SysRoleDataPermission> rolePermissionList = this.sysRoleDataPermissionService
                .list(Wrappers.<SysRoleDataPermission>lambdaQuery().in(SysRoleDataPermission::getDataPermissionId, ids));
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            return Result.warning(Boolean.FALSE, "该数据权限已被使用");
        }
        return Result.ok(this.removeBatchByIds(ids));
    }

}




