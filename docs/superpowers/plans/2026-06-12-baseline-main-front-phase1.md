# baseline-main-front Phase 1 实施计划：脚手架 + 登录 + 主布局

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 `D:/project/benchmark-project/baseline-main-front/` 搭出一个可登录、登录后能进入主布局空壳页的 UMI 4 单体 SPA 脚手架，并验证 401 行为正确。

**Architecture:** UMI 4 + React 18 + AntD 5 + Dva。单体 SPA 部署在根路径 `/`。dev proxy `/api → http://localhost:38080`（baseline-backend gateway 或 frame-service 单体）。鉴权使用 Sa-Token Bearer token 存 localStorage，每请求带 Authorization 头，401 跳 `/login`。无 Qiankun。

**Tech Stack:** umi 4.3.35, react 18, antd 5.22.3, @ant-design/pro-components 2.8.2, @umijs/plugins 4.4.12, axios 1.7.8, dva (via @umijs/plugins/dist/dva), lodash, qs, moment, crypto-js, cross-env

**Spec source:** `docs/superpowers/specs/2026-06-12-baseline-main-front-design.md`

**Scope of THIS plan:** 阶段 1 only —— 脚手架 + Servpost + 登录 + baseLayout + home 空壳页 + 401 跳转。阶段 2-4（用户/角色/菜单/部门/租户/字典/岗位/日志/系统配置 CRUD）放后续 plan。

**Testing approach:** 前端无单元测试框架（baseline 系列项目均无）。每个任务的验证步骤是 `yarn build` 类型/编译检查 + 阶段末浏览器手动验证。

**Backend prerequisite for verification at Phase 1 end:** baseline-backend 至少需要 frame-service 单体或 gateway+auth+system 三个微服务跑在本机（gateway/frame 在 38080，auth 在 38081，system 在 38082）。

---

## File Structure (Phase 1 范围)

新建项目 `D:/project/benchmark-project/baseline-main-front/`，本阶段涉及文件：

**项目根：**
- `package.json` — 依赖与脚本
- `.umirc.ts` — UMI 配置：plugins、proxy、routes、alias
- `tsconfig.json` — TS 配置（继承 umi 默认）
- `typings.d.ts` — 类型声明
- `.npmrc` — npmmirror 源
- `.prettierrc.json` — 格式化配置
- `.gitignore`

