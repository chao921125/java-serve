package com.cc.core.entity.crm;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 销售线索
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_lead")
public class CrmLead extends BaseEntity {

    /** 公司名称 */
    private String companyName;

    /** 联系人 */
    private String contactName;

    /** 电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 线索来源: WEBSITE/EXHIBITION/REFERRAL/AD/OTHER */
    private String source;

    /** 行业 */
    private String industry;

    /** 地址 */
    private String address;

    /** 状态: 0-新线索 1-已联系 2-已确认 3-已转化 4-已关闭 */
    private Integer status;

    /** 转化客户ID */
    private Long convertToCustomerId;

    /** 负责人ID */
    private Long ownerId;

    /** 下次跟进日期 */
    private LocalDate nextFollowDate;
}
