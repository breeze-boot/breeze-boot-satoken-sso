package com.breeze.boot.system.exception;

import org.springframework.http.HttpStatus;

/**
 * 管理服务异常
 *
 * @author breeze
 * @date 2022-08-31
 */
public class AdminServiceException extends RuntimeException {

    /**
     * 管理服务异常
     *
     * @param message 消息
     * @param status  状态
     */
    public AdminServiceException(String message, HttpStatus status) {
        super(message);
    }

}
