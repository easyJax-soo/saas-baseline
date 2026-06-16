import React from "react"
import { Button, Dropdown, message } from "antd"
import { LogoutOutlined, UserOutlined } from "@ant-design/icons"
import { useDispatch, useNavigate, useSelector } from "umi"
import type { SessionUser } from "@/utils/sessionStore"

const UserMenu: React.FC = () => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const user = useSelector((s: any) => s.app.user as SessionUser | null)

    const handleLogout = () => {
        dispatch({
            type: "login/handleLoginOut",
            callback: () => {
                message.success("已退出登录")
                navigate("/login", { replace: true })
            },
        })
    }

    const items = [
        {
            key: "logout",
            label: (
                <span>
                    <LogoutOutlined /> 退出登录
                </span>
            ),
            onClick: handleLogout,
        },
    ]

    return (
        <Dropdown menu={{ items }} placement="bottomRight">
            <Button type="text" icon={<UserOutlined />}>
                {user?.name || user?.account || "未登录"}
            </Button>
        </Dropdown>
    )
}

export default UserMenu
