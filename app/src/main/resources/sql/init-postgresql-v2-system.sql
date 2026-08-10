-- ============================================
-- 进销存管理系统 V2 - 系统管理增强 (Phase 7)
-- ============================================

-- 审批流定义
DROP TABLE IF EXISTS sys_approval_flow CASCADE;
CREATE TABLE sys_approval_flow (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    target_type VARCHAR(50) NOT NULL,
    is_enabled SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sys_af_code UNIQUE (code)
);
COMMENT ON TABLE sys_approval_flow IS '审批流定义';
COMMENT ON COLUMN sys_approval_flow.target_type IS '适用业务类型: PURCHASE_ORDER/SALES_ORDER/EXPENSE';

-- 审批节点
DROP TABLE IF EXISTS sys_approval_node CASCADE;
CREATE TABLE sys_approval_node (
    id BIGSERIAL PRIMARY KEY,
    flow_id BIGINT NOT NULL,
    node_name VARCHAR(100) NOT NULL,
    node_order INTEGER NOT NULL,
    approver_type VARCHAR(20) NOT NULL,
    approver_value VARCHAR(500) NOT NULL,
    can_reject SMALLINT DEFAULT 1,
    can_delegate SMALLINT DEFAULT 1,
    timeout_hours INTEGER,
    condition_expression VARCHAR(500),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_approval_node IS '审批节点';
CREATE INDEX idx_sys_an_flow ON sys_approval_node(flow_id);

-- 审批实例
DROP TABLE IF EXISTS sys_approval_instance CASCADE;
CREATE TABLE sys_approval_instance (
    id BIGSERIAL PRIMARY KEY,
    flow_id BIGINT NOT NULL,
    business_type VARCHAR(50) NOT NULL,
    business_id BIGINT NOT NULL,
    business_no VARCHAR(50) NOT NULL,
    current_node_order INTEGER DEFAULT 1,
    total_nodes INTEGER NOT NULL,
    status SMALLINT DEFAULT 0,
    applicant_id BIGINT NOT NULL,
    applicant_name VARCHAR(100) NOT NULL,
    applied_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_approval_instance IS '审批实例';
CREATE UNIQUE INDEX uk_sys_ai_btb ON sys_approval_instance(business_type, business_id);
CREATE INDEX idx_sys_ai_status ON sys_approval_instance(status);
CREATE INDEX idx_sys_ai_applicant ON sys_approval_instance(applicant_id);

-- 审批记录
DROP TABLE IF EXISTS sys_approval_record CASCADE;
CREATE TABLE sys_approval_record (
    id BIGSERIAL PRIMARY KEY,
    instance_id BIGINT NOT NULL,
    node_id BIGINT NOT NULL,
    node_order INTEGER NOT NULL,
    node_name VARCHAR(100) NOT NULL,
    approver_id BIGINT NOT NULL,
    approver_name VARCHAR(100) NOT NULL,
    action VARCHAR(20) NOT NULL,
    comment VARCHAR(500),
    delegate_to_id BIGINT,
    action_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_approval_record IS '审批记录';
CREATE INDEX idx_sys_ar_instance ON sys_approval_record(instance_id);
CREATE INDEX idx_sys_ar_approver ON sys_approval_record(approver_id);

-- 通知消息
DROP TABLE IF EXISTS sys_notification CASCADE;
CREATE TABLE sys_notification (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(20) NOT NULL,
    level VARCHAR(20) DEFAULT 'INFO',
    sender_id BIGINT,
    sender_name VARCHAR(100),
    target_type VARCHAR(20) NOT NULL,
    target_value VARCHAR(500),
    business_type VARCHAR(50),
    business_id BIGINT,
    send_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expire_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_notification IS '通知消息';
COMMENT ON COLUMN sys_notification.type IS '类型: SYSTEM/APPROVAL/WARNING/BUSINESS';
COMMENT ON COLUMN sys_notification.level IS '级别: INFO/WARNING/URGENT';
COMMENT ON COLUMN sys_notification.target_type IS '目标类型: ALL/USER/ROLE/DEPARTMENT';
CREATE INDEX idx_sys_n_type_level ON sys_notification(type, level);
CREATE INDEX idx_sys_n_time ON sys_notification(send_time);

-- 通知已读记录
DROP TABLE IF EXISTS sys_notification_read CASCADE;
CREATE TABLE sys_notification_read (
    id BIGSERIAL PRIMARY KEY,
    notification_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    read_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_notification_read IS '通知已读记录';
CREATE UNIQUE INDEX uk_sys_nr_nu ON sys_notification_read(notification_id, user_id);

-- 系统配置
DROP TABLE IF EXISTS sys_config CASCADE;
CREATE TABLE sys_config (
    id BIGSERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL,
    config_value TEXT NOT NULL,
    config_type VARCHAR(20) DEFAULT 'STRING',
    config_group VARCHAR(50) DEFAULT 'SYSTEM',
    description VARCHAR(500),
    is_system SMALLINT DEFAULT 0,
    sort_order INTEGER DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sys_c_key UNIQUE (config_key)
);
COMMENT ON TABLE sys_config IS '系统配置';
COMMENT ON COLUMN sys_config.config_type IS '类型: STRING/NUMBER/BOOLEAN/JSON';
COMMENT ON COLUMN sys_config.config_group IS '分组: SYSTEM/BUSINESS/EMAIL/SMS/WECHAT';
CREATE INDEX idx_sys_c_group ON sys_config(config_group);

-- 附件
DROP TABLE IF EXISTS sys_attachment CASCADE;
CREATE TABLE sys_attachment (
    id BIGSERIAL PRIMARY KEY,
    original_name VARCHAR(500) NOT NULL,
    stored_name VARCHAR(500) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    file_ext VARCHAR(20),
    storage_type VARCHAR(20) DEFAULT 'LOCAL',
    md5 VARCHAR(64),
    business_type VARCHAR(50) NOT NULL,
    business_id BIGINT NOT NULL,
    uploaded_by VARCHAR(64) NOT NULL,
    uploaded_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_attachment IS '附件';
CREATE INDEX idx_sys_att_biz ON sys_attachment(business_type, business_id);
CREATE INDEX idx_sys_att_md5 ON sys_attachment(md5);

-- 打印模板
DROP TABLE IF EXISTS sys_print_template CASCADE;
CREATE TABLE sys_print_template (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    business_type VARCHAR(50) NOT NULL,
    template_content TEXT NOT NULL,
    page_size VARCHAR(20) DEFAULT 'A4',
    is_default SMALLINT DEFAULT 0,
    is_enabled SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sys_pt_code UNIQUE (code)
);
COMMENT ON TABLE sys_print_template IS '打印模板';
CREATE INDEX idx_sys_pt_biz ON sys_print_template(business_type);

-- 数据备份记录
DROP TABLE IF EXISTS sys_data_backup CASCADE;
CREATE TABLE sys_data_backup (
    id BIGSERIAL PRIMARY KEY,
    backup_type VARCHAR(20) NOT NULL,
    backup_file VARCHAR(500) NOT NULL,
    file_size BIGINT,
    status SMALLINT DEFAULT 0,
    error_message TEXT,
    started_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_time TIMESTAMP,
    triggered_by VARCHAR(64) NOT NULL,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_data_backup IS '数据备份记录';
CREATE INDEX idx_sys_db_type ON sys_data_backup(backup_type);
CREATE INDEX idx_sys_db_status ON sys_data_backup(status);
CREATE INDEX idx_sys_db_time ON sys_data_backup(started_time);
