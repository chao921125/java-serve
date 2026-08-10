package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasUnit;
import com.cc.core.mapper.bas.BasUnitMapper;
import com.cc.core.service.bas.BasUnitService;
import org.springframework.stereotype.Service;

/**
 * 计量单位服务实现
 */
@Service
public class BasUnitServiceImpl extends ServiceImpl<BasUnitMapper, BasUnit> implements BasUnitService {
}
