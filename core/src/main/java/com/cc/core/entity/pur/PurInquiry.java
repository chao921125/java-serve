package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 询价单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_inquiry")
public class PurInquiry extends BaseEntity {

    /** 询价单号 */
    private String inquiryNo;

    /** 询价日期 */
    private LocalDate inquiryDate;

    /** 关联请购单ID */
    private Long requisitionId;

    /** 状态: 0-草稿 1-已发出 2-已报价 3-已比价 4-已转化 */
    private Integer status;

    /** 报价截止日期 */
    private LocalDate deadline;
}
