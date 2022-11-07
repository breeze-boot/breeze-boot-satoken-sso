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

package com.breeze.boot.system.exception;

import com.breeze.boot.core.Result;
import com.breeze.boot.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理程序
 *
 * @author breeze
 * @date 2022-10-10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 空指针统一异常处理
     *
     * @param ex 前女友
     * @return 错误信息
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<? extends Object> globalException(NullPointerException ex) {
        ex.printStackTrace();
        log.error("NullPointerException: {}", ex.getMessage());
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 参数类型异常
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public Result<? extends Object> parameterTypeException(HttpMessageConversionException ex) {
        log.error(ex.getMessage());
        return Result.fail(ResultCode.FAIL, "类型转换错误");
    }

    /**
     * 系统异常处理程序
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ExceptionHandler(SystemServiceException.class)
    public Result<? extends Object> systemExceptionHandler(SystemServiceException ex) {
        log.error("systemExceptionHandler ：{}", ex);
        return Result.fail(ex.getCode(), ex.getMessage(), ex.getDescription());
    }

    /**
     * 异常处理程序
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    public Result<? extends Object> exceptionHandler(Exception ex) {
        log.error("exception：{}", ex);
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * 运行时异常
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<? extends Object> runtimeException(RuntimeException ex) {
        log.error("runtimeException：{}", ex);
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR.getMsg());
    }

    /**
     * 拒绝访问异常
     *
     * @param ex 前女友
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Result<? extends Object> accessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException：{}", ex);
        return Result.fail(ResultCode.UNAUTHORIZED, ex.getMessage());
    }

    /**
     * 身份验证异常
     *
     * @param ex 前女友
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    public Result<? extends Object> authenticationException(AuthenticationException ex) {
        log.error("AccessDeniedException：{}", ex);
        return Result.fail(ResultCode.FORBIDDEN, ex.getMessage());
    }

}
