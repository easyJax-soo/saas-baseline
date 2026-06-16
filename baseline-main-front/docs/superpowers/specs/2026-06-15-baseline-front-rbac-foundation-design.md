# P1 — RBAC 基建层设计

## Goal

为 baseline-main-front 搭起后续所有管理页面共用的 RBAC 基建：用户上下文、菜单、按钮权限、动态侧栏、路由守卫、退出登录、Placeholder 页占位。完成本阶段后，登录 → 首页 → 看见左侧菜单 → 点菜单进 placeholder 页 → 退出 这条链路在浏览器里全程可用。

P2-P5（用户/角色/菜单/部门/字典/租户/数据权限/租户切换）**不在本阶段范围内**，会在 P1 通过后单独立项。

## 非目标 (Out of Scope)

- 不实现任何具体管理页面（user/role/menu/dept/dict/tenant 全部用 Placeholder 占位）
- 不实现租户切换 UI（顶栏租户下拉留到 P5）
- 不实现路由级权限拦截（按用户决策：路由全部静态预注册，能进；侧栏由后端菜单驱动）
- 不实现按钮权限指令（P1 只把权限码列表存到 store 给后续 P2-P5 用）

实际上后端 `@SaAdminCheckPermission` 才是真正的安全门禁，前端不假装做安全。

## Architecture

```
应用启动 (app.ts render hook)
    └─ React 渲染第一帧之前
         └─ 从 localStorage 读 token/user/menu/permissions
              └─ 灌入 Dva store（namespace=app）
                   └─ oldRender() 开始 React 渲染
    Why: F5 后 Dva store 重置但 localStorage 还在，必须在第一帧之前同步恢复，
         否则会闪一下"无菜单"状态。

登录页 LoginForm
    └─ dispatch login/handleLogin
         ├─ POST /auth/user/login  → 拿 token，先 localStorage.setItem('token')
         ├─ Promise.all([
         │     GET  /system/adminApi/user/info          → 用户信息（含 accessibleTenants/currentTenantId/roles）
         │     POST /system/adminApi/menu/list          → 菜单树（type=M/C/F）
         │     POST /system/adminApi/menu/btn/permission → 按钮权限码列表
         │   ])
       全部成功后一次性 dispatch app/saveData + 一次性写 localStorage，callback → navigate('/home', {replace:true})
       user/info 或 menu/list 失败：清 token+user+menu+permissions、回登录页、Message.error
       btn/permission 失败：不阻塞，permissions=[]，照常进 /home，Message.warning

baseLayout 挂载
    └─ 仅做 token 守卫：localStorage.token 不存在 → navigate('/login') 返回 null
         └─ 渲染 Header + Sider + Content（数据已被 app.ts 灌好，直接 useSelector 取）
              ├─ Header  → 当前用户名 + 退出登录
              ├─ Sider   → 由 state.app.menu 递归渲染（type=M 折叠 / type=C 跳路由 / type=F 不渲染）
              └─ Content → <Outlet/>

routers.ts 静态预注册全部 P1-P5 路径
    /login                 → login/login
    /                      → baseLayout/baseLayout（带 Outlet）
        /home              → home/home
        /403               → common/forbidden
        /system/user       → common/placeholder
        /system/role       → common/placeholder
        /system/menu       → common/placeholder
        /system/dept       → common/placeholder
        /system/dict       → common/placeholder
        /system/tenant     → common/placeholder
        /profile           → common/placeholder
    /*                     → 404
```

**Why 静态预注册全部路径：** 用户决策，避免 P2-P5 反复改 routers.ts；P2 上线某页面只需把 `common/placeholder` 替换成实际组件，URL 不变。

**Why 登录时同步拉：** 用户决策。换来首屏即有菜单，代价是登录按钮 loading 多 2-3 个并发请求的时间（可接受）。三接口用 `Promise.all` 并发，不串行。

**Why 不写路由级守卫：** 用户决策。前端只做 token 守卫（没 token 弹回登录页）；菜单的可见性靠 Sider 数据驱动，URL 直输的拦截交给后端 `@SaCheckPermission`。

**Why 单 Dva model（namespace=app）：** user/menu/permissions 三块数据每次同进同出（登录一起拉、退出一起清、F5 一起恢复）。拆三个 model 等于把同一个事务切成三处 dispatch + 三处 localStorage 同步，没收益还多一处忘改的风险。

**Why F5 恢复放在 app.ts 而不是 baseLayout：** baseLayout 的 useEffect 在 React 第一帧渲染**之后**才执行，会闪一下空数据；且 baseLayout 卸载/重挂会重复跑。app.ts 的 `render` hook 在 React 渲染**之前**同步执行一次，更干净。

