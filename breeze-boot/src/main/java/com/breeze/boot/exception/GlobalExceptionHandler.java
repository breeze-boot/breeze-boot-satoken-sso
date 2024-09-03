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

package com.breeze.boot.exception;

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.MessageUtil;
import com.breeze.boot.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理程序
 *
 * @author gaoweixuan
 * @since 2022-10-10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求参数格式错误
     * <p>
     * eg: @RequestBody上使用@Valid 实体上使用@NotNull
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException 处理请求参数格式错误：", ex);
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

    /**
     * http请求方法不支持异常
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException 请求类型不支持：", ex);
        return Result.fail(ex.getMessage());
    }

    /**
     * 绑定异常处理程序
     * <p>
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(BindException.class)
    public Result<?> bindExceptionHandler(BindException ex) {
        log.error("BindException 验证路径中请求实体校验失败后抛出的异常：", ex);
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

    /**
     * 处理请求参数格式错误
     * <p>
     * eg: @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.error("ConstraintViolationException 处理请求参数格式错误：", ex);
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

    /**
     * http消息不可读异常处理程序
     * <p>
     * 参数格式异常
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException 参数格式异常：", ex);
        String message = MessageUtil.getMessage(ResultCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getKey());
        return Result.fail(message);
    }
    /**
     * 空指针统一异常处理
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> nullPointerException(NullPointerException ex) {
        log.error("NullPointerException 空指针异常：", ex);
        String message = MessageUtil.getMessage(ResultCode.SYSTEM_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * http消息转换异常
     *
     * @param ex 错误
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public Result<?> httpMessageConversionException(HttpMessageConversionException ex) {
        log.error("HttpMessageConversionException 请求参数异常：", ex);
        String message = MessageUtil.getMessage(ResultCode.HTTP_MESSAGE_CONVERSION_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * 用来兜底，定义未定义的异常消息，返回给前端
     *
     * @param e e
     * @return {@link Result }<{@link Object }>
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> exceptionHandler(Exception e) {
        log.error("Handler Exception: ", e);
        String message = MessageUtil.getMessage(ResultCode.SYSTEM_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * 系统异常
     *
     * @param ex 错误
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(BreezeBizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> bizException(BreezeBizException ex) {
        log.error("bizException 自定义的系统异常：", ex);
        return Result.fail(ex.getMessage());
    }

}
