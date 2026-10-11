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

-- ============================================================
-- 审批流增强字段（会签/或签/条件分支/抄送）
-- ============================================================

-- sys_approval_node 增加节点类型和分支配置
ALTER TABLE sys_approval_node ADD COLUMN IF NOT EXISTS node_type VARCHAR(20) DEFAULT 'SEQUENTIAL';
COMMENT ON COLUMN sys_approval_node.node_type IS '节点类型: SEQUENTIAL(顺序) PARALLEL_AND(会签) PARALLEL_OR(或签) CONDITIONAL(条件) CC(抄送)';

ALTER TABLE sys_approval_node ADD COLUMN IF NOT EXISTS required_count INTEGER DEFAULT 1;
COMMENT ON COLUMN sys_approval_node.required_count IS '会签/或签所需审批人数';

ALTER TABLE sys_approval_node ADD COLUMN IF NOT EXISTS true_next_order INTEGER;
COMMENT ON COLUMN sys_approval_node.true_next_order IS '条件为真时跳转的节点顺序';

ALTER TABLE sys_approval_node ADD COLUMN IF NOT EXISTS false_next_order INTEGER;
COMMENT ON COLUMN sys_approval_node.false_next_order IS '条件为假时跳转的节点顺序';

-- sys_approval_instance 增加表单数据和回调类型
ALTER TABLE sys_approval_instance ADD COLUMN IF NOT EXISTS form_data TEXT;
COMMENT ON COLUMN sys_approval_instance.form_data IS '表单数据(JSON)，用于条件表达式求值';

ALTER TABLE sys_approval_instance ADD COLUMN IF NOT EXISTS callback_type VARCHAR(50);
COMMENT ON COLUMN sys_approval_instance.callback_type IS '业务回调类型';

-- 新增审批抄送记录表
CREATE TABLE IF NOT EXISTS sys_approval_cc (
    id BIGSERIAL PRIMARY KEY,
    instance_id BIGINT NOT NULL,
    node_id BIGINT NOT NULL,
    cc_user_id BIGINT NOT NULL,
    cc_user_name VARCHAR(100),
    cc_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_read SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_approval_cc IS '审批抄送记录';
CREATE INDEX IF NOT EXISTS idx_sys_acc_instance ON sys_approval_cc(instance_id);
CREATE INDEX IF NOT EXISTS idx_sys_acc_user ON sys_approval_cc(cc_user_id);
