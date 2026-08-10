package com.cc.filestorage.provider;

import com.cc.filestorage.core.FileInfo;
import com.cc.filestorage.core.FileStorage;
import com.cc.filestorage.config.FileStorageProperties;
import io.minio.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * MinIO 文件存储实现
 */
public class MinioFileStorage implements FileStorage {

    private static final Logger log = LoggerFactory.getLogger(MinioFileStorage.class);

    private final MinioClient client;
    private final String bucket;

    public MinioFileStorage(FileStorageProperties properties) {
        FileStorageProperties.Minio config = properties.getMinio();
        this.client = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
        this.bucket = config.getBucket();

        try {
            boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                log.info("创建 MinIO Bucket: {}", bucket);
            }
            log.info("MinIO 文件存储初始化: {}", config.getEndpoint());
        } catch (Exception e) {
            throw new RuntimeException("MinIO 初始化失败", e);
        }
    }

    @Override
    public String upload(InputStream inputStream, String fileName, String contentType) {
        String objectName = UUID.randomUUID().toString().replace("-", "") + "-" + fileName;
        try {
            client.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(contentType)
                    .build());
            log.debug("MinIO 上传成功: {}", objectName);
            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("MinIO 上传失败: " + fileName, e);
        }
    }

    @Override
    public String uploadChunked(InputStream inputStream, String fileName, long partSize) {
        // MinIO SDK 自动处理分片
        return upload(inputStream, fileName, "application/octet-stream");
    }

    @Override
    public InputStream download(String objectName) {
        try {
            return client.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("MinIO 下载失败: " + objectName, e);
        }
    }

    @Override
    public boolean delete(String objectName) {
        try {
            client.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            log.error("MinIO 删除失败: {}", objectName, e);
            return false;
        }
    }

    @Override
    public int deleteBatch(List<String> objectNames) {
        int count = 0;
        for (String name : objectNames) {
            if (delete(name)) count++;
        }
        return count;
    }

    @Override
    public String generatePresignedUrl(String objectName, int expireSeconds) {
        try {
            return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .method(Method.GET)
                    .expiry(expireSeconds)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("MinIO 生成签名 URL 失败: " + objectName, e);
        }
    }

    @Override
    public FileInfo getFileInfo(String objectName) {
        try {
            var stat = client.statObject(StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            return FileInfo.builder()
                    .filePath(objectName)
                    .fileName(objectName)
                    .fileSize(stat.size())
                    .contentType(stat.contentType())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("MinIO 获取文件信息失败: " + objectName, e);
        }
    }

    @Override
    public boolean exists(String objectName) {
        try {
            client.statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getStorageType() {
        return "minio";
    }
}
