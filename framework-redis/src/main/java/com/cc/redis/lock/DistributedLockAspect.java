package com.cc.redis.lock;

import com.cc.redis.annotation.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分布式锁切面（基于 Redisson）
 */
@Aspect
@Component
@ConditionalOnClass(RedissonClient.class)
public class DistributedLockAspect {

    private static final Logger log = LoggerFactory.getLogger(DistributedLockAspect.class);

    private final RedissonClient redissonClient;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    public DistributedLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint pjp, DistributedLock distributedLock) throws Throwable {
        String lockKey = buildLockKey(pjp, distributedLock);
        RLock lock = redissonClient.getLock(distributedLock.prefix() + lockKey);

        boolean acquired = false;
        try {
            if (distributedLock.leaseTime() > 0) {
                acquired = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(),
                        distributedLock.timeUnit());
            } else {
                // 使用看门狗自动续期
                acquired = lock.tryLock(distributedLock.waitTime(), distributedLock.timeUnit());
            }

            if (!acquired) {
                log.warn("获取分布式锁失败: key={}, method={}", lockKey,
                        pjp.getSignature().toShortString());
                throw new RuntimeException(distributedLock.failMessage());
            }

            log.debug("获取分布式锁成功: key={}", lockKey);
            return pjp.proceed();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("获取分布式锁被中断", e);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.debug("释放分布式锁: key={}", lockKey);
            }
        }
    }

    /**
     * 构建锁的 key
     */
    private String buildLockKey(ProceedingJoinPoint pjp, DistributedLock distributedLock) {
        if (!distributedLock.key().isEmpty()) {
            return parseSpel(pjp, distributedLock.key());
        }
        // 默认使用方法的全限定名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }

    private String parseSpel(ProceedingJoinPoint pjp, String spel) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String[] paramNames = discoverer.getParameterNames(method);
        Object[] args = pjp.getArgs();

        EvaluationContext context = new StandardEvaluationContext();
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }
        Expression expression = parser.parseExpression(spel);
        return String.valueOf(expression.getValue(context, String.class));
    }
}
