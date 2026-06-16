import React from "react"
import { Result, Button } from "antd"
import { useNavigate } from "umi"

const NotFound: React.FC = () => {
    const navigate = useNavigate()
    return (
        <Result
            status="404"
            title="404"
            subTitle="抱歉，您访问的页面不存在"
            extra={
                <Button type="primary" onClick={() => navigate("/home", { replace: true })}>
                    返回首页
                </Button>
            }
        />
    )
}

export default NotFound
