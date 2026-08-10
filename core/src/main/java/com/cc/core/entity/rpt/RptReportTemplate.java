package com.cc.core.entity.rpt;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 报表模板
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rpt_report_template")
public class RptReportTemplate extends BaseEntity {

    /** 报表名称 */
    private String name;

    /** 报表编码 */
    private String code;

    /** 报表分类: SALES/PURCHASE/INVENTORY/FINANCE/HR */
    private String category;

    /** 查询SQL */
    private String querySql;

    /** 列配置JSON */
    private String columnsConfig;

    /** 筛选条件配置JSON */
    private String filtersConfig;

    /** 图表配置JSON */
    private String chartsConfig;

    /** 是否系统预置 */
    private Integer isSystem;

    /** 启用状态 */
    private Integer isEnabled;

    /** 排序号 */
    private Integer sortOrder;
}
