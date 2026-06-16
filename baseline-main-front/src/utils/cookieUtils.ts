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