**src/：**
- `src/app.ts` — UMI 运行时配置（路由变更钩子，可选；无 qiankun）
- `src/assets/login_bg.png`、`src/assets/logo.png` — 登录页素材（占位即可）
- `src/utils/Servpost.ts` — axios 封装 + 401 拦截
- `src/utils/ServpostInterface.ts` — 请求参数类型
- `src/utils/cookieUtils.ts` — token/localStorage 清理
- `src/utils/utils.ts` — 自定义加密（用于"记住密码"）
- `src/utils/authGuard.ts` — 路由前置守卫
- `src/routers/routers.ts` — 路由表（Phase 1 只含 /login、/、/home、/* 404）
- `src/pages/404.tsx` — 404 页
- `src/pages/login/login.tsx` — 登录页主组件（只有账户密码 tab）
- `src/pages/login/login.less` — 登录页样式
- `src/pages/login/model.ts` — Dva login model
- `src/pages/login/components/LoginForm.tsx` — 账户密码表单
- `src/pages/baseLayout/baseLayout.tsx` — 主布局（Phase 1 只放占位顶栏 + Outlet）
- `src/pages/baseLayout/baseLayout.less`
- `src/pages/home/home.tsx` — 欢迎页（Phase 1 静态文案）

---

## Task 1: 创建项目目录与 package.json

**Files:**
- Create: `D:/project/benchmark-project/baseline-main-front/package.json`

- [ ] **Step 1: 创建项目目录**

Run:
```bash
mkdir -p D:/project/benchmark-project/baseline-main-front/src
```

- [ ] **Step 2: 写入 package.json**

```json
{
  "private": true,
  "name": "baseline-main-front",
  "version": "1.0.0",
  "scripts": {
    "dev": "cross-env BASE_PATH='/' umi dev",
    "build": "cross-env BASE_PATH='/' CAPTCH=true umi build --max_old_space_size=1024",
    "postinstall": "umi setup",
    "setup": "umi setup",
    "start": "npm run dev"
  },
  "dependencies": {
    "@ant-design/icons": "^5.6.1",
    "@ant-design/pro-components": "^2.8.2",
    "antd": "^5.22.3",
    "axios": "^1.7.8",
    "cross-env": "^7.0.3",
    "crypto-js": "^4.2.0",
    "lodash": "^4.17.21",
    "moment": "^2.30.1",
    "qs": "^6.13.1",
    "umi": "^4.3.35"
  },
  "devDependencies": {
    "@types/crypto-js": "^4.2.2",
    "@types/lodash": "^4.17.13",
    "@types/react": "^18.0.33",
    "@types/react-dom": "^18.0.11",
    "@umijs/plugins": "^4.4.12",
    "typescript": "^5.0.3"
  }
}
```

- [ ] **Step 3: 创建 .npmrc**

写入 `D:/project/benchmark-project/baseline-main-front/.npmrc`：
```
registry=https://registry.npmmirror.com/
```

- [ ] **Step 4: 创建 .gitignore**

写入 `D:/project/benchmark-project/baseline-main-front/.gitignore`：
```
node_modules/
.umi
.umi-production
dist/
*.log
.DS_Store
.env.local
```

- [ ] **Step 5: 创建 .prettierrc.json**

写入 `D:/project/benchmark-project/baseline-main-front/.prettierrc.json`：
```json
{
  "semi": false,
  "tabWidth": 4,
  "singleQuote": false,
  "printWidth": 180,
  "trailingComma": "es5"
}
```

- [ ] **Step 6: 安装依赖**

Run（在 `D:/project/benchmark-project/baseline-main-front/` 目录下）：
```bash
cd D:/project/benchmark-project/baseline-main-front && yarn install
```
Expected: 安装完成，自动触发 `umi setup`，生成 `src/.umi` 目录。如果 yarn 不可用回退 `npm install`。

- [ ] **Step 7: Commit**

baseline-main-front 没有自己的 git 仓库（顶层无 git），所以**这一步不真正提交**。在每个 task 末尾 commit 章节出现时，仅作为里程碑记录。如果以后需要，可在项目根 `git init` 后改成真正的 commit。

---

## Task 2: 创建 TypeScript 与 UMI 配置

**Files:**
- Create: `D:/project/benchmark-project/baseline-main-front/tsconfig.json`
- Create: `D:/project/benchmark-project/baseline-main-front/typings.d.ts`
- Create: `D:/project/benchmark-project/baseline-main-front/.umirc.ts`

- [ ] **Step 1: 写入 tsconfig.json**

```json
{
  "extends": "./src/.umi/tsconfig.json"
}
```

- [ ] **Step 2: 写入 typings.d.ts**

```ts
import "umi/typings"

declare module "*.less"
declare module "*.png"
declare module "*.svg"

declare namespace NodeJS {
    interface ProcessEnv {
        CAPTCH?: string
        BASE_PATH?: string
    }
}
```

- [ ] **Step 3: 写入 .umirc.ts**

```ts
import { defineConfig } from "umi"
import Routers from "./src/routers/routers"
import path from "path"

const BASE_PATH = process.env.BASE_PATH
const CAPTCH = process.env.CAPTCH

export default defineConfig({
    plugins: ["@umijs/plugins/dist/dva"],
    define: { "process.env.CAPTCH": CAPTCH },
    dva: {},
    routes: [...Routers],
    npmClient: "yarn",
    outputPath: "dist",
    base: BASE_PATH || "/",
    publicPath: BASE_PATH || "/",
    ignoreMomentLocale: true,
    codeSplitting: {
        jsStrategy: "granularChunks",
    },
    styles: [`body { margin:0px;overflow:hidden;background-color: rgba(247, 247, 250, 1) }`],
    alias: {
        "@": path.resolve(__dirname, "./src"),
        "&": path.resolve(__dirname, `./src/pages`),
    },
    proxy: {
        "/api": {
            target: "http://localhost:38080",
            changeOrigin: true,
            pathRewrite: {
                "^/api": "",
            },
        },
    },
})
```

注意：这里 `routes` 引用了下一步要创建的 routers 文件，本步骤暂时会 fail。Task 3 写完 routers 后才能通过 `umi dev` 启动。

- [ ] **Step 4: Commit milestone**

记录里程碑：脚手架配置完成。

---

## Task 3: 创建空路由表 + 占位页 + Servpost 工具骨架

为了让 `umi dev` 能启动，先把路由表和占位页文件创建完。

**Files:**
- Create: `D:/project/benchmark-project/baseline-main-front/src/routers/routers.ts`
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/login/login.tsx` (占位)
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/baseLayout/baseLayout.tsx` (占位)
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/home/home.tsx` (占位)
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/404.tsx` (占位)
- Create: `D:/project/benchmark-project/baseline-main-front/src/app.ts` (空)

- [ ] **Step 1: 写入 src/routers/routers.ts**

```ts
const Routers = [
    { path: "/login", component: "login/login" },
    {
        path: "/",
        component: "baseLayout/baseLayout",
        routes: [
            { path: "/", redirect: "/home" },
            { path: "/home", component: "home/home" },
        ],
    },
    { path: "/*", component: "404" },
]

export default Routers
```

- [ ] **Step 2: 写入 src/app.ts**

```ts
// UMI runtime config. Reserved for future route hooks / global error handlers.
export {}
```

- [ ] **Step 3: 写入 src/pages/login/login.tsx (占位)**

```tsx
import React from "react"

const Login: React.FC = () => {
    return <div>login placeholder</div>
}

export default Login
```

- [ ] **Step 4: 写入 src/pages/baseLayout/baseLayout.tsx (占位)**

```tsx
import React from "react"
import { Outlet } from "umi"

const BaseLayout: React.FC = () => {
    return (
        <div style={{ width: "100%", height: "100vh", padding: 16 }}>
            <div>baseLayout placeholder</div>
            <Outlet />
        </div>
    )
}

export default BaseLayout
```

- [ ] **Step 5: 写入 src/pages/home/home.tsx (占位)**

```tsx
import React from "react"

const Home: React.FC = () => {
    return <div>home placeholder</div>
}

export default Home
```

- [ ] **Step 6: 写入 src/pages/404.tsx**

```tsx
import React from "react"
import { Result, Button } from "antd"
import { useNavigate } from "umi"

const NotFound: React.FC = () => {
    const navigate = useNavigate()
    return (
        <Result
            status="404"
            title="404"
            subTitle="抱歉，您访问的页面不存在"
            extra={
                <Button type="primary" onClick={() => navigate("/home", { replace: true })}>
                    返回首页
                </Button>
            }
        />
    )
}

export default NotFound
```

- [ ] **Step 7: 启动 dev server 验证脚手架可跑**

Run:
```bash
cd D:/project/benchmark-project/baseline-main-front && yarn dev
```
Expected: 终端打印 `App listening at: http://localhost:8000`，浏览器访问 `http://localhost:8000/` 看到 `baseLayout placeholder` + `home placeholder`。访问 `/login` 看到 `login placeholder`。访问 `/random` 看到 404 页。

如果端口被占用，UMI 会自动用 8001。

操作完成后 Ctrl+C 停掉 dev server。

- [ ] **Step 8: Commit milestone**

里程碑：空壳应用可启动。

---

## Task 4: 实现 Servpost (axios 封装 + 401 拦截)

**Files:**
- Create: `D:/project/benchmark-project/baseline-main-front/src/utils/ServpostInterface.ts`
- Create: `D:/project/benchmark-project/baseline-main-front/src/utils/cookieUtils.ts`
- Create: `D:/project/benchmark-project/baseline-main-front/src/utils/utils.ts`
- Create: `D:/project/benchmark-project/baseline-main-front/src/utils/Servpost.ts`
- Create: `D:/project/benchmark-project/baseline-main-front/src/utils/authGuard.ts`

- [ ] **Step 1: 写入 src/utils/ServpostInterface.ts**

```ts
export interface IAPI {
    url: string
    data: {}
    methods: string
    timeout?: number
    headers?: {}
    uploadProgress?: any
    config?: any
}
```

- [ ] **Step 2: 写入 src/utils/cookieUtils.ts**

直接复用 sihui 的实现（已包含 `clearAuthOnLogout`、`clearAllCookies`、`forceClearAllAuth`、`getCookie`、`setCookie`、`clearCookie`）：

```ts
/**
 * Cookie 与认证信息清理工具
 */

