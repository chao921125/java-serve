package com.cc.core.entity.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合同变更记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_contract_change_log")
public class BizContractChangeLog extends BaseEntity {

    /** 合同ID */
    private Long contractId;

    /** 变更类型: AMOUNT/DATE/TERMS/OTHER */
    private String changeType;

    /** 变更前内容 */
    private String beforeValue;

    /** 变更后内容 */
    private String afterValue;

    /** 变更原因 */
    private String changeReason;

    /** 变更人 */
    private String changedBy;

    /** 变更时间 */
    private String changedTime;
}
