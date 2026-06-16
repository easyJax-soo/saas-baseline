import React from "react"
import style from "./login.less"
import LoginForm from "./components/LoginForm"

const Login: React.FC = () => {
    return (
        <div className={style.loginBg}>
            <div className={style.TopTitle}>
                <div className={style.logo}></div>
                <div className={style.titleContent}>
                    <h1 className={style.mainTitle}>Baseline 多租户基线系统</h1>
                    <h2 className={style.subTitle}>后台管理平台</h2>
                </div>
            </div>
            <div className={style.loginAreas}>
                <div className={style.rightArea}>
                    <div className={style.formTitle}>账户登录</div>
                    <LoginForm />
                </div>
            </div>
        </div>
    )
}

export default Login
