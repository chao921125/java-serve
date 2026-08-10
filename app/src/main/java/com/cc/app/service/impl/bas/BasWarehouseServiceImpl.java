package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasWarehouse;
import com.cc.core.mapper.bas.BasWarehouseMapper;
import com.cc.core.service.bas.BasWarehouseService;
import org.springframework.stereotype.Service;

/**
 * 仓库服务实现
 */
@Service
public class BasWarehouseServiceImpl extends ServiceImpl<BasWarehouseMapper, BasWarehouse> implements BasWarehouseService {
}
