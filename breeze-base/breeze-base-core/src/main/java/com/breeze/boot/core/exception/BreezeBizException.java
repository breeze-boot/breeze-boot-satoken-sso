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

package com.breeze.boot.core.exception;

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.MessageUtil;
import lombok.Getter;

/**
 * 系统服务异常
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Getter
public class BreezeBizException extends RuntimeException {

    /**
     * 响应码
     */
    private final ResultCode resultCode;

    public BreezeBizException(ResultCode resultCode) {
        super(MessageUtil.getMessage(resultCode.getKey()));
        this.resultCode = resultCode;
    }

}
