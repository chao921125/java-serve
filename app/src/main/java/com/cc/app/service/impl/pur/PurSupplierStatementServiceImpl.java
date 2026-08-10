package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurSupplierStatement;
import com.cc.core.mapper.pur.PurSupplierStatementMapper;
import com.cc.core.service.pur.PurSupplierStatementService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * PurSupplierStatement 服务实现
 */
@Service
public class PurSupplierStatementServiceImpl extends ServiceImpl<PurSupplierStatementMapper, PurSupplierStatement> implements PurSupplierStatementService {

    // ==== Business Logic Methods ====

    @Override
    public void generate(Long supplierId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        PurSupplierStatement stmt = new PurSupplierStatement();
        stmt.setStatementNo("SST-" + System.currentTimeMillis());
        stmt.setSupplierId(supplierId);
        stmt.setStartDate(startDate);
        stmt.setEndDate(endDate);
        stmt.setStatus(0);
        // 实际应查询采购/退货/付款汇总，此处仅设默认值
        stmt.setOpeningPayable(java.math.BigDecimal.ZERO);
        stmt.setPurchaseAmount(java.math.BigDecimal.ZERO);
        stmt.setReturnAmount(java.math.BigDecimal.ZERO);
        stmt.setPaymentAmount(java.math.BigDecimal.ZERO);
        stmt.setClosingPayable(java.math.BigDecimal.ZERO);
        save(stmt);
    }

    @Override
    public void confirm(Long id) {
        PurSupplierStatement stmt = getById(id);
        if (stmt == null) throw new RuntimeException("对账单不存在");
        stmt.setStatus(2);
        stmt.setConfirmedBy(getCurrentUsername());
        stmt.setConfirmedTime(java.time.LocalDateTime.now().toString());
        updateById(stmt);
    }

    @Override
    public void dispute(Long id, String reason) {
        PurSupplierStatement stmt = getById(id);
        if (stmt == null) throw new RuntimeException("对账单不存在");
        stmt.setStatus(3);
        stmt.setRemark(reason);
        updateById(stmt);
    }

    private String getCurrentUsername() {
        try {
            return org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        } catch (Exception e) {
            return "system";
        }
    }

}
