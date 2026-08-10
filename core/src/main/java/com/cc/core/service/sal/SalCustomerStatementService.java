package com.cc.core.service.sal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sal.SalCustomerStatement;

/**
 * SalCustomerStatement 服务接口
 */
public interface SalCustomerStatementService extends IService<SalCustomerStatement> {


    /**
     * 生成对账单
     */
    void generate(Long customerId, java.time.LocalDate startDate, java.time.LocalDate endDate);

    /**
     * 确认对账
     */
    void confirm(Long id);

    /**
     * 标记争议
     */
    void dispute(Long id, String reason);

}
