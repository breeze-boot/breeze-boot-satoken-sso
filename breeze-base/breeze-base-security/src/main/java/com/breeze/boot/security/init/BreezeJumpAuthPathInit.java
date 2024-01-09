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

package com.breeze.boot.security.init;

import com.breeze.boot.core.utils.LoadAnnotationUtils;
import com.breeze.boot.security.config.IgnoreAuthProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 跳过认证注解切面
 *
 * @author gaoweixuan
 * @since 2022/08/31
 */
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(IgnoreAuthProperties.class)
public class BreezeJumpAuthPathInit implements InitializingBean {

    /**
     * 忽略的url
     */
    @Getter
    private final IgnoreAuthProperties ignoreAuthProperties;

    /**
     * 请求映射处理程序映射
     */
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 应用程序上下文
     */
    private final ApplicationContext applicationContext;

    /**
     * 请求
     *
     * @return {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 属性设置
     */
    @Override
    public void afterPropertiesSet() {
        log.info("----- 初始化auth需要被过滤的路径开始 -----");
        LoadAnnotationUtils.loadControllerMapping(ignoreAuthProperties, applicationContext, requestMappingHandlerMapping);
        log.info("----- 初始化auth需要被过滤的路径结束 -----");
    }
}
