package com.cc.framework.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态数据源自动配置（读写分离）
 * <p>
 * 启用条件：引入 dynamic-datasource-spring-boot3-starter 并配置多数据源
 */
@Configuration
@ConditionalOnClass(DynamicRoutingDataSource.class)
public class DynamicDataSourceConfig {

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationAdvisor dynamicDataSourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(
                new DynamicDataSourceAnnotationInterceptor(true, null), DS.class);
    }
}
