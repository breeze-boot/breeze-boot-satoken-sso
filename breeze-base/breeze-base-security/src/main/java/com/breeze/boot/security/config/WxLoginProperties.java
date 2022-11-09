package com.breeze.boot.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * wx登录属性
 *
 * @author breeze
 * @date 2022-11-09
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "breeze.wx")
public class WxLoginProperties {

    /**
     * 应用程序秘密
     */
    private String appSecret;

    /**
     * 应用程序id
     */
    private String appId;
}
