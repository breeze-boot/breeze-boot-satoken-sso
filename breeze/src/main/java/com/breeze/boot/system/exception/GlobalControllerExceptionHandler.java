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

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理程序
 *
 * @author gaoweixuan
 * @date 2022-10-10
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * 处理请求参数格式错误
     * <p>
     * eg: @RequestBody上使用@Valid 实体上使用@NotNull
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("methodArgumentNotValidExceptionHandler: {}", ex.getMessage());
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.warning(ResultCode.WARNING.getCode(), message);
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @ExceptionHandler(BindException.class)
    public Result<?> bindExceptionHandler(BindException ex) {
        log.error("bindExceptionHandler: {}", ex.getMessage());
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.warning(ResultCode.WARNING.getCode(), message);
    }

    /**
     * 处理请求参数格式错误
     * <p>
     * eg: @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.error("constraintViolationExceptionHandler: {}", ex.getMessage());
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return Result.warning(ResultCode.WARNING.getCode(), message);
    }

    /**
     * http消息不可读异常处理程序
     * 参数格式异常
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.error("httpMessageNotReadableExceptionHandler: {}", ex.getMessage());
        return Result.warning(ResultCode.WARNING.getCode(), "参数格式异常");
    }

}
