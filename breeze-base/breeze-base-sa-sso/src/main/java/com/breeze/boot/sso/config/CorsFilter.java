package com.breeze.boot.sso.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 跨域过滤器
 */
@Slf4j
@Order(-200)
@Configuration
public class CorsFilter extends OncePerRequestFilter {

    private static final String OPTIONS = "OPTIONS";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 允许指定域访问跨域资源
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许所有请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // 有效时间
        response.setHeader("Access-Control-Max-Age", "3600");
        // 允许的header参数
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,satoken");

        // 如果是预检请求，直接返回
        if (OPTIONS.equals(request.getMethod())) {
            log.info("=======================浏览器发来了OPTIONS预检请求==========");
            response.getWriter().print("");
            return;
        }

        log.info("*********************************过滤器被使用**************************");
        chain.doFilter(request, response);
    }
}
