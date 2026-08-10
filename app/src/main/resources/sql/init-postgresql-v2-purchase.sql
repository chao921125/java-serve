-- ============================================
-- 进销存管理系统 V2 - 采购管理增强 (Phase 2)
-- ============================================

-- 请购单
DROP TABLE IF EXISTS pur_purchase_requisition CASCADE;
CREATE TABLE pur_purchase_requisition (
    id BIGSERIAL PRIMARY KEY,
    requisition_no VARCHAR(50) NOT NULL,
    requisition_date DATE NOT NULL,
    department_id BIGINT,
    applicant_id BIGINT,
    status SMALLINT DEFAULT 0,
    expected_date DATE,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_pr_no UNIQUE (requisition_no)
);
COMMENT ON TABLE pur_purchase_requisition IS '请购单';
CREATE INDEX idx_pur_pr_status ON pur_purchase_requisition(status);
CREATE INDEX idx_pur_pr_applicant ON pur_purchase_requisition(applicant_id);

-- 请购单明细
DROP TABLE IF EXISTS pur_purchase_requisition_item CASCADE;
CREATE TABLE pur_purchase_requisition_item (
    id BIGSERIAL PRIMARY KEY,
    requisition_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    estimated_price DECIMAL(18,2),
    estimated_amount DECIMAL(18,2),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE pur_purchase_requisition_item IS '请购单明细';
CREATE INDEX idx_pur_pri_req ON pur_purchase_requisition_item(requisition_id);

-- 询价单
DROP TABLE IF EXISTS pur_inquiry CASCADE;
CREATE TABLE pur_inquiry (
    id BIGSERIAL PRIMARY KEY,
    inquiry_no VARCHAR(50) NOT NULL,
    inquiry_date DATE NOT NULL,
    requisition_id BIGINT,
    status SMALLINT DEFAULT 0,
    deadline DATE,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_i_no UNIQUE (inquiry_no)
);
COMMENT ON TABLE pur_inquiry IS '询价单';
CREATE INDEX idx_pur_i_status ON pur_inquiry(status);

-- 询价单明细
DROP TABLE IF EXISTS pur_inquiry_item CASCADE;
CREATE TABLE pur_inquiry_item (
    id BIGSERIAL PRIMARY KEY,
    inquiry_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    spec VARCHAR(500),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE pur_inquiry_item IS '询价单明细';
CREATE INDEX idx_pur_ii_inquiry ON pur_inquiry_item(inquiry_id);

-- 询价供应商
DROP TABLE IF EXISTS pur_inquiry_supplier CASCADE;
CREATE TABLE pur_inquiry_supplier (
    id BIGSERIAL PRIMARY KEY,
    inquiry_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    quote_status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE pur_inquiry_supplier IS '询价供应商';
CREATE INDEX idx_pur_is_inquiry ON pur_inquiry_supplier(inquiry_id);
CREATE INDEX idx_pur_is_supplier ON pur_inquiry_supplier(supplier_id);

-- 报价明细
DROP TABLE IF EXISTS pur_quote_detail CASCADE;
CREATE TABLE pur_quote_detail (
    id BIGSERIAL PRIMARY KEY,
    inquiry_id BIGINT NOT NULL,
    inquiry_supplier_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    unit_price DECIMAL(18,2),
    delivery_days INTEGER,
    min_quantity DECIMAL(18,4),
    payment_terms VARCHAR(200),
    is_selected SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE pur_quote_detail IS '报价明细';
CREATE INDEX idx_pur_qd_inquiry ON pur_quote_detail(inquiry_id);
CREATE INDEX idx_pur_qd_supplier ON pur_quote_detail(inquiry_supplier_id);

-- 补货建议
DROP TABLE IF EXISTS pur_replenishment_suggestion CASCADE;
CREATE TABLE pur_replenishment_suggestion (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    current_quantity DECIMAL(18,4),
    safety_quantity DECIMAL(18,4),
    suggested_quantity DECIMAL(18,4),
    avg_daily_sales DECIMAL(18,4),
    lead_time_days INTEGER,
    suggested_date DATE,
    status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE pur_replenishment_suggestion IS '补货建议';
CREATE INDEX idx_pur_rs_product ON pur_replenishment_suggestion(product_id, warehouse_id);

-- 采购费用分摊
DROP TABLE IF EXISTS pur_expense_allocation CASCADE;
CREATE TABLE pur_expense_allocation (
    id BIGSERIAL PRIMARY KEY,
    allocation_no VARCHAR(50) NOT NULL,
    receipt_id BIGINT,
    expense_type VARCHAR(20) NOT NULL,
    expense_amount DECIMAL(18,2) NOT NULL,
    allocation_method VARCHAR(20) DEFAULT 'BY_AMOUNT',
    status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_ea_no UNIQUE (allocation_no)
);
COMMENT ON TABLE pur_expense_allocation IS '采购费用分摊';
CREATE INDEX idx_pur_ea_receipt ON pur_expense_allocation(receipt_id);

-- 采购费用分摊明细
DROP TABLE IF EXISTS pur_expense_allocation_detail CASCADE;
CREATE TABLE pur_expense_allocation_detail (
    id BIGSERIAL PRIMARY KEY,
    allocation_id BIGINT NOT NULL,
    receipt_item_id BIGINT,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4),
    amount DECIMAL(18,2),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE pur_expense_allocation_detail IS '采购费用分摊明细';
CREATE INDEX idx_pur_ead_allocation ON pur_expense_allocation_detail(allocation_id);

-- 供应商对账单
DROP TABLE IF EXISTS pur_supplier_statement CASCADE;
CREATE TABLE pur_supplier_statement (
    id BIGSERIAL PRIMARY KEY,
    statement_no VARCHAR(50) NOT NULL,
    supplier_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    opening_payable DECIMAL(18,2),
    purchase_amount DECIMAL(18,2),
    return_amount DECIMAL(18,2),
    payment_amount DECIMAL(18,2),
    closing_payable DECIMAL(18,2),
    status SMALLINT DEFAULT 0,
    confirmed_by VARCHAR(64),
    confirmed_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_ss_no UNIQUE (statement_no)
);
COMMENT ON TABLE pur_supplier_statement IS '供应商对账单';
CREATE INDEX idx_pur_ss_supplier ON pur_supplier_statement(supplier_id, start_date, end_date);

-- 供应商评估
DROP TABLE IF EXISTS pur_supplier_evaluation CASCADE;
CREATE TABLE pur_supplier_evaluation (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    evaluation_date DATE NOT NULL,
    evaluator_id BIGINT,
    quality_score DECIMAL(5,2),
    delivery_score DECIMAL(5,2),
    price_score DECIMAL(5,2),
    service_score DECIMAL(5,2),
    total_score DECIMAL(5,2),
    evaluation_result VARCHAR(500),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE pur_supplier_evaluation IS '供应商评估';
CREATE INDEX idx_pur_se_supplier ON pur_supplier_evaluation(supplier_id);
