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

package com.breeze.database.plugins;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.breeze.core.enums.ResultCode;
import com.breeze.core.ex.SystemServiceException;
import com.breeze.database.annotation.BreezeDataPermission;
import com.breeze.security.userextension.DataPermission;
import com.breeze.security.userextension.LoginUser;
import com.breeze.security.utils.SecurityUtils;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.breeze.core.constants.DataPermissionType.*;

/**
 * 数据权限内拦截器
 *
 * @author gaoweixuan
 * @date 2022-10-28
 */
@Slf4j
public class BreezeDataPermissionInterceptor extends JsqlParserSupport implements InnerInterceptor {

    /**
     * 使用数据权限的表
     */
    private final List<String> tables = Lists.newArrayList("sys_role_menu", "sys_menu", "sys_platform");

    /**
     * sql的表名
     *
     * @param sql sql
     * @return {@link List}<{@link String}>
     * @throws JSQLParserException jsqlparser例外
     */
    public static List<String> selectTable(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(selectStatement);
    }

    /**
     * 会查询
     *
     * @param executor      执行器
     * @param ms            映射语句
     * @param parameter     参数
     * @param rowBounds     行范围
     * @param resultHandler 结果处理程序
     * @param boundSql      绑定sql
     * @return boolean
     * @throws SQLException sqlexception异常
     */
    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        return InnerInterceptor.super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    /**
     * 之前查询
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
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
                            ResultHandler resultHandler, BoundSql boundSql) {
        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
            return;
        }

        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String originalSql = boundSql.getSql();

        // 采用判断方法注解方式进行数据权限
        Class<?> clazz = null;

        try {
            // 获取Mapper类
            clazz = Class.forName(ms.getId().substring(0, ms.getId().lastIndexOf(".")));
        } catch (ClassNotFoundException e) {
            log.error("类未发现异常", e);
        }

        // 获取方法名
        String methodName = ms.getId().substring(ms.getId().lastIndexOf(".") + 1);
        assert clazz != null;
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
            List<DataPermission> dataPermissionList = loginUser.getDataPermissions();

            if (CollUtil.isEmpty(dataPermissionList)) {
                continue;
            }
            String sql = this.getSql(dataPermissionList, annotation, originalSql);
            originalSql = String.format("SELECT a.* FROM (%s) a %s", originalSql, sql);
        }
        mpBs.sql(originalSql);
    }

    @NotNull
    private String getSql(List<DataPermission> dataPermissionList, BreezeDataPermission breezeDataPermission, String originalSql) {
        StringBuilder sb = new StringBuilder();
        String operator = dataPermissionList.get(0).getOperator();
        String sql;
        int index = 0;
        sb.append(" WHERE ");
        for (DataPermission permission : dataPermissionList) {
            if (Objects.equals(permission.getDataPermissionType(), OWN)) {
                if (StrUtil.isAllBlank(breezeDataPermission.own())) {
                    throw new SystemServiceException(ResultCode.exception("[OWN= ? ]注解未指定条件字段" + breezeDataPermission.own()));
                }
                if (!originalSql.contains(breezeDataPermission.own())) {
                    throw new SystemServiceException(ResultCode.exception("sql中缺少字段" + breezeDataPermission.own()));
                }
                // create_by 字段
                if (StrUtil.isAllBlank(SecurityUtils.getUserCode())) {
                    continue;
                }

                sb.append(String.format("%s a.%s = '", permission.getOperator(), breezeDataPermission.own()));
                sb.append(SecurityUtils.getUserCode());
                sb.append("' ");
                index++;
            } else if (Objects.equals(permission.getDataPermissionType(), ALL) && index == 0) {
                // 没有自定义SQL
                return sb.toString().replaceFirst("WHERE", "");
            } else if (Objects.equals(permission.getDataPermissionType(), DEPT_AND_LOWER_LEVEL) || Objects.equals(permission.getDataPermissionType(), DEPT_LEVEL)) {
                if (StrUtil.isAllBlank(breezeDataPermission.scope())) {
                    throw new SystemServiceException(ResultCode.exception("[SCOPE= ? ]注解未指定条件字段" + breezeDataPermission.scope()));
                }
                if (!originalSql.contains(breezeDataPermission.scope())) {
                    throw new SystemServiceException(ResultCode.exception("sql中缺少字段" + breezeDataPermission.scope()));
                }
                String deptIds = permission.getDataPermissions();

                // deptId 字段
                if (StrUtil.isAllBlank(deptIds)) {
                    continue;
                }
                sb.append(String.format("%s a.%s IN ( ", permission.getOperator(), breezeDataPermission.scope()));
                sb.append(deptIds);
                sb.append(" ) ");
                index++;
            }
            // 自定义sql
            sql = permission.getStrSql();
            if (StrUtil.isAllNotBlank(sql)) {
                sb.append(permission.getOperator());
                sb.append(" ( ");
                sb.append(sql);
                sb.append(" ) ");
                index++;
            }
        }
        if (StrUtil.isAllNotBlank(sb.toString())) {
            return sb.toString().replaceFirst(operator, "");
        }
        return sb.toString();
    }

}
