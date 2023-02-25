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
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.entity.UserRoleDTO;
import com.breeze.boot.system.domain.SysRole;
import com.breeze.boot.system.domain.SysRoleDataPermission;
import com.breeze.boot.system.domain.SysRoleMenu;
import com.breeze.boot.system.dto.RoleSearchDTO;
import com.breeze.boot.system.mapper.SysRoleMapper;
import com.breeze.boot.system.service.SysRoleDataPermissionService;
import com.breeze.boot.system.service.SysRoleMenuService;
import com.breeze.boot.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统角色服务impl
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /**
     * 系统角色菜单服务
     */
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 数据权限服务系统作用
     */
    @Autowired
    private SysRoleDataPermissionService sysRoleDataPermissionService;

    /**
     * 用户角色列表
     *
     * @param userId 用户id
     * @return {@link Set}<{@link UserRoleDTO}>
     */
    @Override
    public Set<UserRoleDTO> listRoleByUserId(Long userId) {
        return this.baseMapper.listRoleByUserId(userId)
                .stream()
                .map(sysRoleEntity -> {
                    UserRoleDTO userRoleDTO = new UserRoleDTO();
                    BeanUtil.copyProperties(sysRoleEntity, userRoleDTO);
                    userRoleDTO.setRoleId(sysRoleEntity.getId());
                    return userRoleDTO;
                }).collect(Collectors.toSet());
    }

    /**
     * 列表页面
     *
     * @param roleSearchDTO 角色搜索DTO
     * @return {@link Page}<{@link SysRole}>
     */
    @Override
    public Page<SysRole> listPage(RoleSearchDTO roleSearchDTO) {
        Page<SysRole> rolePage = new Page<>(roleSearchDTO.getCurrent(), roleSearchDTO.getSize());
        return this.baseMapper.listPage(rolePage, roleSearchDTO);
    }

    /**
     * 通过IDS删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteByIds(List<Long> ids) {
        List<SysRole> roleEntityList = this.listByIds(ids);
        if (CollUtil.isEmpty(roleEntityList)) {
            return Result.fail(Boolean.FALSE, "角色不存在");
        }
        boolean remove = this.removeByIds(ids);
        if (remove) {
            List<Long> collect = roleEntityList.stream().map(SysRole::getId).collect(Collectors.toList());
            // 删除用户角色关系
            this.sysRoleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, collect));
            // 删除角色数据权限关系
            this.sysRoleDataPermissionService.remove(Wrappers.<SysRoleDataPermission>lambdaQuery().in(SysRoleDataPermission::getRoleId, collect));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 获取用户角色列表
     *
     * @param userId 用户Id
     * @return {@link Result}<{@link List}<{@link Long}>>
     */
    @Override
    public List<Long> listUserRoles(Long userId) {
        return this.baseMapper.listUserRoles(userId);
    }

}
