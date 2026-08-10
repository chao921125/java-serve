-- ============================================
-- 进销存管理系统 V2 - 资金财务增强 (Phase 5)
-- ============================================

-- 成本计算记录
DROP TABLE IF EXISTS fin_cost_calculation CASCADE;
CREATE TABLE fin_cost_calculation (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT,
    batch_no VARCHAR(50),
    transaction_type VARCHAR(20) NOT NULL,
    transaction_id BIGINT NOT NULL,
    quantity_before DECIMAL(18,4),
    cost_before DECIMAL(18,2),
    total_cost_before DECIMAL(18,2),
    transaction_quantity DECIMAL(18,4) NOT NULL,
    transaction_unit_cost DECIMAL(18,2) NOT NULL,
    transaction_total_cost DECIMAL(18,2) NOT NULL,
    quantity_after DECIMAL(18,4),
    cost_after DECIMAL(18,2),
    total_cost_after DECIMAL(18,2),
    calculated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE fin_cost_calculation IS '成本计算记录';
CREATE INDEX idx_fin_cc_product ON fin_cost_calculation(product_id, warehouse_id);
CREATE INDEX idx_fin_cc_txn ON fin_cost_calculation(transaction_type, transaction_id);
CREATE INDEX idx_fin_cc_time ON fin_cost_calculation(calculated_time);

-- 发票
DROP TABLE IF EXISTS fin_invoice CASCADE;
CREATE TABLE fin_invoice (
    id BIGSERIAL PRIMARY KEY,
    invoice_no VARCHAR(50) NOT NULL,
    invoice_code VARCHAR(50),
    invoice_type VARCHAR(20) NOT NULL,
    invoice_date DATE NOT NULL,
    source_type VARCHAR(50) NOT NULL,
    source_id BIGINT NOT NULL,
    counterparty_id BIGINT NOT NULL,
    counterparty_name VARCHAR(200) NOT NULL,
    invoice_amount DECIMAL(18,2) NOT NULL,
    tax_rate DECIMAL(5,2) DEFAULT 0,
    tax_amount DECIMAL(18,2) DEFAULT 0,
    total_amount DECIMAL(18,2) NOT NULL,
    status SMALLINT DEFAULT 0,
    verification_status VARCHAR(20),
    verificated_date DATE,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_fin_i_no UNIQUE (invoice_no)
);
COMMENT ON TABLE fin_invoice IS '发票';
CREATE INDEX idx_fin_i_type_date ON fin_invoice(invoice_type, invoice_date);
CREATE INDEX idx_fin_i_source ON fin_invoice(source_type, source_id);
CREATE INDEX idx_fin_i_party ON fin_invoice(counterparty_id);

-- 运营费用分摊
DROP TABLE IF EXISTS fin_expense_allocation CASCADE;
CREATE TABLE fin_expense_allocation (
    id BIGSERIAL PRIMARY KEY,
    expense_no VARCHAR(50) NOT NULL,
    expense_type VARCHAR(50) NOT NULL,
    expense_amount DECIMAL(18,2) NOT NULL,
    allocation_method VARCHAR(20) NOT NULL DEFAULT 'BY_SALES_AMOUNT',
    allocation_period VARCHAR(7) NOT NULL,
    status SMALLINT DEFAULT 0,
    allocated_amount DECIMAL(18,2) DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_fin_fea_no UNIQUE (expense_no)
);
COMMENT ON TABLE fin_expense_allocation IS '运营费用分摊';
CREATE INDEX idx_fin_fea_period ON fin_expense_allocation(allocation_period);

-- 运营费用分摊明细
DROP TABLE IF EXISTS fin_expense_allocation_detail CASCADE;
CREATE TABLE fin_expense_allocation_detail (
    id BIGSERIAL PRIMARY KEY,
    allocation_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id BIGINT NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    allocation_base DECIMAL(18,4),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE fin_expense_allocation_detail IS '运营费用分摊明细';
CREATE INDEX idx_fin_fead_alloc ON fin_expense_allocation_detail(allocation_id);
