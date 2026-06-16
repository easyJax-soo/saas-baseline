package com.baseline.oss;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 默认存储桶生成规则
 *
 * @author SCloud
 */
@AllArgsConstructor
public class DefaultOssRule implements OssRule {


    private OssProperties ossProperties;


    /**
     * 拼接存储桶名称
     * <p>
     * 开启租户模式则为 bucketName-tenantId 格式，未开启则为 bucketName
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @Override
    public String bucketName(String bucketName) {
        return bucketName;
    }


    @Override
    public String fileName(String originalFilename) {
        String envPath = "";
        if (ossProperties != null) {
            envPath = ossProperties.getEnvPath();
        }
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return FileUtil.normalize(envPath + "/" +
                today + "/" +
                IdUtil.fastSimpleUUID() + "." + FileNameUtil.extName(originalFilename));
    }

}
