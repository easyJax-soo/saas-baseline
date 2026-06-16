
cloud-common-mybatis 主要用于sql相关功能。

## 多租户组件使用

1. 引入组件
 ```
  <dependency>
      <groupId>com.baseline.library</groupId>
      <artifactId>cloud-common-mybatis</artifactId>
     <version>lastest version</version>
  </dependency>
 ```
2. 开启租户
```
tenant:
  # 是否开启租户模式
  enable: true
   # 需要排除的多租户的表
  exclusionTable:
    - "sys_config"
    - "sys_dict_data"
    - "sys_dict_type"
    - "sys_job"
    - "sys_job_log"
    - "sys_menu"
    - "sys_permission"
    - "sys_tenant"
    - "sys_tenant_menu"
    - "sys_tenant_permission"
    - "sys_tenant_user"
    - "sys_menu"
    - "sys_logininfor"
  # 租户字段名称
  column: tenant_id
```
3. 数据权限
- 全部数据权限 [不拼接sql]
- 自定义数据权限 [在原数据上拼接 ```and (dept_id in (?))```]
- 本部门数据权限 [在原sql拼接上 ```and (dept_id = ?)``` ]
- 本部门及以下数据权限 [在原sql拼接上 ```and (dept_id in (?))``` ]
- 仅本人数据权限 [在原sql拼接上 ```and (user_id = ?)``` ]

<u>如果多个角色的数据权限不同，那么每个角色的数据权限是逻辑或(OR)的关系 [在原sql拼接上```AND ( u.dept_id IN (?) OR u.dept_id IN (?) OR u.user_id = ? ) ```]</u>

*\*DataColumn注解的 key = deptName是部门，value = 为部门字段*

*\*DataColumn注解的 key = userName是用户，value = 为用户字段*

*\*字段根据实际表紫字段进行定义*

```
   @DataPermission({
      @DataColumn(key = "deptName", value = "u.dept_id"),
      @DataColumn(key = "userName", value = "u.id"),
   })
```