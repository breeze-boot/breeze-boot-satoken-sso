/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.function.Function;

/**
 * 本地用户详细信息服务
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
public class LocalUserDetailsService implements UserDetailsService {

    protected Function<String, UserDetails> usernameFunction;

    protected Function<String, UserDetails> phoneFunction;

    protected Function<String, UserDetails> wxFunction;

    protected Function<String, UserDetails> emailFunction;

    public LocalUserDetailsService(Function<String, UserDetails> usernameFunction,
                                   Function<String, UserDetails> phoneFunction,
                                   Function<String, UserDetails> wxFunction,
                                   Function<String, UserDetails> emailFunction) {
        this.usernameFunction = usernameFunction;
        this.phoneFunction = phoneFunction;
        this.wxFunction = wxFunction;
        this.emailFunction = emailFunction;
    }

    public UserDetails loadUserByEmail(String email) {
        return this.emailFunction.apply(email);
    }

    public UserDetails loadUserByPhone(String phone) {
        return this.phoneFunction.apply(phone);
    }

    public UserDetails createOrLoadUserByOpenId(String openId) {
        return this.wxFunction.apply(openId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usernameFunction.apply(username);
    }

}
