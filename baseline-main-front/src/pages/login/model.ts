import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { type Reducer } from "umi"
import { message as Message } from "antd"
import utils from "@/utils/utils"
import { clearAuthOnLogout } from "@/utils/cookieUtils"
import { sessionStore } from "@/utils/sessionStore"
import { mapSessionUser } from "@/utils/sessionUserMapper"
import { parseLoginResponse } from "@/utils/parseLoginResponse"

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
        captchImageLoading: false,
        captchaEnabled: false,
        captchaImage: "",
        captchaUuid: "",
    } as LoginStatus,
    effects: {
        *handleLogin({ payload, callback }: any, { put, select }: any): any {
            const { username, password, code = "" } = payload

            yield put({ type: "saveDatas", payload: { btnsLoading: true } })

            try {
                const loginRes = yield Servpost.requestRace<IAPI>({
                    url: "/api/auth/user/login",
                    data: {
                        loginType: "admin",
                        authType: "accountPassword",
                        credentials: {
                            account: username,
                            password,
                            code,
                            uuid: yield select((s: any) => s.login.captchaUuid),
                        },
                    },
                    methods: "post",
                })

                const parsed = parseLoginResponse(loginRes)
                if (!parsed.ok || !parsed.token) {
                    Message.error(parsed.message || "登录失败")
                    return
                }

                // 1) 写 token / prefix（前缀单独存，请求时拦截器拼）
                sessionStore.setToken(parsed.token)
                sessionStore.setTokenPrefix(parsed.tokenPrefix || "")
                yield put({ type: "saveDatas", payload: { token: parsed.token } })

                // 2) user/menu/perm 软失败：任一报错不阻塞登录跳转
                let user = mapSessionUser(null, username)
                let menu: any[] = []
                let permissions: string[] = []

                const [userInfoRes, menuRes, btnPermRes] = yield Promise.all([
                    Servpost.requestRace<IAPI>({ url: "/api/system/adminApi/user/info", data: {}, methods: "get" }),
                    Servpost.requestRace<IAPI>({ url: "/api/system/adminApi/menu/list", data: {}, methods: "post" }),
                    Servpost.requestRace<IAPI>({ url: "/api/system/adminApi/menu/btn/permission", data: {}, methods: "post" }),
                ])

                if (userInfoRes?.status === 200) {
                    user = mapSessionUser(userInfoRes, username)
                } else {
                    console.warn("获取用户信息失败：", userInfoRes?.message)
                }

                const menuPayload = menuRes?.status === 200 ? menuRes.data : menuRes
                if (Array.isArray(menuPayload)) {
                    menu = menuPayload
                } else if (Array.isArray(menuRes?.data)) {
                    menu = menuRes.data
                } else {
                    console.warn("获取菜单失败：", menuRes?.message)
                }

                const permPayload = btnPermRes?.status === 200 ? btnPermRes.data : btnPermRes
                if (Array.isArray(permPayload)) {
                    permissions = permPayload.filter((p: any) => typeof p === "string")
                } else if (Array.isArray(btnPermRes?.data)) {
                    permissions = btnPermRes.data.filter((p: any) => typeof p === "string")
                } else {
                    console.warn("获取按钮权限失败：", btnPermRes?.message)
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

                if (menu.length === 0) {
                    Message.warning("登录成功，但未加载到菜单，请联系管理员")
                } else {
                    Message.success("登录成功")
                }
                callback && callback()
            } finally {
                yield put({ type: "saveDatas", payload: { btnsLoading: false } })
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
            sessionStore.clearAll()
            clearAuthOnLogout()
            yield put({ type: "app/reset" })
            yield put({ type: "dict/clearAll" })
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
                const {
                    data = {},
                    message,
                    status,
                } = yield Servpost.requestRace<IAPI>({
                    url: `/api/captcha/image?_=${Date.now()}`,
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
