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
