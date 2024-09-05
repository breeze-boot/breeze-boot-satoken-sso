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

package com.breeze.boot.mybatis.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.breeze.boot.mybatis.constants.BreezeStrPoolConstants;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import static cn.hutool.core.text.StrPool.C_COMMA;
import static cn.hutool.core.text.StrPool.C_DELIM_END;
import static com.breeze.boot.mybatis.constants.BreezeStrPoolConstants.*;

/**
 * 批量插入
 *
 * @author gaoweixuan
 * @since 2022-10-22
 */
public class BreezeInsertAllBatch extends AbstractMethod {

    /**
     * 批量插入所有数据
     * <p>
     * 使用有参构造，无参构造已过时
     *
     * @param name 方法名称
     */
    public BreezeInsertAllBatch(String name) {
        super(name);
    }

    /**
     * 注入映射语句
     *
     * @param mapperClass mapper类
     * @param modelClass  模型类
     * @param tableInfo   表信息
     * @return {@link MappedStatement}
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(BASE_SQL, tableInfo.getTableName(), this.prepareFieldSql(tableInfo), this.prepareValueSql(tableInfo)), modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, this.methodName, sqlSource, new NoKeyGenerator(), null, null);
    }

    /**
     * 拼装sql
     *
     * @param tableInfo 表信息
     * @return {@link StringBuilder}
     */
    private StringBuilder prepareFieldSql(TableInfo tableInfo) {
        StringBuilder fieldSql = new StringBuilder();
        fieldSql.append(tableInfo.getKeyColumn()).append(C_COMMA);
        tableInfo.getFieldList().forEach(x -> fieldSql.append(x.getColumn()).append(C_COMMA));
        // 拼装括号，删除最后的，
        fieldSql.insert(0, M_DELIM_START);
        fieldSql.delete(fieldSql.length() - 1, fieldSql.length());
        fieldSql.append(M_DELIM_END);
        return fieldSql;
    }

    /**
     * 准备价值sql
     *
     * @param tableInfo 表信息
     * @return {@link StringBuilder}
     */
    private StringBuilder prepareValueSql(TableInfo tableInfo) {
        StringBuilder valueSql = new StringBuilder();
        valueSql.append(FOREACH_START);
        valueSql.append(BreezeStrPoolConstants.ITEM_START).append(tableInfo.getKeyProperty()).append(C_DELIM_END + "" + C_COMMA);
        tableInfo.getFieldList().forEach(x -> valueSql.append(BreezeStrPoolConstants.ITEM_START).append(x.getProperty()).append(C_DELIM_END + "" + C_COMMA));
        valueSql.delete(valueSql.length() - 1, valueSql.length());
        valueSql.append(FOREACH_END);
        return valueSql;
    }

}
