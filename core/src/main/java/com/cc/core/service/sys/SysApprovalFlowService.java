package com.cc.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sys.SysApprovalFlow;

/**
 * SysApprovalFlow 服务接口
 */
public interface SysApprovalFlowService extends IService<SysApprovalFlow> {

    /**
     * 获取审批流下的节点列表
     */
    java.util.List<com.cc.core.entity.sys.SysApprovalNode> getNodesByFlowId(Long flowId);

    /**
     * 添加审批节点
     */
    void addNode(com.cc.core.entity.sys.SysApprovalNode node);
}
