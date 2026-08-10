package com.cc.xxljob;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * XXL-Job 自动配置
 * 配置前缀：xxl.job
 *
 * @author cc
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(XxlJobAutoConfiguration.XxlJobProperties.class)
public class XxlJobAutoConfiguration {

    @Data
    @ConfigurationProperties(prefix = "xxl.job")
    public static class XxlJobProperties {
        /** 调度中心地址 */
        private String adminAddresses = "http://localhost:9080/xxl-job-admin";
        /** 执行器 AppName */
        private String appname = "java-serve-executor";
        /** 执行器地址（为空则自动注册） */
        private String address;
        /** 执行器 IP（为空则自动获取） */
        private String ip;
        /** 执行器端口 */
        private int port = 9999;
        /** 访问令牌 */
        private String accessToken = "default_token";
        /** 日志保存天数 */
        private int logRetentionDays = 30;
    }

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties properties) {
        log.info(">>> XXL-Job executor init: admin={}, appname={}, port={}",
                properties.getAdminAddresses(), properties.getAppname(), properties.getPort());

        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses(properties.getAdminAddresses());
        executor.setAppname(properties.getAppname());
        if (properties.getAddress() != null && !properties.getAddress().isEmpty()) {
            executor.setAddress(properties.getAddress());
        }
        if (properties.getIp() != null && !properties.getIp().isEmpty()) {
            executor.setIp(properties.getIp());
        }
        executor.setPort(properties.getPort());
        executor.setAccessToken(properties.getAccessToken());
        executor.setLogRetentionDays(properties.getLogRetentionDays());
        return executor;
    }
}
