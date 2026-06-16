# P1 — RBAC 基建层 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development to implement this plan task-by-task.

**Goal:** 在 baseline-main-front 搭起后续 P2-P5 共用的 RBAC 基建：单一 Dva model、登录后并发拉取 user/menu/permission、F5 自动恢复、Sidebar 由后端菜单驱动、占位页支撑全部 P1-P5 路径。

**Architecture:** 单一 namespace=app 的 Dva model 持有 `{user, menu, permissions}`；UMI runtime `app.ts` 的 `render` hook 在 React 首帧前从 localStorage 灌回 store；登录 effect 用 Promise.all 并发拉三接口；Sidebar 根据 `state.app.menu` 递归渲染；路由表预注册全部 P1-P5 路径，未实现页面统一指向 `common/Placeholder`。

**Tech Stack:** UMI 4 + React 18 + TypeScript + Dva + Ant Design 5。已就位，无新依赖。

**前置阅读：**
- spec：`docs/superpowers/specs/2026-06-15-baseline-front-rbac-foundation-design.md`
- 后端接口（已存在，无需改动）：
  - `POST /api/auth/user/login` — 已对接，返回 `{token, tokenPrefix, expired, loginType}`（扁平结构，不是 R 包装）
  - `GET  /api/system/adminApi/user/info` — 当前用户信息（含 accessibleTenants/currentTenantId/roles）
  - `POST /api/system/adminApi/menu/list` — 我的菜单树（`SysMenuNodeVO`）
  - `POST /api/system/adminApi/menu/btn/permission` — 我的按钮权限码（`List<String>`）
- 后端 `SysMenu.type` 三类：`M` 目录 / `C` 菜单 / `F` 按钮，`visible=1` 显示，`status=1` 启用
- 参考实现：`D:/project/Zhi_Guang/ai-frontier-front/src/utils/menuConverter.js`（语义对齐，不要照搬代码）

---

## Task 1：sessionStore 工具

**Files:**
- Create: `src/utils/sessionStore.ts`

- [ ] **Step 1：创建 sessionStore.ts**

```ts
// src/utils/sessionStore.ts

const KEYS = {
    TOKEN: "token",
    USER: "userInfo",
    MENU: "menu",
    PERMISSIONS: "permissions",
} as const

export interface SessionUser {
    id: number | null
    account: string
    name: string
    avatar: string
    roles: { id: number; key: string; name: string }[]
    roleKeys: string[]
    currentTenantId: number | null
    currentTenantName: string
    accessibleTenants: { tenantId: number; tenantName: string; tenantCode: string; isCurrent: boolean }[]
}

export interface SysMenuNode {
    id: number
    parentId: number
    name: string
    path: string
    component: string
    type: "M" | "C" | "F"
    visible: number
    status: number
    icon: string
    key: string
    sortNo: number
    children?: SysMenuNode[]
}

export interface CachedSession {
    user: SessionUser | null
    menu: SysMenuNode[]
    permissions: string[]
}

const safeParse = <T>(raw: string | null, fallback: T): T => {
    if (!raw) return fallback
    try {
        return JSON.parse(raw) as T
    } catch {
        return fallback
    }
}

export const sessionStore = {
    getToken(): string {
        return window.localStorage.getItem(KEYS.TOKEN) || ""
    },
    setToken(token: string): void {
        window.localStorage.setItem(KEYS.TOKEN, token)
    },
    clearToken(): void {
        window.localStorage.removeItem(KEYS.TOKEN)
    },
    readAll(): CachedSession {
        return {
            user: safeParse<SessionUser | null>(window.localStorage.getItem(KEYS.USER), null),
            menu: safeParse<SysMenuNode[]>(window.localStorage.getItem(KEYS.MENU), []),
            permissions: safeParse<string[]>(window.localStorage.getItem(KEYS.PERMISSIONS), []),
        }
    },
    writeAll(payload: CachedSession): void {
        window.localStorage.setItem(KEYS.USER, JSON.stringify(payload.user))
        window.localStorage.setItem(KEYS.MENU, JSON.stringify(payload.menu))
        window.localStorage.setItem(KEYS.PERMISSIONS, JSON.stringify(payload.permissions))
    },
    clearAll(): void {
        window.localStorage.removeItem(KEYS.TOKEN)
        window.localStorage.removeItem(KEYS.USER)
        window.localStorage.removeItem(KEYS.MENU)
        window.localStorage.removeItem(KEYS.PERMISSIONS)
    },
}
```

