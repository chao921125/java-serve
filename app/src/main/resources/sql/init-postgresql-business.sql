-- ============================================
-- 进销存管理系统 - 业务表初始化脚本 (PostgreSQL)
-- 基础资料 / 采购 / 销售 / 库存 / 资金
-- ============================================

-- ============================================
-- 1. 基础资料模块 (bas_)
-- ============================================

-- 商品分类表
DROP TABLE IF EXISTS bas_product_categories CASCADE;
CREATE TABLE bas_product_categories (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    level SMALLINT DEFAULT 1,
    sort INTEGER DEFAULT 0,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_pc_code UNIQUE (code)
);
COMMENT ON TABLE bas_product_categories IS '商品分类表';
COMMENT ON COLUMN bas_product_categories.level IS '层级 1-一级 2-二级 3-三级';

-- 计量单位表
DROP TABLE IF EXISTS bas_units CASCADE;
CREATE TABLE bas_units (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL,
    precision_val SMALLINT DEFAULT 2,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_unit_code UNIQUE (code)
);
COMMENT ON TABLE bas_units IS '计量单位表';
COMMENT ON COLUMN bas_units.precision_val IS '小数精度位数';

-- 单位换算表
DROP TABLE IF EXISTS bas_unit_conversions CASCADE;
CREATE TABLE bas_unit_conversions (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    from_unit_id BIGINT NOT NULL,
    to_unit_id BIGINT NOT NULL,
    ratio DECIMAL(18,6) NOT NULL,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE bas_unit_conversions IS '单位换算表';
COMMENT ON COLUMN bas_unit_conversions.ratio IS '换算比例 from_unit数量 * ratio = to_unit数量';

-- 商品资料表
DROP TABLE IF EXISTS bas_products CASCADE;
CREATE TABLE bas_products (
    id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    barcode VARCHAR(50),
    name VARCHAR(200) NOT NULL,
    spec VARCHAR(100),
    category_id BIGINT NOT NULL,
    unit_id BIGINT NOT NULL,
    brand_id BIGINT,
    base_price DECIMAL(18,4) NOT NULL DEFAULT 0,
    sale_price DECIMAL(18,4) NOT NULL DEFAULT 0,
    min_sale_price DECIMAL(18,4),
    default_supplier_id BIGINT,
    is_batch_manage SMALLINT DEFAULT 0,
    is_expiry_manage SMALLINT DEFAULT 0,
    is_serial_manage SMALLINT DEFAULT 0,
    allow_negative SMALLINT DEFAULT 0,
    image_url VARCHAR(500),
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_pd_code UNIQUE (product_code)
);
COMMENT ON TABLE bas_products IS '商品资料表';
COMMENT ON COLUMN bas_products.is_batch_manage IS '是否批次管理 0-否 1-是';
COMMENT ON COLUMN bas_products.is_expiry_manage IS '是否保质期管理 0-否 1-是';
COMMENT ON COLUMN bas_products.is_serial_manage IS '是否序列号管理 0-否 1-是';
COMMENT ON COLUMN bas_products.allow_negative IS '是否允许负库存 0-否 1-是';

-- 仓库表
DROP TABLE IF EXISTS bas_warehouses CASCADE;
CREATE TABLE bas_warehouses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    type SMALLINT DEFAULT 0,
    manager_id BIGINT,
    address VARCHAR(200),
    is_default SMALLINT DEFAULT 0,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_wh_code UNIQUE (code)
);
COMMENT ON TABLE bas_warehouses IS '仓库表';
COMMENT ON COLUMN bas_warehouses.type IS '仓库类型 0-普通仓 1-虚拟仓';

-- 库位表
DROP TABLE IF EXISTS bas_locations CASCADE;
CREATE TABLE bas_locations (
    id BIGSERIAL PRIMARY KEY,
    warehouse_id BIGINT NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100),
    area VARCHAR(50),
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE bas_locations IS '库位表';

