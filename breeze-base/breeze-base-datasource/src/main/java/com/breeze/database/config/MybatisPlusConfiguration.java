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

package com.breeze.database.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.breeze.core.utils.BreezeThreadLocal;
import com.breeze.database.plugins.BreezeDataPermissionInterceptor;
import com.breeze.database.plugins.BreezeSqlLogInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.Objects;

/**
 * mybatis +配置
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
public class MybatisPlusConfiguration {

    /**
     * 自定义 SqlInjector 包含自定义的全局方法
     */
    @Bean
    public BreezeLogicSqlInjector logicSqlInjector() {
        return new BreezeLogicSqlInjector();
    }

    /**
     * mybatis +拦截器
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {

            @Override
            public Expression getTenantId() {
                Long tenantId = BreezeThreadLocal.get();
                if (Objects.isNull(tenantId)) {
                    return new LongValue(1);
                }
                return new LongValue(tenantId);
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            /**
             * 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return Objects.equals("sys_tenant", tableName) || tableName.toLowerCase(Locale.ROOT).startsWith("act") || tableName.startsWith("process");
            }
        }));
        interceptor.addInnerInterceptor(new BreezeDataPermissionInterceptor());
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new BreezeSqlLogInnerInterceptor());
        return interceptor;
    }

}

