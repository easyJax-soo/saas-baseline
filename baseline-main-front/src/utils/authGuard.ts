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