-- 客户表
DROP TABLE IF EXISTS bas_customers CASCADE;
CREATE TABLE bas_customers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    short_name VARCHAR(100),
    credit_limit DECIMAL(18,2) DEFAULT 0,
    level SMALLINT DEFAULT 0,
    contact VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(128),
    address VARCHAR(200),
    tax_no VARCHAR(50),
    bank_name VARCHAR(100),
    bank_account VARCHAR(50),
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_cu_code UNIQUE (code)
);
COMMENT ON TABLE bas_customers IS '客户表';
COMMENT ON COLUMN bas_customers.credit_limit IS '信用额度';
COMMENT ON COLUMN bas_customers.level IS '客户等级 0-普通 1-重要 2-VIP';

-- 供应商表
DROP TABLE IF EXISTS bas_suppliers CASCADE;
CREATE TABLE bas_suppliers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    short_name VARCHAR(100),
    contact VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(128),
    address VARCHAR(200),
    tax_no VARCHAR(50),
    bank_name VARCHAR(100),
    bank_account VARCHAR(50),
    payment_terms VARCHAR(100),
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_sp_code UNIQUE (code)
);
COMMENT ON TABLE bas_suppliers IS '供应商表';
COMMENT ON COLUMN bas_suppliers.payment_terms IS '付款条件';

-- 品牌表
DROP TABLE IF EXISTS bas_brands CASCADE;
CREATE TABLE bas_brands (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    logo_url VARCHAR(500),
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE bas_brands IS '品牌表';

-- 结算方式表
DROP TABLE IF EXISTS bas_settlement_methods CASCADE;
CREATE TABLE bas_settlement_methods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL,
    type SMALLINT DEFAULT 0,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_sm_code UNIQUE (code)
);
COMMENT ON TABLE bas_settlement_methods IS '结算方式表';
COMMENT ON COLUMN bas_settlement_methods.type IS '类型 0-现金 1-银行转账 2-承兑汇票';

-- 付款方式表
DROP TABLE IF EXISTS bas_payment_methods CASCADE;
CREATE TABLE bas_payment_methods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL,
    account_id BIGINT,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_pm_code UNIQUE (code)
);
COMMENT ON TABLE bas_payment_methods IS '付款方式表';


-- ============================================
-- 2. 采购管理模块 (pur_)
-- ============================================

-- 采购订单主表
DROP TABLE IF EXISTS pur_purchase_orders CASCADE;
CREATE TABLE pur_purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    order_no VARCHAR(32) NOT NULL,
    supplier_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    order_date DATE NOT NULL,
    expected_date DATE,
    total_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_tax DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_payable DECIMAL(18,4) NOT NULL DEFAULT 0,
    received_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_po_no UNIQUE (order_no)
);
COMMENT ON TABLE pur_purchase_orders IS '采购订单主表';
COMMENT ON COLUMN pur_purchase_orders.status IS '0-草稿 1-待审核 2-已审核 3-部分到货 4-已完成 5-已关闭';

-- 采购订单明细表
DROP TABLE IF EXISTS pur_purchase_order_items CASCADE;
CREATE TABLE pur_purchase_order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    unit_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    received_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    price DECIMAL(18,4) NOT NULL,
    tax_rate DECIMAL(6,4) NOT NULL DEFAULT 0,
    amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    tax_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    remark VARCHAR(200),
    sort INTEGER DEFAULT 0
);
COMMENT ON TABLE pur_purchase_order_items IS '采购订单明细表';
COMMENT ON COLUMN pur_purchase_order_items.price IS '含税单价';

-- 采购入库单主表
DROP TABLE IF EXISTS pur_purchase_receipts CASCADE;
CREATE TABLE pur_purchase_receipts (
    id BIGSERIAL PRIMARY KEY,
    receipt_no VARCHAR(32) NOT NULL,
    order_id BIGINT,
    supplier_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    receipt_date DATE NOT NULL,
    total_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_pr_no UNIQUE (receipt_no)
);
COMMENT ON TABLE pur_purchase_receipts IS '采购入库单主表';
COMMENT ON COLUMN pur_purchase_receipts.status IS '0-草稿 1-待审核 2-已审核 3-已完成';

