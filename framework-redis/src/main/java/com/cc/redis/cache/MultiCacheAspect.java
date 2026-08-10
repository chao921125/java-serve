package com.cc.redis.cache;

import com.cc.redis.annotation.MultiCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
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
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 多级缓存切面（Caffeine 本地 -> Redis 远程 -> 回源数据库）
 */
@Aspect
@Component
@ConditionalOnClass(RedisTemplate.class)
public class MultiCacheAspect {

    private static final Logger log = LoggerFactory.getLogger(MultiCacheAspect.class);

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    /** 本地 Caffeine 缓存（按缓存名称分组） */
    private final Map<String, Cache<String, Object>> localCaches = new ConcurrentHashMap<>();

    /** 缓存 null 标记 */
    private static final String NULL_MARKER = "__NULL__";

    public MultiCacheAspect(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(multiCache)")
    public Object around(ProceedingJoinPoint pjp, MultiCache multiCache) throws Throwable {
        String cacheName = multiCache.name();
        String cacheKey = buildCacheKey(pjp, multiCache);

        // 1. 先查 Caffeine 本地缓存
        Cache<String, Object> localCache = getLocalCache(cacheName, multiCache);
        Object cachedValue = localCache.getIfPresent(cacheKey);
        if (cachedValue != null) {
            if (NULL_MARKER.equals(cachedValue)) {
                return null;
            }
            return cachedValue;
        }

        // 2. 再查 Redis 远程缓存
        String redisKey = cacheName + ":" + cacheKey;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (redisValue != null) {
            if (NULL_MARKER.equals(redisValue)) {
                localCache.put(cacheKey, NULL_MARKER);
                return null;
            }
            localCache.put(cacheKey, redisValue);
            return redisValue;
        }

        // 3. 回源数据库
        Object result = pjp.proceed();

        // 4. 写入两级缓存
        if (result != null) {
            redisTemplate.opsForValue().set(redisKey, result,
                    multiCache.remoteExpire(), multiCache.timeUnit());
            localCache.put(cacheKey, result);
        } else if (multiCache.cacheNull()) {
            // 防缓存穿透：缓存空值
            redisTemplate.opsForValue().set(redisKey, NULL_MARKER,
                    Math.min(multiCache.remoteExpire(), 60), TimeUnit.SECONDS);
            localCache.put(cacheKey, NULL_MARKER);
        }

        return result;
    }

    /**
     * 构建缓存 key
     */
    private String buildCacheKey(ProceedingJoinPoint pjp, MultiCache multiCache) {
        if (!multiCache.key().isEmpty()) {
            return parseSpel(pjp, multiCache.key());
        }
        // 默认使用所有参数的 hashCode
        StringBuilder sb = new StringBuilder();
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg != null) {
                try {
                    sb.append(objectMapper.writeValueAsString(arg));
                } catch (JsonProcessingException e) {
                    sb.append(arg.toString());
                }
            }
        }
        return String.valueOf(sb.toString().hashCode());
    }

    /**
     * 获取或创建本地缓存区域
     */
    private Cache<String, Object> getLocalCache(String name, MultiCache multiCache) {
        return localCaches.computeIfAbsent(name, k -> Caffeine.newBuilder()
                .expireAfterWrite(multiCache.localExpire(), multiCache.timeUnit())
                .maximumSize(multiCache.localMaxSize())
                .recordStats()
                .build());
    }

    /**
     * 解析 SpEL 表达式
     */
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
