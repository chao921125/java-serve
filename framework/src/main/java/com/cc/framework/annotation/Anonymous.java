package com.cc.framework.annotation;

import java.lang.annotation.*;

/**
 * 匿名访问注解
 * 标注后不需要认证即可访问
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
