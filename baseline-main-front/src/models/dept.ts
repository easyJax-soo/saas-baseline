import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { message as Message } from "antd"
import { responseData, responseOk } from "@/utils/apiResponse"
import type { SysDeptNodeVO, SysDeptSaveDTO } from "@/utils/types"

const BASE = "/api/system/adminApi/dept"

interface DeptFilter {
    name?: string
    code?: string
    status?: number
}

interface DeptLoading {
    list: boolean
    save: boolean
    detail: boolean
    remove: boolean
}

export interface DeptState {
    list: SysDeptNodeVO[]
    filter: DeptFilter
    detail: SysDeptSaveDTO | null
    loading: DeptLoading
}

const DeptModel = {
    namespace: "dept",
    state: {
        list: [],
        filter: {},
        detail: null,
        loading: { list: false, save: false, detail: false, remove: false },
    } as DeptState,
    effects: {
        *fetchList({ payload }: any, { put, select }: any): any {
            yield put({ type: "setLoading", payload: { key: "list", value: true } })
            try {
                const stateFilter = yield select((s: any) => s.dept.filter)
                const filter = { ...stateFilter, ...(payload || {}) }
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/list`, data: filter, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveList", payload: responseData<SysDeptNodeVO[]>(res) || [] })
                } else {
                    Message.error(res?.message || "获取部门列表失败")
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
                    Message.error(res?.message || "获取部门详情失败")
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
                    yield put({ type: "fetchList" })
                    yield put({ type: "dict/invalidate", payload: "deptTree" })
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
                    yield put({ type: "fetchList" })
                    yield put({ type: "dict/invalidate", payload: "deptTree" })
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
            yield put({ type: "mergeFilter", payload })
        },
    },
    reducers: {
        saveList: ((state: any, action: any) => ({ ...state, list: action.payload })) as Reducer<any>,
        saveDetail: ((state: any, action: any) => ({ ...state, detail: action.payload })) as Reducer<any>,
        mergeFilter: ((state: any, action: any) => ({ ...state, filter: { ...state.filter, ...action.payload } })) as Reducer<any>,
        setLoading: ((state: any, action: any) => ({
            ...state,
            loading: { ...state.loading, [action.payload.key]: action.payload.value },
        })) as Reducer<any>,
        reset: (() => ({ list: [], filter: {}, detail: null, loading: { list: false, save: false, detail: false, remove: false } })) as Reducer<any>,
    },
}

export default DeptModel
