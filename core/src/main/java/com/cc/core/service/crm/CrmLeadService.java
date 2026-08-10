package com.cc.core.service.crm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.crm.CrmLead;

/**
 * CrmLead 服务接口
 */
public interface CrmLeadService extends IService<CrmLead> {


    /**
     * 转换线索为客户
     */
    Long convertToCustomer(Long id);

}
