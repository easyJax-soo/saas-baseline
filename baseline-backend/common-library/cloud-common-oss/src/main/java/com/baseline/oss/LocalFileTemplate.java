package com.baseline.oss;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baseline.core.exception.BusinessException;
import com.baseline.oss.model.ChunkFileModel;
import com.baseline.oss.model.FileModel;
import com.baseline.oss.model.MultipartFileParam;
import com.baseline.oss.model.OssFile;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


/**
 * 本地上传
 */
@Slf4j
@AllArgsConstructor
public class LocalFileTemplate implements OssTemplate {


    /**
     * 存储桶命名规则
     */
    private final OssRule ossRule;

    /**
     * 配置类
     */
    private final OssProperties ossProperties;


    @Override
    @SneakyThrows
    public void makeBucket(String bucketName) {
        String absoluteBasePath = this.getAbsoluteUploadPath(bucketName);
        File targetFile = new File(absoluteBasePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }


    @Override
    @SneakyThrows
    public void removeBucket(String bucketName) {

        throw new BusinessException("本地文件当前不支持删除 bucket 目录，请确认后重试！");
    }


    @Override
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        String absoluteBasePath = this.getAbsoluteUploadPath(bucketName);
        File targetFile = new File(absoluteBasePath);
        return targetFile.exists();
    }


    @Override
    @SneakyThrows
    public void copyFile(String bucketName, String fileName, String destBucketName) {
        copyFile(bucketName, fileName, destBucketName, fileName);
    }


    @Override
    @SneakyThrows
    public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {


        throw new BusinessException("本地文件当前不支持复制，请确认后重试");
    }


    @Override
    @SneakyThrows
    public OssFile statFile(String fileName) {
        return statFile(ossProperties.getBucketName(), fileName);
    }


    @Override
    @SneakyThrows
    public OssFile statFile(String bucketName, String ossFileName) {
        String absolutePath = this.getAbsoluteUploadPath(bucketName);
        File file = new File(absolutePath + "/" + ossFileName);
        OssFile ossFile = new OssFile();
        if (file.exists()) {
            BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            ossFile.setName(ossFileName);
            ossFile.setLink(fileLink(ossFile.getName()));
            ossFile.setHash(String.valueOf(file.hashCode()));
            ossFile.setLength(basicFileAttributes.size());
            ossFile.setPutTime(LocalDateTime.ofInstant(basicFileAttributes.lastModifiedTime().toInstant(), ZoneOffset.of("+8")));
            ossFile.setContentType(FileNameUtil.extName(file.getName()));
        }
        return ossFile;
    }


    @Override
    public String filePath(String ossFileName) {
        return getBucketName().concat("/").concat(ossFileName);
    }


    @Override
    public String filePath(String bucketName, String ossFileName) {
        return getBucketName(bucketName).concat("/").concat(ossFileName);
    }


    @Override
    @SneakyThrows
    public String fileLink(String ossFileName) {
        return fileLink(ossProperties.getBucketName(), ossFileName);
    }


    @Override
    @SneakyThrows
    public String fileLink(String bucketName, String ossFileName) {
        return URLUtil.normalize(getAccessEndpoint()
                .concat("/")
                .concat(getBucketName(bucketName))
                .concat("/")
                .concat(ossFileName), false, true);
    }


    @Override
    @SneakyThrows
    public FileModel putFile(MultipartFile file) {
        return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
    }


    @Override
    @SneakyThrows
    public FileModel putFile(String fileName, MultipartFile file) {
        return putFile(ossProperties.getBucketName(), fileName, file);
    }


    @Override
    @SneakyThrows
    public FileModel putFile(String bucketName, String fileName, MultipartFile file) {
        return putFile(bucketName, file.getOriginalFilename(), file.getInputStream());
    }


    @Override
    @SneakyThrows
    public FileModel putFile(String fileName, InputStream stream) {
        return putFile(ossProperties.getBucketName(), fileName, stream);
    }


    @Override
    @SneakyThrows
    public FileModel putFile(String bucketName, String fileName, InputStream stream) {
        return putFile(bucketName, fileName, stream, "application/octet-stream");
    }


    @Override
    @SneakyThrows
    public void removeFile(String ossFileName) {
        removeFile(ossProperties.getBucketName(), ossFileName);
    }


