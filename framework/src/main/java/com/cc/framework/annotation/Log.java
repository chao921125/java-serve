package com.cc.framework.annotation;

import com.cc.core.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 标注在 Controller 方法上，自动记录操作日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /** 模块标题 */
    String title() default "";

    /** 功能类型 */
    BusinessType businessType() default BusinessType.OTHER;

    /** 是否保存请求参数 */
    boolean isSaveRequestData() default true;

    /** 是否保存响应数据 */
    boolean isSaveResponseData() default false;
}
