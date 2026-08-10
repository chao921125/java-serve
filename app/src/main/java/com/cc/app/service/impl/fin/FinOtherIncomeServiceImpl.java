package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinOtherIncome;
import com.cc.core.mapper.fin.FinOtherIncomeMapper;
import com.cc.core.service.fin.FinOtherIncomeService;
import org.springframework.stereotype.Service;

/**
 * 其他收支服务实现
 */
@Service
public class FinOtherIncomeServiceImpl extends ServiceImpl<FinOtherIncomeMapper, FinOtherIncome>
        implements FinOtherIncomeService {
}
