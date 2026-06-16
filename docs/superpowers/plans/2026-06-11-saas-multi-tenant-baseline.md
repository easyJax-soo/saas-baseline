# SaaS Multi-Tenant Baseline Code Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create a SaaS multi-tenant baseline project with desensitized code from reference project, including backend (auth-service, gateway-service, system-service, frame-service) and frontend (React + Umi + Ant Design).

**Architecture:** Maven multi-module backend with common-library + module-service structure; React frontend with Umi 4.x and Ant Design 5.x; Single SQL initialization file for multi-tenant tables.

**Tech Stack:** Spring Boot 4.0, Spring Cloud 2024.x, Java 17, MyBatis-Plus 3.5.x, Sa-Token 1.44.x, MySQL 5.7+, React 18, Umi 4.x, Ant Design 5.x

---

## File Structure

```
D:\project\
├── baseline-backend/                    # Backend baseline project
│   ├── pom.xml                          # Parent POM (com.baseline)
│   ├── common-library/                  # Shared library modules
│   │   ├── pom.xml
│   │   ├── cloud-common-core/           # Core utilities, exceptions, constants
│   │   ├── cloud-common-api/            # API abstraction (feign/local)
│   │   │   ├── cloud-common-feign-api/
│   │   │   └── cloud-common-local-api/
│   │   ├── cloud-common-biz/           # Common DTOs, BaseController
│   │   ├── cloud-common-mybatis/       # MyBatis-Plus, dynamic datasource
│   │   ├── cloud-common-security/       # Sa-Token authentication
│   │   ├── cloud-common-redis/          # Redis templating
│   │   ├── cloud-common-web/            # OpenFeign, Nacos discovery
│   │   ├── cloud-common-log/            # Logging infrastructure
│   │   ├── cloud-common-swagger/        # Knife4j documentation
│   │   ├── cloud-common-oss/            # MinIO object storage
│   │   ├── cloud-common-excel/          # EasyExcel
│   │   ├── cloud-common-utils/          # Utilities
│   │   └── cloud-common-jackson/        # Jackson JSON
│   ├── module-service/
│   │   ├── pom.xml
│   │   ├── auth-service/                # Authentication service
│   │   ├── gateway-service/             # API Gateway
│   │   ├── system-service/             # System management (user, role, menu, tenant)
│   │   └── frame-service/              # Monolithic entry point
│   └── sql/
│       └── baseline_system.sql        # Unified SQL (DDL + seed data)
│
└── baseline-front/                     # Frontend baseline project
    └── (copied from sihui-village-govern-frontend with config changes)
```

---

## Desensitization Rules

| Pattern | Replace | Example |
|---------|---------|---------|
| `com.gzhaochuan` | `com.baseline` | Package names |
| `hc-cloud-` | `cloud-common-` | Module artifactIds |
| `hc_competence` | `baseline_system` | Database/schema name |
| `HC_DEV_V3:` | `baseline:` | Redis key prefixes |
| `sysLogo`, `sysVersion` keys | Keep as-is | Config values |
| Internal business data | Remove | Test records, dummy entries |

---

## Phase 1: Backend Project Foundation

### Task 1: Create Backend Parent POM

**Files:**
- Create: `D:\project\baseline-backend\pom.xml`

- [ ] **Step 1: Create parent POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.baseline</groupId>
    <artifactId>baseline-backend</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>

    <name>baseline-backend</name>
    <description>SaaS Multi-Tenant Baseline Backend</description>

    <modules>
        <module>common-library</module>
        <module>module-service</module>
    </modules>

    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring-boot.version>4.0.0</spring-boot.version>
        <spring-cloud.version>2024.0.0</spring-cloud.version>
        <sa-token.version>1.44.0</sa-token.version>
        <mybatis-plus.version>3.5.8</mybatis-plus.version>
        <druid.version>1.2.23</druid.version>
        <hutool.version>5.8.30</hutool.version>
        <fastjson2.version>2.0.52</fastjson2.version>
        <easyexcel.version>3.3.6</easyexcel.version>
        <knife4j.version>4.5.0</knife4j.version>
        <minio.version>8.5.14</minio.version>
        <dynamic-datasource.version>3.6.1</dynamic-datasource.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- Spring Boot -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- Cloud Common Modules -->
            <dependency>
                <groupId>com.baseline</groupId>
                <artifactId>cloud-common-core</artifactId>
                <version>${project.version}</version>
            </dependency>
            <!-- ... (other cloud-common modules) -->
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Task 2: Create common-library Module Structure