    @Override
    @SneakyThrows
    public void removeFile(String bucketName, String ossFileName) {

        FileUtil.del(this.getAbsoluteUploadPath(bucketName) + "/" + ossFileName);
    }


    @Override
    @SneakyThrows
    public void removeFiles(List<String> ossFileNames) {
        throw new BusinessException("本地文件当前不允许批量删除！！");
    }


    @Override
    @SneakyThrows
    public void removeFiles(String bucketName, List<String> ossFileNames) {
        throw new BusinessException("本地文件当前不允许批量删除！！");
    }


    @Override
    public InputStream getObject(FileModel ossFileModel) {

        try {
            String absolutePath = this.getAbsoluteUploadPath(ossProperties.getBucketName());
            File file = new File(absolutePath + "/" + ossFileModel.getOssFileName());
            if (!file.exists()) {
                throw new BusinessException(
                        MessageFormat.format("不存在的文件，bucket={}, ossFilename={}",
                                ossProperties.getBucketName(), ossFileModel.getOssFileName()));
            }

            return Files.newInputStream(file.toPath());
        } catch (Exception e) {
            throw new BusinessException("Get object from Local File System error!!!", e);
        }
    }


    @Override
    public String getAccessEndpoint() {
        return StrUtil.blankToDefault(ossProperties.getAccessEndpoint(), ossProperties.getEndpoint());
    }


    @SneakyThrows
    public FileModel putFile(String bucketName, String fileName, InputStream stream, String contentType) {
        // 路径和文件名
        makeBucket(bucketName);
        String originalName = fileName;
        fileName = getFileName(fileName);
        // 保存文件
        String absoluteBasePath = this.getAbsoluteUploadPath(bucketName);
        File targetFile = new File(absoluteBasePath + "/" + fileName);
        FileUtil.writeFromStream(stream, targetFile);
        // 构造返回
        FileModel file = new FileModel();
        file.setOriginFileName(originalName);
        file.setOssFileName(fileName);
        file.setAccessDomain(getAccessDomain(bucketName));
        file.setAccessUrl(fileLink(bucketName, fileName));
        return file;
    }


    /**
     * 获取域名
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    @Override
    public String getAccessDomain(String bucketName) {
        return getAccessEndpoint() + "/" + getBucketName(bucketName);
    }

    @Override
    public ChunkFileModel chunkUpload(MultipartFileParam param) {
        String tempFileName = param.getMd5();
        ChunkFileModel chunkFileModel=new ChunkFileModel();
        chunkFileModel.setIndex(param.getIndex());
        File tempFile = new File(ossProperties.getTempPath(), tempFileName);
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            FileChannel fileChannel = raf.getChannel();
            long position = (param.getIndex() - 1) * param.getSize();
            byte[] fileData = param.getFile().getBytes();
            fileChannel.position(position);
            fileChannel.write(ByteBuffer.wrap(fileData));
            fileChannel.force(true);
            fileChannel.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        // 上传完成
        if (param.getIndex() == param.getTotalChunks()) {
            try (FileInputStream fileInputStream = new FileInputStream(tempFile)) {
                FileModel fileModel = putFile(param.getFileName(), fileInputStream);
                chunkFileModel.setFileModel(fileModel);
                tempFile.delete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return chunkFileModel;
    }



    /**
     * 获取域名
     *
     * @return String
     */
    @Override
    public String getAccessDomain() {
        return getAccessDomain(ossProperties.getBucketName());
    }


    /**
     * 根据规则生成存储桶名称规则
     *
     * @return String
     */
    private String getBucketName() {
        return getBucketName(ossProperties.getBucketName());
    }


    /**
     * 根据规则生成存储桶名称规则
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    private String getBucketName(String bucketName) {
        return ossRule.bucketName(bucketName);
    }


    /**
     * 根据规则生成文件名称规则
     *
     * @param originalFilename 原始文件名
     * @return string
     */
    private String getFileName(String originalFilename) {
        return ossRule.fileName(originalFilename);
    }


    /**
     * 获取上传的绝对路径
     *
     * @param originalBucketName
     * @return
     */
    private String getAbsoluteUploadPath(String originalBucketName) {
        // 根路径+bucket，根路径可设绝对路径或相对路径
        String absolutePath = ossProperties.getRootUploadPath() +
                "/" +
                this.getBucketName(originalBucketName) +
                "/";
        return FileUtil.normalize(absolutePath);
    }

}
