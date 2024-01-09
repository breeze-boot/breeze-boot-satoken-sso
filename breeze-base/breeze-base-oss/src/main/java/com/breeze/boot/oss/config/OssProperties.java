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

package com.breeze.boot.oss.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.breeze.boot.oss.config.OssProperties.PREFIX;

/**
 * oss属性
 *
 * @author gaoweixuan
 * @since 2022-11-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = PREFIX)
public class OssProperties {

    public static final String PREFIX = "breeze.oss";

    /**
     * Oss
     */
    private Map<String, ConfigEntity> s3;

    @Getter
    @Setter
    public static class ConfigEntity {

        /**
         * 访问域名
         */
        private String domainUrl;

        /**
         * 对象存储服务的URL
         */
        private String endpoint;

        /**
         * 区域
         */
        private String region;

        /**
         * key
         */
        private String accessKey;

        /**
         * 密钥
         */
        private String accessKeySecret;

        /**
         * 最大线程数
         */
        private Integer maxConnections = 60;

        /**
         * 路径方式访问
         */
        private Boolean pathStyleAccess = true;

    }
}
