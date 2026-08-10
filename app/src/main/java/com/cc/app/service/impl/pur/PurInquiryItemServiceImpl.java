package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurInquiryItem;
import com.cc.core.mapper.pur.PurInquiryItemMapper;
import com.cc.core.service.pur.PurInquiryItemService;
import org.springframework.stereotype.Service;

/**
 * PurInquiryItem 服务实现
 */
@Service
public class PurInquiryItemServiceImpl extends ServiceImpl<PurInquiryItemMapper, PurInquiryItem> implements PurInquiryItemService {
}
