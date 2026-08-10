-- ============================================
-- 进销存管理系统 V2 - 协同与移动端 (Phase 8)
-- ============================================

-- 门户用户
DROP TABLE IF EXISTS portal_user CASCADE;
CREATE TABLE portal_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(200) NOT NULL,
    portal_type VARCHAR(20) NOT NULL,
    customer_id BIGINT,
    supplier_id BIGINT,
    contact_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    status SMALLINT DEFAULT 1,
    last_login_time TIMESTAMP,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    CONSTRAINT uk_portal_user_username UNIQUE (username)
);
COMMENT ON TABLE portal_user IS '门户用户';
COMMENT ON COLUMN portal_user.portal_type IS '门户类型: CUSTOMER-客户, SUPPLIER-供应商';
