-- ============================================
-- Java Serve Admin - PostgreSQL 初始化脚本
-- 完整 RBAC 权限管理系统（14 张表）
-- ============================================

-- 系统用户表
DROP TABLE IF EXISTS sys_user CASCADE;
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(64) NOT NULL,
    password VARCHAR(128) NOT NULL,
    nick_name VARCHAR(64),
    real_name VARCHAR(64),
    avatar VARCHAR(256),
    email VARCHAR(128),
    phone VARCHAR(20),
    sex SMALLINT DEFAULT 0,
    age INTEGER,
    address VARCHAR(256),
    status SMALLINT DEFAULT 0,
    login_ip VARCHAR(64),
    login_address VARCHAR(128),
    login_info VARCHAR(256),
    login_time VARCHAR(32),
    pwd_update_time VARCHAR(32),
    dept_id BIGINT,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(512),
    CONSTRAINT uk_sys_user_name UNIQUE (user_name)
);
COMMENT ON TABLE sys_user IS '系统用户表';
COMMENT ON COLUMN sys_user.id IS '用户ID';
COMMENT ON COLUMN sys_user.user_name IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.nick_name IS '昵称';
COMMENT ON COLUMN sys_user.real_name IS '真实姓名';
COMMENT ON COLUMN sys_user.avatar IS '头像';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.sex IS '性别 0-男 1-女';
COMMENT ON COLUMN sys_user.status IS '状态 0-正常 1-停用';
COMMENT ON COLUMN sys_user.deleted IS '逻辑删除 0-未删除 1-已删除';

-- 系统角色表
DROP TABLE IF EXISTS sys_role CASCADE;
CREATE TABLE sys_role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    code VARCHAR(64) NOT NULL,
    sort INTEGER DEFAULT 0,
    permissions TEXT,
    data_scope SMALLINT DEFAULT 1,
    status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(512),
    CONSTRAINT uk_sys_role_code UNIQUE (code)
);
COMMENT ON TABLE sys_role IS '系统角色表';
COMMENT ON COLUMN sys_role.data_scope IS '数据范围 1-全部 2-自定义 3-本部门 4-本部门及下级 5-仅本人';

-- 系统菜单表
DROP TABLE IF EXISTS sys_menu CASCADE;
CREATE TABLE sys_menu (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(64) NOT NULL,
    sort INTEGER DEFAULT 0,
    path VARCHAR(256),
    component VARCHAR(256),
    icon VARCHAR(64),
    title VARCHAR(64),
    type CHAR(1) DEFAULT 'C',
    auth VARCHAR(128),
    is_link SMALLINT DEFAULT 0,
    is_iframe SMALLINT DEFAULT 0,
    address VARCHAR(256),
    is_hide SMALLINT DEFAULT 0,
    is_hide_sub_menu SMALLINT DEFAULT 0,
    is_mobile SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(512)
);
COMMENT ON TABLE sys_menu IS '系统菜单表';
COMMENT ON COLUMN sys_menu.type IS '菜单类型 M-目录 C-菜单 F-按钮';
COMMENT ON COLUMN sys_menu.auth IS '权限标识';

-- 系统部门表
DROP TABLE IF EXISTS sys_department CASCADE;
CREATE TABLE sys_department (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    ancestors VARCHAR(512) DEFAULT '',
    name VARCHAR(64) NOT NULL,
    sort INTEGER DEFAULT 0,
    leader VARCHAR(64),
    phone VARCHAR(20),
    email VARCHAR(128),
    status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(512)
);
COMMENT ON TABLE sys_department IS '系统部门表';
COMMENT ON COLUMN sys_department.ancestors IS '祖级列表';

-- 系统岗位表
DROP TABLE IF EXISTS sys_post CASCADE;
CREATE TABLE sys_post (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    sort INTEGER DEFAULT 0,
    status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(512)
);
COMMENT ON TABLE sys_post IS '系统岗位表';

-- 系统字典表
DROP TABLE IF EXISTS sys_dictionary CASCADE;
CREATE TABLE sys_dictionary (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    value TEXT,
    status SMALLINT DEFAULT 0,
    deleted SMALLINT DEFAULT 0,
    create_by VARCHAR(64),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(64),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(512),
    CONSTRAINT uk_sys_dict_name UNIQUE (name)
);
COMMENT ON TABLE sys_dictionary IS '系统字典表';
COMMENT ON COLUMN sys_dictionary.value IS '字典值（JSON格式）';

