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

package com.breeze.boot.modules.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.model.entity.SysTenant;
import com.breeze.boot.modules.auth.model.query.TenantQuery;

import java.util.List;

/**
 * 系统租户服务
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
public interface SysTenantService extends IService<SysTenant> {

    /**
     * 列表页面
     *
     * @param tenantQuery 租户查询
     * @return {@link IPage}<{@link SysTenant}>
     */
    IPage<SysTenant> listPage(TenantQuery tenantQuery);

    /**
     * 通过IDS删除租户
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeTenantByIds(List<Long> ids);

}
