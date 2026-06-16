import React from "react"

const Home: React.FC = () => {
    return (
        <div>
            <h2>欢迎使用 Baseline 多租户基线系统</h2>
            <p style={{ color: "rgba(26, 26, 26, 0.6)", marginTop: 16 }}>
                这是脚手架的占位首页。后续阶段会接入：
            </p>
            <ul style={{ color: "rgba(26, 26, 26, 0.6)", lineHeight: 2 }}>
                <li>系统管理：用户、角色、菜单、部门、岗位、字典</li>
                <li>多租户：租户管理、租户切换</li>
                <li>运维：操作日志、系统配置</li>
            </ul>
        </div>
    )
}

export default Home
