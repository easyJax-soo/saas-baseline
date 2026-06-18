import React, { useEffect } from "react"
import { Drawer, Form, Input, TreeSelect, InputNumber, Select, Button, Space, Spin } from "antd"
import { useDispatch, useSelector } from "umi"
import { toNumber } from "@/utils/normalize"

interface Props {
    open: boolean
    id?: number
    parentId?: number
    onClose: () => void
}

const DeptDrawer: React.FC<Props> = ({ open, id, parentId, onClose }) => {
    const dispatch = useDispatch()
    const [form] = Form.useForm()
    const { list, detail, loading } = useSelector((s: any) => s.dept)

    const isEdit = !!id
    const saving = loading.save

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        if (id) dispatch({ type: "dept/fetchDetail", payload: id })
    }, [open, id])

    useEffect(() => {
        if (isEdit && detail) {
            form.setFieldsValue({
                ...detail,
                parentId: detail.parentId === undefined || detail.parentId === null ? "0" : String(detail.parentId),
                sortNo: toNumber(detail.sortNo),
                status: toNumber(detail.status),
            })
        } else if (!isEdit) {
            form.resetFields()
            form.setFieldsValue({ parentId: String(parentId ?? 0), sortNo: 0, status: 1 })
        }
    }, [detail, isEdit, parentId])

    const handleSubmit = () => {
        form.validateFields().then((values) => {
            dispatch({
                type: "dept/saveOrUpdate",
                payload: { ...values, parentId: toNumber(values.parentId), id: isEdit ? id : undefined },
                callback: (ok: boolean) => {
                    if (ok) onClose()
                },
            })
        })
    }

    const buildTreeData = (nodes: any[]): any[] =>
        [{ title: "根部门", key: "0", value: "0" }].concat(
            nodes?.map((n: any) => ({
                title: n.name,
                key: String(n.id),
                value: String(n.id),
                disabled: n.id === id,
                children: buildTreeData(n.children || []),
            })) || []
        )

    return (
        <Drawer
            title={isEdit ? "编辑部门" : "新增部门"}
            width={720}
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
                <Form form={form} layout="vertical" initialValues={{ parentId: "0", sortNo: 0, status: 1 }}>
                    <Form.Item name="parentId" label="上级部门" rules={[{ required: true, message: "请选择上级部门" }]}>
                        <TreeSelect treeData={buildTreeData(list)} placeholder="请选择上级部门" allowClear treeDefaultExpandAll />
                    </Form.Item>
                    <Form.Item name="name" label="部门名称" rules={[{ required: true, message: "请输入部门名称" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="code" label="部门编码" rules={[{ required: true, message: "请输入部门编码" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="leaderUserId" label="负责人用户ID">
                        <InputNumber style={{ width: "100%" }} />
                    </Form.Item>
                    <Form.Item name="sortNo" label="排序号">
                        <InputNumber style={{ width: "100%" }} />
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
            </Spin>
        </Drawer>
    )
}

export default DeptDrawer
