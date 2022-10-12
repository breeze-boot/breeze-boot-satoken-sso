package com.breeze.boot.system.exception;

import com.breeze.boot.core.enums.ResultCode;
import lombok.Getter;

/**
 * 系统服务异常
 *
 * @author breeze
 * @date 2022-08-31
 */
@Getter
public class SystemServiceException extends RuntimeException {

    private final int code;

    private final String description;

    public SystemServiceException(ResultCode resultCode, String description) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.description = description;
    }

    public SystemServiceException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

}
