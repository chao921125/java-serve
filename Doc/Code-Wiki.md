# Java Serve - Code Wiki

> 本文档为 `java-serve` 项目的结构化代码 Wiki，涵盖项目整体架构、模块职责、关键类与函数说明、依赖关系及项目运行方式。

---

## 一、项目概览

| 项 | 说明 |
|---|---|
| 项目名 | Java Serve（`java-serve`） |
| 定位 | 基于 Spring Boot 3 的多模块企业级后台管理系统（进销存 / ERP 业务中台） |
| 技术栈 | Java 21、Spring Boot 3.3.1、Spring Security 6、MyBatis-Plus 3.5、PostgreSQL 16 |
| 构建工具 | Maven 多模块（父 POM 统一版本管理） |
| 包结构根 | `com.cc` |
| 启动类 | [ServerApplication.java](file:///Users/huangchao/Work/GitHub/java-serve/app/src/main/java/com/cc/server/ServerApplication.java) |
| 默认端口 | 8080 |
| API 文档 | http://localhost:8080/docs (Swagger UI: /api) |

项目以"平台核心 + 业务模块 + 技术组件"的方式组织，业务覆盖系统管理、基础数据、采购、销售、库存、财务、CRM、报表、门户等 ERP 全链路能力，并集成了缓存、消息队列、任务调度、限流熔断、监控可观测性等基础设施。

---

## 二、整体架构

### 2.1 分层架构

系统采用经典的四层分层架构，各层职责清晰、单向依赖：

```
┌─────────────────────────────────────────────────────────┐
│  Controller 层 (app)   REST API，参数校验，统一响应 R<T>     │
├─────────────────────────────────────────────────────────┤
│  Service 层  (core 接口 / app 实现)  业务逻辑、事务、编排     │
├─────────────────────────────────────────────────────────┤
│  Mapper 层  (core)   MyBatis-Plus 数据访问 + XML 映射       │
├─────────────────────────────────────────────────────────┤
│  Database  PostgreSQL (Druid 连接池 / 动态数据源)           │
└─────────────────────────────────────────────────────────┘
        横切：Security/JWT、AOP(日志/数据权限)、异常处理、字段加密
```

- **Controller**：位于 `app` 模块，负责 HTTP 路由、参数绑定与校验、调用 Service、返回统一响应对象 `R<T>`。通用 CRUD 由 `BaseController` 提供。
- **Service**：接口定义在 `core` 模块，实现位于 `app` 模块。复杂业务逻辑（如销售订单审核校验信用额度/库存、订单状态流转、库存锁定/释放）在此层完成，并通过 `@Transactional` 管理事务。
- **Mapper**：MyBatis-Plus 的 `BaseMapper` 接口，位于 `core` 模块；复杂 SQL 通过 `app/src/main/resources/mapper/*.xml` 实现。
- **Entity / DTO / VO**：均位于 `core` 模块，分别对应数据库实体、入参传输对象、出参视图对象。

### 2.2 模块依赖拓扑

```
                 ┌──────────────────────────────────────┐
                 │                  app                 │  (启动 + Controller + Service 实现)
                 └──┬───────┬───────┬──────┬──────┬──────┘
                    │       │       │      │      │
        ┌───────────▼──┐ ┌──▼───┐ ┌▼────┐ │      │
        │framework-redis│ │  ... │ │gen  │ │      │   (各 framework-* 技术组件)
        └──────┬────────┘ └──┬───┘ └──┬──┘ │      │
               │             │        │    │      │
               ▼             ▼        ▼    ▼      ▼
               ┌──────────────────────────────┐
               │          framework            │  (安全/JWT/AOP/异常/工具)
               └───────────────┬───────────────┘
                              ▼
               ┌──────────────────────────────┐
               │             core              │  (实体/枚举/Mapper接口/Service接口)
               └──────────────────────────────┘
```

依赖方向：`app` → `framework`（及各 `framework-*`）→ `core`。`core` 是最底层的领域内核，不依赖任何业务实现。

---

## 三、模块组成与职责

项目由 10 个 Maven 子模块组成（见 [pom.xml](file:///Users/huangchao/Work/GitHub/java-serve/pom.xml)）。

### 3.1 业务/应用模块

#### `core` — 领域核心模块
> 描述：核心模块：实体、枚举、常量、VO、Mapper 接口、Service 接口

项目的领域内核，承载所有业务模型与契约定义，不含实现逻辑。

| 子包 | 职责 |
|---|---|
| `core.base` | 基础实体 `BaseEntity` |
| `core.entity.*` | 数据库实体（按业务域分包：sys/bas/sal/pur/inv/fin/crm/biz/rpt/portal） |
| `core.dto.*` | 数据传输对象（按业务域分包，含 Query/Save 等） |
| `core.vo` | 视图对象（`LoginVO`/`UserInfoVO`/`MenuTreeVO`/`DeptTreeVO` 等） |
| `core.mapper.*` | MyBatis-Plus Mapper 接口 |
| `core.service.*` | Service 接口定义 |
| `core.enums` | 枚举（`ResultCode`/`BusinessType`/`BusinessStatus`/`DataScopeEnum`/`MenuType`/`UserStatus`） |
| `core.constants` | 常量 `Constants` |

#### `app` — 应用启动模块
> 描述：应用模块：启动类、控制器、Service 实现、配置

包含 Spring Boot 启动类、所有 Controller、Service 实现类及配置文件，是最终打包可运行单元（`spring-boot-maven-plugin` 生成可执行 jar）。

| 子包 | 职责 |
|---|---|
| `com.cc.server` | 启动类 `ServerApplication`、认证/系统类 Controller 与 Service 实现 |
| `com.cc.app.controller.*` | 业务 Controller（按业务域分包） |
| `com.cc.app.service.impl.*` | 业务 Service 实现类 |

Controller 业务域划分：
- `sys`：系统管理（配置、附件、通知、审批流、打印模板、数据备份、数据导入）
- `bas`：基础数据（商品、品牌、单位、客户、供应商、仓库、店铺、分类、属性）
- `sal`：销售（订单、报价、发货、退货、收款、换货、POS、价格策略、提成、对账）
- `pur`：采购（订单、询价、收货、退货、付款、申请、报价、评估、对账、补货建议）
- `inv`：库存（库存、组装、盘点、调拨、库龄、预警、预留、扫描）
- `fin`：财务（账户、费用、发票、应收/应付、收/付款、其他收入、成本计算）
- `crm`：客户关系（线索、商机、跟进）
- `biz`：业务合同
- `rpt`：报表（仪表盘、利润分析、导出、模板）
- `portal`：门户（用户/客户/供应商认证）
- `m`：移动端

#### `generator` — 代码生成器
> 描述：基于 MyBatis-Plus Generator + Freemarker 的代码生成工具

入口 [CodeGenerator.java](file:///Users/huangchao/Work/GitHub/java-serve/generator/src/main/java/com/cc/generator/CodeGenerator.java)，根据数据库表结构一键生成 Entity / Mapper / Service / Controller。

### 3.2 技术组件模块（framework-*）

| 模块 | 职责 | 关键依赖 |
|---|---|---|
| `framework` | 框架核心：安全(JWT/Security)、AOP(日志/数据权限)、异常处理、配置、工具类 | spring-security, jjwt, mybatis-plus, dynamic-datasource, oshi, springdoc |
| `framework-redis` | 多级缓存(Caffeine+Redis) + Redisson 分布式锁 + 限流 | redisson, caffeine |
| `framework-filestorage` | 统一文件存储抽象（本地/MinIO/OSS/COS） | minio, cos_api, aliyun-sdk-oss |
| `framework-sentinel` | 限流、熔断、降级 | sentinel-core |
| `framework-monitor` | 监控可观测性（Actuator + Micrometer + Prometheus） | micrometer-registry-prometheus |
| `framework-rocketmq` | 消息队列：生产者/消费者封装、消息幂等、领域事件 | rocketmq-spring-boot-starter |
| `framework-xxljob` | 分布式任务调度：XXL-Job 集成、分片执行 | xxl-job-core |

> 说明：`framework-*` 组件模块均以 `@ConditionalOnClass` 等条件装配，按需启用。开发环境默认禁用 Redis，通过 `application-dev.yml` 的 `autoconfigure.exclude` 控制开关。

---

## 四、关键类与函数说明

### 4.1 基础设施（framework 模块）

#### 统一响应 `R<T>` — [R.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/base/R.java)
所有接口统一返回结构，字段：`code` / `msg` / `data` / `timestamp`。
- `R.ok()` / `R.ok(data)` — 成功响应
- `R.fail(msg)` / `R.fail(ResultCode)` — 失败响应
- `R.of(code, msg, data)` — 通用构建
- `isSuccess()` — 判断成功（code == 0）

状态码由 [ResultCode](file:///Users/huangchao/Work/GitHub/java-serve/core/src/main/java/com/cc/core/enums/ResultCode.java) 定义：`SUCCESS(0)` / `ERROR(500)` / `UNAUTHORIZED(401)` / `FORBIDDEN(403)` / `TOKEN_EXPIRED(4010)` 等。

#### 基础实体 `BaseEntity` — [BaseEntity.java](file:///Users/huangchao/Work/GitHub/java-serve/core/src/main/java/com/cc/core/base/BaseEntity.java)
所有数据库实体的父类，提供通用字段与 MyBatis-Plus 注解：
- `id`（自增主键）、`createBy` / `createTime` / `updateBy` / `updateTime`（自动填充）、`remark`、`deleted`（`@TableLogic` 逻辑删除）、`params`（非表字段，用于数据权限注入）

#### 通用控制器 `BaseController<T, S>` — [BaseController.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/base/BaseController.java)
提供标准 CRUD 端点，业务控制器继承即可获得：
- `GET /{id}` 详情、`POST` 新增、`PUT` 修改、`DELETE /{id}` 删除、`DELETE /batch` 批量删除
- `GET /list` 列表、`GET /page` 分页（支持 `PageQuery` 排序参数）

#### 分页 `PageQuery` / `PageResult` — [PageQuery.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/base/PageQuery.java)
`PageQuery`：`pageNum` / `pageSize` / `orderByColumn` / `isAsc`；`PageResult`：`total` / `list` / `pageNum` / `pageSize` / `getTotalPages()`。

### 4.2 安全与认证

#### `SecurityConfig` — [SecurityConfig.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/security/SecurityConfig.java)
Spring Security 6 配置：
- 无状态 Session（`STATELESS`）、禁用 CSRF/CORS
- 白名单：`/api/auth/**`、Swagger、Actuator health
- `DaoAuthenticationProvider` + `BCryptPasswordEncoder`
- `JwtAuthenticationFilter` 置于 `UsernamePasswordAuthenticationFilter` 之前
- 启用方法级安全 `@EnableMethodSecurity`

#### `JwtUtil` — [JwtUtil.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/security/JwtUtil.java)
JWT 工具（基于 jjwt 0.12）：
- `generateToken(userId, username, extraClaims)` — 生成 Token（默认 24h）
- `generateRefreshToken(userId, username)` — 刷新 Token（默认 7 天）
- `extractUsername` / `extractUserId` — 提取声明
- `isTokenValid(token)` — 校验有效性（捕获签名/过期/格式异常）

密钥配置：`application.security.jwt.secret-key`（Base64，256 位）。

#### `JwtAuthenticationFilter` — [JwtAuthenticationFilter.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/security/JwtAuthenticationFilter.java)
继承 `OncePerRequestFilter`，从 `Authorization: Bearer xxx` 提取 Token，校验通过后注入 `SecurityContext`。

#### `LoginUser` / `UserDetailsServiceImpl` / `SecurityUtil`
- [LoginUser](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/security/LoginUser.java)：实现 `UserDetails`，封装 `userId`/`username`/`deptId`/`permissions`/`roles`。
- [UserDetailsServiceImpl](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/security/UserDetailsServiceImpl.java)：登录时加载用户 → 角色 → 权限标识，构建 `LoginUser`。
- [SecurityUtil](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/security/SecurityUtil.java)：静态工具，从 `SecurityContext` 获取当前用户 `getUserId()`/`getUsername()`/`hasPerm()`/`hasRole()`。

#### `AuthController` — [AuthController.java](file:///Users/huangchao/Work/GitHub/java-serve/app/src/main/java/com/cc/server/controller/AuthController.java)
认证端点（`/api/auth`）：
- `POST /login` 登录（`AuthenticationManager` 认证 → 签发 Token/RefreshToken → 记录登录日志）
- `POST /register` 注册
- `POST /refresh` 刷新 Token
- `GET /info` 当前用户信息
- `GET /menus` 当前用户菜单
- `POST /changePassword` 修改密码
- `POST /logout` 登出

### 4.3 AOP 切面

#### `LogAspect` — [LogAspect.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/aop/LogAspect.java)
配合 `@Log` 注解，`@Around` 拦截 Controller 方法，自动记录操作日志到 `log_operation` 表：模块标题、业务类型、操作人、请求方法/URL/IP/参数、耗时、成功/失败状态、异常栈。配合注解 [Log](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/annotation/Log.java)（属性：`title` / `businessType` / `isSaveRequestData` / `isSaveResponseData`）。

#### `DataScopeAspect` — [DataScopeAspect.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/aop/DataScopeAspect.java)
配合 `@DataScope` 注解，`@Before` 在 SQL 执行前按用户数据权限动态拼接部门过滤条件，注入到参数对象的 `params.dataScope`，XML 中以 `${params.dataScope}` 拼接。五级数据范围：
1. 全部数据（不过滤）
2. 自定义（角色关联的部门）
3. 本部门
4. 本部门及以下
5. 仅本人

管理员（`admin` 角色）跳过过滤。

### 4.4 异常处理

#### `GlobalExceptionHandler` — [GlobalExceptionHandler.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/exception/GlobalExceptionHandler.java)
`@RestControllerAdvice` 全局异常处理，统一转 `R<Void>`：
- `ServiceException` → 业务码
- `MethodArgumentNotValidException` / `BindException` → 400 参数校验
- `AuthenticationException` → 401
- `AccessDeniedException` → 403
- `IllegalArgumentException` → 400
- `Exception` → 500

#### `ServiceException` — [ServiceException.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/exception/ServiceException.java)
业务异常，提供 `badRequest()` / `notFound()` 等静态工厂。

### 4.5 MyBatis-Plus 配置

#### `MybatisPlusConfig` — [MybatisPlusConfig.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/MybatisPlusConfig.java)
- `@MapperScan("com.cc.core.mapper")` 扫描 Mapper
- 注册 `PaginationInnerInterceptor` 分页插件

#### `MyMetaObjectHandler` — [MyMetaObjectHandler.java](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/MyMetaObjectHandler.java)
自动填充 `createTime` / `updateTime` / `createBy` / `updateBy`（操作人从 `SecurityUtil` 获取，异常回退 `system`）。

全局配置（`application.yml`）：逻辑删除字段 `deleted`（0 未删除 / 1 已删除）、下划线转驼峰、`id-type: auto`。

### 4.6 其他配置

| 类 | 作用 |
|---|---|
| [CorsConfig](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/CorsConfig.java) | 跨域：允许所有源、所有方法、携带凭证 |
| [DynamicDataSourceConfig](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/DynamicDataSourceConfig.java) | 动态数据源（读写分离），`@ConditionalOnClass` 按需启用，配合 `@DS` / `@Master` / `@Slave` 注解 |
| [FieldEncryptConfig](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/config/FieldEncryptConfig.java) | 字段加密（AES-256），未配置密钥时开发环境自动生成 |

### 4.7 业务实现示例

#### `SalSalesOrderServiceImpl` — [SalSalesOrderServiceImpl.java](file:///Users/huangchao/Work/GitHub/java-serve/app/src/main/java/com/cc/app/service/impl/sal/SalSalesOrderServiceImpl.java)
销售订单核心业务实现，体现典型业务编排模式（继承 `ServiceImpl<Mapper, Entity>`）：
- `page(query)`：多条件分页（订单号/客户/状态/日期）
- `create(dto)`：生成订单号（`SO` + 时间戳 + 随机数）→ 保存主表 → 计算明细金额（含税/不含税/税额拆分）→ 回写合计
- `update(id, dto)`：仅草稿可改，先删旧明细再插新明细
- `approve(id)`：审核——校验客户信用额度（未核销应收 + 本单 ≤ 信用额度）→ 校验库存可用量（`quantity - lockedQuantity`）→ 状态流转待审→已审 → **锁定库存**（`locked_quantity += 订单数量`）
- `close(id)`：关闭——释放未发货部分锁定的库存
- `reject` / `suspend` / `resume`：状态流转与挂单管理

> 该实现展示了订单状态机：`0草稿 → 1待审核 → 2已审核 → 3部分发货 → 4已完成 / 5已关闭`，以及销售与库存、财务（应收）的跨域联动。

#### `SysUserServiceImpl` — [SysUserServiceImpl.java](file:///Users/huangchao/Work/GitHub/java-serve/app/src/main/java/com/cc/server/service/impl/SysUserServiceImpl.java)
用户管理：用户名唯一校验、BCrypt 密码加密、用户-角色-部门-岗位关联维护、重置/修改密码、用户详情聚合（VO）。

### 4.8 技术组件关键点

| 模块 | 关键点 |
|---|---|
| `framework-redis` | [RedisConfig](file:///Users/huangchao/Work/GitHub/java-serve/framework-redis/src/main/java/com/cc/redis/config/RedisConfig.java) Jackson 序列化；[`@RateLimit`](file:///Users/huangchao/Work/GitHub/java-serve/framework-redis/src/main/java/com/cc/redis/annotation/RateLimit.java) 限流注解（滑动窗口/令牌桶）；`@MultiCache` 多级缓存 |
| `framework-filestorage` | 抽象本地/MinIO/OSS/COS，配置 `file-storage.type` |
| `framework-monitor` | Actuator + Prometheus，端点 `/actuator` |
| `framework-rocketmq` | RocketMQ 生产/消费封装、消息幂等 |
| `framework-xxljob` | XXL-Job 调度，[ShardingUtil](file:///Users/huangchao/Work/GitHub/java-serve/framework-xxljob/src/main/java/com/cc/xxljob/ShardingUtil.java) 分片 |
| `framework-sentinel` | 限流熔断降级 |

---

## 五、依赖关系

### 5.1 内部模块依赖

```
app ──► framework ──► core
app ──► framework-redis ──► core
app ──► framework-filestorage ──► core
app ──► framework-sentinel ──► framework ──► core
app ──► framework-monitor ──► core
app ──► framework-rocketmq ──► core
app ──► framework-xxljob ──► core
app ──► generator
```

- `core` 是最底层内核，无内部依赖。
- `framework` 依赖 `core`；`framework-sentinel` 同时依赖 `core` 与 `framework`。
- `app` 聚合所有模块，是唯一可启动模块。

### 5.2 主要外部依赖

根 POM [pom.xml](file:///Users/huangchao/Work/GitHub/java-serve/pom.xml) 统一版本管理：

| 类别 | 依赖 | 版本 |
|---|---|---|
| 框架 | spring-boot-starter-parent | 3.3.1 |
| 持久层 | mybatis-plus-spring-boot3-starter | 3.5.15 |
| 持久层 | dynamic-datasource-spring-boot3-starter | 4.3.1 |
| 数据库 | postgresql / druid-spring-boot-starter | 42.7.3 / 1.2.25 |
| 安全 | jjwt-api/impl/jackson | 0.12.6 |
| 工具 | lombok / mapstruct / commons-lang3 / commons-io | 1.18.36 / 1.5.5 / 3.14.0 / 2.16.1 |
| JSON | fastjson2 | 2.0.51 |
| 文档 | springdoc-openapi-starter-webmvc-ui | 2.6.0 |
| Excel | easyexcel / poi(-ooxml) | 3.3.4 / 5.3.0 |
| 模板 | freemarker | 2.3.33 |
| 监控 | oshi-core | 6.6.1 |
| 中间件 | rocketmq-spring-boot-starter / xxl-job-core | 2.3.0 / 2.4.1 |
| 缓存/锁 | redisson / caffeine | 3.33.0 |
| 文件存储 | minio / cos_api / aliyun-sdk-oss | 8.5.10 / 5.6.212 / 3.17.4 |
| 限流 | sentinel-core | 1.8.7 |

---

## 六、数据模型与业务域

实体按业务域组织在 `core.entity.*` 下，SQL 初始化脚本位于 `app/src/main/resources/sql/`：

| 业务域 | 前缀 | 代表实体 | SQL 脚本 |
|---|---|---|---|
| 系统 | sys_ | SysUser / SysRole / SysMenu / SysDepartment / SysPost / SysDictionary / SysConfig | init-postgresql-v2-system.sql |
| 基础数据 | bas_ | BasProduct / BasBrand / BasUnit / BasCustomer / BasSupplier / BasWarehouse | init-postgresql-v2-attributes.sql |
| 销售 | sal_ | SalSalesOrder / SalSalesDelivery / SalSalesReturn / SalSalesReceipt | init-postgresql-v2-sales.sql |
| 采购 | pur_ | PurPurchaseOrder / PurPurchaseReceipt / PurPurchaseReturn / PurPurchasePayment | init-postgresql-v2-purchase.sql |
| 库存 | inv_ | InvInventory / InvStockTake / InvStockTransfer / InvAssembly | init-postgresql-v2-inventory.sql |
| 财务 | fin_ | FinAccount / FinReceivable / FinPayable / FinReceipt / FinPayment / FinExpense | init-postgresql-v2-finance.sql |
| CRM | crm_ | CrmLead / CrmOpportunity / CrmFollowRecord | init-postgresql-business.sql |
| 合同 | biz_ | BizContract / BizContractItem / BizContractChangeLog | init-postgresql-business.sql |
| 报表 | rpt_ | RptReportTemplate / RptExportRecord | init-postgresql-v2-report.sql |
| 门户 | portal_ | PortalUser | init-postgresql-v2-portal.sql |
| 日志 | log_ | LogLogin / LogOperation | init-postgresql-v2-system.sql |

主初始化脚本：[init-postgresql.sql](file:///Users/huangchao/Work/GitHub/java-serve/app/src/main/resources/sql/init-postgresql.sql)，扩展脚本以 `init-postgresql-v2-*-*.sql` 形式分域管理。

### Mapper XML 模式
复杂查询通过 XML 实现（位于 `app/src/main/resources/mapper/`），如 [SysUserMapper.xml](file:///Users/huangchao/Work/GitHub/java-serve/app/src/main/resources/mapper/SysUserMapper.xml) 的 `selectUserPage`，使用 `<where>` + `<if>` 动态拼接，并通过 `${params.dataScope}` 注入数据权限过滤条件。

---

## 七、项目运行方式

### 7.1 环境要求
- JDK 21
- Maven 3.6+
- PostgreSQL 16（开发也可使用 Docker）
- Redis 7（可选，开发环境默认禁用）
- RocketMQ / XXL-Job（按需启用）

### 7.2 Docker 一键启动中间件

[compose.yaml](file:///Users/huangchao/Work/GitHub/java-serve/compose.yaml) 提供完整中间件栈：

```bash
docker compose up -d
```

包含服务：
- `postgres:16-alpine`（库 `serve` / 用户 `serve` / 密码 `serve123`，端口 5432，自动执行 init 脚本）
- `redis:7-alpine`（端口 6379）
- `rocketmq:5.3.0`（namesrv 9876 + broker 10911）
- `xxl-job-admin:2.4.1`（端口 9080）

### 7.3 数据库准备

修改 `app/src/main/resources/application-dev.yml` 中数据源连接（默认 `localhost:5432/serve`）。执行 `resources/sql/` 下的初始化脚本建表与初始化数据。

### 7.4 编译打包

```bash
# 分析依赖
mvn dependency:tree

# 开发环境打包（默认 profile=dev）
mvn clean package -Dmaven.test.skip=true

# 指定 profile 打包
mvn clean package -P dev   -Dmaven.test.skip=true
mvn clean package -P prod  -Dmaven.test.skip=true
```

Maven Profile：`dev`（默认激活）/ `test` / `prod`，对应 `application-{profile}.yml`。

### 7.5 IDE 运行
直接运行 [ServerApplication.java](file:///Users/huangchao/Work/GitHub/java-serve/app/src/main/java/com/cc/server/ServerApplication.java) 的 `main` 方法，启动成功后访问 http://localhost:8080/docs 查看 API 文档。

### 7.6 生产部署

[run.sh](file:///Users/huangchao/Work/GitHub/java-serve/run.sh) / [run.bat](file:///Users/huangchao/Work/GitHub/java-serve/run.bat) 提供服务管理脚本（JVM 参数：`-Xms512m -Xmx1024m`，时区 `Asia/Shanghai`）：

```bash
./run.sh start     # 启动
./run.sh stop      # 停止
./run.sh restart   # 重启
./run.sh status    # 状态
```

### 7.7 关键配置项（application.yml）

| 配置 | 说明 |
|---|---|
| `server.port` | 8080 |
| `mybatis-plus.*` | Mapper 扫描 `classpath*:mapper/**/*.xml`，实体别名包 `com.cc.core.entity`，逻辑删除 |
| `application.security.jwt.*` | JWT 密钥与过期时间（access 24h / refresh 7d） |
| `file-storage.*` | 文件存储类型与本地路径 |
| `xxl.job.*` | XXL-Job 调度中心地址与执行器配置 |
| `rocketmq.*` | RocketMQ name-server 与生产者组 |
| `management.*` | Actuator 暴露 health/info/metrics/prometheus |
| `springdoc.*` | API 文档路径 `/docs`（UI `/api`） |

---

## 八、横切机制与约定速查

### 8.1 注解一览

| 注解 | 位置 | 作用 |
|---|---|---|
| `@Log` | Controller 方法 | 自动记录操作日志（[LogAspect](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/aop/LogAspect.java)） |
| `@DataScope` | Service 方法 | 数据权限过滤注入（[DataScopeAspect](file:///Users/huangchao/Work/GitHub/java-serve/framework/src/main/java/com/cc/framework/aop/DataScopeAspect.java)） |
| `@Anonymous` | 方法/类 | 匿名访问（无需认证） |
| `@DS` / `@Master` / `@Slave` | 方法/类 | 动态数据源切换 |
| `@RateLimit` | 方法 | Redis 限流（滑动窗口/令牌桶） |
| `@MultiCache` | 方法 | 多级缓存（Caffeine + Redis） |
| `@PreAuthorize` | 方法 | Spring Security 方法级权限 |

### 8.2 开发约定
- 实体继承 `BaseEntity`，表名 `@TableName`，命名按业务域前缀（`sal_` / `pur_` ...）。
- Controller 统一返回 `R<T>`；通用 CRUD 继承 `BaseController`，复杂业务自定义端点。
- Service 接口在 `core`，实现 `extends ServiceImpl<Mapper, Entity>` 放 `app`，跨表操作加 `@Transactional(rollbackFor = Exception.class)`。
- 复杂查询写 XML，分页用 `Page` + `LambdaQueryWrapper`，数据权限用 `${params.dataScope}`。
- 密码使用 `BCryptPasswordEncoder`，禁止明文存储。

---

## 九、目录结构总览

```
java-serve/
├── pom.xml                     # 父 POM（版本统一管理）
├── compose.yaml                # Docker 中间件编排
├── run.sh / run.bat            # 部署启停脚本
├── Doc/                        # 项目文档
├── core/                       # 领域核心（实体/枚举/Mapper接口/Service接口/DTO/VO）
├── framework/                  # 框架核心（安全/JWT/AOP/异常/配置/工具）
├── framework-redis/            # 多级缓存 + 分布式锁 + 限流
├── framework-filestorage/      # 文件存储抽象
├── framework-sentinel/         # 限流熔断
├── framework-monitor/          # 监控可观测性
├── framework-rocketmq/         # 消息队列
├── framework-xxljob/           # 任务调度
├── generator/                  # 代码生成器
└── app/                        # 应用启动（Controller + Service 实现 + 配置 + SQL）
    └── src/main/
        ├── java/com/cc/
        │   ├── server/         # 启动类 + 认证/系统 Controller & Service 实现
        │   └── app/            # 业务 Controller & Service 实现（按域分包）
        └── resources/
            ├── application*.yml
            ├── mapper/         # MyBatis XML
            └── sql/            # 数据库初始化脚本
```

---

*文档基于源码静态分析生成，反映当前仓库状态。如代码演进请同步更新。*
