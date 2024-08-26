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

package com.breeze.boot.security.handle;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义注销成功处理程序
 *
 * @author gaoweixuan
 * @since 2023/05/12
 */
public class BreezeLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 注销成功
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 身份验证
     */
    @SneakyThrows
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String referer = request.getHeader(HttpHeaders.REFERER);
        if (StrUtil.isBlank(referer)) {
            // 撒也不干
            return;
        }
        response.sendRedirect(referer);
    }

}
