package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据备份记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_data_backup")
public class SysDataBackup extends BaseEntity {

    /** 备份类型: FULL/INCREMENTAL */
    private String backupType;

    /** 备份文件路径 */
    private String backupFile;

    /** 文件大小(byte) */
    private Long fileSize;

    /** 状态: 0-进行中 1-成功 2-失败 */
    private Integer status;

    /** 错误信息 */
    private String errorMessage;

    /** 开始时间 */
    private String startedTime;

    /** 完成时间 */
    private String completedTime;

    /** 触发人 */
    private String triggeredBy;
}
