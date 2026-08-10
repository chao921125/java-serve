package com.cc.monitor.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * 自定义业务指标
 * <p>
 * 使用 Micrometer 注册到 Prometheus
 */
@Component
public class BusinessMetrics {

    private final MeterRegistry registry;

    private Counter loginSuccessCounter;
    private Counter loginFailureCounter;
    private Counter apiRequestCounter;

    public BusinessMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        loginSuccessCounter = Counter.builder("app.login.success")
                .description("登录成功次数")
                .register(registry);

        loginFailureCounter = Counter.builder("app.login.failure")
                .description("登录失败次数")
                .register(registry);

        apiRequestCounter = Counter.builder("app.api.requests")
                .description("API 请求总次数")
                .register(registry);
    }

    public void incrementLoginSuccess() {
        loginSuccessCounter.increment();
    }

    public void incrementLoginFailure() {
        loginFailureCounter.increment();
    }

    public void incrementApiRequest() {
        apiRequestCounter.increment();
    }
}
