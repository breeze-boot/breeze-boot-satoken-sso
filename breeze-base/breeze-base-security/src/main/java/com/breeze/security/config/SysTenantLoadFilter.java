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

package com.breeze.security.config;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import com.breeze.core.enums.ResultCode;
import com.breeze.core.ex.SystemServiceException;
import com.breeze.core.utils.BreezeThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 系统租户加载过滤器
 *
 * @author gaoweixuan
 * @date 2022-11-08
 */
@EnableConfigurationProperties(TenantWhiteListProperties.class)
public class SysTenantLoadFilter extends OncePerRequestFilter {

    AntPathMatcher matcher = new AntPathMatcher("/");

    /**
     * 租户白名单属性
     */
    @Autowired
    private TenantWhiteListProperties tenantWhiteListProperties;

    /**
     * 过滤器
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException servlet异常
     * @throws IOException      IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.match(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String apiTenantId = request.getHeader("b_tenant_id");
        String webSocketTenantId = request.getParameter("b_tenant_id");
        if (StrUtil.isAllBlank(apiTenantId) && StrUtil.isAllBlank(webSocketTenantId)) {
            throw new SystemServiceException(ResultCode.exception("缺少租户信息"));
        } else if (StrUtil.isAllNotBlank(apiTenantId)) {
            BreezeThreadLocal.set(Long.parseLong(apiTenantId));
        } else if (StrUtil.isAllNotBlank(webSocketTenantId)) {
            BreezeThreadLocal.set(Long.parseLong(webSocketTenantId));
        }
        filterChain.doFilter(request, response);
    }

    private boolean match(HttpServletRequest request) {
        List<String> ignoreUrls = this.tenantWhiteListProperties.getIgnoreUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (matcher.match(ignoreUrl, request.getRequestURI())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
