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

package com.breeze.boot.sap.config;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

/**
 * @author gaoweixuan
 * @since 2024/01/09
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SapLinkConfiguration implements InitializingBean {

    private final SapConnectionFileProperties sapConnectionFileProperties;

    @Override
    public void afterPropertiesSet() {
        log.info(">>>>>>>>>>>>>>>>>>>>>初始化设定  正在加载链接SAP的配置文件 [ {}{}]", sapConnectionFileProperties.getSapFilePath(), sapConnectionFileProperties.getSapClientId());
        log.info(">>>>>>>>>>>>>>>>>>>>> {} >>>>>>>>>>>>>>>>>>>>> {}", this.getClass(), sapConnectionFileProperties.getSapClientId());
        // 自定义配置文件
        String profileFile = sapConnectionFileProperties.getSapFilePath() + sapConnectionFileProperties.getSapClientId() + "." + sapConnectionFileProperties.getSapFileType();
        // 从classpath路径下面查找文件
        Resource resource = new ClassPathResource(profileFile);
        Properties jcoProps = loadProfiles(resource);
        log.info("当前环境使用的SAP连接参数\n {}", JSONUtil.toJsonStr(jcoProps));
        File jcoFile = new File("ABAP_AS_" + sapConnectionFileProperties.getSapClientId().toUpperCase(Locale.ROOT) + ".jcoDestination");
        log.info(jcoFile.getAbsolutePath());
        try {
            FileOutputStream fos = new FileOutputStream(jcoFile, false);
            jcoProps.store(fos, "Write jco file.");
            fos.close();
            log.info("Write jco file successfully. ({})", jcoFile.getName());
        } catch (Exception e) {
            log.error("Write jco file failed. ({})", jcoFile.getName(), e);
        }
    }

    /**
     * 加载单个配置文件
     *
     * @param resource 资源
     * @return {@link Properties }
     */
    private Properties loadProfiles(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("资源" + resource + "不存在");
        }
        if (Objects.nonNull(resource.getFilename()) && resource.getFilename().contains(".yml")) {
            return loadYaml(resource);
        }
        return loadProperty(resource);
    }

    /**
     * 加载properties格式的配置文件
     *
     * @param resource 资源
     * @return {@link Properties }
     */
    private Properties loadProperty(Resource resource) {
        try {
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (Exception ex) {
            log.error("[加载配置文件失败：{}]", resource, ex);
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }

    /**
     * 加载yml格式的配置文件
     *
     * @param resource 资源
     * @return {@link Properties }
     */
    private Properties loadYaml(Resource resource) {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource);
            return factory.getObject();
        } catch (Exception ex) {
            log.error("[加载配置文件失败：{}]", resource, ex);
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }
}
