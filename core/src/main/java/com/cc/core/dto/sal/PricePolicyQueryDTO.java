package com.cc.core.dto.sal;

import lombok.Data;

/**
 * 价格策略查询条件
 */
@Data
public class PricePolicyQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 策略名称 */
    private String name;

    /** 类型 */
    private Integer type;

    /** 客户 ID */
    private Long customerId;

    /** 商品 ID */
    private Long productId;

    /** 状态 */
    private Integer status;
}