- [ ] **Step 2：commit**

```bash
git add src/utils/sessionStore.ts
git commit -m "feat(p1): add sessionStore helper for token/user/menu/permissions"
```

---

## Task 2：sessionUserMapper 规范化用户信息

**Files:**
- Create: `src/utils/sessionUserMapper.ts`

后端 `GET /system/adminApi/user/info` 返回的字段与前端 `SessionUser` 不完全对齐，需要做映射。规则参考 ai-frontier-front 的 `buildSessionUserFromInfo`，但**不做"无角色 → 默认 SA"**的兜底：取不到角色就给空数组，避免给任何用户隐式授超级管理员权限。

- [ ] **Step 1：创建 mapper**

```ts
// src/utils/sessionUserMapper.ts
import type { SessionUser } from "./sessionStore"

interface RawRole {
    id?: number
    key?: string
    name?: string
}

interface RawAccessibleTenant {
    tenantId?: number
    tenantName?: string
    tenantCode?: string
    isCurrent?: boolean
}

interface RawUserInfo {
    id?: number
    account?: string
    name?: string
    avatar?: string
    roles?: RawRole[]
    currentTenantId?: number
    currentTenantName?: string
    accessibleTenants?: RawAccessibleTenant[]
}

const isUserInfoLike = (obj: any): boolean =>
    !!(obj && typeof obj === "object" && (obj.account != null || obj.id != null || obj.name != null || obj.currentTenantId != null || Array.isArray(obj.roles)))

/**
 * Servpost 已经把 axios 响应的 res.data 解出来；后端可能返回 R 包装也可能扁平。
 * 这里递归剥几层 .data，找到第一个 isUserInfoLike 的对象。
 */
const unwrap = (res: any): RawUserInfo | null => {
    let cur = res
    for (let i = 0; i < 5; i += 1) {
        if (isUserInfoLike(cur)) return cur as RawUserInfo
        cur = cur?.data
    }
    return null
}

export const mapSessionUser = (res: any, fallbackAccount: string): SessionUser => {
    const info = unwrap(res) || {}
    const roles = Array.isArray(info.roles) ? info.roles : []
    const roleKeys = roles.map((r) => String(r?.key || "")).filter(Boolean)
    return {
        id: info.id ?? null,
        account: info.account ?? fallbackAccount,
        name: info.name ?? info.account ?? fallbackAccount,
        avatar: info.avatar ?? "",
        roles: roles.map((r) => ({ id: r.id ?? 0, key: r.key ?? "", name: r.name ?? "" })),
        roleKeys,
        currentTenantId: info.currentTenantId ?? null,
        currentTenantName: info.currentTenantName ?? "",
        accessibleTenants: Array.isArray(info.accessibleTenants)
            ? info.accessibleTenants.map((t) => ({
                  tenantId: t.tenantId ?? 0,
                  tenantName: t.tenantName ?? "",
                  tenantCode: t.tenantCode ?? "",
                  isCurrent: !!t.isCurrent,
              }))
            : [],
    }
}
```

- [ ] **Step 2：commit**

```bash
git add src/utils/sessionUserMapper.ts
git commit -m "feat(p1): add sessionUserMapper to normalize /user/info response"
```

---

## Task 3：app Dva model（user/menu/permissions 合一）

**Files:**
- Create: `src/models/app.ts`

UMI 4 的 `@umijs/plugins/dist/dva` 自动扫描 `src/models/*.ts`，导出默认对象即可。

- [ ] **Step 1：创建 model**

```ts
// src/models/app.ts
import type { Reducer } from "umi"
import type { SessionUser, SysMenuNode } from "@/utils/sessionStore"

export interface AppState {
    user: SessionUser | null
    menu: SysMenuNode[]
    permissions: string[]
}

const initialState: AppState = {
    user: null,
    menu: [],
    permissions: [],
}

const AppModel = {
    namespace: "app",
    state: initialState,
    reducers: {
        saveData: ((state: AppState, action: { payload: Partial<AppState> }) => ({
            ...state,
            ...action.payload,
        })) as Reducer<AppState>,
        reset: (() => ({ ...initialState })) as Reducer<AppState>,
    },
}

export default AppModel
```

- [ ] **Step 2：commit**

```bash
git add src/models/app.ts
git commit -m "feat(p1): add app dva model with user/menu/permissions"
```

---

## Task 4：app.ts render hook（F5 恢复）

**Files:**
- Modify: `src/app.ts`

