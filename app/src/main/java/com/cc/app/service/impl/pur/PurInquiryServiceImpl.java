package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurInquiry;
import com.cc.core.mapper.pur.PurInquiryMapper;
import com.cc.core.service.pur.PurInquiryService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.cc.core.service.pur.PurQuoteDetailService;
import com.cc.core.service.pur.PurPurchaseRequisitionService;
import com.cc.core.entity.pur.PurQuoteDetail;
import com.cc.core.entity.pur.PurPurchaseRequisition;

/**
 * PurInquiry 服务实现
 */
@Service
@RequiredArgsConstructor
public class PurInquiryServiceImpl extends ServiceImpl<PurInquiryMapper, PurInquiry> implements PurInquiryService {
    private final PurQuoteDetailService quoteDetailService;
    private final PurPurchaseRequisitionService requisitionService;


    // ==== Business Logic Methods ====

    @Override
    public void issue(Long id) {
        PurInquiry entity = getById(id);
        if (entity == null) throw new RuntimeException("询价单不存在");
        if (entity.getStatus() != 0) throw new RuntimeException("仅草稿状态可发出");
        entity.setStatus(1);
        updateById(entity);
    }

    @Override
    public void compareAndSelect(Long id, Long selectedSupplierId) {
        PurInquiry entity = getById(id);
        if (entity == null) throw new RuntimeException("询价单不存在");
        entity.setStatus(3);
        updateById(entity);
        // 标记选中的供应商报价
        java.util.List<PurQuoteDetail> quotes = quoteDetailService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PurQuoteDetail>()
                .eq(PurQuoteDetail::getInquiryId, id)
        );
        for (PurQuoteDetail q : quotes) {
            if (q.getInquirySupplierId().equals(selectedSupplierId)) {
                q.setIsSelected(1);
            } else {
                q.setIsSelected(0);
            }
            quoteDetailService.updateById(q);
        }
    }

    @Override
    public Long convertToPurchaseOrder(Long id) {
        PurInquiry entity = getById(id);
        if (entity == null) throw new RuntimeException("询价单不存在");
        entity.setStatus(4);
        updateById(entity);
        // 自动生成请购单（关联 requisition_id）
        if (entity.getRequisitionId() != null) {
            PurPurchaseRequisition req = requisitionService.getById(entity.getRequisitionId());
            if (req != null) {
                req.setStatus(3);
                requisitionService.updateById(req);
            }
        }
        return entity.getRequisitionId();
    }

    @Override
    public void close(Long id) {
        PurInquiry entity = getById(id);
        if (entity == null) throw new RuntimeException("询价单不存在");
        entity.setStatus(2);
        updateById(entity);
    }

    @Override
    public void enterQuote(Long inquiryId, Long supplierId, Long inquiryItemId, java.math.BigDecimal unitPrice) {
        PurQuoteDetail quote = new PurQuoteDetail();
        quote.setInquiryId(inquiryId);
        quote.setInquiryItemId(inquiryItemId);
        quote.setSupplierId(supplierId);
        quote.setUnitPrice(unitPrice);
        quoteDetailService.save(quote);
    }

    @Override
    public void decide(Long inquiryId, Long selectedSupplierId) {
        PurInquiry entity = getById(inquiryId);
        if (entity == null) throw new RuntimeException("询价单不存在");
        entity.setStatus(3);
        updateById(entity);
        // 标记选中供应商
        java.util.List<PurQuoteDetail> quotes = quoteDetailService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PurQuoteDetail>()
                .eq(PurQuoteDetail::getInquiryId, inquiryId)
        );
        for (PurQuoteDetail q : quotes) {
            if (q.getSupplierId().equals(selectedSupplierId)) {
                q.setIsSelected(1);
            } else {
                q.setIsSelected(0);
            }
            quoteDetailService.updateById(q);
        }
    }

}
