package com.breeze.boot.database.annotation;

import java.lang.annotation.*;

/**
 * 数据权限
 *
 * @author breeze
 * @date 2022-10-29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataPermission {

    String scope() default "dept_id";

    String own() default "create_by";

}
