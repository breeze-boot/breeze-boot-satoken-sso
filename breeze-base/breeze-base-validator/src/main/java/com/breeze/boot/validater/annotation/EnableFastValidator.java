package com.breeze.boot.validater.annotation;


import com.breeze.boot.validater.config.ValidatorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用验证器
 *
 * @author breeze
 * @date 2022-10-19
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(value = ValidatorConfiguration.class)
public @interface EnableFastValidator {

}
