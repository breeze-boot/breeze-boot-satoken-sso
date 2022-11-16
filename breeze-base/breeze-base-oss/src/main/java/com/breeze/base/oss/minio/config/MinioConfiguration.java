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

package com.breeze.base.oss.minio.config;

import com.breeze.base.oss.minio.service.MinioService;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * minio配置
 *
 * @author gaoweixuan
 * @date 2022-09-27
 */
@Configuration
@ConditionalOnProperty(prefix = "breeze.oss.minio", value = "enable")
@EnableConfigurationProperties(BreezeMinioProperties.class)
@Import(MinioService.class)
public class MinioConfiguration {

    @Bean
    public MinioClient minioClient(BreezeMinioProperties breezeMinioProperties) {
        return MinioClient.builder()
                .endpoint(breezeMinioProperties.getEndpoint())
                .credentials(breezeMinioProperties.getAccessKey(), breezeMinioProperties.getSecretKey())
                .build();
    }
}
