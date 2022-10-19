package com.breeze.boot.system.exception;

import com.breeze.boot.core.Result;
import com.breeze.boot.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
     * 处理请求参数格式错误
     * <p>
     * eg: @RequestBody上使用@Valid 实体上使用@NotNull
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<? extends Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("methodArgumentNotValidExceptionHandler: {}", ex.getMessage());
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
    @ResponseBody
    public Result<? extends Object> bindExceptionHandler(BindException ex) {
        log.warn("bindExceptionHandler: {}", ex.getMessage());
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
    @ResponseBody
    public Result<? extends Object> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("constraintViolationExceptionHandler: {}", ex.getMessage());
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
    @ResponseBody
    public Result<? extends Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.warn("httpMessageNotReadableExceptionHandler: {}", ex.getMessage());
        return Result.warning(ResultCode.WARNING.getCode(), "参数格式异常");
    }

}
