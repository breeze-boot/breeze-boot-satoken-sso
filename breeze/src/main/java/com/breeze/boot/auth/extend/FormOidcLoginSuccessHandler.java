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

package com.breeze.boot.auth.extend;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理程序
 *
 * @author gaoweixuan
 * @date 2023/05/11
 */
@RequiredArgsConstructor
public class FormOidcLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 发布保存系统的日志事件
     */
    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    /**
     * 身份验证成功
     * <p>
     * 由于RequestCache只保存了上一次的请求地址，自定义登录页使用controller【/login】中转了一下页面导致oidc的请求路径地址被覆盖，从线程本地获取
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 身份验证
     */
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SysLogBO sysLogBO = this.buildLog(request);
        try {
            // 从线程本地获取
            String redirect = request.getParameter("redirect");
            if (StrUtil.isNotBlank(redirect)) {
                response.sendRedirect(redirect);
            }
        } finally {
            // 保存日志
            this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
        }
    }

    /**
     * 建立日志
     *
     * @param request 请求
     */
    @SneakyThrows
    private SysLogBO buildLog(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return SysLogBO.builder()
                .systemModule("auth服务")
                .system(userAgent)
                .logTitle("登录日志")
                .logType(1)
                .doType(4)
                .resultMsg("登录成功")
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .result(1)
                .build();
    }


}
