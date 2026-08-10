package com.cc.sentinel.annotation;

import java.lang.annotation.*;

/**
 * Sentinel 限流注解
 * <p>
 * 使用方式：在 Controller 方法上添加，自动进行 QPS 限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SentinelRateLimit {

    /** 资源名称（默认方法全限定名） */
    String value() default "";

    /** QPS 阈值 */
    int qps() default 100;

    /** 限流类型：FLOW（QPS 限流）、CONCURRENT（并发线程数） */
    String type() default "FLOW";

    /** 降级处理方法名（同类中的方法名） */
    String fallback() default "";

    /** 限流提示 */
    String message() default "系统繁忙，请稍后再试";
}
