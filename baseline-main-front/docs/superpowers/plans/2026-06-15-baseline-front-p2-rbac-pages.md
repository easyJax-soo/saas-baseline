# P2 — RBAC 三张管理页实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 把 `/system/{user,role,menu}` 三张占位页落地为用户/角色/菜单 CRUD + 角色绑定，覆盖后端 DTO 全字段。

**Architecture:** 每页独立 dva model（user/role/menu）+ 共享 dict model 缓存字典；Drawer 编辑、树形表格（菜单）、一行筛选栏、rowSelection 批量删除、PermButton 按钮权限隐藏。

**Tech Stack:** UMI 4 + React 18 + Dva + AntD 5 + TypeScript

**注意：本工程无单元测试框架。TDD 替换为：写码 → `tsc --noEmit` 类型检查 → 浏览器人工点验。每一步的"验证"均指启动 `npm run dev` 并确认页面行为正确。**

---

## 后端端点核实（实施时对照）

| 模块 | 实际路径 | 方法 | 备注 |
|---|---|---|---|
| 用户 | `/system/adminApi/user/page/saveOrUpdate/detail/remove/resetPw` | POST | - |
| 角色 | `/system/adminApi/role/page/saveOrUpdate/detail/remove` | POST | list 是 GET `/system/adminApi/role/list` |
| 菜单 | `/system/adminApi/menu/tree/saveOrUpdate/detail/remove` | POST | - |
| 权限 | `/system/adminApi/sysPermission/tree` | POST | - |
| 部门 | `/system/adminApi/dept/list` | POST | 返回 `SysDeptNodeVO[]` 树结构，FilterDTO 请求体 |
| 岗位 | `/system/adminApi/post/page` | POST | 无纯 list，用 page size=1000 替代 |
| 项目 | `/system/adminApi/project/listByType` | POST | 返回按 type 分组列表 |

---

### 前置条件

- [ ] 确认 `baseline_system.sql` 中已补入 9 条 `sys_menu type=F` 行（与 `PERMS` 常量对齐），并赋给 super_admin 角色。（不在本计划范围内，假设已就绪）
- [ ] 确认 `sessionStore` 和 `Servpost` 已在 P1 中可用

---

### Task 1: 基础设施 — 常量定义 + 类型声明 + dict model

**Files:**
- Create: `src/constants/perms.ts`
- Create: `src/constants/dataScope.ts`
- Create: `src/models/dict.ts`
- Create: `src/utils/types.ts`
- Modify: `src/pages/login/model.ts:119` — 追加 dict/clearAll

**Step 1: Create `src/constants/perms.ts`**

```ts
export const PERMS = {
  user:  { add:"system:user:add", edit:"system:user:edit", del:"system:user:remove", reset:"system:user:resetPwd" },
  role:  { add:"system:role:add", edit:"system:role:edit", del:"system:role:remove" },
  menu:  { add:"system:menu:add", edit:"system:menu:edit", del:"system:menu:remove" },
} as const
```

**Step 2: Create `src/constants/dataScope.ts`**

```ts
export enum DataScope {
  ALL = 1,
  CUSTOM = 2,
  DEPT = 3,
  DEPT_AND_CHILD = 4,
  SELF = 5,
}

export const DATA_SCOPE_TEXT: Record<DataScope, string> = {
  [DataScope.ALL]: "全部数据",
  [DataScope.CUSTOM]: "自定义部门",
  [DataScope.DEPT]: "本部门",
  [DataScope.DEPT_AND_CHILD]: "本部门及以下",
  [DataScope.SELF]: "仅本人",
}
```

**Step 3: Create `src/utils/types.ts`**

```ts
// 字典结点
export interface DeptNode {
  id: number
  parentId: number
  name: string
  children?: DeptNode[]
}

export interface SimplePostVO {
  id: number
  name: string
  code: string
}

export interface ProjectVO {
  id: number
  name: string
  code: string
}

export interface ProjectTypeGroup {
  type: string
  projects: ProjectVO[]
}

// 用户
export interface SysUserPageVO {
  id: number
  account: string
  name: string
  phone: string
  deptId: number
  deptName: string
  status: number
  createTime: string
  hasThirdPartyBind: boolean
  hasRealNameAuth: boolean
  realNameAuthStatus: number
}

export interface SysUserDetailVO extends SysUserPageVO {
  email: string
  sex: number
  avatar: string
  remark: string
  roleIds: number[]
  postIds: number[]
  accessibleTenants: any[]
}

export interface SysUserSaveDTO {
  id?: number
  deptId: number
  account: string
  password?: string
  name: string
  email: string
  phone: string
  sex: number
  avatar: string
  status: number
  remark: string
  roleIds: number[]
  postIds: number[]
}

// 角色
export interface SysRolePageVO {
  id: number
  name: string
  key: string
  status: number
  createTime: string
  dataScope: number
}

export interface SimpleRoleVO {
  id: number
  name: string
  key: string
}

export interface SysRoleSaveDTO {
  id?: number
  name: string
  key: string
  dataScope: number
  status: number
  remark: string
  menuIds: number[]
  permissionIds: number[]
  deptIds: number[]
  projectCodes: string[]
}

// 菜单
export interface SysMenuNodeVO {
  id: number
  parentId: number
  name: string
  path: string
  component: string
  type: "M" | "C" | "F"
  visible: number
  status: number
  icon: string
  key: string
  sortNo: number
  cache: number
  target: string
  projectCode: string
  remark: string
  level: number
  createTime: string
  children?: SysMenuNodeVO[]
}

export interface SysMenuSaveDTO {
  id?: number
  parentId: number
  name: string
  path: string
  component: string
  type: "M" | "C" | "F"
  visible: number
  status: number
  icon: string
  key: string
  sortNo: number
  cache: number
  target: string
  projectCode: string
  remark: string
  pathType: string
}

// 权限树（角色绑定用）
export interface SysPermissionNodeVO {
  id: number
  parentId: number
  name: string
  sortNo: number
  permission: string
  level: number
  remark: string
  createTime: string
  children?: SysPermissionNodeVO[]
}

// 通用响应
export interface ApiResult<T> {
  status: number
  message: string
  data: T
}

export interface PageResult<T> {
  records: T[]
  total: number
  current: number
  size: number
}
```

