package com.cc.core.entity.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 合同
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_contract")
public class BizContract extends BaseEntity {

    /** 合同编号 */
    private String contractNo;

    /** 合同名称 */
    private String contractName;

    /** 类型: PURCHASE/SALES/SERVICE/OTHER */
    private String contractType;

    /** 对方类型: CUSTOMER/SUPPLIER */
    private String counterpartyType;

    /** 对方ID */
    private Long counterpartyId;

    /** 对方名称 */
    private String counterpartyName;

    /** 合同金额 */
    private BigDecimal contractAmount;

    /** 已结算金额 */
    private BigDecimal signedAmount;

    /** 开始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 签订日期 */
    private LocalDate signedDate;

    /** 状态: 0-草稿 1-执行中 2-已完成 3-已终止 4-已过期 */
    private Integer status;

    /** 付款条款 */
    private String paymentTerms;

    /** 交货条款 */
    private String deliveryTerms;

    /** 负责人 */
    private String responsiblePerson;

    /** 附件数量 */
    private Integer attachmentCount;
}
