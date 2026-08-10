package com.cc.sentinel.annotation;

import java.lang.annotation.*;

/**
 * Sentinel 熔断降级注解
 * <p>
 * 当方法调用异常比例/慢调用比例超过阈值时自动熔断
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SentinelCircuitBreaker {

    /** 资源名称 */
    String value() default "";

    /** 熔断策略：SLOW_RATIO（慢调用比例）、ERROR_RATIO（异常比例）、ERROR_COUNT（异常数） */
    String strategy() default "ERROR_RATIO";

    /** 比例阈值（0.0 ~ 1.0） */
    double threshold() default 0.5;

    /** 熔断时长（秒） */
    int circuitBreakerSeconds() default 60;

    /** 统计时长（秒） */
    int statIntervalSeconds() default 1;

    /** 最小请求数 */
    int minRequestCount() default 5;

    /** 降级处理方法名 */
    String fallback() default "";

    /** 降级提示 */
    String message() default "服务暂时不可用，请稍后再试";
}
