import React, { useEffect, useState } from "react"
import { Drawer, Form, Input, Select, Button, Space, Spin, Tabs, Tree, Checkbox } from "antd"
import { useDispatch, useSelector } from "umi"
import DataScopeRadio from "./DataScopeRadio"
import { DataScope } from "@/constants/dataScope"
import { toNumber, toStringIdArray } from "@/utils/normalize"

interface Props {
    open: boolean
    id?: number
    onClose: () => void
}

const RoleDrawer: React.FC<Props> = ({ open, id, onClose }) => {
    const dispatch = useDispatch()
    const [form] = Form.useForm()
    const { detail, loading } = useSelector((s: any) => s.role)
    const dict = useSelector((s: any) => s.dict)
    const dataScopeWatch = Form.useWatch("dataScope", form)
    const dataScope: number = dataScopeWatch ?? DataScope.ALL
    const [checkedMenuKeys, setCheckedMenuKeys] = useState<string[]>([])
    const [checkedPermKeys, setCheckedPermKeys] = useState<string[]>([])
    const [checkedDeptKeys, setCheckedDeptKeys] = useState<string[]>([])
    const [selectedProjects, setSelectedProjects] = useState<string[]>([])

    const isEdit = !!id
    const saving = loading.save

    useEffect(() => {
        if (!open) {
            form.resetFields()
            return
        }
        dispatch({ type: "dict/loadMenuTree" })
        dispatch({ type: "dict/loadPermTree" })
        dispatch({ type: "dict/loadDeptTree" })
        dispatch({ type: "dict/loadProjectGroups" })
        if (id) dispatch({ type: "role/fetchDetail", payload: id })
    }, [open, id])

    useEffect(() => {
        if (isEdit && detail) {
            form.setFieldsValue({
                name: detail.name,
                key: detail.key,
                status: toNumber(detail.status),
                dataScope: toNumber(detail.dataScope),
                remark: detail.remark,
            })
            setCheckedMenuKeys(toStringIdArray(detail.menuIds))
            setCheckedPermKeys(toStringIdArray(detail.permissionIds))
            setCheckedDeptKeys(toStringIdArray(detail.deptIds))
            setSelectedProjects(detail.projectCodes || [])
        } else if (!isEdit) {
            form.resetFields()
            setCheckedMenuKeys([])
            setCheckedPermKeys([])
            setCheckedDeptKeys([])
            setSelectedProjects([])
        }
    }, [detail, isEdit])

    const handleSubmit = () => {
        form.validateFields().then((values) => {
            const payload = {
                ...values,
                id: isEdit ? id : undefined,
                menuIds: checkedMenuKeys,
                permissionIds: checkedPermKeys,
                deptIds: dataScope === DataScope.CUSTOM ? checkedDeptKeys : [],
                projectCodes: selectedProjects,
            }
            dispatch({
                type: "role/saveOrUpdate",
                payload,
                callback: (ok: boolean) => {
                    if (ok) onClose()
                },
            })
        })
    }

    const buildTreeData = (nodes: any[]): any[] => nodes?.map((n: any) => ({ title: n.name, key: String(n.id), children: buildTreeData(n.children || []) })) || []

    return (
        <Drawer
            title={isEdit ? "编辑角色" : "新增角色"}
            width={960}
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
                <Form form={form} layout="vertical" initialValues={{ status: 1, dataScope: DataScope.ALL }}>
                    <Form.Item name="name" label="角色名称" rules={[{ required: true, message: "请输入角色名称" }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name="key"
                        label="角色标识"
                        rules={[
                            { required: true, message: "请输入角色标识" },
                            { pattern: /^[a-zA-Z_][a-zA-Z0-9_]*$/, message: "仅允许字母数字下划线" },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name="status" label="状态">
                        <Select>
                            <Select.Option value={1}>启用</Select.Option>
                            <Select.Option value={0}>禁用</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item name="dataScope" label="数据范围" rules={[{ required: true, message: "请选择数据范围" }]}>
                        <DataScopeRadio />
                    </Form.Item>
                    <Form.Item name="remark" label="备注">
                        <Input.TextArea rows={2} />
                    </Form.Item>
                </Form>

                <Tabs
                    items={[
                        {
                            key: "menu",
                            label: "菜单权限",
                            children: (
                                <Tree
                                    checkable
                                    treeData={buildTreeData(dict.menuTree)}
                                    checkedKeys={checkedMenuKeys}
                                    onCheck={(keys: any) => setCheckedMenuKeys(keys)}
                                    defaultExpandAll
                                />
                            ),
                        },
                        {
                            key: "perm",
                            label: "按钮权限",
                            children: (
                                <Tree
                                    checkable
                                    treeData={buildTreeData(dict.permTree)}
                                    checkedKeys={checkedPermKeys}
                                    onCheck={(keys: any) => setCheckedPermKeys(keys)}
                                    defaultExpandAll
                                />
                            ),
                        },
                        ...(dataScope === DataScope.CUSTOM
                            ? [
                                  {
                                      key: "dept",
                                      label: "数据部门",
                                      children: (
                                          <Tree
                                              checkable
                                              treeData={buildTreeData(dict.deptTree)}
                                              checkedKeys={checkedDeptKeys}
                                              onCheck={(keys: any) => setCheckedDeptKeys(keys)}
                                              defaultExpandAll
                                          />
                                      ),
                                  },
                              ]
                            : []),
                        {
                            key: "project",
                            label: "项目权限",
                            children: dict.projectGroups?.map((g: any) => (
                                <div key={g.type} style={{ marginBottom: 12 }}>
                                    <div style={{ fontWeight: 500, marginBottom: 4 }}>{g.type}</div>
                                    <Checkbox.Group
                                        options={g.projects?.map((p: any) => ({ label: p.name, value: p.code }))}
                                        value={selectedProjects}
                                        onChange={(vals: any) => setSelectedProjects(vals)}
                                    />
                                </div>
                            )),
                        },
                    ]}
                />
            </Spin>
        </Drawer>
    )
}

export default RoleDrawer
