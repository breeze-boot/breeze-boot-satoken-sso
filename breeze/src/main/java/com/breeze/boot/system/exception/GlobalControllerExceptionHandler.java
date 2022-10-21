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
import org.omg.CORBA.SystemException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理程序
 *
 * @author breeze
 * @date 2022-10-10
 */
@Slf4j
@ControllerAdvice(basePackageClasses = {RestController.class, Controller.class})
public class GlobalControllerExceptionHandler {

    /**
     * 空指针统一异常处理
     *
     * @param ex
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public Result<? extends Object> globalException(HttpServletResponse response, NullPointerException ex) {
        log.error("NullPointerException: {}", ex.getMessage());
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 参数类型转换错误
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
     * 自定义异常统一异常处理
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(SystemException.class)
    public Result<? extends Object> systemExceptionHandler(SystemServiceException ex) {
        log.error("systemExceptionHandler ：{}", ex);
        return Result.fail(ex.getCode(), ex.getMessage(), ex.getDescription());
    }

    /**
     * 运行时异常统一异常处理
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result<? extends Object> runtimeExceptionHandler(RuntimeException ex) {
        log.error("runtimeException：{}", ex);
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * 全局异常统一异常处理
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<? extends Object> exceptionHandler(RuntimeException ex) {
        log.error("exception：{}", ex);
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}
