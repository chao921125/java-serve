package com.cc.rocketmq.annotation;

import java.lang.annotation.*;

/**
 * RocketMQ 消息注解
 * 标记需要发送消息的方法，框架自动将方法返回值作为消息发送
 *
 * @author cc
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketMQMessage {

    /** 消息主题 */
    String topic();

    /** 消息标签（可选，用于二级分类） */
    String tag() default "*";

    /** 消息 Key（支持 SpEL 表达式） */
    String key() default "";

    /** 延迟级别：1s/5s/10s/30s/1m/2m/3m/4m/5m/6m/7m/8m/9m/10m/20m/30m/1h/2h */
    int delayLevel() default 0;

    /** 是否异步发送 */
    boolean async() default false;
}