**Files:**
- Create: `D:\project\baseline-backend\common-library\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-core\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-api\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-biz\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-mybatis\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-security\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-redis\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-web\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-log\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-swagger\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-oss\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-excel\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-utils\pom.xml`
- Create: `D:\project\baseline-backend\common-library\cloud-common-jackson\pom.xml`

- [ ] **Step 1: Create common-library parent POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.baseline</groupId>
        <artifactId>baseline-backend</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>cloud-common</artifactId>
    <packaging>pom</packaging>
    <description>Cloud Common Library Parent</description>

    <modules>
        <module>cloud-common-core</module>
        <module>cloud-common-api</module>
        <module>cloud-common-biz</module>
        <module>cloud-common-mybatis</module>
        <module>cloud-common-security</module>
        <module>cloud-common-redis</module>
        <module>cloud-common-web</module>
        <module>cloud-common-log</module>
        <module>cloud-common-swagger</module>
        <module>cloud-common-oss</module>
        <module>cloud-common-excel</module>
        <module>cloud-common-utils</module>
        <module>cloud-common-jackson</module>
    </modules>
</project>
```

### Task 3: Migrate cloud-common-core with Desensitization

**Files:**
- Create: `D:\project\baseline-backend\common-library\cloud-common-core\src\main\java\com\baseline\core\...`
- (Migrated from: `D:\project\sihui-digital-village-backend\common-library\hc-cloud-core`)

- [ ] **Step 1: Copy and desensitize source files**

Copy all files from `hc-cloud-core` to `cloud-common-core`, replacing:
- Package `com.gzhaochuan.core` → `com.baseline.core`
- Any `hc-cloud` references → `cloud-common`
- `gzhaochuan` strings → `baseline`

### Task 4: Migrate cloud-common-api (feign/local)

**Files:**
- Create: `D:\project\baseline-backend\common-library\cloud-common-api\cloud-common-feign-api\...`
- Create: `D:\project\baseline-backend\common-library\cloud-common-api\cloud-common-local-api\...`

- [ ] **Step 1: Copy and desensitize feign-api module**
- [ ] **Step 2: Copy and desensitize local-api module**

### Task 5: Migrate Remaining cloud-common Modules

**Files:**
- Migrate: cloud-common-biz, cloud-common-mybatis, cloud-common-security, cloud-common-redis, cloud-common-web, cloud-common-log, cloud-common-swagger, cloud-common-oss, cloud-common-excel, cloud-common-utils, cloud-common-jackson

- [ ] **Step 1: Migrate each module with desensitization**

For each module:
1. Copy source files
2. Replace package names: `com.gzhaochuan` → `com.baseline`
3. Replace module references: `hc-cloud-` → `cloud-common-`
4. Update `pom.xml` dependencies

---

## Phase 2: Service Modules Migration

### Task 6: Create module-service POM

**Files:**
- Create: `D:\project\baseline-backend\module-service\pom.xml`

- [ ] **Step 1: Create module-service POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.baseline</groupId>
        <artifactId>baseline-backend</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>module-service</artifactId>
    <packaging>pom</packaging>

    <modules>
        <module>auth-service</module>
        <module>gateway-service</module>
        <module>system-service</module>
        <module>frame-service</module>
    </modules>
</project>
```

### Task 7: Migrate auth-service

**Files:**
- Create: `D:\project\baseline-backend\module-service\auth-service\...`
- (Migrated from: `D:\project\sihui-digital-village-backend\module-service\auth-service`)

**Key Components:**
- `AuthApplication.java` - Spring Boot entry
- `controller/CaptchaController.java`
- `controller/UserAuthController.java`
- `controller/admin/Oauth2ServerController.java`
- `service/impl/UserAuthServiceImpl.java`
- `provider/AccountPasswordAuthHandler.java`

- [ ] **Step 1: Copy source files and desensitize**
- [ ] **Step 2: Update package names**
- [ ] **Step 3: Update pom.xml dependencies**

### Task 8: Migrate gateway-service

**Files:**
- Create: `D:\project\baseline-backend\module-service\gateway-service\...`
- (Migrated from: `D:\project\sihui-digital-village-backend\module-service\gateway-service`)

