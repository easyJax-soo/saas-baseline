import React, { useEffect, useState } from "react"
import { Outlet } from "umi"
import { requireAuth } from "@/utils/authGuard"
import Sidebar from "./Sidebar"
import UserMenu from "./UserMenu"
import style from "./baseLayout.less"

const BaseLayout: React.FC = () => {
    const [authed, setAuthed] = useState<boolean>(false)

    useEffect(() => {
        const ok = requireAuth()
        setAuthed(ok)
    }, [])

    if (!authed) {
        return null
    }

    return (
        <div className={style.baseLayoutRoot}>
            <div className={style.header}>
                <div className={style.headerTitle}>Baseline 多租户基线系统</div>
                <div className={style.headerRight}>
                    <UserMenu />
                </div>
            </div>
            <div className={style.body}>
                <div className={style.sidebar}>
                    <Sidebar />
                </div>
                <div className={style.content}>
                    <Outlet />
                </div>
            </div>
        </div>
    )
}

export default BaseLayout
