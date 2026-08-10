package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalQuotationItem;
import com.cc.core.mapper.sal.SalQuotationItemMapper;
import com.cc.core.service.sal.SalQuotationItemService;
import org.springframework.stereotype.Service;

/**
 * SalQuotationItem 服务实现
 */
@Service
public class SalQuotationItemServiceImpl extends ServiceImpl<SalQuotationItemMapper, SalQuotationItem> implements SalQuotationItemService {
}
