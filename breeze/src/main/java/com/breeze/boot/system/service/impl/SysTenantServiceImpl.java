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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.system.domain.SysTenant;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.TenantSearchDTO;
import com.breeze.boot.system.mapper.SysTenantMapper;
import com.breeze.boot.system.service.SysTenantService;
import com.breeze.boot.system.service.SysUserService;
import com.breeze.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统租户服务impl
 *
 * @author gaoweixuan
 * @date 2022-11-06
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表页面
     *
     * @param tenantSearchDTO 租户搜索DTO
     * @return {@link IPage}<{@link SysTenant}>
     */
    @Override
    public IPage<SysTenant> listPage(TenantSearchDTO tenantSearchDTO) {
        Page<SysTenant> logEntityPage = new Page<>(tenantSearchDTO.getCurrent(), tenantSearchDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(tenantSearchDTO.getTenantName()), SysTenant::getTenantName, tenantSearchDTO.getTenantName())
                .orderByDesc(SysTenant::getCreateTime)
                .page(logEntityPage);
    }

    /**
     * 通过IDS删除租户
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removeTenantByIds(List<Long> ids) {
        List<SysUser> sysUserList = this.sysUserService.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getTenantId, ids));
        if (CollUtil.isNotEmpty(sysUserList)) {
            return Result.warning(Boolean.FALSE, "租户已经被使用");
        }
        return Result.ok(this.removeByIds(ids));
    }

}




