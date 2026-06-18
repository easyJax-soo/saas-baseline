export const toNumber = (value: unknown): number | undefined => {
    if (value === null || value === undefined || value === "") return undefined
    const num = Number(value)
    return Number.isNaN(num) ? undefined : num
}

export const toNumberArray = (values: unknown): number[] => {
    if (!Array.isArray(values)) return []
    return values.map(toNumber).filter((value): value is number => value !== undefined)
}

export const toStringId = (value: unknown): string | undefined => {
    if (value === null || value === undefined || value === "") return undefined
    return String(value)
}

export const toStringIdArray = (values: unknown): string[] => {
    if (!Array.isArray(values)) return []
    return values.map(toStringId).filter((value): value is string => value !== undefined)
}
