# Java Serve 进销存系统 - 完整开发设计文档

> 版本：v2.0 | 日期：2026-08-07 | 基于 Spring Boot 3.3.1 + Java 21 + MyBatis-Plus 3.5.15 + PostgreSQL

---

## 目录

1. [Phase 1: 基础资料扩展 — 商品辅助属性](#phase-1-基础资料扩展--商品辅助属性)
2. [Phase 2: 采购管理增强](#phase-2-采购管理增强)
3. [Phase 3: 销售管理增强](#phase-3-销售管理增强)
4. [Phase 4: 库存管理增强](#phase-4-库存管理增强)
5. [Phase 5: 资金财务增强](#phase-5-资金财务增强)
6. [Phase 6: 报表与数据分析](#phase-6-报表与数据分析)
7. [Phase 7: 系统管理增强](#phase-7-系统管理增强)
8. [Phase 8: 协同与移动端](#phase-8-协同与移动端)
9. [Phase 9: 扩展能力](#phase-9-扩展能力)

---

## Phase 1: 基础资料扩展 — 商品辅助属性

### 1.1 概述

面向管道/管材行业的商品辅助属性管理，支持自定义属性模板、属性值、以及商品与属性值的灵活关联。采用 EAV（实体-属性-值）扩展模型，确保高扩展性。

### 1.2 数据表设计

#### 表1: `bas_attribute_template` — 属性模板

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| name | VARCHAR(100) | ✓ | 模板名称，如"管道属性"、"阀门属性" |
| code | VARCHAR(50) | ✓ | 模板编码，唯一 |
| category_id | BIGINT | | 关联商品分类ID（null=全局模板） |
| is_enabled | SMALLINT | ✓ | 启用状态 0-停用 1-启用，默认1 |
| sort_order | INT | | 排序号，默认0 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除，默认0 |
| create_by | VARCHAR(64) | | 创建人 |
| create_time | TIMESTAMP | | 创建时间 |
| update_by | VARCHAR(64) | | 更新人 |
| update_time | TIMESTAMP | | 更新时间 |

**索引：** UNIQUE(code)，INDEX(category_id)

#### 表2: `bas_attribute` — 属性定义

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| template_id | BIGINT | ✓ | 关联属性模板ID |
| name | VARCHAR(100) | ✓ | 属性名称，如"口径"、"壁厚"、"材质" |
| code | VARCHAR(50) | ✓ | 属性编码 |
| value_type | VARCHAR(20) | ✓ | 值类型: SELECT(单选)/MULTI_SELECT(多选)/INPUT(文本)/NUMBER(数字) |
| is_required | SMALLINT | ✓ | 是否必填 0-否 1-是，默认0 |
| is_searchable | SMALLINT | ✓ | 是否可搜索 0-否 1-是，默认0 |
| is_sku_generate | SMALLINT | ✓ | 是否生成SKU 0-否 1-是，默认0 |
| sort_order | INT | | 排序号，默认0 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by | VARCHAR(64) | | 创建人 |
| create_time | TIMESTAMP | | 创建时间 |
| update_by | VARCHAR(64) | | 更新人 |
| update_time | TIMESTAMP | | 更新时间 |

**索引：** INDEX(template_id)，UNIQUE(template_id, code)

#### 表3: `bas_attribute_value` — 属性值（预设可选值）

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| attribute_id | BIGINT | ✓ | 关联属性ID |
| value | VARCHAR(200) | ✓ | 属性值，如"DN50"、"4.0mm"、"304不锈钢" |
| sort_order | INT | | 排序号，默认0 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by | VARCHAR(64) | | 创建人 |
| create_time | TIMESTAMP | | 创建时间 |
| update_by | VARCHAR(64) | | 更新人 |
| update_time | TIMESTAMP | | 更新时间 |

**索引：** INDEX(attribute_id)

#### 表4: `bas_product_attribute` — 商品属性值关联

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| product_id | BIGINT | ✓ | 商品ID |
| attribute_id | BIGINT | ✓ | 属性ID |
| attribute_value_id | BIGINT | | 预设属性值ID（为null时使用manual_value） |
| manual_value | VARCHAR(500) | | 手动输入值（value_type=INPUT/NUMBER 时使用） |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by | VARCHAR(64) | | 创建人 |
| create_time | TIMESTAMP | | 创建时间 |
| update_by | VARCHAR(64) | | 更新人 |
| update_time | TIMESTAMP | | 更新时间 |

**索引：** UNIQUE(product_id, attribute_id)，INDEX(attribute_id)

### 1.3 管道行业预设属性模板

系统初始化时创建"管道管材"属性模板，预设以下属性：

| 属性名称 | 编码 | 值类型 | 预设值示例 |
|---------|------|--------|-----------|
| 公称口径 | nominal_diameter | SELECT | DN15, DN20, DN25, DN32, DN40, DN50, DN65, DN80, DN100, DN125, DN150, DN200, DN250, DN300, DN350, DN400, DN450, DN500, DN600 |
| 外径 | outer_diameter | INPUT | 如 21.3mm, 26.9mm, 33.7mm, 48.3mm, 60.3mm |
| 壁厚等级 | wall_thickness_grade | SELECT | SCH5S, SCH10S, SCH20, SCH30, SCH40, SCH40S, SCH80, SCH80S, SCH160, XXS |
| 壁厚 | wall_thickness | NUMBER | 如 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0 (单位mm) |
| 材质 | material | SELECT | 20#钢, Q235B, Q345B, 16Mn, 304不锈钢, 304L不锈钢, 316不锈钢, 316L不锈钢, 321不锈钢, 双相钢2205, 碳钢A106, 合金钢15CrMo, PVC, PPR, PE, HDPE |
| 压力等级 | pressure_rating | SELECT | PN6, PN10, PN16, PN25, PN40, PN64, PN100, PN160, Class150, Class300, Class600, Class900, Class1500 |
| 连接方式 | connection_type | SELECT | 焊接, 法兰连接, 螺纹连接, 卡箍连接, 承插连接, 热熔连接, 电熔连接, 沟槽连接 |
| 执行标准 | standard | SELECT | GB/T 3091, GB/T 8163, GB/T 14976, GB/T 12771, ASTM A312, ASTM A106, API 5L, DIN 2448, JIS G3459 |
| 表面处理 | surface_treatment | SELECT | 镀锌, 喷漆, 酸洗, 抛光, 喷砂, 3PE防腐, 环氧涂层, 无处理 |
| 长度 | length | INPUT | 如 6米, 12米, 定尺 |
| 管端形式 | end_type | SELECT | 平端, 坡口端, 螺纹端, 法兰端, 承口端 |

### 1.4 API 设计

```
# 属性模板
GET    /api/v1/attribute-templates          — 分页查询
GET    /api/v1/attribute-templates/{id}     — 查询详情
POST   /api/v1/attribute-templates          — 新增
PUT    /api/v1/attribute-templates/{id}     — 修改
DELETE /api/v1/attribute-templates/{id}     — 删除
GET    /api/v1/attribute-templates/all      — 全部列表（下拉选择）

# 属性定义
GET    /api/v1/attribute-templates/{templateId}/attributes       — 模板下属性列表
GET    /api/v1/attributes/{id}                                    — 属性详情
POST   /api/v1/attribute-templates/{templateId}/attributes       — 新增属性
PUT    /api/v1/attributes/{id}                                    — 修改属性
DELETE /api/v1/attributes/{id}                                    — 删除属性

# 属性值
GET    /api/v1/attributes/{attributeId}/values                    — 属性值列表
POST   /api/v1/attributes/{attributeId}/values                    — 新增属性值
PUT    /api/v1/attribute-values/{id}                              — 修改属性值
DELETE /api/v1/attribute-values/{id}                              — 删除属性值

# 商品属性关联
GET    /api/v1/products/{productId}/attributes                    — 查询商品属性
PUT    /api/v1/products/{productId}/attributes                    — 批量设置商品属性
DELETE /api/v1/products/{productId}/attributes/{attrId}           — 删除单个属性关联
```

---

## Phase 2: 采购管理增强

### 2.1 请购单

#### 表5: `pur_purchase_requisition` — 采购请购单

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| requisition_no | VARCHAR(50) | ✓ | 请购单号 |
| requisition_date | DATE | ✓ | 请购日期 |
| department_id | BIGINT | | 申请部门 |
| requisitioner_id | BIGINT | ✓ | 请购人ID |
| status | SMALLINT | ✓ | 状态: 0-草稿 1-待审核 2-已审核 3-已转采购 4-已关闭 |
| expected_date | DATE | | 期望到货日期 |
| urgent_level | SMALLINT | | 紧急程度: 0-普通 1-紧急 2-特急 |
| total_amount | DECIMAL(18,2) | | 预计金额 |
| approver_id | BIGINT | | 审核人ID |
| approve_time | TIMESTAMP | | 审核时间 |
| approve_comment | VARCHAR(500) | | 审核意见 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by | VARCHAR(64) | | 创建人 |
| create_time | TIMESTAMP | | 创建时间 |
| update_by | VARCHAR(64) | | 更新人 |
| update_time | TIMESTAMP | | 更新时间 |

**索引：** UNIQUE(requisition_no)，INDEX(status)，INDEX(requisitioner_id)，INDEX(requisition_date)

#### 表6: `pur_purchase_requisition_item` — 请购单明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| requisition_id | BIGINT | ✓ | 请购单ID |
| product_id | BIGINT | ✓ | 商品ID |
| quantity | DECIMAL(18,4) | ✓ | 请购数量 |
| estimated_unit_price | DECIMAL(18,2) | | 预估单价 |
| estimated_total | DECIMAL(18,2) | | 预估金额 |
| purpose | VARCHAR(200) | | 用途说明 |
| source_order_type | VARCHAR(50) | | 来源单据类型（null=手动,MPS=生产计划,SALES=销售订单,SAFETY=安全库存） |
| source_order_id | BIGINT | | 来源单据ID |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by | VARCHAR(64) | | 创建人 |
| create_time | TIMESTAMP | | 创建时间 |
| update_by | VARCHAR(64) | | 更新人 |
| update_time | TIMESTAMP | | 更新时间 |

**索引：** INDEX(requisition_id)，INDEX(product_id)

### 2.2 采购询比价

#### 表7: `pur_inquiry` — 采购询价单

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| inquiry_no | VARCHAR(50) | ✓ | 询价单号 |
| inquiry_date | DATE | ✓ | 询价日期 |
| status | SMALLINT | ✓ | 状态: 0-草稿 1-已发出 2-已截止 3-已定价 |
| deadline_date | DATE | | 报价截止日期 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(inquiry_no)，INDEX(status)

#### 表8: `pur_inquiry_item` — 询价单明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| inquiry_id | BIGINT | ✓ | 询价单ID |
| product_id | BIGINT | ✓ | 商品ID |
| quantity | DECIMAL(18,4) | ✓ | 数量 |
| specification | VARCHAR(200) | | 规格要求 |
| delivery_requirement | VARCHAR(200) | | 交货要求 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(inquiry_id)

#### 表9: `pur_inquiry_supplier` — 询价供应商

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| inquiry_id | BIGINT | ✓ | 询价单ID |
| supplier_id | BIGINT | ✓ | 供应商ID |
| is_quoted | SMALLINT | ✓ | 是否已报价 0-否 1-是 |
| quote_date | DATE | | 报价日期 |
| selected | SMALLINT | ✓ | 是否选中 0-否 1-是 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** UNIQUE(inquiry_id, supplier_id)

#### 表10: `pur_quote_detail` — 报价明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| inquiry_id | BIGINT | ✓ | 询价单ID |
| inquiry_item_id | BIGINT | ✓ | 询价明细ID |
| supplier_id | BIGINT | ✓ | 供应商ID |
| unit_price | DECIMAL(18,2) | ✓ | 报价单价 |
| tax_rate | DECIMAL(5,2) | | 税率% |
| delivery_days | INT | | 交货天数 |
| payment_terms | VARCHAR(200) | | 付款条件 |
| validity_days | INT | | 报价有效期(天) |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(inquiry_id, supplier_id)，INDEX(inquiry_item_id)

### 2.3 智能补货建议

#### 表11: `pur_replenishment_suggestion` — 补货建议

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| product_id | BIGINT | ✓ | 商品ID |
| warehouse_id | BIGINT | | 仓库ID |
| current_stock | DECIMAL(18,4) | | 当前库存 |
| locked_stock | DECIMAL(18,4) | | 锁定库存 |
| available_stock | DECIMAL(18,4) | | 可用库存 |
| safety_stock | DECIMAL(18,4) | | 安全库存 |
| min_stock | DECIMAL(18,4) | | 最低库存 |
| max_stock | DECIMAL(18,4) | | 最高库存 |
| avg_daily_sales | DECIMAL(18,4) | | 日均销量（近30天） |
| purchase_lead_time | INT | | 采购提前期（天） |
| on_order_quantity | DECIMAL(18,4) | | 在途数量 |
| suggested_quantity | DECIMAL(18,4) | | 建议采购量 |
| suggested_date | DATE | | 建议采购日期 |
| status | SMALLINT | ✓ | 状态: 0-待处理 1-已生成请购单 2-已忽略 |
| priority | SMALLINT | | 优先级: 0-低 1-中 2-高（缺货为高） |
| reason | VARCHAR(200) | | 建议原因: BELOW_SAFETY/BELOW_MIN/ABOVE_MAX/SEASONAL |
| generated_time | TIMESTAMP | ✓ | 建议生成时间 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(product_id, warehouse_id)，INDEX(status)，INDEX(generated_time)

### 2.4 采购费用分摊

#### 表12: `pur_expense_allocation` — 采购费用分摊

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| expense_type | VARCHAR(50) | ✓ | 费用类型: FREIGHT(运费), TARIFF(关税), INSURANCE(保险费), HANDLING(装卸费), OTHER(其他) |
| expense_amount | DECIMAL(18,2) | ✓ | 费用金额 |
| currency | VARCHAR(10) | ✓ | 币种，默认CNY |
| allocation_method | VARCHAR(20) | ✓ | 分摊方式: BY_AMOUNT(按金额), BY_WEIGHT(按重量), BY_VOLUME(按体积), BY_QUANTITY(按数量), MANUAL(手动) |
| source_type | VARCHAR(50) | ✓ | 来源单据类型: PURCHASE_ORDER(采购订单), PURCHASE_RECEIPT(采购入库) |
| source_id | BIGINT | ✓ | 来源单据ID |
| allocated_amount | DECIMAL(18,2) | | 已分摊金额 |
| status | SMALLINT | ✓ | 状态: 0-待分摊 1-已分摊 2-已冲销 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(source_type, source_id)，INDEX(status)

#### 表13: `pur_expense_allocation_detail` — 费用分摊明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| allocation_id | BIGINT | ✓ | 分摊单ID |
| detail_item_id | BIGINT | ✓ | 分摊到单据明细ID（采购入库明细ID等） |
| amount | DECIMAL(18,2) | ✓ | 分摊金额 |
| allocation_base | DECIMAL(18,4) | | 分摊基数（金额/重量/体积/数量） |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(allocation_id)

### 2.5 供应商对账单

#### 表14: `pur_supplier_statement` — 供应商对账单

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| statement_no | VARCHAR(50) | ✓ | 对账单号 |
| supplier_id | BIGINT | ✓ | 供应商ID |
| start_date | DATE | ✓ | 对账开始日期 |
| end_date | DATE | ✓ | 对账结束日期 |
| opening_balance | DECIMAL(18,2) | | 期初余额（应付） |
| purchase_amount | DECIMAL(18,2) | | 本期采购金额 |
| return_amount | DECIMAL(18,2) | | 本期退货金额 |
| payment_amount | DECIMAL(18,2) | | 本期付款金额 |
| closing_balance | DECIMAL(18,2) | | 期末余额 |
| status | SMALLINT | ✓ | 状态: 0-草稿 1-待确认 2-已确认 3-有争议 |
| confirmed_by | VARCHAR(64) | | 确认人（供应商） |
| confirmed_time | TIMESTAMP | | 确认时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(statement_no)，INDEX(supplier_id, start_date, end_date)，INDEX(status)

### 2.6 供应商评估

#### 表15: `pur_supplier_evaluation` — 供应商评估

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| supplier_id | BIGINT | ✓ | 供应商ID |
| evaluation_date | DATE | ✓ | 评估日期 |
| delivery_score | DECIMAL(3,1) | | 交货准时性评分(1-10) |
| quality_score | DECIMAL(3,1) | | 质量评分(1-10) |
| price_score | DECIMAL(3,1) | | 价格竞争力评分(1-10) |
| service_score | DECIMAL(3,1) | | 服务态度评分(1-10) |
| cooperation_score | DECIMAL(3,1) | | 配合度评分(1-10) |
| total_score | DECIMAL(4,1) | | 综合评分（加权平均） |
| evaluation_content | TEXT | | 评估详细内容 |
| improvement_suggestion | TEXT | | 改进建议 |
| evaluator_id | BIGINT | ✓ | 评估人ID |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(supplier_id, evaluation_date)

#### 表16: `pur_supplier_evaluation_criteria` — 评估维度权重配置

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| name | VARCHAR(100) | ✓ | 维度名称 |
| code | VARCHAR(50) | ✓ | 维度编码 |
| weight | DECIMAL(4,2) | ✓ | 权重（0-1） |
| is_enabled | SMALLINT | ✓ | 启用状态 |
| sort_order | INT | | 排序号 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

### 2.7 API 设计

```
# 请购单
GET    /api/v1/purchase-requisitions                  — 分页查询
GET    /api/v1/purchase-requisitions/{id}             — 查询详情
POST   /api/v1/purchase-requisitions                  — 新增
PUT    /api/v1/purchase-requisitions/{id}             — 修改
DELETE /api/v1/purchase-requisitions/{id}             — 删除（仅草稿）
POST   /api/v1/purchase-requisitions/{id}/submit      — 提交审核
POST   /api/v1/purchase-requisitions/{id}/approve     — 审核
POST   /api/v1/purchase-requisitions/{id}/reject      — 驳回
POST   /api/v1/purchase-requisitions/{id}/to-order    — 转采购订单
POST   /api/v1/purchase-requisitions/{id}/close       — 关闭

# 询比价
GET    /api/v1/inquiries                              — 分页查询
POST   /api/v1/inquiries                              — 新增询价单
PUT    /api/v1/inquiries/{id}                         — 修改
POST   /api/v1/inquiries/{id}/issue                   — 发出询价
POST   /api/v1/inquiries/{id}/close                   — 截止
POST   /api/v1/inquiries/{id}/quotes                  — 录入报价
GET    /api/v1/inquiries/{id}/quotes/compare          — 比价分析
POST   /api/v1/inquiries/{id}/decide                  — 定价（选中供应商）

# 补货建议
GET    /api/v1/replenishment-suggestions              — 分页查询
POST   /api/v1/replenishment-suggestions/generate     — 手动触发生成
POST   /api/v1/replenishment-suggestions/{id}/to-requisition — 转请购单
POST   /api/v1/replenishment-suggestions/{id}/ignore  — 忽略

# 费用分摊
GET    /api/v1/expense-allocations                    — 分页查询
POST   /api/v1/expense-allocations                    — 新增分摊单
POST   /api/v1/expense-allocations/{id}/allocate      — 执行分摊
POST   /api/v1/expense-allocations/{id}/reverse       — 冲销

# 供应商对账单
GET    /api/v1/supplier-statements                    — 分页查询
POST   /api/v1/supplier-statements/generate           — 生成对账单
PUT    /api/v1/supplier-statements/{id}/confirm        — 确认
PUT    /api/v1/supplier-statements/{id}/dispute        — 标记争议

# 供应商评估
GET    /api/v1/supplier-evaluations                   — 分页查询
POST   /api/v1/supplier-evaluations                   — 新增评估
GET    /api/v1/supplier-evaluations/criteria           — 评估维度配置
PUT    /api/v1/supplier-evaluations/criteria           — 更新维度权重
```

---

## Phase 3: 销售管理增强

### 3.1 销售报价单

#### 表17: `sal_quotation` — 销售报价单

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| quotation_no | VARCHAR(50) | ✓ | 报价单号 |
| quotation_date | DATE | ✓ | 报价日期 |
| customer_id | BIGINT | ✓ | 客户ID |
| salesperson_id | BIGINT | | 业务员ID |
| status | SMALLINT | ✓ | 状态: 0-草稿 1-已发出 2-已确认 3-已转订单 4-已失效 |
| valid_until | DATE | ✓ | 有效期至 |
| total_amount | DECIMAL(18,2) | | 报价金额 |
| discount_rate | DECIMAL(5,2) | | 整单折扣率% |
| after_discount | DECIMAL(18,2) | | 折后金额 |
| tax_amount | DECIMAL(18,2) | | 税额 |
| final_amount | DECIMAL(18,2) | | 含税最终金额 |
| payment_terms | VARCHAR(200) | | 付款条件 |
| delivery_terms | VARCHAR(200) | | 交货条件 |
| remark | VARCHAR(500) | | 备注 |
| approver_id | BIGINT | | 审核人ID |
| approve_time | TIMESTAMP | | 审核时间 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(quotation_no)，INDEX(customer_id)，INDEX(status)，INDEX(valid_until)

#### 表18: `sal_quotation_item` — 报价单明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| quotation_id | BIGINT | ✓ | 报价单ID |
| product_id | BIGINT | ✓ | 商品ID |
| quantity | DECIMAL(18,4) | ✓ | 数量 |
| unit_price | DECIMAL(18,2) | ✓ | 单价 |
| discount_rate | DECIMAL(5,2) | | 单品折扣率% |
| discount_amount | DECIMAL(18,2) | | 折扣金额 |
| tax_rate | DECIMAL(5,2) | | 税率% |
| tax_amount | DECIMAL(18,2) | | 税额 |
| total_amount | DECIMAL(18,2) | ✓ | 小计金额 |
| cost_price | DECIMAL(18,2) | | 成本价（毛利率参考） |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(quotation_id)，INDEX(product_id)

### 3.2 销售换货

#### 表19: `sal_exchange_order` — 销售换货单

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| exchange_no | VARCHAR(50) | ✓ | 换货单号 |
| exchange_date | DATE | ✓ | 换货日期 |
| customer_id | BIGINT | ✓ | 客户ID |
| sales_order_id | BIGINT | | 关联销售订单ID |
| delivery_id | BIGINT | | 关联销售出库单ID |
| status | SMALLINT | ✓ | 状态: 0-草稿 1-待审核 2-已审核 3-已完成 |
| exchange_reason | VARCHAR(500) | | 换货原因 |
| return_total | DECIMAL(18,2) | | 退回商品总金额 |
| exchange_total | DECIMAL(18,2) | | 换出商品总金额 |
| difference_amount | DECIMAL(18,2) | | 差额（换出-退回，正=补款，负=退款） |
| warehouse_id | BIGINT | ✓ | 仓库ID |
| approver_id | BIGINT | | 审核人ID |
| approve_time | TIMESTAMP | | 审核时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(exchange_no)，INDEX(customer_id)，INDEX(status)

#### 表20: `sal_exchange_return_item` — 换货退回明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| exchange_id | BIGINT | ✓ | 换货单ID |
| product_id | BIGINT | ✓ | 退回商品ID |
| quantity | DECIMAL(18,4) | ✓ | 数量 |
| unit_price | DECIMAL(18,2) | ✓ | 单价 |
| total_amount | DECIMAL(18,2) | ✓ | 金额 |
| batch_no | VARCHAR(50) | | 批次号 |
| reason | VARCHAR(200) | | 退货原因: QUALITY(质量)/SIZE(尺寸)/WRONG(发错)/OTHER(其他) |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(exchange_id)

#### 表21: `sal_exchange_out_item` — 换货发出明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| exchange_id | BIGINT | ✓ | 换货单ID |
| product_id | BIGINT | ✓ | 换出商品ID |
| quantity | DECIMAL(18,4) | ✓ | 数量 |
| unit_price | DECIMAL(18,2) | ✓ | 单价 |
| total_amount | DECIMAL(18,2) | ✓ | 金额 |
| batch_no | VARCHAR(50) | | 批次号 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(exchange_id)

### 3.3 销售提成

#### 表22: `sal_commission_rule` — 提成规则

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| name | VARCHAR(100) | ✓ | 规则名称 |
| calculation_method | VARCHAR(20) | ✓ | 计算方式: RATE(按比例), FIXED(固定金额), TIER(阶梯) |
| base_on | VARCHAR(20) | ✓ | 计算基数: SALES_AMOUNT(销售额), GROSS_PROFIT(毛利), QUANTITY(数量) |
| commission_rate | DECIMAL(8,4) | | 提成比例% |
| fixed_amount | DECIMAL(18,2) | | 固定金额 |
| min_threshold | DECIMAL(18,2) | | 最低门槛（基数低于此不提成） |
| max_cap | DECIMAL(18,2) | | 提成上限 |
| is_enabled | SMALLINT | ✓ | 启用状态 |
| start_date | DATE | | 生效日期 |
| end_date | DATE | | 截止日期 |
| apply_to_product_type | VARCHAR(20) | | 适用商品类型: ALL(全部)/CATEGORY(分类)/PRODUCT(指定商品) |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(is_enabled)

#### 表23: `sal_commission_rule_target` — 提成规则适用对象

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| rule_id | BIGINT | ✓ | 规则ID |
| target_type | VARCHAR(20) | ✓ | 对象类型: USER(用户), ROLE(角色), DEPARTMENT(部门) |
| target_id | BIGINT | ✓ | 对象ID |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** UNIQUE(rule_id, target_type, target_id)

#### 表24: `sal_commission_record` — 提成记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| user_id | BIGINT | ✓ | 业务员ID |
| rule_id | BIGINT | ✓ | 提成规则ID |
| period | VARCHAR(7) | ✓ | 提成周期 YYYY-MM |
| source_type | VARCHAR(50) | ✓ | 来源单据类型 |
| source_id | BIGINT | ✓ | 来源单据ID |
| base_amount | DECIMAL(18,2) | ✓ | 计算基数 |
| commission_amount | DECIMAL(18,2) | ✓ | 提成金额 |
| status | SMALLINT | ✓ | 状态: 0-待发放 1-已发放 2-已冲销 |
| calculated_time | TIMESTAMP | ✓ | 计算时间 |
| paid_time | TIMESTAMP | | 发放时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(user_id, period)，INDEX(status)

### 3.4 销售挂单

在现有销售订单表 `sal_sales_orders` 中增加一个挂单标记字段即可，无需新建表。

#### 修改: `sal_sales_orders` 增加字段

| 新增字段 | 类型 | 说明 |
|---------|------|------|
| is_suspended | SMALLINT | 是否挂单 0-否 1-是（挂单状态下 status=0 草稿，可恢复后继续编辑） |

### 3.5 客户对账单

#### 表25: `sal_customer_statement` — 客户对账单

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| statement_no | VARCHAR(50) | ✓ | 对账单号 |
| customer_id | BIGINT | ✓ | 客户ID |
| start_date | DATE | ✓ | 对账开始日期 |
| end_date | DATE | ✓ | 对账结束日期 |
| opening_receivable | DECIMAL(18,2) | | 期初应收 |
| sales_amount | DECIMAL(18,2) | | 本期销售金额 |
| return_amount | DECIMAL(18,2) | | 本期退货金额 |
| receipt_amount | DECIMAL(18,2) | | 本期收款金额 |
| closing_receivable | DECIMAL(18,2) | | 期末应收 |
| status | SMALLINT | ✓ | 状态: 0-草稿 1-待确认 2-已确认 3-有争议 |
| confirmed_by | VARCHAR(64) | | 确认人 |
| confirmed_time | TIMESTAMP | | 确认时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(statement_no)，INDEX(customer_id, start_date, end_date)

### 3.6 API 设计

```
# 报价单
GET    /api/v1/quotations                           — 分页查询
POST   /api/v1/quotations                           — 新增
PUT    /api/v1/quotations/{id}                      — 修改
DELETE /api/v1/quotations/{id}                      — 删除
POST   /api/v1/quotations/{id}/issue                — 发出报价
POST   /api/v1/quotations/{id}/confirm              — 确认报价
POST   /api/v1/quotations/{id}/to-order             — 转销售订单
POST   /api/v1/quotations/{id}/expire               — 标记失效

# 换货单
GET    /api/v1/exchange-orders                      — 分页查询
POST   /api/v1/exchange-orders                      — 新增
PUT    /api/v1/exchange-orders/{id}                 — 修改
DELETE /api/v1/exchange-orders/{id}                 — 删除（仅草稿）
POST   /api/v1/exchange-orders/{id}/approve         — 审核
POST   /api/v1/exchange-orders/{id}/complete        — 完成（出库退回+发出）

# 提成管理
GET    /api/v1/commission-rules                     — 规则列表
POST   /api/v1/commission-rules                     — 新增规则
PUT    /api/v1/commission-rules/{id}                — 修改规则
DELETE /api/v1/commission-rules/{id}                — 删除规则
PUT    /api/v1/commission-rules/{id}/toggle         — 启用/停用
GET    /api/v1/commission-records                   — 提成记录
POST   /api/v1/commission-records/calculate         — 计算指定周期提成
POST   /api/v1/commission-records/{id}/pay          — 发放

# 挂单
POST   /api/v1/sales-orders/{id}/suspend            — 挂单
POST   /api/v1/sales-orders/{id}/resume             — 恢复挂单
GET    /api/v1/sales-orders/suspended               — 挂单列表

# 客户对账单
GET    /api/v1/customer-statements                  — 分页查询
POST   /api/v1/customer-statements/generate         — 生成对账单
PUT    /api/v1/customer-statements/{id}/confirm      — 确认
PUT    /api/v1/customer-statements/{id}/dispute      — 标记争议
```

## Phase 4: 库存管理增强

### 4.1 库存锁定/预留

#### 表26: `inv_stock_reservation` — 库存预留

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| reservation_no | VARCHAR(50) | ✓ | 预留单号 |
| product_id | BIGINT | ✓ | 商品ID |
| warehouse_id | BIGINT | ✓ | 仓库ID |
| batch_no | VARCHAR(50) | | 批次号 |
| quantity | DECIMAL(18,4) | ✓ | 预留数量 |
| released_quantity | DECIMAL(18,4) | | 已释放数量 |
| source_type | VARCHAR(50) | ✓ | 来源单据类型: SALES_ORDER, STOCK_TRANSFER, ASSEMBLY |
| source_id | BIGINT | ✓ | 来源单据ID |
| source_item_id | BIGINT | | 来源单据明细ID |
| status | SMALLINT | ✓ | 状态: 0-已预留 1-已释放 2-已出库 3-已取消 |
| reserved_by | VARCHAR(64) | ✓ | 预留人 |
| reserved_time | TIMESTAMP | ✓ | 预留时间 |
| expire_time | TIMESTAMP | | 自动释放时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(reservation_no)，INDEX(product_id, warehouse_id, batch_no, status)，INDEX(source_type, source_id)，INDEX(expire_time)

### 4.2 保质期预警

#### 表27: `inv_expiry_alert` — 保质期预警记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| product_id | BIGINT | ✓ | 商品ID |
| warehouse_id | BIGINT | ✓ | 仓库ID |
| batch_no | VARCHAR(50) | ✓ | 批次号 |
| expiry_date | DATE | ✓ | 到期日期 |
| quantity | DECIMAL(18,4) | ✓ | 批次数量 |
| remaining_days | INT | ✓ | 剩余天数 |
| alert_level | VARCHAR(20) | ✓ | 预警级别: EXPIRED(已过期), URGENT(30天内), WARNING(60天内), NOTICE(90天内) |
| alert_time | TIMESTAMP | ✓ | 预警触发时间 |
| handled | SMALLINT | ✓ | 已处理 0-否 1-是 |
| handled_time | TIMESTAMP | | 处理时间 |
| handle_method | VARCHAR(50) | | 处理方式: DISCOUNT(折价处理), RETURN(退货), DISPOSAL(报废), OTHER |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(product_id)，INDEX(alert_level)，INDEX(alert_time)，INDEX(handled)

#### 保质期预警配置

在现有 `inv_warning_configs` 表中增加字段:

| 新增字段 | 类型 | 说明 |
|---------|------|------|
| expiry_warning_days | INT | 保质期预警提前天数（如 30） |
| expiry_warning_enabled | SMALLINT | 是否启用保质期预警 0-否 1-是 |

### 4.3 库龄分析

#### 表28: `inv_stock_aging` — 库存库龄快照

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| snapshot_date | DATE | ✓ | 快照日期 |
| product_id | BIGINT | ✓ | 商品ID |
| warehouse_id | BIGINT | ✓ | 仓库ID |
| batch_no | VARCHAR(50) | | 批次号 |
| quantity | DECIMAL(18,4) | ✓ | 库存数量 |
| cost_amount | DECIMAL(18,2) | | 库存成本金额 |
| last_inbound_date | DATE | | 最后入库日期 |
| aging_days | INT | | 库龄天数 |
| aging_bucket | VARCHAR(20) | | 库龄区间: 0-30/31-60/61-90/91-180/181-365/365+ |
| turnover_rate | DECIMAL(8,4) | | 周转率 |
| is_slow_moving | SMALLINT | | 是否呆滞品 0-否 1-是 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(snapshot_date)，INDEX(product_id, warehouse_id)，INDEX(aging_bucket)

### 4.4 扫码出入库

现有框架无需新建表，需在 Controller 层增加：
- 扫码查询商品信息接口
- 扫码快速入库/出库接口（传入条码+仓库+数量）

### 4.5 API 设计

```
# 库存预留
GET    /api/v1/stock-reservations                   — 分页查询
POST   /api/v1/stock-reservations                   — 新增预留
POST   /api/v1/stock-reservations/{id}/release      — 释放预留
POST   /api/v1/stock-reservations/{id}/cancel       — 取消预留

# 保质期预警
GET    /api/v1/expiry-alerts                        — 预警列表
POST   /api/v1/expiry-alerts/scan                   — 手动扫描生成预警
PUT    /api/v1/expiry-alerts/{id}/handle            — 处理预警
GET    /api/v1/expiry-alerts/stats                  — 预警统计

# 库龄分析
GET    /api/v1/stock-aging                          — 库龄查询
POST   /api/v1/stock-aging/snapshot                 — 生成库龄快照
GET    /api/v1/stock-aging/slow-moving              — 呆滞品列表
GET    /api/v1/stock-aging/turnover-rate            — 周转率统计

# 扫码 API
GET    /api/v1/products/barcode/{barcode}            — 扫码查商品
POST   /api/v1/inventory/scan-in                    — 扫码入库
POST   /api/v1/inventory/scan-out                   — 扫码出库
POST   /api/v1/inventory/scan-check                 — 扫码盘点
```

---

## Phase 5: 资金财务增强

### 5.1 成本核算配置

不新建表。在商品 `bas_products` 表中增加成本核算方法字段：

| 新增字段 | 类型 | 说明 |
|---------|------|------|
| costing_method | VARCHAR(20) | 成本核算方法: WEIGHTED_AVG(移动加权平均 默认), FIFO(先进先出), SPECIFIC(个别计价) |

### 5.2 成本核算明细（移动加权平均）

#### 表29: `fin_cost_calculation` — 成本计算记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| product_id | BIGINT | ✓ | 商品ID |
| warehouse_id | BIGINT | | 仓库ID |
| batch_no | VARCHAR(50) | | 批次号 |
| transaction_type | VARCHAR(20) | ✓ | 交易类型: PURCHASE_IN(采购入库), SALES_OUT(销售出库), RETURN_IN(退货入库), OTHER_IN(其他入库), OTHER_OUT(其他出库) |
| transaction_id | BIGINT | ✓ | 交易单据ID |
| quantity_before | DECIMAL(18,4) | | 交易前数量 |
| cost_before | DECIMAL(18,2) | | 交易前单位成本 |
| total_cost_before | DECIMAL(18,2) | | 交易前总成本 |
| transaction_quantity | DECIMAL(18,4) | ✓ | 交易数量 |
| transaction_unit_cost | DECIMAL(18,2) | ✓ | 交易单位成本 |
| transaction_total_cost | DECIMAL(18,2) | ✓ | 交易总成本 |
| quantity_after | DECIMAL(18,4) | | 交易后数量 |
| cost_after | DECIMAL(18,2) | | 交易后单位成本 |
| total_cost_after | DECIMAL(18,2) | | 交易后总成本 |
| calculated_time | TIMESTAMP | ✓ | 计算时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(product_id, warehouse_id)，INDEX(transaction_type, transaction_id)，INDEX(calculated_time)

### 5.3 发票管理

#### 表30: `fin_invoice` — 发票

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| invoice_no | VARCHAR(50) | ✓ | 发票号码 |
| invoice_code | VARCHAR(50) | | 发票代码 |
| invoice_type | VARCHAR(20) | ✓ | 类型: PURCHASE_IN(进项发票), SALES_OUT(销项发票) |
| invoice_date | DATE | ✓ | 开票日期 |
| source_type | VARCHAR(50) | ✓ | 来源单据类型: PURCHASE_ORDER, PURCHASE_RECEIPT, SALES_ORDER, SALES_DELIVERY |
| source_id | BIGINT | ✓ | 来源单据ID |
| counterparty_id | BIGINT | ✓ | 对方单位ID（供应商/客户） |
| counterparty_name | VARCHAR(200) | ✓ | 对方单位名称 |
| invoice_amount | DECIMAL(18,2) | ✓ | 发票金额（不含税） |
| tax_rate | DECIMAL(5,2) | ✓ | 税率% |
| tax_amount | DECIMAL(18,2) | ✓ | 税额 |
| total_amount | DECIMAL(18,2) | ✓ | 价税合计 |
| status | SMALLINT | ✓ | 状态: 0-待开票 1-已开票 2-已认证(进项)/已红冲(销项) 3-已作废 |
| verification_status | VARCHAR(20) | | 认证状态（进项发票）: PENDING/VERIFIED/FAILED |
| verificated_date | DATE | | 认证日期 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(invoice_no)，INDEX(invoice_type, invoice_date)，INDEX(source_type, source_id)，INDEX(counterparty_id)

### 5.4 费用分摊（采购费用已覆盖，此处为运营费用）

#### 表31: `fin_expense_allocation` — 运营费用分摊

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| expense_no | VARCHAR(50) | ✓ | 分摊单号 |
| expense_type | VARCHAR(50) | ✓ | 费用类型: SHIPPING(运费), PACKAGING(包装), ADVERTISING(广告), RENT(租金), UTILITIES(水电), SALARY(工资), OTHER |
| expense_amount | DECIMAL(18,2) | ✓ | 费用金额 |
| allocation_method | VARCHAR(20) | ✓ | 分摊方式: BY_SALES_AMOUNT(按销售额), BY_SALES_QUANTITY(按销量), BY_COST(按成本), BY_WEIGHT(按重量), MANUAL(手动) |
| allocation_period | VARCHAR(7) | ✓ | 分摊周期 YYYY-MM |
| status | SMALLINT | ✓ | 状态: 0-待分摊 1-已分摊 2-已冲销 |
| allocated_amount | DECIMAL(18,2) | | 已分摊金额 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(expense_no)，INDEX(allocation_period)

#### 表32: `fin_expense_allocation_detail` — 运营费用分摊明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| allocation_id | BIGINT | ✓ | 分摊单ID |
| target_type | VARCHAR(20) | ✓ | 分摊对象: PRODUCT(商品), SALE_ORDER(销售订单), DEPARTMENT(部门) |
| target_id | BIGINT | ✓ | 对象ID |
| amount | DECIMAL(18,2) | ✓ | 分摊金额 |
| allocation_base | DECIMAL(18,4) | | 分摊基数 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(allocation_id)

### 5.5 利润分析

不新增表，通过查询现有业务数据实时计算。

### 5.6 API 设计

```
# 成本核算
GET    /api/v1/cost-calculations                    — 成本计算记录查询
POST   /api/v1/cost-calculations/recalculate        — 重新核算指定商品成本
GET    /api/v1/cost-calculations/product/{productId} — 商品成本追溯

# 发票管理
GET    /api/v1/invoices                             — 分页查询
POST   /api/v1/invoices                             — 新增发票
PUT    /api/v1/invoices/{id}                        — 修改
DELETE /api/v1/invoices/{id}                        — 删除
POST   /api/v1/invoices/{id}/verify                 — 认证（进项）
POST   /api/v1/invoices/{id}/red-rush               — 红冲（销项）
POST   /api/v1/invoices/{id}/cancel                 — 作废
GET    /api/v1/invoices/unbilled                     — 待开票单据列表

# 费用分摊
GET    /api/v1/expense-allocations                  — 分页查询
POST   /api/v1/expense-allocations                  — 新增分摊单
POST   /api/v1/expense-allocations/{id}/allocate    — 执行分摊
POST   /api/v1/expense-allocations/{id}/reverse     — 冲销

# 利润分析
GET    /api/v1/profit-analysis/product              — 商品毛利分析
GET    /api/v1/profit-analysis/customer             — 客户利润贡献
GET    /api/v1/profit-analysis/salesperson           — 业务员利润
GET    /api/v1/profit-analysis/monthly              — 月度利润趋势
```

## Phase 6: 报表与数据分析

### 6.1 报表模板

#### 表33: `rpt_report_template` — 报表模板

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| name | VARCHAR(100) | ✓ | 报表名称 |
| code | VARCHAR(50) | ✓ | 报表编码 |
| category | VARCHAR(50) | ✓ | 报表分类: SALES(销售), PURCHASE(采购), INVENTORY(库存), FINANCE(财务), HR(人事) |
| query_sql | TEXT | ✓ | 查询SQL（支持占位符） |
| columns_config | JSON | ✓ | 列配置JSON: [{field, title, width, align, format, sortable}] |
| filters_config | JSON | | 筛选条件配置JSON: [{field, label, type, options, default}] |
| charts_config | JSON | | 图表配置JSON: [{type: bar/line/pie, title, xField, yField}] |
| is_system | SMALLINT | ✓ | 是否系统预置 0-自定义 1-系统 |
| is_enabled | SMALLINT | ✓ | 启用状态 |
| sort_order | INT | | 排序号 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(code)，INDEX(category)

### 6.2 报表导出记录

#### 表34: `rpt_export_record` — 导出记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| report_id | BIGINT | | 报表模板ID（null=自定义导出） |
| export_type | VARCHAR(20) | ✓ | 导出类型: EXCEL, PDF, CSV |
| file_name | VARCHAR(200) | ✓ | 文件名 |
| file_path | VARCHAR(500) | ✓ | 文件存储路径 |
| file_size | BIGINT | | 文件大小(byte) |
| filter_params | JSON | | 导出筛选条件JSON |
| exported_time | TIMESTAMP | ✓ | 导出时间 |
| exported_by | VARCHAR(64) | ✓ | 导出人 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(exported_time)，INDEX(exported_by)

### 6.3 系统预置报表

系统初始化时预置以下报表模板：

| 编码 | 名称 | 分类 |
|------|------|------|
| SALES_DAILY | 销售日报 | 销售 |
| SALES_MONTHLY | 销售月报 | 销售 |
| SALES_PRODUCT_RANK | 商品销售排行 | 销售 |
| SALES_CUSTOMER_RANK | 客户销售排行 | 销售 |
| SALES_TREND | 销售趋势图 | 销售 |
| SALES_GROSS_PROFIT | 销售毛利表 | 销售 |
| PURCHASE_SUMMARY | 采购汇总表 | 采购 |
| PURCHASE_SUPPLIER_RANK | 供应商采购排名 | 采购 |
| PURCHASE_PRICE_TREND | 采购价格趋势 | 采购 |
| INVENTORY_BALANCE | 库存余额表 | 库存 |
| INVENTORY_RECEIVE_SEND | 收发存汇总表 | 库存 |
| INVENTORY_AGING | 库存库龄分析 | 库存 |
| INVENTORY_TURNOVER | 库存周转率 | 库存 |
| INVENTORY_SLOW_MOVING | 呆滞品分析 | 库存 |
| FINANCE_AR_AGING | 应收账龄分析 | 财务 |
| FINANCE_AP_AGING | 应付账龄分析 | 财务 |
| FINANCE_CASH_FLOW | 资金流水报表 | 财务 |
| FINANCE_PROFIT | 利润报表 | 财务 |
| FINANCE_EXPENSE | 费用统计表 | 财务 |

### 6.4 经营看板

通过 `/api/v1/dashboard` 提供聚合查询，不建独立表。实时计算以下指标：

```
GET /api/v1/dashboard/summary                      — 首页数据总览
  返回: {todaySales, todayProfit, monthSales, monthProfit, 
         inventoryValue, receivableAmount, payableAmount,
         lowStockCount, expiryAlertCount, pendingOrderCount}

GET /api/v1/dashboard/sales-trend                  — 销售趋势（近30天/近12月）
GET /api/v1/dashboard/top-products                 — 热销商品TOP10
GET /api/v1/dashboard/top-customers                — 客户贡献TOP10
GET /api/v1/dashboard/stock-warning                — 库存预警汇总
GET /api/v1/dashboard/receivable-warning            — 应收预警汇总
```

### 6.5 API 设计

```
# 报表模板
GET    /api/v1/report-templates                    — 模板列表
POST   /api/v1/report-templates                    — 新增自定义模板
PUT    /api/v1/report-templates/{id}               — 修改模板
DELETE /api/v1/report-templates/{id}               — 删除模板

# 报表查询与导出
POST   /api/v1/reports/{code}/query                — 执行报表查询（传入筛选参数）
POST   /api/v1/reports/{code}/export/excel         — 导出Excel
POST   /api/v1/reports/{code}/export/pdf           — 导出PDF
GET    /api/v1/reports/export-records              — 导出记录查询
GET    /api/v1/reports/export-records/{id}/download — 下载导出文件

# 经营看板
GET    /api/v1/dashboard/summary                   — 首页数据总览
GET    /api/v1/dashboard/sales-trend               — 销售趋势
GET    /api/v1/dashboard/top-products              — 热销商品
GET    /api/v1/dashboard/top-customers             — 客户排名
GET    /api/v1/dashboard/stock-warning             — 库存预警
GET    /api/v1/dashboard/receivable-warning         — 应收预警
```

---

## Phase 7: 系统管理增强

### 7.1 多级审批流引擎

#### 表35: `sys_approval_flow` — 审批流定义

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| name | VARCHAR(100) | ✓ | 流程名称，如"采购订单审批" |
| code | VARCHAR(50) | ✓ | 流程编码，如 PURCHASE_ORDER |
| target_type | VARCHAR(50) | ✓ | 适用业务类型: PURCHASE_ORDER/SALES_ORDER/EXPENSE/等等 |
| is_enabled | SMALLINT | ✓ | 启用状态 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(code)

#### 表36: `sys_approval_node` — 审批节点

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| flow_id | BIGINT | ✓ | 流程ID |
| node_name | VARCHAR(100) | ✓ | 节点名称，如"部门经理审批"、"总经理审批" |
| node_order | INT | ✓ | 节点顺序（从1开始） |
| approver_type | VARCHAR(20) | ✓ | 审批人类型: USER(指定用户), ROLE(角色), DEPARTMENT_LEADER(部门负责人), CUSTOM(自定义表达式) |
| approver_value | VARCHAR(500) | ✓ | 审批人值（用户ID/角色编码/自定义表达式） |
| can_reject | SMALLINT | ✓ | 可否驳回 0-否 1-是 |
| can_delegate | SMALLINT | ✓ | 可否转交 0-否 1-是 |
| timeout_hours | INT | | 超时时间（小时），超时自动通过或通知 |
| condition_expression | VARCHAR(500) | | 条件表达式（满足条件才进入此节点） |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(flow_id)

#### 表37: `sys_approval_instance` — 审批实例

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| flow_id | BIGINT | ✓ | 审批流ID |
| business_type | VARCHAR(50) | ✓ | 业务类型 |
| business_id | BIGINT | ✓ | 业务单据ID |
| business_no | VARCHAR(50) | ✓ | 业务单号 |
| current_node_order | INT | ✓ | 当前审批节点顺序 |
| total_nodes | INT | ✓ | 总节点数 |
| status | SMALLINT | ✓ | 状态: 0-审批中 1-已通过 2-已驳回 3-已撤回 |
| applicant_id | BIGINT | ✓ | 申请人ID |
| applicant_name | VARCHAR(100) | ✓ | 申请人姓名 |
| applied_time | TIMESTAMP | ✓ | 申请时间 |
| completed_time | TIMESTAMP | | 完成时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** UNIQUE(business_type, business_id)，INDEX(flow_id)，INDEX(status)，INDEX(applicant_id)

#### 表38: `sys_approval_record` — 审批记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| instance_id | BIGINT | ✓ | 审批实例ID |
| node_id | BIGINT | ✓ | 审批节点ID |
| node_order | INT | ✓ | 节点顺序 |
| node_name | VARCHAR(100) | ✓ | 节点名称 |
| approver_id | BIGINT | ✓ | 审批人ID |
| approver_name | VARCHAR(100) | ✓ | 审批人姓名 |
| action | VARCHAR(20) | ✓ | 审批动作: APPROVE(通过), REJECT(驳回), DELEGATE(转交), RETURN(退回) |
| comment | VARCHAR(500) | | 审批意见 |
| delegate_to_id | BIGINT | | 转交人ID |
| action_time | TIMESTAMP | ✓ | 操作时间 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(instance_id)，INDEX(approver_id)

### 7.2 消息通知

#### 表39: `sys_notification` — 通知消息

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| title | VARCHAR(200) | ✓ | 通知标题 |
| content | TEXT | ✓ | 通知内容 |
| type | VARCHAR(20) | ✓ | 类型: SYSTEM(系统), APPROVAL(审批), WARNING(预警), BUSINESS(业务) |
| level | VARCHAR(20) | ✓ | 级别: INFO(信息), WARNING(警告), URGENT(紧急) |
| sender_id | BIGINT | | 发送人ID（系统通知为null） |
| sender_name | VARCHAR(100) | | 发送人姓名 |
| target_type | VARCHAR(20) | ✓ | 目标类型: ALL(全员), USER(指定用户), ROLE(角色), DEPARTMENT(部门) |
| target_value | VARCHAR(500) | | 目标值 |
| business_type | VARCHAR(50) | | 关联业务类型 |
| business_id | BIGINT | | 关联业务ID |
| send_time | TIMESTAMP | ✓ | 发送时间 |
| expire_time | TIMESTAMP | | 过期时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(type, level)，INDEX(send_time)，INDEX(target_type)

#### 表40: `sys_notification_read` — 通知已读记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| notification_id | BIGINT | ✓ | 通知ID |
| user_id | BIGINT | ✓ | 用户ID |
| read_time | TIMESTAMP | ✓ | 阅读时间 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** UNIQUE(notification_id, user_id)

### 7.3 系统参数配置

#### 表41: `sys_config` — 系统配置

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| config_key | VARCHAR(100) | ✓ | 配置键 |
| config_value | TEXT | ✓ | 配置值 |
| config_type | VARCHAR(20) | ✓ | 类型: STRING, NUMBER, BOOLEAN, JSON |
| config_group | VARCHAR(50) | ✓ | 分组: SYSTEM(系统), BUSINESS(业务), EMAIL(邮件), SMS(短信), WECHAT(微信) |
| description | VARCHAR(500) | | 配置说明 |
| is_system | SMALLINT | ✓ | 是否系统内置 0-否 1-是（系统内置不可删除） |
| sort_order | INT | | 排序号 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(config_key)，INDEX(config_group)

### 7.4 文件上传与附件

#### 表42: `sys_attachment` — 附件

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| original_name | VARCHAR(500) | ✓ | 原始文件名 |
| stored_name | VARCHAR(500) | ✓ | 存储文件名（UUID） |
| file_path | VARCHAR(500) | ✓ | 存储路径 |
| file_size | BIGINT | ✓ | 文件大小(byte) |
| mime_type | VARCHAR(100) | ✓ | 文件类型 |
| file_ext | VARCHAR(20) | | 文件扩展名 |
| storage_type | VARCHAR(20) | ✓ | 存储类型: LOCAL(本地), MINIO |
| md5 | VARCHAR(64) | | MD5校验值 |
| business_type | VARCHAR(50) | ✓ | 业务类型（关联哪个业务模块） |
| business_id | BIGINT | ✓ | 业务单据ID |
| uploaded_by | VARCHAR(64) | ✓ | 上传人 |
| uploaded_time | TIMESTAMP | ✓ | 上传时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(business_type, business_id)，INDEX(md5)

### 7.5 打印模板

#### 表43: `sys_print_template` — 打印模板

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| name | VARCHAR(100) | ✓ | 模板名称 |
| code | VARCHAR(50) | ✓ | 模板编码 |
| business_type | VARCHAR(50) | ✓ | 业务类型 |
| template_content | TEXT | ✓ | 模板内容（HTML/FreeMarker） |
| page_size | VARCHAR(20) | ✓ | 纸张大小: A4, A5, 80mm_continuous |
| is_default | SMALLINT | ✓ | 是否默认模板 |
| is_enabled | SMALLINT | ✓ | 启用状态 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(code)，INDEX(business_type)

### 7.6 数据备份

#### 表44: `sys_data_backup` — 数据备份记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| backup_type | VARCHAR(20) | ✓ | 备份类型: FULL(全量), INCREMENTAL(增量) |
| backup_file | VARCHAR(500) | ✓ | 备份文件路径 |
| file_size | BIGINT | | 文件大小(byte) |
| status | SMALLINT | ✓ | 状态: 0-进行中 1-成功 2-失败 |
| error_message | TEXT | | 错误信息 |
| started_time | TIMESTAMP | ✓ | 开始时间 |
| completed_time | TIMESTAMP | | 完成时间 |
| triggered_by | VARCHAR(64) | ✓ | 触发人: SYSTEM(自动), 或用户名 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(backup_type)，INDEX(status)，INDEX(started_time)

### 7.7 数据导入

不新建表，通过 Controller 层暴露上传 Excel 并解析导入的接口。

### 7.8 API 设计

```
# 审批流
GET    /api/sys/approval-flows                      — 流程列表
POST   /api/sys/approval-flows                      — 新增流程
PUT    /api/sys/approval-flows/{id}                 — 修改流程
DELETE /api/sys/approval-flows/{id}                 — 删除流程
PUT    /api/sys/approval-flows/{id}/toggle          — 启用/停用
GET    /api/sys/approval-flows/{id}/nodes           — 节点列表
POST   /api/sys/approval-flows/{id}/nodes           — 新增节点
PUT    /api/sys/approval-nodes/{id}                 — 修改节点
DELETE /api/sys/approval-nodes/{id}                 — 删除节点
POST   /api/sys/approval-instances/submit           — 提交审批
POST   /api/sys/approval-instances/{id}/approve     — 审批通过
POST   /api/sys/approval-instances/{id}/reject      — 审批驳回
POST   /api/sys/approval-instances/{id}/delegate    — 转交
POST   /api/sys/approval-instances/{id}/withdraw    — 撤回
GET    /api/sys/approval-instances/my-apply         — 我的申请
GET    /api/sys/approval-instances/my-approval      — 我的待审
GET    /api/sys/approval-instances/my-done           — 我的已审

# 消息通知
GET    /api/sys/notifications                       — 通知列表
GET    /api/sys/notifications/unread-count          — 未读数量
PUT    /api/sys/notifications/{id}/read             — 标记已读
PUT    /api/sys/notifications/read-all              — 全部已读
POST   /api/sys/notifications                       — 发送通知（管理员）
DELETE /api/sys/notifications/{id}                  — 删除通知

# 系统配置
GET    /api/sys/configs                             — 配置列表（按分组）
GET    /api/sys/configs/{key}                       — 获取单个配置值
PUT    /api/sys/configs                             — 批量更新配置
PUT    /api/sys/configs/{key}                       — 更新单个配置

# 文件管理
POST   /api/sys/files/upload                        — 上传文件
GET    /api/sys/files/{id}/download                 — 下载文件
GET    /api/sys/files/{id}/preview                  — 预览（图片/PDF）
DELETE /api/sys/files/{id}                          — 删除文件
GET    /api/sys/files/business/{type}/{id}          — 查询业务关联附件

# 打印模板
GET    /api/sys/print-templates                     — 模板列表
POST   /api/sys/print-templates                     — 新增模板
PUT    /api/sys/print-templates/{id}                — 修改模板
DELETE /api/sys/print-templates/{id}                — 删除模板
POST   /api/sys/print-templates/{id}/preview/{businessId} — 预览打印
POST   /api/sys/print-templates/{id}/set-default    — 设为默认

# 数据备份
GET    /api/sys/backups                             — 备份记录列表
POST   /api/sys/backups                             — 手动备份
POST   /api/sys/backups/{id}/restore                — 恢复备份
DELETE /api/sys/backups/{id}                        — 删除备份文件
GET    /api/sys/backups/{id}/download               — 下载备份文件

# 数据导入
POST   /api/sys/data/import/products                — 导入商品
POST   /api/sys/data/import/customers               — 导入客户
POST   /api/sys/data/import/suppliers               — 导入供应商
GET    /api/sys/data/import/templates/{type}        — 下载导入模板
```

## Phase 8: 协同与移动端

### 8.1 移动端 API

移动端复用现有的业务 Controller，新增独立的移动端简版 API：

```
# 移动端认证（简化返回）
POST   /api/m/auth/login                            — 移动端登录（返回简化 token+user 信息）

# 移动端业务看板
GET    /api/m/dashboard                             — 移动端首页数据
  返回: {todaySales, monthSales, pendingApprovals, stockWarnings, notifications}

# 移动端业务操作
GET    /api/m/products/search                       — 商品搜索（支持扫码+关键字）
GET    /api/m/products/{id}/stock                   — 查询商品库存（多仓库）
POST   /api/m/sales-orders                          — 快速开销售单
GET    /api/m/sales-orders                          — 销售订单列表（移动端简化字段）
POST   /api/m/purchase-orders                       — 快速开采购单
POST   /api/m/approvals/{id}/approve                — 移动端审批
GET    /api/m/my-approvals                          — 我的待审列表
GET    /api/m/inventory/check                        — 库存速查（扫码）

# 移动端扫码
POST   /api/m/scan/barcode                          — 扫码获取商品信息+库存
POST   /api/m/scan/check-in                         — 扫码快速入库
POST   /api/m/scan/check-out                        — 扫码快速出库
POST   /api/m/scan/stock-take                       — 扫码盘点（逐件扫描提交）
POST   /api/m/scan/batch-stock-take                 — 扫码批量盘点

# 移动端拍照上传
POST   /api/m/upload/photo                          — 拍照上传（自动压缩）
POST   /api/m/upload/attachment                     — 单据附件上传

# 移动端消息
GET    /api/m/notifications                         — 消息列表
GET    /api/m/notifications/unread-count            — 未读数量
PUT    /api/m/notifications/{id}/read               — 标记已读
```

### 8.2 消息推送集成

不新增表，通过 `sys_config` 配置第三方推送服务参数：

- 邮件推送：SMTP 配置项
- 微信推送：公众号/小程序 AppId/AppSecret
- 钉钉推送：Webhook URL
- 短信推送：短信服务商 AccessKey

推送时机由各业务 Service 触发（如：提交审批→通知审批人、库存低于安全线→通知仓管、应收超期→通知业务员）。

### 8.3 客户/供应商门户

#### 表45: `portal_user` — 门户用户

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| username | VARCHAR(64) | ✓ | 用户名 |
| password | VARCHAR(200) | ✓ | 密码（BCrypt） |
| portal_type | VARCHAR(20) | ✓ | 门户类型: CUSTOMER(客户), SUPPLIER(供应商) |
| customer_id | BIGINT | | 关联客户ID |
| supplier_id | BIGINT | | 关联供应商ID |
| contact_name | VARCHAR(100) | | 联系人 |
| phone | VARCHAR(20) | | 手机号 |
| email | VARCHAR(100) | | 邮箱 |
| status | SMALLINT | ✓ | 状态: 0-停用 1-启用 |
| last_login_time | TIMESTAMP | | 最后登录时间 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(username)

#### 门户 API

```
# 门户认证
POST   /api/portal/login                            — 门户登录
POST   /api/portal/refresh                          — 刷新Token
GET    /api/portal/info                             — 获取个人信息

# 客户门户
GET    /api/portal/customer/orders                  — 查看订单
GET    /api/portal/customer/orders/{id}             — 订单详情+物流
GET    /api/portal/customer/deliveries              — 发货记录
GET    /api/portal/customer/statements              — 对账单
GET    /api/portal/customer/invoices                — 发票记录
GET    /api/portal/customer/receivable              — 应收账款

# 供应商门户
GET    /api/portal/supplier/orders                  — 查看采购订单
POST   /api/portal/supplier/orders/{id}/confirm     — 确认订单
GET    /api/portal/supplier/statements              — 对账单
GET    /api/portal/supplier/invoices                — 发票记录
GET    /api/portal/supplier/payable                 — 应付账款
POST   /api/portal/supplier/quotes/{inquiryId}      — 提交报价
```

---

## Phase 9: 扩展能力

### 9.1 合同管理

#### 表46: `biz_contract` — 合同

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| contract_no | VARCHAR(50) | ✓ | 合同编号 |
| contract_name | VARCHAR(200) | ✓ | 合同名称 |
| contract_type | VARCHAR(20) | ✓ | 类型: PURCHASE(采购合同), SALES(销售合同), SERVICE(服务合同), OTHER |
| counterparty_type | VARCHAR(20) | ✓ | 对方类型: CUSTOMER(客户), SUPPLIER(供应商) |
| counterparty_id | BIGINT | ✓ | 对方ID |
| counterparty_name | VARCHAR(200) | ✓ | 对方名称 |
| contract_amount | DECIMAL(18,2) | ✓ | 合同金额 |
| signed_amount | DECIMAL(18,2) | | 已结算金额 |
| start_date | DATE | ✓ | 开始日期 |
| end_date | DATE | ✓ | 结束日期 |
| signed_date | DATE | | 签订日期 |
| status | SMALLINT | ✓ | 状态: 0-草稿 1-执行中 2-已完成 3-已终止 4-已过期 |
| payment_terms | TEXT | | 付款条款 |
| delivery_terms | TEXT | | 交货条款 |
| responsible_person | VARCHAR(100) | | 负责人 |
| attachment_count | INT | | 附件数量 |
| remark | TEXT | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(contract_no)，INDEX(contract_type)，INDEX(counterparty_type, counterparty_id)，INDEX(status)

#### 表47: `biz_contract_item` — 合同明细

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| contract_id | BIGINT | ✓ | 合同ID |
| product_id | BIGINT | ✓ | 商品ID |
| quantity | DECIMAL(18,4) | ✓ | 数量 |
| unit_price | DECIMAL(18,2) | ✓ | 单价 |
| total_amount | DECIMAL(18,2) | ✓ | 金额 |
| delivered_quantity | DECIMAL(18,4) | | 已交付数量 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(contract_id)

#### 表48: `biz_contract_change_log` — 合同变更记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| contract_id | BIGINT | ✓ | 合同ID |
| change_type | VARCHAR(20) | ✓ | 变更类型: AMOUNT(金额), DATE(日期), TERMS(条款), OTHER |
| before_value | TEXT | | 变更前内容 |
| after_value | TEXT | | 变更后内容 |
| change_reason | VARCHAR(500) | | 变更原因 |
| changed_by | VARCHAR(64) | ✓ | 变更人 |
| changed_time | TIMESTAMP | ✓ | 变更时间 |
| deleted | SMALLINT | ✓ | 逻辑删除 |

**索引：** INDEX(contract_id)

### 9.2 客户关系管理 CRM

#### 表49: `crm_lead` — 销售线索

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| company_name | VARCHAR(200) | ✓ | 公司名称 |
| contact_name | VARCHAR(100) | | 联系人 |
| phone | VARCHAR(20) | | 电话 |
| email | VARCHAR(100) | | 邮箱 |
| source | VARCHAR(50) | | 线索来源: WEBSITE(官网), EXHIBITION(展会), REFERRAL(转介绍), AD(广告), OTHER |
| industry | VARCHAR(50) | | 行业 |
| address | VARCHAR(500) | | 地址 |
| status | SMALLINT | ✓ | 状态: 0-新线索 1-已联系 2-已确认 3-已转化 4-已关闭 |
| convert_to_customer_id | BIGINT | | 转化客户ID |
| owner_id | BIGINT | | 负责人ID |
| next_follow_date | DATE | | 下次跟进日期 |
| remark | TEXT | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(status)，INDEX(owner_id)，INDEX(source)

#### 表50: `crm_follow_record` — 跟进记录

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| lead_id | BIGINT | | 线索ID（顾客跟进可能为空） |
| customer_id | BIGINT | | 客户ID |
| follow_type | VARCHAR(20) | ✓ | 跟进方式: PHONE(电话), VISIT(拜访), EMAIL(邮件), MEETING(会议), OTHER |
| follow_date | DATE | ✓ | 跟进日期 |
| content | TEXT | ✓ | 跟进内容 |
| result | VARCHAR(500) | | 跟进结果 |
| next_plan | VARCHAR(500) | | 下一步计划 |
| followed_by | VARCHAR(64) | ✓ | 跟进人 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(lead_id)，INDEX(customer_id)，INDEX(follow_date)

#### 表51: `crm_opportunity` — 商机

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| name | VARCHAR(200) | ✓ | 商机名称 |
| customer_id | BIGINT | ✓ | 客户ID |
| expected_amount | DECIMAL(18,2) | ✓ | 预计金额 |
| probability | DECIMAL(3,0) | | 成交概率% (0-100) |
| stage | VARCHAR(20) | ✓ | 阶段: INITIAL(初步接触), NEEDS_ANALYSIS(需求分析), QUOTATION(报价), NEGOTIATION(谈判), WON(赢单), LOST(输单) |
| expected_close_date | DATE | | 预计成交日期 |
| owner_id | BIGINT | ✓ | 负责人ID |
| competitor | VARCHAR(200) | | 竞争对手 |
| remark | TEXT | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** INDEX(customer_id)，INDEX(stage)，INDEX(owner_id)

### 9.3 多门店/连锁管理

#### 表52: `bas_store` — 门店

| 字段 | 类型 | 必填 | 说明 |
|------|------|:--:|------|
| id | BIGSERIAL | ✓ | 主键 |
| store_code | VARCHAR(50) | ✓ | 门店编码 |
| store_name | VARCHAR(100) | ✓ | 门店名称 |
| warehouse_id | BIGINT | | 关联仓库ID（门店库存对应仓库） |
| address | VARCHAR(500) | | 门店地址 |
| phone | VARCHAR(20) | | 联系电话 |
| manager_name | VARCHAR(100) | | 店长姓名 |
| is_enabled | SMALLINT | ✓ | 启用状态 |
| opening_date | DATE | | 开业日期 |
| remark | VARCHAR(500) | | 备注 |
| deleted | SMALLINT | ✓ | 逻辑删除 |
| create_by/create_time/update_by/update_time | — | | 标准BaseEntity字段 |

**索引：** UNIQUE(store_code)，INDEX(warehouse_id)

现有仓库 `bas_warehouses` 增加字段：

| 新增字段 | 类型 | 说明 |
|---------|------|------|
| warehouse_type | VARCHAR(20) | 仓库类型: MAIN(总仓), STORE(门店仓), REGIONAL(区域仓) |
| parent_warehouse_id | BIGINT | 上级仓库（门店仓→区域仓→总仓） |

### 9.4 零售 POS（轻量版）

无需新增表，在销售订单 API 基础上增加零售快捷开单接口：

```
POST   /api/v1/pos/checkout                         — POS 收银结算
  请求: {storeId, items: [{productId, quantity, unitPrice}],
         paymentMethod, receivedAmount, customerId?}
  返回: {orderNo, totalAmount, changeAmount}

GET    /api/v1/pos/daily-summary/{storeId}          — POS 日结
POST   /api/v1/pos/daily-close/{storeId}            — POS 日清
```

---

## 附录 A: 完整表汇总

| 序号 | 表名 | 模块 | 说明 |
|:--:|------|------|------|
| 1 | bas_attribute_template | 基础资料 | 属性模板 |
| 2 | bas_attribute | 基础资料 | 属性定义 |
| 3 | bas_attribute_value | 基础资料 | 属性预设值 |
| 4 | bas_product_attribute | 基础资料 | 商品属性值关联 |
| 5 | pur_purchase_requisition | 采购 | 请购单 |
| 6 | pur_purchase_requisition_item | 采购 | 请购单明细 |
| 7 | pur_inquiry | 采购 | 询价单 |
| 8 | pur_inquiry_item | 采购 | 询价单明细 |
| 9 | pur_inquiry_supplier | 采购 | 询价供应商 |
| 10 | pur_quote_detail | 采购 | 报价明细 |
| 11 | pur_replenishment_suggestion | 采购 | 补货建议 |
| 12 | pur_expense_allocation | 采购 | 采购费用分摊 |
| 13 | pur_expense_allocation_detail | 采购 | 分摊明细 |
| 14 | pur_supplier_statement | 采购 | 供应商对账单 |
| 15 | pur_supplier_evaluation | 采购 | 供应商评估 |
| 16 | pur_supplier_evaluation_criteria | 采购 | 评估维度权重 |
| 17 | sal_quotation | 销售 | 报价单 |
| 18 | sal_quotation_item | 销售 | 报价单明细 |
| 19 | sal_exchange_order | 销售 | 换货单 |
| 20 | sal_exchange_return_item | 销售 | 换货退回明细 |
| 21 | sal_exchange_out_item | 销售 | 换货发出明细 |
| 22 | sal_commission_rule | 销售 | 提成规则 |
| 23 | sal_commission_rule_target | 销售 | 提成适用对象 |
| 24 | sal_commission_record | 销售 | 提成记录 |
| 25 | sal_customer_statement | 销售 | 客户对账单 |
| 26 | inv_stock_reservation | 库存 | 库存预留 |
| 27 | inv_expiry_alert | 库存 | 保质期预警 |
| 28 | inv_stock_aging | 库存 | 库龄快照 |
| 29 | fin_cost_calculation | 财务 | 成本计算记录 |
| 30 | fin_invoice | 财务 | 发票 |
| 31 | fin_expense_allocation | 财务 | 运营费用分摊 |
| 32 | fin_expense_allocation_detail | 财务 | 分摊明细 |
| 33 | rpt_report_template | 报表 | 报表模板 |
| 34 | rpt_export_record | 报表 | 导出记录 |
| 35 | sys_approval_flow | 系统 | 审批流定义 |
| 36 | sys_approval_node | 系统 | 审批节点 |
| 37 | sys_approval_instance | 系统 | 审批实例 |
| 38 | sys_approval_record | 系统 | 审批记录 |
| 39 | sys_notification | 系统 | 通知消息 |
| 40 | sys_notification_read | 系统 | 通知已读 |
| 41 | sys_config | 系统 | 系统配置 |
| 42 | sys_attachment | 系统 | 附件 |
| 43 | sys_print_template | 系统 | 打印模板 |
| 44 | sys_data_backup | 系统 | 备份记录 |
| 45 | portal_user | 门户 | 门户用户 |
| 46 | biz_contract | 扩展 | 合同 |
| 47 | biz_contract_item | 扩展 | 合同明细 |
| 48 | biz_contract_change_log | 扩展 | 合同变更 |
| 49 | crm_lead | 扩展 | 销售线索 |
| 50 | crm_follow_record | 扩展 | 跟进记录 |
| 51 | crm_opportunity | 扩展 | 商机 |
| 52 | bas_store | 扩展 | 门店 |

**涉及字段修改的现有表：**
- `sal_sales_orders` — 增加 `is_suspended` 挂单标记
- `bas_products` — 增加 `costing_method` 成本核算方法
- `inv_warning_configs` — 增加 `expiry_warning_days`, `expiry_warning_enabled`
- `bas_warehouses` — 增加 `warehouse_type`, `parent_warehouse_id`

---

## 附录 B: 目录结构规划

```
core/src/main/java/com/cc/core/
├── entity/
│   ├── bas/  (+ BasAttributeTemplate, BasAttribute, BasAttributeValue, BasProductAttribute)
│   │         (+ BasStore — 新增)
│   ├── pur/  (+ PurPurchaseRequisition, PurPurchaseRequisitionItem)
│   │         (+ PurInquiry, PurInquiryItem, PurInquirySupplier, PurQuoteDetail)
│   │         (+ PurReplenishmentSuggestion, PurExpenseAllocation, PurExpenseAllocationDetail)
│   │         (+ PurSupplierStatement, PurSupplierEvaluation, PurSupplierEvaluationCriteria)
│   ├── sal/  (+ SalQuotation, SalQuotationItem, SalExchangeOrder, SalExchangeReturnItem, SalExchangeOutItem)
│   │         (+ SalCommissionRule, SalCommissionRuleTarget, SalCommissionRecord, SalCustomerStatement)
│   ├── inv/  (+ InvStockReservation, InvExpiryAlert, InvStockAging)
│   ├── fin/  (+ FinCostCalculation, FinInvoice, FinExpenseAllocation, FinExpenseAllocationDetail)
│   ├── rpt/  (+ RptReportTemplate, RptExportRecord)
│   ├── sys/  (+ SysApprovalFlow, SysApprovalNode, SysApprovalInstance, SysApprovalRecord)
│   │         (+ SysNotification, SysNotificationRead, SysConfig, SysAttachment, SysPrintTemplate, SysDataBackup)
│   ├── portal/ (+ PortalUser)
│   ├── biz/  (+ BizContract, BizContractItem, BizContractChangeLog)
│   └── crm/  (+ CrmLead, CrmFollowRecord, CrmOpportunity)
├── mapper/   (对应Mapper接口)
├── service/  (对应Service接口)
└── dto/      (对应DTO/VO)

app/src/main/java/com/cc/app/
├── controller/
│   ├── bas/  (+ BasAttributeTemplateController, BasStoreController)
│   ├── pur/  (+ PurPurchaseRequisitionController, PurInquiryController, PurReplenishmentController, ...)
│   ├── sal/  (+ SalQuotationController, SalExchangeOrderController, SalCommissionController, ...)
│   ├── inv/  (+ InvStockReservationController, InvExpiryAlertController, InvStockAgingController, ...)
│   ├── fin/  (+ FinCostController, FinInvoiceController, FinExpenseAllocationController, ...)
│   ├── rpt/  (+ RptDashboardController, RptReportController, RptExportController)
│   ├── sys/  (+ SysApprovalController, SysNotificationController, SysConfigController, ...)
│   ├── portal/ (+ PortalAuthController, PortalCustomerController, PortalSupplierController)
│   ├── biz/  (+ BizContractController)
│   ├── crm/  (+ CrmLeadController, CrmOpportunityController)
│   └── m/    (+ 移动端专用Controller)
└── service/impl/  (对应Service实现)

app/src/main/resources/sql/
└── init-postgresql-v2.sql  (新增表建表脚本)
```
