package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysApprovalFlow;
import com.cc.core.entity.sys.SysApprovalNode;
import com.cc.core.mapper.sys.SysApprovalFlowMapper;
import com.cc.core.service.sys.SysApprovalFlowService;
import com.cc.core.service.sys.SysApprovalNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * SysApprovalFlow 服务实现
 */
@Service
@RequiredArgsConstructor
public class SysApprovalFlowServiceImpl extends ServiceImpl<SysApprovalFlowMapper, SysApprovalFlow> implements SysApprovalFlowService {

    private final SysApprovalNodeService approvalNodeService;

    @Override
    public java.util.List<SysApprovalNode> getNodesByFlowId(Long flowId) {
        return approvalNodeService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysApprovalNode>()
                .eq(SysApprovalNode::getFlowId, flowId)
                .orderByAsc(SysApprovalNode::getNodeOrder)
        );
    }

    @Override
    public void addNode(SysApprovalNode node) {
        approvalNodeService.save(node);
    }
}
