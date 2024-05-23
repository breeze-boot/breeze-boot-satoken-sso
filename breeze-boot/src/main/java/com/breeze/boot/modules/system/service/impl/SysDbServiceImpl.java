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

package com.breeze.boot.modules.system.service.impl;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.modules.system.model.entity.SysDb;
import com.breeze.boot.modules.system.model.query.DbQuery;
import com.breeze.boot.modules.system.mapper.SysDbMapper;
import com.breeze.boot.modules.system.service.SysDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

/**
 * 系统字典服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysDbServiceImpl extends ServiceImpl<SysDbMapper, SysDb> implements SysDbService {

    private final DefaultDataSourceCreator defaultDataSourceCreator;

    private final DataSource dataSource;

    /**
     * 分页
     *
     * @param dbQuery 数据源查询参数
     * @return {@link IPage}<{@link SysDb}>
     */
    @Override
    public IPage<SysDb> listPage(DbQuery dbQuery) {
        return this.baseMapper.listPage(new Page<>(dbQuery.getCurrent(), dbQuery.getSize()));
    }

    /**
     * 添加数据源
     *
     * @param sysDb 数据源
     * @return {@link Boolean}
     */
    @Override
    public Boolean saveDb(SysDb sysDb) {
        try {
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
            DataSourceProperty sourceProperty = new DataSourceProperty();
            sourceProperty.setPoolName(sysDb.getDbName());
            sourceProperty.setDriverClassName(sysDb.getDriver());
            sourceProperty.setUsername(sysDb.getUsername());
            sourceProperty.setPassword(sysDb.getPassword());
            DataSource creatorDataSource = defaultDataSourceCreator.createDataSource(sourceProperty);
            dynamicRoutingDataSource.addDataSource(sourceProperty.getPoolName(), creatorDataSource);
        } catch (Exception e) {
            log.error("[数据源注入失败]", e);
        }
        return this.save(sysDb);
    }

    /**
     * 更新数据源
     *
     * @param sysDb 数据源
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateDbById(SysDb sysDb) {
        SysDb checkSysDb = this.getById(sysDb.getId());
        if (Objects.isNull(checkSysDb)) {
            log.error("[更新数据源失败]");
            throw new SystemServiceException(ResultCode.EXCEPTION);
        }
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
        dynamicRoutingDataSource.removeDataSource(checkSysDb.getDbName());
        this.saveDb(checkSysDb);
        return Boolean.TRUE;
    }

    /**
     * 删除数据源
     *
     * @param ids 数据源Ids
     * @return {@link Boolean}
     */
    @Override
    public Boolean removeDbByIds(List<Long> ids) {
        for (Long id : ids) {
            SysDb sysDb = this.getById(id);
            if (Objects.isNull(sysDb)) {
                log.error("[删除数据源失败]");
                throw new SystemServiceException(ResultCode.EXCEPTION);
            }
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
            dynamicRoutingDataSource.removeDataSource(sysDb.getDbName());
        }
        return Boolean.TRUE;
    }

}
