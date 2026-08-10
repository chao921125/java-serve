package com.cc.framework.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * 通过 AOP 在 SQL 执行前动态拼接部门过滤条件
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /** 部门表别名 */
    String deptAlias() default "d";

    /** 用户表别名 */
    String userAlias() default "u";

    /** 权限标识（如 sys:user:list） */
    String permission() default "";
}
