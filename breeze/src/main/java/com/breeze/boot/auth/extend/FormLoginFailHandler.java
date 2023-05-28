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

import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 登录失败处理程序
 *
 * @author gaoweixuan
 * @date 2023/05/13
 */
@Slf4j
@RequiredArgsConstructor
public class FormLoginFailHandler implements AuthenticationFailureHandler {

    /**
     * 发布保存系统的日志事件
     */
    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    /**
     * 在身份验证失败
     *
     * @param request   请求
     * @param response  响应
     * @param exception 异常
     */
    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("[校验 {}] ", exception.getMessage(), exception);
        SysLogBO sysLogBO = this.buildLog(request);
        try {
            response.sendRedirect("/auth/error?error=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
        } finally {
            // 保存日志
            sysLogBO.setResultMsg(exception.getMessage());
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
                .resultMsg("登录失败")
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .result(0)
                .build();
    }

}
