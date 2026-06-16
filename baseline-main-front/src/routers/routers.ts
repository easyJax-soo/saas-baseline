const Routers = [
    { path: "/login", component: "login/login" },
    {
        path: "/",
        component: "baseLayout/baseLayout",
        routes: [
            { path: "/", redirect: "/home" },
            { path: "/home", component: "home/home" },
            { path: "/403", component: "common/Forbidden" },
            { path: "/system/user", component: "system/user/index" },
            { path: "/system/role", component: "system/role/index" },
            { path: "/system/menu", component: "system/menu/index" },
            { path: "/system/dept", component: "common/Placeholder" },
            { path: "/system/dict", component: "common/Placeholder" },
            { path: "/system/tenant", component: "common/Placeholder" },
            { path: "/profile", component: "common/Placeholder" },
        ],
    },
    { path: "/*", component: "404" },
]

export default Routers
