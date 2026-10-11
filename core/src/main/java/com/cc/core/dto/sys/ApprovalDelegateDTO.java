// AI生成
package com.cc.core.dto.sys;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 审批转交 DTO
 */
@Data
public class ApprovalDelegateDTO {

    @NotNull(message = "转交人ID不能为空")
    private Long delegateToId;

    private String comment;
}