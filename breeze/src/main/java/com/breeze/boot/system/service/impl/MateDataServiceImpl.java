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

import com.breeze.boot.system.service.MateService;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 元数据服务impl
 *
 * @author gaoweixuan
 * @date 2022-11-12
 */
@Service
public class MateDataServiceImpl implements MateService {

    /**
     * 连接
     */
    Connection connection;

    /**
     * 元数据
     */
    DatabaseMetaData metaData;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void init() {
        connection = sqlSessionFactory.openSession().getConnection();
        try {
            metaData = connection.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 表名下拉框
     *
     * @return {@link List}<{@link Map}<{@link String}, {@link String}>>
     */
    @Override
    public List<Map<String, Object>> selectTable() {
        List<Map<String, Object>> tableList = new ArrayList<>();
        try {
            // 获取 表
            ResultSet rs = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"table"});
            while (rs.next()) {
                Map<String, Object> selectMap = Maps.newHashMap();
                selectMap.put("value", rs.getString("TABLE_NAME"));
                selectMap.put("label", rs.getString("TABLE_NAME"));
                tableList.add(selectMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    /**
     * 字段下拉框
     *
     * @param tableName
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<Map<String, Object>> selectColumn(String tableName) {
        List<Map<String, Object>> columnList = new ArrayList<>();
        try {
            // 获取 字段
            ResultSet rs = metaData.getColumns(connection.getCatalog(), null, tableName, null);
            while (rs.next()) {
                Map<String, Object> selectMap = Maps.newHashMap();
                selectMap.put("value", rs.getString("COLUMN_NAME"));
                selectMap.put("label", rs.getString("COLUMN_NAME"));
                columnList.add(selectMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }

}