-- 用户角色关联表
DROP TABLE IF EXISTS sys_user_role CASCADE;
CREATE TABLE sys_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL
);
CREATE INDEX idx_ur_user_id ON sys_user_role(user_id);
CREATE INDEX idx_ur_role_id ON sys_user_role(role_id);
COMMENT ON TABLE sys_user_role IS '用户角色关联表';

-- 角色菜单关联表
DROP TABLE IF EXISTS sys_role_menu CASCADE;
CREATE TABLE sys_role_menu (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL
);
CREATE INDEX idx_rm_role_id ON sys_role_menu(role_id);
CREATE INDEX idx_rm_menu_id ON sys_role_menu(menu_id);
COMMENT ON TABLE sys_role_menu IS '角色菜单关联表';

-- 用户部门关联表
DROP TABLE IF EXISTS sys_user_department CASCADE;
CREATE TABLE sys_user_department (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    CONSTRAINT uk_ud_user_id UNIQUE (user_id)
);
CREATE INDEX idx_ud_dept_id ON sys_user_department(department_id);
COMMENT ON TABLE sys_user_department IS '用户部门关联表';

-- 用户岗位关联表
DROP TABLE IF EXISTS sys_user_post CASCADE;
CREATE TABLE sys_user_post (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL
);
CREATE INDEX idx_up_user_id ON sys_user_post(user_id);
CREATE INDEX idx_up_post_id ON sys_user_post(post_id);
COMMENT ON TABLE sys_user_post IS '用户岗位关联表';

-- 角色部门关联表（自定义数据权限）
DROP TABLE IF EXISTS sys_role_department CASCADE;
CREATE TABLE sys_role_department (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL
);
CREATE INDEX idx_rd_role_id ON sys_role_department(role_id);
CREATE INDEX idx_rd_dept_id ON sys_role_department(department_id);
COMMENT ON TABLE sys_role_department IS '角色部门关联表';

-- 登录日志表
DROP TABLE IF EXISTS log_login CASCADE;
CREATE TABLE log_login (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    user_name VARCHAR(64),
    ip VARCHAR(64),
    ip_real VARCHAR(64),
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    address VARCHAR(128),
    system VARCHAR(128),
    status SMALLINT DEFAULT 0,
    message VARCHAR(256),
    exception_msg TEXT
);
CREATE INDEX idx_ll_user_id ON log_login(user_id);
CREATE INDEX idx_ll_login_time ON log_login(login_time);
COMMENT ON TABLE log_login IS '登录日志表';

-- 操作日志表
DROP TABLE IF EXISTS log_operation CASCADE;
CREATE TABLE log_operation (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(128),
    business_type INTEGER DEFAULT 0,
    user_id BIGINT,
    user_name VARCHAR(64),
    ip VARCHAR(64),
    ip_real VARCHAR(64),
    oper_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    address VARCHAR(128),
    system VARCHAR(128),
    status SMALLINT DEFAULT 0,
    url VARCHAR(256),
    method VARCHAR(64),
    method_type VARCHAR(16),
    message VARCHAR(512),
    exception_msg TEXT,
    params TEXT,
    result TEXT,
    cost_time BIGINT DEFAULT 0
);
CREATE INDEX idx_lo_user_id ON log_operation(user_id);
CREATE INDEX idx_lo_oper_time ON log_operation(oper_time);
COMMENT ON TABLE log_operation IS '操作日志表';

-- ============================================
-- 初始化数据
-- ============================================

-- 超级管理员（密码：123456 -> BCrypt）
INSERT INTO sys_user (id, user_name, password, nick_name, real_name, status, create_by, remark)
VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', '超级管理员', '管理员', 0, 'system', '系统内置超级管理员')
ON CONFLICT (id) DO NOTHING;

-- 部门数据
INSERT INTO sys_department (id, parent_id, ancestors, name, sort, status, create_by, remark) VALUES
(1, 0, '0', '总部', 0, 0, 'system', '公司总部'),
(2, 1, '0,1', '研发部', 1, 0, 'system', '研发部门'),
(3, 1, '0,1', '运营部', 2, 0, 'system', '运营部门')
ON CONFLICT (id) DO NOTHING;

