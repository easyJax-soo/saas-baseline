import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { message as Message } from "antd"
import { responseData, responseOk } from "@/utils/apiResponse"
import type { SysRolePageVO, SysRoleSaveDTO, PageResult } from "@/utils/types"

const BASE = "/api/system/adminApi/role"

interface RoleFilter {
    name?: string
    key?: string
    status?: number
    dataScope?: number
    current: number
    size: number
}

interface RoleLoading {
    list: boolean
    save: boolean
    detail: boolean
    remove: boolean
}

export interface RoleState {
    list: SysRolePageVO[]
    total: number
    filter: RoleFilter
    detail: SysRoleSaveDTO | null
    loading: RoleLoading
}

const initialFilter: RoleFilter = { current: 1, size: 10 }

const RoleModel = {
    namespace: "role",
    state: {
        list: [],
        total: 0,
        filter: { ...initialFilter },
        detail: null,
        loading: { list: false, save: false, detail: false, remove: false },
    } as RoleState,
    effects: {
        *fetchPage({ payload }: any, { put, select }: any): any {
            yield put({ type: "setLoading", payload: { key: "list", value: true } })
            try {
                const stateFilter = yield select((s: any) => s.role.filter)
                const filter = { ...stateFilter, ...(payload || {}) }
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/page`, data: filter, methods: "post" })
                if (responseOk(res)) {
                    const page = responseData<PageResult<SysRolePageVO>>(res)
                    yield put({ type: "saveList", payload: { list: page.records || [], total: page.total || 0 } })
                } else {
                    Message.error(res?.message || "获取角色列表失败")
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "list", value: false } })
            }
        },
        *fetchDetail({ payload }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "detail", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/detail`, data: { id: payload }, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveDetail", payload: responseData(res) })
                } else {
                    Message.error(res?.message || "获取角色详情失败")
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "detail", value: false } })
            }
        },
        *saveOrUpdate({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "save", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/saveOrUpdate`, data: payload, methods: "post" })
                if (responseOk(res)) {
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
                if (responseOk(res)) {
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
        *saveFilter({ payload }: any, { put }: any): any {
            yield put({ type: "mergeFilter", payload: { ...payload, current: 1 } })
        },
        *pageChange({ payload }: any, { put }: any): any {
            yield put({ type: "mergeFilter", payload })
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
            loading: { list: false, save: false, detail: false, remove: false },
        })) as Reducer<any>,
    },
}

export default RoleModel
