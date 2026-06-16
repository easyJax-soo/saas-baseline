package com.baseline.oss.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class OssFile {

    /**
     * 文件地址
     */
    private String link;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件hash值
     */
    public String hash;
    /**
     * 文件大小
     */
    private long length;
    /**
     * 文件上传时间
     */
    private LocalDateTime putTime;
    /**
     * 文件contentType
     */
    private String contentType;
}
