package com.baseline.oss;

import io.minio.MinioClient;

import jakarta.annotation.Resource;

public class OssTemplateFactory {

    @Resource
    OssProperties ossProperties;

    public OssTemplate getTemplate() {
        OssTemplate ossTemplate = null;
        switch (ossProperties.getType()) {
            case MINIO:
                MinioClient minioClient = MinioClient.builder()
                        .endpoint(ossProperties.getEndpoint())
                        .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                        .build();
                ossTemplate = new MinioTemplate(minioClient, new DefaultOssRule(ossProperties), ossProperties);
                break;
            case LOCAL:
                ossTemplate = new LocalFileTemplate(new DefaultOssRule(ossProperties), ossProperties);
                break;
        }

        return ossTemplate;

    }
}