export const clearAllCookies = (): void => {
    document.cookie.split(";").forEach(function (c) {
        const cookieName = c.replace(/^ +/, "").split("=")[0]
        if (cookieName) {
            document.cookie = `${cookieName}=;expires=${new Date().toUTCString()};path=/`
            document.cookie = `${cookieName}=;expires=${new Date().toUTCString()};path=/;domain=${window.location.hostname}`
            document.cookie = `${cookieName}=;expires=${new Date().toUTCString()};path=/;domain=.${window.location.hostname}`
        }
    })

    const commonAuthCookies = [
        "token", "access_token", "refresh_token", "auth_token", "jwt", "session",
        "JSESSIONID", "PHPSESSID", "ASP.NET_SessionId", "connect.sid",
        "Authorization", "Bearer", "X-Auth-Token", "X-Token",
    ]

    commonAuthCookies.forEach((cookieName) => {
        document.cookie = `${cookieName}=;expires=${new Date().toUTCString()};path=/`
        document.cookie = `${cookieName}=;expires=${new Date().toUTCString()};path=/;domain=${window.location.hostname}`
        document.cookie = `${cookieName}=;expires=${new Date().toUTCString()};path=/;domain=.${window.location.hostname}`
    })
}

export const clearAuthOnLogout = (): void => {
    const rememberAccountInfo = window.localStorage.getItem("rememberAccountInfo")

    const keysToRemove = ["token", "domain", "userInfos", "btnPermissionList", "menuList", "dictGroups", "tenantList"]
    keysToRemove.forEach((key) => window.localStorage.removeItem(key))

    if (rememberAccountInfo) {
        window.localStorage.setItem("rememberAccountInfo", rememberAccountInfo)
    }

    clearAllCookies()

    try {
        window.sessionStorage.clear()
    } catch (e) {
        console.warn("清除sessionStorage失败:", e)
    }
}

export const forceClearAllAuth = clearAuthOnLogout
```

注：本项目阶段 1 用不到 cookie 部分（baseline-backend 用 Authorization 头，不写 cookie），但保留这套工具供后续阶段及自定义需求使用。

- [ ] **Step 3: 写入 src/utils/utils.ts**

```ts
const utils = {
    // 自定义加密（用于"记住密码"场景）
    customEncrypt: ({ keyStr = "1:2", string = null as any }): string => {
        if (!keyStr || !string || !(keyStr.indexOf(":") > -1)) return ""
        const keys: any = keyStr.split(":")
        keys[0] = parseInt(keys[0])
        keys[1] = parseInt(keys[1])
        const result: string[] = []
        let start = (string as string).substr(keys[0], (string as string).length)
        start = start.split("").reverse().join("")
        start += (string as string).substr(0, keys[0])
        const data = start.split("")
        for (let i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                result.push(String.fromCharCode(data[i].charCodeAt(0) - keys[1]))
            } else {
                result.push(String.fromCharCode(data[i].charCodeAt(0) + keys[1]))
            }
        }
        return result.join("")
    },
    customDecrypt: ({ keyStr = "1:2", string = null as any }): string => {
        if (!keyStr || !string || !(keyStr.indexOf(":") > -1)) return ""
        let result = ""
        const keys: any = keyStr.split(":")
        keys[0] = parseInt(keys[0])
        keys[1] = parseInt(keys[1])
        let temp
        try {
            for (let i = 0; i < (string as string).length; i++) {
                if (i % 2 == 1) {
                    result += String.fromCharCode((string as string).charCodeAt(i) - keys[1])
                } else {
                    result += String.fromCharCode((string as string).charCodeAt(i) + keys[1])
                }
            }
            temp = result.substr(0, result.length - keys[0])
            result = result.substr(result.length - keys[0], keys[0])
            result += temp.split("").reverse().join("")
        } catch (e) {
            result = ""
        }
        return result
    },
}

