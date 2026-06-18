import React, { useEffect } from "react"
import { Drawer, Form, Input, InputNumber, Select, Button, Space, Spin } from "antd"
import { useDispatch, useSelector } from "umi"
import { toNumber } from "@/utils/normalize"

interface Props {
    open: boolean
    id?: number
    onClose: () => void
}

const PostDrawer: React.FC<Props> = ({ open, id, onClose }) => {
    const dispatch = useDispatch()
    const [form] = Form.useForm()
    const { detail, loading } = useSelector((s: any) => s.post)

    const isEdit = !!id
    const saving = loading.save

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        if (id) dispatch({ type: "post/fetchDetail", payload: id })
    }, [open, id])

    useEffect(() => {
        if (isEdit && detail) {
            form.setFieldsValue({
                ...detail,
                sortNo: toNumber(detail.sortNo),
                status: toNumber(detail.status),
            })
        } else if (!isEdit) {
            form.resetFields()
            form.setFieldsValue({ sortNo: 0, status: 1 })
        }
    }, [detail, isEdit])

    const handleSubmit = () => {
        form.validateFields().then((values) => {
            dispatch({
                type: "post/saveOrUpdate",
                payload: { ...values, id: isEdit ? id : undefined },
                callback: (ok: boolean) => {
                    if (ok) onClose()
                },
            })
        })
    }

    return (
        <Drawer
            title={isEdit ? "编辑岗位" : "新增岗位"}
            width={640}
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
            <Spin spinning={isEdit && loading.detail}>
                <Form form={form} layout="vertical" initialValues={{ sortNo: 0, status: 1 }}>
                    <Form.Item name="name" label="岗位名称" rules={[{ required: true, message: "请输入岗位名称" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="code" label="岗位编码" rules={[{ required: true, message: "请输入岗位编码" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="sortNo" label="排序号">
                        <InputNumber style={{ width: "100%" }} />
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
            </Spin>
        </Drawer>
    )
}

export default PostDrawer
