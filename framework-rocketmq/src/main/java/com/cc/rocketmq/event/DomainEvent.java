package com.cc.rocketmq.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 领域事件基类
 * 所有业务事件需继承此类
 *
 * @author cc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DomainEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 事件 ID（全局唯一） */
    private String eventId = UUID.randomUUID().toString();

    /** 事件发生时间 */
    private LocalDateTime eventTime = LocalDateTime.now();

    /** 事件来源服务 */
    private String source;

    /** 触发事件的用户 ID */
    private Long userId;

    /** 事件类型 */
    public abstract String eventType();
}
