package com.cc.redis.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine 本地缓存配置
 */
@Configuration
@EnableCaching
@ConditionalOnClass(Caffeine.class)
public class CaffeineConfig {

    /**
     * Caffeine 本地缓存管理器
     */
    @Bean
    @Primary
    @ConditionalOnClass(name = "org.springframework.cache.caffeine.CaffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>();

        // 预建常用缓存区域
        caches.add(buildCache("dict", 600, 500));
        caches.add(buildCache("config", 600, 200));
        caches.add(buildCache("permission", 300, 500));
        caches.add(buildCache("sys_menu", 300, 200));

        manager.setCaches(caches);
        return manager;
    }

    private CaffeineCache buildCache(String name, int expireSeconds, int maxSize) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .maximumSize(maxSize)
                .recordStats()
                .build());
    }
}
