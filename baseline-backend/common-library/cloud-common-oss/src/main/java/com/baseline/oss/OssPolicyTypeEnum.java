package com.baseline.oss;


import lombok.Getter;

/**
 * minio策略配置
 *
 * @author SCMOX
 */
@Getter
public enum OssPolicyTypeEnum {

    /**
     * 只读
     */
    READ,

    /**
     * 只写
     */
    WRITE,

    /**
     * 读写
     */
    READ_WRITE;


}
