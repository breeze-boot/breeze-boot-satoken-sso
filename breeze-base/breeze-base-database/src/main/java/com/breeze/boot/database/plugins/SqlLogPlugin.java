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

package com.breeze.boot.database.plugins;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * sql记录拦截器
 *
 * @author breeze
 * @date 2022-10-23
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class SqlLogPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (invocation.getArgs().length <= 1) {
            return invocation.proceed();
        }
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        StopWatch stopWatch = new StopWatch();
        Object returnValue;
        try {
            stopWatch.start();
            returnValue = invocation.proceed();
        } finally {
            stopWatch.stop();
            String sql = showSql(configuration, boundSql);
            log.info("sql： \n {} \n 花费时间: {}", sql, stopWatch.getTotalTimeSeconds());
        }
        return returnValue;
    }


    private String showSql(Configuration configuration, BoundSql boundSql) {
        // 替换空格、换行、tab缩进
        String sql = this.formatSql(boundSql);
        if (StrUtil.isAllBlank(sql)) {
            return "";
        }
        // 入参
        Object parameterObject = boundSql.getParameterObject();
        // #{值}
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (Objects.isNull(parameterObject) || CollUtil.isEmpty(parameterMappings)) {
            return sql;
        }
        // 类型处理器
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            // 简单类型的传参
            sql = sql.replaceFirst("\\?", replace(parameterObject));
        } else {
            // 自定义类型的传参
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            // parameterMappings：#{ 参数 }
            // 遍历参数名 获取 参数值
            for (ParameterMapping parameterMapping : parameterMappings) {
                // #{propertyName}
                String propertyName = parameterMapping.getProperty();
                // 判断是否有getter方法
                if (metaObject.hasGetter(propertyName)) {
                    sql = replace(sql, metaObject.getValue(propertyName));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    // 判断是否有动态参数
                    // #{user.?}
                    sql = replace(sql, boundSql.getAdditionalParameter(propertyName));
                }
            }
        }
        return sql;
    }

    @NotNull
    private String replace(String sql, Object additionalParameter) {
        sql = sql.replaceFirst("\\?", replace(additionalParameter));
        return sql;
    }

    @NotNull
    private String formatSql(BoundSql boundSql) {
        return Optional.ofNullable(boundSql.getSql())
                .orElse("")
                .replaceAll("[\\s]+", " ");
    }

    private String replace(Object obj) {
        String resultStr;
        //
        if (obj instanceof String) {
            resultStr = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            resultStr = "'" + formatter.format(new Date()) + "'";
        } else if (obj instanceof LocalDateTime) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            resultStr = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                resultStr = obj.toString();
            } else {
                resultStr = "";
            }
        }
        return resultStr.replace("$", "\\$");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
