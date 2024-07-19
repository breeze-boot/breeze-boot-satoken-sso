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
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.breeze.boot.core.base.CustomizePermission;
import com.breeze.boot.core.base.UserInfoDTO;
import com.breeze.boot.core.enums.DataPermissionType;
import com.breeze.boot.core.enums.DataRole;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.mybatis.annotation.BreezeDataPermission;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.breeze.boot.core.constants.CacheConstants.ROW_PERMISSION;
import static com.breeze.boot.core.enums.DataRole.getDataRoleByType;

/**
 * 数据权限内拦截器
 *
 * @author gaoweixuan
 * @since 2022-10-28
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
        Class<?> clazz = Class.forName(ms.getId().substring(0, ms.getId().lastIndexOf(StringPool.DOT)));
        // 获取方法名
        String methodName = ms.getId().substring(ms.getId().lastIndexOf(StringPool.DOT) + 1);
        Method[] methods = clazz.getMethods();
        // 遍历类的方法
        for (Method method : methods) {
            BreezeDataPermission annotation = method.getAnnotation(BreezeDataPermission.class);
            // 判断是否存在注解且方法名一致
            if (Objects.isNull(annotation) || !methodName.equals(method.getName())) {
                continue;
            }
            UserInfoDTO currentUser = SecurityUtils.getCurrentUser();
            originalSql = this.getSql(currentUser, annotation, originalSql);
        }
        mpBs.sql(originalSql);
    }

    @SneakyThrows
    private String getSql(UserInfoDTO currentUser, BreezeDataPermission dataPer, String originalSql) {
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
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
        // 获取当前用户的数据权限
        String permissionType = currentUser.getPermissionType();
        if (StrUtil.equals(DataPermissionType.ALL.getType(), permissionType)) {
            // 所有
            return originalSql;
        } else if (StrUtil.equals(DataPermissionType.DEPT_LEVEL.getType(), permissionType)) {
            // 所在部门范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = %s", column, originalSql, dataPer.dept().getColumn(), currentUser.getDeptId());
        } else if (StrUtil.equals(DataPermissionType.SUB_DEPT_LEVEL.getType(), permissionType)) {
            // 本级部门以及子部门
            // originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s IN (%s)", column, originalSql, dataPer.own().getColumn(), currentUser.getSubDeptId());
        } else if (StrUtil.equals(DataPermissionType.OWN.getType(), permissionType)) {
            // 个人范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = '%s'", column, originalSql, dataPer.own().getColumn(), currentUser.getUserId());
        } else if (StrUtil.equals(DataPermissionType.CUSTOMIZES.getType(), permissionType)) {
            // 自定义权限
            originalSql = getSqlString(currentUser, originalSql, cacheManager, column);
        }
        return originalSql;
    }

    @NotNull
    private static String getSqlString(UserInfoDTO currentUser, String originalSql, CacheManager cacheManager, String column) {
        Cache cache = cacheManager.getCache(ROW_PERMISSION);
        Set<String> rowPermissionCodeSet = currentUser.getRowPermissionCode();

        StringBuilder originalSqlBuilder = new StringBuilder();
        originalSqlBuilder.append(String.format("SELECT %s FROM (%s) temp WHERE 1 = 1 ", column, originalSql));
        if (cache == null) {
            throw new SystemServiceException(ResultCode.exception("自定义权限缓存未开启"));
        }
        for (String rowPermissionCode : rowPermissionCodeSet) {
            CustomizePermission sysCustomizePermission = cache.get(rowPermissionCode, CustomizePermission.class);
            if (sysCustomizePermission == null) {
                throw new SystemServiceException(ResultCode.exception("自定义权限缓存未开启"));
            }
            DataRole dataRole = getDataRoleByType(sysCustomizePermission.getCustomizesType());
            if (dataRole != null) {
                String permissions = sysCustomizePermission.getPermissions();
                originalSqlBuilder.append(String.format("AND temp.%s IN (%s) ", dataRole.getColumn(), permissions));
            }
        }
        originalSql = originalSqlBuilder.toString();
        return originalSql;
    }
}
