import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined, FolderOutlined, FileOutlined, CodeOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import MenuDrawer from "./MenuDrawer"
import type { SysMenuNodeVO } from "@/utils/types"

const typeIcon: Record<string, React.ReactNode> = {
    M: <FolderOutlined style={{ color: "#faad14" }} />,
    C: <FileOutlined style={{ color: "#1890ff" }} />,
    F: <CodeOutlined style={{ color: "#52c41a" }} />,
}

const typeText: Record<string, string> = { M: "目录", C: "页面", F: "按钮" }

const MenuPage: React.FC = () => {
    const dispatch = useDispatch()
    const { tree, loading } = useSelector((s: any) => s.menu)
    const [drawerOpen, setDrawerOpen] = useState(false)
    const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
    const [parentId, setParentId] = useState<number | undefined>(undefined)
    const [form] = Form.useForm()

    useEffect(() => {
        dispatch({ type: "menu/fetchTree" })
    }, [])

    const handleSearch = () => {
        const vals = form.getFieldsValue()
        dispatch({ type: "menu/saveFilter", payload: vals })
        dispatch({ type: "menu/fetchTree" })
    }

    const handleReset = () => {
        form.resetFields()
        dispatch({ type: "menu/saveFilter", payload: {} })
        dispatch({ type: "menu/fetchTree" })
    }

    const handleRemove = (record: SysMenuNodeVO) => {
        Modal.confirm({
            title: "确认删除",
            content: `确定要删除「${record.name}」吗？若有子菜单将一并删除。`,
            onOk: () => {
                dispatch({ type: "menu/remove", payload: [record.id] })
            },
        })
    }

    const columns = [
        {
            title: "菜单名称",
            dataIndex: "name",
            key: "name",
            render: (_: string, record: SysMenuNodeVO) => (
                <Space>
                    {typeIcon[record.type] || null}
                    <span>{record.name}</span>
                    {record.type === "F" && record.key && <Tag style={{ fontSize: 11 }}>{record.key}</Tag>}
                </Space>
            ),
        },
        { title: "类型", dataIndex: "type", key: "type", width: 70, render: (v: string) => typeText[v] || v },
        { title: "路由", dataIndex: "path", key: "path", width: 200, ellipsis: true },
        { title: "排序", dataIndex: "sortNo", key: "sortNo", width: 60 },
        {
            title: "可见",
            dataIndex: "visible",
            key: "visible",
            width: 60,
            render: (v: number) => (v === 1 ? <Tag color="blue">是</Tag> : <Tag>否</Tag>),
        },
        {
            title: "状态",
            dataIndex: "status",
            key: "status",
            width: 60,
            render: (v: number) => (v === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag>),
        },
        { title: "创建时间", dataIndex: "createTime", key: "createTime", width: 160 },
        {
            title: "操作",
            key: "action",
            width: 200,
            render: (_: any, record: SysMenuNodeVO) => (
                <Space size="small">
                    <PermButton
                        perm={PERMS.menu.add}
                        type="link"
                        size="small"
                        onClick={() => {
                            setParentId(record.id)
                            setDrawerId(undefined)
                            setDrawerOpen(true)
                        }}
                    >
                        +子菜单
                    </PermButton>
                    <PermButton
                        perm={PERMS.menu.edit}
                        type="link"
                        size="small"
                        onClick={() => {
                            setParentId(undefined)
                            setDrawerId(record.id)
                            setDrawerOpen(true)
                        }}
                    >
                        编辑
                    </PermButton>
                    <PermButton perm={PERMS.menu.del} type="link" size="small" danger onClick={() => handleRemove(record)}>
                        删除
                    </PermButton>
                </Space>
            ),
        },
    ]

    return (
        <>
            <Card size="small" style={{ marginBottom: 8 }}>
                <Form form={form} layout="inline" style={{ flexWrap: "wrap", gap: 8 }}>
                    <Form.Item name="name">
                        <Input placeholder="菜单名称" allowClear />
                    </Form.Item>
                    <Form.Item name="projectCode">
                        <Select placeholder="项目码" allowClear style={{ width: 150 }}>
                            <Select.Option value="BASELINE">BASELINE</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item>
                        <Space>
                            <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>
                                搜索
                            </Button>
                            <Button icon={<ReloadOutlined />} onClick={handleReset}>
                                重置
                            </Button>
                        </Space>
                    </Form.Item>
                </Form>
            </Card>
            <Card size="small">
                <div style={{ marginBottom: 12 }}>
                    <PermButton
                        perm={PERMS.menu.add}
                        type="primary"
                        icon={<PlusOutlined />}
                        onClick={() => {
                            setParentId(0)
                            setDrawerId(undefined)
                            setDrawerOpen(true)
                        }}
                    >
                        新增根菜单
                    </PermButton>
                </div>
                <Table rowKey="id" dataSource={tree} columns={columns} loading={loading.tree} pagination={false} defaultExpandAllRows />
            </Card>
            <MenuDrawer
                open={drawerOpen}
                id={drawerId}
                parentId={parentId}
                onClose={() => {
                    setDrawerOpen(false)
                    setDrawerId(undefined)
                    setParentId(undefined)
                }}
            />
        </>
    )
}

export default MenuPage
