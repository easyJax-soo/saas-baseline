import React from "react"
import { Modal, Form, Input, message } from "antd"
import { useDispatch } from "umi"

interface Props {
    userId?: number
    onClose: () => void
}

const ResetPwdModal: React.FC<Props> = ({ userId, onClose }) => {
    const dispatch = useDispatch()
    const [form] = Form.useForm()

    const handleOk = () => {
        form.validateFields().then((values) => {
            if (values.newPw !== values.confirmPw) {
                message.error("两次密码输入不一致")
                return
            }
            dispatch({
                type: "user/resetPwd",
                payload: { userId, newPw: values.newPw },
                callback: (ok: boolean) => {
                    if (ok) {
                        form.resetFields()
                        onClose()
                    }
                },
            })
        })
    }

    return (
        <Modal
            title="重置密码"
            open={!!userId}
            onOk={handleOk}
            onCancel={() => {
                form.resetFields()
                onClose()
            }}
            destroyOnClose
        >
            <Form form={form} layout="vertical">
                <Form.Item
                    name="newPw"
                    label="新密码"
                    rules={[
                        { required: true, message: "请输入新密码" },
                        { min: 6, message: "至少6位" },
                    ]}
                >
                    <Input.Password />
                </Form.Item>
                <Form.Item name="confirmPw" label="确认密码" rules={[{ required: true, message: "请确认新密码" }]}>
                    <Input.Password />
                </Form.Item>
            </Form>
        </Modal>
    )
}

export default ResetPwdModal
