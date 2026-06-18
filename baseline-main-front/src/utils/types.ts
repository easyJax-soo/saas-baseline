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
    projectCode: string
    level: number
    remark: string
    createTime: string
    children?: SysPermissionNodeVO[]
}

export interface SysPermissionSaveDTO {
    id?: number
    name: string
    permission: string
    parentId: number
    sortNo: number
    projectCode: string
}

export interface SysDeptNodeVO {
    id: number
    name: string
    code: string
    sortNo: number
    parentId: number
    parentPath: string
    level: number
    leaderUserId: number
    leaderUserName: string
    createTime: string
    status: number
    remark: string
    children?: SysDeptNodeVO[]
}

export interface SysDeptSaveDTO {
    id?: number
    name: string
    code: string
    parentId: number
    parentPath?: string
    sortNo: number
    leaderUserId?: number
    status: number
    level?: number
    remark?: string
}

export interface SysPostVO {
    id: number
    name: string
    code: string
    sortNo: number
    status: number
    createTime: string
    updateTime: string
    remark: string
}

export interface SysPostSaveDTO {
    id?: number
    name: string
    code: string
    sortNo: number
    status: number
    remark?: string
}

export interface SysDictTypeVO {
    id: number
    name: string
    code: string
    status: number
    createTime: string
    updateTime: string
    remark: string
}

export interface SysDictDataVO {
    id: number
    sortNo: number
    label: string
    value: string
    code: string
    isDefault: number
    status: number
    createTime: string
    updateTime: string
    remark: string
}

export interface SysDictGroupVO {
    name: string
    code: string
    dicts: SysDictDataVO[]
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