-- 用户部门关联
INSERT INTO sys_user_department (id, user_id, department_id) VALUES (1, 1, 1)
ON CONFLICT (id) DO NOTHING;

-- 角色数据
INSERT INTO sys_role (id, name, code, sort, data_scope, status, create_by, remark) VALUES
(1, '超级管理员', 'admin', 0, 1, 0, 'system', '系统内置超级管理员角色'),
(2, '普通角色', 'common', 1, 5, 0, 'system', '普通用户角色')
ON CONFLICT (id) DO NOTHING;

-- 用户角色关联
INSERT INTO sys_user_role (id, user_id, role_id) VALUES (1, 1, 1)
ON CONFLICT (id) DO NOTHING;

-- 岗位数据
INSERT INTO sys_post (id, code, name, sort, status, create_by, remark) VALUES
(1, 'ceo', '董事长', 0, 0, 'system', 'CEO'),
(2, 'cto', '技术总监', 1, 0, 'system', 'CTO'),
(3, 'dev', '开发工程师', 2, 0, 'system', '开发人员')
ON CONFLICT (id) DO NOTHING;

-- ============================================
-- 菜单数据（41 条）
-- ============================================

-- 一级目录
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(1, 0, 'system', 1, '/system', 'Layout', 'Ele-Setting', '系统管理', 'M', '', 0, 'system', '系统管理目录'),
(2, 0, 'monitor', 2, '/monitor', 'Layout', 'Ele-Monitor', '系统监控', 'M', '', 0, 'system', '系统监控目录')
ON CONFLICT (id) DO NOTHING;

