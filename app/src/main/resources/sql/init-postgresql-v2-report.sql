-- ============================================
-- 进销存管理系统 V2 - 报表与数据分析 (Phase 6)
-- ============================================

-- 报表模板
DROP TABLE IF EXISTS rpt_report_template CASCADE;
CREATE TABLE rpt_report_template (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    query_sql TEXT NOT NULL,
    columns_config TEXT,
    filters_config TEXT,
    charts_config TEXT,
    is_system SMALLINT DEFAULT 0,
    is_enabled SMALLINT DEFAULT 1,
    sort_order INTEGER DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_rpt_rt_code UNIQUE (code)
);
COMMENT ON TABLE rpt_report_template IS '报表模板';
COMMENT ON COLUMN rpt_report_template.category IS '报表分类: SALES/PURCHASE/INVENTORY/FINANCE/HR';
CREATE INDEX idx_rpt_rt_category ON rpt_report_template(category);

-- 导出记录
DROP TABLE IF EXISTS rpt_export_record CASCADE;
CREATE TABLE rpt_export_record (
    id BIGSERIAL PRIMARY KEY,
    report_id BIGINT,
    export_type VARCHAR(20) NOT NULL,
    file_name VARCHAR(200) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT,
    filter_params TEXT,
    exported_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    exported_by VARCHAR(64) NOT NULL,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE rpt_export_record IS '导出记录';
CREATE INDEX idx_rpt_er_time ON rpt_export_record(exported_time);
CREATE INDEX idx_rpt_er_by ON rpt_export_record(exported_by);
