package com.breeze.base.oss.minio.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio属性
 *
 * @author breeze
 * @date 2022-11-14
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "breeze.oss.minio")
public class MinioProperties {

    /**
     * 连接地址
     */
    private String endpoint;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 域名
     */
    private String nginxHost;


}
