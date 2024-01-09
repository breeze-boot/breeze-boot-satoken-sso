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

package com.breeze.boot.core.utils;

import com.breeze.boot.core.enums.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 结果
 *
 * @param <T>
 * @author gaoweixuan
 * @since 2021/10/1
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 返回时的 时间戳
     */
    private long timestamp;

    /**
     * 结果
     */
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.OK.getCode());
        result.setMessage(ResultCode.OK.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 成功
     *
     * @return {@link Result}
     */
    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.OK.getCode());
        result.setMessage(ResultCode.OK.getMsg());
        return result;
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param msg  信息
     * @return {@link Result}
     */
    public static <T> Result<T> ok(T data, String msg) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.OK.getCode());
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 警告
     *
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> warning(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.WARNING.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 警告
     *
     * @param message 消息
     * @param data    数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> warning(T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.WARNING.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param data    数据
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param data    数据
     * @param message 消息
     * @param code    代码
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(String code, T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败
     *
     * @param resultCode 结果代码枚举
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMsg());
        return result;
    }

    /**
     * 失败
     *
     * @param code 结果代码
     * @param msg  信息
     * @return {@link Result}<{@link String}>
     */
    public static Result<String> fail(String code, String msg) {
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

}
