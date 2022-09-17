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

package com.breeze.boot.admin.controller.login;

import com.breeze.boot.core.Result;
import com.breeze.boot.jwtlogin.entity.CurrentLoginUser;
import com.breeze.boot.jwtlogin.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 你好控制器
 *
 * @author breeze
 * @date 2022-08-31
 */
@RestController
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

}
