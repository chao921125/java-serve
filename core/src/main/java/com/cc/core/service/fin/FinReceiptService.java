package com.cc.core.service.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.fin.ReceiptQueryDTO;
import com.cc.core.entity.fin.FinReceipt;
import com.cc.core.entity.fin.FinReceiptItem;

import java.util.List;

/**
 * 收款单服务接口
 */
public interface FinReceiptService extends IService<FinReceipt> {

    /**
     * 分页查询收款单
     */
    IPage<FinReceipt> page(ReceiptQueryDTO query);

    /**
     * 审核收款单 — 核销应收账款
     */
    void approve(Long id);

    /**
     * 获取收款单明细
     */
    List<FinReceiptItem> getItems(Long receiptId);
}
