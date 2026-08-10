-- ============================================
-- 进销存管理系统 V2 - 商品辅助属性 (Phase 1)
-- ============================================

-- 属性模板表
DROP TABLE IF EXISTS bas_attribute_template CASCADE;
CREATE TABLE bas_attribute_template (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    category_id BIGINT,
    is_enabled SMALLINT DEFAULT 1,
    sort_order INTEGER DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_at_code UNIQUE (code)
);
COMMENT ON TABLE bas_attribute_template IS '属性模板表';
COMMENT ON COLUMN bas_attribute_template.category_id IS '关联商品分类ID，为空表示全局模板';
COMMENT ON COLUMN bas_attribute_template.is_enabled IS '启用状态 0-停用 1-启用';

-- 属性定义表
DROP TABLE IF EXISTS bas_attribute CASCADE;
CREATE TABLE bas_attribute (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    template_id BIGINT NOT NULL,
    value_type VARCHAR(20) NOT NULL DEFAULT 'SELECT',
    is_required SMALLINT DEFAULT 0,
    is_sku SMALLINT DEFAULT 0,
    sort_order INTEGER DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_bas_a_code UNIQUE (code)
);
COMMENT ON TABLE bas_attribute IS '属性定义表';
COMMENT ON COLUMN bas_attribute.template_id IS '所属属性模板ID';
COMMENT ON COLUMN bas_attribute.value_type IS '属性类型: SELECT-下拉选择, INPUT-文本输入, NUMBER-数字, DATE-日期, BOOL-布尔';
COMMENT ON COLUMN bas_attribute.is_sku IS '是否用于SKU生成 0-否 1-是';

-- 属性预设值表
DROP TABLE IF EXISTS bas_attribute_value CASCADE;
CREATE TABLE bas_attribute_value (
    id BIGSERIAL PRIMARY KEY,
    attribute_id BIGINT NOT NULL,
    value VARCHAR(200) NOT NULL,
    sort_order INTEGER DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE bas_attribute_value IS '属性预设值表';

-- 商品属性值关联表
DROP TABLE IF EXISTS bas_product_attribute CASCADE;
CREATE TABLE bas_product_attribute (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    attribute_value_id BIGINT,
    manual_value VARCHAR(500),
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE bas_product_attribute IS '商品属性值关联表';
COMMENT ON COLUMN bas_product_attribute.attribute_value_id IS '预设属性值ID，为null时使用manual_value';
COMMENT ON COLUMN bas_product_attribute.manual_value IS '手动输入值，value_type为INPUT/NUMBER时使用';

-- 索引
CREATE INDEX idx_bas_at_category ON bas_attribute_template(category_id);
CREATE INDEX idx_bas_a_template ON bas_attribute(template_id);
CREATE INDEX idx_bas_av_attribute ON bas_attribute_value(attribute_id);
CREATE INDEX idx_bas_pa_product ON bas_product_attribute(product_id);
CREATE INDEX idx_bas_pa_attribute ON bas_product_attribute(attribute_id);
