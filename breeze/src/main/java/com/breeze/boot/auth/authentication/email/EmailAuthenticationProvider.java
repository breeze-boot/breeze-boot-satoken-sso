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

package com.breeze.boot.auth.authentication.email;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.auth.domain.UserPrincipal;
import com.breeze.boot.auth.service.impl.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.breeze.boot.core.constants.CacheConstants.VALIDATE_EMAIL_CODE;

/**
 * 邮箱身份验证提供者
 * <p>
 * 参考：
 * {@link  org.springframework.security.authentication.dao.DaoAuthenticationProvider}
 *
 * @author gaoweixuan
 * @date 2022-09-03
 */
@Slf4j
public class EmailAuthenticationProvider implements AuthenticationProvider {

    /**
     * 电子邮件未获取到代码
     */
    private static final String EMAIL_NOT_FOUND_CODE = "emailNotFoundCode";

    /**
     * redis 模板
     */
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 用户详细信息服务
     */
    private final UserDetailService userDetailsService;

    /**
     * 电子邮件密码身份验证提供者
     *
     * @param userDetailsService 用户详细信息服务
     * @param redisTemplate      复述,模板
     */
    public EmailAuthenticationProvider(UserDetailService userDetailsService, RedisTemplate<String, String> redisTemplate) {
        this.userDetailsService = userDetailsService;
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
        try {
            UserPrincipal loadedUser = this.userDetailsService.loadUserByEmail((String) authentication.getPrincipal());
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            }
            // 验证码
            String validateCode = String.valueOf(this.redisTemplate.opsForValue().get(VALIDATE_EMAIL_CODE + loadedUser.getEmail()));
            String code = authentication.getCredentials().toString();
            if (!StrUtil.equals(validateCode, String.valueOf(code))) {
                log.debug("Failed to authenticate since code does not match stored value");
                throw new BadCredentialsException("Bad credentials");
            }
            EmailAuthenticationToken emailAuthenticationToken = new EmailAuthenticationToken(loadedUser, code, loadedUser.getAuthorities());
            emailAuthenticationToken.setDetails(loadedUser);
            return emailAuthenticationToken;
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException var4) {
            throw var4;
        } catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
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
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
