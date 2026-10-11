// AI生成
package com.cc.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sys.SysApprovalCc;

import java.util.List;

/**
 * SysApprovalCc 服务接口
 */
public interface SysApprovalCcService extends IService<SysApprovalCc> {

    /**
     * 查询实例的抄送记录
     */
    List<SysApprovalCc> getByInstanceId(Long instanceId);
}