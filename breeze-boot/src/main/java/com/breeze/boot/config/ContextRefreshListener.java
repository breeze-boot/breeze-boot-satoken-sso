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

package com.breeze.boot.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.breeze.boot.modules.system.model.entity.SysDb;
import com.breeze.boot.modules.system.service.SysDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.sql.DataSource;
import java.util.List;

/**
 * 加载数据源
 *
 * @author gaoweixuan
 * @since 2024/01/24
 */
@Configuration
@RequiredArgsConstructor
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DefaultDataSourceCreator defaultDataSourceCreator;

    private final DataSource dataSource;

    private final SysDbService sysDbService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
        List<SysDb> selected = sysDbService.list();
        for (SysDb sysDb : selected) {
            DataSourceProperty sourceProperty = new DataSourceProperty();
            sourceProperty.setPoolName(sysDb.getDbName());
            sourceProperty.setDriverClassName(sysDb.getDriver());
            sourceProperty.setUsername(sysDb.getUsername());
            sourceProperty.setPassword(sysDb.getPassword());
            DataSource creatorDataSource = defaultDataSourceCreator.createDataSource(sourceProperty);
            dynamicRoutingDataSource.addDataSource(sourceProperty.getPoolName(), creatorDataSource);
        }
    }

}
