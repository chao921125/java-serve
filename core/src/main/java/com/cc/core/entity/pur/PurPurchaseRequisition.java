package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 请购单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_purchase_requisition")
public class PurPurchaseRequisition extends BaseEntity {

    /** 请购单号 */
    private String requisitionNo;

    /** 请购日期 */
    private LocalDate requisitionDate;

    /** 申请部门ID */
    private Long departmentId;

    /** 申请人ID */
    private Long applicantId;

    /** 状态: 0-草稿 1-待审核 2-已审核 3-已处理 4-已关闭 */
    private Integer status;

    /** 期望到货日期 */
    private LocalDate expectedDate;

    /** 审核人ID */
    private Long approverId;

    /** 审核时间 */
    private String approveTime;
}
