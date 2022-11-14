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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志方面
 *
 * @author gaoweixuan
 * @date 2022-10-19
 */
@Slf4j
@Aspect
public class SysLogAspect {

    /**
     * 映射器
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * 发布保存系统的日志事件
     */
    @Autowired
    private PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @SneakyThrows
    @AfterReturning(pointcut = "@annotation(sysLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, BreezeSysLog sysLog, Object jsonResult) {
        this.doLog(joinPoint, sysLog, MAPPER.writeValueAsString(jsonResult));
    }

    /**
     * 后做了把
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param ex        异常
     * @param sysLog    系统日志
     */
    @AfterThrowing(value = "@annotation(sysLog)", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, BreezeSysLog sysLog, Exception ex) {
        this.doLog(joinPoint, sysLog, ex);
    }

    /**
     * 执行日志 业务
     *
     * @param joinPoint 连接点
     * @param log       日志
     * @param obj       obj
     */
    public void doLog(JoinPoint joinPoint, BreezeSysLog log, Object obj) {
        HttpServletRequest request = getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 方法名称
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 入参
        Object[] param = joinPoint.getArgs();
        this.doLog(log, request, methodName, param, obj);
    }

    /**
     * 执行日志 业务
     *
     * @param log        日志
     * @param request    请求
     * @param methodName 方法名称
     * @param param      参数
     * @param obj        obj
     */
    @SneakyThrows
    private void doLog(BreezeSysLog log, HttpServletRequest request, String methodName, Object[] param, Object obj) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        String userAgent = request.getHeader("User-Agent");
        SysLogDTO build = SysLogDTO.builder()
                .systemModule("通用权限系统")
                .system(userAgent)
                .logTitle(log.description())
                .doType(log.type().getCode())
                .logType(0)
                .browser(request.getRemoteAddr())
                .resultMsg("")
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .paramContent(MAPPER.writeValueAsString(param))
                .result(1)
                .build();
        if (obj instanceof Exception) {
            build.setResultMsg(((Exception) obj).getMessage());
            build.setResult(0);
        }
        stopWatch.stop();
        build.setTime(String.valueOf(stopWatch.getTotalTimeSeconds()));
        this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(build));
        this.printLog(request, methodName, MAPPER.writeValueAsString(param), stopWatch);
    }

    /**
     * @return {@link StopWatch}
     */
    private StopWatch getStopWatch() {
        return new StopWatch();
    }

    /**
     * 打印日志
     *
     * @param request    请求
     * @param methodName 方法名称
     * @param stopWatch  时间监听
     */
    private void printLog(HttpServletRequest request, String methodName, String jsonString, StopWatch stopWatch) {
        log.trace("HTTP_METHOD : {} , URL {} , IP : {}", request.getMethod(), request.getRequestURL(), request.getRemoteAddr());
        log.info("进入方法 [{}], \n 传入参数：\n {}", methodName, jsonString);
        log.trace("方法[{}]执行时间： {}", methodName, stopWatch.getTotalTimeMillis());
    }

    /**
     * 获取http servlet请求
     *
     * @return {@link HttpServletRequest}
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }

}