UMI runtime 的 `render` 钩子在 React 第一次渲染**之前**同步执行，正好用来从 localStorage 灌回 dva store。

- [ ] **Step 1：替换 app.ts**

```ts
// src/app.ts
import { getDvaApp } from "umi"
import { sessionStore } from "@/utils/sessionStore"

export function render(oldRender: () => void) {
    const cached = sessionStore.readAll()
    if (cached.user) {
        const app: any = getDvaApp()
        app._store.dispatch({ type: "app/saveData", payload: cached })
    }
    oldRender()
}
```

- [ ] **Step 2：commit**

```bash
git add src/app.ts
git commit -m "feat(p1): hydrate dva store from localStorage on app render"
```

---

## Task 5：common/Placeholder 与 common/Forbidden 占位页

**Files:**
- Create: `src/pages/common/Placeholder.tsx`
- Create: `src/pages/common/Forbidden.tsx`

不带返回按钮——P2-P5 上线时替换为真页面。

- [ ] **Step 1：创建 Placeholder**

```tsx
// src/pages/common/Placeholder.tsx
import React from "react"
import { useLocation } from "umi"
import { Empty } from "antd"

const Placeholder: React.FC = () => {
    const { pathname } = useLocation()
    return (
        <div style={{ padding: 48 }}>
            <Empty description={`该页面建设中：${pathname}`} />
        </div>
    )
}

export default Placeholder
```

- [ ] **Step 2：创建 Forbidden**

```tsx
// src/pages/common/Forbidden.tsx
import React from "react"
import { Result } from "antd"

const Forbidden: React.FC = () => {
    return (
        <div style={{ padding: 48 }}>
            <Result status="403" title="403" subTitle="您没有访问该页面的权限" />
        </div>
    )
}

export default Forbidden
```

- [ ] **Step 3：commit**

```bash
git add src/pages/common
git commit -m "feat(p1): add Placeholder and Forbidden pages"
```

---

## Task 6：扩 routers.ts 预注册全部 P1-P5 路径

**Files:**
- Modify: `src/routers/routers.ts`

未实现的页面统一指向 `common/Placeholder`，URL 已定型；P2-P5 上线只改 component 不动 path。

- [ ] **Step 1：替换 routers.ts**

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
            { path: "/403", component: "common/Forbidden" },
            { path: "/system/user", component: "common/Placeholder" },
            { path: "/system/role", component: "common/Placeholder" },
            { path: "/system/menu", component: "common/Placeholder" },
            { path: "/system/dept", component: "common/Placeholder" },
            { path: "/system/dict", component: "common/Placeholder" },
            { path: "/system/tenant", component: "common/Placeholder" },
            { path: "/profile", component: "common/Placeholder" },
        ],
    },
    { path: "/*", component: "404" },
]

export default Routers
```

- [ ] **Step 2：commit**

```bash
git add src/routers/routers.ts
git commit -m "feat(p1): pre-register P2-P5 routes pointing to Placeholder"
```

---

## Task 7：改写登录 effect 并发拉资源

**Files:**
- Modify: `src/pages/login/model.ts`

去掉 `Message.success("登录成功")` 的过早提示——挪到三接口都成功后再弹。

- [ ] **Step 1：替换 handleLogin effect**

把 `src/pages/login/model.ts` 里的 `handleLogin` effect 整段替换为：

```ts
        *handleLogin({ payload, callback }: any, { put, select }: any): any {
            const { username, password, code = "" } = payload

            yield put({ type: "saveDatas", payload: { btnsLoading: true } })

            try {
                const res = yield Servpost.requestRace<IAPI>({
                    url: "/api/auth/user/login",
                    data: {
                        loginType: "admin",
                        authType: "accountPassword",
                        credentials: {
                            account: username,
                            password: password,
                            code: code,
                            uuid: yield select((s: any) => s.login.captchaUuid),
                        },
                    },
                    methods: "post",
                })

                const tokenPayload = res?.data?.token ? res.data : res
                const token = tokenPayload?.token || ""

                if (!token) {
                    Message.error(res?.message || "登录失败")
                    return
                }

                const tokenPrefix = tokenPayload.tokenPrefix || ""
                const fullToken = tokenPrefix ? `${tokenPrefix} ${token}` : token

                // 必须先存 token，下面三个并发请求都要带 Authorization
                sessionStore.setToken(fullToken)
                yield put({ type: "saveDatas", payload: { token: fullToken } })

                // 并发拉用户信息 / 菜单 / 按钮权限
                const [userInfoRes, menuRes, btnPermRes] = yield Promise.all([
                    Servpost.requestRace<IAPI>({ url: "/api/system/adminApi/user/info", data: {}, methods: "get" }),
                    Servpost.requestRace<IAPI>({ url: "/api/system/adminApi/menu/list", data: {}, methods: "post" }),
                    Servpost.requestRace<IAPI>({ url: "/api/system/adminApi/menu/btn/permission", data: {}, methods: "post" }),
                ])

                const user = mapSessionUser(userInfoRes, username)
                const menu = Array.isArray(menuRes?.data) ? menuRes.data : Array.isArray(menuRes) ? menuRes : []
                if (!user.account || menu.length === 0) {
                    sessionStore.clearAll()
                    yield put({ type: "app/reset" })
                    yield put({ type: "saveDatas", payload: { token: "" } })
                    Message.error("获取用户信息或菜单失败，请重新登录")
                    return
                }

                const rawPerms = btnPermRes?.data ?? btnPermRes
                const permissions: string[] = Array.isArray(rawPerms) ? rawPerms.filter((p: any) => typeof p === "string") : []
                if (!Array.isArray(rawPerms)) {
                    Message.warning("按钮权限加载失败，部分操作可能受限")
                }

                yield put({ type: "app/saveData", payload: { user, menu, permissions } })
                sessionStore.writeAll({ user, menu, permissions })

                if (payload.remember) {
                    localStorage.setItem(
                        "rememberAccountInfo",
                        JSON.stringify({
                            account: username,
                            password: utils.customEncrypt({ string: password }),
                        })
                    )
                } else {
                    localStorage.removeItem("rememberAccountInfo")
                }

                Message.success("登录成功")
                callback && callback()
            } finally {
                yield put({ type: "saveDatas", payload: { btnsLoading: false } })
            }
        },
