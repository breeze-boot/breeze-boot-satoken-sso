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
import com.breeze.boot.core.base.BaseLoginUser;
import com.breeze.boot.core.enums.DataPermissionCode;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.mybatis.annotation.BreezeDataPermission;
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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import static com.breeze.boot.core.constants.CacheConstants.LOGIN_USER;

/**
 * 数据权限内拦截器
 *
 * @author gaoweixuan
 * @since 2022-10-28
 */
@Slf4j
public class BreezeDataPermissionInterceptor extends JsqlParserSupport implements InnerInterceptor {

    /**
     * 通过缓存获取当前用户
     *
     * @return {@link BaseLoginUser}
     */
    public static BaseLoginUser getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        if (Objects.isNull(name)) {
            log.error("[未登录，获取不到登录名]");
            throw new SystemServiceException(ResultCode.UN_LOGIN);
        }
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(LOGIN_USER);
        if (Objects.isNull(cache)) {
            log.error("[混村不存在用户信息:{}]", name);
            throw new AccessDeniedException("用户未登录");
        }
        BaseLoginUser currentUser = cache.get(name, BaseLoginUser.class);
        if (Objects.isNull(currentUser)) {
            log.error("[登录名称对应的用户信息不存在:{}]", name);
            throw new SystemServiceException(ResultCode.UN_LOGIN);
        }
        return currentUser;
    }

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
            BaseLoginUser currentUser = getCurrentUser();
            originalSql = this.getSql(currentUser, annotation, originalSql);
        }
        mpBs.sql(originalSql);
    }

    @SneakyThrows
    private String getSql(BaseLoginUser currentUser, BreezeDataPermission dataPer, String originalSql) {
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
        String permissionCode = currentUser.getPermission().getPermissionCode();
        if (StrUtil.equals(DataPermissionCode.ALL.getCode(), permissionCode)) {
            // 所有
            return originalSql;
        } else if (StrUtil.equals(DataPermissionCode.DEPT_LEVEL.getCode(), permissionCode)) {
            // 所在部门范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = %s", columns, originalSql, dataPer.dept().getColumn(), currentUser.getDeptId());
        } else if (StrUtil.equals(DataPermissionCode.SUB_DEPT_LEVEL.getCode(), permissionCode)) {
            // 本级部门以及子部门
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = %s", column, originalSql, dataPer.own().getColumn(), currentUser.getDeptId());
        }  else if (StrUtil.equals(DataPermissionCode.OWN.getCode(), permissionCode)) {
            // 个人范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = '%s'", column, originalSql, dataPer.own().getColumn(), currentUser.getId());
        } else if (StrUtil.equals(DataPermissionCode.CUSTOMIZES.getCode(), permissionCode)) {
            // 自定义权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s IN (%s)", column, originalSql, dataPer.Customize().getColumn(), permissionCode);
        }
        return originalSql;
    }
}
