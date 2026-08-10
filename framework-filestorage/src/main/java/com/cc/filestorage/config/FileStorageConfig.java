package com.cc.filestorage.config;

import com.cc.filestorage.core.FileStorage;
import com.cc.filestorage.provider.LocalFileStorage;
import com.cc.filestorage.provider.MinioFileStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储自动配置
 * <p>
 * 根据 file-storage.type 配置自动选择存储后端
 */
@Configuration
public class FileStorageConfig {

    @Bean
    @ConditionalOnProperty(name = "file-storage.type", havingValue = "local", matchIfMissing = true)
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage localFileStorage(FileStorageProperties properties) {
        return new LocalFileStorage(properties);
    }

    @Bean
    @ConditionalOnProperty(name = "file-storage.type", havingValue = "minio")
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage minioFileStorage(FileStorageProperties properties) {
        return new MinioFileStorage(properties);
    }
}
