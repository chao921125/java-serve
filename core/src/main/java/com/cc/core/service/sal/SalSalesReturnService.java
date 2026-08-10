package com.cc.core.service.sal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.sal.SalesReturnQueryDTO;
import com.cc.core.entity.sal.SalSalesReturn;
import com.cc.core.entity.sal.SalSalesReturnItem;

import java.util.List;

/**
 * 销售退货单服务接口
 */
public interface SalSalesReturnService extends IService<SalSalesReturn> {

    /**
     * 分页查询销售退货单
     */
    IPage<SalSalesReturn> page(SalesReturnQueryDTO query);

    /**
     * 获取退货单明细列表
     */
    List<SalSalesReturnItem> getItems(Long returnId);

    /**
     * 审核退货单：库存回库、写库存流水、冲减应收账款
     */
    void approve(Long id);
}
