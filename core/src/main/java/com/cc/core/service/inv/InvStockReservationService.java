package com.cc.core.service.inv;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.inv.InvStockReservation;

/**
 * InvStockReservation 服务接口
 */
public interface InvStockReservationService extends IService<InvStockReservation> {


    /**
     * 释放预留库存
     */
    void release(Long id);

    /**
     * 取消预留
     */
    void cancel(Long id);

}
