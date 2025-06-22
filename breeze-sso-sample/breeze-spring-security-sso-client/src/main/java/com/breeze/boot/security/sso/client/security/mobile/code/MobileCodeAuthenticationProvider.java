/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.sso.client.security.mobile.code;

import com.breeze.boot.security.sso.client.security.exception.BizException;
import com.breeze.boot.security.sso.client.security.service.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 手机号验证码认证提供商
 *
 * @author gaoweixuan
 * @since 2025/06/19
 */
@Configuration
@RequiredArgsConstructor
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {

    private final SysUserDetailsService sysUserDetailsService;


    /**
     * 支持MobileCodeAuthenticationProvider认证
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return MobileCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }

    /**
     * 认证
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        MobileCodeAuthenticationToken token = (MobileCodeAuthenticationToken) authentication;
        String mobile = (String) token.getPrincipal();
        if (mobile == null) {
            throw new BadCredentialsException("无法获取电话信息");
        }
        UserDetails userDetails = sysUserDetailsService.loadUserByMobile(mobile);
        return getMobileCodeAuthenticationToken(userDetails, token);
    }

    private static MobileCodeAuthenticationToken getMobileCodeAuthenticationToken(UserDetails userDetails, MobileCodeAuthenticationToken token) {
        if (Objects.isNull(userDetails)) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        String code = (String) token.getCredentials();
        if (code == null) {
            throw new BizException("验证码为空");
        }
        if(!"123456".equals(code)){
            throw new BizException("验证码错误");
        }
        MobileCodeAuthenticationToken result =
                new MobileCodeAuthenticationToken(userDetails, code, new ArrayList<>());
        result.setDetails(token.getDetails());
        return result;
    }
}
