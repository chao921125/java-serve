package com.cc.core.service.pur;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.pur.PurExpenseAllocation;

/**
 * PurExpenseAllocation 服务接口
 */
public interface PurExpenseAllocationService extends IService<PurExpenseAllocation> {


    /**
     * 执行分摊——按指定方式将费用分配到入库单各明细
     */
    void allocate(Long id);

    /**
     * 冲销分摊
     */
    void reverse(Long id);

}
