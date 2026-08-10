package com.cc.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sys.SysApprovalInstance;
import com.cc.core.entity.sys.SysApprovalRecord;

/**
 * SysApprovalRecord 服务接口
 */
public interface SysApprovalRecordService extends IService<SysApprovalRecord> {


    /**
     * 审批通过
     */
    SysApprovalInstance approve(Long instanceId, Long approverId, String approverName, String comment);

    /**
     * 审批驳回
     */
    void reject(Long instanceId, Long approverId, String approverName, String comment);

    /**
     * 转交审批
     */
    void delegate(Long instanceId, Long approverId, String approverName, Long delegateToId, String comment);

    /**
     * 撤回
     */
    void withdraw(Long instanceId);

}
