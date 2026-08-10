package com.cc.filestorage.core;

import java.io.InputStream;
import java.util.List;

/**
 * 统一文件存储抽象接口
 * <p>
 * 支持本地存储、MinIO、阿里云 OSS、腾讯云 COS 等多种后端
 */
public interface FileStorage {

    /**
     * 上传文件
     * @param inputStream 文件流
     * @param fileName   文件名
     * @param contentType MIME 类型
     * @return 文件访问路径
     */
    String upload(InputStream inputStream, String fileName, String contentType);

    /**
     * 分片上传
     * @param inputStream 文件流
     * @param fileName   文件名
     * @param partSize   分片大小（字节）
     * @return 文件访问路径
     */
    String uploadChunked(InputStream inputStream, String fileName, long partSize);

    /**
     * 下载文件
     * @param filePath 文件路径
     * @return 文件流
     */
    InputStream download(String filePath);

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return 是否成功
     */
    boolean delete(String filePath);

    /**
     * 批量删除
     * @param filePaths 文件路径列表
     * @return 删除成功的数量
     */
    int deleteBatch(List<String> filePaths);

    /**
     * 生成带签名的临时访问 URL（用于私有文件访问）
     * @param filePath     文件路径
     * @param expireSeconds 过期时间（秒）
     * @return 签名 URL
     */
    String generatePresignedUrl(String filePath, int expireSeconds);

    /**
     * 获取文件元信息
     * @param filePath 文件路径
     * @return 文件信息
     */
    FileInfo getFileInfo(String filePath);

    /**
     * 文件是否存在
     * @param filePath 文件路径
     * @return 是否存在
     */
    boolean exists(String filePath);

    /**
     * 存储类型标识
     */
    String getStorageType();
}
