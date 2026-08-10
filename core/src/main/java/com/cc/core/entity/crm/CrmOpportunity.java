package com.cc.core.entity.crm;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 商机
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_opportunity")
public class CrmOpportunity extends BaseEntity {

    /** 商机名称 */
    private String name;

    /** 客户ID */
    private Long customerId;

    /** 预计金额 */
    private BigDecimal expectedAmount;

    /** 成交概率% */
    private Integer probability;

    /** 阶段: INITIAL/NEEDS_ANALYSIS/QUOTATION/NEGOTIATION/WON/LOST */
    private String stage;

    /** 预计成交日期 */
    private LocalDate expectedCloseDate;

    /** 负责人ID */
    private Long ownerId;

    /** 竞争对手 */
    private String competitor;
}
