package com.cc.app.service.impl.rpt;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.rpt.RptReportTemplate;
import com.cc.core.mapper.rpt.RptReportTemplateMapper;
import com.cc.core.service.rpt.RptReportTemplateService;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * RptReportTemplate 服务实现
 */
@Service
public class RptReportTemplateServiceImpl extends ServiceImpl<RptReportTemplateMapper, RptReportTemplate> implements RptReportTemplateService {

    // ==== Business Logic Methods ====

    @Override
    public java.util.List<java.util.Map<String, Object>> executeQuery(Long templateId, java.util.Map<String, Object> params) {
        RptReportTemplate template = getById(templateId);
        if (template == null) throw new RuntimeException("报表模板不存在");
        // 此处应通过 JdbcTemplate 或 MyBatis 直接执行 SQL
        // 简化返回空列表，实际需注入 JdbcTemplate
        return java.util.Collections.emptyList();
    }

    @Override
    public RptReportTemplate getByCode(String code) {
        return getOne(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<RptReportTemplate>()
                .eq(RptReportTemplate::getCode, code)
        );
    }

}
