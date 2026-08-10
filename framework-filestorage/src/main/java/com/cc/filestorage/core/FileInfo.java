package com.cc.filestorage.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件元信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    /** 文件路径 */
    private String filePath;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private long fileSize;

    /** MIME 类型 */
    private String contentType;

    /** MD5 哈希 */
    private String md5;

    /** 上传者 */
    private String uploadBy;

    /** 上传时间 */
    private String uploadTime;

    /** 访问 URL */
    private String url;
}
