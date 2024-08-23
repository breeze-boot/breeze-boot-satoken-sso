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

package com.breeze.boot.config;

import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.breeze.boot.security.service.ISysRegisteredClientService;
import com.breeze.boot.security.service.ISysUserService;
import com.breeze.boot.security.service.impl.RemoteRegisterClientService;
import com.breeze.boot.security.service.impl.UserDetailService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.breeze.boot.core.constants.CacheConstants.LOGIN_USER;

/**
 * 资源服务器配置
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Configuration
@RequiredArgsConstructor
public class ResourceServerConfiguration {

    private final ApplicationContext context;

    private final CaptchaService captchaService;

    private final ISysRegisteredClientService registeredClientService;

    private final CacheManager cacheManager;

    private final ISysUserService userService;

    public String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * 注册客户端库
     *
     * @return {@link RegisteredClientRepository}
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new RemoteRegisterClientService(() -> registeredClientService);
    }

    /**
     * 用户服务
     *
     * @return {@link UserDetailService}
     */
    @Bean
    public UserDetailService userDetailService() {
        return new UserDetailService(() -> userService, (userInfoDTO) -> {
            Objects.requireNonNull(cacheManager.getCache(LOGIN_USER)).put(userInfoDTO.getUsername(), userInfoDTO);
        }, this::check);
    }

    private boolean check(HttpServletRequest contextRequest) {
        if (getActiveProfile().endsWith("dev")) {
            return true;
        }
        CaptchaVO captchaVO = new CaptchaVO();
        String captchaVerification = contextRequest.getParameter("captchaVerification");
        if (StrUtil.isBlank(captchaVerification)) {
            return false;
        }
        captchaVO.setCaptchaVerification(captchaVerification);
        ResponseModel response = captchaService.verification(captchaVO);
        //验证码校验失败，返回信息告诉前端
        //repCode  0000  无异常，代表成功
        //repCode  9999  服务器内部异常
        //repCode  0011  参数不能为空
        //repCode  6110  验证码已失效，请重新获取
        //repCode  6111  验证失败
        //repCode  6112  获取验证码失败,请联系管理员
        return response.isSuccess();
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("").version(appVersion)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}



