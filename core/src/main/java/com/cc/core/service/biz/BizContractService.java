package com.cc.core.service.biz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.biz.BizContract;

/**
 * BizContract 服务接口
 */
public interface BizContractService extends IService<BizContract> {


    /**
     * 记录变更
     */
    void recordChange(Long contractId, String changeType, String beforeValue, String afterValue,
        String reason, String changedBy);

    /**
     * 执行合同查询
     */
    java.util.List<java.util.Map<String, Object>> executeQuery(Long id);

    /**
     * 按合同编号查询
     */
    com.cc.core.entity.biz.BizContract getByCode(String code);

}
