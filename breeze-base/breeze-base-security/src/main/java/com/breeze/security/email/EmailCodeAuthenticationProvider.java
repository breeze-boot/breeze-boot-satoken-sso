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

package com.breeze.security.email;

import com.breeze.security.service.LocalUserDetailsService;
import com.breeze.security.userextension.CurrentLoginUser;
import com.breeze.security.utils.LoginCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/**
 * 短信密码身份验证提供者
 * <p>
 * 参考：
 * {@link  org.springframework.security.authentication.dao.DaoAuthenticationProvider}
 *
 * @author gaoweixuan
 * @date 2022-09-03
 */
@Slf4j
public class EmailCodeAuthenticationProvider implements AuthenticationProvider {

    /**
     * 电子邮件未获取到代码
     */
    private static final String EMAIL_NOT_FOUND_CODE = "emailNotFoundCode";
    /**
     * redis 模板
     */
    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * 用户详细信息服务
     */
    private final LocalUserDetailsService userDetailsService;
    /**
     * 登录检查
     */
    private final LoginCheck loginCheck;
    /**
     * 消息
     */
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    /**
     * 电子邮件密码身份验证提供者
     *
     * @param userDetailsService 用户详细信息服务
     * @param redisTemplate      复述,模板
     */
    public EmailCodeAuthenticationProvider(LocalUserDetailsService userDetailsService, RedisTemplate<String, Object> redisTemplate, LoginCheck loginCheck) {
        this.userDetailsService = userDetailsService;
        this.redisTemplate = redisTemplate;
        this.loginCheck = loginCheck;
    }

    /**
     * 进行身份验证
     *
     * @param authentication 身份验证
     * @return {@link Authentication}
     * @throws AuthenticationException 身份验证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailCodeAuthenticationToken authenticationToken = (EmailCodeAuthenticationToken) authentication;
        UserDetails userDetails = this.userDetailsService.loadUserByEmail((String) authenticationToken.getPrincipal());
        if (Objects.isNull(userDetails)) {
            throw new InternalAuthenticationServiceException(EMAIL_NOT_FOUND_CODE);
        }
        this.loginCheck.checkCode((CurrentLoginUser) userDetails, authenticationToken, loginUser -> {
            Object token = this.redisTemplate.opsForValue().get("sys:validate_code:" + loginUser.getEmail());
            if (Objects.isNull(token)) {
                log.debug("Failed to authenticate since no credentials provided");
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            return String.valueOf(token);
        });
        EmailCodeAuthenticationToken emailCodeAuthenticationToken = new EmailCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        emailCodeAuthenticationToken.setDetails(userDetails);
        return emailCodeAuthenticationToken;
    }

    /**
     * 支持
     * <p>
     * 此方法标识可以使用传入的当前的PROVIDER可以作为验证
     *
     * @param authentication 身份验证
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return EmailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
