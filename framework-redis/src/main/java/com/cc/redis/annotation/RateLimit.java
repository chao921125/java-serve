package com.cc.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解（基于 Redis 滑动窗口）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /** 限流 key（支持 SpEL），默认方法全限定名 */
    String key() default "";

    /** 时间窗口内允许的请求次数 */
    int count() default 100;

    /** 时间窗口大小 */
    int time() default 1;

    /** 时间单位 */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /** 限流类型：SLIDING_WINDOW（滑动窗口）、TOKEN_BUCKET（令牌桶） */
    String type() default "SLIDING_WINDOW";

    /** 限流提示 */
    String message() default "请求过于频繁，请稍后再试";
}
