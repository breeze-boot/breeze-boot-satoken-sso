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

package com.breeze.boot.auth.controller;

import com.breeze.boot.auth.utils.UrlThreadLocal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 路由控制器
 *
 * @author gaoweixuan
 * @date 2023-04-24
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RouteController {

    /**
     * 租户接口
     */
    private final CommonService commonService;

    /**
     * 登录
     *
     * @param model   模型
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        Result<List<Map<String, Object>>> mapResult = this.commonService.selectTenant();
        List<Map<String, Object>> tenantMap = mapResult.getData();
        if (tenantMap == null) {
            model.addAttribute("loginError", "租户获取失败");
            return "error";
        }
        // 给login页面赋值
        model.addAttribute("tenant", tenantMap);
        Object redirect = request.getAttribute("redirect");
        if (Objects.isNull(redirect)) {
            redirect = UrlThreadLocal.get();
        }
        model.addAttribute("redirect", redirect);
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
