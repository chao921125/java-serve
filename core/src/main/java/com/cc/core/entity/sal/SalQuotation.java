package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售报价单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_quotation")
public class SalQuotation extends BaseEntity {

    /** 报价单号 */
    private String quotationNo;

    /** 报价日期 */
    private LocalDate quotationDate;

    /** 客户ID */
    private Long customerId;

    /** 业务员ID */
    private Long salespersonId;

    /** 状态: 0-草稿 1-已发出 2-已确认 3-已转订单 4-已失效 */
    private Integer status;

    /** 有效期至 */
    private LocalDate validUntil;

    /** 报价金额 */
    private BigDecimal totalAmount;

    /** 整单折扣率% */
    private BigDecimal discountRate;

    /** 折后金额 */
    private BigDecimal afterDiscount;

    /** 税额 */
    private BigDecimal taxAmount;

    /** 含税最终金额 */
    private BigDecimal finalAmount;

    /** 付款条件 */
    private String paymentTerms;

    /** 交货条件 */
    private String deliveryTerms;

    /** 审核人ID */
    private Long approverId;

    /** 审核时间 */
    private String approveTime;
}
