import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { message as Message } from "antd"
import type { SysMenuNodeVO } from "@/utils/types"

const BASE = "/api/system/adminApi/menu"

interface MenuFilter {
    name?: string
    projectCode?: string
}

interface MenuLoading {
    tree: boolean
    save: boolean
    detail: boolean
    remove: boolean
}

export interface MenuState {
    tree: SysMenuNodeVO[]
    filter: MenuFilter
    detail: SysMenuNodeVO | null
    loading: MenuLoading
}

const MenuModel = {
    namespace: "menu",
    state: {
        tree: [],
        filter: {},
        detail: null,
        loading: { tree: false, save: false, detail: false, remove: false },
    } as MenuState,
    effects: {
        *fetchTree(_: any, { put, select }: any): any {
            yield put({ type: "setLoading", payload: { key: "tree", value: true } })
            try {
                const filter = yield select((s: any) => s.menu.filter)
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/tree`, data: filter, methods: "post" })
                if (res?.status === 200) {
                    yield put({ type: "saveTree", payload: res.data || [] })
                } else {
                    Message.error(res?.message || "获取菜单树失败")
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "tree", value: false } })
            }
        },
        *fetchDetail({ payload }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "detail", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/detail`, data: { id: payload }, methods: "post" })
                if (res?.status === 200) {
                    yield put({ type: "saveDetail", payload: res.data })
                } else {
                    Message.error(res?.message || "获取菜单详情失败")
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
                    yield put({ type: "fetchTree" })
                    yield put({ type: "dict/invalidate", payload: "menuTree" })
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
                    yield put({ type: "fetchTree" })
                    yield put({ type: "dict/invalidate", payload: "menuTree" })
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
        saveTree: ((state: any, action: any) => ({ ...state, tree: action.payload })) as Reducer<any>,
        saveDetail: ((state: any, action: any) => ({ ...state, detail: action.payload })) as Reducer<any>,
        mergeFilter: ((state: any, action: any) => ({ ...state, filter: { ...state.filter, ...action.payload } })) as Reducer<any>,
        setLoading: ((state: any, action: any) => ({
            ...state,
            loading: { ...state.loading, [action.payload.key]: action.payload.value },
        })) as Reducer<any>,
        reset: (() => ({ tree: [], filter: {}, detail: null, loading: { tree: false, save: false, detail: false, remove: false } })) as Reducer<any>,
    },
}

export default MenuModel
