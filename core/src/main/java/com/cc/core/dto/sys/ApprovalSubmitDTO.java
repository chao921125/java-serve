// AI生成
package com.cc.core.dto.sys;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 审批提交 DTO
 */
@Data
public class ApprovalSubmitDTO {

    @NotBlank(message = "业务类型不能为空")
    private String businessType;

    @NotNull(message = "业务单据ID不能为空")
    private Long businessId;

    @NotBlank(message = "业务单号不能为空")
    private String businessNo;

    /** 表单数据(JSON)，用于条件表达式求值 */
    private String formData;
}