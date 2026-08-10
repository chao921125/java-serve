package com.cc.framework.annotation.datasource;

import java.lang.annotation.*;

/**
 * 标记方法使用从库（读操作）
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Slave {
}
