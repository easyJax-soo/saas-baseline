/**
 * 解析登录接口返回。后端 R 包装：
 *   { status: 200, message, data: { token, tokenPrefix, expired, loginType }, timestamp }
 * 兼容旧形态：data 直接是 token 字符串、整个 body 就是 token 对象。
 */
export interface ParsedLoginResponse {
    ok: boolean
    message?: string
    token?: string
    tokenPrefix?: string
    expired?: number | string | null
    loginType?: string
}

const isStringNonEmpty = (v: any): v is string => typeof v === "string" && v.length > 0

export const parseLoginResponse = (body: any): ParsedLoginResponse => {
    if (body == null || typeof body !== "object") {
        return { ok: false, message: "响应格式错误" }
    }

    if (body.status != null && Number(body.status) !== 200) {
        return { ok: false, message: body.message || body.msg || `登录失败（${body.status}）` }
    }

    if (isStringNonEmpty(body.data)) {
        return { ok: true, token: body.data, tokenPrefix: "", expired: null, loginType: "" }
    }

    const payload = body.data && typeof body.data === "object" ? body.data : body

    if (isStringNonEmpty(payload?.token)) {
        return {
            ok: true,
            token: payload.token,
            tokenPrefix: isStringNonEmpty(payload.tokenPrefix) ? payload.tokenPrefix : "",
            expired: payload.expired ?? null,
            loginType: typeof payload.loginType === "string" ? payload.loginType : "",
        }
    }

    return { ok: false, message: body.message || body.msg || "未返回 token" }
}
