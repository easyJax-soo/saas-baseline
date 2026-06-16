package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "SimpleRoleVO",description = "简单角色信息")
@Data
public class SimpleRoleVO {
    Long id;
    String name;
    String key;
}
