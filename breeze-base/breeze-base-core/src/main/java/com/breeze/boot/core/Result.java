/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.core;

import com.breeze.boot.core.enums.ResultCode;

import java.io.Serializable;

/**
 * 结果
 *
 * @param <T>
 * @author breeze
 * @date 2021/10/1
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 结果
     */
    private Result() {

    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result();
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
    public static Result ok() {
        Result result = new Result();
        result.setCode(ResultCode.OK.getCode());
        result.setMessage(ResultCode.OK.getMsg());
        return result;
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param msg  味精
     * @return {@link Result}
     */
    public static <T> Result ok(T data, String msg) {
        Result result = new Result();
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
        Result<T> result = new Result();
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
        Result<T> result = new Result();
        result.setCode(ResultCode.FAIL.getCode());
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
        Result<T> result = new Result();
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
        Result<T> result = new Result();
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
    public static <T> Result<T> fail(Integer code, T data, String message) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败
     *
     * @param resultCode 结果代码
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> fail(ResultCode resultCode) {
        Result<T> result = new Result();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMsg());
        return result;
    }

    /**
     * 失败
     *
     * @param resultCode 结果代码
     * @param msg        味精
     * @return {@link Result}<{@link String}>
     */
    public static Result<String> fail(ResultCode resultCode, String msg) {
        Result<String> result = new Result();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMsg());
        result.setData(msg);
        return result;
    }

    /**
     * 获取代码
     *
     * @return int
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 得到消息
     *
     * @return {@link String}
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置消息
     *
     * @param message 消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取数据
     *
     * @return {@link T}
     */
    public T getData() {
        return data;
    }

    /**
     * 集数据
     *
     * @param data 数据
     */
    public void setData(T data) {
        this.data = data;
    }
}
