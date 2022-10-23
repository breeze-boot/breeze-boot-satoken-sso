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

package com.breeze.boot.log.config;

import cn.hutool.core.date.StopWatch;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.dto.SysLogDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统日志方面
 *
 * @author breeze
 * @date 2022-10-19
 */
@Slf4j
@Aspect
public class SysLogAspect {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * 发布保存系统的日志事件
     */
    @Autowired
    private PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    /**
     * AOP 切点
     */
    @Pointcut("@annotation(com.breeze.boot.log.annotation.BreezeSysLog)")
    public void logPointcut() {
    }

    /**
     * 在
     * 处理完请求后执行此处代码
     *
     * @param joinPoint 切点
     * @return {@link Object}
     */
    @Around(value = "logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 方法名称
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 方法
        Method method = signature.getMethod();
        // 入参
        Object[] param = joinPoint.getArgs();
        // 注解
        BreezeSysLog breezeSysLog = method.getAnnotation(BreezeSysLog.class);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = null;
        SysLogDTO build = SysLogDTO.builder().build();
        try {
            build = SysLogDTO.builder()
                    .systemModule("通用权限系统")
                    .system("管理中心")
                    .logTitle(breezeSysLog.description())
                    .doType(breezeSysLog.type().getCode())
                    .logType(0)
                    .browser(request.getRemoteAddr())
                    .ip(request.getRemoteAddr())
                    .requestType(request.getMethod())
                    .paramContent(mapper.writeValueAsString(param))
                    .result(1)
                    .build();
            proceed = joinPoint.proceed();
            String resultJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(proceed);
            this.printLog(request, methodName, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(param), resultJson, stopWatch);
        } catch (Throwable e) {
            e.printStackTrace();
            build.setExMsg(e.getMessage());
            build.setResult(0);
        }
        stopWatch.stop();
        build.setTime(String.valueOf(stopWatch.getTotalTimeSeconds()));
        this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(build));
        return proceed;
    }

    /**
     * 打印日志
     *
     * @param request    请求
     * @param methodName 方法名称
     * @param jsonString json字符串
     * @param stopWatch  时间监听
     */
    private void printLog(HttpServletRequest request, String methodName, String jsonString, String resultJSON, StopWatch stopWatch) {
        log.info("HTTP_METHOD : {} , URL {} , IP : {}", request.getMethod(), request.getRequestURL(), request.getRemoteAddr());
        log.info("进入方法 [{}], \n 传入参数：\n {}", methodName, jsonString);
        log.info("方法[{}]执行结束, \n 返回值：\n {}", methodName, resultJSON);
        log.info("方法[{}]执行时间： {}", methodName, stopWatch.getTotalTimeSeconds());
    }

}