```

并在文件顶部加 import：

```ts
import { sessionStore } from "@/utils/sessionStore"
import { mapSessionUser } from "@/utils/sessionUserMapper"
```

- [ ] **Step 2：commit**

```bash
git add src/pages/login/model.ts
git commit -m "feat(p1): fetch user/menu/permissions concurrently after login"
```

---

## Task 8：改写 handleLoginOut effect 同步清 store

**Files:**
- Modify: `src/pages/login/model.ts`

退出时除了清 token 还要把 `app` model 重置 + 清 user/menu/permissions 三份 localStorage。

- [ ] **Step 1：把 handleLoginOut 整段替换为**

```ts
        *handleLoginOut({ callback }: any, { put }: any): any {
            try {
                yield Servpost.requestRace<IAPI>({
                    url: "/api/auth/user/logout",
                    data: {},
                    methods: "post",
                })
            } catch (e) {
                console.warn("退出登录接口调用异常:", e)
            }
            sessionStore.clearAll()
            clearAuthOnLogout()
            yield put({ type: "app/reset" })
            yield put({ type: "saveDatas", payload: { token: "" } })
            callback && callback()
        },
```

- [ ] **Step 2：commit**

```bash
git add src/pages/login/model.ts
git commit -m "feat(p1): reset app store and clear sessionStore on logout"
```

---

## Task 9：Sidebar 由后端菜单驱动

**Files:**
- Create: `src/pages/baseLayout/Sidebar.tsx`

把后端返回的 `SysMenuNode[]` 递归转换为 antd `<Menu>` 的 items：`type=F` 不渲染、`type=M` 折叠、`type=C` 跳路由、`visible!==1 || status!==1` 跳过。

- [ ] **Step 1：创建 Sidebar.tsx**

```tsx
// src/pages/baseLayout/Sidebar.tsx
import React, { useMemo } from "react"
import { Menu } from "antd"
import { useSelector, useNavigate, useLocation } from "umi"
import * as AntIcons from "@ant-design/icons"
import type { SysMenuNode } from "@/utils/sessionStore"

type MenuItem = Required<React.ComponentProps<typeof Menu>>["items"][number]

const renderIcon = (name: string): React.ReactNode => {
    if (!name) return undefined
    const Icon = (AntIcons as any)[name]
    if (!Icon) return undefined
    return React.createElement(Icon)
}