**Step 4: Create `src/models/dict.ts`**

```ts
import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import type { DeptNode, SimplePostVO, ProjectTypeGroup, SysMenuNodeVO, SysPermissionNodeVO, SimpleRoleVO } from "@/utils/types"

const BASE = "/api/system/adminApi"

export interface DictState {
  deptTree: DeptNode[] | null
  postList: SimplePostVO[] | null
  projectGroups: ProjectTypeGroup[] | null
  menuTree: SysMenuNodeVO[] | null
  permTree: SysPermissionNodeVO[] | null
  simpleRoles: SimpleRoleVO[] | null
  loading: { [key: string]: boolean }
}

const initialState: DictState = {
  deptTree: null,
  postList: null,
  projectGroups: null,
  menuTree: null,
  permTree: null,
  simpleRoles: null,
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
        if (res?.status === 200) {
          yield put({ type: "saveDict", payload: { key: "deptTree", value: res.data } })
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
        if (res?.status === 200) {
          yield put({ type: "saveDict", payload: { key: "postList", value: res.data?.records || [] } })
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
        if (res?.status === 200) {
          yield put({ type: "saveDict", payload: { key: "projectGroups", value: res.data } })
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
        if (res?.status === 200) {
          yield put({ type: "saveDict", payload: { key: "menuTree", value: res.data } })
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
        if (res?.status === 200) {
          yield put({ type: "saveDict", payload: { key: "permTree", value: res.data } })
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
        if (res?.status === 200) {
          yield put({ type: "saveDict", payload: { key: "simpleRoles", value: res.data } })
        }
      } finally {
        yield put({ type: "setLoading", payload: { key: "simpleRoles", value: false } })
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
```

**Step 5: Modify `src/pages/login/model.ts` — logout 时清 dict**

在 `handleLoginOut` effect 中，`yield put({ type: "app/reset" })` 之后追加：

```ts
yield put({ type: "dict/clearAll" })
```

并确保 `src/models/app.ts` 不会干扰（dva model 隔离，互不影响）。

**Step 6: 注册 `dict` model**

`src/models/dict.ts` 在 UMI 约定式 model 加载下会自动注册（`src/models/` 下所有 `*.ts` 文件会被 UMI 作为 model 加载）。确认 `.umirc.ts` 中 dva 配置为 `lazyLoad: false` 或未开启 lazy。不做修改。

**Step 7: 验证**

```bash
cd D:/project/benchmark-project/baseline-main-front
npx tsc --noEmit 2>&1 | head -30
# 预期：无类型错误
```

---

### Task 2: 共享组件 — PermButton + usePerm + Router 更新

**Files:**
- Create: `src/pages/common/PermButton.tsx`
- Create: `src/pages/common/usePerm.ts`
- Modify: `src/routers/routers.ts`

**Step 1: Create `src/pages/common/PermButton.tsx`**

```tsx
import React from "react"
import { Button } from "antd"
import type { ButtonProps } from "antd"
import { useSelector } from "umi"

type PermButtonProps = ButtonProps & { perm?: string }

const PermButton: React.FC<PermButtonProps> = ({ perm, children, ...rest }) => {
  const permissions = useSelector((s: any) => s.app.permissions as string[])
  if (perm && !permissions.includes(perm)) return null
  return <Button {...rest}>{children}</Button>
}

export default PermButton
```

**Step 2: Create `src/pages/common/usePerm.ts`**

```ts
import { useSelector } from "umi"

export const usePerm = () => {
  const permissions = useSelector((s: any) => s.app.permissions as string[])
  return (code: string) => permissions.includes(code)
}
```

**Step 3: Modify `src/routers/routers.ts`**

把 `/system/user`, `/system/role`, `/system/menu` 从 `common/Placeholder` 改真实路由：

```ts
{ path: "/system/user", component: "system/user/index" },
{ path: "/system/role", component: "system/role/index" },
{ path: "/system/menu", component: "system/menu/index" },
```

其余行 `/system/dept`, `/system/dict`, `/system/tenant`, `/profile` 维持 Placeholder。

---

### Task 3: 用户管理 — model

**Files:**
- Create: `src/models/user.ts`

```ts
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
    reset: (() => ({ list: [], total: 0, filter: { ...initialFilter }, detail: null, loading: { list: false, save: false, detail: false, remove: false, resetPwd: false } })) as Reducer<any>,
  },
}

export default UserModel
```

---

### Task 4: 用户管理 — 列表页

**Files:**
- Create: `src/pages/system/user/index.tsx`
- Create: `src/pages/system/user/UserDrawer.tsx`
- Create: `src/pages/system/user/ResetPwdModal.tsx`

**Step 1: Create `src/pages/system/user/index.tsx`**