-- 采购入库单明细表
DROP TABLE IF EXISTS pur_purchase_receipt_items CASCADE;
CREATE TABLE pur_purchase_receipt_items (
    id BIGSERIAL PRIMARY KEY,
    receipt_id BIGINT NOT NULL,
    order_item_id BIGINT,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    production_date DATE,
    expiry_date DATE,
    quantity DECIMAL(18,4) NOT NULL,
    price DECIMAL(18,4) NOT NULL DEFAULT 0,
    amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    remark VARCHAR(200)
);
COMMENT ON TABLE pur_purchase_receipt_items IS '采购入库单明细表';

-- 采购退货单主表
DROP TABLE IF EXISTS pur_purchase_returns CASCADE;
CREATE TABLE pur_purchase_returns (
    id BIGSERIAL PRIMARY KEY,
    return_no VARCHAR(32) NOT NULL,
    receipt_id BIGINT,
    supplier_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    return_date DATE NOT NULL,
    total_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_prt_no UNIQUE (return_no)
);
COMMENT ON TABLE pur_purchase_returns IS '采购退货单主表';
COMMENT ON COLUMN pur_purchase_returns.status IS '0-草稿 1-待审核 2-已审核 3-已完成';

-- 采购退货单明细表
DROP TABLE IF EXISTS pur_purchase_return_items CASCADE;
CREATE TABLE pur_purchase_return_items (
    id BIGSERIAL PRIMARY KEY,
    return_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    quantity DECIMAL(18,4) NOT NULL,
    price DECIMAL(18,4) NOT NULL DEFAULT 0,
    amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    remark VARCHAR(200)
);
COMMENT ON TABLE pur_purchase_return_items IS '采购退货单明细表';

-- 采购付款单
DROP TABLE IF EXISTS pur_purchase_payments CASCADE;
CREATE TABLE pur_purchase_payments (
    id BIGSERIAL PRIMARY KEY,
    payment_no VARCHAR(32) NOT NULL,
    supplier_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    payment_date DATE NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    pay_type SMALLINT DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_pp_no UNIQUE (payment_no)
);
COMMENT ON TABLE pur_purchase_payments IS '采购付款单';
COMMENT ON COLUMN pur_purchase_payments.pay_type IS '付款方式 0-现金 1-转账 2-承兑';

-- 供应商价格表
DROP TABLE IF EXISTS pur_supplier_prices CASCADE;
CREATE TABLE pur_supplier_prices (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price DECIMAL(18,4) NOT NULL,
    effective_date DATE,
    expiry_date DATE,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_pur_sp_supplier_product UNIQUE (supplier_id, product_id)
);
COMMENT ON TABLE pur_supplier_prices IS '供应商价格表';


-- ============================================
-- 3. 销售管理模块 (sal_)
-- ============================================

-- 销售订单主表
DROP TABLE IF EXISTS sal_sales_orders CASCADE;
CREATE TABLE sal_sales_orders (
    id BIGSERIAL PRIMARY KEY,
    order_no VARCHAR(32) NOT NULL,
    customer_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    order_date DATE NOT NULL,
    expected_date DATE,
    total_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_tax DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_receivable DECIMAL(18,4) NOT NULL DEFAULT 0,
    delivered_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sal_so_no UNIQUE (order_no)
);
COMMENT ON TABLE sal_sales_orders IS '销售订单主表';
COMMENT ON COLUMN sal_sales_orders.status IS '0-草稿 1-待审核 2-已审核 3-部分发货 4-已完成 5-已关闭';

-- 销售订单明细表
DROP TABLE IF EXISTS sal_sales_order_items CASCADE;
CREATE TABLE sal_sales_order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    unit_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    delivered_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    price DECIMAL(18,4) NOT NULL,
    tax_rate DECIMAL(6,4) NOT NULL DEFAULT 0,
    amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    tax_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    remark VARCHAR(200),
    sort INTEGER DEFAULT 0
);
COMMENT ON TABLE sal_sales_order_items IS '销售订单明细表';

-- 销售出库单主表
DROP TABLE IF EXISTS sal_sales_deliveries CASCADE;
CREATE TABLE sal_sales_deliveries (
    id BIGSERIAL PRIMARY KEY,
    delivery_no VARCHAR(32) NOT NULL,
    order_id BIGINT,
    customer_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    delivery_date DATE NOT NULL,
    total_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sal_sd_no UNIQUE (delivery_no)
);
COMMENT ON TABLE sal_sales_deliveries IS '销售出库单主表';
COMMENT ON COLUMN sal_sales_deliveries.status IS '0-草稿 1-待审核 2-已审核 3-已完成';

