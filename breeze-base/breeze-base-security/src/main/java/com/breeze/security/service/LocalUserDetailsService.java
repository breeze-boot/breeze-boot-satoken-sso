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

package com.breeze.security.service;

import com.breeze.security.params.WxLoginParam;
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

    protected Function<WxLoginParam, UserDetails> wxFunction;

    protected Function<String, UserDetails> emailFunction;
    protected Function<String, UserDetails> wxPhoneFunction;

    public LocalUserDetailsService(Function<String, UserDetails> usernameFunction,
                                   Function<String, UserDetails> phoneFunction,
                                   Function<WxLoginParam, UserDetails> wxFunction,
                                   Function<String, UserDetails> emailFunction,
                                   Function<String, UserDetails> wxPhoneFunction) {
        this.usernameFunction = usernameFunction;
        this.phoneFunction = phoneFunction;
        this.wxFunction = wxFunction;
        this.emailFunction = emailFunction;
        this.wxPhoneFunction = wxPhoneFunction;
    }

    public UserDetails loadUserByEmail(String email) {
        return this.emailFunction.apply(email);
    }

    public UserDetails loadUserByPhone(String phone) {
        return this.phoneFunction.apply(phone);
    }

    public UserDetails createOrLoadUser(WxLoginParam wxLoginBody) {
        return this.wxFunction.apply(wxLoginBody);
    }

    public UserDetails createOrLoadUserByPhone(String phone) {
        return this.wxPhoneFunction.apply(phone);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usernameFunction.apply(username);
    }

}
