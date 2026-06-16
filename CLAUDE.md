# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository layout

This is a SaaS multi-tenant baseline / starter template, made up of three sibling projects:

- `baseline-backend/` — current, maintained Java backend (Spring Boot 3.5.14, Spring Cloud 2025.0.2, Spring Cloud Alibaba 2025.0.0.0, Java 17). Root Java package `com.baseline.*`.
- `previous-baseline-backend/` — older snapshot of the backend under package `com.benchmark.*`, pinned to Spring Boot 4.0.0 / Cloud 2024.0.0. **Not the deployed code**; kept for reference. Do not change it when working on the active backend unless explicitly asked.
- `baseline-front/` — UMI 4 + React + Ant Design 5 frontend, packaged as a **Qiankun micro-frontend slave** (not a standalone SPA — the parent shell owns auth/login).
- `docs/superpowers/plans/` — implementation plans (`2026-06-11-saas-multi-tenant-baseline.md`, `2026-06-11-dependency-fix.md`) that describe how this baseline was constructed and known fix-ups.

There is no top-level git repo and no top-level build — work inside one of the project directories.

## Backend (`baseline-backend/`)

### Two deployment modes via Maven profiles

The same codebase ships either as four microservices or as one monolith — this is the central architectural decision:

- **`-Pmicro` (default, `SPRING_ACTIVE=micro`)** — each service registers in Nacos; inter-service calls go through `cloud-common-feign-api` Feign clients.
- **`-Psingle`** — each service produces a `classes`-classifier jar; `frame-service` pulls them all in plus `cloud-common-local-api` (direct in-JVM impls of the same API interfaces) and runs as one Spring Boot app.

The `cloud-common-api` module split — `cloud-common-feign-api` vs `cloud-common-local-api` — is what makes this work. When adding a cross-service call, add the interface in `cloud-common-api`, a Feign impl in `cloud-common-feign-api`, and a direct impl in `cloud-common-local-api`. Activating exactly one is the profile's job.

### Module hierarchy

```
baseline-backend/ (parent pom, uses flatten-maven-plugin + ${revision})
├── common-library/
│   ├── cloud-common-core, -biz, -mybatis, -security, -redis, -web,
│   │   -log, -swagger, -oss, -excel, -utils, -jackson
│   └── cloud-common-api/{cloud-common-feign-api, cloud-common-local-api}
└── module-service/
    ├── auth-service      (port 38081) login, OAuth2, SSO, captcha
    ├── gateway-service   (port 38080) Spring Cloud Gateway (WebFlux), Sentinel, reactive Sa-Token
    ├── system-service    (port 38082) users/roles/menus/depts/dicts/tenants/files/members/projects
    └── frame-service     (port 38080, `-Psingle` host)
```

### Controller package → URL prefix convention

`system-service` (and analogous configs) auto-prefix controller routes by package. **Place new controllers in the right package** — there is no annotation; the prefix comes from the package name:

- `controller/admin/*` → `/adminApi/**` (back-office UI)
- `controller/api/*` → `/webApi/**` (public/web-facing)
- `controller/feign/*` → `/feignApi/**` (internal cross-service)

See `SystemApiPrefixConfig`.

### Auth handler strategy pattern

New login methods (account/password, WeChat MiniApp, DingTalk, …) are added by implementing `AuthenticationHandler` and registering it as a Spring bean. `AuthHandlerRegistry` auto-collects all such beans via `List<>` injection and indexes them by `getAuthType()`. Don't hand-wire a switch.

### Multi-tenancy and data permissions

- Tenant column is `tenant_id`; MyBatis-Plus interceptor injects it automatically. Tables in `tenant.exclusionTable` (in shared Nacos config / `frame-service` `application.yml`) are global — currently `sys_user, sys_menu, sys_config, sys_dict_data, sys_dict_type, sys_tenant, oauth_client_details`. Update this list when adding global tables.
- `tenant.hierarchy.enable=true` adds layered/parent-child tenant semantics.
- Row-level data permissions use `@DataPermission` + `@DataColumn` annotations — see `common-library/cloud-common-mybatis/README.md` for the five permission modes (全部 / 自定义 / 本部门 / 本部门及以下 / 仅本人) and OR-across-roles semantics.

### Dict serialization convention

Fields annotated `@Dict` are **always serialized as strings** in JSON regardless of underlying type, and a sibling `*_Text` field is emitted with the human label. See `common-library/cloud-common-biz/README_DICT_SERIALIZATION.md`. Don't be surprised that an integer comes out as a string on the wire.

### Auth

- Sa-Token everywhere — `sa-token-spring-boot3-starter` in services, `sa-token-reactor-spring-boot3-starter` in the gateway, plus `sa-token-oauth2` and `sa-token-sso` in `auth-service`.
- Tokens are UUIDs with `Bearer` prefix in the `Authorization` header; 30-day timeout; concurrent logins enabled.
- Public/no-auth endpoints are listed in shared Nacos config under `security.ignore-whites` (e.g., `/auth/captcha/**`, `/auth/user/login`, `/system/adminApi/file/domain`, …). Add new public endpoints there, not via annotations.

