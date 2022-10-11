package com.breeze.boot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 监控 应用程序
 *
 * @author breeze
 * @date 2022-10-10
 */
@EnableAdminServer
@SpringBootApplication
public class BreezeBootAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeBootAdminServerApplication.class, args);
    }

}
