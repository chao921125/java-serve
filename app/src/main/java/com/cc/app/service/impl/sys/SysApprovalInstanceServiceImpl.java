package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysApprovalInstance;
import com.cc.core.mapper.sys.SysApprovalInstanceMapper;
import com.cc.core.service.sys.SysApprovalInstanceService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.cc.core.service.sys.SysApprovalFlowService;
import com.cc.core.service.sys.SysApprovalNodeService;
import com.cc.core.entity.sys.SysApprovalFlow;
import com.cc.core.entity.sys.SysApprovalNode;

/**
 * SysApprovalInstance 服务实现
 */
@Service
@RequiredArgsConstructor
public class SysApprovalInstanceServiceImpl extends ServiceImpl<SysApprovalInstanceMapper, SysApprovalInstance> implements SysApprovalInstanceService {
    private final SysApprovalFlowService approvalFlowService;
    private final SysApprovalNodeService approvalNodeService;


    // ==== Business Logic Methods ====

    @Override
    public void submit(String businessType, Long businessId, String businessNo, Long applicantId, String applicantName) {
        // 查找匹配的审批流
        java.util.List<SysApprovalFlow> flows = approvalFlowService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysApprovalFlow>()
                .eq(SysApprovalFlow::getTargetType, businessType)
                .eq(SysApprovalFlow::getIsEnabled, 1)
        );
        if (flows.isEmpty()) throw new RuntimeException("未找到审批流配置: " + businessType);

        SysApprovalFlow flow = flows.get(0);
        java.util.List<SysApprovalNode> nodes = approvalNodeService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysApprovalNode>()
                .eq(SysApprovalNode::getFlowId, flow.getId())
                .orderByAsc(SysApprovalNode::getNodeOrder)
        );
        if (nodes.isEmpty()) throw new RuntimeException("审批流未配置节点");

        SysApprovalInstance instance = new SysApprovalInstance();
        instance.setFlowId(flow.getId());
        instance.setBusinessType(businessType);
        instance.setBusinessId(businessId);
        instance.setBusinessNo(businessNo);
        instance.setCurrentNodeOrder(1);
        instance.setTotalNodes(nodes.size());
        instance.setStatus(0);
        instance.setApplicantId(applicantId);
        instance.setApplicantName(applicantName);
        instance.setAppliedTime(java.time.LocalDateTime.now().toString());
        save(instance);
    }

    @Override
    public java.util.List<SysApprovalInstance> getMyApply(Long userId) {
        return lambdaQuery().eq(SysApprovalInstance::getApplicantId, userId)
                .orderByDesc(SysApprovalInstance::getAppliedTime).list();
    }

    @Override
    public java.util.List<SysApprovalInstance> getMyApproval(Long userId) {
        return lambdaQuery().eq(SysApprovalInstance::getStatus, 0)
                .orderByDesc(SysApprovalInstance::getAppliedTime).list();
    }

    @Override
    public java.util.List<SysApprovalInstance> getMyDone(Long userId) {
        return lambdaQuery().eq(SysApprovalInstance::getApplicantId, userId)
                .ne(SysApprovalInstance::getStatus, 0)
                .orderByDesc(SysApprovalInstance::getCompletedTime).list();
    }

}
