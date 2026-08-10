package com.cc.core.service.sal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.sal.SalesReceiptQueryDTO;
import com.cc.core.entity.sal.SalSalesReceipt;

/**
 * 销售收款单服务接口
 */
public interface SalSalesReceiptService extends IService<SalSalesReceipt> {

    /**
     * 分页查询销售收款单
     */
    IPage<SalSalesReceipt> page(SalesReceiptQueryDTO query);

    /**
     * 审核收款单：更新应收账款(增加 received_amount)、更新账户余额、写账户流水
     */
    void approve(Long id);
}
