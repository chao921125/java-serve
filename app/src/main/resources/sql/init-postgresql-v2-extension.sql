-- ============================================
-- 进销存管理系统 V2 - 扩展能力 (Phase 9)
-- ============================================

-- 合同
DROP TABLE IF EXISTS biz_contract CASCADE;
CREATE TABLE biz_contract (
    id BIGSERIAL PRIMARY KEY,
    contract_no VARCHAR(50) NOT NULL,
    contract_name VARCHAR(200) NOT NULL,
    contract_type VARCHAR(20) NOT NULL,
    counterparty_type VARCHAR(20) NOT NULL,
    counterparty_id BIGINT NOT NULL,
    counterparty_name VARCHAR(200) NOT NULL,
    contract_amount DECIMAL(18,2) NOT NULL,
    signed_amount DECIMAL(18,2) DEFAULT 0,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    signed_date DATE,
    status SMALLINT DEFAULT 0,
    payment_terms TEXT,
    delivery_terms TEXT,
    responsible_person VARCHAR(100),
    attachment_count INTEGER DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark TEXT,
    CONSTRAINT uk_biz_c_no UNIQUE (contract_no)
);
COMMENT ON TABLE biz_contract IS '合同';
COMMENT ON COLUMN biz_contract.status IS '状态: 0-草稿 1-执行中 2-已完成 3-已终止 4-已过期';
CREATE INDEX idx_biz_c_type ON biz_contract(contract_type);
CREATE INDEX idx_biz_c_party ON biz_contract(counterparty_type, counterparty_id);
CREATE INDEX idx_biz_c_status ON biz_contract(status);

-- 合同明细
DROP TABLE IF EXISTS biz_contract_item CASCADE;
CREATE TABLE biz_contract_item (
    id BIGSERIAL PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    unit_price DECIMAL(18,2) NOT NULL,
    total_amount DECIMAL(18,2) NOT NULL,
    delivered_quantity DECIMAL(18,4) DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE biz_contract_item IS '合同明细';
CREATE INDEX idx_biz_ci_contract ON biz_contract_item(contract_id);

-- 合同变更记录
DROP TABLE IF EXISTS biz_contract_change_log CASCADE;
CREATE TABLE biz_contract_change_log (
    id BIGSERIAL PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    change_type VARCHAR(20) NOT NULL,
    before_value TEXT,
    after_value TEXT,
    change_reason VARCHAR(500),
    changed_by VARCHAR(64) NOT NULL,
    changed_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE biz_contract_change_log IS '合同变更记录';
CREATE INDEX idx_biz_ccl_contract ON biz_contract_change_log(contract_id);

-- 销售线索(CRM)
DROP TABLE IF EXISTS crm_lead CASCADE;
CREATE TABLE crm_lead (
    id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(200) NOT NULL,
    contact_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    source VARCHAR(50),
    industry VARCHAR(50),
    address VARCHAR(500),
    status SMALLINT DEFAULT 0,
    convert_to_customer_id BIGINT,
    owner_id BIGINT,
    next_follow_date DATE,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark TEXT
);
COMMENT ON TABLE crm_lead IS '销售线索';
COMMENT ON COLUMN crm_lead.status IS '状态: 0-新线索 1-已联系 2-已确认 3-已转化 4-已关闭';
CREATE INDEX idx_crm_l_status ON crm_lead(status);
CREATE INDEX idx_crm_l_owner ON crm_lead(owner_id);
CREATE INDEX idx_crm_l_source ON crm_lead(source);

-- 跟进记录(CRM)
DROP TABLE IF EXISTS crm_follow_record CASCADE;
CREATE TABLE crm_follow_record (
    id BIGSERIAL PRIMARY KEY,
    lead_id BIGINT,
    customer_id BIGINT,
    follow_type VARCHAR(20) NOT NULL,
    follow_date DATE NOT NULL,
    content TEXT NOT NULL,
    result VARCHAR(500),
    next_plan VARCHAR(500),
    followed_by VARCHAR(64) NOT NULL,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE crm_follow_record IS '跟进记录';
CREATE INDEX idx_crm_fr_lead ON crm_follow_record(lead_id);
CREATE INDEX idx_crm_fr_customer ON crm_follow_record(customer_id);
CREATE INDEX idx_crm_fr_date ON crm_follow_record(follow_date);

-- 商机(CRM)
DROP TABLE IF EXISTS crm_opportunity CASCADE;
CREATE TABLE crm_opportunity (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    customer_id BIGINT NOT NULL,
    expected_amount DECIMAL(18,2) NOT NULL,
    probability INTEGER DEFAULT 0,
    stage VARCHAR(20),
    expected_close_date DATE,
    owner_id BIGINT NOT NULL,
    competitor VARCHAR(200),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark TEXT
);
COMMENT ON TABLE crm_opportunity IS '商机';
COMMENT ON COLUMN crm_opportunity.stage IS '阶段: INITIAL/NEEDS_ANALYSIS/QUOTATION/NEGOTIATION/WON/LOST';
CREATE INDEX idx_crm_o_customer ON crm_opportunity(customer_id);
CREATE INDEX idx_crm_o_stage ON crm_opportunity(stage);
CREATE INDEX idx_crm_o_owner ON crm_opportunity(owner_id);

-- 门店
DROP TABLE IF EXISTS bas_store CASCADE;
CREATE TABLE bas_store (
    id BIGSERIAL PRIMARY KEY,
    store_code VARCHAR(50) NOT NULL,
    store_name VARCHAR(100) NOT NULL,
    warehouse_id BIGINT,
    address VARCHAR(500),
    phone VARCHAR(20),
    manager_name VARCHAR(100),
    is_enabled SMALLINT DEFAULT 1,
    opening_date DATE,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_s_code UNIQUE (store_code)
);
COMMENT ON TABLE bas_store IS '门店';
CREATE INDEX idx_bas_s_warehouse ON bas_store(warehouse_id);
