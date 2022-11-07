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

import com.breeze.boot.core.enums.ResultCode;
import lombok.Getter;

/**
 * 系统服务异常
 *
 * @author gaoweixuan
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
