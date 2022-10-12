package com.breeze.boot.system.exception;

import cn.hutool.core.collection.CollUtil;
import com.breeze.boot.core.Result;
import com.breeze.boot.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理程序
 *
 * @author breeze
 * @date 2022-10-10
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数错误统一异常处理
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class, ValidationException.class})
    public Result<? extends Object> handleParameterVerificationException(Exception ex) {
        log.warn("exception: {}", ex.getMessage());
        BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        List<String> msgList = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(msgList)) {
            return Result.warning(ResultCode.PARAMETER_IS_INCORRECT, msgList.get(0));
        }
        return Result.warning(ResultCode.WARNING, ResultCode.WARNING.getMsg());
    }

    /**
     * 空指针统一异常处理
     *
     * @param ex
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public Result<? extends Object> globalException(HttpServletResponse response, NullPointerException ex) {
        log.error("GlobalExceptionHandler...{}", ex.getMessage());
        log.error("错误代码：" + response.getStatus());
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
        log.error(ex.getCause().getLocalizedMessage());
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
        log.error("systemException：{}", ex);
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
