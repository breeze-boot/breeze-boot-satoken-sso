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

package com.breeze.boot.core.enums;

import lombok.Getter;

/**
 * 返回结果代码
 *
 * @author gaoweixuan
 * @date 2021/10/1
 */
@Getter
public enum ResultCode {

    /**
     * 错误用户名或密码
     */
    WRONG_USERNAME_OR_PASSWORD(1002, "用户名或者密码错误"),

    /**
     * 用户名没有发现异常
     */
    USERNAME_NOT_FOUND_EXCEPTION(1005, "用户名或者密码错误"),

    /**
     * 时间异常
     */
    TIME_OUT_EXCEPTION(500, "服务器错误"),

    /**
     * http身份验证异常
     */
    HTTP_AUTHENTICATION_EXCEPTION(10000, "客户端身份验证失败：Client_secret/client_id"),

    /**
     * GRANT_TYP不支持类型
     */
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),

    /**
     * 令牌无效
     */
    TOKEN_INVALID(1004, "验证失效"),

    /**
     * 未授权资源，请联系管理员授权
     */
    UNAUTHORIZED(401, "未授权资源，请联系管理员授权"),

    /**
     * 身份验证异常
     */
    FORBIDDEN(403, "身份验证异常"),

    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器错误"),

    /**
     * http消息转换异常
     */
    HTTP_MESSAGE_CONVERSION_EXCEPTION(500, "请求参数错误"),

    /**
     * 成功
     */
    OK(1, "请求成功"),
    /**
     * 警告
     */
    WARNING(2, "请求不合法"),
    /**
     * 失败
     */
    FAIL(0, "请求失败"),
    /**
     * 系统异常
     */
    EXCEPTION(500, "系统异常");

    /**
     * 响应代码
     */
    private int code;
    /**
     * 响应信息
     */
    private String msg;

    /**
     * 返回结果代码
     *
     * @param code 代码
     * @param msg  msg
     */
    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 系统异常 返回结果代码
     *
     * @param msg  msg
     * @param code 代码
     * @return {@link ResultCode}
     */
    public static ResultCode exception(int code, String msg) {
        EXCEPTION.code = code;
        EXCEPTION.msg = msg;
        return ResultCode.EXCEPTION;
    }

    /**
     * 系统异常 返回结果代码
     *
     * @param msg msg
     * @return {@link ResultCode}
     */
    public static ResultCode exception(String msg) {
        EXCEPTION.msg = msg;
        return ResultCode.EXCEPTION;
    }

    /**
     * 系统异常 返回结果代码
     *
     * @param msg msg
     * @return {@link ResultCode}
     */
    public static ResultCode httpAuthenticationException(String msg) {
        HTTP_AUTHENTICATION_EXCEPTION.msg = msg;
        return ResultCode.HTTP_AUTHENTICATION_EXCEPTION;
    }

}
