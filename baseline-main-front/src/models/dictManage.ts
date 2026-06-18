import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { message as Message } from "antd"
import { responseData, responseOk } from "@/utils/apiResponse"
import type { PageResult, SysDictDataVO, SysDictTypeVO } from "@/utils/types"

const BASE = "/api/system/adminApi/dict"

interface DictDataFilter {
    label?: string
    code?: string
    status?: number
    current: number
    size: number
}

interface DictManageLoading {
    typeList: boolean
    dataList: boolean
    typeSave: boolean
    dataSave: boolean
    typeRemove: boolean
    dataRemove: boolean
}

export interface DictManageState {
    typeList: SysDictTypeVO[]
    dataList: SysDictDataVO[]
    dataTotal: number
    selectedType: SysDictTypeVO | null
    dataFilter: DictDataFilter
    loading: DictManageLoading
}

const initialDataFilter: DictDataFilter = { current: 1, size: 10 }

const DictManageModel = {
    namespace: "dictManage",
    state: {
        typeList: [],
        dataList: [],
        dataTotal: 0,
        selectedType: null,
        dataFilter: { ...initialDataFilter },
        loading: { typeList: false, dataList: false, typeSave: false, dataSave: false, typeRemove: false, dataRemove: false },
    } as DictManageState,
    effects: {
        *fetchTypeList(_: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "typeList", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/type/list`, data: {}, methods: "post" })
                if (responseOk(res)) {
                    const list = responseData<SysDictTypeVO[]>(res) || []
                    yield put({ type: "saveTypeList", payload: list })
                    yield put({ type: "ensureSelectedType", payload: list })
                } else {
                    Message.error(res?.message || "获取字典类型失败")
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "typeList", value: false } })
            }
        },
        *saveType({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "typeSave", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/type`, data: payload, methods: "post" })
                if (responseOk(res)) {
                    Message.success("保存成功")
                    yield put({ type: "fetchTypeList" })
                    yield put({ type: "dict/invalidate", payload: "dictGroups" })
                    callback?.(true)
                } else {
                    Message.error(res?.message || "保存失败")
                    callback?.(false)
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "typeSave", value: false } })
            }
        },
        *removeType({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "typeRemove", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/type/remove`, data: payload, methods: "post" })
                if (responseOk(res)) {
                    Message.success("删除成功")
                    yield put({ type: "fetchTypeList" })
                    yield put({ type: "clearData" })
                    yield put({ type: "dict/invalidate", payload: "dictGroups" })
                    callback?.(true)
                } else {
                    Message.error(res?.message || "删除失败")
                    callback?.(false)
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "typeRemove", value: false } })
            }
        },
        *fetchDataPage({ payload }: any, { put, select }: any): any {
            yield put({ type: "setLoading", payload: { key: "dataList", value: true } })
            try {
                const state = yield select((s: any) => s.dictManage)
                const filter = { ...state.dataFilter, code: state.selectedType?.code, ...(payload || {}) }
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/data/page`, data: filter, methods: "post" })
                if (responseOk(res)) {
                    const page = responseData<PageResult<SysDictDataVO>>(res)
                    yield put({ type: "saveDataList", payload: { list: page.records || [], total: page.total || 0 } })
                } else {
                    Message.error(res?.message || "获取字典数据失败")
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "dataList", value: false } })
            }
        },
        *saveData({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "dataSave", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/data`, data: payload, methods: "post" })
                if (responseOk(res)) {
                    Message.success("保存成功")
                    yield put({ type: "fetchDataPage" })
                    yield put({ type: "dict/invalidate", payload: "dictGroups" })
                    callback?.(true)
                } else {
                    Message.error(res?.message || "保存失败")
                    callback?.(false)
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "dataSave", value: false } })
            }
        },
        *removeData({ payload, callback }: any, { put }: any): any {
            yield put({ type: "setLoading", payload: { key: "dataRemove", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/data/remove`, data: payload, methods: "post" })
                if (responseOk(res)) {
                    Message.success("删除成功")
                    yield put({ type: "fetchDataPage" })
                    yield put({ type: "dict/invalidate", payload: "dictGroups" })
                    callback?.(true)
                } else {
                    Message.error(res?.message || "删除失败")
                    callback?.(false)
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "dataRemove", value: false } })
            }
        },
        *selectType({ payload }: any, { put }: any): any {
            yield put({ type: "saveSelectedType", payload })
            yield put({ type: "mergeDataFilter", payload: { current: 1, size: 10, label: undefined, status: undefined } })
        },
        *saveDataFilter({ payload }: any, { put }: any): any {
            yield put({ type: "mergeDataFilter", payload: { ...payload, current: 1 } })
        },
        *pageChange({ payload }: any, { put }: any): any {
            yield put({ type: "mergeDataFilter", payload })
        },
    },
    reducers: {
        saveTypeList: ((state: any, action: any) => ({ ...state, typeList: action.payload })) as Reducer<any>,
        ensureSelectedType: ((state: any, action: any) => {
            const selectedType = state.selectedType && action.payload.some((item: SysDictTypeVO) => item.id === state.selectedType.id) ? state.selectedType : action.payload[0] || null
            return { ...state, selectedType }
        }) as Reducer<any>,
        saveSelectedType: ((state: any, action: any) => ({ ...state, selectedType: action.payload })) as Reducer<any>,
        saveDataList: ((state: any, action: any) => ({ ...state, dataList: action.payload.list, dataTotal: action.payload.total })) as Reducer<any>,
        mergeDataFilter: ((state: any, action: any) => ({ ...state, dataFilter: { ...state.dataFilter, ...action.payload } })) as Reducer<any>,
        clearData: ((state: any) => ({ ...state, dataList: [], dataTotal: 0, selectedType: null, dataFilter: { ...initialDataFilter } })) as Reducer<any>,
        setLoading: ((state: any, action: any) => ({
            ...state,
            loading: { ...state.loading, [action.payload.key]: action.payload.value },
        })) as Reducer<any>,
        reset: (() => ({
            typeList: [],
            dataList: [],
            dataTotal: 0,
            selectedType: null,
            dataFilter: { ...initialDataFilter },
            loading: { typeList: false, dataList: false, typeSave: false, dataSave: false, typeRemove: false, dataRemove: false },
        })) as Reducer<any>,
    },
}

export default DictManageModel
