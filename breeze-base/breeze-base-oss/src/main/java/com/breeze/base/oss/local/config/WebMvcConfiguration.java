package com.breeze.base.oss.local.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc配置
 *
 * @author breeze
 * @date 2022-11-14
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(LocalProperties.class)
@ConditionalOnProperty(prefix = "breeze.oss.local", value = "enable")
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LocalProperties localProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
            handler为前台访问的URL目录，locations为files相对应的本地路径
            eg: 也就是说如果有一个 img/1.png 请求，那程序会到后面的目录里面找1.png文件
            ps：在Security里面配置过滤下
         */
        registry.addResourceHandler("/img/**").addResourceLocations(this.localProperties.getPath());
    }
}
