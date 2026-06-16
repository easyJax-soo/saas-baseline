package com.baseline.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 参数配置
 */
@Data
@ConfigurationProperties(prefix = "oss")
@Component
public class OssProperties {

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 上传目录的环境前缀
     * <p>
     * 用于拼接路径，如 envPath=dev，上传为 /dev/{customUploadPath}
     */
    private String envPath = "";

    /**
     * 允许上传的后缀
     * 字符串格式，使用英文逗号 {@code ,} 进行分隔
     */
    private String allowSuffix;

    /**
     * 对象存储类型
     */
    private OssType type;

    /**
     * 是否开启租户模式
     */
    private Boolean tenantMode = false;

    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * Access key就像用户ID，可以唯一标识你的账户
     */
    private String accessKey;

    /**
     * Secret key是你账户的密码
     */
    private String secretKey;

    /**
     * 默认的存储桶名称
     */
    private String bucketName = "oss";


    /**
     * 访问文件使用的URL
     * 若为空，则使用 endpoint 的值
     */
    private String accessEndpoint = "";

    /**
     * 文件上传根路径
     * 主要是本地上传文件使用，默认为空
     */
    private String rootUploadPath = "";

    /**
     * 临时文件存放目录
     */
    private String tempPath="";


    /**
     * 允许预览的文件Content-Type
     */
    private List<String> allowPreview;
}
