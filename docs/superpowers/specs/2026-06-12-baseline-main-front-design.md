# baseline-main-front 设计文档

- 日期：2026-06-12
- 状态：草案，待用户审阅
- 目标读者：实施本项目的下一位 Claude/开发者

## 1. 项目定位与架构

`baseline-main-front` 是 `baseline-backend` 配套的**多租户 SaaS 后台脚手架前端**，作为未来基于 baseline 启动新项目时的前端起点。

**只包含：登录 + 系统管理（多租户/用户/角色/菜单/部门/岗位/字典/日志/系统配置/改密/用户中心）。不包含任何业务页面。**

### 1.1 技术栈

与 `D:/project/sihui-village-main-frontend/` 保持一致：

- UMI 4（`umi ^4.3.35`）
- React 18
- Ant Design 5（`antd ^5.22.3`）
- `@ant-design/pro-components`
- Dva（`@umijs/plugins/dist/dva`）做全局状态管理
- axios 1.7.x
- `cross-env`、`qs`、`lodash`、`crypto-js`、`moment`

依赖版本号以 `D:/project/benchmark-project/baseline-front/package.json` 为基准对齐，确保与现有 baseline 系列项目的依赖矩阵一致。

### 1.2 部署形态

- 纯单体 SPA，**不引入 Qiankun**（无 master/slave 任何角色）
- `base` / `publicPath` 均为 `/`（根路径部署）
- dev server 端口 8000（UMI 默认）
- dev proxy：`/api/*` → `http://localhost:38080`，rewrite `^/api → ""`（与 baseline-front 一致；38080 是 gateway-service 或 frame-service 单体的端口）

### 1.3 与现有项目的关系

| 项目 | 形态 | 与本项目关系 |
|---|---|---|
| `baseline-backend` | 后端 | 本项目调用其 `/auth/**` 和 `/system/**` 网关路径 |
| `baseline-front` | Qiankun 子应用脚手架 | 保留不动，作为子应用形态的参考案例 |
| `baseline-main-front`（本项目） | 单体 SPA 脚手架 | 兄弟项目，独立 |
| `sihui-village-main-frontend` | 生产项目（Qiankun 主应用） | 代码复用来源，但**不依赖**它 |

## 2. 目录结构

```
baseline-main-front/
├── package.json
├── .umirc.ts                    # base=publicPath=/, proxy /api → 38080, dva 插件
├── tsconfig.json
├── typings.d.ts
├── Dockerfile, nginx/, k8s/, start.js   # 占位，照搬 sihui-main 风格
├── .prettierrc.json
├── .npmrc                       # 使用 npmmirror 源
├── src/
│   ├── app.ts                   # 全局错误监听，不含 qiankun
│   ├── assets/                  # logo、登录背景图等
│   ├── components/
│   │   ├── baseHeader/          # 顶栏：用户名、租户切换、退出、改密入口
│   │   ├── baseMenu/            # 左侧菜单（递归渲染后端返回的 menuList）
│   │   ├── baseTable/           # ProTable 通用封装
│   │   └── baseModal/           # Modal 通用封装
│   ├── hooks/
│   │   ├── useBtnPermission.ts
│   │   └── useDictData.ts
│   ├── layouts/
│   │   └── BlankLayout.tsx      # 无壳布局（login 使用）
│   ├── models/                  # Dva 全局 model
│   │   ├── common.ts            # 域名/字典/系统配置
│   │   └── user.ts              # 当前用户、菜单、按钮权限、租户列表
│   ├── pages/
│   │   ├── 404.tsx
│   │   ├── login/               # 登录页（账户密码 + 验证码）
│   │   ├── baseLayout/          # 顶栏 + 菜单 + Outlet
│   │   ├── home/                # 简单欢迎页
│   │   ├── changePassword/
│   │   ├── userCenter/
│   │   └── setting/
│   │       ├── system/          # 系统配置 sys_config
│   │       ├── tenant/          # 租户管理 sys_tenant + tenantUser 子页
│   │       ├── user/            # 用户 sys_user
│   │       ├── role/            # 角色 sys_role + 角色菜单/数据权限
│   │       ├── menu/            # 菜单 sys_menu
│   │       ├── dept/            # 部门 sys_dept
│   │       ├── post/            # 岗位 sys_post
│   │       ├── dic/             # 字典 sys_dict_type + sys_dict_data
│   │       └── log/             # 操作日志 sys_log
│   ├── routers/
│   │   └── routers.ts
│   └── utils/
│       ├── Servpost.ts          # axios 封装，401 → /login
│       ├── ServpostInterface.ts
│       ├── apiUtils.ts
│       ├── cookieUtils.ts
│       ├── authGuard.ts         # 路由前置守卫（无 token 跳 login）
│       └── utils.ts
└── docs/
    └── 技术文档.md（可选）
```

### 2.1 路由表

