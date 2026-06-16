const utils = {
    // 自定义加密（用于"记住密码"场景）
    customEncrypt: ({ keyStr = "1:2", string = null as any }): string => {
        if (!keyStr || !string || !(keyStr.indexOf(":") > -1)) return ""
        const keys: any = keyStr.split(":")
        keys[0] = parseInt(keys[0], 10)
        keys[1] = parseInt(keys[1], 10)
        const result: string[] = []
        let start = (string as string).substr(keys[0], (string as string).length)
        start = start.split("").reverse().join("")
        start += (string as string).substr(0, keys[0])
        const data = start.split("")
        for (let i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                result.push(String.fromCharCode(data[i].charCodeAt(0) - keys[1]))
            } else {
                result.push(String.fromCharCode(data[i].charCodeAt(0) + keys[1]))
            }
        }
        return result.join("")
    },
    customDecrypt: ({ keyStr = "1:2", string = null as any }): string => {
        if (!keyStr || !string || !(keyStr.indexOf(":") > -1)) return ""
        let result = ""
        const keys: any = keyStr.split(":")
        keys[0] = parseInt(keys[0], 10)
        keys[1] = parseInt(keys[1], 10)
        let temp
        try {
            for (let i = 0; i < (string as string).length; i++) {
                if (i % 2 == 1) {
                    result += String.fromCharCode((string as string).charCodeAt(i) - keys[1])
                } else {
                    result += String.fromCharCode((string as string).charCodeAt(i) + keys[1])
                }
            }
            temp = result.substr(0, result.length - keys[0])
            result = result.substr(result.length - keys[0], keys[0])
            result += temp.split("").reverse().join("")
        } catch (e) {
            result = ""
        }
        return result
    },
}

export default utils
