package com.baseline.system.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 消息中心 exportVO
 *
 * @author bryant
 * @since 2025-11-25
 */

@ApiModel(value = "SysMessageInfoImportDTO", description = "消息中心")
@Data
public class SysMessageInfoImportDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ExcelProperty(value = "ID")
    private Long id;
    @ExcelProperty(value = "对应事项id")
    private Long contactId;
    @ExcelProperty(value = "消息大类")
    private Integer oneCategory;
    @ExcelProperty(value = "消息小类")
    private Integer twoCategory;
    @ExcelProperty(value = "阅读状态")
    private Integer readStatus;
    @ExcelProperty(value = "消息接收人")
    private Long userId;
    @ExcelProperty(value = "消息内容")
    private String content;
    @ExcelProperty(value = "删除标识")
    private Byte deleted;
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ExcelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