```ts
// src/routers/routers.ts
const Routers = [
  { path: "/login", component: "login/login" },
  {
    path: "/",
    component: "baseLayout/baseLayout",
    routes: [
      { path: "/", redirect: "/home" },
      { path: "/home", component: "home/home" },
      { path: "/changePassword", component: "changePassword/changePassword" },
      { path: "/userCenter", component: "userCenter/userCenter" },
      { path: "/setting/system",        component: "setting/system/system" },
      { path: "/setting/tenant",        component: "setting/tenant/tenant" },
      { path: "/setting/tenant/users",  component: "setting/tenant/tenantUser" },
      { path: "/setting/user",          component: "setting/user/user" },
      { path: "/setting/role",          component: "setting/role/role" },
      { path: "/setting/menu",          component: "setting/menu/menu" },
      { path: "/setting/dept",          component: "setting/dept/dept" },
      { path: "/setting/post",          component: "setting/post/post" },
      { path: "/setting/dic",           component: "setting/dic/dic" },
      { path: "/setting/log",           component: "setting/log/log" },
    ],
  },
  { path: "/*", component: "404" },
];
```

**约定**：所有系统管理路由统一放在 `/setting/*` 前缀下，对应 `pages/setting/*` 包结构。这样未来在脚手架上加业务页面（如 `/order`、`/customer`）时不会与系统管理路由冲突。

## 3. 鉴权与数据流

### 3.1 登录到首页的链路

```
1. 用户访问 / (或任何受保护路由)
   ↓
2. authGuard 检查 localStorage.token
   ├─ 无 → history.replace('/login')
   └─ 有 → 放行
   ↓
3. baseLayout 挂载时按需派发：
   - user/getUserInfo  →  GET  /api/system/adminApi/user/info
   - user/getMenuList  →  GET  /api/system/adminApi/menu/userMenus
   - user/getBtnPermission → GET /api/system/adminApi/menu/btn/permission
   - common/getSystemConfig → GET /api/system/adminApi/config/system
   - 若 tenantEnable=true:
     login/getUserTenantList → POST /api/system/adminApi/user/myTenantList
   ↓
4. 数据存 Dva model + localStorage 二级缓存（与 sihui 一致）
   下次刷新不重复拉
   ↓
5. 渲染左侧菜单 + 顶栏（含租户切换下拉）
```

### 3.2 Login 页流程

```
进入 /login
  ↓
GET  /api/auth/captcha/isEnabled      → 决定是否显示验证码框
  ↓ (若 enabled)
GET  /api/captcha/image               → 拿验证码图 (base64) + uuid
  ↓
用户提交 →
POST /api/auth/user/login
  body: {
    loginType: "admin",
    authType: "accountPassword",
    credentials: { account, password, code, uuid }
  }
  ↓ 200
存 token = `${tokenPrefix} ${token}` 到 localStorage.token
跳 /home
```

### 3.3 401 拦截

发生在 `utils/Servpost.ts`：

```
任何接口返回 status === 401
  ↓
- antd Message.error('身份信息过期，请重新登陆')（去抖：flag 标志只弹一次）
- POST /api/auth/user/logout（清后端 session）
- 清 localStorage 全部 key（token / userInfos / menuList / btnPermissionList / dictGroups / tenantList）
- window.location.href = '/login'
```

**与 sihui 的关键差异**：sihui 是子应用，靠主应用注入的 `props.clearAccessToken()` 和 `props.login()` 处理 401；本项目是单体，自己跳 `window.location.href = '/login'`。

### 3.4 路由前置守卫

`baseLayout` 顶部判断：

```ts
useEffect(() => {
  if (!localStorage.getItem('token')) {
    history.replace('/login');
  }
}, []);
```

兜底：在 `app.ts` 暴露 UMI 的 `onRouteChange` 钩子做全局拦截。

### 3.5 Token 格式

遵循 baseline-backend Sa-Token 约定：`Bearer <uuid>`，整串放 `Authorization` 头。

### 3.6 多租户切换

- 顶栏下拉显示 `tenantList`（来自 `/user/myTenantList`），当前选中由 localStorage 缓存
- 切换调 `POST /api/system/adminApi/user/switchTenant { tenantId }`
- 成功后**清掉** localStorage 的 `userInfos / menuList / btnPermissionList / dictGroups`
- 然后 `window.location.reload()`，让所有数据按新租户重新拉一遍（sihui 已验证）

## 4. 复用策略

### 4.1 从 sihui-village-main-frontend 复用

