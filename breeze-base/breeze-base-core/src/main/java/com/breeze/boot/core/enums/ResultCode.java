package com.breeze.boot.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gaoweixuan
 * @since 2021/10/1
 */
public enum ResultCode {

    /**
     * 成功
     */
    OK("0000", "请求成功"),

    /**
     * 警告
     */
    WARNING("0001", "请求不合法"),

    /**
     * 失败
     */
    FAIL("0002", "请求失败"),

    /**
     * 错误用户名或密码
     */
    WRONG_USERNAME_OR_PASSWORD("A001", "用户名或者密码错误"),

    /**
     * 用户名没有发现异常
     */
    USERNAME_NOT_FOUND_EXCEPTION("A002", "用户名没有发现异常"),

    /**
     * http身份验证异常
     */
    HTTP_AUTHENTICATION_EXCEPTION("A101", "客户端身份验证失败：Client_secret/client_id"),

    /**
     * GRANT_TYP不支持类型
     */
    UNSUPPORTED_GRANT_TYPE("A102", "不支持的认证模式"),

    /**
     * 令牌无效
     */
    TOKEN_INVALID("A103", "令牌无效"),

    /**
     * 账户已过期
     */
    ACCOUNT_EXPIRED("A104", "账户已过期"),

    /**
     * 账户已经锁定
     */
    ACCOUNT_LOCKED("A105", "账户已经锁定"),

    /**
     * 未登录
     */
    UN_LOGIN("A106", "未登录"),

    /**
     * 未授权资源，请联系管理员授权
     */
    UNAUTHORIZED("A104", "未授权资源，请联系管理员授权"),

    /**
     * 未经授权的服务器
     */
    INSUFFICIENT_AUTHENTICATION("A105", "未经授权的服务器"),

    /**
     * 身份验证异常
     */
    FORBIDDEN("A105", "身份验证异常"),

    /**
     * http消息转换异常
     */
    HTTP_MESSAGE_CONVERSION_EXCEPTION("B001", "请求参数错误"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND("B002", "文件不存在"),


    /**
     * 系统异常
     */
    EXCEPTION("SYSTEM_ERROR_0001", "系统异常"),

    /**
     * 系统异常
     */
    CUS_EXCEPTION("SYSTEM_ERROR_0001", "系统异常");

    @Getter
    private final String code;

    @Getter
    @Setter
    private String msg;

    /**
     * 返回结果代码
     *
     * @param code 代码
     * @param msg  msg
     */
    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode exception(String errMsg) {
        ResultCode.CUS_EXCEPTION.setMsg(errMsg);
        return ResultCode.CUS_EXCEPTION;
    }
}
