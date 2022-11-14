package com.breeze.base.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件配置
 *
 * @author gaoweixuan
 * @date 2022-11-12
 */
@Data
@Configuration
@ConfigurationProperties("spring.mail")
public class MailProperties {

    private String fromAddress;

}
