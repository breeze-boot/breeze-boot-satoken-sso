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

package com.breeze.boot.security.service.impl;

import com.breeze.boot.core.base.UserInfoDTO;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.BreezeThreadLocal;
import com.breeze.boot.security.model.entity.UserPrincipal;
import com.breeze.boot.security.service.ISysUserService;
import com.breeze.boot.security.service.IUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 用户主体服务
 *
 * @author gaoweixuan
 * @since 2023-04-21
 */
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements IUserDetailService {

    /**
     * 用户服务接口
     */
    private final Supplier<ISysUserService> userService;
    private final Consumer<UserInfoDTO> cacheManagerConsumer;
    private final Function<HttpServletRequest, Boolean> captchaServiceFunction;

    /**
     * 加载用户用户名
     *
     * @param username 用户名
     * @return {@link UserPrincipal}
     */
    @Override
    public UserPrincipal loadUserByUsername(String username) {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Assert.notNull(requestAttributes, "requestAttributes is null");

            this.getTenantId(requestAttributes);

            Boolean apply = this.captchaServiceFunction.apply(requestAttributes.getRequest());
            if (!apply) {
                throw new BreezeBizException(ResultCode.VERIFY_UN_FOUND);
            }
            UserInfoDTO userInfoDTO = this.getUserPrincipal(this.userService.get().loadUserByUsername(username));
            this.cacheManagerConsumer.accept(userInfoDTO);
            return this.convertResponseUserData(userInfoDTO);
        } finally {
            BreezeThreadLocal.remove();
        }
    }

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link UserDetails}
     */
    @Override
    public UserPrincipal loadUserByPhone(String phone) {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Assert.notNull(requestAttributes, "requestAttributes is null");
            this.getTenantId(requestAttributes);

            UserInfoDTO userInfoDTO = this.getUserPrincipal(this.userService.get().loadUserByPhone(phone));
            this.cacheManagerConsumer.accept(userInfoDTO);
            return this.convertResponseUserData(userInfoDTO);
        } finally {
            BreezeThreadLocal.remove();
        }
    }

    /**
     * 加载用户通过电子邮件
     *
     * @param email 邮箱
     * @return {@link Object}
     */
    @Override
    public UserPrincipal loadUserByEmail(String email) {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Assert.notNull(requestAttributes, "requestAttributes is null");
            this.getTenantId(requestAttributes);

            UserInfoDTO userInfoDTO = this.getUserPrincipal(this.userService.get().loadUserByEmail(email));
            this.cacheManagerConsumer.accept(userInfoDTO);
            return this.convertResponseUserData(userInfoDTO);
        } finally {
            BreezeThreadLocal.remove();
        }
    }

}
