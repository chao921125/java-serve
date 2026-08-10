package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasPaymentMethod;
import com.cc.core.mapper.bas.BasPaymentMethodMapper;
import com.cc.core.service.bas.BasPaymentMethodService;
import org.springframework.stereotype.Service;

/**
 * 付款方式服务实现
 */
@Service
public class BasPaymentMethodServiceImpl extends ServiceImpl<BasPaymentMethodMapper, BasPaymentMethod> implements BasPaymentMethodService {
}
