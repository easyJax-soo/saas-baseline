const Routers = [
    { path: "/login", component: "login/login" },
    {
        path: "/",
        component: "baseLayout/baseLayout",
        routes: [
            { path: "/", redirect: "/home" },
            { path: "/home", component: "home/home" },
            { path: "/403", component: "common/Forbidden" },
            { path: "/setting/user", component: "system/user/index" },
            { path: "/role", component: "system/role/index" },
            { path: "/menu", component: "system/menu/index" },
            { path: "/permission", component: "system/permission/index" },
            { path: "/dept", component: "system/dept/index" },
            { path: "/Post", component: "system/post/index" },
            { path: "/post", component: "system/post/index" },
            { path: "/dic", component: "system/dict/index" },
            { path: "/dict", component: "system/dict/index" },
            { path: "/system/user", component: "system/user/index" },
            { path: "/system/role", component: "system/role/index" },
            { path: "/system/menu", component: "system/menu/index" },
            { path: "/system/permission", component: "system/permission/index" },
            { path: "/system/dept", component: "system/dept/index" },
            { path: "/system/post", component: "system/post/index" },
            { path: "/system/dict", component: "system/dict/index" },
            { path: "/system/tenant", component: "common/Placeholder" },
            { path: "/profile", component: "common/Placeholder" },
        ],
    },
    { path: "/*", component: "404" },
]

export default Routers
