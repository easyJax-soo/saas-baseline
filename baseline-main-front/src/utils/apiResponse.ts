const isWrappedResponse = (res: any): boolean => res && typeof res === "object" && "status" in res && ("data" in res || "message" in res)

export const responseOk = (res: any): boolean => {
    if (isWrappedResponse(res)) {
        return res.status === 200
    }
    return res !== null && res !== undefined && res !== false
}

export const responseData = <T = any>(res: any): T => {
    if (isWrappedResponse(res) && res.status === 200) {
        return res.data as T
    }
    return res as T
}
