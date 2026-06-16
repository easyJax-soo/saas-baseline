package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件管理
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file")
@ApiModel(value="SysFile对象", description="文件管理表")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id")
    private Long id;
    @ApiModelProperty(value = "上传文件名")
    private String ossFileName;
    @ApiModelProperty(value = "原文件名")
    private String originFileName;
    @ApiModelProperty(value = "文件后缀名")
    private String fileExtension;
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
    @ApiModelProperty(value = "文件类型")
    private String fileContentType;

}
