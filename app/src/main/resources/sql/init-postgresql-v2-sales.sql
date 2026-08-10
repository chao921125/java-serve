-- ============================================
-- 进销存管理系统 V2 - 销售管理增强 (Phase 3)
-- ============================================

-- 销售报价单
DROP TABLE IF EXISTS sal_quotation CASCADE;
CREATE TABLE sal_quotation (
    id BIGSERIAL PRIMARY KEY,
    quotation_no VARCHAR(50) NOT NULL,
    quotation_date DATE NOT NULL,
    customer_id BIGINT NOT NULL,
    salesperson_id BIGINT,
    status SMALLINT DEFAULT 0,
    valid_until DATE NOT NULL,
    total_amount DECIMAL(18,2),
    discount_rate DECIMAL(5,2),
    after_discount DECIMAL(18,2),
    tax_amount DECIMAL(18,2),
    final_amount DECIMAL(18,2),
    payment_terms VARCHAR(200),
    delivery_terms VARCHAR(200),
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sal_q_no UNIQUE (quotation_no)
);
COMMENT ON TABLE sal_quotation IS '销售报价单';
CREATE INDEX idx_sal_q_customer ON sal_quotation(customer_id);
CREATE INDEX idx_sal_q_status ON sal_quotation(status);
CREATE INDEX idx_sal_q_valid ON sal_quotation(valid_until);

-- 报价单明细
DROP TABLE IF EXISTS sal_quotation_item CASCADE;
CREATE TABLE sal_quotation_item (
    id BIGSERIAL PRIMARY KEY,
    quotation_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    unit_price DECIMAL(18,2) NOT NULL,
    discount_rate DECIMAL(5,2),
    discount_amount DECIMAL(18,2),
    tax_rate DECIMAL(5,2),
    tax_amount DECIMAL(18,2),
    total_amount DECIMAL(18,2) NOT NULL,
    cost_price DECIMAL(18,2),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sal_quotation_item IS '报价单明细';
CREATE INDEX idx_sal_qi_quotation ON sal_quotation_item(quotation_id);

-- 销售换货单
DROP TABLE IF EXISTS sal_exchange_order CASCADE;
CREATE TABLE sal_exchange_order (
    id BIGSERIAL PRIMARY KEY,
    exchange_no VARCHAR(50) NOT NULL,
    exchange_date DATE NOT NULL,
    customer_id BIGINT NOT NULL,
    sales_order_id BIGINT,
    delivery_id BIGINT,
    status SMALLINT DEFAULT 0,
    exchange_reason VARCHAR(500),
    return_total DECIMAL(18,2),
    exchange_total DECIMAL(18,2),
    difference_amount DECIMAL(18,2),
    warehouse_id BIGINT NOT NULL,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sal_eo_no UNIQUE (exchange_no)
);
COMMENT ON TABLE sal_exchange_order IS '销售换货单';
CREATE INDEX idx_sal_eo_customer ON sal_exchange_order(customer_id);
CREATE INDEX idx_sal_eo_status ON sal_exchange_order(status);

-- 换货退回明细
DROP TABLE IF EXISTS sal_exchange_return_item CASCADE;
CREATE TABLE sal_exchange_return_item (
    id BIGSERIAL PRIMARY KEY,
    exchange_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    unit_price DECIMAL(18,2) NOT NULL,
    total_amount DECIMAL(18,2) NOT NULL,
    batch_no VARCHAR(50),
    reason VARCHAR(20),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sal_exchange_return_item IS '换货退回明细';
CREATE INDEX idx_sal_eri_exchange ON sal_exchange_return_item(exchange_id);

-- 换货发出明细
DROP TABLE IF EXISTS sal_exchange_out_item CASCADE;
CREATE TABLE sal_exchange_out_item (
    id BIGSERIAL PRIMARY KEY,
    exchange_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    unit_price DECIMAL(18,2) NOT NULL,
    total_amount DECIMAL(18,2) NOT NULL,
    batch_no VARCHAR(50),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sal_exchange_out_item IS '换货发出明细';
CREATE INDEX idx_sal_eoi_exchange ON sal_exchange_out_item(exchange_id);

-- 提成规则
DROP TABLE IF EXISTS sal_commission_rule CASCADE;
CREATE TABLE sal_commission_rule (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    calculation_method VARCHAR(20) NOT NULL,
    base_on VARCHAR(20) NOT NULL,
    commission_rate DECIMAL(8,4),
    fixed_amount DECIMAL(18,2),
    min_threshold DECIMAL(18,2),
    max_cap DECIMAL(18,2),
    is_enabled SMALLINT DEFAULT 1,
    start_date DATE,
    end_date DATE,
    apply_to_product_type VARCHAR(20),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sal_commission_rule IS '提成规则';
CREATE INDEX idx_sal_cr_enabled ON sal_commission_rule(is_enabled);

-- 提成规则适用对象
DROP TABLE IF EXISTS sal_commission_rule_target CASCADE;
CREATE TABLE sal_commission_rule_target (
    id BIGSERIAL PRIMARY KEY,
    rule_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id BIGINT NOT NULL,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sal_commission_rule_target IS '提成规则适用对象';
CREATE UNIQUE INDEX uk_sal_crt_rtt ON sal_commission_rule_target(rule_id, target_type, target_id);

-- 提成记录
DROP TABLE IF EXISTS sal_commission_record CASCADE;
CREATE TABLE sal_commission_record (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    rule_id BIGINT NOT NULL,
    period VARCHAR(7) NOT NULL,
    source_type VARCHAR(50) NOT NULL,
    source_id BIGINT NOT NULL,
    base_amount DECIMAL(18,2) NOT NULL,
    commission_amount DECIMAL(18,2) NOT NULL,
    status SMALLINT DEFAULT 0,
    calculated_time TIMESTAMP NOT NULL,
    paid_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sal_commission_record IS '提成记录';
CREATE INDEX idx_sal_cr_up ON sal_commission_record(user_id, period);
CREATE INDEX idx_sal_cr_status ON sal_commission_record(status);

-- 客户对账单
DROP TABLE IF EXISTS sal_customer_statement CASCADE;
CREATE TABLE sal_customer_statement (
    id BIGSERIAL PRIMARY KEY,
    statement_no VARCHAR(50) NOT NULL,
    customer_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    opening_receivable DECIMAL(18,2),
    sales_amount DECIMAL(18,2),
    return_amount DECIMAL(18,2),
    receipt_amount DECIMAL(18,2),
    closing_receivable DECIMAL(18,2),
    status SMALLINT DEFAULT 0,
    confirmed_by VARCHAR(64),
    confirmed_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sal_cs_no UNIQUE (statement_no)
);
COMMENT ON TABLE sal_customer_statement IS '客户对账单';
CREATE INDEX idx_sal_cs_customer ON sal_customer_statement(customer_id, start_date, end_date);
