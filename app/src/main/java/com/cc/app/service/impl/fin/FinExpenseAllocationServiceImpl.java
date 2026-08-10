package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinExpenseAllocation;
import com.cc.core.mapper.fin.FinExpenseAllocationMapper;
import com.cc.core.service.fin.FinExpenseAllocationService;
import org.springframework.stereotype.Service;

/**
 * FinExpenseAllocation 服务实现
 */
@Service
public class FinExpenseAllocationServiceImpl extends ServiceImpl<FinExpenseAllocationMapper, FinExpenseAllocation> implements FinExpenseAllocationService {
}
