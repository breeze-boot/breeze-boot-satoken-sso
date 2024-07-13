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
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.breeze.boot.core.enums.ResultCode.HTTP_MESSAGE_CONVERSION_EXCEPTION;


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
     * 空指针统一异常处理
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?> nullPointerException(NullPointerException ex) {
        log.error("NullPointerException 空指针异常：", ex);
        return Result.fail(ResultCode.EXCEPTION);
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
        return Result.fail(HTTP_MESSAGE_CONVERSION_EXCEPTION);
    }

    /**
     * 系统异常
     *
     * @param ex 错误
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(SystemServiceException.class)
    public Result<?> systemExceptionHandler(SystemServiceException ex) {
        log.error("SystemServiceException 自定义的系统异常：", ex);
        return Result.fail(ex.getCode(), ex.getMsg());
    }

}
