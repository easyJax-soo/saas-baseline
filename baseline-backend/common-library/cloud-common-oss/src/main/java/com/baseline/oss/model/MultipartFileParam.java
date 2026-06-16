package com.baseline.oss.model;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MultipartFileParam {

    // 文件md5信息
    private String md5;

    // 分块序号
    private int index;

    // 分块大小
    private long size;

    // 分块文件
    private MultipartFile file;

    // 总共多少分块
    private int totalChunks;

    // 文件总大小
    private long totalSize;

    // 原文件名
    private String fileName;

}
