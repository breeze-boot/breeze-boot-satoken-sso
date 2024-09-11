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

package com.breeze.boot.sso.controller;

import cn.dev33.satoken.sso.template.SaSsoUtil;
import cn.dev33.satoken.sso.util.SaSsoConsts;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前后台分离架构下集成SSO所需的代码 （SSO-Server端）
 *
 * @author gaoweixuan
 * @since 2024/09/11
 */
@RestController
public class SsoServerWebController {

    /**
     * 获取 redirectUrl
     *
     * @param redirect 重定向
     * @param mode     模式
     * @param client   客户
     * @return {@link SaResult }
     */
    @Deprecated
    @RequestMapping("/sso/getRedirectUrl")
    public SaResult getRedirectUrl(String redirect, String mode, String client) {
        // 未登录情况下，返回 code=401
        if (!StpUtil.isLogin()) {
            return SaResult.code(401);
        }
        // 已登录情况下，构建 redirectUrl
        redirect = SaFoxUtil.decoderUrl(redirect);
        if (SaSsoConsts.MODE_SIMPLE.equals(mode)) {
            // 模式一
            SaSsoUtil.checkRedirectUrl(redirect);
            return SaResult.data(redirect);
        }
        // 模式二或模式三
        String redirectUrl = SaSsoUtil.buildRedirectUrl(StpUtil.getLoginId(), client, redirect);
        return SaResult.data(redirectUrl);
    }

}