## Tech Stack

- UMI 4 + React 18 + TypeScript（已就绪）
- Dva（已就绪，用于 user/menu/permission 三个 model）
- Ant Design 5 + `@ant-design/icons`（已就绪）
- 状态持久化：`localStorage` + `dva` 内存 store 双写。app.ts 启动钩子在 React 首帧前把 localStorage 灌回 store。

## 数据模型

### `LocalStorage` 键

| key | 值 | 写入时机 | 清除时机 |
|---|---|---|---|
| `token` | `Bearer xxx` | 登录成功 | 退出 / 401 拦截 |
| `userInfo` | `JSON.stringify(SessionUser)` | 登录成功 | 退出 / 401 |
| `menu` | `JSON.stringify(SysMenuNode[])` | 登录成功 | 退出 / 401 |
| `permissions` | `JSON.stringify(string[])` | 登录成功 | 退出 / 401 |
| `rememberAccountInfo` | 已有，不动 | — | — |

### `SessionUser`（前端规范化后的用户对象）

```ts
interface SessionUser {
  id: number | null
  account: string
  name: string
  avatar: string
  roles: { id: number; key: string; name: string }[]    // 后端原始角色列表
  roleKeys: string[]                                     // 提取的 key 数组，便于权限判断
  currentTenantId: number | null
  currentTenantName: string
  accessibleTenants: { tenantId: number; tenantName: string; tenantCode: string; isCurrent: boolean }[]
}
```

来源：`GET /system/adminApi/user/info`。映射规则参考 `ai-frontier-front/src/views/login.vue:232-287`，但**不要做"无角色 → 默认 SA"**的兜底（参考自其代码注释 244-248 行的安全约定）；roleKeys 取不到就是空数组。

### `SysMenuNode`（直接透传后端字段）

```ts
interface SysMenuNode {
  id: number
  parentId: number
  name: string         // 显示文案
  path: string         // 前端路由 path（M 类型可空）
  component: string    // 组件路径（C 类型必填，M/F 忽略）
  type: 'M' | 'C' | 'F'
  visible: number      // 0隐藏 1显示
  status: number       // 0停用 1启用
  icon: string
  key: string          // 权限标识（F 类型按钮码）
  sortNo: number
  children?: SysMenuNode[]
}
```

后端返回的就是树结构，前端不再扁平化。

## 文件清单（新增 / 修改）

### 新增

```
src/
  models/
    app.ts                        # 单一 namespace=app 状态：{user, menu, permissions}
                                  #   reducer: saveData（合并 payload）/ reset（清空）
  utils/
    sessionStore.ts               # localStorage 读写封装（一处定义 key，避免到处魔法字符串）
                                  #   readAll() / writeAll(payload) / clearAll() / getToken() / setToken() / clearToken()
    sessionUserMapper.ts          # 后端 user/info 响应 → SessionUser 规范化
  pages/
    common/
      Placeholder.tsx             # P2-P5 占位页（"该页面建设中"，无返回按钮，反正后续会删）
      Forbidden.tsx               # /403 页
    baseLayout/
      Sidebar.tsx                 # 由 state.app.menu 递归渲染的 antd Menu
      UserMenu.tsx                # 顶栏右上角 dropdown（账号 + 退出）
```

### 修改

```
src/app.ts                        # 新增 render hook：从 localStorage 灌 user/menu/permissions 进 dva store
src/pages/login/model.ts          # handleLogin 加并发拉 user/info + menu/list + menu/btn/permission，统一 dispatch app/saveData
src/pages/baseLayout/baseLayout.tsx # 接 Sidebar + UserMenu，只做 token 守卫不再做 F5 恢复
src/routers/routers.ts            # 增加全部 P1-P5 静态路径，未实现的指 common/Placeholder
src/utils/Servpost.ts             # 401 拦截要清掉 user/menu/permission 三份 localStorage（目前只清 token）
src/utils/authGuard.ts            # clearAuthOnLogout 同上同步清三份
```

## 关键流程细节

### 1. 应用启动时恢复 store（F5 / 重新打开标签页）

`src/app.ts` 新增 `render` hook（UMI runtime 暴露的钩子，会在第一次渲染**之前**同步执行）：

```ts
import { getDvaApp } from 'umi'
import { sessionStore } from '@/utils/sessionStore'

export function render(oldRender: () => void) {
  const app = getDvaApp()
  const cached = sessionStore.readAll()  // {user, menu, permissions}，缺失字段给安全默认值
  if (cached.user) {
    app._store.dispatch({ type: 'app/saveData', payload: cached })
  }
  oldRender()
}
```

