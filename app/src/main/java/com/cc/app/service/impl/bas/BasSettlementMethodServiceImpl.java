package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasSettlementMethod;
import com.cc.core.mapper.bas.BasSettlementMethodMapper;
import com.cc.core.service.bas.BasSettlementMethodService;
import org.springframework.stereotype.Service;

/**
 * 结算方式服务实现
 */
@Service
public class BasSettlementMethodServiceImpl extends ServiceImpl<BasSettlementMethodMapper, BasSettlementMethod> implements BasSettlementMethodService {
}
