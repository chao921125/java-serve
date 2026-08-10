package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalSalesDeliveryItem;
import com.cc.core.mapper.sal.SalSalesDeliveryItemMapper;
import com.cc.core.service.sal.SalSalesDeliveryItemService;
import org.springframework.stereotype.Service;

/**
 * 销售出库单明细服务实现
 */
@Service
public class SalSalesDeliveryItemServiceImpl extends ServiceImpl<SalSalesDeliveryItemMapper, SalSalesDeliveryItem>
        implements SalSalesDeliveryItemService {
}
