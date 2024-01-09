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

package com.breeze.boot.mybatis.exception;

import com.breeze.boot.core.enums.ResultCode;

/**
 * 租户未获取到值异常
 *
 * @author gaoweixuan
 * @since 2023/04/16
 */
public class TenantNotValueException extends RuntimeException {

    private String code;
    private final String msg;

    public TenantNotValueException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public TenantNotValueException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
