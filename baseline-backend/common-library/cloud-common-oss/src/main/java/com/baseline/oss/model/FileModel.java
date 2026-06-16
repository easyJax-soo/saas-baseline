package com.baseline.oss.model;


import lombok.Data;


/**
 * 文件上传之后的结果返回
 */
@Data
public class FileModel {

    /** 域名 */
    private String accessDomain;

    /**
     * 文件地址
     */
    private String accessUrl;
    /**
     * 文件名
     */
    private String ossFileName;
    /**
     * 原始文件名
     */
    private String originFileName;
}