### Configuration sources

- Bootstrap config in each service's `bootstrap.yml` points at Nacos (default namespace `a5318def-6881-4de0-991d-d8ab55731f30`). Overridable env vars: `NACOS_HOST`, `NACOS_PORT`, `NACOS_NAMESPACE`, `NACOS_GROUP`, `NACOS_USERNAME`, `NACOS_PASSWORD`, `SPRING_ACTIVE`.
- Shared Nacos configs are committed under `baseline-backend/DEFAULT_GROUP/` (`application-micro.yml`, `gateway-service-micro.yml`, `auth-service-micro.yml`, `system-service-micro.yml`) — these are the source of truth for runtime config in `-Pmicro` mode; edit them here and upload to Nacos.
- `frame-service/.../application.yml` ships a self-contained `-Psingle` config with `localhost` MySQL/Redis defaults — use this for local monolith runs.
- DB connection env vars: `DB_HOST`, `DB_NAME` (default `easyjax_baseline` in shared config), `DB_USERNAME`, `DB_PASSWORD`.
- Redis key prefix is `BASELINE:`.

### Common commands

```bash
# from baseline-backend/
./mvnw clean package -Pmicro         # build microservices (assembly zips under target/service-package/)
./mvnw clean package -Psingle        # build monolith (frame-service/target/frame-service.jar)
./mvnw -pl module-service/auth-service -am clean package -Pmicro   # build one service + its deps

java -jar frame-service.jar          # run monolith on 38080
java -jar gateway-service.jar        # 38080
java -jar auth-service.jar           # 38081
java -jar system-service.jar         # 38082
```

DB init: `baseline-backend/sql/baseline_system.sql` is the unified DDL+seed. Per-service `sql/*_mysql.sql` and `*_dm8.sql` exist (DM8 = 达梦, a Chinese DB).

**No tests are configured anywhere** in the backend — no `src/test/`, no JUnit deps. Don't run `mvn test` expecting results.

## Frontend (`baseline-front/`)

### This is a Qiankun slave, not a standalone app

`src/app.ts` wires Qiankun `bootstrap`/`mount`/`unmount` lifecycles. On mount, the master app's props (`token`, `userInfo`, `clearAccessToken`, `login`) are stored in the `Actions` singleton (`src/utils/actions.ts`).

`src/utils/Servpost.ts` (axios wrapper) attaches `Authorization` on every request and, on 401, calls `/api/auth/user/logout` and invokes the parent's `login()` — there is no in-app login screen. Don't try to add one; route auth through the parent shell.

The only real page shipped is `src/pages/setting/permission/`; `src/pages/baseLayout/` is the outer shell that reads parent token / fetches button permissions. New pages are meant to be added under `src/pages/<module>/routers/routers.ts` and wired via the `MODULES` env var consumed in `.umirc.ts`.

### Dev proxy → backend

`.umirc.ts` proxies `/api` → `http://localhost:38080` (gateway-service or monolith) with `^/api` rewritten to empty. Run a backend on 38080 before `yarn dev`.

### Stack notes

- UMI v4 with `@umijs/plugins/dist/dva` (Dva state) and `@umijs/plugins/dist/qiankun` (slave).
- Ant Design 5 + `@ant-design/pro-components`; ECharts; `@wangeditor/editor`, `react-quill`, `react-ace`; EasyPlayer/HLSPlayer for video (ships `.wasm` decoders under `src/components/EasyPlayer/`).
- Path aliases: `@ → src/`, `& → src/pages/`.
- `npmClient: "yarn"` per `.umirc.ts`; `.npmrc` uses `https://registry.npmmirror.com/`. Both `yarn.lock` and `package-lock.json` exist — prefer yarn to match config.
- Prettier ships (`.prettierrc.json`: no semi, 4-space tabs, double quotes, printWidth 180, `trailingComma: es5`). No eslint, no tests configured.

### Common commands

```bash
# from baseline-front/
yarn install                 # postinstall auto-runs `umi setup`
yarn dev                     # = cross-env MODULES='' BASE_PATH='/govern/' umi dev
yarn build                   # builds dist/ with CAPTCH=true, BASE_PATH=/govern/
npx prettier --write .       # no script for it; run directly

docker build -t baseline-front .             # nginx:1.18.0-alpine, serves /govern/ on port 8888
node start.js --imgname baseline-front \
  --tag <tag> --regurl harbor.example.com:443 --regroute /subapps/   # build+push helper
```

## When desensitizing / renaming

The plans under `docs/superpowers/plans/` document the desensitization rules used to derive this baseline (e.g., `com.gzhaochuan → com.baseline`, `hc-cloud- → cloud-common-`, `hc_competence → baseline_system`, Redis prefix `HC_DEV_V3: → BASELINE:`). If asked to rebrand the baseline for a new project, follow the same kind of consistent global replacement across Java packages, Maven groupIds/artifactIds, SQL schema/table prefixes, and Redis key prefixes.
