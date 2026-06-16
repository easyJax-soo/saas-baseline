package com.baseline.system.controller.admin;

import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.oss.OssTemplateFactory;
import com.baseline.oss.model.ChunkFileModel;
import com.baseline.oss.model.FileModel;
import com.baseline.oss.model.MultipartFileParam;
import com.baseline.system.dto.MultipartFileParamDTO;
import com.baseline.system.vo.SysFileDomainVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

/**
 * 文件请求处理
 * 
 * @author ruoyi
 */
@Api(tags = "[admin]-文件上传")
@RestController
@RequestMapping("/file")
public class SysFileController
{
    @Resource
    OssTemplateFactory templateFactory;

    @ApiOperation("文件上传")
    @Log(title = "文件上传")
    @SaAdminCheckPermission("system:file:upload")
    @PostMapping("/put-file")
    public FileModel putFile(@RequestPart("file") MultipartFile file) {
        return templateFactory.getTemplate().putFile(file);
    }

    @ApiOperation("分片上传")
    @Log(title = "分片上传")
    @SaAdminCheckPermission("system:file:chunk")
    @PostMapping("/chunk")
    public ChunkFileModel chunkUpload(MultipartFileParamDTO dto){
        MultipartFileParam param=new MultipartFileParam();
        param.setFile(dto.getFile());
        param.setFileName(dto.getFile().getOriginalFilename());
        param.setIndex(dto.getChunkNumber());
        param.setTotalChunks(dto.getTotalChunks());
        param.setTotalSize(dto.getTotalSize());
        param.setMd5(dto.getIdentifier());
        return templateFactory.getTemplate().chunkUpload(param);
    }



    @ApiOperation("访问文件的前缀域名部分")
    @Log(title = "访问文件的前缀域名部分")
    @GetMapping("/domain")
    public SysFileDomainVO getAccessDomain(){
        String domain = templateFactory.getTemplate().getAccessDomain();
        SysFileDomainVO fileVo = new SysFileDomainVO();
        fileVo.setDomain(domain);
        return fileVo;
    }

}