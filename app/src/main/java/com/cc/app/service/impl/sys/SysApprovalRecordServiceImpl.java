package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysApprovalRecord;
import com.cc.core.mapper.sys.SysApprovalRecordMapper;
import com.cc.core.service.sys.SysApprovalRecordService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.cc.core.entity.sys.SysApprovalInstance;
import com.cc.core.service.sys.SysApprovalInstanceService;
import com.cc.core.service.sys.SysApprovalNodeService;
import com.cc.core.entity.sys.SysApprovalNode;

/**
 * SysApprovalRecord 服务实现
 */
@Service
@RequiredArgsConstructor
public class SysApprovalRecordServiceImpl extends ServiceImpl<SysApprovalRecordMapper, SysApprovalRecord> implements SysApprovalRecordService {
    private final SysApprovalInstanceService approvalInstanceService;
    private final SysApprovalNodeService approvalNodeService;


    // ==== Business Logic Methods ====

    @Override
    public SysApprovalInstance approve(Long instanceId, Long approverId, String approverName, String comment) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) throw new RuntimeException("审批实例不存在");
        if (instance.getStatus() != 0) throw new RuntimeException("审批已结束");

        // 查找当前节点
        java.util.List<SysApprovalNode> nodes = approvalNodeService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysApprovalNode>()
                .eq(SysApprovalNode::getFlowId, instance.getFlowId())
                .eq(SysApprovalNode::getNodeOrder, instance.getCurrentNodeOrder())
        );
        SysApprovalNode currentNode = nodes.isEmpty() ? null : nodes.get(0);

        // 记录审批
        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeId(currentNode != null ? currentNode.getId() : null);
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName(currentNode != null ? currentNode.getNodeName() : "未知节点");
        record.setApproverId(approverId);
        record.setApproverName(approverName);
        record.setAction("APPROVE");
        record.setComment(comment);
        record.setActionTime(java.time.LocalDateTime.now().toString());
        save(record);

        // 判断是否最后一个节点
        if (instance.getCurrentNodeOrder() >= instance.getTotalNodes()) {
            instance.setStatus(1);
            instance.setCompletedTime(java.time.LocalDateTime.now().toString());
        } else {
            instance.setCurrentNodeOrder(instance.getCurrentNodeOrder() + 1);
        }
        approvalInstanceService.updateById(instance);
        return instance;
    }

    @Override
    public void reject(Long instanceId, Long approverId, String approverName, String comment) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) throw new RuntimeException("审批实例不存在");

        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName("驳回节点");
        record.setApproverId(approverId);
        record.setApproverName(approverName);
        record.setAction("REJECT");
        record.setComment(comment);
        record.setActionTime(java.time.LocalDateTime.now().toString());
        save(record);

        instance.setStatus(2);
        instance.setCompletedTime(java.time.LocalDateTime.now().toString());
        approvalInstanceService.updateById(instance);
    }

    @Override
    public void delegate(Long instanceId, Long approverId, String approverName, Long delegateToId, String comment) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) throw new RuntimeException("审批实例不存在");

        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName("转交");
        record.setApproverId(approverId);
        record.setApproverName(approverName);
        record.setAction("DELEGATE");
        record.setComment(comment);
        record.setDelegateToId(delegateToId);
        record.setActionTime(java.time.LocalDateTime.now().toString());
        save(record);
    }

    @Override
    public void withdraw(Long instanceId) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) throw new RuntimeException("审批实例不存在");
        if (instance.getStatus() != 0) throw new RuntimeException("仅审批中状态可撤回");
        instance.setStatus(3);
        instance.setCompletedTime(java.time.LocalDateTime.now().toString());
        approvalInstanceService.updateById(instance);
    }

}