const buildItems = (nodes: SysMenuNode[]): MenuItem[] => {
    const out: MenuItem[] = []
    for (const node of nodes) {
        if (node.type === "F") continue
        if (node.visible !== 1 || node.status !== 1) continue

        const children = Array.isArray(node.children) ? buildItems(node.children) : []

        if (node.type === "M") {
            // 目录节点没有子菜单就不渲染（避免空目录）
            if (children.length === 0) continue
            out.push({
                key: node.path || `m_${node.id}`,
                label: node.name,
                icon: renderIcon(node.icon),
                children,
            } as MenuItem)
        } else if (node.type === "C") {
            out.push({
                key: node.path,
                label: node.name,
                icon: renderIcon(node.icon),
                ...(children.length > 0 ? { children } : {}),
            } as MenuItem)
        }
    }
    return out
}

const Sidebar: React.FC = () => {
    const menu = useSelector((s: any) => s.app.menu as SysMenuNode[])
    const navigate = useNavigate()
    const location = useLocation()

    const items = useMemo(() => buildItems(menu || []), [menu])
    const selected = location.pathname

    return (
        <Menu
            mode="inline"
            theme="light"
            items={items}
            selectedKeys={[selected]}
            onClick={({ key }) => {
                if (typeof key === "string" && key.startsWith("/")) {
                    navigate(key)
                }
            }}
            style={{ height: "100%", borderRight: 0 }}
        />
    )
}

export default Sidebar
```

- [ ] **Step 2：commit**

```bash
git add src/pages/baseLayout/Sidebar.tsx
git commit -m "feat(p1): render sidebar from backend menu tree"
```

---

## Task 10：UserMenu 顶栏用户下拉

**Files:**
- Create: `src/pages/baseLayout/UserMenu.tsx`

显示当前用户名（从 `state.app.user.name` 取），下拉里只放"退出登录"一项。

- [ ] **Step 1：创建 UserMenu.tsx**

```tsx
// src/pages/baseLayout/UserMenu.tsx
import React from "react"
import { Button, Dropdown, message } from "antd"
import { LogoutOutlined, UserOutlined } from "@ant-design/icons"
import { useDispatch, useNavigate, useSelector } from "umi"
import type { SessionUser } from "@/utils/sessionStore"

const UserMenu: React.FC = () => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const user = useSelector((s: any) => s.app.user as SessionUser | null)

    const handleLogout = () => {
        dispatch({
            type: "login/handleLoginOut",
            callback: () => {
                message.success("已退出登录")
                navigate("/login", { replace: true })
            },
        })
    }

    const items = [
        {
            key: "logout",
            label: (
                <span>
                    <LogoutOutlined /> 退出登录
                </span>
            ),
            onClick: handleLogout,
        },
    ]

    return (
        <Dropdown menu={{ items }} placement="bottomRight">
            <Button type="text" icon={<UserOutlined />}>
                {user?.name || user?.account || "未登录"}
            </Button>
        </Dropdown>
    )
}

export default UserMenu
```

- [ ] **Step 2：commit**

```bash
git add src/pages/baseLayout/UserMenu.tsx
git commit -m "feat(p1): add UserMenu showing current user name"
```

---

## Task 11：baseLayout 接 Sidebar / UserMenu，去掉本地恢复逻辑

**Files:**
- Modify: `src/pages/baseLayout/baseLayout.tsx`

不再做 F5 恢复（那是 app.ts 的事），只保留 token 守卫。把侧栏占位换成真正的 `<Sidebar />`，把内联 dropdown 换成 `<UserMenu />`。

- [ ] **Step 1：替换 baseLayout.tsx**

```tsx
// src/pages/baseLayout/baseLayout.tsx
import React, { useEffect, useState } from "react"
import { Outlet } from "umi"
import { requireAuth } from "@/utils/authGuard"
import Sidebar from "./Sidebar"
import UserMenu from "./UserMenu"
import style from "./baseLayout.less"

const BaseLayout: React.FC = () => {
    const [authed, setAuthed] = useState<boolean>(false)

    useEffect(() => {
        const ok = requireAuth()
        setAuthed(ok)
    }, [])

    if (!authed) {
        return null
    }

    return (
        <div className={style.baseLayoutRoot}>
            <div className={style.header}>
                <div className={style.headerTitle}>Baseline 多租户基线系统</div>
                <div className={style.headerRight}>
                    <UserMenu />
                </div>
            </div>
            <div className={style.body}>
                <div className={style.sidebar}>
                    <Sidebar />
                </div>
                <div className={style.content}>
                    <Outlet />
                </div>
            </div>
        </div>
    )
}

