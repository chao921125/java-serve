package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 组装拆卸单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inv_assemblies")
public class InvAssembly extends BaseEntity {

    /** 组装拆卸单号 */
    private String assemblyNo;

    /** 类型 0-组装 1-拆卸 */
    private Integer type;

    /** 仓库 ID */
    private Long warehouseId;

    /** 成品商品 ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 状态 0-草稿 1-待审 2-已审 3-已完成 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
