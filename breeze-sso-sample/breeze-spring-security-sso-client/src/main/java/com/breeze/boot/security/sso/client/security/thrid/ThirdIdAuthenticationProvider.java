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

package com.breeze.boot.security.sso.client.security.thrid;

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
 * 第三方ID认证提供商
 *
 * @author gaoweixuan
 * @since 2025/03/14
 */
@Configuration
@RequiredArgsConstructor
public class ThirdIdAuthenticationProvider implements AuthenticationProvider {

    private final SysUserDetailsService sysUserDetailsService;


    /**
     * 支持PhoneCodeAuthenticationToken认证
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return ThirdIdAuthenticationToken.class.isAssignableFrom(aClass);
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
        ThirdIdAuthenticationToken token = (ThirdIdAuthenticationToken) authentication;
        String loginId = (String) token.getPrincipal();
        if (loginId == null) {
            throw new BadCredentialsException("无法获取登录信息");
        }
        // 根据手机号查询用户信息UserDetails
        UserDetails userDetails = sysUserDetailsService.loadUserByLoginId(loginId);
        return getThirdIdAuthenticationToken(userDetails, token);
    }

    private static ThirdIdAuthenticationToken getThirdIdAuthenticationToken(UserDetails userDetails, ThirdIdAuthenticationToken token) {
        if (Objects.isNull(userDetails)) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        String pwd = (String) token.getCredentials();
        if (pwd == null) {
            throw new BizException("密码为空");
        }
        ThirdIdAuthenticationToken result =
                new ThirdIdAuthenticationToken(userDetails, pwd, new ArrayList<>());
        result.setDetails(token.getDetails());
        return result;
    }

}
