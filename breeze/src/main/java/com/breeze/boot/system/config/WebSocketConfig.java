package com.breeze.boot.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author breeze
 * @date 2022-10-12
 */
@Configuration
public class WebSocketConfig {
    /**
     * 服务器端点出口国
     *
     * @return {@link ServerEndpointExporter}
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