```tsx
import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Row, Col, Modal, message } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import UserDrawer from "./UserDrawer"
import ResetPwdModal from "./ResetPwdModal"
import type { SysUserPageVO } from "@/utils/types"

const UserPage: React.FC = () => {
  const dispatch = useDispatch()
  const { list, total, filter, loading } = useSelector((s: any) => s.user)
  const [drawerOpen, setDrawerOpen] = useState(false)
  const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
  const [resetPwdUserId, setResetPwdUserId] = useState<number | undefined>(undefined)
  const [selectedRowKeys, setSelectedRowKeys] = useState<number[]>([])
  const [form] = Form.useForm()

  useEffect(() => { dispatch({ type: "user/fetchPage" }) }, [])

  const handleSearch = () => {
    const vals = form.getFieldsValue()
    dispatch({ type: "user/saveFilter", payload: vals })
    dispatch({ type: "user/fetchPage" })
  }

  const handleReset = () => {
    form.resetFields()
    dispatch({ type: "user/saveFilter", payload: {} })
    dispatch({ type: "user/fetchPage" })
  }

  const handleAdd = () => {
    setDrawerId(undefined)
    setDrawerOpen(true)
  }

  const handleEdit = (id: number) => {
    setDrawerId(id)
    setDrawerOpen(true)
  }

  const handleRemove = (id: number) => {
    Modal.confirm({
      title: "确认删除",
      content: "确定要删除该用户吗？",
      onOk: () => {
        dispatch({ type: "user/remove", payload: [id] })
      },
    })
  }

  const handleBatchRemove = () => {
    if (selectedRowKeys.length === 0) return
    Modal.confirm({
      title: "确认批量删除",
      content: `确定要删除选中的 ${selectedRowKeys.length} 条记录吗？`,
      onOk: () => {
        dispatch({ type: "user/remove", payload: selectedRowKeys, callback: () => setSelectedRowKeys([]) })
      },
    })
  }

  const columns = [
    { title: "账号", dataIndex: "account", key: "account" },
    { title: "姓名", dataIndex: "name", key: "name" },
    { title: "手机号", dataIndex: "phone", key: "phone" },
    { title: "部门", dataIndex: "deptName", key: "deptName" },
    {
      title: "状态", dataIndex: "status", key: "status",
      render: (v: number) => v === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag>,
    },
    { title: "创建时间", dataIndex: "createTime", key: "createTime" },
    {
      title: "操作", key: "action", width: 200,
      render: (_: any, record: SysUserPageVO) => (
        <Space size="small">
          <PermButton perm={PERMS.user.edit} type="link" size="small" onClick={() => handleEdit(record.id)}>编辑</PermButton>
          <PermButton perm={PERMS.user.reset} type="link" size="small" onClick={() => setResetPwdUserId(record.id)}>重置密码</PermButton>
          <PermButton perm={PERMS.user.del} type="link" size="small" danger onClick={() => handleRemove(record.id)}>删除</PermButton>
        </Space>
      ),
    },
  ]

  return (
    <>
      <Card size="small" style={{ marginBottom: 8 }}>
        <Form form={form} layout="inline" style={{ flexWrap: "wrap", gap: 8 }}>
          <Form.Item name="name"><Input placeholder="姓名" allowClear /></Form.Item>
          <Form.Item name="phone"><Input placeholder="手机号" allowClear /></Form.Item>
          <Form.Item name="status">
            <Select placeholder="状态" allowClear style={{ width: 100 }}>
              <Select.Option value={1}>启用</Select.Option>
              <Select.Option value={0}>禁用</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Space>
              <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>搜索</Button>
              <Button icon={<ReloadOutlined />} onClick={handleReset}>重置</Button>
            </Space>
          </Form.Item>
        </Form>
      </Card>
      <Card size="small">
        <div style={{ marginBottom: 12 }}>
          <Space>
            <PermButton perm={PERMS.user.add} type="primary" icon={<PlusOutlined />} onClick={handleAdd}>新增用户</PermButton>
            <PermButton perm={PERMS.user.del} disabled={selectedRowKeys.length === 0} onClick={handleBatchRemove}>批量删除</PermButton>
          </Space>
        </div>
        <Table
          rowKey="id"
          dataSource={list}
          columns={columns}
          loading={loading.list}
          rowSelection={{ selectedRowKeys, onChange: (keys: any) => setSelectedRowKeys(keys) }}
          pagination={{
            current: filter.current, pageSize: filter.size, total,
            onChange: (page, size) => {
              dispatch({ type: "user/pageChange", payload: { current: page, size } })
              dispatch({ type: "user/fetchPage" })
            },
            showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条`,
          }}
        />
      </Card>
      <UserDrawer
        open={drawerOpen}
        id={drawerId}
        onClose={() => { setDrawerOpen(false); setDrawerId(undefined) }}
      />
      <ResetPwdModal
        userId={resetPwdUserId}
        onClose={() => setResetPwdUserId(undefined)}
      />
    </>
  )
}

export default UserPage
```

**Step 2: Create `src/pages/system/user/UserDrawer.tsx`**

```tsx
import React, { useEffect } from "react"
import { Drawer, Form, Input, Select, TreeSelect, Switch, Radio, Button, Space, Spin, Divider, message } from "antd"
import { useDispatch, useSelector } from "umi"

interface Props {
  open: boolean
  id?: number
  onClose: () => void
}

