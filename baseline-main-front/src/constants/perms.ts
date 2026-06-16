export const PERMS = {
    user: { add: "system:user:add", edit: "system:user:edit", del: "system:user:remove", reset: "system:user:resetPwd" },
    role: { add: "system:role:add", edit: "system:role:edit", del: "system:role:remove" },
    menu: { add: "system:menu:add", edit: "system:menu:edit", del: "system:menu:remove" },
} as const
