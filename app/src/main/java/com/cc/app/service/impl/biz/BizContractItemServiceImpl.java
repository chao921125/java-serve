package com.cc.app.service.impl.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.biz.BizContractItem;
import com.cc.core.mapper.biz.BizContractItemMapper;
import com.cc.core.service.biz.BizContractItemService;
import org.springframework.stereotype.Service;

/**
 * BizContractItem 服务实现
 */
@Service
public class BizContractItemServiceImpl extends ServiceImpl<BizContractItemMapper, BizContractItem> implements BizContractItemService {
}
