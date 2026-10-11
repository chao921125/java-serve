// AI生成
package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysApprovalCc;
import com.cc.core.mapper.sys.SysApprovalCcMapper;
import com.cc.core.service.sys.SysApprovalCcService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysApprovalCc 服务实现
 */
@Service
public class SysApprovalCcServiceImpl extends ServiceImpl<SysApprovalCcMapper, SysApprovalCc> implements SysApprovalCcService {

    @Override
    public List<SysApprovalCc> getByInstanceId(Long instanceId) {
        return lambdaQuery()
                .eq(SysApprovalCc::getInstanceId, instanceId)
                .orderByAsc(SysApprovalCc::getCcTime)
                .list();
    }
}