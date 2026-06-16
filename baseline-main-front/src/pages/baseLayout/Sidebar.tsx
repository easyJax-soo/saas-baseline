import React, { useMemo } from "react"
import { Menu } from "antd"
import { useSelector, useNavigate, useLocation } from "umi"
import * as AntIcons from "@ant-design/icons"
import type { SysMenuNode } from "@/utils/sessionStore"

type MenuItem = Required<React.ComponentProps<typeof Menu>>["items"][number]

const renderIcon = (name: string): React.ReactNode => {
    if (!name) return undefined
    const Icon = (AntIcons as any)[name]
    if (!Icon) return undefined
    return React.createElement(Icon)
}

const buildItems = (nodes: SysMenuNode[]): MenuItem[] => {
    const out: MenuItem[] = []
    for (const node of nodes) {
        if (node.type === "F") continue
        if (node.visible !== 1 || node.status !== 1) continue

        const children = Array.isArray(node.children) ? buildItems(node.children) : []

        if (node.type === "M") {
            if (children.length === 0) continue
            out.push({
                key: node.path || `m_${node.id}`,
                label: node.name,
                icon: renderIcon(node.icon),
                children,
            } as MenuItem)
        } else if (node.type === "C") {
            out.push({
                key: node.path,
                label: node.name,
                icon: renderIcon(node.icon),
                ...(children.length > 0 ? { children } : {}),
            } as MenuItem)
        }
    }
    return out
}

const Sidebar: React.FC = () => {
    const menu = useSelector((s: any) => s.app.menu as SysMenuNode[])
    const navigate = useNavigate()
    const location = useLocation()

    const items = useMemo(() => buildItems(menu || []), [menu])
    const selected = location.pathname

    return (
        <Menu
            mode="inline"
            theme="light"
            items={items}
            selectedKeys={[selected]}
            onClick={({ key }) => {
                if (typeof key === "string" && key.startsWith("/")) {
                    navigate(key)
                }
            }}
            style={{ height: "100%", borderRight: 0 }}
        />
    )
}

export default Sidebar
