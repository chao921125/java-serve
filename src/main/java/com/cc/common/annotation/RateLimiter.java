package com.cc.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cc.common.constant.CacheConstants;
import com.cc.common.enums.LimitType;

/**
 * 限流注解
 *
 * @author cc
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    public String key() default CacheConstants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    public int time() default 60;

    /**
     * 限流次数
     */
    public int count() default 100;

    /**
     * 限流类型
     */
    public LimitType limitType() default LimitType.DEFAULT;
}