**Key Components:**
- `GatewayApplication.java`
- `config/SwaggerProvider.java`
- `config/SaTokenConfigure.java`
- `filter/ServiceNameGlobalFilter.java`
- `filter/XssFilter.java`
- `handler/SentinelFallbackHandler.java`

- [ ] **Step 1: Copy source files and desensitize**
- [ ] **Step 2: Update package names**
- [ ] **Step 3: Update pom.xml dependencies**

### Task 9: Migrate system-service

**Files:**
- Create: `D:\project\baseline-backend\module-service\system-service\...`
- (Migrated from: `D:\project\sihui-digital-village-backend\module-service\system-service`)

**Key Components:**
- `SystemApplication.java`
- `controller/admin/SysUserController.java`
- `controller/admin/SysRoleController.java`
- `controller/admin/SysMenuController.java`
- `controller/admin/SysDeptController.java`
- `controller/admin/SysTenantController.java`
- `controller/admin/SysConfigController.java`
- `controller/feign/*FeignController.java`

- [ ] **Step 1: Copy source files and desensitize**
- [ ] **Step 2: Update package names**
- [ ] **Step 3: Update pom.xml dependencies**

### Task 10: Migrate frame-service (Monolithic Entry)

**Files:**
- Create: `D:\project\baseline-backend\module-service\frame-service\...`
- (Migrated from: `D:\project\sihui-digital-village-backend\module-service\frame-service`)

**Key Components:**
- `FrameApplication.java`
- `config/SaTokenConfigure.java`
- `config/GlobalCorsConfig.java`
- `filter/XssFilter.java`
- `filter/ServiceNameFilter.java`

- [ ] **Step 1: Copy source files and desensitize**
- [ ] **Step 2: Update to use local-api instead of feign-api**
- [ ] **Step 3: Update pom.xml dependencies**

---

## Phase 3: SQL Generation

### Task 11: Generate baseline_system.sql

**Files:**
- Create: `D:\project\baseline-backend\sql\baseline_system.sql`

**SQL Requirements:**
1. Based on `hc_competence_v3_1007.sql` with enhancements from `system_mysql.sql`
2. Add `tenant_id` column to `sys_user` table (missing in v3_1007)
3. All DDL must include `AUTO_INCREMENT`
4. No hardcoded IDs in INSERT statements - use `SELECT` subqueries
5. Remove meaningless test data
6. Remove `sys_project` related tables (business-specific)
7. Remove `sys_member*` tables (business-specific)
8. Remove `sys_user_real_name_auth`, `sys_user_third_party_bind` (business-specific)

**Tables to Include:**

| Table | Purpose |
|-------|---------|
| `oauth_client_details` | OAuth2 clients |
| `sys_config` | System configuration |
| `sys_config_group` | Config groups |
| `sys_dept` | Department/org structure |
| `sys_dict_data` | Dictionary data |
| `sys_dict_type` | Dictionary types |
| `sys_logininfor` | Login logs |
| `sys_menu` | Menu/permission tree |
| `sys_oplog` | Operation logs |
| `sys_permission` | Fine-grained permissions |
| `sys_post` | Job positions |
| `sys_role` | Roles |
| `sys_role_dept` | Role-Department mapping |
| `sys_role_menu` | Role-Menu mapping |
| `sys_role_permission` | Role-Permission mapping |
| `sys_tenant` | Tenant registry |
| `sys_tenant_menu` | Tenant-Menu assignment |
| `sys_tenant_permission` | Tenant-Permission assignment |
| `sys_tenant_user` | Tenant-User binding |
| `sys_user` | Users (add tenant_id) |
| `sys_user_post` | User-Post mapping |
| `sys_user_role` | User-Role mapping |

**Seed Data Requirements:**

1. **sys_tenant**: Default tenant (id=1)
2. **sys_dept**: Root department
3. **sys_role**: admin role (id=1), with role_key='admin'
4. **sys_user**: admin user (account='admin', password hashed), with tenant_id=0
5. **sys_menu**: System management menus (Home, System Management, User Management, Role Management, Menu Management, Permission Management, Dept Management, Post Management, Dict Management, System Config, Log Management, Tenant Management, Tenant User)
6. **sys_role_menu**: Admin role has all menus

