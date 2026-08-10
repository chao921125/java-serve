package com.cc.core.dto.bas;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户新增/修改
 */
@Data
public class CustomerSaveDTO {

    /** 客户编码 */
    private String code;

    /** 客户名称 */
    private String name;

    /** 简称 */
    private String shortName;

    /** 信用额度 */
    private BigDecimal creditLimit;

    /** 等级 */
    private Integer level;

    /** 联系人 */
    private String contact;

    /** 电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 地址 */
    private String address;

    /** 税号 */
    private String taxNo;

    /** 开户行 */
    private String bankName;

    /** 银行账号 */
    private String bankAccount;

    /** 状态 0-正常 1-停用 */
    private Integer status;

    /** 备注 */
    private String remark;
}
