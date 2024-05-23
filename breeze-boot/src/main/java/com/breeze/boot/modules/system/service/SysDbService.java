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

package com.breeze.boot.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.modules.system.model.entity.SysDb;
import com.breeze.boot.modules.system.model.query.DbQuery;

import java.util.List;

/**
 * 系统数据源服务
 *
 * @author gaoweixuan
 * @since 2024-01-15
 */
public interface SysDbService extends IService<SysDb> {

    /**
     * 分页
     *
     * @param dbQuery 数据源查询参数
     * @return {@link IPage}<{@link SysDb}>
     */
    IPage<SysDb> listPage(DbQuery dbQuery);

    /**
     * 添加数据源
     *
     * @param sysDb 数据源
     * @return {@link Boolean}
     */
    Boolean saveDb(SysDb sysDb);

    /**
     * 更新数据源
     *
     * @param sysDb 数据源
     * @return {@link Boolean}
     */
    Boolean updateDbById(SysDb sysDb);

    /**
     * 删除数据源
     *
     * @param ids 数据源Ids
     * @return {@link Boolean}
     */
    Boolean removeDbByIds(List<Long> ids);

}
