package com.cc.core.entity.crm;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 跟进记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_follow_record")
public class CrmFollowRecord extends BaseEntity {

    /** 线索ID */
    private Long leadId;

    /** 客户ID */
    private Long customerId;

    /** 跟进方式: PHONE/VISIT/EMAIL/MEETING/OTHER */
    private String followType;

    /** 跟进日期 */
    private LocalDate followDate;

    /** 跟进内容 */
    private String content;

    /** 跟进结果 */
    private String result;

    /** 下一步计划 */
    private String nextPlan;

    /** 跟进人 */
    private String followedBy;
}
