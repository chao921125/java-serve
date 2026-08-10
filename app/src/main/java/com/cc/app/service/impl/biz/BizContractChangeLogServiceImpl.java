package com.cc.app.service.impl.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.biz.BizContractChangeLog;
import com.cc.core.mapper.biz.BizContractChangeLogMapper;
import com.cc.core.service.biz.BizContractChangeLogService;
import org.springframework.stereotype.Service;

/**
 * BizContractChangeLog 服务实现
 */
@Service
public class BizContractChangeLogServiceImpl extends ServiceImpl<BizContractChangeLogMapper, BizContractChangeLog> implements BizContractChangeLogService {
}
