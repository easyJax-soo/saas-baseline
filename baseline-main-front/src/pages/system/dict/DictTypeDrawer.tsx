import React, { useEffect } from "react"
import { Drawer, Form, Input, Select, Button, Space } from "antd"
import { toNumber } from "@/utils/normalize"
import type { SysDictTypeVO } from "@/utils/types"

interface Props {
    open: boolean
    record?: SysDictTypeVO
    saving: boolean
    onSubmit: (values: any) => void
    onClose: () => void
}

const DictTypeDrawer: React.FC<Props> = ({ open, record, saving, onSubmit, onClose }) => {
    const [form] = Form.useForm()

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        if (record) {
            form.setFieldsValue({ ...record, status: toNumber(record.status) })
        } else {
            form.resetFields()
            form.setFieldsValue({ status: 1 })
        }
    }, [open, record])

    const handleSubmit = () => {
        form.validateFields().then((values) => onSubmit({ ...values, id: record?.id }))
    }

    return (
        <Drawer
            title={record ? "编辑字典类型" : "新增字典类型"}
            width={560}
            open={open}
            onClose={onClose}
            maskClosable={!saving}
            closable={!saving}
            footer={
                <Space>
                    <Button onClick={onClose} disabled={saving}>
                        取消
                    </Button>
                    <Button type="primary" loading={saving} onClick={handleSubmit}>
                        保存
                    </Button>
                </Space>
            }
        >
            <Form form={form} layout="vertical" initialValues={{ status: 1 }}>
                <Form.Item name="name" label="字典名称" rules={[{ required: true, message: "请输入字典名称" }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="code" label="字典编码" rules={[{ required: true, message: "请输入字典编码" }]}>
                    <Input placeholder="sysStatus" disabled={!!record} />
                </Form.Item>
                <Form.Item name="status" label="状态" rules={[{ required: true, message: "请选择状态" }]}>
                    <Select>
                        <Select.Option value={1}>启用</Select.Option>
                        <Select.Option value={0}>禁用</Select.Option>
                    </Select>
                </Form.Item>
                <Form.Item name="remark" label="备注">
                    <Input.TextArea rows={3} />
                </Form.Item>
            </Form>
        </Drawer>
    )
}

export default DictTypeDrawer
