/*
 * Copyright 2002-2020 the original author or authors.
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

package com.breeze.boot.system.controller.login;

import com.breeze.boot.core.Result;
import com.breeze.boot.jwtlogin.entity.CurrentLoginUser;
import com.breeze.boot.jwtlogin.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 你好控制器
 *
 * @author breeze
 * @date 2022-08-31
 */
@RestController
@RequestMapping("/test")
public class HelloController {

    /**
     * 你好，世界
     *
     * @param authentication 身份验证
     * @return {@link Result}<{@link String}>
     */
    @PreAuthorize("hasAnyAuthority('sys:test:hello')")
    @GetMapping("/hello")
    public Result<String> helloWorld(Authentication authentication) {
        CurrentLoginUser currentLoginUser = SecurityUtils.getCurrentLoginUser();
        currentLoginUser.getAuthorities().forEach((x) -> System.out.println(x.toString()));
        return Result.ok("Hello, " + authentication.getName() + "!");
    }

    /**
     * 获取用户
     *
     * @return {@link Authentication}
     */
    @GetMapping("/v1/getUser")
    public Authentication getUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 得到user
     *
     * @param principal 主要
     * @return {@link Principal}
     */
    @GetMapping("/v2/getUser")
    public Principal getUser2(Principal principal) {
        return principal;
    }

    /**
     * 得到user
     * 文档 「https://springdoc.org/#how-can-i-ignore-authenticationprincipal-parameter-from-spring-security」
     *
     * @return {@link Principal}
     */
    @GetMapping("/v3/getUser")
    public User getUser3(@AuthenticationPrincipal @Parameter(hidden = true) User user) {
        return user;
    }

    /**
     * 得到user4
     *
     * @param authentication 身份验证
     * @return {@link Object}
     */
    @GetMapping("/v4/getUser")
    public Object getUser4(Authentication authentication) {
        return authentication.getPrincipal();
    }

}
