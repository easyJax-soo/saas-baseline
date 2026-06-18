export const PERMS = {
    user: { add: "system:user:save", edit: "system:user:save", del: "system:user:delete", reset: "system:user:resetPassword" },
    role: { add: "system:role:save", edit: "system:role:save", del: "system:role:delete" },
    menu: { add: "system:menu:save", edit: "system:menu:save", del: "system:menu:delete" },
    permission: { add: "system:permission:save", edit: "system:permission:save", del: "system:permission:delete" },
    dept: { add: "system:dept:save", edit: "system:dept:save", del: "system:dept:delete" },
    post: { add: "system:post:save", edit: "system:post:save", del: "system:post:delete" },
    dictType: { add: "system:dictType:save", edit: "system:dictType:save", del: "system:dictType:delete" },
    dict: { add: "system:dict:save", edit: "system:dict:save", del: "system:dict:delete" },
} as const
