// AI生成
package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysApprovalCc;
import com.cc.core.entity.sys.SysApprovalFlow;
import com.cc.core.entity.sys.SysApprovalInstance;
import com.cc.core.entity.sys.SysApprovalNode;
import com.cc.core.mapper.sys.SysApprovalInstanceMapper;
import com.cc.core.service.sys.SysApprovalCcService;
import com.cc.core.service.sys.SysApprovalFlowService;
import com.cc.core.service.sys.SysApprovalInstanceService;
import com.cc.core.service.sys.SysApprovalNodeService;
import com.cc.core.service.sys.SysApprovalRecordService;
import com.cc.framework.exception.ServiceException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 审批实例服务实现 — 支持会签/或签/条件分支/抄送
 */
@Service
public class SysApprovalInstanceServiceImpl extends ServiceImpl<SysApprovalInstanceMapper, SysApprovalInstance> implements SysApprovalInstanceService {

    private final SysApprovalFlowService approvalFlowService;
    private final SysApprovalNodeService approvalNodeService;
    private final SysApprovalCcService approvalCcService;
    private final ApprovalApproverResolver approverResolver;
    private final ApprovalConditionEvaluator conditionEvaluator;
    private final SysApprovalRecordService approvalRecordService;

    public SysApprovalInstanceServiceImpl(
            SysApprovalFlowService approvalFlowService,
            SysApprovalNodeService approvalNodeService,
            SysApprovalCcService approvalCcService,
            ApprovalApproverResolver approverResolver,
            ApprovalConditionEvaluator conditionEvaluator,
            @Lazy SysApprovalRecordService approvalRecordService) {
        this.approvalFlowService = approvalFlowService;
        this.approvalNodeService = approvalNodeService;
        this.approvalCcService = approvalCcService;
        this.approverResolver = approverResolver;
        this.conditionEvaluator = conditionEvaluator;
        this.approvalRecordService = approvalRecordService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(String businessType, Long businessId, String businessNo, Long applicantId, String applicantName) {
        submit(businessType, businessId, businessNo, applicantId, applicantName, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(String businessType, Long businessId, String businessNo, Long applicantId, String applicantName, String formData) {
        long existing = lambdaQuery()
                .eq(SysApprovalInstance::getBusinessType, businessType)
                .eq(SysApprovalInstance::getBusinessId, businessId)
                .eq(SysApprovalInstance::getStatus, 0)
                .count();
        if (existing > 0) {
            throw ServiceException.badRequest("该单据已在审批中，请勿重复提交");
        }

        List<SysApprovalFlow> flows = approvalFlowService.list(
                new LambdaQueryWrapper<SysApprovalFlow>()
                        .eq(SysApprovalFlow::getTargetType, businessType)
                        .eq(SysApprovalFlow::getIsEnabled, 1)
        );
        if (flows.isEmpty()) {
            throw ServiceException.badRequest("未找到启用的审批流配置: " + businessType);
        }

        SysApprovalFlow flow = flows.get(0);
        List<SysApprovalNode> nodes = approvalNodeService.list(
                new LambdaQueryWrapper<SysApprovalNode>()
                        .eq(SysApprovalNode::getFlowId, flow.getId())
                        .orderByAsc(SysApprovalNode::getNodeOrder)
        );
        if (nodes.isEmpty()) {
            throw ServiceException.badRequest("审批流未配置节点");
        }

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
        instance.setAppliedTime(LocalDateTime.now());
        instance.setFormData(formData);
        save(instance);

        processAutoNodes(instance, nodes);
    }

    @Override
    public List<SysApprovalInstance> getMyApply(Long userId) {
        return lambdaQuery().eq(SysApprovalInstance::getApplicantId, userId)
                .orderByDesc(SysApprovalInstance::getAppliedTime)
                .list();
    }

    @Override
    public List<SysApprovalInstance> getMyApproval(Long userId) {
        List<SysApprovalInstance> allPending = lambdaQuery()
                .eq(SysApprovalInstance::getStatus, 0)
                .orderByDesc(SysApprovalInstance::getAppliedTime)
                .list();
        if (allPending.isEmpty()) {
            return allPending;
        }

        List<Long> flowIds = allPending.stream()
                .map(SysApprovalInstance::getFlowId)
                .distinct()
                .toList();
        List<SysApprovalNode> allNodes = approvalNodeService.list(
                new LambdaQueryWrapper<SysApprovalNode>()
                        .in(SysApprovalNode::getFlowId, flowIds)
        );

        List<SysApprovalInstance> result = new ArrayList<>();
        for (SysApprovalInstance instance : allPending) {
            SysApprovalNode currentNode = allNodes.stream()
                    .filter(n -> n.getFlowId().equals(instance.getFlowId())
                            && n.getNodeOrder().equals(instance.getCurrentNodeOrder()))
                    .findFirst()
                    .orElse(null);
            if (currentNode == null) continue;
            String nodeType = currentNode.getNodeType() == null ? "SEQUENTIAL" : currentNode.getNodeType();
            if ("CC".equals(nodeType) || "CONDITIONAL".equals(nodeType)) continue;
            if (approverResolver.isApprover(currentNode, userId, instance.getApplicantId())) {
                result.add(instance);
            }
        }
        return result;
    }

    @Override
    public List<SysApprovalInstance> getMyDone(Long userId) {
        return baseMapper.selectMyDone(userId);
    }

    @Override
    public Map<String, Object> getTrace(Long instanceId) {
        SysApprovalInstance instance = getById(instanceId);
        if (instance == null) {
            throw ServiceException.notFound("审批实例不存在");
        }
        Map<String, Object> trace = new LinkedHashMap<>();
        trace.put("instance", instance);
        trace.put("flow", approvalFlowService.getById(instance.getFlowId()));
        trace.put("nodes", approvalNodeService.list(
                new LambdaQueryWrapper<SysApprovalNode>()
                        .eq(SysApprovalNode::getFlowId, instance.getFlowId())
                        .orderByAsc(SysApprovalNode::getNodeOrder)
        ));
        trace.put("records", approvalRecordService.getByInstanceId(instanceId));
        trace.put("ccRecords", approvalCcService.getByInstanceId(instanceId));
        return trace;
    }

    /**
     * 自动处理抄送节点和条件分支节点
     */
    void processAutoNodes(SysApprovalInstance instance, List<SysApprovalNode> nodes) {
        boolean updated = false;
        while (true) {
            SysApprovalNode currentNode = nodes.stream()
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
                int nextOrder = result
                        ? (currentNode.getTrueNextOrder() != null ? currentNode.getTrueNextOrder() : instance.getCurrentNodeOrder() + 1)
                        : (currentNode.getFalseNextOrder() != null ? currentNode.getFalseNextOrder() : instance.getCurrentNodeOrder() + 1);
                instance.setCurrentNodeOrder(nextOrder);
                updated = true;
                continue;
            }
            break;
        }
        if (updated) {
            updateById(instance);
        }
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
}