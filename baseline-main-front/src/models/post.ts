import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { message as Message } from "antd"
import { responseData, responseOk } from "@/utils/apiResponse"
import type { PageResult, SysPostSaveDTO, SysPostVO } from "@/utils/types"

const BASE = "/api/system/adminApi/post"

interface PostFilter {
    name?: string
    code?: string
    status?: number
    current: number
    size: number
}

interface PostLoading {
    list: boolean
    save: boolean
    detail: boolean
    remove: boolean
}

export interface PostState {
    list: SysPostVO[]
    total: number
    filter: PostFilter
    detail: SysPostSaveDTO | null
    loading: PostLoading
}

const initialFilter: PostFilter = { current: 1, size: 10 }

const PostModel = {
    namespace: "post",
    state: {
        list: [],
        total: 0,
        filter: { ...initialFilter },
        detail: null,
        loading: { list: false, save: false, detail: false, remove: false },
    } as PostState,
    effects: {
        *fetchPage({ payload }: any, { put, select }: any): any {
            yield put({ type: "setLoading", payload: { key: "list", value: true } })
            try {
                const stateFilter = yield select((s: any) => s.post.filter)
                const filter = { ...stateFilter, ...(payload || {}) }
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/page`, data: filter, methods: "post" })
                if (responseOk(res)) {
                    const page = responseData<PageResult<SysPostVO>>(res)
                    yield put({ type: "saveList", payload: { list: page.records || [], total: page.total || 0 } })
                } else {
                    Message.error(res?.message || "获取岗位列表失败")
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
                    Message.error(res?.message || "获取岗位详情失败")
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
                    yield put({ type: "dict/invalidate", payload: "postList" })
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
                    yield put({ type: "dict/invalidate", payload: "postList" })
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

export default PostModel
