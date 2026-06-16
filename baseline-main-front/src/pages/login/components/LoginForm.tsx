import React, { useEffect } from "react"
import { Button, Checkbox, Form, Input, Spin } from "antd"
import type { FormProps } from "antd"
import { useDispatch, useSelector, useNavigate } from "umi"
import utils from "@/utils/utils"
import style from "./../login.less"

const needCaptch = process.env.CAPTCH

interface IFieldType {
    username?: string
    password?: string
    code?: string
    remember?: boolean
}

const LoginForm: React.FC = () => {
    const accountInfos = window.localStorage.getItem("rememberAccountInfo")
    const { btnsLoading = false, captchaImage = "", captchImageLoading = false } = useSelector((state: any) => state.login)
    const [form] = Form.useForm()
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const onFinish: FormProps<IFieldType>["onFinish"] = (value): void => {
        dispatch({
            type: "login/handleLogin",
            payload: value,
            callback: () => navigate("/home", { replace: true }),
        })
    }

    const handleGetNewCaptchImage = () => {
        dispatch({ type: "login/getCaptchaImage" })
    }

    useEffect(() => {
        if (accountInfos) {
            const temps = JSON.parse(accountInfos)
            const { account = "", password = "" } = temps
            form.setFieldValue("username", account)
            form.setFieldValue("password", utils.customDecrypt({ string: password }))
        }
        if (needCaptch) {
            dispatch({ type: "login/getCaptchaImage" })
        }
    }, [])

    return (
        <Form
            name="login"
            form={form}
            wrapperCol={{ span: 24 }}
            initialValues={{ remember: true }}
            onFinish={onFinish}
            size="large"
            autoComplete="off"
        >
            <Form.Item<IFieldType> name="username" rules={[{ required: true, message: "请输入用户名" }]}>
                <Input style={{ height: 56 }} placeholder="请输入用户名" />
            </Form.Item>
            <Form.Item<IFieldType> name="password" rules={[{ required: true, message: "请输入密码" }]}>
                <Input.Password style={{ height: 56 }} placeholder="请输入密码" />
            </Form.Item>

            {needCaptch && (
                <Form.Item<IFieldType> name="code" rules={[{ required: true, message: "请输入验证码" }]}>
                    <div className={style.captchAreas}>
                        <Input style={{ height: 56 }} placeholder="请输入验证码" />
                        <div className={style.captchImages} onClick={handleGetNewCaptchImage} style={{ cursor: "pointer" }}>
                            <Spin spinning={captchImageLoading} style={{ height: 56 }}>
                                <img src={captchaImage} alt="" />
                            </Spin>
                        </div>
                    </div>
                </Form.Item>
            )}

            <Form.Item valuePropName="checked" name="remember" label={null}>
                <Checkbox>记住密码</Checkbox>
            </Form.Item>
            <Form.Item<IFieldType>>
                <Button type="primary" htmlType="submit" className={style.loginBtns} loading={btnsLoading}>
                    登录
                </Button>
            </Form.Item>
        </Form>
    )
}

export default LoginForm
