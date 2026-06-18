import React, { useEffect } from "react"
import { Drawer, Form, Input, Select, TreeSelect, Switch, Radio, Button, Space, Spin, Divider } from "antd"
import { useDispatch, useSelector } from "umi"
import { toNumber, toStringIdArray } from "@/utils/normalize"

interface Props {
    open: boolean
    id?: number
    onClose: () => void
}

const UserDrawer: React.FC<Props> = ({ open, id, onClose }) => {
    const dispatch = useDispatch()
    const [form] = Form.useForm()
    const { detail, loading } = useSelector((s: any) => s.user)
    const dict = useSelector((s: any) => s.dict)

    const isEdit = !!id
    const saving = loading.save

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        dispatch({ type: "dict/loadDeptTree" })
        dispatch({ type: "dict/loadPostList" })
        dispatch({ type: "dict/loadSimpleRoles" })
        if (id) {
            dispatch({ type: "user/fetchDetail", payload: id })
        }
    }, [open, id])

    useEffect(() => {
        if (isEdit && detail) {
            form.setFieldsValue({
                ...detail,
                password: undefined,
                deptId: detail.deptId ? String(detail.deptId) : undefined,
                sex: toNumber(detail.sex),
                status: toNumber(detail.status),
                roleIds: toStringIdArray(detail.roleIds),
                postIds: toStringIdArray(detail.postIds),
            })
        } else if (!isEdit) {
            form.resetFields()
        }
    }, [detail, isEdit])

    const handleSubmit = () => {
        form.validateFields().then((values) => {
            const payload = isEdit ? { ...values, id } : values
            dispatch({
                type: "user/saveOrUpdate",
                payload,
                callback: (ok: boolean) => {
                    if (ok) onClose()
                },
            })
        })
    }

    const buildTreeData = (nodes: any[]): any[] => nodes?.map((n: any) => ({ title: n.name, key: String(n.id), value: String(n.id), children: buildTreeData(n.children || []) })) || []

    return (
        <Drawer
            title={isEdit ? "编辑用户" : "新增用户"}
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
                <Form form={form} layout="vertical" initialValues={{ status: 1, sex: 1 }}>
                    <Divider orientation="left" plain>
                        基本信息
                    </Divider>
                    <Form.Item
                        name="account"
                        label="账号"
                        rules={[
                            { required: true, message: "请输入账号" },
                            { pattern: /^[a-zA-Z0-9_]+$/, message: "仅允许字母数字下划线" },
                        ]}
                    >
                        <Input disabled={isEdit} placeholder="登录账号" />
                    </Form.Item>
                    <Form.Item name="name" label="姓名" rules={[{ required: true, message: "请输入姓名" }]}>
                        <Input />
                    </Form.Item>
                    {!isEdit && (
                        <Form.Item
                            name="password"
                            label="密码"
                            rules={[
                                { required: true, message: "请输入密码" },
                                { min: 6, message: "密码至少6位" },
                            ]}
                        >
                            <Input.Password />
                        </Form.Item>
                    )}
                    <Form.Item name="phone" label="手机号" rules={[{ pattern: /^1[3-9]\d{9}$/, message: "手机号格式不正确" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="email" label="邮箱" rules={[{ type: "email", message: "邮箱格式不正确" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="sex" label="性别">
                        <Radio.Group>
                            <Radio value={1}>男</Radio>
                            <Radio value={2}>女</Radio>
                            <Radio value={0}>未知</Radio>
                        </Radio.Group>
                    </Form.Item>
                    <Divider orientation="left" plain>
                        组织信息
                    </Divider>
                    <Form.Item name="deptId" label="部门">
                        <TreeSelect treeData={buildTreeData(dict.deptTree)} placeholder="请选择部门" allowClear treeDefaultExpandAll />
                    </Form.Item>
                    <Divider orientation="left" plain>
                        角色与岗位
                    </Divider>
                    <Form.Item name="roleIds" label="角色">
                        <Select mode="multiple" placeholder="请选择角色" allowClear options={dict.simpleRoles?.map((r: any) => ({ label: r.name, value: String(r.id) }))} />
                    </Form.Item>
                    <Form.Item name="postIds" label="岗位">
                        <Select mode="multiple" placeholder="请选择岗位" allowClear options={dict.postList?.map((p: any) => ({ label: p.name, value: String(p.id) }))} />
                    </Form.Item>
                    <Divider orientation="left" plain>
                        其他
                    </Divider>
                    <Form.Item name="status" label="状态" getValueFromEvent={(v: boolean) => (v ? 1 : 0)} getValueProps={(v: number | string) => ({ checked: toNumber(v) === 1 })}>
                        <Switch checkedChildren="启用" unCheckedChildren="禁用" />
                    </Form.Item>
                    <Form.Item name="remark" label="备注">
                        <Input.TextArea rows={3} />
                    </Form.Item>
                </Form>
            </Spin>
        </Drawer>
    )
}

export default UserDrawer
