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

package com.breeze.boot.oss.operation;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.breeze.boot.oss.config.OssProperties;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * 操作系统模板
 *
 * @author gaoweixuan
 * @since 2023-04-18
 */
@Slf4j
public class MinioOssTemplate extends OssTemplate implements InitializingBean, EnvironmentAware {

    /**
     * 存储地址
     */
    private final String resourceName;
    private OssProperties ossProperties;

    public MinioOssTemplate() {
        this.resourceName = "minio";
    }

    @Override
    public void afterPropertiesSet() {
        OssProperties.ConfigEntity configEntity = ossProperties.getS3().get(resourceName);
        // 全局客户端配置
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // 最大连接数
        clientConfiguration.setMaxConnections(configEntity.getMaxConnections());
        // url以及region
        AwsClientBuilder.EndpointConfiguration endpointConfiguration
                = new AwsClientBuilder.EndpointConfiguration(configEntity.getEndpoint(), configEntity.getRegion());
        // 认证凭证
        AWSCredentials awsCredentials
                = new BasicAWSCredentials(configEntity.getAccessKey(), configEntity.getAccessKeySecret());

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        super.setAmazonS3(AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfiguration)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(configEntity.getPathStyleAccess())
                .build());
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        ConfigurationProperties annotation = OssProperties.class.getAnnotation(ConfigurationProperties.class);
        BindResult<OssProperties> bindResult = Binder.get(environment).bind(annotation.prefix(), OssProperties.class);
        ossProperties = bindResult.get();
    }

}
