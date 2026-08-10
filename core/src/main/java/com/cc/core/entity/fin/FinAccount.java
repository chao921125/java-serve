package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金账户
 */
@Data
@TableName("fin_accounts")
public class FinAccount {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 账户编码 */
    private String code;

    /** 账户名称 */
    private String name;

    /** 账户类型 0-现金 1-银行 2-支付宝 3-微信 */
    private Integer type;

    /** 开户行 */
    private String bankName;

    /** 银行账号 */
    private String bankAccount;

    /** 余额 */
    private BigDecimal balance;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
