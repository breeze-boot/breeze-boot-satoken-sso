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

package com.breeze.boot;

import com.breeze.springdoc.annotation.EnableSpringDoc;
import com.breeze.validater.annotation.EnableFastValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 微风启动应用程序
 *
 * @author gaoweixuan
 * @date 2022-11-16
 */
@EnableSpringDoc
@EnableCaching
@EnableFastValidator
@SpringBootApplication
public class BreezeBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeBootApplication.class, args);
    }

}
