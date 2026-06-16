import React from "react"
import { Result } from "antd"

const Forbidden: React.FC = () => {
    return (
        <div style={{ padding: 48 }}>
            <Result status="403" title="403" subTitle="您没有访问该页面的权限" />
        </div>
    )
}

export default Forbidden
