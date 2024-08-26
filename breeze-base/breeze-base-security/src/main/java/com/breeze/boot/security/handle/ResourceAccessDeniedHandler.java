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

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拒绝访问处理程序
 * <p>
 * {@link AccessDeniedHandler} 用来解决认证过的用户访问无权限资源时的异常
 * 携带了合法token,但权限不足以访问其请求的资源：403
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Slf4j
public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理
     *
     * @param request               请求
     * @param response              响应
     * @param accessDeniedException 拒绝访问异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        log.error("[携带了合法token,但权限不足以访问其请求的资源：403]", accessDeniedException);
        // 此处不再返回使用异常拦截进行处理
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ResponseUtil.response(response, ResultCode.SC_FORBIDDEN);
    }

}