const UserDrawer: React.FC<Props> = ({ open, id, onClose }) => {
  const dispatch = useDispatch()
  const [form] = Form.useForm()
  const { detail, loading } = useSelector((s: any) => s.user)
  const dict = useSelector((s: any) => s.dict)

  const isEdit = !!id
  const saving = loading.save

  useEffect(() => {
    if (!open) { form.resetFields(); return }
    dispatch({ type: "dict/loadDeptTree" })
    dispatch({ type: "dict/loadPostList" })
    dispatch({ type: "dict/loadSimpleRoles" })
    if (id) {
      dispatch({ type: "user/fetchDetail", payload: id })
    }
  }, [open, id])

  useEffect(() => {
    if (isEdit && detail) {
      form.setFieldsValue({
        ...detail,
        password: undefined,
      })
    } else if (!isEdit) {
      form.resetFields()
    }
  }, [detail, isEdit])

  const handleSubmit = () => {
    form.validateFields().then((values) => {
      const payload = isEdit ? { ...values, id } : values
      dispatch({
        type: "user/saveOrUpdate",
        payload,
        callback: (ok: boolean) => { if (ok) onClose() },
      })
    })
  }

  const buildTreeData = (nodes: any[]): any[] =>
    nodes?.map((n: any) => ({ title: n.name, key: n.id, value: n.id, children: buildTreeData(n.children || []) })) || []

  return (
    <Drawer
      title={isEdit ? "编辑用户" : "新增用户"}
      width={800}
      open={open}
      onClose={onClose}
      maskClosable={!saving}
      closable={!saving}
      footer={
        <Space>
          <Button onClick={onClose} disabled={saving}>取消</Button>
          <Button type="primary" loading={saving} onClick={handleSubmit}>保存</Button>
        </Space>
      }
    >
      <Spin spinning={isEdit && loading.detail}>
        <Form form={form} layout="vertical" initialValues={{ status: 1, sex: 1 }}>
          <Divider orientation="left" plain>基本信息</Divider>
          <Form.Item name="account" label="账号" rules={[{ required: true, message: "请输入账号" }, { pattern: /^[a-zA-Z0-9_]+$/, message: "仅允许字母数字下划线" }]}>
            <Input disabled={isEdit} placeholder="登录账号" />
          </Form.Item>
          <Form.Item name="name" label="姓名" rules={[{ required: true, message: "请输入姓名" }]}>
            <Input />
          </Form.Item>
          {!isEdit && (
            <Form.Item name="password" label="密码" rules={[{ required: true, message: "请输入密码" }, { min: 6, message: "密码至少6位" }]}>
              <Input.Password />
            </Form.Item>
          )}
          <Form.Item name="phone" label="手机号" rules={[{ pattern: /^1[3-9]\d{9}$/, message: "手机号格式不正确" }]}>
            <Input />
          </Form.Item>
          <Form.Item name="email" label="邮箱" rules={[{ type: "email", message: "邮箱格式不正确" }]}>
            <Input />
          </Form.Item>
          <Form.Item name="sex" label="性别">
            <Radio.Group>
              <Radio value={1}>男</Radio>
              <Radio value={2}>女</Radio>
              <Radio value={0}>未知</Radio>
            </Radio.Group>
          </Form.Item>
          <Divider orientation="left" plain>组织信息</Divider>
          <Form.Item name="deptId" label="部门">
            <TreeSelect
              treeData={buildTreeData(dict.deptTree)}
              placeholder="请选择部门"
              allowClear
              treeDefaultExpandAll
            />
          </Form.Item>
          <Divider orientation="left" plain>角色与岗位</Divider>
          <Form.Item name="roleIds" label="角色">
            <Select mode="multiple" placeholder="请选择角色" allowClear
              options={dict.simpleRoles?.map((r: any) => ({ label: r.name, value: r.id }))} />
          </Form.Item>
          <Form.Item name="postIds" label="岗位">
            <Select mode="multiple" placeholder="请选择岗位" allowClear
              options={dict.postList?.map((p: any) => ({ label: p.name, value: p.id }))} />
          </Form.Item>
          <Divider orientation="left" plain>其他</Divider>
          <Form.Item name="status" label="状态" valuePropName="checked" getValueFromEvent={(v: boolean) => v ? 1 : 0} getValueProps={(v: number) => ({ checked: v === 1 })}>
            <Switch checkedChildren="启用" unCheckedChildren="禁用" />
          </Form.Item>
          <Form.Item name="remark" label="备注">
            <Input.TextArea rows={3} />
          </Form.Item>
        </Form>
      </Spin>
    </Drawer>
  )
}

export default UserDrawer
```

**Step 3: Create `src/pages/system/user/ResetPwdModal.tsx`**

```tsx
import React from "react"
import { Modal, Form, Input, message } from "antd"
import { useDispatch } from "umi"

interface Props {
  userId?: number
  onClose: () => void
}

const ResetPwdModal: React.FC<Props> = ({ userId, onClose }) => {
  const dispatch = useDispatch()
  const [form] = Form.useForm()

  const handleOk = () => {
    form.validateFields().then((values) => {
      if (values.newPw !== values.confirmPw) {
        message.error("两次密码输入不一致")
        return
      }
      dispatch({
        type: "user/resetPwd",
        payload: { userId, newPw: values.newPw },
        callback: (ok: boolean) => { if (ok) { form.resetFields(); onClose() } },
      })
    })
  }

  return (
    <Modal
      title="重置密码"
      open={!!userId}
      onOk={handleOk}
      onCancel={() => { form.resetFields(); onClose() }}
      destroyOnClose
    >
      <Form form={form} layout="vertical">
        <Form.Item name="newPw" label="新密码" rules={[{ required: true, message: "请输入新密码" }, { min: 6, message: "至少6位" }]}>
          <Input.Password />
        </Form.Item>
        <Form.Item name="confirmPw" label="确认密码" rules={[{ required: true, message: "请确认新密码" }]}>
          <Input.Password />
        </Form.Item>
      </Form>
    </Modal>
  )
}

export default ResetPwdModal
```

---

### Task 5: 角色管理 — model

**Files:**
- Create: `src/models/role.ts`

```ts
import { type Reducer } from "umi"
import Servpost from "@/utils/Servpost"
import { IAPI } from "@/utils/ServpostInterface"
import { message as Message } from "antd"
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
    *fetchPage(_: any, { put, select }: any): any {
      yield put({ type: "setLoading", payload: { key: "list", value: true } })
      try {
        const filter = yield select((s: any) => s.role.filter)
        const res: any = yield Servpost.requestRace<IAPI>({ url: `${BASE}/page`, data: filter, methods: "post" })
        if (res?.status === 200) {
          const page = res.data as PageResult<SysRolePageVO>
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
        if (res?.status === 200) {
          yield put({ type: "saveDetail", payload: res.data })
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
      ...state, loading: { ...state.loading, [action.payload.key]: action.payload.value },
    })) as Reducer<any>,
    reset: (() => ({
      list: [], total: 0, filter: { ...initialFilter }, detail: null,
      loading: { list: false, save: false, detail: false, remove: false },
    })) as Reducer<any>,
  },
}

export default RoleModel
```

---

### Task 6: 角色管理 — 列表页 + Drawer

**Files:**
- Create: `src/pages/system/role/index.tsx`
- Create: `src/pages/system/role/RoleDrawer.tsx`
- Create: `src/pages/system/role/DataScopeRadio.tsx`

**Step 1: Create `src/pages/system/role/index.tsx`**

```tsx
import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import { DATA_SCOPE_TEXT } from "@/constants/dataScope"
import RoleDrawer from "./RoleDrawer"
import type { SysRolePageVO } from "@/utils/types"

