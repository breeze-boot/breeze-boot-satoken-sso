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

package com.breeze.boot.mybatis.plugins;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.mybatis.annotation.BreezeDataPermission;
import com.breeze.boot.security.ext.LoginUser;
import com.breeze.boot.security.utils.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 数据权限内拦截器 TODO BUG
 *
 * @author gaoweixuan
 * @date 2022-10-28
 */
@Slf4j
public class BreezeDataPermissionInterceptor extends JsqlParserSupport implements InnerInterceptor {

    /**
     * 查询之前去拼装权限的sql
     *
     * @param executor      遗嘱执行人
     * @param ms            映射语句
     * @param parameter     参数
     * @param rowBounds     行范围
     * @param resultHandler 结果处理程序
     * @param boundSql      绑定sql
     */
    @SneakyThrows
    @Override
    public void beforeQuery(Executor executor,
                            MappedStatement ms,
                            Object parameter,
                            RowBounds rowBounds,
                            ResultHandler resultHandler,
                            BoundSql boundSql) {
        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
            return;
        }

        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String originalSql = boundSql.getSql();

        // 采用判断方法注解方式进行数据权限
        Class<?> clazz = Class.forName(ms.getId().substring(0, ms.getId().lastIndexOf(".")));
        // 获取方法名
        String methodName = ms.getId().substring(ms.getId().lastIndexOf(".") + 1);
        Method[] methods = clazz.getMethods();
        // 遍历类的方法
        for (Method method : methods) {
            BreezeDataPermission annotation = method.getAnnotation(BreezeDataPermission.class);
            // 判断是否存在注解且方法名一致
            if (Objects.isNull(annotation) || !methodName.equals(method.getName())) {
                continue;
            }

            LoginUser loginUser = SecurityUtils.getCurrentUser();
            if (loginUser == null) {
                throw new SystemServiceException(ResultCode.exception("未登录，数据权限不可实现"));
            }
            originalSql = this.getSql(loginUser, annotation, originalSql);
        }
        mpBs.sql(originalSql);
    }

    @SneakyThrows
    private String getSql(LoginUser loginUser, BreezeDataPermission dataPer, String originalSql) {
        List<String> columns = Lists.newArrayList();
        Select stmt = (Select) CCJSqlParserUtil.parse(originalSql);
        for (SelectItem selectItem : ((PlainSelect) stmt.getSelectBody()).getSelectItems()) {
            selectItem.accept(new SelectItemVisitorAdapter() {
                @Override
                public void visit(SelectExpressionItem item) {
                    // 获取字段别名 字段名
                    if (item.getAlias() == null) {
                        String expression = item.getExpression().toString();
                        columns.add("temp." + expression.substring(expression.lastIndexOf(".") + 1));
                    } else {
                        String name = item.getAlias().getName();
                        columns.add("temp." + name.substring(name.lastIndexOf(".") + 1));
                        log.debug(item.getAlias().getName(), item.getExpression());
                    }
                }
            });
        }
        String column = String.join(", ", columns);
        String permissions = loginUser.getPermissions();
        if (StrUtil.isAllBlank(permissions)) {
            // 无论注解设置什么，没有配置权限就不查询出任何数据
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE 1 = 2", column, originalSql);
        } else if (StrUtil.isNotBlank(dataPer.own())) {
            // 个人范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = '%s'", column, originalSql, dataPer.own(), loginUser.getUsername());
        } else if (StrUtil.isNotBlank(dataPer.scope())) {
            // 部门范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s IN (%s)", columns, originalSql, dataPer.scope(), permissions);
        }
        return originalSql;
    }

}
