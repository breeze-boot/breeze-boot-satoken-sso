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
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.ex.SystemServiceException;
import com.breeze.boot.database.annotation.DataPermission;
import com.breeze.boot.security.entity.LoginUserDTO;
import com.breeze.boot.security.entity.PermissionDTO;
import com.breeze.boot.security.utils.SecurityUtils;
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

import static com.breeze.boot.core.constants.DataPermissionType.*;

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
            DataPermission annotation = method.getAnnotation(DataPermission.class);
            // 判断是否存在注解且方法名一致
            if (annotation == null || !methodName.equals(method.getName())) {
                continue;
            }
            LoginUserDTO currentUser = SecurityUtils.getCurrentUser();
            if (currentUser == null) {
                throw new SystemServiceException(ResultCode.exception("未登录，数据权限不可实现"));
            }
            List<PermissionDTO> permissionList = currentUser.getPermissions();

            if (CollUtil.isEmpty(permissionList)) {
                continue;
            }
            String sql = this.getSql(permissionList, annotation);
            originalSql = String.format("SELECT a.* FROM (%s) a %s", originalSql, sql);
        }
        mpBs.sql(originalSql);
    }

    @NotNull
    private String getSql(List<PermissionDTO> permissions, DataPermission dataPermission) {
        StringBuilder sb = new StringBuilder();
        String operator = "";
        sb.append(" WHERE ");
        for (PermissionDTO permission : permissions) {
            operator = permission.getOperator();
            // 自定义sql
            String sql = permission.getStrSql();
            if (StrUtil.isAllNotBlank(sql)) {
                sb.append(permission.getOperator());
                sb.append(" ( ");
                sb.append(sql);
                sb.append(" ) ");
            }
            if (Objects.equals(permission.getPermissionType(), OWN)) {
                // create_by 字段
                if (StrUtil.isAllBlank(SecurityUtils.getUserCode())) {
                    continue;
                }
                sb.append(String.format("OR ( a.%s = '", dataPermission.own()));
                sb.append(SecurityUtils.getUserCode());
                sb.append(" ' ) ");
            } else if (Objects.equals(permission.getPermissionType(), DEPT_AND_LOWER_LEVEL) || Objects.equals(permission.getPermissionType(), DEPT_LEVEL)) {
                String deptIds = permission.getPermissions();
                // deptId 字段
                if (StrUtil.isAllBlank(deptIds)) {
                    continue;
                }
                sb.append(String.format("OR ( a.%s IN ( ", dataPermission.scope()));
                sb.append(deptIds);
                sb.append(" ) ) ");
            } else if (Objects.equals(permission.getPermissionType(), ALL)) {
                return sb.toString().replaceFirst("WHERE", "");
            } else if (Objects.equals(permission.getPermissionType(), DIY_DEPT)) {
                return sb.toString().replaceFirst("WHERE", "");
            }
        }
        if (StrUtil.isAllBlank(operator)) {
            return sb.toString().replaceFirst("OR", "");
        }
        return sb.toString().replaceFirst(operator, "");
    }

}
