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

package com.breeze.boot.jwtlogin.sms;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.jwtlogin.entity.CurrentLoginUser;
import com.breeze.boot.jwtlogin.service.IUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;

import java.util.Objects;

/**
 * 短信密码身份验证提供者
 * <p>
 * 参考：
 * {@link  org.springframework.security.authentication.dao.DaoAuthenticationProvider}
 *
 * @author breeze
 * @date 2022-09-03
 */
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    /**
     * 短信没有发现代码
     */
    private static final String SMS_NOT_FOUND_CODE = "phoneNotFoundCode";
    /**
     * 消息
     */
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    /**
     * redis 模板
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户详细信息服务
     */
    private IUserDetailsService userDetailsService;

    public SmsCodeAuthenticationProvider(IUserDetailsService userDetailsService, RedisTemplate redisTemplate) {
        this.setUserDetailsService(userDetailsService);
        this.setRedisTemplate(redisTemplate);
    }

    /**
     * 检查短信代码
     *
     * @param authentication 身份验证
     * @param loginUser      登录用户
     */
    private void checkSmsCode(CurrentLoginUser loginUser, SmsCodeAuthenticationToken authentication) {
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        Object codeObj = this.redisTemplate.opsForValue().get("sys：validate_code:" + loginUser.getPhone());
        if (Objects.isNull(codeObj)) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String presentCode = authentication.getCredentials().toString();
        if (!StrUtil.equals(presentCode, String.valueOf(codeObj))) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    /**
     * 获取用户详细信息服务
     *
     * @return {@link IUserDetailsService}
     */
    protected IUserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    /**
     * 设置用户详细信息服务
     *
     * @param userDetailsService 用户详细信息服务
     */
    public void setUserDetailsService(IUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * redis设置模板
     *
     * @param redisTemplate redis模板
     */
    protected void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
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
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        CurrentLoginUser currentLoginUser = this.getUserDetailsService().loadUserByPhone((String) authenticationToken.getPrincipal());
        if (Objects.isNull(currentLoginUser)) {
            throw new InternalAuthenticationServiceException(SMS_NOT_FOUND_CODE);
        }
        // this.checkSmsCode(currentLoginUser, authenticationToken);
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = new SmsCodeAuthenticationToken(currentLoginUser, currentLoginUser.getAuthorities());
        smsCodeAuthenticationToken.setDetails(currentLoginUser);
        return smsCodeAuthenticationToken;
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
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