**INSERT Pattern for role_menu:**

❌ NOT THIS (hardcoded):
```sql
INSERT INTO `sys_role_menu` (`id`, `tenant_id`, `role_id`, `menu_id`, `create_time`) 
VALUES (1974039004068225026, 0, 1, 1970859363574153218, '2025-10-03 17:09:09');
```

✅ BUT THIS (dynamic lookup):
```sql
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`, `create_time`)
SELECT r.id, m.id, NOW()
FROM sys_role r, sys_menu m
WHERE r.role_key = 'admin' AND m.path = '/home';
```

**Example seed data for sys_config:**

Keep meaningful configs:
- sysLogo
- sysVersion
- sysMultiModule

Remove meaningless configs like:
- '111', '111', '111111', '111' entries

- [ ] **Step 1: Define DDL with all tables**
- [ ] **Step 2: Define seed data with SELECT-based INSERTs**
- [ ] **Step 3: Remove test/bad data entries**

---

## Phase 4: Frontend Setup

### Task 12: Copy and Configure Frontend

**Files:**
- Copy from: `D:\project\sihui-village-govern-frontend`
- Create: `D:\project\baseline-front\...`

- [ ] **Step 1: Copy frontend files**

Copy entire `sihui-village-govern-frontend` to `baseline-front`

- [ ] **Step 2: Update package.json name**

```json
{
  "name": "baseline-front",
  "version": "1.0.0",
  ...
}
```

- [ ] **Step 3: Update .umirc.ts proxy target**

```typescript
proxy: {
    "/hcapi": {
        target: 'http://localhost:38080',
        pathRewrite: { "^/hcapi": "" },
    }
}
```

Change to:

```typescript
proxy: {
    "/api": {
        target: 'http://localhost:38080',
        pathRewrite: { "^/api": "" },
    }
}
```

- [ ] **Step 4: Update API base URL references**

Find and replace all `/hcapi/` references to `/api/`

- [ ] **Step 5: Clean up business-specific pages**

Remove business pages (BaseDataManage, RuralGovernance, EmergencyManagement, etc.) keeping only system management pages:
- System Management (setting)
- User Management
- Role Management
- Menu Management
- Permission Management
- Department Management
- Post Management
- Dictionary Management
- System Configuration
- Log Management
- Tenant Management
- Tenant User Management

---

## Phase 5: Configuration Files

### Task 13: Create Application Configuration Files

**Files:**
- Create: `D:\project\baseline-backend\module-service\frame-service\src\main\resources\application.yml`
- Create: `D:\project\baseline-backend\module-service\frame-service\src\main\resources\bootstrap.yml`

**application.yml structure:**

```yaml
server:
  port: 38080

spring:
  application:
    name: frame-service
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/baseline_system?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: root
          driver-class-name: com.mysql.cj.jdbc.Driver
  redis:
    host: localhost
    port: 6379
    database: 0
    password: ''
    cachePrefix: "baseline:"

sa-token:
  token-name: Authorization
  timeout: 2592000
  active-timeout: 1800
  is-concurrent: true
  is-share: false
  token-style: uuid
  token-prefix: Bearer

mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.baseline.**.domain
  configuration:
    map-underscore-to-camel-case: true

tenant:
  enable: true
  column: tenant_id
  exclusionTable:
    - sys_user
    - sys_menu
    - sys_config
    - sys_dict_data
    - sys_dict_type
    - sys_tenant
    - oauth_client_details
```

---

## Task Count Summary

| Phase | Tasks |
|-------|-------|
| Phase 1: Backend Foundation | 5 |
| Phase 2: Service Modules | 5 |
| Phase 3: SQL Generation | 1 |
| Phase 4: Frontend | 1 |
| Phase 5: Configuration | 1 |
| **Total** | **13** |

---

## Self-Review Checklist

- [ ] All `com.gzhaochuan` replaced with `com.baseline`
- [ ] All `hc-cloud-` replaced with `cloud-common-`
- [ ] All `hc_competence` replaced with `baseline_system`
- [ ] SQL has no hardcoded IDs in INSERTs
- [ ] SQL has proper AUTO_INCREMENT on all tables
- [ ] Meaningless test data removed from SQL
- [ ] Frontend proxy updated from `/hcapi` to `/api`
- [ ] Spring Boot 4.0 compatibility verified in all pom.xml files
