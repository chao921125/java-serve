package com.cc.core.entity.rpt;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导出记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rpt_export_record")
public class RptExportRecord extends BaseEntity {

    /** 报表模板ID */
    private Long reportId;

    /** 导出类型: EXCEL/PDF/CSV */
    private String exportType;

    /** 文件名 */
    private String fileName;

    /** 文件存储路径 */
    private String filePath;

    /** 文件大小(byte) */
    private Long fileSize;

    /** 导出筛选条件JSON */
    private String filterParams;

    /** 导出时间 */
    private String exportedTime;

    /** 导出人 */
    private String exportedBy;
}
