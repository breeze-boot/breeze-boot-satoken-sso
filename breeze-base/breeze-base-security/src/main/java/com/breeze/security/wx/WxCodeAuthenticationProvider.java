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

package com.breeze.security.wx;

import com.breeze.security.config.WxLoginProperties;
import com.breeze.security.entity.WxLoginBody;
import com.breeze.security.service.LocalUserDetailsService;
import com.breeze.security.utils.WxHttpInterfaces;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/**
 * 微信Code身份验证提供者
 * <p>
 * 参考：
 * {@link  org.springframework.security.authentication.dao.DaoAuthenticationProvider}
 *
 * @author gaoweixuan
 * @date 2022-11-09
 */
@Slf4j
public class WxCodeAuthenticationProvider implements AuthenticationProvider {

    /**
     * 创建失败
     */
    private static final String CREATE_FAIL = "微信用户获取失败";

    /**
     * 用户详细信息服务
     */
    private final LocalUserDetailsService userDetailsService;

    /**
     * wx登录属性
     */
    private final WxLoginProperties wxLoginProperties;

    public WxCodeAuthenticationProvider(LocalUserDetailsService userDetailsService,
                                        WxLoginProperties wxLoginProperties) {
        this.userDetailsService = userDetailsService;
        this.wxLoginProperties = wxLoginProperties;
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
        WxCodeAuthenticationToken authenticationToken = (WxCodeAuthenticationToken) authentication;
        WxLoginBody wxLoginBody = (WxLoginBody) authenticationToken.getPrincipal();
        String openId = WxHttpInterfaces.getOpenId(this.wxLoginProperties.getAppId(), this.wxLoginProperties.getAppSecret(), wxLoginBody.getCode());
        wxLoginBody.setOpenId(openId);
        UserDetails userDetails = this.userDetailsService.createOrLoadUser(wxLoginBody);
        if (Objects.isNull(userDetails)) {
            throw new InternalAuthenticationServiceException(CREATE_FAIL);
        }
        WxCodeAuthenticationToken wxCodeAuthenticationToken = new WxCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        wxCodeAuthenticationToken.setDetails(userDetails);
        return wxCodeAuthenticationToken;
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
        return WxCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
