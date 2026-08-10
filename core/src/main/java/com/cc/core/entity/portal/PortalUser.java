package com.cc.core.entity.portal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 门户用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("portal_user")
public class PortalUser extends BaseEntity {

    /** 用户名 */
    private String username;

    /** 密码(BCrypt) */
    private String password;

    /** 门户类型: CUSTOMER/SUPPLIER */
    private String portalType;

    /** 关联客户ID */
    private Long customerId;

    /** 关联供应商ID */
    private Long supplierId;

    /** 联系人 */
    private String contactName;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 状态: 0-停用 1-启用 */
    private Integer status;

    /** 最后登录时间 */
    private String lastLoginTime;
}
