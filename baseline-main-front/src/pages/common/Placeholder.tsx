import React from "react"
import { useLocation } from "umi"
import { Empty } from "antd"

const Placeholder: React.FC = () => {
    const { pathname } = useLocation()
    return (
        <div style={{ padding: 48 }}>
            <Empty description={`该页面建设中：${pathname}`} />
        </div>
    )
}

export default Placeholder