export default utils
```

- [ ] **Step 4: 写入 src/utils/authGuard.ts**

```ts
import { history } from "umi"

/**
 * 简单路由守卫：检查 localStorage.token，没有就跳 /login。
 * 在 baseLayout 顶部调用即可。
 */
export const requireAuth = (): boolean => {
    const token = window.localStorage.getItem("token")
    if (!token) {
        if (window.location.pathname !== "/login") {
            history.replace("/login")
        }
        return false
    }
    return true
}

/**
 * 401 时调用：清认证信息 + 强制跳登录页。
 * 用 window.location.href 而不是 history.replace，确保 Dva store 完全重置。
 */
export const forceRedirectToLogin = (): void => {
    const rememberAccountInfo = window.localStorage.getItem("rememberAccountInfo")
    window.localStorage.clear()
    if (rememberAccountInfo) {
        window.localStorage.setItem("rememberAccountInfo", rememberAccountInfo)
    }
    window.location.href = "/login"
}
```

- [ ] **Step 5: 写入 src/utils/Servpost.ts**

```ts
import axios from "axios"
import { IAPI } from "./ServpostInterface"
import { isEmpty } from "lodash"
import { message as Message } from "antd"
import { forceRedirectToLogin } from "./authGuard"

let flag = false // 是否已弹过 401 提示
let isLoggingOut = false

export const reset401Flags = (): void => {
    flag = false
    isLoggingOut = false
}

if (typeof window !== "undefined") {
    window.addEventListener("load", reset401Flags)
    window.addEventListener("beforeunload", reset401Flags)
}

const requestSetTimeOut = <T extends number>(timeout: T): Promise<any> => {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({ data: null, status: "99999", message: "请求超时" })
        }, timeout)
    })
}

const requestPOST = <T extends IAPI>(params: T): Promise<any> => {
    const { url, methods = "post", data, headers, uploadProgress, config = {} } = params
    return new Promise((resolve) => {
        let options: any = {}
        if (methods === "post" || methods === "delete") {
            options = {
                method: methods,
                url: url,
                data: data,
                headers: {
                    Authorization: window.localStorage.getItem("token"),
                    ...headers,
                },
                ...config,
            }
            if (uploadProgress) {
                options.onUploadProgress = (progressEvent: any) => uploadProgress(progressEvent)
            }
        } else if (methods === "get") {
            let getParams = ""
            if (data && !isEmpty(data)) {
                const qs = require("qs")
                getParams = qs.stringify(data)
            }
            options = {
                method: "get",
                url: getParams !== "" ? `${url}?${getParams}` : url,
                headers: {
                    Authorization: window.localStorage.getItem("token"),
                    ...headers,
                },
                ...config,
            }
        }
        axios({ ...options })
            .then((res) => {
                const { status } = res.data || {}
                if (status == 401 && !flag && !isLoggingOut) {
                    flag = true
                    isLoggingOut = true
                    Message.error("身份信息过期，请重新登陆", 1)
                    forceRedirectToLogin()
                    return
                }
                if (status == 403) {
                    console.warn("API权限不足:", res.data?.message || "无权限访问")
                }
                resolve(res.data)
            })
            .catch((error) => {
                if (error?.name === "AbortError" || error?.code === "ERR_CANCELED") {
                    resolve({ data: null, status: "99999", message: "请求已取消" })
                } else {
                    resolve({ data: null, status: "99999", message: "请求异常" })
                }
            })
    })
}

const Servpost = {
    requestRace: <T extends IAPI>(object: T): {} => {
        const { timeout = 10000 } = object
        return Promise.race([requestPOST<IAPI>(object), requestSetTimeOut<number>(Number(timeout))])
    },
}

export default Servpost
```

- [ ] **Step 6: 编译检查**

Run:
```bash
cd D:/project/benchmark-project/baseline-main-front && yarn build
```
Expected: build 成功，无 TS 错误。会在 `dist/` 输出产物。

注意：如果 `yarn build` 因为 home/login 还是占位、没用到 Servpost 而给出 unused 警告，是预期的。但**类型错误**必须为零。

- [ ] **Step 7: Commit milestone**

里程碑：网络层与路由守卫就绪。

---

## Task 5: 实现登录页 (Dva model + LoginForm + 页面)

**Files:**
- Modify: `D:/project/benchmark-project/baseline-main-front/src/pages/login/login.tsx`
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/login/login.less`
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/login/model.ts`
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/login/components/LoginForm.tsx`
- Create: `D:/project/benchmark-project/baseline-main-front/src/assets/.gitkeep` (素材可后续补)

- [ ] **Step 1: 准备素材占位**