-- 销售出库单明细表
DROP TABLE IF EXISTS sal_sales_delivery_items CASCADE;
CREATE TABLE sal_sales_delivery_items (
    id BIGSERIAL PRIMARY KEY,
    delivery_id BIGINT NOT NULL,
    order_item_id BIGINT,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    quantity DECIMAL(18,4) NOT NULL,
    price DECIMAL(18,4) NOT NULL DEFAULT 0,
    amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    remark VARCHAR(200)
);
COMMENT ON TABLE sal_sales_delivery_items IS '销售出库单明细表';

-- 销售退货单主表
DROP TABLE IF EXISTS sal_sales_returns CASCADE;
CREATE TABLE sal_sales_returns (
    id BIGSERIAL PRIMARY KEY,
    return_no VARCHAR(32) NOT NULL,
    delivery_id BIGINT,
    customer_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    return_date DATE NOT NULL,
    total_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sal_sr_no UNIQUE (return_no)
);
COMMENT ON TABLE sal_sales_returns IS '销售退货单主表';
COMMENT ON COLUMN sal_sales_returns.status IS '0-草稿 1-待审核 2-已审核 3-已完成';

-- 销售退货单明细表
DROP TABLE IF EXISTS sal_sales_return_items CASCADE;
CREATE TABLE sal_sales_return_items (
    id BIGSERIAL PRIMARY KEY,
    return_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    quantity DECIMAL(18,4) NOT NULL,
    price DECIMAL(18,4) NOT NULL DEFAULT 0,
    amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    remark VARCHAR(200)
);
COMMENT ON TABLE sal_sales_return_items IS '销售退货单明细表';

-- 销售收款单
DROP TABLE IF EXISTS sal_sales_receipts CASCADE;
CREATE TABLE sal_sales_receipts (
    id BIGSERIAL PRIMARY KEY,
    receipt_no VARCHAR(32) NOT NULL,
    customer_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    receipt_date DATE NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    pay_type SMALLINT DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_sal_srp_no UNIQUE (receipt_no)
);
COMMENT ON TABLE sal_sales_receipts IS '销售收款单';

-- 价格策略表
DROP TABLE IF EXISTS sal_price_policies CASCADE;
CREATE TABLE sal_price_policies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type SMALLINT NOT NULL DEFAULT 0,
    customer_id BIGINT,
    customer_level SMALLINT,
    product_id BIGINT,
    category_id BIGINT,
    discount_rate DECIMAL(6,4),
    fixed_price DECIMAL(18,4),
    start_date DATE,
    end_date DATE,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sal_price_policies IS '价格策略表';
COMMENT ON COLUMN sal_price_policies.type IS '0-会员价 1-促销价 2-客户专属价';


-- ============================================
-- 4. 库存管理模块 (inv_)
-- ============================================

-- 库存表
DROP TABLE IF EXISTS inv_inventory CASCADE;
CREATE TABLE inv_inventory (
    id BIGSERIAL PRIMARY KEY,
    warehouse_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50) DEFAULT '',
    production_date DATE,
    expiry_date DATE,
    quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    locked_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    available_quantity DECIMAL(18,4) GENERATED ALWAYS AS (quantity - locked_quantity) STORED,
    cost_price DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_cost DECIMAL(18,4) NOT NULL DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_inv_wh_product_batch UNIQUE (warehouse_id, product_id, batch_no)
);
COMMENT ON TABLE inv_inventory IS '库存表';
COMMENT ON COLUMN inv_inventory.available_quantity IS '可用数量 计算列 = quantity - locked_quantity';

