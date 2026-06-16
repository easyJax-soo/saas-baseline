# P2 — RBAC 三张管理页设计（用户 / 角色 / 菜单）

> 上承 P1 RBAC Foundation（登录链路 / sessionStore / Sidebar / UserMenu / app model），把 `/system/{user,role,menu}` 三张占位页落地为可用的管理界面。

## 目标

- 三张列表页 + Drawer 编辑：用户、角色、菜单；覆盖后端 DTO 全字段。
- 角色页内嵌「菜单 / 按钮 / 数据部门 / 项目」四类绑定，单次保存提交完整 `SysRoleSaveDTO`。
- 共享字典（部门树 / 岗位 / 项目码 / 菜单树 / 权限树 / 简单角色）按需拉取 + 内存缓存。
- 按钮级权限通过 `permissions[]` 隐藏不可用按钮，后端 `@SaAdminCheckPermission` 兜底。

不做：菜单反查角色、字段级权限、行级权限自定义、角色排序拖拽、批量导入。

## 后端 endpoint 清单

| 模块 | 端点 | 备注 |
|---|---|---|
| 用户 | `POST /api/system/adminApi/user/page` `/saveOrUpdate` `/detail` `/remove` `/resetPw` | saveOrUpdate 内联 roleIds、postIds |
| 角色 | `POST /api/system/adminApi/role/page` `/list` `/saveOrUpdate` `/detail` `/remove` | list 返回 SimpleRoleVO；detail 返回 SaveDTO 结构（用于 Drawer 回填） |
| 菜单 | `POST /api/system/adminApi/menu/tree` `/saveOrUpdate` `/detail` `/remove` | tree 不分页 |
| 权限 | `POST /api/sysPermission/tree` `/saveOrUpdate` `/detail` `/remove` | 角色 Drawer 用 tree |
| 字典 | `/dept/tree` `/post/list` `/project/list` | 实际路径在 plan 第一批 task 中核对 |

## 架构

### 目录

```
src/
├── models/
│   ├── user.ts        namespace=user
│   ├── role.ts        namespace=role
│   ├── menu.ts        namespace=menu
│   └── dict.ts        namespace=dict — 共享字典缓存
├── pages/
│   ├── system/
│   │   ├── user/{index.tsx, UserDrawer.tsx, ResetPwdModal.tsx}
│   │   ├── role/{index.tsx, RoleDrawer.tsx, DataScopeRadio.tsx}
│   │   └── menu/{index.tsx, MenuDrawer.tsx}
│   └── common/
│       ├── PermButton.tsx
│       └── usePerm.ts
├── utils/
│   └── dictApi.ts     字典端点常量
└── constants/
    ├── perms.ts       按钮权限码常量
    └── dataScope.ts   DataScope 枚举映射
```

### 数据流

```
列表加载：dispatch xxx/fetchPage → effect 调 /page → reducers.saveList
Drawer 打开：dispatch dict/loadDeptTree（命中缓存直返）→ 字段下拉
保存：dispatch xxx/saveOrUpdate → 成功后 dispatch xxx/fetchPage 刷列表
按钮权限：useSelector(s => s.app.permissions) → <PermButton perm="...">
```

## Models

### `user`

```ts
state: {
  list: PageSysUserVO[],
  total: number,
  filter: { name?, phone?, status?, deptId?, roleId?, beginTime?, endTime?, current: 1, size: 10 },
  detail: SysUserDetailVO | null,
  loading: { list: false, save: false, detail: false },
}

effects:
  *fetchPage      POST /user/page
  *fetchDetail    POST /user/detail
  *saveOrUpdate   POST /user/saveOrUpdate
  *remove         POST /user/remove   ← ids 数组，单/批量复用
  *resetPwd       POST /user/resetPw

reducers: saveList、saveFilter、saveDetail、saveLoading、reset
```

### `role`

```ts
state: {
  list: SysRolePageVO[], total,
  filter: { name?, key?, status?, dataScope?, current, size },
  detail: SysRoleSaveDTO | null,    // detail 接口直接返回 SaveDTO
  loading: { list, save, detail },
}

effects:
  *fetchPage         POST /role/page
  *fetchDetail       POST /role/detail
  *saveOrUpdate      POST /role/saveOrUpdate
  *remove            POST /role/remove
```

