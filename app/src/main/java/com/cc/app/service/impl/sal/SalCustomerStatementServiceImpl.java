package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalCustomerStatement;
import com.cc.core.mapper.sal.SalCustomerStatementMapper;
import com.cc.core.service.sal.SalCustomerStatementService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * SalCustomerStatement 服务实现
 */
@Service
public class SalCustomerStatementServiceImpl extends ServiceImpl<SalCustomerStatementMapper, SalCustomerStatement> implements SalCustomerStatementService {

    // ==== Business Logic Methods ====

    @Override
    public void generate(Long customerId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        com.cc.core.entity.sal.SalCustomerStatement stmt = new com.cc.core.entity.sal.SalCustomerStatement();
        stmt.setStatementNo("CST-" + System.currentTimeMillis());
        stmt.setCustomerId(customerId);
        stmt.setStartDate(startDate);
        stmt.setEndDate(endDate);
        stmt.setStatus(0);
        stmt.setOpeningReceivable(java.math.BigDecimal.ZERO);
        stmt.setSalesAmount(java.math.BigDecimal.ZERO);
        stmt.setReturnAmount(java.math.BigDecimal.ZERO);
        stmt.setReceiptAmount(java.math.BigDecimal.ZERO);
        stmt.setClosingReceivable(java.math.BigDecimal.ZERO);
        save(stmt);
    }

    @Override
    public void confirm(Long id) {
        com.cc.core.entity.sal.SalCustomerStatement stmt = getById(id);
        stmt.setStatus(2);
        stmt.setConfirmedBy(getCurrentUsernameForSal());
        stmt.setConfirmedTime(java.time.LocalDateTime.now().toString());
        updateById(stmt);
    }

    @Override
    public void dispute(Long id, String reason) {
        com.cc.core.entity.sal.SalCustomerStatement stmt = getById(id);
        stmt.setStatus(3);
        stmt.setRemark(reason);
        updateById(stmt);
    }

    private String getCurrentUsernameForSal() {
        try {
            return org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        } catch (Exception e) {
            return "system";
        }
    }

}
