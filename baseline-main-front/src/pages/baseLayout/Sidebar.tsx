import React, { useEffect, useMemo, useState } from "react"
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

const menuKey = (node: SysMenuNode): string => node.path || `m_${node.id}`

const isEnabledMenu = (node: SysMenuNode): boolean => node.type !== "F" && node.visible === "1" && node.status === "1"

const buildItems = (nodes: SysMenuNode[]): MenuItem[] => {
    const out: MenuItem[] = []
    for (const node of nodes) {
        if (!isEnabledMenu(node)) continue

        const children = Array.isArray(node.children) ? buildItems(node.children) : []

        if (node.type === "M") {
            if (children.length === 0) continue
            out.push({
                key: menuKey(node),
                label: node.name,
                icon: renderIcon(node.icon),
                children,
            } as MenuItem)
        } else if (node.type === "C") {
            out.push({
                key: menuKey(node),
                label: node.name,
                icon: renderIcon(node.icon),
                ...(children.length > 0 ? { children } : {}),
            } as MenuItem)
        }
    }
    return out
}

const findMenuPath = (nodes: SysMenuNode[], pathname: string, parents: string[] = []): string[] => {
    for (const node of nodes) {
        if (!isEnabledMenu(node)) continue

        const key = menuKey(node)
        const nextParents = node.type === "M" ? [...parents, key] : parents
        if (node.type === "C" && key === pathname) return [...parents, key]

        const childPath = Array.isArray(node.children) ? findMenuPath(node.children, pathname, nextParents) : []
        if (childPath.length > 0) return childPath
    }
    return []
}

const Sidebar: React.FC = () => {
    const menu = useSelector((s: any) => s.app.menu as SysMenuNode[])
    const navigate = useNavigate()
    const location = useLocation()

    const items = useMemo(() => buildItems(menu || []), [menu])
    const activePath = useMemo(() => findMenuPath(menu || [], location.pathname), [menu, location.pathname])
    const selected = activePath[activePath.length - 1] || location.pathname
    const activeOpenKeys = activePath.slice(0, -1)
    const [openKeys, setOpenKeys] = useState<string[]>([])

    useEffect(() => {
        setOpenKeys((prev) => Array.from(new Set([...prev, ...activeOpenKeys])))
    }, [activeOpenKeys.join("|")])

    return (
        <Menu
            mode="inline"
            theme="light"
            items={items}
            selectedKeys={[selected]}
            openKeys={openKeys}
            onOpenChange={(keys) => setOpenKeys(keys as string[])}
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
