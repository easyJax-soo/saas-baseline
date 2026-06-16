package com.baseline.oss;


import com.baseline.oss.model.ChunkFileModel;
import com.baseline.oss.model.FileModel;
import com.baseline.oss.model.MultipartFileParam;
import com.baseline.oss.model.OssFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


public interface OssTemplate {

    /**
     * 创建 存储桶
     *
     * @param bucketName 存储桶名称
     */
    void makeBucket(String bucketName);


    /**
     * 删除 存储桶
     *
     * @param bucketName 存储桶名称
     */
    void removeBucket(String bucketName);


    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    boolean bucketExists(String bucketName);


    /**
     * 拷贝文件
     *
     * @param bucketName     存储桶名称
     * @param fileName       存储桶文件名称
     * @param destBucketName 目标存储桶名称
     */
    void copyFile(String bucketName, String fileName, String destBucketName);


    /**
     * 拷贝文件
     *
     * @param bucketName     存储桶名称
     * @param fileName       存储桶文件名称
     * @param destBucketName 目标存储桶名称
     * @param destFileName   目标存储桶文件名称
     */
    void copyFile(String bucketName, String fileName, String destBucketName, String destFileName);


    /**
     * 获取文件信息
     *
     * @param fileName 存储桶文件名称
     * @return InputStream
     */
    OssFile statFile(String fileName);


    /**
     * 获取文件信息
     *
     * @param bucketName 存储桶名称
     * @param fileName   存储桶文件名称
     * @return InputStream
     */
    OssFile statFile(String bucketName, String fileName);


    /**
     * 获取文件相对路径
     *
     * @param fileName 存储桶对象名称
     * @return String
     */
    String filePath(String fileName);


    /**
     * 获取文件相对路径
     *
     * @param bucketName 存储桶名称
     * @param fileName   存储桶对象名称
     * @return String
     */
    String filePath(String bucketName, String fileName);


    /**
     * 获取文件地址
     *
     * @param fileName 存储桶对象名称
     * @return String
     */
    String fileLink(String fileName);


    /**
     * 获取文件地址
     *
     * @param bucketName 存储桶名称
     * @param fileName   存储桶对象名称
     * @return String
     */
    String fileLink(String bucketName, String fileName);


    /**
     * 上传文件
     *
     * @param file 上传文件类
     * @return BladeFile
     */
    FileModel putFile(MultipartFile file);


    /**
     * 上传文件
     *
     * @param file     上传文件类
     * @param fileName 上传文件名
     * @return BladeFile
     */
    FileModel putFile(String fileName, MultipartFile file);


    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   上传文件名
     * @param file       上传文件类
     * @return BladeFile
     */
    FileModel putFile(String bucketName, String fileName, MultipartFile file);


    /**
     * 上传文件
     *
     * @param fileName 存储桶对象名称
     * @param stream   文件流
     * @return BladeFile
     */
    FileModel putFile(String fileName, InputStream stream);


    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   存储桶对象名称
     * @param stream     文件流
     * @return BladeFile
     */
    FileModel putFile(String bucketName, String fileName, InputStream stream);


    /**
     * 删除文件
     *
     * @param fileName 存储桶对象名称
     */
    void removeFile(String fileName);


    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   存储桶对象名称
     */
    void removeFile(String bucketName, String fileName);


    /**
     * 批量删除文件
     *
     * @param fileNames 存储桶对象名称集合
     */
    void removeFiles(List<String> fileNames);


    /**
     * 批量删除文件
     *
     * @param bucketName 存储桶名称
     * @param fileNames  存储桶对象名称集合
     */
    void removeFiles(String bucketName, List<String> fileNames);


    /**
     * 获取对象的文件流
     *
     */
    InputStream getObject(FileModel fileModel);


    /**
     * 获取用于访问文件的基础地址
     *
     * @return
     */
    String getAccessEndpoint();


    /**
     * 访问的地址前缀 accessEndpoint+bucket
     * @return string(http://192.168.6.101:8900/sfile/oss)
     */
     String getAccessDomain();
    String getAccessDomain(String bucketName);


    /**
     * 大文件分块上传
     * @param param MultipartFileParam
     * @return 分片序号
     */
    ChunkFileModel chunkUpload(MultipartFileParam param);


}
