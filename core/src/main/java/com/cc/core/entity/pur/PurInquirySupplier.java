package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 询价供应商
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_inquiry_supplier")
public class PurInquirySupplier extends BaseEntity {

    /** 询价单ID */
    private Long inquiryId;

    /** 供应商ID */
    private Long supplierId;

    /** 报价状态: 0-未报价 1-已报价 2-已拒绝 */
    private Integer quoteStatus;
}
