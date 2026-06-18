import React, { useEffect, useState } from "react"
import { Card, Table, Form, Input, Select, Button, Space, Tag, Modal, Empty } from "antd"
import { PlusOutlined, SearchOutlined, ReloadOutlined } from "@ant-design/icons"
import { useDispatch, useSelector } from "umi"
import PermButton from "@/pages/common/PermButton"
import { PERMS } from "@/constants/perms"
import DictTypeDrawer from "./DictTypeDrawer"
import DictDataDrawer from "./DictDataDrawer"
import type { SysDictDataVO, SysDictTypeVO } from "@/utils/types"
import { toNumber } from "@/utils/normalize"

const DictPage: React.FC = () => {
    const dispatch = useDispatch()
    const { typeList, dataList, dataTotal, selectedType, dataFilter, loading } = useSelector((s: any) => s.dictManage)
    const [typeDrawerOpen, setTypeDrawerOpen] = useState(false)
    const [typeRecord, setTypeRecord] = useState<SysDictTypeVO | undefined>(undefined)
    const [dataDrawerOpen, setDataDrawerOpen] = useState(false)
    const [dataRecord, setDataRecord] = useState<SysDictDataVO | undefined>(undefined)
    const [form] = Form.useForm()

    useEffect(() => {
        dispatch({ type: "dictManage/fetchTypeList" })
    }, [])

    useEffect(() => {
        if (selectedType?.code) {
            dispatch({ type: "dictManage/fetchDataPage", payload: { code: selectedType.code } })
        }
    }, [selectedType?.code])

    const handleSearch = () => {
        const payload = { ...form.getFieldsValue(), current: 1, code: selectedType?.code }
        dispatch({ type: "dictManage/saveDataFilter", payload })
        dispatch({ type: "dictManage/fetchDataPage", payload })
    }

    const handleReset = () => {
        form.resetFields()
        const payload = { current: 1, size: dataFilter.size, label: undefined, status: undefined, code: selectedType?.code }
        dispatch({ type: "dictManage/saveDataFilter", payload })
        dispatch({ type: "dictManage/fetchDataPage", payload })
    }

    const typeColumns = [
        { title: "字典名称", dataIndex: "name", key: "name", ellipsis: true },
        { title: "字典编码", dataIndex: "code", key: "code", ellipsis: true },
        {
            title: "状态",
            dataIndex: "status",
            key: "status",
            width: 80,
            render: (v: number | string) => (toNumber(v) === 1 ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag>),
        },
        {
            title: "操作",
            key: "action",
            width: 120,
            render: (_: any, record: SysDictTypeVO) => (
                <Space size="small">
                    <PermButton
                        perm={PERMS.dictType.edit}
                        type="link"
                        size="small"
                        onClick={() => {
                            setTypeRecord(record)
                            setTypeDrawerOpen(true)
                        }}
                    >
                        编辑
                    </PermButton>
                    <PermButton
                        perm={PERMS.dictType.del}
                        type="link"
                        size="small"
                        danger
                        onClick={() => {
                            Modal.confirm({
                                title: "确认删除",
                                content: `确定删除字典类型「${record.name}」？`,
                                onOk: () => dispatch({ type: "dictManage/removeType", payload: [record.id] }),
                            })
                        }}
                    >
                        删除
                    </PermButton>
                </Space>
            ),
        },
    ]

    const dataColumns = [
        { title: "标签", dataIndex: "label", key: "label" },
        { title: "键值", dataIndex: "value", key: "value" },
        { title: "排序", dataIndex: "sortNo", key: "sortNo", width: 80 },
        {
            title: "默认",
            dataIndex: "isDefault",
            key: "isDefault",
            width: 80,
            render: (v: number | string) => (toNumber(v) === 1 ? <Tag color="blue">是</Tag> : <Tag>否</Tag>),
        },
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
            width: 150,
            render: (_: any, record: SysDictDataVO) => (
                <Space size="small">
                    <PermButton
                        perm={PERMS.dict.edit}
                        type="link"
                        size="small"
                        onClick={() => {
                            setDataRecord(record)
                            setDataDrawerOpen(true)
                        }}
                    >
                        编辑
                    </PermButton>
                    <PermButton
                        perm={PERMS.dict.del}
                        type="link"
                        size="small"
                        danger
                        onClick={() => {
                            Modal.confirm({
                                title: "确认删除",
                                content: `确定删除字典数据「${record.label}」？`,
                                onOk: () => dispatch({ type: "dictManage/removeData", payload: [record.id] }),
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
        <div style={{ display: "flex", gap: 12 }}>
            <Card
                size="small"
                title="字典类型"
                style={{ width: 460, flexShrink: 0 }}
                extra={
                    <PermButton
                        perm={PERMS.dictType.add}
                        type="primary"
                        size="small"
                        icon={<PlusOutlined />}
                        onClick={() => {
                            setTypeRecord(undefined)
                            setTypeDrawerOpen(true)
                        }}
                    >
                        新增
                    </PermButton>
                }
            >
                <Table
                    rowKey="id"
                    dataSource={typeList}
                    columns={typeColumns}
                    loading={loading.typeList}
                    pagination={false}
                    size="small"
                    rowClassName={(record: SysDictTypeVO) => (record.id === selectedType?.id ? "ant-table-row-selected" : "")}
                    onRow={(record: SysDictTypeVO) => ({
                        onClick: () => dispatch({ type: "dictManage/selectType", payload: record }),
                    })}
                />
            </Card>
            <Card size="small" title={selectedType ? `字典数据：${selectedType.name}` : "字典数据"} style={{ flex: 1, minWidth: 0 }}>
                {selectedType ? (
                    <>
                        <Card size="small" style={{ marginBottom: 8 }}>
                            <Form form={form} layout="inline" style={{ flexWrap: "wrap", gap: 8 }}>
                                <Form.Item name="label">
                                    <Input placeholder="字典标签" allowClear />
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
                        <div style={{ marginBottom: 12 }}>
                            <PermButton
                                perm={PERMS.dict.add}
                                type="primary"
                                icon={<PlusOutlined />}
                                onClick={() => {
                                    setDataRecord(undefined)
                                    setDataDrawerOpen(true)
                                }}
                            >
                                新增字典数据
                            </PermButton>
                        </div>
                        <Table
                            rowKey="id"
                            dataSource={dataList}
                            columns={dataColumns}
                            loading={loading.dataList}
                            pagination={{
                                current: dataFilter.current,
                                pageSize: dataFilter.size,
                                total: dataTotal,
                                onChange: (page, size) => {
                                    const payload = { current: page, size, code: selectedType.code }
                                    dispatch({ type: "dictManage/pageChange", payload })
                                    dispatch({ type: "dictManage/fetchDataPage", payload })
                                },
                                showSizeChanger: true,
                                showTotal: (t: number) => `共 ${t} 条`,
                            }}
                        />
                    </>
                ) : (
                    <Empty description="请选择字典类型" />
                )}
            </Card>
            <DictTypeDrawer
                open={typeDrawerOpen}
                record={typeRecord}
                saving={loading.typeSave}
                onSubmit={(values) => {
                    dispatch({
                        type: "dictManage/saveType",
                        payload: values,
                        callback: (ok: boolean) => {
                            if (ok) {
                                setTypeDrawerOpen(false)
                                setTypeRecord(undefined)
                            }
                        },
                    })
                }}
                onClose={() => {
                    setTypeDrawerOpen(false)
                    setTypeRecord(undefined)
                }}
            />
            <DictDataDrawer
                open={dataDrawerOpen}
                record={dataRecord}
                dictType={selectedType}
                saving={loading.dataSave}
                onSubmit={(values) => {
                    dispatch({
                        type: "dictManage/saveData",
                        payload: values,
                        callback: (ok: boolean) => {
                            if (ok) {
                                setDataDrawerOpen(false)
                                setDataRecord(undefined)
                            }
                        },
                    })
                }}
                onClose={() => {
                    setDataDrawerOpen(false)
                    setDataRecord(undefined)
                }}
            />
        </div>
    )
}

export default DictPage
