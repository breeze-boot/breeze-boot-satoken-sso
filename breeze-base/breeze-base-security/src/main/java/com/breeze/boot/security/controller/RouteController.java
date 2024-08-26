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

package com.breeze.boot.security.controller;

import com.breeze.boot.security.service.ITenantService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * 路由控制器
 *
 * @author gaoweixuan
 * @since 2023-04-24
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RouteController {

    /**
     * 租户接口
     */
    private final ITenantService tenantService;

    /**
     * 登录
     *
     * @param model   模型
     * @return {@link String}
     */
    @RequestMapping("/login")
    public String login(Model model) {
        List<Map<String, Object>> tenantMap = this.tenantService.selectTenant();
        if (tenantMap == null) {
            model.addAttribute("loginError", "租户获取失败");
            return "error";
        }
        // 给login页面赋值
        model.addAttribute("tenant", tenantMap);
        return "login";
    }

    /**
     * 错误
     *
     * @param error 登录异常错误信息
     * @param model 模型
     * @return {@link String}
     */
    @SneakyThrows
    @GetMapping("/error")
    public String error(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("error", URLDecoder.decode(error, "UTF-8"));
        return "error";
    }

}
