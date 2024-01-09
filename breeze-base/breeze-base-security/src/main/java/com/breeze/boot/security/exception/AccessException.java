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

package com.breeze.boot.security.exception;

import com.breeze.boot.core.enums.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访问异常
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessException extends RuntimeException {

    /**
     * 代码
     */
    private final String code;
    /**
     * 信息
     */
    private final String msg;

    /**
     * 访问异常
     *
     * @param resultCode 结果代码
     */
    public AccessException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.msg = resultCode.getMsg();
        this.code = resultCode.getCode();
    }
}
