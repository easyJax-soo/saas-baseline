import React, { useEffect } from "react"
import { Drawer, Form, Input, TreeSelect, InputNumber, Button, Space, Spin, Select } from "antd"
import { useDispatch, useSelector } from "umi"
import { toNumber } from "@/utils/normalize"

interface Props {
    open: boolean
    id?: number
    parentId?: number
    onClose: () => void
}

const PermissionDrawer: React.FC<Props> = ({ open, id, parentId, onClose }) => {
    const dispatch = useDispatch()
    const [form] = Form.useForm()
    const { detail, loading } = useSelector((s: any) => s.permission)
    const { tree } = useSelector((s: any) => s.permission)

    const isEdit = !!id
    const saving = loading.save

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        if (id) dispatch({ type: "permission/fetchDetail", payload: id })
    }, [open, id])

    useEffect(() => {
        if (isEdit && detail) {
            form.setFieldsValue({
                ...detail,
                parentId: detail.parentId === undefined || detail.parentId === null ? "0" : String(detail.parentId),
                sortNo: toNumber(detail.sortNo),
            })
        } else if (!isEdit) {
            form.resetFields()
            form.setFieldsValue({ parentId: String(parentId ?? 0), sortNo: 0, projectCode: "system" })
        }
    }, [detail, isEdit, parentId])

    const handleSubmit = () => {
        form.validateFields().then((values) => {
            dispatch({
                type: "permission/saveOrUpdate",
                payload: { ...values, parentId: toNumber(values.parentId), id: isEdit ? id : undefined },
                callback: (ok: boolean) => {
                    if (ok) onClose()
                },
            })
        })
    }

    const buildTreeData = (nodes: any[]): any[] =>
        [{ title: "根权限", key: "0", value: "0" }].concat(
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
            title={isEdit ? "编辑权限" : "新增权限"}
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
                <Form form={form} layout="vertical" initialValues={{ parentId: "0", sortNo: 0, projectCode: "system" }}>
                    <Form.Item name="parentId" label="上级权限" rules={[{ required: true, message: "请选择上级权限" }]}>
                        <TreeSelect treeData={buildTreeData(tree)} placeholder="请选择上级权限" allowClear treeDefaultExpandAll />
                    </Form.Item>
                    <Form.Item name="name" label="权限名称" rules={[{ required: true, message: "请输入权限名称" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="permission" label="权限标识" rules={[{ required: true, message: "请输入权限标识" }]}>
                        <Input placeholder="system:user:save" />
                    </Form.Item>
                    <Form.Item name="projectCode" label="项目码">
                        <Select allowClear>
                            <Select.Option value="system">system</Select.Option>
                            <Select.Option value="BASELINE">BASELINE</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item name="sortNo" label="排序号">
                        <InputNumber style={{ width: "100%" }} />
                    </Form.Item>
                </Form>
            </Spin>
        </Drawer>
    )
}

export default PermissionDrawer
