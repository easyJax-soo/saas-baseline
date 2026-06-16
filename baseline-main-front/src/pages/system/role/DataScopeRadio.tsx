import React from "react"
import { Radio } from "antd"
import { DataScope, DATA_SCOPE_TEXT } from "@/constants/dataScope"

interface Props {
    value?: number
    onChange?: (v: number) => void
}

const DataScopeRadio: React.FC<Props> = ({ value, onChange }) => (
    <Radio.Group value={value} onChange={(e) => onChange?.(e.target.value)}>
        {Object.values(DataScope)
            .filter((v) => typeof v === "number")
            .map((v) => (
                <Radio key={v as number} value={v}>
                    {DATA_SCOPE_TEXT[v as keyof typeof DATA_SCOPE_TEXT]}
                </Radio>
            ))}
    </Radio.Group>
)

export default DataScopeRadio
