package com.breeze.base.oss.local.config;

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
@ConfigurationProperties(prefix = "breeze.oss.local")
public class LocalProperties {

    /**
     * 路径
     */
    private String path;

}
