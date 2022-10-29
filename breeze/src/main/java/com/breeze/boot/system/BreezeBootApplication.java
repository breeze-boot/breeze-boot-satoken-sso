package com.breeze.boot.system;

import com.breeze.boot.springdoc.annotation.EnableSpringDoc;
import com.breeze.boot.validater.annotation.EnableFastValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author gaoweixuan
 */
@EnableSpringDoc
@EnableCaching
@EnableFastValidator
@SpringBootApplication
public class BreezeBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeBootApplication.class, args);
    }

}
