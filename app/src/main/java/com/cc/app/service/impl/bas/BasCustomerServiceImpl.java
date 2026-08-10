package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasCustomer;
import com.cc.core.mapper.bas.BasCustomerMapper;
import com.cc.core.service.bas.BasCustomerService;
import org.springframework.stereotype.Service;

/**
 * 客户服务实现
 */
@Service
public class BasCustomerServiceImpl extends ServiceImpl<BasCustomerMapper, BasCustomer> implements BasCustomerService {
}
