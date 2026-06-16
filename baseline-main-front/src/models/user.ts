import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { message as Message } from "antd"
import type { SysUserPageVO, SysUserDetailVO, PageResult } from "@/utils/types"

const BASE = "/api/system/adminApi/user"

interface UserFilter {
    name?: string
    phone?: string
    status?: number
    deptId?: number
    beginTime?: string
    endTime?: string
    current: number
    size: number
}

interface UserLoading {
    list: boolean
    save: boolean
    detail: boolean
    remove: boolean
    resetPwd: boolean
}

export interface UserState {
    list: SysUserPageVO[]
    total: number
    filter: UserFilter
    detail: SysUserDetailVO | null
    loading: UserLoading
}

const initialFilter: UserFilter = { current: 1, size: 10 }

const UserModel = {
    namespace: "user",
    state: {
        list: [],
        total: 0,
        filter: { ...initialFilter },
        detail: null,
        loading: { list: false, save: false, detail: false, remove: false, resetPwd: false },
    } as UserState,
    effects: {
        *fetchPage(_: any, { put, select }: any): any {
            yield put({ type: "setLoading", payload: { key: "list", value: true } })
            try {
                const filter = yield select((s: any) => s.user.filter)
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/page`, data: filter, methods: "post" })
                if (res?.status === 200) {
                    const page = res.data as PageResult<SysUserPageVO>
                    yield put({ type: "saveList", payload: { list: page.records || [], total: page.total || 0 } })
                } else {
                    Message.error(res?.message || "获取用户列表失败")
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "list", value: false } })
            }
        },
        *fetchDetail({ payload }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "detail", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/detail`, data: { id: payload }, methods: "post" })
                if (res?.status === 200) {
                    yield put({ type: "saveDetail", payload: res.data })
                } else {
                    Message.error(res?.message || "获取用户详情失败")
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "detail", value: false } })
            }
        },
        *saveOrUpdate({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "save", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/saveOrUpdate`, data: payload, methods: "post" })
                if (res?.status === 200 && res.data !== false) {
                    Message.success("保存成功")
                    yield put({ type: "fetchPage" })
                    callback?.(true)
                } else {
                    Message.error(res?.message || "保存失败")
                    callback?.(false)
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "save", value: false } })
            }
        },
        *remove({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "remove", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/remove`, data: payload, methods: "post" })
                if (res?.status === 200 && res.data !== false) {
                    Message.success("删除成功")
                    yield put({ type: "fetchPage" })
                    callback?.(true)
                } else {
                    Message.error(res?.message || "删除失败")
                    callback?.(false)
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "remove", value: false } })
            }
        },
        *resetPwd({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "resetPwd", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/resetPw`, data: payload, methods: "post" })
                if (res?.status === 200 && res.data !== false) {
                    Message.success("密码重置成功")
                    callback?.(true)
                } else {
                    Message.error(res?.message || "重置密码失败")
                    callback?.(false)
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "resetPwd", value: false } })
            }
        },
        *saveFilter({ payload }: any, { put }: any): any {
            yield put({ type: "mergeFilter", payload: { ...payload, current: 1 } })
        },
        *pageChange({ payload }: any, { put }: any): any {
            yield put({ type: "mergeFilter", payload: payload })
        },
    },
    reducers: {
        saveList: ((state: any, action: any) => ({ ...state, list: action.payload.list, total: action.payload.total })) as Reducer<any>,
        saveDetail: ((state: any, action: any) => ({ ...state, detail: action.payload })) as Reducer<any>,
        mergeFilter: ((state: any, action: any) => ({ ...state, filter: { ...state.filter, ...action.payload } })) as Reducer<any>,
        setLoading: ((state: any, action: any) => ({
            ...state,
            loading: { ...state.loading, [action.payload.key]: action.payload.value },
        })) as Reducer<any>,
        reset: (() => ({
            list: [],
            total: 0,
            filter: { ...initialFilter },
            detail: null,
            loading: { list: false, save: false, detail: false, remove: false, resetPwd: false },
        })) as Reducer<any>,
    },
}

export default UserModel
