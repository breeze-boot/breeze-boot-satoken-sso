package com.breeze.security.config;

import lombok.Data;

/**
 * 身份验证属性
 *
 * @author breeze
 * @date 2023-04-07
 */
@Data
public class AuthProperties {

    /**
     * 应用程序id
     */
    private String clientId;

    /**
     * 应用程序秘密
     */
    private String clientSecret;

    /**
     * 重定向uri
     */
    private String redirectUri;

}
