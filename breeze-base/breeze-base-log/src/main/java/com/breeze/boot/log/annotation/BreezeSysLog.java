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

package com.breeze.boot.log.annotation;

import com.breeze.boot.log.config.LogType;

import java.lang.annotation.*;

/**
 * 日志
 *
 * @author gaoweixuan
 * @date 2021/10/19
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BreezeSysLog {

    /**
     * 描述
     *
     * @return {@link String}
     */
    String description() default "";

    /**
     * 类型
     *
     * @return {@link LogType}
     */
    LogType type();

}
