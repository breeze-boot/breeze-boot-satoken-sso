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

package com.breeze.boot.security.wxphone;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 微信手机号身份验证令牌
 * <p>
 * 参考：
 * {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
 * <p>
 *
 * @author gaoweixuan
 * @date 2023-02-28
 */
public class WxPhoneAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 主要
     */
    private final Object principal;

    /**
     * 凭证
     */
    private Object credentials;

    /**
     * 邮箱代码身份验证令牌
     *
     * @param principal   主要
     * @param credentials 凭证
     */
    public WxPhoneAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 邮箱代码身份验证令牌
     *
     * @param principal   主要
     * @param credentials 凭证
     * @param authorities 当局
     */
    public WxPhoneAuthenticationToken(Object principal, Object credentials,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    /**
     * 未经身份验证
     * <p>
     * This factory method can be safely used by any code that wishes to create a unauthenticated <code>SmsCodeAuthenticationToken</code>.
     *
     * @param principal   主要
     * @param credentials 凭证
     * @return {@link WxCodeAuthenticationToken}
     */
    public static WxPhoneAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new WxPhoneAuthenticationToken(principal, credentials);
    }

    /**
     * 通过身份验证
     * <p>
     * This factory method can be safely used by any code that wishes to create a authenticated <code>SmsCodeAuthenticationToken</code>.
     *
     * @param principal   主要
     * @param credentials 凭证
     * @param authorities 当局
     * @return {@link WxCodeAuthenticationToken}
     */
    public static WxPhoneAuthenticationToken authenticated(Object principal, Object credentials,
                                                           Collection<? extends GrantedAuthority> authorities) {
        return new WxPhoneAuthenticationToken(principal, credentials, authorities);
    }

    /**
     * 获得证书
     *
     * @return {@link Object}
     */
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    /**
     * 获取 principal
     *
     * @return {@link Object}
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * 设置身份验证
     *
     * @param isAuthenticated 身份验证
     * @throws IllegalArgumentException 非法参数异常
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    /**
     * 删除凭证
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
