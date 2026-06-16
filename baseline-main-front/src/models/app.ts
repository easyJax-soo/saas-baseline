// src/models/app.ts
import type { Reducer } from "umi"
import { sessionStore, type SessionUser, type SysMenuNode } from "@/utils/sessionStore"

export interface AppState {
    user: SessionUser | null
    menu: SysMenuNode[]
    permissions: string[]
}

const cached = sessionStore.readAll()
const initialState: AppState = {
    user: cached.user,
    menu: cached.menu,
    permissions: cached.permissions,
}

const AppModel = {
    namespace: "app",
    state: initialState,
    reducers: {
        saveData: ((state: AppState, action: { payload: Partial<AppState> }) => ({
            ...state,
            ...action.payload,
        })) as Reducer<AppState, { payload: Partial<AppState>; type: string }>,
        reset: (() => ({ user: null, menu: [], permissions: [] })) as Reducer<AppState, { type: string }>,
    },
}

export default AppModel