const RolePage: React.FC = () => {
  const dispatch = useDispatch()
  const { list, total, filter, loading } = useSelector((s: any) => s.role)
  const [drawerOpen, setDrawerOpen] = useState(false)
  const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
  const [selectedRowKeys, setSelectedRowKeys] = useState<number[]>([])
  const [form] = Form.useForm()

  useEffect(() => { dispatch({ type: "role/fetchPage" }) }, [])

  const handleSearch = () => {
    const vals = form.getFieldsValue()
    dispatch({ type: "role/saveFilter", payload: vals })
    dispatch({ type: "role/fetchPage" })
  }

  const handleReset = () => {
    form.resetFields()
    dispatch({ type: "role/saveFilter", payload: {} })
    dispatch({ type: "role/fetchPage" })
  }

  const handleBatchRemove = () => {
    if (selectedRowKeys.length === 0) return
    Modal.confirm({
      title: "确认批量删除",
      content: `确定要删除选中的 ${selectedRowKeys.length} 条记录吗？`,
      onOk: () => dispatch({ type: "role/remove", payload: selectedRowKeys, callback: () => setSelectedRowKeys([]) }),
    })
  }

  const columns = [
    { title: "角色名称", dataIndex: "name", key: "name" },
    { title: "角色标识", dataIndex: "key", key: "key" },
    {
      title: "数据范围", dataIndex: "dataScope", key: "dataScope",
      render: (v: number) => DATA_SCOPE_TEXT[v as keyof typeof DATA_SCOPE_TEXT] || v,
    },
    { title: "状态", dataIndex: "status", key: "status", render: (v: number) => v === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag> },
    { title: "创建时间", dataIndex: "createTime", key: "createTime" },
    {
      title: "操作", key: "action", width: 160,
      render: (_: any, record: SysRolePageVO) => (
        <Space size="small">
          <PermButton perm={PERMS.role.edit} type="link" size="small" onClick={() => { setDrawerId(record.id); setDrawerOpen(true) }}>编辑</PermButton>
          <PermButton perm={PERMS.role.del} type="link" size="small" danger onClick={() => {
            Modal.confirm({ title: "确认删除", content: `确定删除角色「${record.name}」？`, onOk: () => dispatch({ type: "role/remove", payload: [record.id] }) })
          }}>删除</PermButton>
        </Space>
      ),
    },
  ]

  return (
    <>
      <Card size="small" style={{ marginBottom: 8 }}>
        <Form form={form} layout="inline" style={{ flexWrap: "wrap", gap: 8 }}>
          <Form.Item name="name"><Input placeholder="角色名称" allowClear /></Form.Item>
          <Form.Item name="key"><Input placeholder="角色标识" allowClear /></Form.Item>
          <Form.Item name="status">
            <Select placeholder="状态" allowClear style={{ width: 100 }}>
              <Select.Option value={1}>启用</Select.Option>
              <Select.Option value={0}>禁用</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Space>
              <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>搜索</Button>
              <Button icon={<ReloadOutlined />} onClick={handleReset}>重置</Button>
            </Space>
          </Form.Item>
        </Form>
      </Card>
      <Card size="small">
        <div style={{ marginBottom: 12 }}>
          <Space>
            <PermButton perm={PERMS.role.add} type="primary" icon={<PlusOutlined />} onClick={() => { setDrawerId(undefined); setDrawerOpen(true) }}>新增角色</PermButton>
            <PermButton perm={PERMS.role.del} disabled={selectedRowKeys.length === 0} onClick={handleBatchRemove}>批量删除</PermButton>
          </Space>
        </div>
        <Table
          rowKey="id"
          dataSource={list}
          columns={columns}
          loading={loading.list}
          rowSelection={{ selectedRowKeys, onChange: (keys: any) => setSelectedRowKeys(keys) }}
          pagination={{
            current: filter.current, pageSize: filter.size, total,
            onChange: (p, s) => { dispatch({ type: "role/pageChange", payload: { current: p, size: s } }); dispatch({ type: "role/fetchPage" }) },
            showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条`,
          }}
        />
      </Card>
      <RoleDrawer open={drawerOpen} id={drawerId} onClose={() => { setDrawerOpen(false); setDrawerId(undefined) }} />
    </>
  )
}

export default RolePage
```

**Step 2: Create `src/pages/system/role/DataScopeRadio.tsx`**

```tsx
import React from "react"
import { Radio } from "antd"
import { DataScope, DATA_SCOPE_TEXT } from "@/constants/dataScope"

interface Props {
  value?: number
  onChange?: (v: number) => void
}

const DataScopeRadio: React.FC<Props> = ({ value, onChange }) => (
  <Radio.Group value={value} onChange={(e) => onChange?.(e.target.value)}>
    {Object.values(DataScope).filter(v => typeof v === "number").map((v) => (
      <Radio key={v} value={v}>{DATA_SCOPE_TEXT[v as keyof typeof DATA_SCOPE_TEXT]}</Radio>
    ))}
  </Radio.Group>
)

export default DataScopeRadio
```

**Step 3: Create `src/pages/system/role/RoleDrawer.tsx`**

```tsx
import React, { useEffect, useState } from "react"
import { Drawer, Form, Input, Select, Button, Space, Spin, Tabs, Tree, Checkbox, message } from "antd"
import { useDispatch, useSelector } from "umi"
import DataScopeRadio from "./DataScopeRadio"
import { DataScope } from "@/constants/dataScope"

interface Props {
  open: boolean
  id?: number
  onClose: () => void
}

const RoleDrawer: React.FC<Props> = ({ open, id, onClose }) => {
  const dispatch = useDispatch()
  const [form] = Form.useForm()
  const { detail, loading } = useSelector((s: any) => s.role)
  const dict = useSelector((s: any) => s.dict)
  const [dataScope, setDataScope] = useState(DataScope.ALL)
  const [checkedMenuKeys, setCheckedMenuKeys] = useState<number[]>([])
  const [checkedPermKeys, setCheckedPermKeys] = useState<number[]>([])
  const [checkedDeptKeys, setCheckedDeptKeys] = useState<number[]>([])
  const [selectedProjects, setSelectedProjects] = useState<string[]>([])

  const isEdit = !!id
  const saving = loading.save

  useEffect(() => {
    if (!open) { form.resetFields(); return }
    dispatch({ type: "dict/loadMenuTree" })
    dispatch({ type: "dict/loadPermTree" })
    dispatch({ type: "dict/loadDeptTree" })
    dispatch({ type: "dict/loadProjectGroups" })
    if (id) dispatch({ type: "role/fetchDetail", payload: id })
  }, [open, id])

  useEffect(() => {
    if (isEdit && detail) {
      form.setFieldsValue({
        name: detail.name,
        key: detail.key,
        status: detail.status,
        dataScope: detail.dataScope,
        remark: detail.remark,
      })
      setDataScope(detail.dataScope)
      setCheckedMenuKeys(detail.menuIds || [])
      setCheckedPermKeys(detail.permissionIds || [])
      setCheckedDeptKeys(detail.deptIds || [])
      setSelectedProjects(detail.projectCodes || [])
    } else if (!isEdit) {
      form.resetFields()
      setDataScope(DataScope.ALL)
      setCheckedMenuKeys([])
      setCheckedPermKeys([])
      setCheckedDeptKeys([])
      setSelectedProjects([])
    }
  }, [detail, isEdit])

  const handleSubmit = () => {
    form.validateFields().then((values) => {
      const payload = {
        ...values,
        id: isEdit ? id : undefined,
        menuIds: checkedMenuKeys,
        permissionIds: checkedPermKeys,
        deptIds: dataScope === DataScope.CUSTOM ? checkedDeptKeys : [],
        projectCodes: selectedProjects,
      }
      dispatch({ type: "role/saveOrUpdate", payload, callback: (ok: boolean) => { if (ok) onClose() } })
    })
  }

  const buildTreeData = (nodes: any[]): any[] =>
    nodes?.map((n: any) => ({ title: n.name, key: n.id, children: buildTreeData(n.children || []) })) || []

  return (
    <Drawer
      title={isEdit ? "编辑角色" : "新增角色"}
      width={960}
      open={open}
      onClose={onClose}
      maskClosable={!saving}
      closable={!saving}
      footer={
        <Space>
          <Button onClick={onClose} disabled={saving}>取消</Button>
          <Button type="primary" loading={saving} onClick={handleSubmit}>保存</Button>
        </Space>
      }
    >
      <Spin spinning={isEdit && loading.detail}>
        <Form form={form} layout="vertical" initialValues={{ status: 1, dataScope: DataScope.ALL }}>
          <Form.Item name="name" label="角色名称" rules={[{ required: true, message: "请输入角色名称" }]}>
            <Input />
          </Form.Item>
          <Form.Item name="key" label="角色标识" rules={[{ required: true, message: "请输入角色标识" }, { pattern: /^[a-zA-Z_][a-zA-Z0-9_]*$/, message: "仅允许字母数字下划线" }]}>
            <Input />
          </Form.Item>
          <Form.Item name="status" label="状态">
            <Select>
              <Select.Option value={1}>启用</Select.Option>
              <Select.Option value={0}>禁用</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="dataScope" label="数据范围" rules={[{ required: true, message: "请选择数据范围" }]}>
            <DataScopeRadio />
          </Form.Item>
          <Form.Item name="remark" label="备注">
            <Input.TextArea rows={2} />
          </Form.Item>
        </Form>

        <Tabs items={[
          {
            key: "menu", label: "菜单权限",
            children: <Tree checkable treeData={buildTreeData(dict.menuTree)} checkedKeys={checkedMenuKeys} onCheck={(keys: any) => setCheckedMenuKeys(keys)} defaultExpandAll />,
          },
          {
            key: "perm", label: "按钮权限",
            children: <Tree checkable treeData={buildTreeData(dict.permTree)} checkedKeys={checkedPermKeys} onCheck={(keys: any) => setCheckedPermKeys(keys)} defaultExpandAll />,
          },
          ...(dataScope === DataScope.CUSTOM ? [{
            key: "dept", label: "数据部门",
            children: <Tree checkable treeData={buildTreeData(dict.deptTree)} checkedKeys={checkedDeptKeys} onCheck={(keys: any) => setCheckedDeptKeys(keys)} defaultExpandAll />,
          }] : []),
          {
            key: "project", label: "项目权限",
            children: dict.projectGroups?.map((g: any) => (
              <div key={g.type} style={{ marginBottom: 12 }}>
                <div style={{ fontWeight: 500, marginBottom: 4 }}>{g.type}</div>
                <Checkbox.Group options={g.projects?.map((p: any) => ({ label: p.name, value: p.code }))} value={selectedProjects} onChange={(vals: any) => setSelectedProjects(vals)} />
              </div>
            )),
          },
        ]} />
      </Spin>
    </Drawer>
  )
}

export default RoleDrawer
```

---

### Task 7: 菜单管理 — model

**Files:**
- Create: `src/models/menu.ts`

```ts
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
      ...state, loading: { ...state.loading, [action.payload.key]: action.payload.value },
    })) as Reducer<any>,
    reset: (() => ({ tree: [], filter: {}, detail: null, loading: { tree: false, save: false, detail: false, remove: false } })) as Reducer<any>,
  },
}

export default MenuModel
```

---

### Task 8: 菜单管理 — 列表页 + Drawer

**Files:**
- Create: `src/pages/system/menu/index.tsx`
- Create: `src/pages/system/menu/MenuDrawer.tsx`

**Step 1: Create `src/pages/system/menu/index.tsx`**

```tsx
import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal, Tooltip } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined, MinusCircleOutlined, FolderOutlined, FileOutlined, CodeOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import MenuDrawer from "./MenuDrawer"
import type { SysMenuNodeVO } from "@/utils/types"

const typeIcon: Record<string, React.ReactNode> = {
  M: <FolderOutlined style={{ color: "#faad14" }} />,
  C: <FileOutlined style={{ color: "#1890ff" }} />,
  F: <CodeOutlined style={{ color: "#52c41a" }} />,
}

const typeText: Record<string, string> = { M: "目录", C: "页面", F: "按钮" }

const flattenTree = (nodes: SysMenuNodeVO[]): SysMenuNodeVO[] => {
  const out: SysMenuNodeVO[] = []
  const walk = (list: SysMenuNodeVO[]) => {
    for (const n of list) { out.push(n); if (n.children) walk(n.children) }
  }
  walk(nodes)
  return out
}

const MenuPage: React.FC = () => {
  const dispatch = useDispatch()
  const { tree, filter, loading } = useSelector((s: any) => s.menu)
  const [drawerOpen, setDrawerOpen] = useState(false)
  const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
  const [parentId, setParentId] = useState<number | undefined>(undefined)
  const [form] = Form.useForm()

  useEffect(() => { dispatch({ type: "menu/fetchTree" }) }, [])

  const handleSearch = () => {
    const vals = form.getFieldsValue()
    dispatch({ type: "menu/saveFilter", payload: vals })
    dispatch({ type: "menu/fetchTree" })
  }

  const handleReset = () => {
    form.resetFields()
    dispatch({ type: "menu/saveFilter", payload: {} })
    dispatch({ type: "menu/fetchTree" })
  }

  const handleRemove = (record: SysMenuNodeVO) => {
    Modal.confirm({
      title: "确认删除",
      content: `确定要删除「${record.name}」吗？若有子菜单将一并删除。`,
      onOk: () => { dispatch({ type: "menu/remove", payload: [record.id] }) },
    })
  }

  const columns = [
    {
      title: "菜单名称", dataIndex: "name", key: "name",
      render: (_: string, record: SysMenuNodeVO) => (
        <Space>
          {typeIcon[record.type] || null}
          <span>{record.name}</span>
          {record.type === "F" && record.key && (
            <Tag style={{ fontSize: 11 }}>{record.key}</Tag>
          )}
        </Space>
      ),
    },
    { title: "类型", dataIndex: "type", key: "type", width: 70, render: (v: string) => typeText[v] || v },
    { title: "路由", dataIndex: "path", key: "path", width: 200, ellipsis: true },
    { title: "排序", dataIndex: "sortNo", key: "sortNo", width: 60 },
    { title: "可见", dataIndex: "visible", key: "visible", width: 60, render: (v: number) => v === 1 ? <Tag color="blue">是</Tag> : <Tag>否</Tag> },
    { title: "状态", dataIndex: "status", key: "status", width: 60, render: (v: number) => v === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag> },
    { title: "创建时间", dataIndex: "createTime", key: "createTime", width: 160 },
    {
      title: "操作", key: "action", width: 200,
      render: (_: any, record: SysMenuNodeVO) => (
        <Space size="small">
          <PermButton perm={PERMS.menu.add} type="link" size="small"
            onClick={() => { setParentId(record.id); setDrawerId(undefined); setDrawerOpen(true) }}>
            +子菜单
          </PermButton>
          <PermButton perm={PERMS.menu.edit} type="link" size="small"
            onClick={() => { setParentId(undefined); setDrawerId(record.id); setDrawerOpen(true) }}>
            编辑
          </PermButton>
          <PermButton perm={PERMS.menu.del} type="link" size="small" danger onClick={() => handleRemove(record)}>
            删除
          </PermButton>
        </Space>
      ),
    },
  ]

  return (
    <>
      <Card size="small" style={{ marginBottom: 8 }}>
        <Form form={form} layout="inline" style={{ flexWrap: "wrap", gap: 8 }}>
          <Form.Item name="name"><Input placeholder="菜单名称" allowClear /></Form.Item>
          <Form.Item name="projectCode">
            <Select placeholder="项目码" allowClear style={{ width: 150 }}>
              <Select.Option value="BASELINE">BASELINE</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Space>
              <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>搜索</Button>
              <Button icon={<ReloadOutlined />} onClick={handleReset}>重置</Button>
            </Space>
          </Form.Item>
        </Form>
      </Card>
      <Card size="small">
        <div style={{ marginBottom: 12 }}>
          <PermButton perm={PERMS.menu.add} type="primary" icon={<PlusOutlined />}
            onClick={() => { setParentId(0); setDrawerId(undefined); setDrawerOpen(true) }}>
            新增根菜单
          </PermButton>
        </div>
        <Table
          rowKey="id"
          dataSource={tree}
          columns={columns}
          loading={loading.tree}
          pagination={false}
          defaultExpandAllRows
        />
      </Card>
      <MenuDrawer
        open={drawerOpen}
        id={drawerId}
        parentId={parentId}
        onClose={() => { setDrawerOpen(false); setDrawerId(undefined); setParentId(undefined) }}
      />
    </>
  )
}

export default MenuPage
```

**Step 2: Create `src/pages/system/menu/MenuDrawer.tsx`**

```tsx
import React, { useEffect } from "react"
import { Drawer, Form, Input, TreeSelect, Radio, InputNumber, Switch, Button, Space, Spin, Select } from "antd"
import { useDispatch, useSelector } from "umi"

interface Props {
  open: boolean
  id?: number
  parentId?: number
  onClose: () => void
}

const MenuDrawer: React.FC<Props> = ({ open, id, parentId, onClose }) => {
  const dispatch = useDispatch()
  const [form] = Form.useForm()
  const { detail, loading } = useSelector((s: any) => s.menu)
  const dict = useSelector((s: any) => s.dict)
  const menuType = Form.useWatch("type", form)

  const isEdit = !!id
  const saving = loading.save

  useEffect(() => {
    if (!open) { form.resetFields(); return }
    dispatch({ type: "dict/loadMenuTree" })
    if (id) { dispatch({ type: "menu/fetchDetail", payload: id }) }
  }, [open, id])

  useEffect(() => {
    if (isEdit && detail) {
      form.setFieldsValue(detail)
    } else if (!isEdit) {
      form.resetFields()
      form.setFieldsValue({ type: "M", status: 1, visible: 1, cache: 1, sortNo: 0, parentId: parentId || 0 })
    }
  }, [detail, isEdit, parentId])

  const handleSubmit = () => {
    form.validateFields().then((values) => {
      // 按 type 过滤无效字段
      const payload: any = { ...values, id: isEdit ? id : undefined }
      if (values.type === "M") { payload.path = ""; payload.component = ""; payload.key = "" }
      if (values.type === "F") { payload.path = ""; payload.component = "" }
      dispatch({ type: "menu/saveOrUpdate", payload, callback: (ok: boolean) => { if (ok) onClose() } })
    })
  }

  const buildTreeData = (nodes: any[]): any[] =>
    [{ title: "根目录", key: 0, value: 0 }].concat(
      nodes?.map((n: any) => ({ title: n.name, key: n.id, value: n.id, disabled: n.id === id, children: buildTreeData(n.children || []) })) || []
    )

  return (
    <Drawer
      title={isEdit ? "编辑菜单" : "新增菜单"}
      width={800}
      open={open}
      onClose={onClose}
      maskClosable={!saving}
      closable={!saving}
      footer={
        <Space>
          <Button onClick={onClose} disabled={saving}>取消</Button>
          <Button type="primary" loading={saving} onClick={handleSubmit}>保存</Button>
        </Space>
      }
    >
      <Spin spinning={isEdit && loading.detail}>
        <Form form={form} layout="vertical" initialValues={{ type: "M", status: 1, visible: 1, cache: 1, sortNo: 0 }}>
          <Form.Item name="type" label="菜单类型" rules={[{ required: true }]}>
            <Radio.Group>
              <Radio value="M">目录 (M)</Radio>
              <Radio value="C">页面 (C)</Radio>
              <Radio value="F">按钮 (F)</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item name="parentId" label="上级菜单" rules={[{ required: true }]}>
            <TreeSelect treeData={buildTreeData(dict.menuTree)} placeholder="请选择上级菜单" allowClear treeDefaultExpandAll />
          </Form.Item>
          <Form.Item name="name" label="菜单名称" rules={[{ required: true, message: "请输入菜单名称" }]}>
            <Input />
          </Form.Item>
          {(menuType === "C") && (
            <>
              <Form.Item name="path" label="路由地址" rules={[{ required: true, message: "请输入路由地址" }]}>
                <Input placeholder="/system/user" />
              </Form.Item>
              <Form.Item name="component" label="组件路径" rules={[{ required: true, message: "请输入组件路径" }]}>
                <Input placeholder="system/user/index" />
              </Form.Item>
            </>
          )}
          {(menuType === "F") && (
            <Form.Item name="key" label="权限标识" rules={[{ required: true, message: "请输入权限标识" }]}>
              <Input placeholder="system:user:add" />
            </Form.Item>
          )}
          {menuType !== "F" && (
            <Form.Item name="icon" label="图标">
              <Input placeholder="FolderOutlined" />
            </Form.Item>
          )}
          <Form.Item name="sortNo" label="排序号">
            <InputNumber style={{ width: "100%" }} />
          </Form.Item>
          <Form.Item name="status" label="状态">
            <Select>
              <Select.Option value={1}>启用</Select.Option>
              <Select.Option value={0}>禁用</Select.Option>
            </Select>
          </Form.Item>
          {menuType !== "F" && (
            <Form.Item name="visible" label="是否可见" getValueFromEvent={(v: boolean) => v ? 1 : 0} getValueProps={(v: number) => ({ checked: v === 1 })}>
              <Switch checkedChildren="可见" unCheckedChildren="隐藏" />
            </Form.Item>
          )}
          {menuType === "C" && (
            <Form.Item name="cache" label="是否缓存" getValueFromEvent={(v: boolean) => v ? 1 : 0} getValueProps={(v: number) => ({ checked: v === 1 })}>
              <Switch checkedChildren="缓存" unCheckedChildren="不缓存" />
            </Form.Item>
          )}
          <Form.Item name="projectCode" label="项目码">
            <Select allowClear>
              <Select.Option value="BASELINE">BASELINE</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="remark" label="备注">
            <Input.TextArea rows={2} />
          </Form.Item>
        </Form>
      </Spin>
    </Drawer>
  )
}

export default MenuDrawer
```

---

### Task 9: 全链路验证

**Step 1: TypeScript 编译检查**

```bash
cd D:/project/benchmark-project/baseline-main-front
npx tsc --noEmit 2>&1
```

预期：无类型错误。若有，修正。

**Step 2: 启动 dev server + 浏览器验证**

```bash
npm run dev
```

按验收清单逐条点验：

- [ ] 登录后侧边栏点击「用户管理」→ 列表加载正常，筛选栏可用
- [ ] 新增用户：打开 Drawer → 部门 TreeSelect 有数据 → 保存后列表刷新
- [ ] 编辑用户：点编辑 → Drawer 回填正确 → 保存成功
- [ ] 重置密码：弹窗输入新密码 → 成功回调关闭
- [ ] 批量删除：勾选多行 → 批量删除 → 确认后记录消失
- [ ] 角色管理：列表/筛选/分页正常
- [ ] 角色 Drawer：4个 Tab 菜单/按钮/部门(仅CUSTOM)/项目 → 勾选 → 保存后重新打开回显正确
- [ ] 角色 dataScope 切换 CUSTOM → 部门 Tab 出现；切其他 → 消失且清空
- [ ] 菜单管理：树形表格展开折叠正常
- [ ] 新增根菜单 → +子菜单 预填 parentId
- [ ] 菜单类型切换 M/C/F → 字段联动（路由/组件显隐、key 显示）
- [ ] 删除菜单 → 确认 → 成功，树刷新
- [ ] 无权限的按钮不展示（登录非 super_admin 账号验证）
- [ ] dict 缓存：二次打开同一 Drawer 不重复请求（看 Network tab）
- [ ] logout 后重新登录 → dict 重新拉取

**Step 3: 提交**

```bash
git add -A
git commit -m "feat: P2 RBAC 三张管理页（用户/角色/菜单）"
```