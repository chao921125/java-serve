package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 结算方式
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_settlement_methods")
public class BasSettlementMethod extends BaseEntity {

    /** 名称 */
    private String name;

    /** 编码 */
    private String code;

    /** 类型 */
    private Integer type;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
