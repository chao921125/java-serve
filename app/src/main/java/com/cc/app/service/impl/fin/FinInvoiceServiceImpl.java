package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinInvoice;
import com.cc.core.mapper.fin.FinInvoiceMapper;
import com.cc.core.service.fin.FinInvoiceService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

/**
 * FinInvoice 服务实现
 */
@Service
public class FinInvoiceServiceImpl extends ServiceImpl<FinInvoiceMapper, FinInvoice> implements FinInvoiceService {

    // ==== Business Logic Methods ====

    @Override
    public void verify(Long id) {
        FinInvoice invoice = getById(id);
        if (!"PURCHASE_IN".equals(invoice.getInvoiceType())) throw new RuntimeException("仅进项发票可认证");
        invoice.setStatus(2);
        invoice.setVerificationStatus("VERIFIED");
        invoice.setVerificatedDate(java.time.LocalDate.now());
        updateById(invoice);
    }

    @Override
    public void redRush(Long id) {
        FinInvoice invoice = getById(id);
        if (!"SALES_OUT".equals(invoice.getInvoiceType())) throw new RuntimeException("仅销项发票可红冲");
        invoice.setStatus(2);
        updateById(invoice);
    }

    @Override
    public void cancel(Long id) {
        FinInvoice invoice = getById(id);
        invoice.setStatus(3);
        updateById(invoice);
    }

    @Override
    public java.util.List<FinInvoice> getUnbilled() {
        return lambdaQuery().eq(FinInvoice::getStatus, 0).list();
    }

}
