/*
 * Copyright (c) 2024, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.sso.client.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT token 过滤器
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@RequiredArgsConstructor
public class JwsTokenFilter extends OncePerRequestFilter {

    private final BreezeJwsTokenProvider breezeJwsTokenProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = breezeJwsTokenProvider.getTokenStr(request);
        try {
            if (StrUtil.isAllNotBlank(token)) {
                breezeJwsTokenProvider.verifyHMACToken(token);
                SecurityContextHolder.getContext().setAuthentication(breezeJwsTokenProvider.getAuthentication(token));
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            ResponseUtil.response(response, ResultCode.FAIL);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
