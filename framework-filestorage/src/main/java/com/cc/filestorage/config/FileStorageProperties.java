package com.cc.filestorage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件存储配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "file-storage")
public class FileStorageProperties {

    /** 存储类型：local / minio / oss / cos */
    private String type = "local";

    /** 本地存储路径 */
    private String localPath = "./uploads";

    /** 文件大小限制（MB） */
    private int maxFileSize = 100;

    /** 允许的文件类型（逗号分隔） */
    private String allowedTypes = "*";

    /** 临时签名 URL 过期时间（秒） */
    private int presignedExpire = 3600;

    /** MinIO 配置 */
    private Minio minio = new Minio();

    /** 阿里云 OSS 配置 */
    private Oss oss = new Oss();

    /** 腾讯云 COS 配置 */
    private Cos cos = new Cos();

    @Data
    public static class Minio {
        private String endpoint = "http://localhost:9000";
        private String accessKey;
        private String secretKey;
        private String bucket = "default";
    }

    @Data
    public static class Oss {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucket;
    }

    @Data
    public static class Cos {
        private String region;
        private String secretId;
        private String secretKey;
        private String bucket;
    }
}
