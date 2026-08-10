package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalSalesOrderItem;
import com.cc.core.mapper.sal.SalSalesOrderItemMapper;
import com.cc.core.service.sal.SalSalesOrderItemService;
import org.springframework.stereotype.Service;

/**
 * 销售订单明细服务实现
 */
@Service
public class SalSalesOrderItemServiceImpl extends ServiceImpl<SalSalesOrderItemMapper, SalSalesOrderItem>
        implements SalSalesOrderItemService {
}
