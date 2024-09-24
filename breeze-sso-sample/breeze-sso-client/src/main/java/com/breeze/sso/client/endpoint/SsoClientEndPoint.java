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

package com.breeze.sso.client.endpoint;

import cn.dev33.satoken.sso.SaSsoManager;
import cn.dev33.satoken.sso.processor.SaSsoClientProcessor;
import cn.dev33.satoken.sso.template.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Sa-Token-SSO Client端
 */
@RestController
@Slf4j
public class SsoClientEndPoint {

    /**
     * sso请求
     *
     * @return {@link Object }
     * <p>
     * SSO-Client端：处理所有SSO相关请求
     * http://{host}:{port}/sso/login			-- Client端登录地址，接受参数：back=登录后的跳转地址
     * http://{host}:{port}/sso/logout			-- Client端单点注销地址（isSlo=true时打开），接受参数：back=注销后的跳转地址
     * http://{host}:{port}/sso/logoutCall		-- Client端单点注销回调地址（isSlo=true时打开），此接口为框架回调，开发者无需关心
     */
    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoClientProcessor.instance.dister();
    }

    /**
     * index
     *
     * @return {@link Object }
     */
    @RequestMapping("/")
    public Object index() {
        return "仅支持前后端分离模式";
    }

    /**
     * 用户信息
     * 查询我的账号信息
     *
     * @return {@link Object }
     */
    @RequestMapping("/sso/userInfo")
    public Object userInfo(@RequestHeader("X-Tenant-Id") String XTenantId) {
        // 组织请求参数
        Map<String, Object> map = new HashMap<>();
        map.put("apiType", "userinfo");
        map.put("loginId", StpUtil.getLoginId());
        map.put("client", SaSsoManager.getClientConfig().getClient());
        map.put("X-Tenant-Id", XTenantId);
        // 发起请求
        Object resData = SaSsoUtil.getData(map);
        log.info("sso-server 返回的用户信息：{}", resData);

        return resData;
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
