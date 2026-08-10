package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasUnitConversion;
import com.cc.core.mapper.bas.BasUnitConversionMapper;
import com.cc.core.service.bas.BasUnitConversionService;
import org.springframework.stereotype.Service;

/**
 * 单位换算服务实现
 */
@Service
public class BasUnitConversionServiceImpl extends ServiceImpl<BasUnitConversionMapper, BasUnitConversion> implements BasUnitConversionService {
}
