package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 应收账款
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_receivables")
public class FinReceivable extends BaseEntity {

    /** 客户 ID */
    private Long customerId;

    /** 来源类型 */
    private String sourceType;

    /** 来源 ID */
    private Long sourceId;

    /** 来源单号 */
    private String sourceNo;

    /** 金额 */
    private BigDecimal amount;

    /** 已收金额 */
    private BigDecimal receivedAmount;

    /** 余额 */
    private BigDecimal balance;

    /** 到期日 */
    private LocalDate dueDate;

    /** 状态 0-未核销 1-部分核销 2-已核销 */
    private Integer status;
}