-- 库存流水表
DROP TABLE IF EXISTS inv_inventory_transactions CASCADE;
CREATE TABLE inv_inventory_transactions (
    id BIGSERIAL PRIMARY KEY,
    warehouse_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50) DEFAULT '',
    transaction_type SMALLINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    before_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    after_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    cost_price DECIMAL(18,4) NOT NULL DEFAULT 0,
    source_type VARCHAR(32) NOT NULL,
    source_id BIGINT NOT NULL,
    source_no VARCHAR(32),
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operator_id BIGINT,
    remark VARCHAR(500)
);
COMMENT ON TABLE inv_inventory_transactions IS '库存流水表';
COMMENT ON COLUMN inv_inventory_transactions.transaction_type IS '1-采购入库 2-采购退货出库 3-销售出库 4-销售退货入库 5-盘盈入库 6-盘亏出库 7-调拨出库 8-调拨入库 9-组装出库 10-拆卸入库';

-- 盘点单主表
DROP TABLE IF EXISTS inv_stock_takes CASCADE;
CREATE TABLE inv_stock_takes (
    id BIGSERIAL PRIMARY KEY,
    take_no VARCHAR(32) NOT NULL,
    warehouse_id BIGINT NOT NULL,
    take_type SMALLINT NOT NULL DEFAULT 0,
    take_date DATE NOT NULL,
    total_diff_quantity DECIMAL(18,4) DEFAULT 0,
    total_diff_amount DECIMAL(18,4) DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_inv_st_no UNIQUE (take_no)
);
COMMENT ON TABLE inv_stock_takes IS '盘点单主表';
COMMENT ON COLUMN inv_stock_takes.take_type IS '0-全盘 1-抽盘';
COMMENT ON COLUMN inv_stock_takes.status IS '0-草稿 1-盘点中 2-待审核 3-已完成';

-- 盘点单明细表
DROP TABLE IF EXISTS inv_stock_take_items CASCADE;
CREATE TABLE inv_stock_take_items (
    id BIGSERIAL PRIMARY KEY,
    take_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50) DEFAULT '',
    book_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    actual_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    diff_quantity DECIMAL(18,4) GENERATED ALWAYS AS (actual_quantity - book_quantity) STORED,
    cost_price DECIMAL(18,4) NOT NULL DEFAULT 0,
    diff_amount DECIMAL(18,4) GENERATED ALWAYS AS ((actual_quantity - book_quantity) * cost_price) STORED,
    remark VARCHAR(200)
);
COMMENT ON TABLE inv_stock_take_items IS '盘点单明细表';

-- 库存调拨单主表
DROP TABLE IF EXISTS inv_stock_transfers CASCADE;
CREATE TABLE inv_stock_transfers (
    id BIGSERIAL PRIMARY KEY,
    transfer_no VARCHAR(32) NOT NULL,
    from_warehouse_id BIGINT NOT NULL,
    to_warehouse_id BIGINT NOT NULL,
    transfer_date DATE NOT NULL,
    total_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    total_amount DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_inv_stf_no UNIQUE (transfer_no)
);
COMMENT ON TABLE inv_stock_transfers IS '库存调拨单主表';
COMMENT ON COLUMN inv_stock_transfers.status IS '0-草稿 1-待审核 2-已审核 3-已完成';

-- 库存调拨单明细表
DROP TABLE IF EXISTS inv_stock_transfer_items CASCADE;
CREATE TABLE inv_stock_transfer_items (
    id BIGSERIAL PRIMARY KEY,
    transfer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    batch_no VARCHAR(50) DEFAULT '',
    quantity DECIMAL(18,4) NOT NULL,
    cost_price DECIMAL(18,4) NOT NULL DEFAULT 0,
    amount DECIMAL(18,4) NOT NULL DEFAULT 0
);
COMMENT ON TABLE inv_stock_transfer_items IS '库存调拨单明细表';

-- 组装拆卸单主表
DROP TABLE IF EXISTS inv_assemblies CASCADE;
CREATE TABLE inv_assemblies (
    id BIGSERIAL PRIMARY KEY,
    assembly_no VARCHAR(32) NOT NULL,
    type SMALLINT NOT NULL DEFAULT 0,
    warehouse_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_inv_as_no UNIQUE (assembly_no)
);
COMMENT ON TABLE inv_assemblies IS '组装拆卸单主表';
COMMENT ON COLUMN inv_assemblies.type IS '0-组装 1-拆卸';

