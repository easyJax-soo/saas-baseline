import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { responseData, responseOk } from "@/utils/apiResponse"
import type { DeptNode, SimplePostVO, ProjectTypeGroup, SysMenuNodeVO, SysPermissionNodeVO, SimpleRoleVO, SysDictGroupVO } from "@/utils/types"

const BASE = "/api/system/adminApi"

export interface DictState {
    deptTree: DeptNode[] | null
    postList: SimplePostVO[] | null
    projectGroups: ProjectTypeGroup[] | null
    menuTree: SysMenuNodeVO[] | null
    permTree: SysPermissionNodeVO[] | null
    simpleRoles: SimpleRoleVO[] | null
    dictGroups: SysDictGroupVO[] | null
    loading: { [key: string]: boolean }
}

const initialState: DictState = {
    deptTree: null,
    postList: null,
    projectGroups: null,
    menuTree: null,
    permTree: null,
    simpleRoles: null,
    dictGroups: null,
    loading: {},
}

const DictModel = {
    namespace: "dict",
    state: initialState,
    effects: {
        *loadDeptTree(_: any, { put, select }: any): any {
            const state = yield select((s: any) => s.dict)
            if (state.deptTree) return
            yield put({ type: "setLoading", payload: { key: "deptTree", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/dept/list`, data: {}, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveDict", payload: { key: "deptTree", value: responseData<DeptNode[]>(res) } })
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "deptTree", value: false } })
            }
        },
        *loadPostList(_: any, { put, select }: any): any {
            const state = yield select((s: any) => s.dict)
            if (state.postList) return
            yield put({ type: "setLoading", payload: { key: "postList", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/post/page`, data: { current: 1, size: 1000 }, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveDict", payload: { key: "postList", value: responseData<{ records?: SimplePostVO[] }>(res)?.records || [] } })
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "postList", value: false } })
            }
        },
        *loadProjectGroups(_: any, { put, select }: any): any {
            const state = yield select((s: any) => s.dict)
            if (state.projectGroups) return
            yield put({ type: "setLoading", payload: { key: "projectGroups", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/project/listByType`, data: {}, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveDict", payload: { key: "projectGroups", value: responseData<ProjectTypeGroup[]>(res) } })
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "projectGroups", value: false } })
            }
        },
        *loadMenuTree(_: any, { put, select }: any): any {
            const state = yield select((s: any) => s.dict)
            if (state.menuTree) return
            yield put({ type: "setLoading", payload: { key: "menuTree", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/menu/tree`, data: {}, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveDict", payload: { key: "menuTree", value: responseData<SysMenuNodeVO[]>(res) } })
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "menuTree", value: false } })
            }
        },
        *loadPermTree(_: any, { put, select }: any): any {
            const state = yield select((s: any) => s.dict)
            if (state.permTree) return
            yield put({ type: "setLoading", payload: { key: "permTree", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/sysPermission/tree`, data: {}, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveDict", payload: { key: "permTree", value: responseData<SysPermissionNodeVO[]>(res) } })
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "permTree", value: false } })
            }
        },
        *loadSimpleRoles(_: any, { put, select }: any): any {
            const state = yield select((s: any) => s.dict)
            if (state.simpleRoles) return
            yield put({ type: "setLoading", payload: { key: "simpleRoles", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/role/list`, data: {}, methods: "get" })
                if (responseOk(res)) {
                    yield put({ type: "saveDict", payload: { key: "simpleRoles", value: responseData<SimpleRoleVO[]>(res) } })
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "simpleRoles", value: false } })
            }
        },
        *loadDictGroups(_: any, { put, select }: any): any {
            const state = yield select((s: any) => s.dict)
            if (state.dictGroups) return
            yield put({ type: "setLoading", payload: { key: "dictGroups", value: true } })
            try {
                const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/dict/groups`, data: {}, methods: "post" })
                if (responseOk(res)) {
                    yield put({ type: "saveDict", payload: { key: "dictGroups", value: responseData<SysDictGroupVO[]>(res) } })
                }
            } finally {
                yield put({ type: "setLoading", payload: { key: "dictGroups", value: false } })
            }
        },
        *invalidate({ payload }: any, { put }: any): any {
            yield put({ type: "saveDict", payload: { key: payload, value: null } })
        },
        *clearAll(_: any, { put }: any): any {
            yield put({ type: "reset" })
        },
    },
    reducers: {
        saveDict: ((state: DictState, action: { payload: { key: keyof DictState; value: any } }) => ({
            ...state,
            [action.payload.key]: action.payload.value,
        })) as Reducer<DictState, any>,
        setLoading: ((state: DictState, action: { payload: { key: string; value: boolean } }) => ({
            ...state,
            loading: { ...state.loading, [action.payload.key]: action.payload.value },
        })) as Reducer<DictState, any>,
        reset: (() => ({ ...initialState })) as Reducer<DictState, any>,
    },
}

export default DictModel
