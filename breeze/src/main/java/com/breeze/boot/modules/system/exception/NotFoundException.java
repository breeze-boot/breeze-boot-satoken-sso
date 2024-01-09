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

package com.breeze.boot.modules.system.exception;

/**
 * 没有发现异常
 *
 * @author gaoweixuan
 * @since 2023/05/17
 */
public class NotFoundException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误提示
     */
    private final String msg;

    public NotFoundException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }
}
