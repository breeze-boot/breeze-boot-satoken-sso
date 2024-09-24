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

package com.breeze.boot.security.sso.client.service.impl;

import com.breeze.boot.security.sso.client.model.User;
import com.breeze.boot.security.sso.client.service.UserService;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

/**
 * 用户服务impl
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User loadUserByUsername(String username) {
        User user = new User();
        user.setUserId(1L);
        user.setUsername(username);
        user.setPassword("{bcrypt}$2a$10$An69KbzJaPxu/E60d/r/zO4Tgy2fa0svuMAu1XybtzPpDI2kwgqt6");
        user.setStatus(1);
        user.setRoles(Sets.newHashSet("ROLE_ADMIN"));
        return user;
    }

}