-- 系统管理 > 用户管理
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(10, 1, 'sysUser', 1, 'sys-user', 'system/user/index', 'Ele-User', '用户管理', 'C', 'sys:user:list', 0, 'system', '用户管理菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(100, 10, '', 1, '用户查询', 'F', 'sys:user:query', 'system'),
(101, 10, '', 2, '用户新增', 'F', 'sys:user:add', 'system'),
(102, 10, '', 3, '用户修改', 'F', 'sys:user:edit', 'system'),
(103, 10, '', 4, '用户删除', 'F', 'sys:user:remove', 'system'),
(104, 10, '', 5, '重置密码', 'F', 'sys:user:resetPwd', 'system')
ON CONFLICT (id) DO NOTHING;

-- 系统管理 > 角色管理
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(11, 1, 'sysRole', 2, 'sys-role', 'system/role/index', 'Ele-Lock', '角色管理', 'C', 'sys:role:list', 0, 'system', '角色管理菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(110, 11, '', 1, '角色查询', 'F', 'sys:role:query', 'system'),
(111, 11, '', 2, '角色新增', 'F', 'sys:role:add', 'system'),
(112, 11, '', 3, '角色修改', 'F', 'sys:role:edit', 'system'),
(113, 11, '', 4, '角色删除', 'F', 'sys:role:remove', 'system')
ON CONFLICT (id) DO NOTHING;

-- 系统管理 > 菜单管理
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(12, 1, 'sysMenu', 3, 'sys-menu', 'system/menu/index', 'Ele-Menu', '菜单管理', 'C', 'sys:menu:list', 0, 'system', '菜单管理菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(120, 12, '', 1, '菜单查询', 'F', 'sys:menu:query', 'system'),
(121, 12, '', 2, '菜单新增', 'F', 'sys:menu:add', 'system'),
(122, 12, '', 3, '菜单修改', 'F', 'sys:menu:edit', 'system'),
(123, 12, '', 4, '菜单删除', 'F', 'sys:menu:remove', 'system')
ON CONFLICT (id) DO NOTHING;

-- 系统管理 > 部门管理
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(13, 1, 'sysDept', 4, 'sys-dept', 'system/dept/index', 'Ele-OfficeBuilding', '部门管理', 'C', 'sys:dept:list', 0, 'system', '部门管理菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(130, 13, '', 1, '部门查询', 'F', 'sys:dept:query', 'system'),
(131, 13, '', 2, '部门新增', 'F', 'sys:dept:add', 'system'),
(132, 13, '', 3, '部门修改', 'F', 'sys:dept:edit', 'system'),
(133, 13, '', 4, '部门删除', 'F', 'sys:dept:remove', 'system')
ON CONFLICT (id) DO NOTHING;

-- 系统管理 > 岗位管理
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(14, 1, 'sysPost', 5, 'sys-post', 'system/post/index', 'Ele-Postcard', '岗位管理', 'C', 'sys:post:list', 0, 'system', '岗位管理菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(140, 14, '', 1, '岗位查询', 'F', 'sys:post:query', 'system'),
(141, 14, '', 2, '岗位新增', 'F', 'sys:post:add', 'system'),
(142, 14, '', 3, '岗位修改', 'F', 'sys:post:edit', 'system'),
(143, 14, '', 4, '岗位删除', 'F', 'sys:post:remove', 'system')
ON CONFLICT (id) DO NOTHING;

-- 系统管理 > 字典管理
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(15, 1, 'sysDict', 6, 'sys-dict', 'system/dict/index', 'Ele-Collection', '字典管理', 'C', 'sys:dict:list', 0, 'system', '字典管理菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(150, 15, '', 1, '字典查询', 'F', 'sys:dict:query', 'system'),
(151, 15, '', 2, '字典新增', 'F', 'sys:dict:add', 'system'),
(152, 15, '', 3, '字典修改', 'F', 'sys:dict:edit', 'system'),
(153, 15, '', 4, '字典删除', 'F', 'sys:dict:remove', 'system')
ON CONFLICT (id) DO NOTHING;

-- 系统监控 > 登录日志
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(20, 2, 'loginLog', 1, 'login-log', 'monitor/loginLog/index', 'Ele-Document', '登录日志', 'C', 'monitor:loginLog:list', 0, 'system', '登录日志菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(200, 20, '', 1, '日志查询', 'F', 'monitor:loginLog:query', 'system'),
(201, 20, '', 2, '日志删除', 'F', 'monitor:loginLog:remove', 'system'),
(202, 20, '', 3, '日志清空', 'F', 'monitor:loginLog:clean', 'system')
ON CONFLICT (id) DO NOTHING;

-- 系统监控 > 操作日志
INSERT INTO sys_menu (id, parent_id, name, sort, path, component, icon, title, type, auth, is_hide, create_by, remark) VALUES
(21, 2, 'operLog', 2, 'oper-log', 'monitor/operLog/index', 'Ele-EditPen', '操作日志', 'C', 'monitor:operLog:list', 0, 'system', '操作日志菜单')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_menu (id, parent_id, name, sort, title, type, auth, create_by) VALUES
(210, 21, '', 1, '日志查询', 'F', 'monitor:operLog:query', 'system'),
(211, 21, '', 2, '日志删除', 'F', 'monitor:operLog:remove', 'system'),
(212, 21, '', 3, '日志清空', 'F', 'monitor:operLog:clean', 'system')
ON CONFLICT (id) DO NOTHING;

-- ============================================
-- 角色菜单关联（admin 角色关联全部菜单）
-- ============================================
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2),
(1, 10), (1, 100), (1, 101), (1, 102), (1, 103), (1, 104),
(1, 11), (1, 110), (1, 111), (1, 112), (1, 113),
(1, 12), (1, 120), (1, 121), (1, 122), (1, 123),
(1, 13), (1, 130), (1, 131), (1, 132), (1, 133),
(1, 14), (1, 140), (1, 141), (1, 142), (1, 143),
(1, 15), (1, 150), (1, 151), (1, 152), (1, 153),
(1, 20), (1, 200), (1, 201), (1, 202),
(1, 21), (1, 210), (1, 211), (1, 212)
ON CONFLICT (id) DO NOTHING;

-- 重置序列（因为手动插入了 id）
SELECT setval('sys_user_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_user));
SELECT setval('sys_role_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_role));
SELECT setval('sys_menu_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_menu));
SELECT setval('sys_department_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_department));
SELECT setval('sys_post_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_post));
SELECT setval('sys_user_role_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_user_role));
SELECT setval('sys_user_department_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_user_department));
SELECT setval('sys_role_menu_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sys_role_menu));
