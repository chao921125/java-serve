package com.cc.core.dto.bas;

import lombok.Data;

/**
 * 供应商新增/修改
 */
@Data
public class SupplierSaveDTO {

    /** 供应商编码 */
    private String code;

    /** 供应商名称 */
    private String name;

    /** 简称 */
    private String shortName;

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

    /** 付款条件 */
    private String paymentTerms;

    /** 状态 0-正常 1-停用 */
    private Integer status;

    /** 备注 */
    private String remark;
}
