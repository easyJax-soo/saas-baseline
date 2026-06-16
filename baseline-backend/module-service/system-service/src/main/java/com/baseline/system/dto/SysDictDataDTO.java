package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字典数据表 sys_dict_data
 * 
 * @author ruoyi
 */
@Data
public class SysDictDataDTO
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典ID")
    private Long id;

    @ApiModelProperty(value = "字典排序")
    private Integer sortNo;

    @ApiModelProperty(value = "字典标签")
    private String label;

    @ApiModelProperty(value = "字典键值")
    private String value;

    @ApiModelProperty(value = "字典类型编码")
    private String code;

    @ApiModelProperty(value = "是否默认")
    private Integer isDefault;

    @ApiModelProperty(value = "状态,1：正常 0：禁用")
    @Dict(dictType = "sysStatus")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
