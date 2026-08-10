package com.cc.rocketmq.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

/**
 * RocketMQ 消息生产者封装
 * 提供同步/异步/单向/延迟/顺序消息发送
 *
 * @author cc
 */
@Slf4j
public class RocketMQProducer {

    private final RocketMQTemplate rocketMQTemplate;
    private final ObjectMapper objectMapper;

    public RocketMQProducer(RocketMQTemplate rocketMQTemplate, ObjectMapper objectMapper) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 发送同步消息
     */
    public SendResult sendSync(String topic, String tag, Object payload) {
        return sendSync(topic, tag, UUID.randomUUID().toString(), payload);
    }

    public SendResult sendSync(String topic, String tag, String key, Object payload) {
        String destination = buildDestination(topic, tag);
        Message<String> message = buildMessage(key, payload);
        SendResult result = rocketMQTemplate.syncSend(destination, message);
        log.debug("RocketMQ sync send success: topic={}, tag={}, msgId={}", topic, tag, result.getMsgId());
        return result;
    }

    /**
     * 发送异步消息
     */
    public void sendAsync(String topic, String tag, Object payload) {
        sendAsync(topic, tag, UUID.randomUUID().toString(), payload, null);
    }

    public void sendAsync(String topic, String tag, Object payload, SendCallback callback) {
        sendAsync(topic, tag, UUID.randomUUID().toString(), payload, callback);
    }

    public void sendAsync(String topic, String tag, String key, Object payload, SendCallback callback) {
        String destination = buildDestination(topic, tag);
        Message<String> message = buildMessage(key, payload);
        if (callback == null) {
            callback = new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.debug("RocketMQ async send success: topic={}, msgId={}", topic, sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    log.error("RocketMQ async send failed: topic={}, key={}", topic, key, e);
                }
            };
        }
        rocketMQTemplate.asyncSend(destination, message, callback);
    }

    /**
     * 发送单向消息（不关心结果，性能最高）
     */
    public void sendOneWay(String topic, String tag, Object payload) {
        String destination = buildDestination(topic, tag);
        Message<String> message = buildMessage(UUID.randomUUID().toString(), payload);
        rocketMQTemplate.sendOneWay(destination, message);
    }

    /**
     * 发送延迟消息
     */
    public SendResult sendDelay(String topic, String tag, Object payload, int delayLevel) {
        String destination = buildDestination(topic, tag);
        Message<String> message = buildMessage(UUID.randomUUID().toString(), payload);
        return rocketMQTemplate.syncSendDelayTimeSeconds(destination, message,
                delayLevelToSeconds(delayLevel));
    }

    /**
     * 发送顺序消息（按 key 哈希到同一队列）
     */
    public SendResult sendOrderly(String topic, String tag, String key, Object payload) {
        String destination = buildDestination(topic, tag);
        Message<String> message = buildMessage(key, payload);
        return rocketMQTemplate.syncSendOrderly(destination, message, key);
    }

    /**
     * 构建消息
     */
    private Message<String> buildMessage(String key, Object payload) {
        try {
            String json = payload instanceof String ? (String) payload : objectMapper.writeValueAsString(payload);
            return MessageBuilder
                    .withPayload(json)
                    .setHeader("KEYS", key)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("序列化消息失败", e);
        }
    }

    /**
     * 构建目标地址 topic:tag
     */
    private String buildDestination(String topic, String tag) {
        return topic + ":" + tag;
    }

    /**
     * 延迟级别转秒数
     */
    private long delayLevelToSeconds(int level) {
        long[] levels = {0, 1, 5, 10, 30, 60, 120, 180, 240, 300, 360, 420, 480, 540, 600, 1200, 1800, 3600, 7200};
        return level < levels.length ? levels[level] : 0;
    }
}