-- 组装拆卸单组件明细表
DROP TABLE IF EXISTS inv_assembly_items CASCADE;
CREATE TABLE inv_assembly_items (
    id BIGSERIAL PRIMARY KEY,
    assembly_id BIGINT NOT NULL,
    component_product_id BIGINT NOT NULL,
    quantity DECIMAL(18,4) NOT NULL,
    cost_price DECIMAL(18,4) NOT NULL DEFAULT 0
);
COMMENT ON TABLE inv_assembly_items IS '组装拆卸单组件明细表';

-- 库存预警配置表
DROP TABLE IF EXISTS inv_warning_configs CASCADE;
CREATE TABLE inv_warning_configs (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT,
    min_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    max_quantity DECIMAL(18,4) NOT NULL DEFAULT 0,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_inv_wc_product_warehouse UNIQUE (product_id, warehouse_id)
);
COMMENT ON TABLE inv_warning_configs IS '库存预警配置表';


-- ============================================
-- 5. 资金管理模块 (fin_)
-- ============================================

-- 账户表
DROP TABLE IF EXISTS fin_accounts CASCADE;
CREATE TABLE fin_accounts (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    type SMALLINT NOT NULL DEFAULT 0,
    bank_name VARCHAR(100),
    bank_account VARCHAR(50),
    balance DECIMAL(18,2) NOT NULL DEFAULT 0,
    status SMALLINT DEFAULT 1,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_fin_ac_code UNIQUE (code)
);
COMMENT ON TABLE fin_accounts IS '账户表';
COMMENT ON COLUMN fin_accounts.type IS '0-银行账户 1-现金账户';

-- 账户流水表
DROP TABLE IF EXISTS fin_account_transactions CASCADE;
CREATE TABLE fin_account_transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    transaction_type SMALLINT NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    balance_before DECIMAL(18,2) NOT NULL DEFAULT 0,
    balance_after DECIMAL(18,2) NOT NULL DEFAULT 0,
    source_type VARCHAR(32) NOT NULL,
    source_id BIGINT NOT NULL,
    source_no VARCHAR(32),
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operator_id BIGINT,
    remark VARCHAR(500)
);
COMMENT ON TABLE fin_account_transactions IS '账户流水表';
COMMENT ON COLUMN fin_account_transactions.transaction_type IS '1-收入 2-支出';