### `menu`

```ts
state: {
  tree: SysMenuNodeVO[],          // 不分页，整树
  filter: { name?, projectCode? },
  detail: SysMenuVO | null,
  loading: { tree, save, detail },
}

effects:
  *fetchTree         POST /menu/tree
  *fetchDetail       POST /menu/detail
  *saveOrUpdate      POST /menu/saveOrUpdate   // 成功后 put dict/invalidate menuTree
  *remove            POST /menu/remove         // 成功后 put dict/invalidate menuTree
```

### `dict`（共享字典 + 内存缓存）

```ts
state: {
  deptTree: DeptNode[] | null,
  postList: SimplePostVO[] | null,
  projectCodes: ProjectVO[] | null,
  menuTree: SysMenuNodeVO[] | null,
  permTree: SysPermissionNodeVO[] | null,
  simpleRoles: SimpleRoleVO[] | null,
  loading: { ... },
}

effects:（每个 effect 先查命中缓存，命中直返）
  *loadDeptTree
  *loadPostList
  *loadProjectCodes
  *loadMenuTree
  *loadPermTree
  *loadSimpleRoles
  *invalidate({key})    // 失效单条

reducers: saveDict({key,value})、clearAll
```

`logout` 副作用：在 `login/handleLoginOut` 里追加 `yield put({type:"dict/clearAll"})`。

## 页面交互

### `/system/user`

```
筛选栏：[姓名] [手机号] [状态▾] [部门▾] [搜索] [重置]
工具栏：[+新增] [批量删除]（无勾选 disabled）
表格 (rowSelection 多选)：
  ☑ 账号 姓名 手机 部门 状态 创建时间 [编辑][重置密码][删除]
分页：current/size 同 filter
```

**UserDrawer (800px)** — 字段分组：
- 基本：account（编辑禁用）、name、password（仅新增）、phone、email、sex(Radio)
- 组织：deptId(TreeSelect ← dict.deptTree)
- 角色岗位：roleIds(多选 ← simpleRoles)、postIds(多选 ← postList)
- 其它：avatar、status(Switch)、remark
- 校验：account 必填+pattern；phone pattern；email pattern；password 长度

**ResetPwdModal**：行操作触发，单字段 newPw + 确认密码。

### `/system/role`

```
筛选栏：[名称] [Key] [状态▾] [数据范围▾] [搜索] [重置]
工具栏：[+新增] [批量删除]
表格：☑ 名称 Key 数据范围_Text 状态 创建时间 [编辑][删除]
```

**RoleDrawer (960px)** — 顶部基本信息 + 4 Tab：
- 基本信息：名称*、Key*、状态、`<DataScopeRadio>`*、备注
- Tab 1「菜单权限」：`<Tree checkable>` ← dict.menuTree → menuIds
- Tab 2「按钮权限」：`<Tree checkable>` ← dict.permTree → permissionIds
- Tab 3「数据部门」（仅 dataScope=CUSTOM 显示）：`<Tree checkable>` ← dict.deptTree → deptIds
- Tab 4「项目权限」：`<CheckboxGroup>` ← dict.projectCodes → projectCodes

dataScope 切换非 CUSTOM 时清空 deptIds。Tree 用 `checkStrictly=false`，父子联动。

### `/system/menu`

```
筛选栏：[名称] [项目码▾] [搜索] [重置]
工具栏：[+新增根菜单]
表格 (pagination=false, 默认展开 1 级)：
  名称(带 icon) 类型 路径 排序 可见 状态 创建时间 [+子菜单][编辑][删除]
```

**MenuDrawer (800px)** — 字段联动（type Radio: M/C/F）：
- M（folder）：必填 name/parentId/sortNo/icon；隐藏 path/component/key
- C（page）：必填 name/parentId/path/component；可填 cache/visible/icon
- F（button）：必填 name/parentId/key（按钮 perm code）；隐藏 path/component/icon
- 公共：sortNo、status、visible、cache、target、projectCode、remark
- parentId：TreeSelect ← dict.menuTree（root 选项 = 0）
- 行操作 `+子菜单` 把当前行 id 预填到 parentId

