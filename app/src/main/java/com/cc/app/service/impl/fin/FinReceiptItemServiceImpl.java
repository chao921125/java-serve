package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinReceiptItem;
import com.cc.core.mapper.fin.FinReceiptItemMapper;
import com.cc.core.service.fin.FinReceiptItemService;
import org.springframework.stereotype.Service;

/**
 * 收款单明细服务实现
 */
@Service
public class FinReceiptItemServiceImpl extends ServiceImpl<FinReceiptItemMapper, FinReceiptItem>
        implements FinReceiptItemService {
}
