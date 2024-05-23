package com.breeze.boot.ureport;

import com.bstek.ureport.UReportPropertyPlaceholderConfigurer;
import com.bstek.ureport.console.UReportServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.Servlet;

/**
 * @author gaoweixuan
 * @since 2024/05/20
 */
@Slf4j
@ImportResource("classpath:ureport-console-context.xml")
public class UReportConfig {

    /**
     * ureport2 servlet
     *
     * @return {@link ServletRegistrationBean }<{@link Servlet }>
     */
    @Bean
    public ServletRegistrationBean<Servlet> ureport2Servlet() {
        return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
    }

    /**
     * ureport属性占位符配置
     *
     * @return {@link UReportPropertyPlaceholderConfigurer }
     */
    @Bean
    public UReportPropertyPlaceholderConfigurer UReportPropertyPlaceholderConfigurer() {
        UReportPropertyPlaceholderConfigurer propertyConfigurer = new UReportPropertyPlaceholderConfigurer();
        propertyConfigurer.setIgnoreUnresolvablePlaceholders(true);
        ClassPathResource pathResource = new ClassPathResource("config.properties");
        propertyConfigurer.setLocation(pathResource);
        return propertyConfigurer;
    }

}
