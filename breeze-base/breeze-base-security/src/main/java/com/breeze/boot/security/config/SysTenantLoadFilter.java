package com.breeze.boot.security.config;

import com.breeze.boot.core.utils.BreezeThreadLocal;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统租户加载过滤器
 *
 * @author breeze
 * @date 2022-11-08
 */
public class SysTenantLoadFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long tenantId = Long.valueOf(request.getHeader("tenantId"));
        BreezeThreadLocal.set(tenantId);
        filterChain.doFilter(request, response);
    }
}