**Why 在 render 而不在 baseLayout：** baseLayout 的 useEffect 在第一帧渲染**后**才跑，会闪空数据；且 baseLayout 卸载/重挂会重复跑。

### 2. 登录后并发拉资源

`pages/login/model.ts` `handleLogin` effect 改造后：

1. `POST /api/auth/user/login` 成功，`localStorage.setItem('token', fullToken)`（**必须先存**，因为后续三请求要带 Authorization header）
2. `Promise.all([userInfoCall, menuCall, btnPermCall])` 并发
3. user/info 和 menu/list 都成功：
   - `sessionUserMapper(userInfoRes)` → SessionUser
   - `dispatch({ type: 'app/saveData', payload: { user, menu, permissions } })`（一次入 store）
   - `sessionStore.writeAll({ user, menu, permissions })`（一次写 localStorage）
   - `callback()` 跳 `/home`
4. user/info 或 menu/list 失败：`sessionStore.clearAll()` + `dispatch app/reset` + Message.error，留登录页
5. btn/permission 单独失败：permissions 取 `[]`，照常完成第 3 步，加一条 Message.warning

### 3. baseLayout 挂载时只做守卫

```ts
useEffect(() => {
  if (!sessionStore.getToken()) {
    navigate('/login', { replace: true })
  }
}, [])
```

不再读 localStorage 灌 store——那是 app.ts 的事。

### 4. Sidebar 递归渲染

转换约定（与 `ai-frontier-front/src/utils/menuConverter.js` 语义一致）：

- `type === 'F'` → 不渲染菜单项（按钮权限码已经在 state.app.permissions 里）
- `type === 'M'` → 渲染 `<SubMenu>`，`children` 递归
- `type === 'C'` → 渲染 `<Menu.Item>`，点击 `navigate(node.path)`
- `visible !== 1` → 跳过（隐藏菜单）
- `status !== 1` → 跳过（停用菜单）

侧栏 `selectedKeys` 由 `useLocation().pathname` 推导，路由变化自动高亮。

### 5. 退出登录

已有逻辑保留，**追加**：

- `dispatch({ type: 'app/reset' })` 清 store
- `sessionStore.clearAll()` 清四份 localStorage（token + user + menu + permissions）

## Error Handling

| 场景 | 处理 |
|---|---|
| 登录接口本身失败 | Message.error，停留登录页（已有） |
| 登录成功但 user/info 失败 | clearAuthOnLogout，Message.error("获取用户信息失败")，停留登录页 |
| 登录成功但 menu/list 失败 | 同上 |
| 登录成功但 menu/btn/permission 失败 | 不阻塞登录，按钮权限码视为空数组，Message.warning("权限码加载失败，部分操作可能受限")，照常跳 /home |
| 任何后续请求 401 | Servpost 已有逻辑（弹一次提示 + forceRedirectToLogin），追加清 user/menu/permission |
| F5 刷新但 token 已过期 | 第一个业务请求触发 401，由上面那条流程兜住 |

**Why 按钮权限码失败不阻塞：** 它只影响 P2-P5 的按钮显隐，不影响进系统看页面。优先保留可用性。

## 测试计划

P1 没有自动化测试（项目本来就没配测试，参见 CLAUDE.md），用浏览器人工验：

1. 用 admin/admin123 登录 → 看到 /home，左侧出菜单
2. 点菜单第一个有 path 的项 → 跳到 /system/xxx，看到 Placeholder 页
3. 直接访问 /system/role → 进 placeholder（**不应该 404**，因为静态预注册了）
4. 直接访问 /not-exist → 进 404
5. 顶栏点退出 → 跳回 /login，再点浏览器后退按钮 → 仍在 /login（因为 baseLayout 守卫弹回去）
6. 登录后 F5 → 仍在 /home，菜单仍在
7. 登录后手动改 localStorage 删掉 token + F5 → 弹回 /login

## 与 P2 的衔接点

- `pages/system/user/list.tsx` 等具体页面在 P2 实现时，只需把 `routers.ts` 里那条 `component: 'common/placeholder'` 改成 `component: 'system/user/list'`，URL 不动
- 按钮权限指令（如 `<Auth code="system:user:save">`）在 P2 设计时基于 `permission` store 里已有的码列表实现
- 顶栏租户下拉（P5）基于 `user.accessibleTenants` 实现，本阶段已经把数据存好了
