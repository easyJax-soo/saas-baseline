import React from "react"
import { Button } from "antd"
import type { ButtonProps } from "antd"
import { useSelector } from "umi"

type PermButtonProps = ButtonProps & { perm?: string }

const PermButton: React.FC<PermButtonProps> = ({ perm, children, ...rest }) => {
    const permissions = useSelector((s: any) => s.app.permissions as string[])
    if (perm && !permissions.includes(perm)) return null
    return <Button {...rest}>{children}</Button>
}

export default PermButton
