package com.cc.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 多级缓存注解（Caffeine 本地 + Redis 远程）
 * <p>
 * 使用方式：在方法上添加 @MultiCache，方法返回值将自动缓存。
 * 首次调用从数据库查询并写入两级缓存，后续调用优先从 Caffeine 本地缓存获取，
 * 本地缓存未命中则查 Redis，Redis 未命中则回源数据库。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiCache {

    /** 缓存名称（必填） */
    String name();

    /** 缓存 key（支持 SpEL 表达式），默认取所有参数 hash */
    String key() default "";

    /** 本地缓存过期时间（秒），默认 60 */
    int localExpire() default 60;

    /** 远程缓存过期时间（秒），默认 300 */
    int remoteExpire() default 300;

    /** 过期时间单位，默认秒 */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /** 是否缓存 null 值（防穿透），默认 false */
    boolean cacheNull() default false;

    /** 本地缓存最大条目数，默认 1000 */
    int localMaxSize() default 1000;
}
