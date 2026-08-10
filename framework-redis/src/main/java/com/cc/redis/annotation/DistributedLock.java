package com.cc.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解（基于 Redisson）
 * <p>
 * 使用方式：在方法上添加 @DistributedLock，方法执行时自动获取/释放分布式锁。
 * 支持 SpEL 表达式指定锁的 key。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /** 锁的 key（支持 SpEL 表达式），默认使用方法全限定名 */
    String key() default "";

    /** 锁前缀 */
    String prefix() default "distributed_lock:";

    /** 等待获取锁的最大时间（秒），0 表示不等待立即失败 */
    long waitTime() default 3;

    /** 持有锁的最大时间（秒），-1 使用看门狗自动续期 */
    long leaseTime() default -1;

    /** 时间单位 */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /** 获取锁失败时的提示信息 */
    String failMessage() default "系统繁忙，请稍后重试";
}
