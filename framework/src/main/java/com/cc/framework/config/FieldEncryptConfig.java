package com.cc.framework.config;

import com.cc.framework.utils.FieldEncryptUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 字段加密自动配置
 * <p>
 * 配置项：
 * <pre>
 * application:
 *   security:
 *     field-encrypt-key: <Base64 编码的 AES-256 密钥>
 * </pre>
 * 如果未配置，自动生成随机密钥（开发环境可用，生产环境务必通过配置中心指定固定密钥）
 */
@Slf4j
@Configuration
public class FieldEncryptConfig {

    @Value("${application.security.field-encrypt-key:}")
    private String fieldEncryptKey;

    @PostConstruct
    public void init() {
        if (fieldEncryptKey != null && !fieldEncryptKey.isEmpty()) {
            FieldEncryptUtil.setEncryptKey(fieldEncryptKey);
            log.info("字段加密密钥已从配置加载");
        } else {
            // 开发环境自动生成密钥
            String key = FieldEncryptUtil.generateKey();
            FieldEncryptUtil.setEncryptKey(key);
            log.warn("未配置字段加密密钥，已自动生成随机密钥。生产环境请通过 application.security.field-encrypt-key 配置固定密钥。");
        }
    }
}
