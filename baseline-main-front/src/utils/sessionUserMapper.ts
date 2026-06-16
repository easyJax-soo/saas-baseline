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
