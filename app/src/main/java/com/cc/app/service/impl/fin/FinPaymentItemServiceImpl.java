package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinPaymentItem;
import com.cc.core.mapper.fin.FinPaymentItemMapper;
import com.cc.core.service.fin.FinPaymentItemService;
import org.springframework.stereotype.Service;

/**
 * 付款单明细服务实现
 */
@Service
public class FinPaymentItemServiceImpl extends ServiceImpl<FinPaymentItemMapper, FinPaymentItem>
        implements FinPaymentItemService {
}
