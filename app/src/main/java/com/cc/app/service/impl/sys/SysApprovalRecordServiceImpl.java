// AI生成
package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysApprovalCc;
import com.cc.core.entity.sys.SysApprovalInstance;
import com.cc.core.entity.sys.SysApprovalNode;
import com.cc.core.entity.sys.SysApprovalRecord;
import com.cc.core.mapper.sys.SysApprovalRecordMapper;
import com.cc.core.service.sys.SysApprovalCcService;
import com.cc.core.service.sys.SysApprovalInstanceService;
import com.cc.core.service.sys.SysApprovalNodeService;
import com.cc.core.service.sys.SysApprovalRecordService;
import com.cc.framework.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 审批记录服务实现 — 支持会签/或签/退回
 */
@Service
@RequiredArgsConstructor
public class SysApprovalRecordServiceImpl extends ServiceImpl<SysApprovalRecordMapper, SysApprovalRecord> implements SysApprovalRecordService {

    private final SysApprovalInstanceService approvalInstanceService;
    private final SysApprovalNodeService approvalNodeService;
    private final ApprovalApproverResolver approverResolver;
    private final ApprovalConditionEvaluator conditionEvaluator;
    private final SysApprovalCcService approvalCcService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysApprovalInstance approve(Long instanceId, Long approverId, String approverName, String comment) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) {
            throw ServiceException.notFound("审批实例不存在");
        }
        if (instance.getStatus() != 0) {
            throw ServiceException.badRequest("审批已结束，无法操作");
        }

        SysApprovalNode currentNode = getCurrentNode(instance);
        if (currentNode == null) {
            throw ServiceException.badRequest("当前节点不存在");
        }

        if (!approverResolver.isApprover(currentNode, approverId, instance.getApplicantId())) {
            throw ServiceException.badRequest("您不是当前节点的审批人");
        }

        long alreadyApproved = lambdaQuery()
                .eq(SysApprovalRecord::getInstanceId, instanceId)
                .eq(SysApprovalRecord::getNodeOrder, instance.getCurrentNodeOrder())
                .eq(SysApprovalRecord::getApproverId, approverId)
                .eq(SysApprovalRecord::getAction, "APPROVE")
                .count();
        if (alreadyApproved > 0) {
            throw ServiceException.badRequest("您已审批过该节点");
        }

        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeId(currentNode.getId());
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName(currentNode.getNodeName());
        record.setApproverId(approverId);
        record.setApproverName(approverName);
        record.setAction("APPROVE");
        record.setComment(comment);
        record.setActionTime(LocalDateTime.now());
        save(record);

        String nodeType = currentNode.getNodeType() == null ? "SEQUENTIAL" : currentNode.getNodeType();
        boolean shouldAdvance = switch (nodeType) {
            case "SEQUENTIAL" -> true;
            case "PARALLEL_OR" -> true;
            case "PARALLEL_AND" -> checkParallelAndComplete(instance, currentNode);
            default -> true;
        };

        if (shouldAdvance) {
            advanceToNext(instance);
        } else {
            approvalInstanceService.updateById(instance);
        }
        return instance;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(Long instanceId, Long approverId, String approverName, String comment) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) {
            throw ServiceException.notFound("审批实例不存在");
        }
        if (instance.getStatus() != 0) {
            throw ServiceException.badRequest("审批已结束，无法操作");
        }

        SysApprovalNode currentNode = getCurrentNode(instance);
        if (currentNode != null && currentNode.getCanReject() != null && currentNode.getCanReject() == 0) {
            throw ServiceException.badRequest("当前节点不允许驳回");
        }

        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeId(currentNode != null ? currentNode.getId() : null);
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName(currentNode != null ? currentNode.getNodeName() : "未知节点");
        record.setApproverId(approverId);
        record.setApproverName(approverName);
        record.setAction("REJECT");
        record.setComment(comment);
        record.setActionTime(LocalDateTime.now());
        save(record);

        instance.setStatus(2);
        instance.setCompletedTime(LocalDateTime.now());
        approvalInstanceService.updateById(instance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegate(Long instanceId, Long approverId, String approverName, Long delegateToId, String comment) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) {
            throw ServiceException.notFound("审批实例不存在");
        }
        if (instance.getStatus() != 0) {
            throw ServiceException.badRequest("审批已结束，无法操作");
        }
        if (delegateToId == null) {
            throw ServiceException.badRequest("转交人不能为空");
        }

        SysApprovalNode currentNode = getCurrentNode(instance);
        if (currentNode != null && currentNode.getCanDelegate() != null && currentNode.getCanDelegate() == 0) {
            throw ServiceException.badRequest("当前节点不允许转交");
        }

        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeId(currentNode != null ? currentNode.getId() : null);
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName(currentNode != null ? currentNode.getNodeName() : "未知节点");
        record.setApproverId(approverId);
        record.setApproverName(approverName);
        record.setAction("DELEGATE");
        record.setComment(comment);
        record.setDelegateToId(delegateToId);
        record.setActionTime(LocalDateTime.now());
        save(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(Long instanceId, Long applicantId) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) {
            throw ServiceException.notFound("审批实例不存在");
        }
        if (instance.getStatus() != 0) {
            throw ServiceException.badRequest("仅审批中状态可撤回");
        }
        if (!instance.getApplicantId().equals(applicantId)) {
            throw ServiceException.badRequest("仅申请人可撤回审批");
        }

        SysApprovalNode currentNode = getCurrentNode(instance);
        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeId(currentNode != null ? currentNode.getId() : null);
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName(currentNode != null ? currentNode.getNodeName() : "未知节点");
        record.setApproverId(applicantId);
        record.setApproverName(instance.getApplicantName());
        record.setAction("WITHDRAW");
        record.setActionTime(LocalDateTime.now());
        save(record);

        instance.setStatus(3);
        instance.setCompletedTime(LocalDateTime.now());
        approvalInstanceService.updateById(instance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnTo(Long instanceId, Long approverId, String approverName, Integer targetNodeOrder, String comment) {
        SysApprovalInstance instance = approvalInstanceService.getById(instanceId);
        if (instance == null) {
            throw ServiceException.notFound("审批实例不存在");
        }
        if (instance.getStatus() != 0) {
            throw ServiceException.badRequest("审批已结束，无法操作");
        }
        if (targetNodeOrder == null || targetNodeOrder < 1 || targetNodeOrder >= instance.getCurrentNodeOrder()) {
            throw ServiceException.badRequest("退回节点顺序无效");
        }

        SysApprovalNode currentNode = getCurrentNode(instance);
        SysApprovalRecord record = new SysApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeId(currentNode != null ? currentNode.getId() : null);
        record.setNodeOrder(instance.getCurrentNodeOrder());
        record.setNodeName(currentNode != null ? currentNode.getNodeName() : "未知节点");
        record.setApproverId(approverId);
        record.setApproverName(approverName);
        record.setAction("RETURN");
        record.setComment(comment);
        record.setActionTime(LocalDateTime.now());
        save(record);

        instance.setCurrentNodeOrder(targetNodeOrder);
        approvalInstanceService.updateById(instance);
    }

    @Override
    public List<SysApprovalRecord> getByInstanceId(Long instanceId) {
        return lambdaQuery()
                .eq(SysApprovalRecord::getInstanceId, instanceId)
                .orderByAsc(SysApprovalRecord::getActionTime)
                .list();
    }

    private boolean checkParallelAndComplete(SysApprovalInstance instance, SysApprovalNode node) {
        int requiredCount = node.getRequiredCount() != null ? node.getRequiredCount() : 1;
        long approvedCount = lambdaQuery()
                .eq(SysApprovalRecord::getInstanceId, instance.getId())
                .eq(SysApprovalRecord::getNodeOrder, instance.getCurrentNodeOrder())
                .eq(SysApprovalRecord::getAction, "APPROVE")
                .count();
        return approvedCount >= requiredCount;
    }

    private void advanceToNext(SysApprovalInstance instance) {
        int nextOrder = instance.getCurrentNodeOrder() + 1;
        instance.setCurrentNodeOrder(nextOrder);

        List<SysApprovalNode> allNodes = approvalNodeService.list(
                new LambdaQueryWrapper<SysApprovalNode>()
                        .eq(SysApprovalNode::getFlowId, instance.getFlowId())
                        .orderByAsc(SysApprovalNode::getNodeOrder)
        );

        boolean updated = false;
        while (true) {
            SysApprovalNode currentNode = allNodes.stream()
                    .filter(n -> n.getNodeOrder().equals(instance.getCurrentNodeOrder()))
                    .findFirst()
                    .orElse(null);
            if (currentNode == null) {
                instance.setStatus(1);
                instance.setCompletedTime(LocalDateTime.now());
                updated = true;
                break;
            }
            String nodeType = currentNode.getNodeType() == null ? "SEQUENTIAL" : currentNode.getNodeType();
            if ("CC".equals(nodeType)) {
                createCcRecords(instance, currentNode);
                instance.setCurrentNodeOrder(instance.getCurrentNodeOrder() + 1);
                updated = true;
                continue;
            }
            if ("CONDITIONAL".equals(nodeType)) {
                boolean result = conditionEvaluator.evaluate(
                        currentNode.getConditionExpression(), instance.getFormData());
                int target = result
                        ? (currentNode.getTrueNextOrder() != null ? currentNode.getTrueNextOrder() : instance.getCurrentNodeOrder() + 1)
                        : (currentNode.getFalseNextOrder() != null ? currentNode.getFalseNextOrder() : instance.getCurrentNodeOrder() + 1);
                instance.setCurrentNodeOrder(target);
                updated = true;
                continue;
            }
            break;
        }
        approvalInstanceService.updateById(instance);
    }

    private void createCcRecords(SysApprovalInstance instance, SysApprovalNode node) {
        List<Long> ccUserIds = approverResolver.resolveApprovers(node, instance.getApplicantId());
        for (Long userId : ccUserIds) {
            SysApprovalCc cc = new SysApprovalCc();
            cc.setInstanceId(instance.getId());
            cc.setNodeId(node.getId());
            cc.setCcUserId(userId);
            cc.setCcTime(LocalDateTime.now());
            cc.setIsRead(0);
            approvalCcService.save(cc);
        }
    }

    private SysApprovalNode getCurrentNode(SysApprovalInstance instance) {
        List<SysApprovalNode> nodes = approvalNodeService.list(
                new LambdaQueryWrapper<SysApprovalNode>()
                        .eq(SysApprovalNode::getFlowId, instance.getFlowId())
                        .eq(SysApprovalNode::getNodeOrder, instance.getCurrentNodeOrder())
        );
        return nodes.isEmpty() ? null : nodes.get(0);
    }
}