package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 付款方式
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_payment_methods")
public class BasPaymentMethod extends BaseEntity {

    /** 名称 */
    private String name;

    /** 编码 */
    private String code;

    /** 账户 ID */
    private Long accountId;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
