import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import UserDrawer from "./UserDrawer"
import ResetPwdModal from "./ResetPwdModal"
import type { SysUserPageVO } from "@/utils/types"
import { toNumber } from "@/utils/normalize"

const UserPage: React.FC = () => {
    const dispatch = useDispatch()
    const { list, total, filter, loading } = useSelector((s: any) => s.user)
    const [drawerOpen, setDrawerOpen] = useState(false)
    const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
    const [resetPwdUserId, setResetPwdUserId] = useState<number | undefined>(undefined)
    const [selectedRowKeys, setSelectedRowKeys] = useState<number[]>([])
    const [form] = Form.useForm()

    useEffect(() => {
        dispatch({ type: "user/fetchPage" })
    }, [])

    const handleSearch = () => {
        const payload = { ...form.getFieldsValue(), current: 1 }
        dispatch({ type: "user/saveFilter", payload })
        dispatch({ type: "user/fetchPage", payload })
    }

    const handleReset = () => {
        form.resetFields()
        const payload = { current: 1, size: filter.size }
        dispatch({ type: "user/saveFilter", payload })
        dispatch({ type: "user/fetchPage", payload })
    }

    const handleAdd = () => {
        setDrawerId(undefined)
        setDrawerOpen(true)
    }

    const handleEdit = (id: number) => {
        setDrawerId(id)
        setDrawerOpen(true)
    }

    const handleRemove = (id: number) => {
        Modal.confirm({
            title: "确认删除",
            content: "确定要删除该用户吗？",
            onOk: () => {
                dispatch({ type: "user/remove", payload: [id] })
            },
        })
    }

    const handleBatchRemove = () => {
        if (selectedRowKeys.length === 0) return
        Modal.confirm({
            title: "确认批量删除",
            content: `确定要删除选中的 ${selectedRowKeys.length} 条记录吗？`,
            onOk: () => {
                dispatch({ type: "user/remove", payload: selectedRowKeys, callback: () => setSelectedRowKeys([]) })
            },
        })
    }

    const columns = [
        { title: "账号", dataIndex: "account", key: "account" },
        { title: "姓名", dataIndex: "name", key: "name" },
        { title: "手机号", dataIndex: "phone", key: "phone" },
        { title: "部门", dataIndex: "deptName", key: "deptName" },
        {
            title: "状态",
            dataIndex: "status",
            key: "status",
            render: (v: number | string) => (toNumber(v) === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag>),
        },
        { title: "创建时间", dataIndex: "createTime", key: "createTime" },
        {
            title: "操作",
            key: "action",
            width: 200,
            render: (_: any, record: SysUserPageVO) => (
                <Space size="small">
                    <PermButton perm={PERMS.user.edit} type="link" size="small" onClick={() => handleEdit(record.id)}>
                        编辑
                    </PermButton>
                    <PermButton perm={PERMS.user.reset} type="link" size="small" onClick={() => setResetPwdUserId(record.id)}>
                        重置密码
                    </PermButton>
                    <PermButton perm={PERMS.user.del} type="link" size="small" danger onClick={() => handleRemove(record.id)}>
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
                        <Input placeholder="姓名" allowClear />
                    </Form.Item>
                    <Form.Item name="phone">
                        <Input placeholder="手机号" allowClear />
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
                        <PermButton perm={PERMS.user.add} type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
                            新增用户
                        </PermButton>
                        <PermButton perm={PERMS.user.del} disabled={selectedRowKeys.length === 0} onClick={handleBatchRemove}>
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
                        onChange: (page, size) => {
                            const payload = { current: page, size }
                            dispatch({ type: "user/pageChange", payload })
                            dispatch({ type: "user/fetchPage", payload })
                        },
                        showSizeChanger: true,
                        showTotal: (t: number) => `共 ${t} 条`,
                    }}
                />
            </Card>
            <UserDrawer
                open={drawerOpen}
                id={drawerId}
                onClose={() => {
                    setDrawerOpen(false)
                    setDrawerId(undefined)
                }}
            />
            <ResetPwdModal userId={resetPwdUserId} onClose={() => setResetPwdUserId(undefined)} />
        </>
    )
}

export default UserPage
