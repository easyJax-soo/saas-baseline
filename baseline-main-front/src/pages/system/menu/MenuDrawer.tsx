import React, { useEffect } from "react"
import { Drawer, Form, Input, TreeSelect, Radio, InputNumber, Switch, Button, Space, Spin, Select } from "antd"
import { useDispatch, useSelector } from "umi"
import { toNumber } from "@/utils/normalize"

interface Props {
    open: boolean
    id?: number
    parentId?: number
    onClose: () => void
}

const MenuDrawer: React.FC<Props> = ({ open, id, parentId, onClose }) => {
    const dispatch = useDispatch()
    const [form] = Form.useForm()
    const { detail, loading } = useSelector((s: any) => s.menu)
    const dict = useSelector((s: any) => s.dict)
    const menuType = Form.useWatch("type", form)

    const isEdit = !!id
    const saving = loading.save

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        dispatch({ type: "dict/loadMenuTree" })
        if (id) {
            dispatch({ type: "menu/fetchDetail", payload: id })
        }
    }, [open, id])

    useEffect(() => {
        if (isEdit && detail) {
            form.setFieldsValue({
                ...detail,
                parentId: toNumber(detail.parentId),
                sortNo: toNumber(detail.sortNo),
                visible: toNumber(detail.visible),
                status: toNumber(detail.status),
                cache: toNumber(detail.cache),
            })
        } else if (!isEdit) {
            form.resetFields()
            form.setFieldsValue({ type: "M", status: 1, visible: 1, cache: 1, sortNo: 0, parentId: parentId || 0 })
        }
    }, [detail, isEdit, parentId])

    const handleSubmit = () => {
        form.validateFields().then((values) => {
            const payload: any = { ...values, id: isEdit ? id : undefined }
            if (values.type === "M") {
                payload.path = ""
                payload.component = ""
                payload.key = ""
            }
            if (values.type === "F") {
                payload.path = ""
                payload.component = ""
            }
            dispatch({
                type: "menu/saveOrUpdate",
                payload,
                callback: (ok: boolean) => {
                    if (ok) onClose()
                },
            })
        })
    }

    const buildTreeData = (nodes: any[]): any[] =>
        [{ title: "根目录", key: 0, value: 0 }].concat(
            nodes?.map((n: any) => ({
                title: n.name,
                key: n.id,
                value: n.id,
                disabled: n.id === id,
                children: buildTreeData(n.children || []),
            })) || []
        )

    return (
        <Drawer
            title={isEdit ? "编辑菜单" : "新增菜单"}
            width={800}
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
                <Form form={form} layout="vertical" initialValues={{ type: "M", status: 1, visible: 1, cache: 1, sortNo: 0 }}>
                    <Form.Item name="type" label="菜单类型" rules={[{ required: true }]}>
                        <Radio.Group>
                            <Radio value="M">目录 (M)</Radio>
                            <Radio value="C">页面 (C)</Radio>
                            <Radio value="F">按钮 (F)</Radio>
                        </Radio.Group>
                    </Form.Item>
                    <Form.Item name="parentId" label="上级菜单" rules={[{ required: true }]}>
                        <TreeSelect treeData={buildTreeData(dict.menuTree)} placeholder="请选择上级菜单" allowClear treeDefaultExpandAll />
                    </Form.Item>
                    <Form.Item name="name" label="菜单名称" rules={[{ required: true, message: "请输入菜单名称" }]}>
                        <Input />
                    </Form.Item>
                    {menuType === "C" && (
                        <>
                            <Form.Item name="path" label="路由地址" rules={[{ required: true, message: "请输入路由地址" }]}>
                                <Input placeholder="/system/user" />
                            </Form.Item>
                            <Form.Item name="component" label="组件路径" rules={[{ required: true, message: "请输入组件路径" }]}>
                                <Input placeholder="system/user/index" />
                            </Form.Item>
                        </>
                    )}
                    {menuType === "F" && (
                        <Form.Item name="key" label="权限标识" rules={[{ required: true, message: "请输入权限标识" }]}>
                            <Input placeholder="system:user:add" />
                        </Form.Item>
                    )}
                    {menuType !== "F" && (
                        <Form.Item name="icon" label="图标">
                            <Input placeholder="FolderOutlined" />
                        </Form.Item>
                    )}
                    <Form.Item name="sortNo" label="排序号">
                        <InputNumber style={{ width: "100%" }} />
                    </Form.Item>
                    <Form.Item name="status" label="状态">
                        <Select>
                            <Select.Option value={1}>启用</Select.Option>
                            <Select.Option value={0}>禁用</Select.Option>
                        </Select>
                    </Form.Item>
                    {menuType !== "F" && (
                        <Form.Item name="visible" label="是否可见" getValueFromEvent={(v: boolean) => (v ? 1 : 0)} getValueProps={(v: number | string) => ({ checked: toNumber(v) === 1 })}>
                            <Switch checkedChildren="可见" unCheckedChildren="隐藏" />
                        </Form.Item>
                    )}
                    {menuType === "C" && (
                        <Form.Item name="cache" label="是否缓存" getValueFromEvent={(v: boolean) => (v ? 1 : 0)} getValueProps={(v: number | string) => ({ checked: toNumber(v) === 1 })}>
                            <Switch checkedChildren="缓存" unCheckedChildren="不缓存" />
                        </Form.Item>
                    )}
                    <Form.Item name="projectCode" label="项目码">
                        <Select allowClear>
                            <Select.Option value="BASELINE">BASELINE</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item name="remark" label="备注">
                        <Input.TextArea rows={2} />
                    </Form.Item>
                </Form>
            </Spin>
        </Drawer>
    )
}

export default MenuDrawer