| 文件 / 目录 | 改动 |
|---|---|
| `src/utils/Servpost.ts` | `/hcapi/` → `/api/`；401 跳 `/login` 替换 `parent.login()` |
| `src/utils/ServpostInterface.ts` | 无 |
| `src/utils/apiUtils.ts` | API 路径前缀替换 |
| `src/utils/cookieUtils.ts` | 无 |
| `src/utils/utils.ts` | 仅保留加密等通用函数 |
| `src/pages/login/login.tsx` + `LoginForm` | 删粤政易 Tab，只留账户密码 |
| `src/pages/login/model.ts` | API 前缀；去掉项目选择跳转，登录成功直接跳 `/home` |
| `src/pages/login/login.less` | 标题文案改"基线后台管理平台" |
| `src/pages/changePassword/*` | API 前缀 |
| `src/pages/userCenter/*` | API 前缀 |
| `src/pages/setting/{tenant,user,role,menu,dept,post,dic,log,system}/*` | API 前缀；保留多租户/数据权限相关逻辑 |
| `src/components/baseHeader/*` | 删项目切换；保留租户切换、改密、退出 |
| `src/components/baseMenu/*` | 无 |
| `src/components/baseTable/*`、`baseModal/*` | 无 |
| `src/models/common.ts` | 删项目相关 state |
| `src/hooks/*` | 仅保留通用 hook（如 `useBtnPermission`、`useDictData`） |

### 4.2 从 baseline-front 复用

- `package.json` 依赖版本号矩阵
- `.prettierrc.json`
- `.npmrc`（npmmirror 源）

### 4.3 明确剔除（不从 sihui 复制）

- `pages/projectSelection/*`、`projectSelectionLayout/*`：不做多项目
- `pages/messageCenter/*`、`pages/officialWritingCopilot/*`：业务模块
- `pages/*SubApp/*`：Qiankun 子应用挂载组件
- `app.ts` 的 `qiankun.master` 配置
- `utils/environment.ts`、`mergeSignals`、`createTimedFetch`：Qiankun 错误广播

## 5. 后端依赖核对

### 5.1 必需接口（已验证存在于 baseline-backend）

| 接口 | 用途 | 后端位置 |
|---|---|---|
| `POST /auth/user/login` | 登录 | `auth-service/UserAuthController.java:24` ✓ |
| `POST /auth/user/logout` | 退出 | `UserAuthController.java:32` ✓ |
| `GET  /auth/captcha/isEnabled` | 验证码开关 | `CaptchaController.java:31` ✓ |
| `GET  /captcha/image` | 验证码图（不带 `/auth` 前缀，与 sihui 一致） | `CaptchaController.java:24` ✓ |

网关路由（`gateway-service-micro.yml`）：
- `/auth/**` → auth-service（StripPrefix=1）
- `/system/**` → system-service（StripPrefix=1）

### 5.2 必需接口（实施时需在 system-service `controller/admin/` 下逐一搜索确认）

- `/system/adminApi/user/info`
- `/system/adminApi/menu/userMenus`
- `/system/adminApi/menu/btn/permission`
- `/system/adminApi/config/system`
- `/system/adminApi/user/myTenantList`
- `/system/adminApi/user/switchTenant`
- `/system/adminApi/{tenant,user,role,menu,dept,post,dict/type,dict/data,config,log}/*` CRUD

### 5.3 风险点

1. **接口齐备性**：sihui 后端接口可能比 baseline-backend 多。第一阶段开工前要在 `baseline-backend/module-service/system-service/.../controller/admin/` 下扫一遍类名，对不上的接口需要：(a) 后端补，或 (b) 前端先不做、放 TODO。
2. **字段命名差异**：sihui 后端 DTO 字段名未必与 baseline 一致，调用时按 baseline 实际返回结构调整。
3. **`tokenPrefix`**：sihui 是用 `${tokenPrefix} ${token}` 拼接 Authorization。如果 baseline Sa-Token 已经返回带前缀的整串，就不要再拼第二次 —— 第一阶段联调时实际试一下。

## 6. 实施阶段划分

每阶段独立可交付，便于联调。

- **阶段 1：骨架可登录**
  脚手架初始化、`Servpost`、登录页、`baseLayout`、`home` 空页。验收：能从 `/login` 登录进入 `/home`，401 能跳回 `/login`。

- **阶段 2：核心 CRUD（4 个表）**
  用户、角色、菜单、部门。验收：4 个页面 CRUD 跑通，菜单可挂在左侧渲染。

- **阶段 3：多租户**
  租户管理页 + 顶栏租户切换控件 + 字典管理。验收：能在两个租户间切换并看到数据隔离。

- **阶段 4：辅助**
  岗位、操作日志、系统配置、改密、用户中心。验收：每个页面能打开、CRUD 通顺。

## 7. 验收口径

完整脚手架交付时，以下流程必须通过浏览器手动验证：

1. `yarn dev` 启动，浏览器访问 `http://localhost:8000/` → 自动跳 `/login`
2. 输入正确账号密码 → 跳 `/home`，顶栏显示用户名
3. 顶栏切租户 → 页面刷新，菜单与数据切换为新租户
4. 左侧菜单点击进入 `/setting/user`、`/setting/role` 等页面，CRUD 操作通顺
5. 手动删除 localStorage.token 后任意调接口 → 弹"身份信息过期"并跳 `/login`
6. F5 刷新已登录页面 → 不重新拉用户/菜单（依赖 localStorage 缓存）
