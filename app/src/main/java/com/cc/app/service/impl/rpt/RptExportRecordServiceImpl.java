package com.cc.app.service.impl.rpt;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.rpt.RptExportRecord;
import com.cc.core.mapper.rpt.RptExportRecordMapper;
import com.cc.core.service.rpt.RptExportRecordService;
import org.springframework.stereotype.Service;

/**
 * RptExportRecord 服务实现
 */
@Service
public class RptExportRecordServiceImpl extends ServiceImpl<RptExportRecordMapper, RptExportRecord> implements RptExportRecordService {
}
