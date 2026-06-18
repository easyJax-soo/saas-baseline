import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined, SafetyCertificateOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import PermissionDrawer from "./PermissionDrawer"
import type { SysPermissionNodeVO } from "@/utils/types"

const PermissionPage: React.FC = () => {
    const dispatch = useDispatch()
    const { tree, loading } = useSelector((s: any) => s.permission)
    const [drawerOpen, setDrawerOpen] = useState(false)
    const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
    const [parentId, setParentId] = useState<number | undefined>(undefined)
    const [form] = Form.useForm()

    useEffect(() => {
        dispatch({ type: "permission/fetchTree" })
    }, [])

    const handleSearch = () => {
        const payload = form.getFieldsValue()
        dispatch({ type: "permission/saveFilter", payload })
        dispatch({ type: "permission/fetchTree", payload })
    }

    const handleReset = () => {
        form.resetFields()
        const payload = {}
        dispatch({ type: "permission/saveFilter", payload })
        dispatch({ type: "permission/fetchTree", payload })
    }

    const handleRemove = (record: SysPermissionNodeVO) => {
        Modal.confirm({
            title: "确认删除",
            content: `确定要删除「${record.name}」吗？若有子权限将一并删除。`,
            onOk: () => dispatch({ type: "permission/remove", payload: [record.id] }),
        })
    }

    const columns = [
        {
            title: "权限名称",
            dataIndex: "name",
            key: "name",
            render: (name: string, record: SysPermissionNodeVO) => (
                <Space>
                    <SafetyCertificateOutlined style={{ color: "#1890ff" }} />
                    <span>{name}</span>
                    {record.permission && <Tag style={{ fontSize: 11 }}>{record.permission}</Tag>}
                </Space>
            ),
        },
        { title: "权限标识", dataIndex: "permission", key: "permission", width: 260, ellipsis: true },
        { title: "项目码", dataIndex: "projectCode", key: "projectCode", width: 120 },
        { title: "排序", dataIndex: "sortNo", key: "sortNo", width: 80 },
        { title: "创建时间", dataIndex: "createTime", key: "createTime", width: 180 },
        {
            title: "操作",
            key: "action",
            width: 220,
            render: (_: any, record: SysPermissionNodeVO) => (
                <Space size="small">
                    <PermButton
                        perm={PERMS.permission.add}
                        type="link"
                        size="small"
                        onClick={() => {
                            setParentId(record.id)
                            setDrawerId(undefined)
                            setDrawerOpen(true)
                        }}
                    >
                        +子权限
                    </PermButton>
                    <PermButton
                        perm={PERMS.permission.edit}
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
                    <PermButton perm={PERMS.permission.del} type="link" size="small" danger onClick={() => handleRemove(record)}>
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
                        <Input placeholder="权限名称" allowClear />
                    </Form.Item>
                    <Form.Item name="permission">
                        <Input placeholder="权限标识" allowClear />
                    </Form.Item>
                    <Form.Item name="projectCode">
                        <Select placeholder="项目码" allowClear style={{ width: 150 }}>
                            <Select.Option value="system">system</Select.Option>
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
                        perm={PERMS.permission.add}
                        type="primary"
                        icon={<PlusOutlined />}
                        onClick={() => {
                            setParentId(0)
                            setDrawerId(undefined)
                            setDrawerOpen(true)
                        }}
                    >
                        新增根权限
                    </PermButton>
                </div>
                <Table rowKey="id" dataSource={tree} columns={columns} loading={loading.tree} pagination={false} defaultExpandAllRows />
            </Card>
            <PermissionDrawer
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

export default PermissionPage
