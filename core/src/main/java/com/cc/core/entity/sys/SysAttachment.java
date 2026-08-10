package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 附件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_attachment")
public class SysAttachment extends BaseEntity {

    /** 原始文件名 */
    private String originalName;

    /** 存储文件名 */
    private String storedName;

    /** 存储路径 */
    private String filePath;

    /** 文件大小(byte) */
    private Long fileSize;

    /** 文件类型 */
    private String mimeType;

    /** 文件扩展名 */
    private String fileExt;

    /** 存储类型: LOCAL/MINIO */
    private String storageType;

    /** MD5校验值 */
    private String md5;

    /** 业务类型 */
    private String businessType;

    /** 业务单据ID */
    private Long businessId;

    /** 上传人 */
    private String uploadedBy;

    /** 上传时间 */
    private String uploadedTime;
}
