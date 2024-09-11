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

import cn.dev33.satoken.sso.SaSsoManager;
import com.breeze.boot.validater.EnableFastValidator;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 微风启动应用程序
 *
 * @author gaoweixuan
 * @since 2022-11-16
 */
@EnableRabbit
@EnableFastValidator
@SpringBootApplication
public class BreezeBootSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeBootSsoServerApplication.class, args);

        System.out.println();
        System.out.println("---------------------- Sa-Token SSO 统一认证中心启动成功 ----------------------");
        System.out.println("配置信息：" + SaSsoManager.getServerConfig());
        System.out.println();
    }
}