保存/删除成功 → `dispatch dict/invalidate('menuTree')`。

## 共享组件

### `PermButton`

```tsx
type Props = ButtonProps & { perm?: string }
const PermButton: React.FC<Props> = ({ perm, children, ...rest }) => {
  const permissions = useSelector((s:any) => s.app.permissions as string[])
  if (perm && !permissions.includes(perm)) return null
  return <Button {...rest}>{children}</Button>
}
```

### `usePerm`

```ts
export const usePerm = () => {
  const perms = useSelector((s:any) => s.app.permissions as string[])
  return (code: string) => perms.includes(code)
}
```

### `constants/perms.ts`

```ts
export const PERMS = {
  user: { add:"system:user:add", edit:"system:user:edit", del:"system:user:remove", reset:"system:user:resetPwd" },
  role: { add:"system:role:add", edit:"system:role:edit", del:"system:role:remove" },
  menu: { add:"system:menu:add", edit:"system:menu:edit", del:"system:menu:remove" },
}
```

### `constants/dataScope.ts`

```ts
export enum DataScope { ALL=1, CUSTOM=2, DEPT=3, DEPT_AND_CHILD=4, SELF=5 }
export const DATA_SCOPE_TEXT: Record<DataScope, string> = {
  1:"全部数据", 2:"自定义部门", 3:"本部门", 4:"本部门及以下", 5:"仅本人",
}
```

## 错误处理

- effect 内 `Servpost.requestRace` 返回 `{status, message, data}`
- `status===200` → 处理 data；否则 `Message.error(res.message || "操作失败")`
- `try/finally` 确保 loading 复位
- 不在 effect 内 throw；callback 形如 `callback?.(success, data?)`，与 `login/model.ts` 一致
- 删除 / 批量删除：`Modal.confirm` 二次确认（批量显示选中条数）
- Drawer loading 期间：提交按钮 loading；maskClosable=false；closable=!loading
- 401 / 网络错由 Servpost 统一拦截

## 路由更新

`src/routers/routers.ts` 把 P1 的占位条目改为真实页面：

```ts
{ path: "/system/user", component: "@/pages/system/user" },
{ path: "/system/role", component: "@/pages/system/role" },
{ path: "/system/menu", component: "@/pages/system/menu" },
```

`/system/{dept,dict,tenant}` 维持 Placeholder。

## 前置任务（plan 第一批）

1. **核对字典端点真实路径**：`/dept/tree` `/post/list` `/project/list` `/sysPermission/tree`；缺失 → BLOCKED 记 plan 顶部。
2. **数据初始化**：`baseline_system.sql` 补 9 条 `sys_menu type=F` 行（与 PERMS 对齐），并赋给 super_admin。
3. **DataScope 字典对齐**：前端 `constants/dataScope.ts` 与后端枚举对齐。

## 后续模块兼容性

P2 是后续所有「列表 + Drawer 管理页」的样板。后续模块（部门、岗位、项目、字典、租户、会员、文件、配置、SSO 客户端 …）应**直接复用**下列约定，避免每个新页面重新发明轮子。

### 直接复用（不要改写）

| 资产 | 复用方式 |
|---|---|
| `dict` model + 内存缓存 | 后续页面要任何下拉 / 树选择，统一加到 `dict` 而不是各自 model 内冗余拉取 |
| `PermButton` / `usePerm` | 所有管理页按钮级权限统一走它，新增 perm code 加到 `constants/perms.ts` |
| `DataScopeRadio` / `constants/dataScope.ts` | 任何涉及数据范围的模块（如未来岗位/项目数据权限）直接引用 |
| Drawer 800/960px + 一行筛选栏 + `rowSelection` 批量删除 + `Modal.confirm` 二次确认 | 后续每个管理页的标准 UI 范式；不要换 Modal、不要换上下/左侧布局 |
| effect 约定（`Servpost.requestRace` + try/finally + callback + Message 错误） | 所有 model effect 都按这个写，不要 throw、不要在 component 里 catch |
| 树形 CRUD（菜单页）模式 | 后续部门 / 字典类型 / 区域等树形数据复用此模板 |
| 列表 CRUD（用户/角色页）模式 | 后续会员 / 租户 / 文件 / 客户端等扁平列表复用此模板 |