-- 应收账款表
DROP TABLE IF EXISTS fin_receivables CASCADE;
CREATE TABLE fin_receivables (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    source_type VARCHAR(32) NOT NULL,
    source_id BIGINT NOT NULL,
    source_no VARCHAR(32),
    amount DECIMAL(18,2) NOT NULL,
    received_amount DECIMAL(18,2) NOT NULL DEFAULT 0,
    balance DECIMAL(18,2) NOT NULL,
    due_date DATE,
    status SMALLINT NOT NULL DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE fin_receivables IS '应收账款表';
COMMENT ON COLUMN fin_receivables.status IS '0-未核销 1-部分核销 2-已核销';
COMMENT ON COLUMN fin_receivables.source_type IS '来源类型 sales_delivery-销售出库';

-- 应付账款表
DROP TABLE IF EXISTS fin_payables CASCADE;
CREATE TABLE fin_payables (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    source_type VARCHAR(32) NOT NULL,
    source_id BIGINT NOT NULL,
    source_no VARCHAR(32),
    amount DECIMAL(18,2) NOT NULL,
    paid_amount DECIMAL(18,2) NOT NULL DEFAULT 0,
    balance DECIMAL(18,2) NOT NULL,
    due_date DATE,
    status SMALLINT NOT NULL DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE fin_payables IS '应付账款表';
COMMENT ON COLUMN fin_payables.status IS '0-未核销 1-部分核销 2-已核销';
COMMENT ON COLUMN fin_payables.source_type IS '来源类型 purchase_receipt-采购入库';

-- 收款单主表
DROP TABLE IF EXISTS fin_receipts CASCADE;
CREATE TABLE fin_receipts (
    id BIGSERIAL PRIMARY KEY,
    receipt_no VARCHAR(32) NOT NULL,
    customer_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    receipt_date DATE NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    pay_type SMALLINT DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_fin_rc_no UNIQUE (receipt_no)
);
COMMENT ON TABLE fin_receipts IS '收款单主表';

-- 收款核销明细表
DROP TABLE IF EXISTS fin_receipt_items CASCADE;
CREATE TABLE fin_receipt_items (
    id BIGSERIAL PRIMARY KEY,
    receipt_id BIGINT NOT NULL,
    receivable_id BIGINT NOT NULL,
    amount DECIMAL(18,2) NOT NULL
);
COMMENT ON TABLE fin_receipt_items IS '收款核销明细表';

-- 付款单主表
DROP TABLE IF EXISTS fin_payments CASCADE;
CREATE TABLE fin_payments (
    id BIGSERIAL PRIMARY KEY,
    payment_no VARCHAR(32) NOT NULL,
    supplier_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    payment_date DATE NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    pay_type SMALLINT DEFAULT 0,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_fin_py_no UNIQUE (payment_no)
);
COMMENT ON TABLE fin_payments IS '付款单主表';

-- 付款核销明细表
DROP TABLE IF EXISTS fin_payment_items CASCADE;
CREATE TABLE fin_payment_items (
    id BIGSERIAL PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    payable_id BIGINT NOT NULL,
    amount DECIMAL(18,2) NOT NULL
);
COMMENT ON TABLE fin_payment_items IS '付款核销明细表';

-- 费用支出表
DROP TABLE IF EXISTS fin_expenses CASCADE;
CREATE TABLE fin_expenses (
    id BIGSERIAL PRIMARY KEY,
    expense_no VARCHAR(32) NOT NULL,
    department_id BIGINT,
    category VARCHAR(50),
    amount DECIMAL(18,2) NOT NULL,
    expense_date DATE NOT NULL,
    account_id BIGINT NOT NULL,
    status SMALLINT NOT NULL DEFAULT 0,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_fin_ex_no UNIQUE (expense_no)
);
COMMENT ON TABLE fin_expenses IS '费用支出表';
COMMENT ON COLUMN fin_expenses.status IS '0-草稿 1-待审核 2-已审核';

-- 其他收支表
DROP TABLE IF EXISTS fin_other_incomes CASCADE;
CREATE TABLE fin_other_incomes (
    id BIGSERIAL PRIMARY KEY,
    type SMALLINT NOT NULL DEFAULT 0,
    category VARCHAR(50),
    amount DECIMAL(18,2) NOT NULL,
    occur_date DATE NOT NULL,
    account_id BIGINT NOT NULL,
    status SMALLINT NOT NULL DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE fin_other_incomes IS '其他收支表';
COMMENT ON COLUMN fin_other_incomes.type IS '0-收入 1-支出';


-- ============================================
-- 6. 系统扩展表
-- ============================================

-- 系统配置表
DROP TABLE IF EXISTS sys_system_configs CASCADE;
CREATE TABLE sys_system_configs (
    id BIGSERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL,
    config_value TEXT,
    config_type VARCHAR(50) DEFAULT 'string',
    description VARCHAR(500),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_sys_sc_key UNIQUE (config_key)
);
COMMENT ON TABLE sys_system_configs IS '系统配置表';

-- 数据备份记录表
DROP TABLE IF EXISTS sys_data_backups CASCADE;
CREATE TABLE sys_data_backups (
    id BIGSERIAL PRIMARY KEY,
    backup_name VARCHAR(200) NOT NULL,
    file_path VARCHAR(500),
    file_size BIGINT DEFAULT 0,
    backup_type SMALLINT DEFAULT 0,
    status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_data_backups IS '数据备份记录表';
COMMENT ON COLUMN sys_data_backups.backup_type IS '0-手动 1-自动';


-- ============================================
-- 索引设计
-- ============================================

-- 基础资料索引
CREATE INDEX idx_bas_pd_category ON bas_products(category_id, status);
CREATE INDEX idx_bas_pd_name ON bas_products(name);
CREATE INDEX idx_bas_pd_supplier ON bas_products(default_supplier_id);
CREATE INDEX idx_bas_pc_parent ON bas_product_categories(parent_id);
CREATE INDEX idx_bas_wh_manager ON bas_warehouses(manager_id);
CREATE INDEX idx_bas_lo_warehouse ON bas_locations(warehouse_id);
CREATE INDEX idx_bas_cu_level ON bas_customers(level, status);
CREATE INDEX idx_bas_sp_cu_name ON bas_customers(name);
CREATE INDEX idx_bas_sp_name ON bas_suppliers(name);

-- 采购索引
CREATE INDEX idx_pur_po_supplier_date ON pur_purchase_orders(supplier_id, order_date);
CREATE INDEX idx_pur_po_status ON pur_purchase_orders(status, order_date);
CREATE INDEX idx_pur_poi_order ON pur_purchase_order_items(order_id);
CREATE INDEX idx_pur_pr_order ON pur_purchase_receipts(order_id);
CREATE INDEX idx_pur_pr_status ON pur_purchase_receipts(status);
CREATE INDEX idx_pur_pri_receipt ON pur_purchase_receipt_items(receipt_id);
CREATE INDEX idx_pur_pp_supplier ON pur_purchase_payments(supplier_id);
CREATE INDEX idx_pur_pp_status ON pur_purchase_payments(status);

-- 销售索引
CREATE INDEX idx_sal_so_customer_date ON sal_sales_orders(customer_id, order_date);
CREATE INDEX idx_sal_so_status ON sal_sales_orders(status, order_date);
CREATE INDEX idx_sal_soi_order ON sal_sales_order_items(order_id);
CREATE INDEX idx_sal_sd_order ON sal_sales_deliveries(order_id);
CREATE INDEX idx_sal_sd_status ON sal_sales_deliveries(status);
CREATE INDEX idx_sal_sdi_delivery ON sal_sales_delivery_items(delivery_id);
CREATE INDEX idx_sal_srp_customer ON sal_sales_receipts(customer_id);
CREATE INDEX idx_sal_pp_status ON sal_price_policies(status, start_date, end_date);

-- 库存索引
CREATE INDEX idx_inv_product ON inv_inventory(product_id);
CREATE INDEX idx_inv_warehouse ON inv_inventory(warehouse_id);
CREATE INDEX idx_inv_trans_product_date ON inv_inventory_transactions(product_id, transaction_time);
CREATE INDEX idx_inv_trans_source ON inv_inventory_transactions(source_type, source_id);
CREATE INDEX idx_inv_trans_warehouse ON inv_inventory_transactions(warehouse_id);
CREATE INDEX idx_inv_st_warehouse ON inv_stock_takes(warehouse_id);
CREATE INDEX idx_inv_st_status ON inv_stock_takes(status);
CREATE INDEX idx_inv_sti_take ON inv_stock_take_items(take_id);
CREATE INDEX idx_inv_stf_from ON inv_stock_transfers(from_warehouse_id);
CREATE INDEX idx_inv_stf_to ON inv_stock_transfers(to_warehouse_id);
CREATE INDEX idx_inv_wc_product ON inv_warning_configs(product_id);

-- 资金索引
CREATE INDEX idx_fin_ar_customer_status ON fin_receivables(customer_id, status, due_date);
CREATE INDEX idx_fin_ar_source ON fin_receivables(source_type, source_id);
CREATE INDEX idx_fin_ap_supplier_status ON fin_payables(supplier_id, status, due_date);
CREATE INDEX idx_fin_ap_source ON fin_payables(source_type, source_id);
CREATE INDEX idx_fin_rc_customer ON fin_receipts(customer_id);
CREATE INDEX idx_fin_rc_status ON fin_receipts(status);
CREATE INDEX idx_fin_ri_receipt ON fin_receipt_items(receipt_id);
CREATE INDEX idx_fin_ri_receivable ON fin_receipt_items(receivable_id);
CREATE INDEX idx_fin_py_supplier ON fin_payments(supplier_id);
CREATE INDEX idx_fin_py_status ON fin_payments(status);
CREATE INDEX idx_fin_pi_payment ON fin_payment_items(payment_id);
CREATE INDEX idx_fin_pi_payable ON fin_payment_items(payable_id);
CREATE INDEX idx_fin_at_account ON fin_account_transactions(account_id, transaction_time);
CREATE INDEX idx_fin_at_source ON fin_account_transactions(source_type, source_id);
CREATE INDEX idx_fin_ex_account ON fin_expenses(account_id, expense_date);