Run:
```bash
mkdir -p D:/project/benchmark-project/baseline-main-front/src/assets && touch D:/project/benchmark-project/baseline-main-front/src/assets/.gitkeep
```

后续用户可以放 `login_bg.png` 和 `logo.png`。如果素材缺失，登录页 background 会是空，不影响功能。

- [ ] **Step 2: 写入 src/pages/login/model.ts**

```ts
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { type Reducer } from "umi"
import { message as Message } from "antd"
import utils from "@/utils/utils"
import moment from "moment"
import { clearAuthOnLogout } from "@/utils/cookieUtils"

const needCaptch = process.env.CAPTCH

export interface LoginStatus {
    token: string
    btnsLoading: boolean
    captchImageLoading: boolean
    captchaEnabled: boolean
    captchaImage: string
    captchaUuid: string
}

const LoginModel = {
    namespace: "login",
    state: {
        token: "",
        btnsLoading: false,
        captchImageLoading: true,
        captchaEnabled: false,
        captchaImage: "",
        captchaUuid: "",
    } as LoginStatus,
    effects: {
        *handleLogin({ payload, callback }: any, { put }: any): any {
            const { username, password, code = "" } = payload
            if (!username) {
                Message.error("用户名不能为空")
                return
            }
            if (!password) {
                Message.error("密码不能为空")
                return
            }
            if (!code && needCaptch) {
                Message.error("验证码不能为空")
                return
            }

            yield put({ type: "saveDatas", payload: { btnsLoading: true } })

            const { data, message, status } = yield Servpost.requestRace<IAPI>({
                url: "/api/auth/user/login",
                data: {
                    loginType: "admin",
                    authType: "accountPassword",
                    credentials: {
                        account: username,
                        password: password,
                        code: code,
                        uuid: "",
                    },
                },
                methods: "post",
            })

            yield put({ type: "saveDatas", payload: { btnsLoading: false } })

            if (status == "200") {
                Message.success("登录成功")
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

                const { token = "", tokenPrefix = "" } = data || {}
                const fullToken = tokenPrefix ? `${tokenPrefix} ${token}` : token

                yield put({ type: "saveDatas", payload: { token: fullToken } })
                localStorage.setItem("token", fullToken)
                callback && callback()
            } else {
                Message.error(message)
            }
        },

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
            clearAuthOnLogout()
            yield put({ type: "saveDatas", payload: { token: "" } })
            callback && callback()
        },

        *checkCaptchaEnabled({ callback }: any, { put }: any): any {
            const { data, message, status } = yield Servpost.requestRace<IAPI>({
                url: "/api/auth/captcha/isEnabled",
                data: {},
                methods: "get",
            })

            if (status == 200) {
                const { enabled = false } = data || {}
                yield put({ type: "saveDatas", payload: { captchaEnabled: enabled } })
                if (enabled) {
                    yield put({ type: "getCaptchaImage" })
                }
                callback && callback(enabled)
            } else {
                Message.error(message || "检查验证码状态失败", 1)
                yield put({ type: "saveDatas", payload: { captchaEnabled: false } })
                callback && callback(false)
            }
        },

        *getCaptchaImage({ callback }: any, { put }: any): any {
            yield put({ type: "saveDatas", payload: { captchImageLoading: true } })
            try {
                const { data = {}, message, status } = yield Servpost.requestRace<IAPI>({
                    url: `/api/captcha/image?_=${moment().valueOf()}`,
                    data: {},
                    methods: "get",
                })

                if (status === 200) {
                    const { image = "" } = data
                    yield put({
                        type: "saveDatas",
                        payload: {
                            captchaImage: image ? `data:image/png;base64,${image}` : "",
                            captchaUuid: data.uuid || "",
                        },
                    })
                    callback && callback(true, data)
                } else {
                    Message.error(message || "获取验证码失败")
                    yield put({ type: "saveDatas", payload: { captchaImage: "" } })
                    callback && callback(false)
                }
            } catch (e) {
                console.error("获取验证码错误:", e)
                Message.error("获取验证码失败，请重试")
                yield put({ type: "saveDatas", payload: { captchaImage: "" } })
                callback && callback(false)
            } finally {
                yield put({ type: "saveDatas", payload: { captchImageLoading: false } })
            }
        },
    },
    reducers: {
        saveDatas: ((state: any, action: any) => ({ ...state, ...action.payload })) as Reducer<any>,
    },
}

export default LoginModel
```

- [ ] **Step 3: 写入 src/pages/login/login.less**

