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

package com.breeze.boot.sso.endpoint;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.processor.SaSsoServerProcessor;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.sso.spt.IUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sa-Token-SSO Server端 Controller
 *
 * @author gaoweixuan
 * @since 2024/09/11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SsoServerEndPoint {

    private final IUserDetailService userDetailService;

    /**
     * SSO-Server端：处理所有SSO相关请求
     * <p>
     * http://{host}:{port}/sso/auth			-- 单点登录授权地址，接受参数：redirect=授权重定向地址
     * http://{host}:{port}/sso/doLogin		-- 账号密码登录接口，接受参数：name、pwd
     * http://{host}:{port}/sso/checkTicket	-- Ticket校验接口（isHttp=true时打开），接受参数：ticket=ticket码、ssoLogoutCall=单点注销回调地址 [可选]
     * http://{host}:{port}/sso/signout		-- 单点注销地址（isSlo=true时打开），接受参数：loginId=账号id、sign=参数签名
     * </p>
     */
    @RequestMapping({"/sso/auth", "/sso/doLogin", "/sso/checkTicket", "/sso/signout"})
    public Object ssoServerRequest() {
        return SaSsoServerProcessor.instance.dister();
    }

    /**
     * 获取数据接口（用于在模式三下，为 client 端开放拉取数据的接口）
     */
    @RequestMapping("/sso/getData")
    public Result<UserPrincipal> userInfoData(String apiType, String loginId) {
        log.info("---------------- 获取数据 ----------------");
        // 校验签名：只有拥有正确秘钥发起的请求才能通过校验
        String client = SaHolder.getRequest().getParam("client");
        SaSsoServerProcessor.instance.ssoServerTemplate.getSignTemplate(client).checkRequest(SaHolder.getRequest());

        UserPrincipal userPrincipal = userDetailService.loadUserByUserId(loginId);
        // 自定义返回结果（模拟）
        return Result.ok(userPrincipal);
    }

}
