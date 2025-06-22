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

package com.breeze.boot.security.sso.client.security.mobile.password;

import com.breeze.boot.security.sso.client.security.exception.BizException;
import com.breeze.boot.security.sso.client.security.service.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 手机号验证码认证提供商
 *
 * @author gaoweixuan
 * @since 2025/06/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MobilePasswordAuthenticationProvider implements AuthenticationProvider, UserDetailsChecker {

    private final SysUserDetailsService sysUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    /**
     * 支持MobilePasswordAuthenticationToken认证
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return MobilePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }

    /**
     * 认证
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        MobilePasswordAuthenticationToken token = (MobilePasswordAuthenticationToken) authentication;
        String mobile = (String) token.getPrincipal();
        if (mobile == null) {
            throw new BadCredentialsException("无法获取手机信息");
        }
        // 根据手机号查询用户信息UserDetails
        UserDetails userDetails = sysUserDetailsService.loadUserByMobile(mobile);
        return getMobilePasswordAuthenticationToken(userDetails, token);
    }

    private MobilePasswordAuthenticationToken getMobilePasswordAuthenticationToken(UserDetails userDetails, MobilePasswordAuthenticationToken token) {
        if (Objects.isNull(userDetails)) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        String password = (String) token.getCredentials();
        if (password == null) {
            throw new BizException("密码为空");
        }
        this.check(userDetails);
        additionalAuthenticationChecks(userDetails, token);

        MobilePasswordAuthenticationToken result = new MobilePasswordAuthenticationToken(userDetails, password, new ArrayList<>());
        result.setDetails(token.getDetails());
        return result;
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, MobilePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.error("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            log.error("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    @Override
    public void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            log.error("Failed to authenticate since user account is locked");
            throw new LockedException(MobilePasswordAuthenticationProvider.this.messages.getMessage("MobilePasswordAuthenticationProvider.locked", "User account is locked"));
        }
        if (!user.isEnabled()) {
            log.error("Failed to authenticate since user account is disabled");
            throw new DisabledException(MobilePasswordAuthenticationProvider.this.messages.getMessage("MobilePasswordAuthenticationProvider.disabled", "User is disabled"));
        }
        if (!user.isAccountNonExpired()) {
            log.error("Failed to authenticate since user account has expired");
            throw new AccountExpiredException(MobilePasswordAuthenticationProvider.this.messages.getMessage("MobilePasswordAuthenticationProvider.expired", "User account has expired"));
        }
    }
}
