import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import PostDrawer from "./PostDrawer"
import type { SysPostVO } from "@/utils/types"
import { toNumber } from "@/utils/normalize"

const PostPage: React.FC = () => {
    const dispatch = useDispatch()
    const { list, total, filter, loading } = useSelector((s: any) => s.post)
    const [drawerOpen, setDrawerOpen] = useState(false)
    const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
    const [selectedRowKeys, setSelectedRowKeys] = useState<number[]>([])
    const [form] = Form.useForm()

    useEffect(() => {
        dispatch({ type: "post/fetchPage" })
    }, [])

    const handleSearch = () => {
        const payload = { ...form.getFieldsValue(), current: 1 }
        dispatch({ type: "post/saveFilter", payload })
        dispatch({ type: "post/fetchPage", payload })
    }

    const handleReset = () => {
        form.resetFields()
        const payload = { current: 1, size: filter.size }
        dispatch({ type: "post/saveFilter", payload })
        dispatch({ type: "post/fetchPage", payload })
    }

    const handleBatchRemove = () => {
        if (selectedRowKeys.length === 0) return
        Modal.confirm({
            title: "确认批量删除",
            content: `确定要删除选中的 ${selectedRowKeys.length} 条记录吗？`,
            onOk: () => dispatch({ type: "post/remove", payload: selectedRowKeys, callback: () => setSelectedRowKeys([]) }),
        })
    }

    const columns = [
        { title: "岗位名称", dataIndex: "name", key: "name" },
        { title: "岗位编码", dataIndex: "code", key: "code" },
        { title: "排序", dataIndex: "sortNo", key: "sortNo", width: 80 },
        {
            title: "状态",
            dataIndex: "status",
            key: "status",
            width: 90,
            render: (v: number | string) => (toNumber(v) === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag>),
        },
        { title: "创建时间", dataIndex: "createTime", key: "createTime" },
        {
            title: "操作",
            key: "action",
            width: 160,
            render: (_: any, record: SysPostVO) => (
                <Space size="small">
                    <PermButton
                        perm={PERMS.post.edit}
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
                        perm={PERMS.post.del}
                        type="link"
                        size="small"
                        danger
                        onClick={() => {
                            Modal.confirm({
                                title: "确认删除",
                                content: `确定删除岗位「${record.name}」？`,
                                onOk: () => dispatch({ type: "post/remove", payload: [record.id] }),
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
                        <Input placeholder="岗位名称" allowClear />
                    </Form.Item>
                    <Form.Item name="code">
                        <Input placeholder="岗位编码" allowClear />
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
                            perm={PERMS.post.add}
                            type="primary"
                            icon={<PlusOutlined />}
                            onClick={() => {
                                setDrawerId(undefined)
                                setDrawerOpen(true)
                            }}
                        >
                            新增岗位
                        </PermButton>
                        <PermButton perm={PERMS.post.del} disabled={selectedRowKeys.length === 0} onClick={handleBatchRemove}>
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
                            dispatch({ type: "post/pageChange", payload })
                            dispatch({ type: "post/fetchPage", payload })
                        },
                        showSizeChanger: true,
                        showTotal: (t: number) => `共 ${t} 条`,
                    }}
                />
            </Card>
            <PostDrawer
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

export default PostPage
