package com.baseline.oss;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baseline.core.exception.BusinessException;
import com.baseline.oss.model.ChunkFileModel;
import com.baseline.oss.model.FileModel;

import com.baseline.oss.model.MultipartFileParam;
import com.baseline.oss.model.OssFile;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@AllArgsConstructor
public class MinioTemplate implements OssTemplate {


    /**
     * MinIO客户端
     */
    private final MinioClient client;

    /**
     * 存储桶命名规则
     */
    private final OssRule ossRule;

    /**
     * 配置类
     */
    private final OssProperties ossProperties;


    /**
     * 获取存储桶策略
     *
     * @param bucketName 存储桶名称
     * @param policyType 策略枚举
     * @return String
     */
    public static String getPolicyType(String bucketName, OssPolicyTypeEnum policyType) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"Statement\": [\n");
        builder.append("        {\n");
        builder.append("            \"Action\": [\n");

        switch (policyType) {
            case WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            case READ_WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucket\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            default:
                builder.append("                \"s3:GetBucketLocation\"\n");
                break;
        }

        builder.append("            ],\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("\"\n");
        builder.append("        },\n");
        if (OssPolicyTypeEnum.READ.equals(policyType)) {
            builder.append("        {\n");
            builder.append("            \"Action\": [\n");
            builder.append("                \"s3:ListBucket\"\n");
            builder.append("            ],\n");
            builder.append("            \"Effect\": \"Deny\",\n");
            builder.append("            \"Principal\": \"*\",\n");
            builder.append("            \"Resource\": \"arn:aws:s3:::");
            builder.append(bucketName);
            builder.append("\"\n");
            builder.append("        },\n");

        }
        builder.append("        {\n");
        builder.append("            \"Action\": ");

        switch (policyType) {
            case WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            case READ_WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:GetObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            default:
                builder.append("\"s3:GetObject\",\n");
                break;
        }

        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("/*\"\n");
        builder.append("        }\n");
        builder.append("    ],\n");
        builder.append("    \"Version\": \"2012-10-17\"\n");
        builder.append("}\n");
        return builder.toString();
    }


    @Override
    @SneakyThrows
    public void makeBucket(String bucketName) {
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName(bucketName)).build())) {
            client.makeBucket(
                    MakeBucketArgs.builder().bucket(getBucketName(bucketName)).build());
            client.setBucketPolicy(
                    SetBucketPolicyArgs.builder().bucket(getBucketName(bucketName))
                            .config(getPolicyType(getBucketName(bucketName), OssPolicyTypeEnum.READ)).build());
        }
    }


    @Override
    @SneakyThrows
    public void removeBucket(String bucketName) {
        client.removeBucket(
                RemoveBucketArgs.builder().bucket(getBucketName(bucketName)).build());
    }


    @Override
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return client.bucketExists(
                BucketExistsArgs.builder().bucket(getBucketName(bucketName)).build());
    }


    @Override
    @SneakyThrows
    public void copyFile(String bucketName, String fileName, String destBucketName) {
        copyFile(bucketName, fileName, destBucketName, fileName);
    }


    @Override
    @SneakyThrows
    public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
        client.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(getBucketName(bucketName)).object(fileName).build())
                        .bucket(getBucketName(destBucketName))
                        .object(destFileName)
                        .build());
    }


    @Override
    @SneakyThrows
    public OssFile statFile(String fileName) {
        return statFile(ossProperties.getBucketName(), fileName);
    }


    @Override
    @SneakyThrows
    public OssFile statFile(String bucketName, String fileName) {
        StatObjectResponse stat = client.statObject(
                StatObjectArgs.builder().bucket(getBucketName(bucketName)).object(fileName).build());
        OssFile ossFile = new OssFile();
        ossFile.setName(ObjectUtil.isEmpty(stat.object()) ? fileName : stat.object());
        ossFile.setLink(fileLink(ossFile.getName()));
        ossFile.setHash(String.valueOf(stat.hashCode()));
        ossFile.setLength(stat.size());
        ossFile.setPutTime(stat.lastModified().toLocalDateTime());
        ossFile.setContentType(stat.contentType());
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
        // 包装 InputStream 以支持 mark 和 reset
        BufferedInputStream bufferedStream = new BufferedInputStream(stream);
        bufferedStream.mark(Integer.MAX_VALUE); // 标记当前流的位置

        String uploadContentType = "application/octet-stream";

        // 使用 Apache Tika 来检测 Content-Type
        Tika tika = new Tika();
        String contentType = tika.detect(bufferedStream);

        // 重置流的位置
        bufferedStream.reset();

        // 检查 contentType 是否在 allowedTypes 中
        if (ObjectUtil.isNotNull(ossProperties.getAllowPreview()) && ossProperties.getAllowPreview().contains(contentType)) {
            uploadContentType = contentType;
        }

        // 使用 bufferedStream 而不是原始的 stream
        return putFile(bucketName, fileName, bufferedStream, uploadContentType);
    }


    @Override
    @SneakyThrows
    public void removeFile(String ossFileName) {
        removeFile(ossProperties.getBucketName(), ossFileName);
    }


    @Override
    @SneakyThrows
    public void removeFile(String bucketName, String ossFileName) {
        client.removeObject(
                RemoveObjectArgs.builder().bucket(getBucketName(bucketName)).object(ossFileName).build()
        );
    }


    @Override
    @SneakyThrows
    public void removeFiles(List<String> ossFileNames) {
        removeFiles(ossProperties.getBucketName(), ossFileNames);
    }


    @Override
    @SneakyThrows
    public void removeFiles(String bucketName, List<String> ossFileNames) {
        Stream<DeleteObject> stream = ossFileNames.stream().map(DeleteObject::new);
        client.removeObjects(
                RemoveObjectsArgs.builder().bucket(getBucketName(bucketName)).objects(stream::iterator).build());
    }


    @Override
    public InputStream getObject(FileModel ossFileModel) {
        if (ObjectUtil.isNull(ossFileModel)) {
            throw new BusinessException("错误的OSS文件信息");
        }
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(ossProperties.getBucketName())
                    .object(ossFileModel.getOssFileName())
                    .build();

            return client.getObject(getObjectArgs);
        } catch (Exception e) {
            throw new BusinessException("Get object from MinIO error!!!", e);
        }
    }


    @Override
    public String getAccessEndpoint() {
        return StrUtil.blankToDefault(ossProperties.getAccessEndpoint(), ossProperties.getEndpoint());
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

    @SneakyThrows
    @Override
    public ChunkFileModel chunkUpload(MultipartFileParam param) {
        MultipartFile file = param.getFile();
        ChunkFileModel chunkFileModel=new ChunkFileModel();
        chunkFileModel.setIndex(param.getIndex());
        makeBucket("temp");
        client.putObject(PutObjectArgs.builder()
                .bucket(getBucketName("temp"))
                .object(param.getMd5() + "/" + param.getIndex())
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        if (param.getIndex() == param.getTotalChunks()) {
            // 完成之后进行文件合并
            List<ComposeSource> sourceList = Stream.iterate(1, i -> ++i).limit(param.getTotalChunks())
                    .map(i -> ComposeSource.builder()
                            .bucket(getBucketName("temp"))
                            .object(param.getMd5() + "/" + i).build())
                    .collect(Collectors.toList());
            String fileName = getFileName(param.getFileName());
            String bucketName =ossProperties.getBucketName();
            makeBucket(bucketName);
            client.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(getBucketName(bucketName))
                            .object(fileName)
                            .sources(sourceList)
                            .build());
            FileModel fileModel=new FileModel();
            fileModel.setOriginFileName(param.getFileName());
            fileModel.setOssFileName(fileName);
            fileModel.setAccessUrl(fileLink(bucketName,fileName));
            fileModel.setAccessDomain(getOssHost(bucketName));
            chunkFileModel.setFileModel(fileModel);
            // 删除临时文件
            removeFiles(getBucketName("temp"),Stream.iterate(1,i->++i)
                    .limit(param.getTotalChunks())
                    .map(i->param.getMd5()+"/"+i)
                    .collect(Collectors.toList()));
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


    @SneakyThrows
    public Bucket getBucket() {
        return getBucket(getBucketName());
    }


    @SneakyThrows
    public Bucket getBucket(String bucketName) {
        Optional<Bucket> bucketOptional = client.listBuckets().stream()
                .filter(bucket -> bucket.name().equals(getBucketName(bucketName))).findFirst();
        return bucketOptional.orElse(null);
    }


    @SneakyThrows
    public List<Bucket> listBuckets() {
        return client.listBuckets();
    }


    @SneakyThrows
    public FileModel putFile(String bucketName, String fileName, InputStream stream, String contentType) {
        makeBucket(bucketName);
        String originalName = fileName;
        fileName = getFileName(fileName);

        client.putObject(
                PutObjectArgs.builder()
                        .bucket(getBucketName(bucketName))
                        .object(fileName)
                        .stream(stream, stream.available(), -1)
                        .contentType(contentType)
                        .build());
        FileModel file = new FileModel();
        file.setOriginFileName(originalName);
        file.setOssFileName(fileName);
        file.setAccessDomain(getOssHost(bucketName));
        file.setAccessUrl(fileLink(bucketName, fileName));
        return file;
    }


    /**
     * 获取文件外链
     *
     * @param bucketName  bucket名称
     * @param ossFileName 文件名称
     * @param expires     过期时间 <=7 秒级
     * @return url
     */
    @SneakyThrows
    public String getPresignedObjectUrl(String bucketName, String ossFileName, Integer expires) {
        return client.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(getBucketName(bucketName))
                        .object(ossFileName)
                        .expiry(expires)
                        .build()
        );
    }


    /**
     * 获取存储桶策略
     *
     * @param policyTypeEnum 策略枚举
     * @return String
     */
    public String getPolicyType(OssPolicyTypeEnum policyTypeEnum) {
        return getPolicyType(getBucketName(), policyTypeEnum);
    }


    /**
     * 获取域名
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    public String getOssHost(String bucketName) {
        return getAccessEndpoint() + "/" + getBucketName(bucketName);
    }


    /**
     * 获取域名
     *
     * @return String
     */
    public String getOssHost() {
        return getOssHost(ossProperties.getBucketName());
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

}
