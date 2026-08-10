package com.cc.core.service.pur;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.pur.PurSupplierStatement;

/**
 * PurSupplierStatement 服务接口
 */
public interface PurSupplierStatementService extends IService<PurSupplierStatement> {


    /**
     * 生成对账单
     */
    void generate(Long supplierId, java.time.LocalDate startDate, java.time.LocalDate endDate);

    /**
     * 确认对账
     */
    void confirm(Long id);

    /**
     * 标记争议
     */
    void dispute(Long id, String reason);

}
