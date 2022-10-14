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

package com.breeze.boot.jwtlogin.config;

import com.breeze.boot.jwtlogin.annotation.JoinWhiteList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 微风中加入白色方面
 * 项目初始化的时候去加载使用注解的URL加入到 白名单 中、
 * <p>
 * TODO
 * ps：目前测试没起作用
 *
 * @author breeze
 * @date 2022/08/31
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Configuration
public class BreezeJoinTheWhiteAspect implements InitializingBean {

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
     * 忽略url属性
     */
    @Autowired
    private IgnoreUrlProperties ignoreUrlProperties;

    /**
     * 获得实例
     *
     * @return {@link RestTemplate}
     */
    @Bean
    public RestTemplate getInstance() {
        return new RestTemplate();
    }

    /**
     * 属性设置
     */
    @Override
    public void afterPropertiesSet() {
        // 获取全部的请求方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethodMap.forEach((requestMappingInfo, method) -> {
            Class clazz = applicationContext.getBean(method.getBean().toString()).getClass();
            Set<String> methodUrls = requestMappingInfo.getPatternsCondition().getPatterns();
            if (Objects.isNull(AnnotationUtils.findAnnotation(clazz, JoinWhiteList.class))) {
                this.setUrl(method, methodUrls, requestMappingInfo.getMethodsCondition());
            } else {
                methodUrls.forEach(handlerMethod -> {
                    log.info("已经加入白名单 ====> {}:{}", handlerMethod, requestMappingInfo.getMethodsCondition().getMethods().toArray()[0].toString());
                    this.ignoreUrlProperties.getExcludeUrls().add(handlerMethod);
                });
            }
        });
    }

    /**
     * 设置地址
     *
     * @param method           方法
     * @param methodUrls       方法url
     * @param methodsCondition 方法条件
     */
    private void setUrl(HandlerMethod method, Set<String> methodUrls, RequestMethodsRequestCondition methodsCondition) {
        methodUrls.forEach(handlerMethod -> {
            if (filterHandlerMethod(method)) {
                log.info("已经加入白名单 ====> {}:{}", handlerMethod, methodsCondition.getMethods().toArray()[0].toString());
                this.ignoreUrlProperties.getExcludeUrls().add(handlerMethod);
            }
        });
    }

    /**
     * 过滤器处理程序方法
     *
     * @param method 方法
     * @return {@link Boolean}
     */
    private Boolean filterHandlerMethod(HandlerMethod method) {
        JoinWhiteList joinWhiteList = method.getMethodAnnotation(JoinWhiteList.class);
        return Objects.isNull(joinWhiteList) ? Boolean.FALSE : Boolean.TRUE;
    }

}
