package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 补货建议
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_replenishment_suggestion")
public class PurReplenishmentSuggestion extends BaseEntity {

    /** 商品ID */
    private Long productId;

    /** 仓库ID */
    private Long warehouseId;

    /** 当前库存 */
    private BigDecimal currentQuantity;

    /** 安全库存 */
    private BigDecimal safetyQuantity;

    /** 建议补货数量 */
    private BigDecimal suggestedQuantity;

    /** 日均销量 */
    private BigDecimal avgDailySales;

    /** 采购提前期(天) */
    private Integer leadTimeDays;

    /** 建议采购日期 */
    private LocalDate suggestedDate;

    /** 状态: 0-待处理 1-已生成请购 2-已忽略 */
    private Integer status;
}
