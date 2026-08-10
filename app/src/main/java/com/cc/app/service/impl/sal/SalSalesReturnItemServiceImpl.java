package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalSalesReturnItem;
import com.cc.core.mapper.sal.SalSalesReturnItemMapper;
import com.cc.core.service.sal.SalSalesReturnItemService;
import org.springframework.stereotype.Service;

/**
 * 销售退货单明细服务实现
 */
@Service
public class SalSalesReturnItemServiceImpl extends ServiceImpl<SalSalesReturnItemMapper, SalSalesReturnItem>
        implements SalSalesReturnItemService {
}
