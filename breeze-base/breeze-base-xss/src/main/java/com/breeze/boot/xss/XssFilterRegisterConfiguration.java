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

package com.breeze.boot.xss;

import com.breeze.boot.core.utils.LoadAnnotationUtils;
import com.breeze.boot.xss.config.XssProperties;
import com.breeze.boot.xss.filters.XssFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * xxs过滤器注册配置
 *
 * @author gaoweixuan
 * @since 2022-10-21
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({XssProperties.class})
public class XssFilterRegisterConfiguration {

    private final XssProperties xssProperties;
    private final ApplicationContext applicationContext;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * xss过滤器登记
     *
     * @return {@link XssFilter }
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public XssFilter xssFilter() {
        log.info("----- 初始化xss需要被过滤的路径开始 -----");
        LoadAnnotationUtils.loadControllerMapping(xssProperties, applicationContext, requestMappingHandlerMapping.getHandlerMethods());
        log.info("----- 初始化xss需要被过滤的路径结束 -----");
        return new XssFilter(this.xssProperties);
    }

}
