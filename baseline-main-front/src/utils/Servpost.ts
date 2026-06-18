import axios from "axios"
import qs from "qs"
import { IAPI } from "./ServpostInterface"
import { isEmpty } from "lodash"
import { message as Message } from "antd"
import { forceRedirectToLogin } from "./authGuard"
import { sessionStore } from "./sessionStore"

let isHandling401 = false

export const reset401Flags = (): void => {
    isHandling401 = false
}

if (typeof window !== "undefined") {
    window.addEventListener("load", reset401Flags)
}

const requestSetTimeOut = <T extends number>(timeout: T): Promise<any> => {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({ data: null, status: "99999", message: "请求超时" })
        }, timeout)
    })
}

const buildAuthHeaders = (extra?: Record<string, any>) => {
    const auth = sessionStore.getAuthHeader()
    return {
        ...(auth ? { Authorization: auth } : {}),
        ...extra,
    }
}

const requestPOST = <T extends IAPI>(params: T): Promise<any> => {
    const { url, methods = "post", data, headers, uploadProgress, config = {} } = params
    return new Promise((resolve) => {
        let options: any = {}
        if (methods === "post" || methods === "delete") {
            options = {
                method: methods,
                url,
                data,
                headers: buildAuthHeaders(headers),
                ...config,
            }
            if (uploadProgress) {
                options.onUploadProgress = (progressEvent: any) => uploadProgress(progressEvent)
            }
        } else if (methods === "get") {
            let getParams = ""
            if (data && !isEmpty(data)) {
                getParams = qs.stringify(data)
            }
            options = {
                method: "get",
                url: getParams !== "" ? `${url}?${getParams}` : url,
                headers: buildAuthHeaders(headers),
                ...config,
            }
        }
        axios({ ...options })
            .then((res) => {
                const status = res?.data?.status
                if (status == 401) {
                    // 登录页发起的请求即使 401 也不强制跳转，由登录流程自己决定
                    const onLoginPage = typeof window !== "undefined" && window.location.pathname === "/login"
                    if (!isHandling401 && !onLoginPage) {
                        isHandling401 = true
                        Message.error("身份信息过期，请重新登录", 1)
                        forceRedirectToLogin()
                        // 1s 后复位，避免页面没真重载时永久锁死
                        setTimeout(() => {
                            isHandling401 = false
                        }, 1000)
                    }
                    resolve({ status: 401, data: null, message: "身份信息过期" })
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
                    return
                }

                const message = error?.response?.data?.message || error?.response?.data?.msg || error?.message || "请求异常"
                const status = error?.response?.data?.status || error?.response?.status || "99999"
                resolve({ data: error?.response?.data?.data ?? null, status, message })
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
