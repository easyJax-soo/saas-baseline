import { useSelector } from "umi"

export const usePerm = () => {
    const permissions = useSelector((s: any) => s.app.permissions as string[])
    return (code: string) => permissions.includes(code)
}
