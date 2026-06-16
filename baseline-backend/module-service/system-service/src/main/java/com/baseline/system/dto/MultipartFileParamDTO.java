package com.baseline.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MultipartFileParamDTO {

    @ApiModelProperty(value = "文件上传id")
    private String taskId;

    @ApiModelProperty(value = "分块编号")
    private int chunkNumber;

    @ApiModelProperty(value = "块大小")
    private long chunkSize;


    @ApiModelProperty(value = "总块数")
    private int totalChunks;

    @ApiModelProperty(value = "分块文件ID")
    private String identifier;

    @ApiModelProperty(value = "分块文件地址")
    private MultipartFile file;

    @ApiModelProperty(value = "总大小")
    private long totalSize;

}
