package com.baseline.system.controller.api;

import com.baseline.log.annotation.Log;
import com.baseline.oss.OssTemplateFactory;
import com.baseline.oss.model.FileModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

@Api(tags = "[api]-文件上传")
@RestController
@RequestMapping("/ api")
public class SysFileApiController {

    @Resource
    private OssTemplateFactory templateFactory;

    @ApiOperation("会员端文件上传")
    @Log(title = "会员端文件上传")
    @PostMapping("/put-file")
    public FileModel putFile(MultipartFile file) {
        return templateFactory.getTemplate().putFile(file);
    }
}
