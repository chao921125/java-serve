package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 应付账款
 */
@Data
@TableName("fin_payables")
public class FinPayable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 供应商 ID */
    private Long supplierId;

    /** 来源类型 */
    private String sourceType;

    /** 来源 ID */
    private Long sourceId;

    /** 来源单号 */
    private String sourceNo;

    /** 金额 */
    private BigDecimal amount;

    /** 已付金额 */
    private BigDecimal paidAmount;

    /** 余额 */
    private BigDecimal balance;

    /** 到期日 */
    private LocalDate dueDate;

    /** 状态 0-未核销 1-部分核销 2-已核销 */
    private Integer status;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 备注 */
    private String remark;
}
