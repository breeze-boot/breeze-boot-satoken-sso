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

package com.breeze.boot.jwtlogin.ex;

import org.springframework.http.HttpStatus;

/**
 * 访问异常
 *
 * @author breeze
 * @date 2022-08-31
 */
public class AcessException extends RuntimeException {

    /**
     * 代码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String msg;

    /**
     * 访问异常
     *
     * @param msg        信息
     * @param httpStatus http状态
     */
    public AcessException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.code = httpStatus.value();
        this.msg = msg;
    }
}
