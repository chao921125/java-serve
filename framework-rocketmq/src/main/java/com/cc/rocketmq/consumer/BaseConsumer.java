package com.cc.rocketmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RocketMQ 消费者基类
 * 内置消息幂等性保障（基于 messageId 去重）
 * 子类实现 handleMessage 方法处理业务逻辑
 *
 * @author cc
 */
@Slf4j
public abstract class BaseConsumer<T> implements RocketMQListener<String> {

    protected final ObjectMapper objectMapper;
    protected final Class<T> messageType;

    /** 本地已消费消息 ID 缓存（防重复消费） */
    private final Map<String, Long> consumedMessageIds = new ConcurrentHashMap<>();

    /** 消息去重过期时间（毫秒），默认 1 小时 */
    private static final long DEDUP_EXPIRE_MS = 3600_000L;

    protected BaseConsumer(ObjectMapper objectMapper, Class<T> messageType) {
        this.objectMapper = objectMapper;
        this.messageType = messageType;
    }

    @Override
    public void onMessage(String message) {
        try {
            // 提取 messageId
            String msgId = extractMessageId(message);
            if (msgId != null && isConsumed(msgId)) {
                log.warn("消息重复消费，已跳过: msgId={}", msgId);
                return;
            }

            // 反序列化
            T payload = parseMessage(message);
            if (payload == null) {
                log.error("消息反序列化失败: {}", message);
                return;
            }

            // 业务处理
            boolean success = handleMessage(payload, msgId);

            // 标记已消费
            if (success && msgId != null) {
                markConsumed(msgId);
            }

            // 清理过期记录
            cleanExpiredRecords();

        } catch (Exception e) {
            log.error("消息消费异常", e);
            // 抛出异常触发重试
            throw new RuntimeException("消息消费失败", e);
        }
    }

    /**
     * 处理消息（子类实现）
     * @param payload 消息体
     * @param msgId 消息 ID
     * @return true-消费成功 false-消费失败（将重试）
     */
    protected abstract boolean handleMessage(T payload, String msgId);

    /**
     * 反序列化消息
     */
    protected T parseMessage(String message) {
        try {
            return objectMapper.readValue(message, messageType);
        } catch (Exception e) {
            log.error("消息反序列化失败: type={}", messageType.getSimpleName(), e);
            return null;
        }
    }

    /**
     * 从 JSON 消息中提取 messageId
     */
    private String extractMessageId(String message) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = objectMapper.readValue(message, Map.class);
            Object msgId = map.get("messageId");
            return msgId != null ? msgId.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检查消息是否已消费
     */
    private boolean isConsumed(String msgId) {
        Long timestamp = consumedMessageIds.get(msgId);
        if (timestamp == null) return false;
        if (System.currentTimeMillis() - timestamp > DEDUP_EXPIRE_MS) {
            consumedMessageIds.remove(msgId);
            return false;
        }
        return true;
    }

    /**
     * 标记消息已消费
     */
    private void markConsumed(String msgId) {
        consumedMessageIds.put(msgId, System.currentTimeMillis());
    }

    /**
     * 清理过期的消费记录
     */
    private void cleanExpiredRecords() {
        long now = System.currentTimeMillis();
        consumedMessageIds.entrySet().removeIf(entry -> now - entry.getValue() > DEDUP_EXPIRE_MS);
    }
}
