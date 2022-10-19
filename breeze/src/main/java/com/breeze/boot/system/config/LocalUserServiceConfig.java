package com.breeze.boot.system.config;

import com.breeze.boot.security.service.LocalUserDetailsService;
import com.breeze.boot.system.service.impl.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本地用户服务配置
 *
 * @author breeze
 * @date 2022-08-31
 */
@Configuration
public class LocalUserServiceConfig {

    @Autowired
    private UserTokenService userTokenService;

    @Bean
    public LocalUserDetailsService loadCurrentLoginUser() {
        return new LocalUserDetailsService(
                userTokenService::loadUserByUsername
                , userTokenService::loadUserByPhone
                , userTokenService::loadUserByEmail);
    }

}
