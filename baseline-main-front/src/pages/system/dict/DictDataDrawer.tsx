import React, { useEffect } from "react"
import { Drawer, Form, Input, InputNumber, Select, Button, Space } from "antd"
import { toNumber } from "@/utils/normalize"
import type { SysDictDataVO, SysDictTypeVO } from "@/utils/types"

interface Props {
    open: boolean
    record?: SysDictDataVO
    dictType?: SysDictTypeVO | null
    saving: boolean
    onSubmit: (values: any) => void
    onClose: () => void
}

const DictDataDrawer: React.FC<Props> = ({ open, record, dictType, saving, onSubmit, onClose }) => {
    const [form] = Form.useForm()

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        if (record) {
            form.setFieldsValue({
                ...record,
                sortNo: toNumber(record.sortNo),
                isDefault: toNumber(record.isDefault),
                status: toNumber(record.status),
            })
        } else {
            form.resetFields()
            form.setFieldsValue({ code: dictType?.code, sortNo: 0, isDefault: 0, status: 1 })
        }
    }, [open, record, dictType?.code])

    const handleSubmit = () => {
        form.validateFields().then((values) => onSubmit({ ...values, code: dictType?.code, id: record?.id }))
    }

    return (
        <Drawer
            title={record ? "编辑字典数据" : "新增字典数据"}
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
            <Form form={form} layout="vertical" initialValues={{ sortNo: 0, isDefault: 0, status: 1 }}>
                <Form.Item name="code" label="字典编码">
                    <Input disabled />
                </Form.Item>
                <Form.Item name="label" label="字典标签" rules={[{ required: true, message: "请输入字典标签" }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="value" label="字典键值" rules={[{ required: true, message: "请输入字典键值" }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="sortNo" label="排序号">
                    <InputNumber style={{ width: "100%" }} />
                </Form.Item>
                <Form.Item name="isDefault" label="是否默认">
                    <Select>
                        <Select.Option value={1}>是</Select.Option>
                        <Select.Option value={0}>否</Select.Option>
                    </Select>
                </Form.Item>
                <Form.Item name="status" label="状态">
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

export default DictDataDrawer
