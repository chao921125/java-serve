package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 价格策略表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_price_policies")
public class SalPricePolicy extends BaseEntity {

    /** 策略名称 */
    private String name;

    /** 类型 0-会员价 1-促销价 2-客户专属价 */
    private Integer type;

    /** 客户 ID */
    private Long customerId;

    /** 客户等级 */
    private Integer customerLevel;

    /** 商品 ID */
    private Long productId;

    /** 商品分类 ID */
    private Long categoryId;

    /** 折扣率 */
    private BigDecimal discountRate;

    /** 固定价格 */
    private BigDecimal fixedPrice;

    /** 生效日期 */
    private LocalDate startDate;

    /** 失效日期 */
    private LocalDate endDate;

    /** 状态 0-停用 1-正常 */
    private Integer status;
}
