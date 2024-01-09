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

package com.breeze.boot.gen.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.breeze.boot.gen.dto.DataSourceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Set;

/**
 * db控制器
 *
 * @author gaoweixuan
 * @since 2023/07/03
 */
@RestController
@RequiredArgsConstructor
public class DbController {

    private final DataSource dataSource;

    private final DefaultDataSourceCreator defaultDataSourceCreator;

    @PostMapping("/addSource")
    public Set<String> addSource(@RequestBody DataSourceDTO dataSourceDTO) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dataSourceDTO, dataSourceProperty);
        DynamicRoutingDataSource source = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = defaultDataSourceCreator.createDataSource(dataSourceProperty);
        source.addDataSource(dataSourceDTO.getPoolName(), dataSource);
        return source.getDataSources().keySet();
    }

}
