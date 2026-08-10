package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasSupplier;
import com.cc.core.mapper.bas.BasSupplierMapper;
import com.cc.core.service.bas.BasSupplierService;
import org.springframework.stereotype.Service;

/**
 * 供应商服务实现
 */
@Service
public class BasSupplierServiceImpl extends ServiceImpl<BasSupplierMapper, BasSupplier> implements BasSupplierService {
}
