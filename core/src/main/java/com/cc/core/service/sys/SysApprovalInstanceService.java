// AI生成
package com.cc.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sys.SysApprovalInstance;

/**
 * SysApprovalInstance 服务接口
 */
public interface SysApprovalInstanceService extends IService<SysApprovalInstance> {


    /**
     * 提交审批——启动审批流
     */
    void submit(String businessType, Long businessId, String businessNo, Long applicantId, String applicantName);

    /**
     * 提交审批（带表单数据）
     */
    void submit(String businessType, Long businessId, String businessNo, Long applicantId, String applicantName, String formData);

    /**
     * 我的申请
     */
    java.util.List<com.cc.core.entity.sys.SysApprovalInstance> getMyApply(Long userId);

    /**
     * 我的待审
     */
    java.util.List<com.cc.core.entity.sys.SysApprovalInstance> getMyApproval(Long userId);

    /**
     * 我的已审
     */
    java.util.List<com.cc.core.entity.sys.SysApprovalInstance> getMyDone(Long userId);

    /**
     * 获取审批实例的完整流程轨迹（含节点信息和审批记录）
     */
    java.util.Map<String, Object> getTrace(Long instanceId);

}
