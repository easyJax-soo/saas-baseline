// src/utils/sessionStore.ts

const KEYS = {
    TOKEN: "token",
    TOKEN_PREFIX: "tokenPrefix",
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
    visible: string
    status: string
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
    getTokenPrefix(): string {
        return window.localStorage.getItem(KEYS.TOKEN_PREFIX) || ""
    },
    setTokenPrefix(prefix: string): void {
        if (prefix) {
            window.localStorage.setItem(KEYS.TOKEN_PREFIX, prefix)
        } else {
            window.localStorage.removeItem(KEYS.TOKEN_PREFIX)
        }
    },
    /** 拼装 Authorization header；token 为空返回空串 */
    getAuthHeader(): string {
        const token = window.localStorage.getItem(KEYS.TOKEN) || ""
        if (!token) return ""
        const prefix = window.localStorage.getItem(KEYS.TOKEN_PREFIX) || ""
        return prefix ? `${prefix} ${token}` : token
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
        window.localStorage.removeItem(KEYS.TOKEN_PREFIX)
        window.localStorage.removeItem(KEYS.USER)
        window.localStorage.removeItem(KEYS.MENU)
        window.localStorage.removeItem(KEYS.PERMISSIONS)
    },
}
