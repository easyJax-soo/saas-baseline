# 字典序列化修改说明

## 修改内容

将所有使用 `@Dict` 注解的字段在 JSON 响应中序列化为字符串格式，无论原始字段类型是 `Integer` 还是 `String`。

## 修改前后对比

### 修改前：
```java
public class SysUserDetailVO {
    @Dict(dictType = "sysSex")
    private Integer sex; // 值为 1
    
    @Dict(dictType = "sysStatus") 
    private Integer status; // 值为 1
}
```

**JSON 响应（修改前）：**
```json
{
  "sex": 1,           // Integer 类型
  "sexText": "男",
  "status": 1,        // Integer 类型  
  "statusText": "启用"
}
```

### 修改后：
```java
public class SysUserDetailVO {
    @Dict(dictType = "sysSex")
    private Integer sex; // 值为 1
    
    @Dict(dictType = "sysStatus")
    private Integer status; // 值为 1
}
```

**JSON 响应（修改后）：**
```json
{
  "sex": "1",         // String 类型
  "sexText": "男",
  "status": "1",      // String 类型
  "statusText": "启用"
}
```

## 优势

1. **类型一致性**：前后端数据类型保持一致，都是字符串
2. **避免类型转换**：前端不需要进行数字到字符串的转换
3. **更好的兼容性**：字符串类型在不同系统间传输更稳定
4. **统一处理**：无论后端字段是 `Integer` 还是 `String`，前端都按字符串处理

## 影响范围

所有使用了 `@Dict` 注解的字段都会受到影响：
- `sysSex` - 性别字段
- `sysStatus` - 状态字段  
- `sysConfigInput` - 输入框类型字段

## 注意事项

1. **前端适配**：前端代码需要相应调整，将这些字段当作字符串处理
2. **数据库存储**：数据库中的存储类型不变，仍然可以是 `INTEGER` 或 `VARCHAR`
3. **验证逻辑**：`@DictVaild` 验证逻辑不受影响，仍然正常工作
4. **向后兼容**：如果前端已经在使用字符串比较，则无需修改
