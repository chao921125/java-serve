package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasLocation;
import com.cc.core.mapper.bas.BasLocationMapper;
import com.cc.core.service.bas.BasLocationService;
import org.springframework.stereotype.Service;

/**
 * 库位服务实现
 */
@Service
public class BasLocationServiceImpl extends ServiceImpl<BasLocationMapper, BasLocation> implements BasLocationService {
}
