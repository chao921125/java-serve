package com.cc.sentinel.aspect;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.cc.sentinel.annotation.SentinelCircuitBreaker;
import com.cc.sentinel.annotation.SentinelRateLimit;
import com.cc.sentinel.handler.DefaultFallbackHandler;
import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Sentinel 限流/熔断统一切面
 */
@Aspect
@Component
@ConditionalOnClass(SphU.class)
public class SentinelAspect {

    private static final Logger log = LoggerFactory.getLogger(SentinelAspect.class);

    private final DefaultFallbackHandler fallbackHandler;

    public SentinelAspect(DefaultFallbackHandler fallbackHandler) {
        this.fallbackHandler = fallbackHandler;
    }

    @PostConstruct
    public void init() {
        log.info("Sentinel 限流熔断切面初始化完成");
    }

    @Around("@annotation(rateLimit)")
    public Object aroundRateLimit(ProceedingJoinPoint pjp, SentinelRateLimit rateLimit) throws Throwable {
        String resourceName = getResourceName(pjp, rateLimit.value());
        initFlowRule(resourceName, rateLimit.qps());

        Entry entry = null;
        try {
            entry = SphU.entry(resourceName);
            return pjp.proceed();
        } catch (BlockException e) {
            log.warn("Sentinel 限流触发: resource={}, qps={}", resourceName, rateLimit.qps());

            if (!rateLimit.fallback().isEmpty()) {
                return invokeFallback(pjp, rateLimit.fallback());
            }
            return fallbackHandler.handleRateLimit(rateLimit.message());
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    @Around("@annotation(circuitBreaker)")
    public Object aroundCircuitBreaker(ProceedingJoinPoint pjp, SentinelCircuitBreaker circuitBreaker) throws Throwable {
        String resourceName = getResourceName(pjp, circuitBreaker.value());
        initDegradeRule(resourceName, circuitBreaker);

        Entry entry = null;
        try {
            entry = SphU.entry(resourceName);
            return pjp.proceed();
        } catch (BlockException e) {
            log.warn("Sentinel 熔断触发: resource={}", resourceName);

            if (!circuitBreaker.fallback().isEmpty()) {
                return invokeFallback(pjp, circuitBreaker.fallback());
            }
            return fallbackHandler.handleCircuitBreaker(circuitBreaker.message());
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    /**
     * 初始化流控规则
     */
    private void initFlowRule(String resourceName, int qps) {
        List<FlowRule> rules = FlowRuleManager.getRules();
        for (FlowRule rule : rules) {
            if (rule.getResource().equals(resourceName)) {
                if (rule.getCount() != qps) {
                    rule.setCount(qps);
                    FlowRuleManager.loadRules(rules);
                }
                return;
            }
        }
        FlowRule rule = new FlowRule(resourceName);
        rule.setCount(qps);
        rule.setGrade(com.alibaba.csp.sentinel.slots.block.RuleConstant.FLOW_GRADE_QPS);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 初始化降级规则
     */
    private void initDegradeRule(String resourceName, SentinelCircuitBreaker cb) {
        List<DegradeRule> rules = DegradeRuleManager.getRules();
        for (DegradeRule rule : rules) {
            if (rule.getResource().equals(resourceName)) {
                return; // 已存在
            }
        }
        DegradeRule rule = new DegradeRule(resourceName);
        rule.setTimeWindow(cb.circuitBreakerSeconds());
        rule.setMinRequestAmount(cb.minRequestCount());
        rule.setStatIntervalMs(cb.statIntervalSeconds() * 1000);
        rule.setSlowRatioThreshold(cb.threshold());

        switch (cb.strategy()) {
            case "SLOW_RATIO":
                rule.setGrade(com.alibaba.csp.sentinel.slots.block.RuleConstant.DEGRADE_GRADE_RT);
                break;
            case "ERROR_COUNT":
                rule.setGrade(com.alibaba.csp.sentinel.slots.block.RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
                break;
            default:
                rule.setGrade(com.alibaba.csp.sentinel.slots.block.RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        }
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    private String getResourceName(ProceedingJoinPoint pjp, String value) {
        if (value != null && !value.isEmpty()) {
            return value;
        }
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }

    private Object invokeFallback(ProceedingJoinPoint pjp, String methodName) {
        try {
            Method method = pjp.getTarget().getClass()
                    .getMethod(methodName, ((MethodSignature) pjp.getSignature()).getParameterTypes());
            return method.invoke(pjp.getTarget(), pjp.getArgs());
        } catch (Exception e) {
            log.error("调用降级方法失败: {}", methodName, e);
            throw new RuntimeException("降级处理异常", e);
        }
    }
}