```less
.loginBg {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(247, 247, 250, 1);
    background-position: center;
    background-size: cover;

    & .loginAreas {
        width: 528px;
        min-height: 480px;
        border-radius: 12px;
        background-color: #fff;
        position: absolute;
        right: 268px;
        top: 50%;
        transform: translate(0, -50%);
        overflow: hidden;
        box-shadow: 0 4px 24px rgba(0, 0, 0, 0.08);
    }

    & .captchAreas {
        display: flex;
        flex-direction: row;
        align-items: center;

        & .captchImages {
            width: 150px;
            height: 40px;
            margin-left: 16px;
            border-radius: 8px;
            overflow: hidden;
            border: 1px solid #d9d9d9;

            & img {
                width: 102px;
                height: 42px;
                margin-top: -2px;
                margin-left: -1px;
            }
        }
    }

    & .TopTitle {
        display: flex;
        flex-direction: row;
        align-items: center;
        position: absolute;
        top: 27px;
        left: 25px;

        & .logo {
            width: 48px;
            height: 48px;
            background-color: #1677ff;
            border-radius: 8px;
        }

        & .titleContent {
            margin-left: 16px;

            & .mainTitle {
                font-weight: 500;
                font-size: 20px;
                color: rgba(26, 26, 26, 1);
                line-height: 32px;
                margin: 0;
            }

            & .subTitle {
                font-weight: 400;
                font-size: 14px;
                color: rgba(26, 26, 26, 0.5);
                line-height: 22px;
                margin: 0;
            }
        }
    }

    & .rightArea {
        width: 408px;
        margin: 64px auto 32px;
    }

    & .formTitle {
        font-size: 24px;
        font-weight: 500;
        text-align: center;
        margin-bottom: 24px;
        color: rgba(51, 51, 51, 1);
    }

    & .loginBtns {
        width: 100%;
        height: 56px;
    }
}
```

- [ ] **Step 4: 写入 src/pages/login/components/LoginForm.tsx**

```tsx
import React, { useEffect } from "react"
import { Button, Checkbox, Form, Input, Spin } from "antd"
import type { FormProps } from "antd"
import { useDispatch, useSelector, useNavigate } from "umi"
import utils from "@/utils/utils"
import style from "./../login.less"

const needCaptch = process.env.CAPTCH

interface IFieldType {
    username?: string
    password?: string
    code?: string
    remember?: boolean
}

const LoginForm: React.FC = () => {
    const accountInfos = window.localStorage.getItem("rememberAccountInfo")
    const { btnsLoading = false, captchaImage = "", captchImageLoading = false } = useSelector((state: any) => state.login)
    const [form] = Form.useForm()
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const onFinish: FormProps<IFieldType>["onFinish"] = (value): void => {
        dispatch({
            type: "login/handleLogin",
            payload: value,
            callback: () => navigate("/home", { replace: true }),
        })
    }

    const handleGetNewCaptchImage = () => {
        dispatch({ type: "login/getCaptchaImage" })
    }

    useEffect(() => {
        if (accountInfos) {
            const temps = JSON.parse(accountInfos)
            const { account = "", password = "" } = temps
            form.setFieldValue("username", account)
            form.setFieldValue("password", utils.customDecrypt({ string: password }))
        }
        if (needCaptch) {
            dispatch({ type: "login/getCaptchaImage" })
        }
    }, [])

    return (
        <Form
            name="login"
            form={form}
            wrapperCol={{ span: 24 }}
            initialValues={{ remember: true }}
            onFinish={onFinish}
            size="large"
            autoComplete="off"
        >
            <Form.Item<IFieldType> name="username">
                <Input style={{ height: 56 }} placeholder="请输入用户名" />
            </Form.Item>
            <Form.Item<IFieldType> name="password">
                <Input.Password style={{ height: 56 }} placeholder="请输入密码" />
            </Form.Item>

            {needCaptch && (
                <Form.Item<IFieldType> name="code">
                    <div className={style.captchAreas} onClick={handleGetNewCaptchImage}>
                        <Input style={{ height: 56 }} placeholder="请输入验证码" />
                        <div className={style.captchImages}>
                            <Spin spinning={captchImageLoading} style={{ height: 56 }}>
                                <img src={captchaImage} alt="" />
                            </Spin>
                        </div>
                    </div>
                </Form.Item>
            )}

            <Form.Item valuePropName="checked" name="remember" label={null}>
                <Checkbox>记住密码</Checkbox>
            </Form.Item>
            <Form.Item<IFieldType>>
                <Button type="primary" htmlType="submit" className={style.loginBtns} loading={btnsLoading}>
                    登录
                </Button>
            </Form.Item>
        </Form>
    )
}

export default LoginForm
```

- [ ] **Step 5: 重写 src/pages/login/login.tsx**

```tsx
import React from "react"
import style from "./login.less"
import LoginForm from "./components/LoginForm"

const Login: React.FC = () => {
    return (
        <div className={style.loginBg}>
            <div className={style.TopTitle}>
                <div className={style.logo}></div>
                <div className={style.titleContent}>
                    <h1 className={style.mainTitle}>Baseline 多租户基线系统</h1>
                    <h2 className={style.subTitle}>后台管理平台</h2>
                </div>
            </div>
            <div className={style.loginAreas}>
                <div className={style.rightArea}>
                    <div className={style.formTitle}>账户登录</div>
                    <LoginForm />
                </div>
            </div>
        </div>
    )
}

export default Login
```

- [ ] **Step 6: 编译验证**

Run:
```bash
cd D:/project/benchmark-project/baseline-main-front && yarn build
```
Expected: build 成功，无 TS 报错。如果 Dva model 类型推断报警，按提示在 `effects` 函数签名加 `any` 即可（与 sihui 一致）。

- [ ] **Step 7: 启动 dev 浏览器目测**

Run:
```bash
cd D:/project/benchmark-project/baseline-main-front && yarn dev
```
浏览器访问 `http://localhost:8000/login`：
- 看到顶部蓝色 logo 块 + "Baseline 多租户基线系统 / 后台管理平台" 标题
- 中间白卡，内含"账户登录"标题、用户名、密码、记住密码、登录按钮
- 不点击的情况下，不应有任何接口请求（除非后端 `/auth/captcha/isEnabled` 等 — 视 `CAPTCH` env 而定，dev 模式默认未设）

