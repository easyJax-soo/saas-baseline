import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import { DATA_SCOPE_TEXT } from "@/constants/dataScope"
import RoleDrawer from "./RoleDrawer"
import type { SysRolePageVO } from "@/utils/types"

const RolePage: React.FC = () => {
    const dispatch = useDispatch()
    const { list, total, filter, loading } = useSelector((s: any) => s.role)
    const [drawerOpen, setDrawerOpen] = useState(false)
    const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
    const [selectedRowKeys, setSelectedRowKeys] = useState<number[]>([])
    const [form] = Form.useForm()

    useEffect(() => {
        dispatch({ type: "role/fetchPage" })
    }, [])

    const handleSearch = () => {
        const vals = form.getFieldsValue()
        dispatch({ type: "role/saveFilter", payload: vals })
        dispatch({ type: "role/fetchPage" })
    }

    const handleReset = () => {
        form.resetFields()
        dispatch({ type: "role/saveFilter", payload: {} })
        dispatch({ type: "role/fetchPage" })
    }

    const handleBatchRemove = () => {
        if (selectedRowKeys.length === 0) return
        Modal.confirm({
            title: "确认批量删除",
            content: `确定要删除选中的 ${selectedRowKeys.length} 条记录吗？`,
            onOk: () => dispatch({ type: "role/remove", payload: selectedRowKeys, callback: () => setSelectedRowKeys([]) }),
        })
    }

    const columns = [
        { title: "角色名称", dataIndex: "name", key: "name" },
        { title: "角色标识", dataIndex: "key", key: "key" },
        {
            title: "数据范围",
            dataIndex: "dataScope",
            key: "dataScope",
            render: (v: number) => DATA_SCOPE_TEXT[v as keyof typeof DATA_SCOPE_TEXT] || v,
        },
        {
            title: "状态",
            dataIndex: "status",
            key: "status",
            render: (v: number) => (v === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag>),
        },
        { title: "创建时间", dataIndex: "createTime", key: "createTime" },
        {
            title: "操作",
            key: "action",
            width: 160,
            render: (_: any, record: SysRolePageVO) => (
                <Space size="small">
                    <PermButton
                        perm={PERMS.role.edit}
                        type="link"
                        size="small"
                        onClick={() => {
                            setDrawerId(record.id)
                            setDrawerOpen(true)
                        }}
                    >
                        编辑
                    </PermButton>
                    <PermButton
                        perm={PERMS.role.del}
                        type="link"
                        size="small"
                        danger
                        onClick={() => {
                            Modal.confirm({
                                title: "确认删除",
                                content: `确定删除角色「${record.name}」？`,
                                onOk: () => dispatch({ type: "role/remove", payload: [record.id] }),
                            })
                        }}
                    >
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
                        <Input placeholder="角色名称" allowClear />
                    </Form.Item>
                    <Form.Item name="key">
                        <Input placeholder="角色标识" allowClear />
                    </Form.Item>
                    <Form.Item name="status">
                        <Select placeholder="状态" allowClear style={{ width: 100 }}>
                            <Select.Option value={1}>启用</Select.Option>
                            <Select.Option value={0}>禁用</Select.Option>
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
                    <Space>
                        <PermButton
                            perm={PERMS.role.add}
                            type="primary"
                            icon={<PlusOutlined />}
                            onClick={() => {
                                setDrawerId(undefined)
                                setDrawerOpen(true)
                            }}
                        >
                            新增角色
                        </PermButton>
                        <PermButton perm={PERMS.role.del} disabled={selectedRowKeys.length === 0} onClick={handleBatchRemove}>
                            批量删除
                        </PermButton>
                    </Space>
                </div>
                <Table
                    rowKey="id"
                    dataSource={list}
                    columns={columns}
                    loading={loading.list}
                    rowSelection={{ selectedRowKeys, onChange: (keys: any) => setSelectedRowKeys(keys) }}
                    pagination={{
                        current: filter.current,
                        pageSize: filter.size,
                        total,
                        onChange: (p, s) => {
                            dispatch({ type: "role/pageChange", payload: { current: p, size: s } })
                            dispatch({ type: "role/fetchPage" })
                        },
                        showSizeChanger: true,
                        showTotal: (t: number) => `共 ${t} 条`,
                    }}
                />
            </Card>
            <RoleDrawer
                open={drawerOpen}
                id={drawerId}
                onClose={() => {
                    setDrawerOpen(false)
                    setDrawerId(undefined)
                }}
            />
        </>
    )
}

export default RolePage
