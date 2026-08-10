package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户流水
 */
@Data
@TableName("fin_account_transactions")
public class FinAccountTransaction {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 账户 ID */
    private Long accountId;

    /** 交易类型 0-收入 1-支出 */
    private Integer transactionType;

    /** 金额 */
    private BigDecimal amount;

    /** 变更前余额 */
    private BigDecimal balanceBefore;

    /** 变更后余额 */
    private BigDecimal balanceAfter;

    /** 来源类型 */
    private String sourceType;

    /** 来源 ID */
    private Long sourceId;

    /** 来源单号 */
    private String sourceNo;

    /** 交易时间 */
    private LocalDateTime transactionTime;

    /** 操作人 ID */
    private Long operatorId;

    /** 备注 */
    private String remark;
}