Ctrl+C 停掉。

- [ ] **Step 8: Commit milestone**

里程碑：登录页 UI 完成。

---

## Task 6: 实现 baseLayout + home + 登录后跳转链路打通

**Files:**
- Modify: `D:/project/benchmark-project/baseline-main-front/src/pages/baseLayout/baseLayout.tsx`
- Create: `D:/project/benchmark-project/baseline-main-front/src/pages/baseLayout/baseLayout.less`
- Modify: `D:/project/benchmark-project/baseline-main-front/src/pages/home/home.tsx`

- [ ] **Step 1: 写入 src/pages/baseLayout/baseLayout.less**

```less
.baseLayoutRoot {
    width: 100%;
    height: 100vh;
    display: flex;
    flex-direction: column;
}

.header {
    height: 56px;
    background-color: #fff;
    border-bottom: 1px solid #f0f0f0;
    padding: 0 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-shrink: 0;
}

.headerTitle {
    font-size: 18px;
    font-weight: 500;
    color: rgba(26, 26, 26, 1);
}

.headerRight {
    display: flex;
    align-items: center;
    gap: 16px;
    color: rgba(26, 26, 26, 0.7);
}

.body {
    flex: 1;
    display: flex;
    flex-direction: row;
    min-height: 0;
}

.sidebar {
    width: 220px;
    background-color: #fff;
    border-right: 1px solid #f0f0f0;
    padding: 16px 0;
    flex-shrink: 0;
}

.sidebarPlaceholder {
    padding: 0 24px;
    color: rgba(26, 26, 26, 0.5);
    font-size: 14px;
}

.content {
    flex: 1;
    margin: 24px;
    overflow: auto;
    background-color: #fff;
    border-radius: 8px;
    padding: 24px;
}
```

- [ ] **Step 2: 重写 src/pages/baseLayout/baseLayout.tsx**

```tsx
import React, { useEffect, useState } from "react"
import { Outlet, useDispatch, useNavigate } from "umi"
import { Button, Dropdown, message } from "antd"
import { LogoutOutlined, UserOutlined } from "@ant-design/icons"
import { requireAuth } from "@/utils/authGuard"
import style from "./baseLayout.less"

const BaseLayout: React.FC = () => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const [authed, setAuthed] = useState<boolean>(false)

    useEffect(() => {
        const ok = requireAuth()
        setAuthed(ok)
    }, [])

    const handleLogout = () => {
        dispatch({
            type: "login/handleLoginOut",
            callback: () => {
                message.success("已退出登录")
                navigate("/login", { replace: true })
            },
        })
    }

    if (!authed) {
        return null
    }

    const userMenuItems = [
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
        <div className={style.baseLayoutRoot}>
            <div className={style.header}>
                <div className={style.headerTitle}>Baseline 多租户基线系统</div>
                <div className={style.headerRight}>
                    <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
                        <Button type="text" icon={<UserOutlined />}>
                            当前用户
                        </Button>
                    </Dropdown>
                </div>
            </div>
            <div className={style.body}>
                <div className={style.sidebar}>
                    <div className={style.sidebarPlaceholder}>菜单（Phase 2 接入后端数据）</div>
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

- [ ] **Step 3: 重写 src/pages/home/home.tsx**

```tsx
import React from "react"

const Home: React.FC = () => {
    return (
        <div>
            <h2>欢迎使用 Baseline 多租户基线系统</h2>
            <p style={{ color: "rgba(26, 26, 26, 0.6)", marginTop: 16 }}>
                这是脚手架的占位首页。后续阶段会接入：
            </p>
            <ul style={{ color: "rgba(26, 26, 26, 0.6)", lineHeight: 2 }}>
                <li>系统管理：用户、角色、菜单、部门、岗位、字典</li>
                <li>多租户：租户管理、租户切换</li>
                <li>运维：操作日志、系统配置</li>
            </ul>
        </div>
    )
}

export default Home
```

- [ ] **Step 4: 编译验证**

Run:
```bash
cd D:/project/benchmark-project/baseline-main-front && yarn build
```
Expected: build 成功。

- [ ] **Step 5: Commit milestone**

里程碑：主布局空壳完成。

---

## Task 7: 端到端联调验证

**Files:** 不修改代码。验证 Phase 1 全链路。

**Backend prerequisite:** baseline-backend 至少跑起来 frame-service 单体（在 38080），或 gateway+auth+system 三服务（gateway/frame 38080、auth 38081、system 38082）。数据库已用 `baseline-backend/sql/baseline_system.sql` 初始化，里面有种子用户（通常是 `admin` 账号，密码看 SQL 文件或 baseline-backend 的 README）。

- [ ] **Step 1: 启动后端并确认可用**

让用户确认后端已启动并能响应。Run（在你本机环境，跟用户确认）：
```bash
curl http://localhost:38080/auth/captcha/isEnabled
```
Expected: 返回 JSON，类似 `{"status":200,"data":{"enabled":false},...}`。如果返回 404 或连接被拒，先解决后端再继续。

- [ ] **Step 2: 启动前端**

Run:
```bash
cd D:/project/benchmark-project/baseline-main-front && yarn dev
```
Expected: dev server 启动在 `http://localhost:8000`。

