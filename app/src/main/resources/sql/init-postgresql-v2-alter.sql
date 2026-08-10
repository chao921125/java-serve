-- ============================================================
-- V2 现有表字段修改脚本
-- 对 4 张现有表增加新字段
-- ============================================================

-- 1. sal_sales_orders 增加挂单标记
ALTER TABLE sal_sales_orders ADD COLUMN IF NOT EXISTS is_suspended SMALLINT DEFAULT 0;
COMMENT ON COLUMN sal_sales_orders.is_suspended IS '是否挂单 0-否 1-是';

-- 2. bas_products 增加成本核算方法
ALTER TABLE bas_products ADD COLUMN IF NOT EXISTS costing_method VARCHAR(20) DEFAULT 'WEIGHTED_AVG';
COMMENT ON COLUMN bas_products.costing_method IS '成本核算方法: WEIGHTED_AVG(移动加权平均), FIFO(先进先出), SPECIFIC(个别计价)';

-- 3. inv_warning_configs 增加保质期预警配置
ALTER TABLE inv_warning_configs ADD COLUMN IF NOT EXISTS expiry_warning_days INT DEFAULT 30;
COMMENT ON COLUMN inv_warning_configs.expiry_warning_days IS '保质期预警提前天数';

ALTER TABLE inv_warning_configs ADD COLUMN IF NOT EXISTS expiry_warning_enabled SMALLINT DEFAULT 0;
COMMENT ON COLUMN inv_warning_configs.expiry_warning_enabled IS '是否启用保质期预警 0-否 1-是';

-- 4. bas_warehouses 增加仓库层级字段
ALTER TABLE bas_warehouses ADD COLUMN IF NOT EXISTS warehouse_type VARCHAR(20) DEFAULT 'MAIN';
COMMENT ON COLUMN bas_warehouses.warehouse_type IS '仓库类型: MAIN(总仓), STORE(门店仓), REGIONAL(区域仓)';

ALTER TABLE bas_warehouses ADD COLUMN IF NOT EXISTS parent_warehouse_id BIGINT;
COMMENT ON COLUMN bas_warehouses.parent_warehouse_id IS '上级仓库ID';
