package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurPurchaseRequisition;
import com.cc.core.mapper.pur.PurPurchaseRequisitionMapper;
import com.cc.core.service.pur.PurPurchaseRequisitionService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * PurPurchaseRequisition 服务实现
 */
@Service
public class PurPurchaseRequisitionServiceImpl extends ServiceImpl<PurPurchaseRequisitionMapper, PurPurchaseRequisition> implements PurPurchaseRequisitionService {

    // ==== Business Logic Methods ====

    @Override
    public void submitForApproval(Long id) {
        PurPurchaseRequisition entity = getById(id);
        if (entity == null) throw new RuntimeException("请购单不存在");
        if (entity.getStatus() != 0) throw new RuntimeException("仅草稿状态可提交审核");
        entity.setStatus(1);
        updateById(entity);
    }

    @Override
    public void approve(Long id, Long approverId) {
        PurPurchaseRequisition entity = getById(id);
        if (entity == null) throw new RuntimeException("请购单不存在");
        if (entity.getStatus() != 1) throw new RuntimeException("仅待审核状态可审批");
        entity.setStatus(2);
        entity.setApproverId(approverId);
        entity.setApproveTime(java.time.LocalDateTime.now().toString());
        updateById(entity);
    }

    @Override
    public void reject(Long id, Long approverId, String reason) {
        PurPurchaseRequisition entity = getById(id);
        if (entity == null) throw new RuntimeException("请购单不存在");
        if (entity.getStatus() != 1) throw new RuntimeException("仅待审核状态可驳回");
        entity.setStatus(0);
        entity.setApproverId(approverId);
        entity.setRemark((entity.getRemark() == null ? "" : entity.getRemark()) + " [驳回原因: " + reason + "]");
        updateById(entity);
    }

    @Override
    public void close(Long id) {
        PurPurchaseRequisition entity = getById(id);
        if (entity == null) throw new RuntimeException("请购单不存在");
        entity.setStatus(4);
        updateById(entity);
    }

}
