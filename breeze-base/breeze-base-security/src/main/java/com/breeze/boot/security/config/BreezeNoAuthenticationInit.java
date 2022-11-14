/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.config;

import cn.hutool.core.util.ReUtil;
import com.breeze.boot.security.annotation.NoAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 无需认证注解切面
 *
 * @author gaoweixuan
 * @date 2022/08/31
 */
@Slf4j
@EnableConfigurationProperties(IgnoreUrlProperties.class)
public class BreezeNoAuthenticationInit implements InitializingBean {

    /**
     * 正则
     */
    private static final String PATTERN = "\\{(.*?)\\}";

    /**
     * 忽略的url
     */
    @Autowired
    public IgnoreUrlProperties ignoreUrlProperties;

    /**
     * 请求映射处理程序映射
     */
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 应用程序上下文
     */
    @Autowired
    private ApplicationContext applicationContext;

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
        log.info("{}", this.ignoreUrlProperties.getIgnoreUrls());
        // 获取全部的请求方法
        Map<RequestMappingInfo, HandlerMethod> methodMap = this.requestMappingHandlerMapping.getHandlerMethods();
        methodMap.forEach((requestMappingInfo, method) -> {
            Class<?> clazz = this.applicationContext.getBean(method.getBean().toString()).getClass();
            if (Objects.nonNull(AnnotationUtils.findAnnotation(clazz, NoAuthentication.class))) {
                Optional.ofNullable(requestMappingInfo.getPathPatternsCondition())
                        .ifPresent((condition) -> condition.getPatternValues().forEach(url -> {
                            log.info("{}", ReUtil.replaceAll(url, PATTERN, "*"));
                            this.ignoreUrlProperties.getIgnoreUrls().add(ReUtil.replaceAll(url, PATTERN, "*"));
                        }));
            } else {
                Optional.ofNullable(requestMappingInfo.getPathPatternsCondition()).ifPresent((condition) -> this.setIgnoreUrl(method, condition.getPatternValues()));
            }
        });
    }

    /**
     * 设置忽略url
     *
     * @param method       方法
     * @param pathPatterns 路径模式
     */
    private void setIgnoreUrl(HandlerMethod method, Set<String> pathPatterns) {
        if (Objects.nonNull(method.getMethodAnnotation(NoAuthentication.class))) {
            pathPatterns.forEach(url -> {
                log.info("{}", ReUtil.replaceAll(url, PATTERN, "*"));
                this.ignoreUrlProperties.getIgnoreUrls().add(ReUtil.replaceAll(url, PATTERN, "*"));
            });
        }
    }

}
