
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

package com.breeze.boot.modules.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.DataPermissionCode;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysRowPermissionMapper;
import com.breeze.boot.modules.auth.model.entity.SysRoleRowPermission;
import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.form.RowPermissionForm;
import com.breeze.boot.modules.auth.model.mappers.SysRowPermissionMapStruct;
import com.breeze.boot.modules.auth.model.query.DataPermissionQuery;
import com.breeze.boot.modules.auth.model.vo.RowPermissionVO;
import com.breeze.boot.modules.auth.service.SysRoleRowPermissionService;
import com.breeze.boot.modules.auth.service.SysRowPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 系统行级数据权限服务 impl
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Service
@RequiredArgsConstructor
public class SysRowPermissionServiceImpl extends ServiceImpl<SysRowPermissionMapper, SysRowPermission> implements SysRowPermissionService {

    /**
     * 系统角色数据权限服务
     */
    private final SysRoleRowPermissionService sysRoleRowPermissionService;

    private final SysRowPermissionMapStruct sysRowPermissionMapStruct;

    /**
     * 列表页面
     *
     * @param permissionQuery 权限查询
     * @return {@link Page}<{@link RowPermissionVO}>
     */
    @Override
    public Page<RowPermissionVO> listPage(DataPermissionQuery permissionQuery) {
        Page<SysRowPermission> rowPermissionPage = this.baseMapper.listPage(new Page<>(permissionQuery.getCurrent(), permissionQuery.getSize()), permissionQuery);
        return this.sysRowPermissionMapStruct.page2VOPage(rowPermissionPage);
    }

    /**
     * 按id获取信息
     *
     * @param permissionId 权限id
     * @return {@link RowPermissionVO }
     */
    @Override
    public RowPermissionVO getInfoById(Long permissionId) {
        SysRowPermission sysRowPermission = this.getById(permissionId);
        return this.sysRowPermissionMapStruct.entity2VO(sysRowPermission);
    }

    /**
     * 保存数据权限
     *
     * @param rowPermissionForm 行权限表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> saveRowPermission(RowPermissionForm rowPermissionForm) {
        SysRowPermission sysRowPermission = sysRowPermissionMapStruct.form2Entity(rowPermissionForm);
        if (DataPermissionCode.checkInEnum(rowPermissionForm.getPermissionCode())) {
            return Result.warning(Boolean.FALSE, "固定权限无需再次添加，请添加自定义权限");
        }
        return Result.ok(this.save(sysRowPermission));
    }


    /**
     * 修改权限
     *
     * @param id                id
     * @param rowPermissionForm 行权限表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Override
    public Result<Boolean> modifyPermission(Long id, RowPermissionForm rowPermissionForm) {
        SysRowPermission sysRowPermission = sysRowPermissionMapStruct.form2Entity(rowPermissionForm);
        rowPermissionForm.setId(id);
        if (DataPermissionCode.checkInEnum(rowPermissionForm.getPermissionCode())) {
            return Result.warning(Boolean.FALSE, "固定权限无需修改，请修改自定义权限");
        }
        return Result.ok(sysRowPermission.updateById());
    }

    /**
     * 删除数据权限通过IDs
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeRowPermissionByIds(List<Long> ids) {
        List<SysRoleRowPermission> rolePermissionList = this.sysRoleRowPermissionService.list(Wrappers.<SysRoleRowPermission>lambdaQuery().in(SysRoleRowPermission::getPermissionId, ids));
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            return Result.warning(Boolean.FALSE, "该数据权限已被使用");
        }
        return Result.ok(this.removeBatchByIds(ids));
    }
}
