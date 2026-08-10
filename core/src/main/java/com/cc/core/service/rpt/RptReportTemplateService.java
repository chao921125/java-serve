package com.cc.core.service.rpt;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.rpt.RptReportTemplate;

/**
 * RptReportTemplate 服务接口
 */
public interface RptReportTemplateService extends IService<RptReportTemplate> {


    /**
     * 执行报表查询
     */
    java.util.List<java.util.Map<String, Object>> executeQuery(Long templateId, java.util.Map<String, Object> params);

    /**
     * 根据编码查模板
     */
    RptReportTemplate getByCode(String code);

}
