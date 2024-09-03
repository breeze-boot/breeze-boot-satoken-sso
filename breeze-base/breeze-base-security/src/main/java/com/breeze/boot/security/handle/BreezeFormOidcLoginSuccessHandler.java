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
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.BreezeThreadLocal;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

@Slf4j
@RequiredArgsConstructor
public class BreezeFormOidcLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    private final HttpSessionRequestCache sessionRequestCache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SysLogBO sysLogBO = this.buildLog(request);

        try {
            SavedRequest savedRequest = this.sessionRequestCache.getRequest(request, response);

            if (Objects.isNull(savedRequest)) {
                handleSuccess(request, response, authentication);
                return;
            }

            if (isAlwaysUseDefaultTargetUrl()) {
                this.sessionRequestCache.removeRequest(request, response);
                handleSuccess(request, response, authentication);
                return;
            }

            this.clearAuthenticationAttributes(request);

            String safeTargetUrl = this.buildSafeTargetUrl(savedRequest, String.valueOf(BreezeThreadLocal.get()));
            this.getRedirectStrategy().sendRedirect(request, response, safeTargetUrl);
        } catch (Exception e) {
            // 处理异常情况，比如记录错误日志
            log.error("Error occurred during authentication success handling", e);
        } finally {
            // 保存日志
            publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
        }
    }

    private void handleSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @SneakyThrows
    private String buildSafeTargetUrl(SavedRequest savedRequest, String tenantId) {
        String redirectUrl = savedRequest.getRedirectUrl();
        if (!isValidRedirectUrl(redirectUrl)) {
            throw new IllegalStateException("Unsafe redirect URL: " + redirectUrl);
        }
        return redirectUrl + "?tenantId=" + URLEncoder.encode(tenantId, StandardCharsets.UTF_8.toString());
    }

    private boolean isValidRedirectUrl(String url) {
        return StrUtil.isAllNotBlank(url);
    }


    /**
     * 建立日志
     *
     * @param request 请求
     */
    @SneakyThrows
    private SysLogBO buildLog(HttpServletRequest request) {
        String tenantId = request.getParameter(X_TENANT_ID);
        if (StrUtil.isBlank(tenantId)) {
            throw new BreezeBizException(ResultCode.TENANT_NOT_FOUND);
        }
        BreezeThreadLocal.set(Long.valueOf(tenantId));
        String userAgent = request.getHeader("User-Agent");
        return SysLogBO.builder().systemModule("auth服务").system(userAgent).logTitle("登录日志").logType(1).doType(4).resultMsg("登录成功").ip(request.getRemoteAddr()).requestType(request.getMethod()).result(1).build();
    }

}
