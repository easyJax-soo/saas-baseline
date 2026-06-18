import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined, ApartmentOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import DeptDrawer from "./DeptDrawer"
import type { SysDeptNodeVO } from "@/utils/types"
import { toNumber } from "@/utils/normalize"

const DeptPage: React.FC = () => {
    const dispatch = useDispatch()
    const { list, loading } = useSelector((s: any) => s.dept)
    const [drawerOpen, setDrawerOpen] = useState(false)
    const [drawerId, setDrawerId] = useState<number | undefined>(undefined)
    const [parentId, setParentId] = useState<number | undefined>(undefined)
    const [form] = Form.useForm()

    useEffect(() => {
        dispatch({ type: "dept/fetchList" })
    }, [])

    const handleSearch = () => {
        const payload = form.getFieldsValue()
        dispatch({ type: "dept/saveFilter", payload })
        dispatch({ type: "dept/fetchList", payload })
    }

    const handleReset = () => {
        form.resetFields()
        const payload = {}
        dispatch({ type: "dept/saveFilter", payload })
        dispatch({ type: "dept/fetchList", payload })
    }

    const handleRemove = (record: SysDeptNodeVO) => {
        Modal.confirm({
            title: "确认删除",
            content: `确定要删除部门「${record.name}」吗？若有下级部门将一并删除。`,
            onOk: () => dispatch({ type: "dept/remove", payload: [record.id] }),
        })
    }

    const columns = [
        {
            title: "部门名称",
            dataIndex: "name",
            key: "name",
            render: (name: string) => (
                <Space>
                    <ApartmentOutlined style={{ color: "#1890ff" }} />
                    <span>{name}</span>
                </Space>
            ),
        },
        { title: "部门编码", dataIndex: "code", key: "code", width: 160 },
        { title: "负责人", dataIndex: "leaderUserName", key: "leaderUserName", width: 120 },
        { title: "排序", dataIndex: "sortNo", key: "sortNo", width: 80 },
        {
            title: "状态",
            dataIndex: "status",
            key: "status",
            width: 90,
            render: (v: number | string) => (toNumber(v) === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag>),
        },
        { title: "创建时间", dataIndex: "createTime", key: "createTime", width: 180 },
        {
            title: "操作",
            key: "action",
            width: 220,
            render: (_: any, record: SysDeptNodeVO) => (
                <Space size="small">
                    <PermButton
                        perm={PERMS.dept.add}
                        type="link"
                        size="small"
                        onClick={() => {
                            setParentId(record.id)
                            setDrawerId(undefined)
                            setDrawerOpen(true)
                        }}
                    >
                        +下级
                    </PermButton>
                    <PermButton
                        perm={PERMS.dept.edit}
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
                    <PermButton perm={PERMS.dept.del} type="link" size="small" danger onClick={() => handleRemove(record)}>
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
                        <Input placeholder="部门名称" allowClear />
                    </Form.Item>
                    <Form.Item name="code">
                        <Input placeholder="部门编码" allowClear />
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
                    <PermButton
                        perm={PERMS.dept.add}
                        type="primary"
                        icon={<PlusOutlined />}
                        onClick={() => {
                            setParentId(0)
                            setDrawerId(undefined)
                            setDrawerOpen(true)
                        }}
                    >
                        新增根部门
                    </PermButton>
                </div>
                <Table rowKey="id" dataSource={list} columns={columns} loading={loading.list} pagination={false} defaultExpandAllRows />
            </Card>
            <DeptDrawer
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

export default DeptPage
