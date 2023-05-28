package com.breeze.boot.auth.authentication.password;

import com.breeze.boot.auth.authentication.OAuth2ResourceOwnerAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * oauth2资源所有者密码身份验证令牌
 *
 * @author gaoweixuan
 * @date 2023-04-21
 */
public class OAuth2ResourceOwnerPasswordAuthenticationToken extends OAuth2ResourceOwnerAuthenticationToken {

    /**
     * oauth2资源所有者密码身份验证令牌构造
     *
     * @param authorizationGrantType 授权类型
     * @param clientPrincipal        the authenticated client principal
     * @param scopes                 作用域
     * @param additionalParameters   额外参数
     */
    public OAuth2ResourceOwnerPasswordAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                          Authentication clientPrincipal,
                                                          Set<String> scopes,
                                                          Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }
}