- [ ] **Step 3: 浏览器测试 - 未登录跳 login**

打开浏览器无痕窗口（无任何 localStorage）：
1. 访问 `http://localhost:8000/` → **预期**：自动跳到 `/login`，显示登录页
2. 访问 `http://localhost:8000/home` → **预期**：同样自动跳到 `/login`

如果失败：检查 `requireAuth` 是否在 baseLayout 顶部正确调用。

- [ ] **Step 4: 浏览器测试 - 登录成功跳 home**

在 `/login` 页：
1. 输入种子账号密码（参考 baseline-backend 的 `sql/baseline_system.sql` 中 `sys_user` 表初始数据；常见为 `admin` / `123456` 等）
2. 点"登录"
3. **预期**：toast "登录成功"，1 秒内跳到 `/home`，看到欢迎页 + 顶栏 "Baseline 多租户基线系统" + 右上角"当前用户"按钮

如果登录失败：F12 看 Network 中 `/api/auth/user/login` 的 response。如果返回非 200，检查 `status`、`message` 字段。

- [ ] **Step 5: 浏览器测试 - 401 跳转**

在已登录状态下：
1. 打开 DevTools Console
2. 执行 `localStorage.setItem('token', 'Bearer invalid-token-12345')`
3. 刷新页面（F5）
4. **预期**：因为 baseLayout 在 mount 时 token 存在但是值错误，会触发某个接口请求（暂无主动接口，所以这一步可能不会自动 401；本测试改为下方手动方法）

手动验证 401：
1. 在 Console 执行：
```js
fetch("/api/system/adminApi/user/info", {
  headers: { Authorization: "Bearer invalid-12345" }
}).then(r => r.json()).then(console.log)
```
2. **预期**：返回 `{ status: 401, ... }`。但因为这是手动 fetch，不会触发 Servpost 的 401 拦截。
3. 改为通过 Servpost 验证：临时在 Home 加一个 Test 按钮，点击调用 Servpost.requestRace 去打 `/api/system/adminApi/user/info`。本步骤跳过加测试按钮，改用 Phase 2 自然引入接口后再验证。

简化版 Phase 1 收尾验证：
- 登录成功 → token 写入 localStorage（F12 Application → Local Storage 看到 `token`）
- 点击右上角"当前用户"→"退出登录" → 跳回 `/login`，localStorage 中 `token` 被清除

- [ ] **Step 6: 浏览器测试 - 退出登录**

1. 已登录状态，点右上角"当前用户"
2. 下拉菜单选"退出登录"
3. **预期**：toast "已退出登录"，跳到 `/login`，localStorage 中 `token`、`userInfos` 等 key 被清除（`rememberAccountInfo` 保留）

- [ ] **Step 7: 浏览器测试 - 记住密码**

1. 在 `/login` 输入账号密码，勾选"记住密码"，登录成功
2. 退出登录
3. 刷新 `/login`
4. **预期**：用户名和密码自动填充

- [ ] **Step 8: 浏览器测试 - 404**

访问 `http://localhost:8000/some/random/path`：
- **预期**：未登录会跳 `/login`；已登录会被 baseLayout 包裹并显示 404 页（"404 抱歉，您访问的页面不存在" + "返回首页"按钮）

- [ ] **Step 9: 记录验证结果**

如果 Step 1-8 全部通过，Phase 1 完成。把验证结果（哪些通过、哪些有 quirks）反馈给用户，决定是否进入 Phase 2 plan 的撰写。

如果有失败：进入 systematic-debugging 流程，定位问题。

- [ ] **Step 10: Commit milestone**

里程碑：Phase 1 整体可用。

---

## 后续 Phase 预览（不在本 plan 范围）

Phase 2-4 会在本 plan 完成验收后分别撰写，预计：

- **Phase 2 plan**：`models/user.ts`（拉用户/菜单/按钮权限）、`components/baseMenu`（动态菜单渲染）、`baseTable` ProTable 封装、`setting/user`、`setting/role`、`setting/menu`、`setting/dept` 4 个 CRUD 页
- **Phase 3 plan**：`setting/tenant` + `tenantUser` 子页、`components/baseHeader` 增加租户切换下拉、`setting/dic` 字典管理、`useDictData` hook
- **Phase 4 plan**：`setting/post`、`setting/log`、`setting/system`、`changePassword`、`userCenter`

---

## Self-Review 检查记录

- **Spec 覆盖**：Phase 1 范围对齐 spec §6 阶段 1 描述（"骨架可登录"）。spec §2 路由表中 Phase 1 不涉及的路由（changePassword、userCenter、setting/*）已明确从本 plan 排除。spec §3.4 路由守卫、§3.2 登录流程、§3.3 401 拦截均覆盖。
- **占位扫描**：每个代码 step 都给出了完整代码而不是 TODO。验证步骤都给出了具体命令和预期结果。
- **类型一致**：`requireAuth` / `forceRedirectToLogin` 名称在 Task 4、Task 6、Servpost 中一致。`login/handleLogin` / `login/handleLoginOut` / `login/getCaptchaImage` action 名在 model 和组件中一致。Dva `saveDatas` reducer 名一致。
- **风险口径**：Task 7 Step 5 的 401 验证因为 Phase 1 没有主动调接口而被简化为"手动留到 Phase 2"，这是诚实的处理而不是夸大覆盖。
