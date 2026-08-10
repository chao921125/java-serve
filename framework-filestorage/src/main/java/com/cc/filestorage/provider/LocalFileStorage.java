package com.cc.filestorage.provider;

import com.cc.filestorage.core.FileInfo;
import com.cc.filestorage.core.FileStorage;
import com.cc.filestorage.config.FileStorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 本地文件存储实现
 */
public class LocalFileStorage implements FileStorage {

    private static final Logger log = LoggerFactory.getLogger(LocalFileStorage.class);

    private final Path basePath;

    public LocalFileStorage(FileStorageProperties properties) {
        this.basePath = Paths.get(properties.getLocalPath()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(basePath);
            log.info("本地文件存储初始化: {}", basePath);
        } catch (IOException e) {
            throw new RuntimeException("创建本地存储目录失败: " + basePath, e);
        }
    }

    @Override
    public String upload(InputStream inputStream, String fileName, String contentType) {
        String storedName = generateStoredName(fileName);
        Path destPath = basePath.resolve(storedName);

        try {
            Files.createDirectories(destPath.getParent());
            Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
            log.debug("文件上传成功: {} -> {}", fileName, destPath);
            return storedName;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + fileName, e);
        }
    }

    @Override
    public String uploadChunked(InputStream inputStream, String fileName, long partSize) {
        // 本地存储不需要分片，直接上传
        return upload(inputStream, fileName, null);
    }

    @Override
    public InputStream download(String filePath) {
        Path file = validatePath(filePath);
        try {
            return Files.newInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败: " + filePath, e);
        }
    }

    @Override
    public boolean delete(String filePath) {
        try {
            Path file = validatePath(filePath);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            log.error("文件删除失败: {}", filePath, e);
            return false;
        }
    }

    @Override
    public int deleteBatch(List<String> filePaths) {
        int count = 0;
        for (String path : filePaths) {
            if (delete(path)) count++;
        }
        return count;
    }

    @Override
    public String generatePresignedUrl(String filePath, int expireSeconds) {
        // 本地存储直接返回文件路径（由 Controller 提供访问）
        return "/api/file/download?path=" + filePath;
    }

    @Override
    public FileInfo getFileInfo(String filePath) {
        Path file = validatePath(filePath);
        try {
            return FileInfo.builder()
                    .filePath(filePath)
                    .fileName(file.getFileName().toString())
                    .fileSize(Files.size(file))
                    .contentType(Files.probeContentType(file))
                    .uploadTime(LocalDateTime.now().toString())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("获取文件信息失败: " + filePath, e);
        }
    }

    @Override
    public boolean exists(String filePath) {
        return Files.exists(validatePath(filePath));
    }

    @Override
    public String getStorageType() {
        return "local";
    }

    /**
     * 校验并返回安全路径（防止路径穿越）
     */
    private Path validatePath(String filePath) {
        Path resolved = basePath.resolve(filePath).normalize();
        if (!resolved.startsWith(basePath)) {
            throw new SecurityException("非法的文件路径: " + filePath);
        }
        return resolved;
    }

    /**
     * 生成存储文件名：日期/年月/UUID.扩展名
     */
    private String generateStoredName(String originalName) {
        LocalDateTime now = LocalDateTime.now();
        String dateDir = String.format("%d/%02d",
                now.getYear(), now.getMonthValue());
        String ext = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            ext = originalName.substring(dotIndex);
        }
        return dateDir + "/" + UUID.randomUUID().toString().replace("-", "") + ext;
    }
}
