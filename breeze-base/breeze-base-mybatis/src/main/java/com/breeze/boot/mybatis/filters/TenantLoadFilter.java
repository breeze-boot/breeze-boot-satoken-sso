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

package com.breeze.boot.mybatis.filters;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.core.utils.BreezeThreadLocal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.breeze.boot.core.constants.CoreConstants.PARAM;

/**
 * 系统租户加载过滤器
 *
 * @author gaoweixuan
 * @since 2022-11-08
 */
@Slf4j
@RequiredArgsConstructor
public class TenantLoadFilter extends OncePerRequestFilter {

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
        try {
            log.info("[当前进入的请求]： {}", request.getRequestURI());
            String apiTenantId = request.getHeader(PARAM);
            String webSocketTenantId = request.getParameter(PARAM);
            if (StrUtil.isAllBlank(apiTenantId) && StrUtil.isAllBlank(webSocketTenantId)) {
                // TODO 默认
            } else if (StrUtil.isAllNotBlank(apiTenantId)) {
                BreezeThreadLocal.set(Long.parseLong(apiTenantId));
            } else if (StrUtil.isAllNotBlank(webSocketTenantId)) {
                BreezeThreadLocal.set(Long.parseLong(webSocketTenantId));
            }
            filterChain.doFilter(request, response);
        } finally {
            BreezeThreadLocal.remove();
        }
    }

}
