package com.cc.core.service.fin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.fin.FinInvoice;

/**
 * FinInvoice 服务接口
 */
public interface FinInvoiceService extends IService<FinInvoice> {


    /**
     * 认证进项发票
     */
    void verify(Long id);

    /**
     * 红冲销项发票
     */
    void redRush(Long id);

    /**
     * 作废发票
     */
    void cancel(Long id);

    /**
     * 获取待开票单据列表
     */
    java.util.List<com.cc.core.entity.fin.FinInvoice> getUnbilled();

}
