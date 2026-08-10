package com.cc.core.service.pur;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.pur.PurPurchaseRequisition;

/**
 * PurPurchaseRequisition 服务接口
 */
public interface PurPurchaseRequisitionService extends IService<PurPurchaseRequisition> {


    /**
     * 提交审核
     */
    void submitForApproval(Long id);

    /**
     * 审核
     */
    void approve(Long id, Long approverId);

    /**
     * 驳回
     */
    void reject(Long id, Long approverId, String reason);

    /**
     * 关闭
     */
    void close(Long id);

}
