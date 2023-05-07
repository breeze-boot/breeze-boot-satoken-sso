package com.breeze.boot.core.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

import java.lang.annotation.*;

/**
 * 基地过滤器
 *
 * @author gaoweixuan
 * @date 2023/04/16
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnWebApplication
public @interface BaseFilter {

}
