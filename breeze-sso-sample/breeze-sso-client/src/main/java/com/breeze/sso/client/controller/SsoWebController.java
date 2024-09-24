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

package com.breeze.sso.client.controller;

import cn.dev33.satoken.sso.model.SaCheckTicketResult;
import cn.dev33.satoken.sso.processor.SaSsoClientProcessor;
import cn.dev33.satoken.sso.template.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.breeze.boot.core.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前后台分离架构下集成SSO所需的代码 （SSO-Client端）
 */
@RestController
public class SsoWebController {

    /**
     * 当前是否登录
     *
     * @return {@link Object }
     */
    @RequestMapping("/sso/isLogin")
    public Result<Boolean> isLogin() {
        return Result.ok(StpUtil.isLogin());
    }

    /**
     * 返回SSO认证中心登录地址
     *
     * @param clientLoginUrl 客户端登录url
     * @return {@link SaResult }
     */
    @RequestMapping("/sso/getSsoAuthUrl")
    public Result<String> getSsoAuthUrl(String clientLoginUrl) {
        String serverAuthUrl = SaSsoUtil.buildServerAuthUrl(clientLoginUrl, "");
        return Result.ok(serverAuthUrl);
    }

    /**
     * 根据ticket进行登录
     *
     * @param ticket 票
     * @return {@link Result }<{@link String }>
     */
    @RequestMapping("/sso/doLoginByTicket")
    public Result<String> doLoginByTicket(String ticket) {
        SaCheckTicketResult ctr = SaSsoClientProcessor.instance.checkTicket(ticket, "/sso/doLoginByTicket");
        StpUtil.login(ctr.loginId, ctr.remainSessionTimeout);
        return Result.ok(StpUtil.getTokenValue());
    }

    /**
     * 全局异常拦截
     *
     * @param e e
     * @return {@link SaResult }
     */
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        return SaResult.error(e.getMessage());
    }

}
