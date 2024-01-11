package com.breeze.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Slf4j
@Configuration
public class SwaggerPrintConfiguration implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            //获取IP
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            //获取应用名
            String applicationName = event.getApplicationContext().getApplicationName();
            log.info("项目启动启动成功！接口文档地址: \n http://" + hostAddress + ":" + event.getWebServer().getPort() + applicationName + "/swagger-ui.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            log.error("[获取地址失败]");
        }
    }
}
