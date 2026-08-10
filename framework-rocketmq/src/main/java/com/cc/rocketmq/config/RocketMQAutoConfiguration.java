package com.cc.rocketmq.config;

import com.cc.rocketmq.producer.RocketMQProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * RocketMQ 自动配置
 *
 * @author cc
 */
@AutoConfiguration
@ConditionalOnClass(RocketMQTemplate.class)
public class RocketMQAutoConfiguration {

    /**
     * 消息生产者封装 Bean
     */
    @Bean
    @ConditionalOnBean(RocketMQTemplate.class)
    public RocketMQProducer rocketMQProducer(RocketMQTemplate rocketMQTemplate, ObjectMapper objectMapper) {
        return new RocketMQProducer(rocketMQTemplate, objectMapper);
    }
}
