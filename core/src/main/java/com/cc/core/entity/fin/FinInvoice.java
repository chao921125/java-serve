package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 发票
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_invoice")
public class FinInvoice extends BaseEntity {

    /** 发票号码 */
    private String invoiceNo;

    /** 发票代码 */
    private String invoiceCode;

    /** 类型: PURCHASE_IN-进项, SALES_OUT-销项 */
    private String invoiceType;

    /** 开票日期 */
    private LocalDate invoiceDate;

    /** 来源单据类型 */
    private String sourceType;

    /** 来源单据ID */
    private Long sourceId;

    /** 对方单位ID */
    private Long counterpartyId;

    /** 对方单位名称 */
    private String counterpartyName;

    /** 发票金额(不含税) */
    private BigDecimal invoiceAmount;

    /** 税率% */
    private BigDecimal taxRate;

    /** 税额 */
    private BigDecimal taxAmount;

    /** 价税合计 */
    private BigDecimal totalAmount;

    /** 状态: 0-待开票 1-已开票 2-已认证/已红冲 3-已作废 */
    private Integer status;

    /** 认证状态: PENDING/VERIFIED/FAILED */
    private String verificationStatus;

    /** 认证日期 */
    private LocalDate verificatedDate;
}
