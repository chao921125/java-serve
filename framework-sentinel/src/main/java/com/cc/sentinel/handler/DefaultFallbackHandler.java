package com.cc.sentinel.handler;

import com.cc.framework.base.R;
import org.springframework.stereotype.Component;

/**
 * 默认降级处理
 */
@Component
public class DefaultFallbackHandler {

    /**
     * 限流降级响应
     */
    public R<?> handleRateLimit(String message) {
        return R.fail(429, message != null ? message : "请求过于频繁，请稍后再试");
    }

    /**
     * 熔断降级响应
     */
    public R<?> handleCircuitBreaker(String message) {
        return R.fail(503, message != null ? message : "服务暂时不可用，请稍后再试");
    }
}
