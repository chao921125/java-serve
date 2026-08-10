package com.cc.redis.lock;

import com.cc.redis.annotation.RateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Collections;

/**
 * Redis 滑动窗口限流切面
 */
@Aspect
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisRateLimitAspect {

    private static final Logger log = LoggerFactory.getLogger(RedisRateLimitAspect.class);

    private final RedisTemplate<String, Object> redisTemplate;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    /**
     * Lua 脚本：滑动窗口限流
     */
    private static final String SLIDING_WINDOW_LUA = """
            local key = KEYS[1]
            local window = tonumber(ARGV[1])
            local limit = tonumber(ARGV[2])
            local now = tonumber(ARGV[3])
            local start = now - window * 1000

            redis.call('ZREMRANGEBYSCORE', key, 0, start)
            local current = redis.call('ZCARD', key)
            if current < limit then
                redis.call('ZADD', key, now, now .. '-' .. math.random())
                redis.call('EXPIRE', key, math.ceil(window + 1))
                return 1
            end
            return 0
            """;

    public RedisRateLimitAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint pjp, RateLimit rateLimit) throws Throwable {
        String key = buildKey(pjp, rateLimit);
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(SLIDING_WINDOW_LUA, Long.class);

        long now = Instant.now().toEpochMilli();
        int windowSeconds = (int) rateLimit.timeUnit().toSeconds(rateLimit.time());

        Long result = redisTemplate.execute(script,
                Collections.singletonList("rate_limit:" + key),
                String.valueOf(windowSeconds),
                String.valueOf(rateLimit.count()),
                String.valueOf(now));

        if (result != null && result == 1) {
            return pjp.proceed();
        }

        log.warn("限流触发: key={}, limit={}/{}s", key, rateLimit.count(), windowSeconds);
        throw new RuntimeException(rateLimit.message());
    }

    private String buildKey(ProceedingJoinPoint pjp, RateLimit rateLimit) {
        if (!rateLimit.key().isEmpty()) {
            return parseSpel(pjp, rateLimit.key());
        }
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