### 新增模块的标准做法

每加一个管理模块（下面以"岗位"为例）：

1. **建 `models/post.ts`**：state 结构与 `user.ts` 同形（list/total/filter/detail/loading）；effects `fetchPage / fetchDetail / saveOrUpdate / remove` 一对一对应后端端点。
2. **建 `pages/system/post/{index.tsx, PostDrawer.tsx}`**：完全套用 P2 的列表页 + Drawer 范式。
3. **如果该模块的列表/树需要被别的页面引用**（例如岗位下拉给用户编辑用），把"轻量列表/树"加到 `dict` model 而非自己 model；自己 model 只服务本页面分页列表。
4. **按钮 perm code** 统一加到 `constants/perms.ts`，例如 `PERMS.post = { add, edit, del }`，命名格式 `system:<module>:<action>`。
5. **路由** 在 `src/routers/routers.ts` 把 P1 占位条目改为真实页面；新模块的话补一条。

### 与多租户的边界（**关键**）

后端 `tenant.exclusionTable` 把以下表设为**全局表**（不带 tenant_id）：

```
sys_user, sys_menu, sys_config, sys_dict_data, sys_dict_type, sys_tenant, oauth_client_details
```

其余表（`sys_role / sys_post / sys_dept / sys_project / sys_member / ...`）都**自动注入 tenant_id**。这条边界决定了前端怎么缓存：

- **全局表数据**（用户、菜单、字典、租户、配置、OAuth 客户端）：可以放心放进 `dict` 跨页缓存，登录时拉一次也无所谓
- **租户隔离表数据**（角色、岗位、部门、项目、会员 …）：**不要长期缓存**，或在引入"切换操作租户"功能时必须 `dict/clearAll` 一次
  - P2 中 `simpleRoles` 暂放 `dict`，因为目前还没有租户切换 UI。引入后需把它移出 `dict`，或在切租户时 invalidate

引入"租户切换"那一刻要做的（**留作未来工作，不在 P2 范围**）：
1. 在顶栏加租户切换器（基于 P1 已抓取的 `accessibleTenants`）
2. 切换时 `dispatch dict/clearAll` + 所有非全局表的 model `reset`
3. Servpost 在请求头加 `X-Tenant-Id` 或类似字段（具体形式待后端约定）

### 一致性保障

- **不要在某个模块里偏离 P2 范式**（例如某个页面用 Modal 不用 Drawer、用 antd Pagination 不用 ProTable 风格的 footer 分页）。如果某个页面真的需要不同 UX，先改 P2 把通用范式抽成 `pages/common/CrudPage.tsx` 之类的高阶模板，再让该页面引用——避免范式裂变。
- **新增 dict 项时**先看是否真的跨页复用；只本页用的小列表保留在自己 model 里，别污染 dict。

## 验收清单

- [ ] 三页列表加载 / 分页 / 筛选 / 重置正常
- [ ] 新增 / 编辑 / 删除 / 批量删除走通；保存后列表自动刷新
- [ ] 用户：重置密码弹窗工作；角色下拉来自 simpleRoles 缓存
- [ ] 角色：4 Tab Tree 勾选回显正确；dataScope 切换 CUSTOM 才显示部门 Tab；保存提交完整 SaveDTO
- [ ] 菜单：树形展开、+子菜单 预填 parentId、type 切换字段联动；保存后清 dict.menuTree 缓存
- [ ] 按钮权限：无权限时新增/编辑/删除按钮不可见
- [ ] dict 缓存命中：同一 Drawer 二次打开不重复请求字典
- [ ] logout 后 dict 状态清空（下次登录拉新）
