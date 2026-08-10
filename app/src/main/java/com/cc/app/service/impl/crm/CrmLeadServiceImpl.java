package com.cc.app.service.impl.crm;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.crm.CrmLead;
import com.cc.core.mapper.crm.CrmLeadMapper;
import com.cc.core.service.crm.CrmLeadService;
import org.springframework.stereotype.Service;

/**
 * CrmLead 服务实现
 */
@Service
public class CrmLeadServiceImpl extends ServiceImpl<CrmLeadMapper, CrmLead> implements CrmLeadService {

    // ==== Business Logic Methods ====

    @Override
    public Long convertToCustomer(Long id) {
        CrmLead lead = getById(id);
        if (lead == null) throw new RuntimeException("线索不存在");
        // 简化：创建客户记录并关联
        // 实际应调用 CustomerService 创建客户
        lead.setStatus(3);
        lead.setRemark("线索已手动转换，请在客户管理中完善信息");
        updateById(lead);
        return null; // 返回 null，由前端引导手动创建客户
    }

}
