-- ============================================
-- 进销存管理系统 V2 - 库存管理增强 (Phase 4)
-- ============================================

-- 库存预留
DROP TABLE IF EXISTS inv_stock_reservation CASCADE;
CREATE TABLE inv_stock_reservation (
    id BIGSERIAL PRIMARY KEY,
    reservation_no VARCHAR(50) NOT NULL,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    released_quantity DECIMAL(18,4) DEFAULT 0,
    source_type VARCHAR(50) NOT NULL,
    source_id BIGINT NOT NULL,
    source_item_id BIGINT,
    status SMALLINT DEFAULT 0,
    reserved_by VARCHAR(64) NOT NULL,
    reserved_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expire_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_inv_sr_no UNIQUE (reservation_no)
);
COMMENT ON TABLE inv_stock_reservation IS '库存预留';
CREATE INDEX idx_inv_sr_product ON inv_stock_reservation(product_id, warehouse_id, batch_no, status);
CREATE INDEX idx_inv_sr_source ON inv_stock_reservation(source_type, source_id);
CREATE INDEX idx_inv_sr_expire ON inv_stock_reservation(expire_time);

-- 保质期预警
DROP TABLE IF EXISTS inv_expiry_alert CASCADE;
CREATE TABLE inv_expiry_alert (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    expiry_date DATE NOT NULL,
    quantity DECIMAL(18,4),
    remaining_days INTEGER,
    alert_level VARCHAR(20) NOT NULL,
    alert_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    handled SMALLINT DEFAULT 0,
    handled_time TIMESTAMP,
    handle_method VARCHAR(50),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE inv_expiry_alert IS '保质期预警';
CREATE INDEX idx_inv_ea_product ON inv_expiry_alert(product_id);
CREATE INDEX idx_inv_ea_level ON inv_expiry_alert(alert_level);
CREATE INDEX idx_inv_ea_time ON inv_expiry_alert(alert_time);
CREATE INDEX idx_inv_ea_handled ON inv_expiry_alert(handled);

-- 库存库龄快照
DROP TABLE IF EXISTS inv_stock_aging CASCADE;
CREATE TABLE inv_stock_aging (
    id BIGSERIAL PRIMARY KEY,
    snapshot_date DATE NOT NULL,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    quantity DECIMAL(18,4),
    cost_amount DECIMAL(18,2),
    last_inbound_date DATE,
    aging_days INTEGER,
    aging_bucket VARCHAR(20),
    turnover_rate DECIMAL(8,4),
    is_slow_moving SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE inv_stock_aging IS '库存库龄快照';
CREATE INDEX idx_inv_sa_date ON inv_stock_aging(snapshot_date);
CREATE INDEX idx_inv_sa_product ON inv_stock_aging(product_id, warehouse_id);
CREATE INDEX idx_inv_sa_bucket ON inv_stock_aging(aging_bucket);