export default BaseLayout
```

- [ ] **Step 2：commit**

```bash
git add src/pages/baseLayout/baseLayout.tsx
git commit -m "feat(p1): wire Sidebar and UserMenu into baseLayout"
```

---

## Task 12：Servpost 401 拦截补清 user/menu/permissions

**Files:**
- Modify: `src/utils/Servpost.ts`

`forceRedirectToLogin` 已经 `localStorage.clear()` 把所有 key 都清了（除了 rememberAccountInfo），所以 token/user/menu/permissions 实际都被清掉了；但 dva store 没重置。401 跳登录用的是 `window.location.href` 整页刷新，store 自然也会被销毁，所以**这一项实际不用改 Servpost**。

但 `clearAuthOnLogout`（在 `cookieUtils.ts`）是退出按钮用的，那个走 `history.push` 不刷新页面，必须显式清 store + localStorage 三份；这个已经在 Task 8 的 handleLoginOut 里加了 `dispatch app/reset` 和 `sessionStore.clearAll()`，没问题。

**结论：本任务只验证不改代码——浏览器手动模拟 401 看拦截链路是否完整。** 这个验证放到 Task 13 一起做。

- [ ] **Step 1：跳过本任务（无代码改动）**

无需改动。

---

## Task 13：浏览器端到端验证

**Files:**
- 无代码改动；纯人工验证。

**前置：** baseline-backend 三个服务（gateway/auth/system）已启动，前端 `yarn dev` 跑在 8000。

按 spec 的"测试计划"七步逐一验证：

- [ ] **Step 1：admin/admin123 登录** → URL 跳到 `/home`，左侧出现菜单（至少有"系统管理"目录），右上角显示 admin
- [ ] **Step 2：点菜单"用户管理"等任意 C 类项** → 跳到 `/system/user`，看到 Placeholder 的"该页面建设中：/system/user"
- [ ] **Step 3：直接地址栏输入 `/system/role`** → 进 Placeholder，**不应该 404**
- [ ] **Step 4：直接地址栏输入 `/not-exist`** → 进 404 页
- [ ] **Step 5：顶栏点退出** → 跳 `/login`，浏览器后退按钮 → 仍在 `/login`（baseLayout 守卫弹回）
- [ ] **Step 6：登录后 F5 刷新** → 仍在 `/home`，菜单仍在，**不闪空数据**（重点验证 app.ts render hook）
- [ ] **Step 7：登录后 DevTools 删 localStorage.token + F5** → 弹回 `/login`
- [ ] **Step 8：登录状态下 DevTools 改 localStorage.token 为乱码** → 第一个业务请求 401 → 弹一次"身份信息过期" → 跳 `/login`

**任意一步失败：** 不要"修一下再继续"，停下来分析根因（systematic-debugging：根因分析、复现、单一假设、最小修复）。

- [ ] **Step 9：全部通过后，提交 TaskList 进度更新**

---

## Self-Review

1. **Spec coverage** — 每条 spec 要求都对应到任务：

   | Spec 章节 | 对应任务 |
   |---|---|
   | 单 Dva model | T3 |
   | F5 恢复在 app.ts | T4 |
   | localStorage 键 + SessionUser/SysMenuNode 类型 | T1 |
   | sessionUserMapper | T2 |
   | Placeholder + Forbidden | T5 |
   | 静态预注册路由 | T6 |
   | 登录后并发拉资源 | T7 |
   | 退出清 store + localStorage | T8 |
   | Sidebar 由后端菜单驱动（M/C/F + visible/status） | T9 |
   | UserMenu 顶栏 | T10 |
   | baseLayout 仅守卫不恢复 | T11 |
   | 401 拦截清三份 | T12（验证型） |
   | 错误处理矩阵 | T7 + T13 |
   | 浏览器测试计划 7 步 | T13 |

2. **Placeholder scan** — 全部步骤都给了完整代码，无 TODO / TBD。

3. **Type consistency** — `SessionUser` / `SysMenuNode` / `CachedSession` 在 sessionStore.ts 单一定义，其他文件 `import type` 引用，不会 drift。`AppState` 的字段名（user/menu/permissions）与 `CachedSession` 完全一致，登录后的 `dispatch app/saveData` 与 `sessionStore.writeAll` 用同一个 payload 对象。

4. **依赖链** — T1 → T2 → T3 → T4，T5/T6 平行，T7 依赖 T1+T2+T3，T8 依赖 T3，T9/T10 依赖 T3，T11 依赖 T9+T10。建议执行顺序：T1, T2, T3, T4, T5, T6, T9, T10, T11, T7, T8, T13（先把 model/工具/页面外壳全建好，最后改登录/退出 effect 串起来，最后浏览器验证）。
