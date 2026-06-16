package com.baseline.system.controller.feign;

import com.baseline.common.dto.SysDeptFilterBizDTO;
import com.baseline.common.vo.SysDeptVO;
import com.baseline.system.dto.SysDeptFilterDTO;
import com.baseline.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [Feign]-部门服务接口
 *
 * @author cascade
 * @date 2025/11/17
 */
@Api(tags = "[Feign]-部门接口")
@RestController
@RequestMapping("/dept")
public class SysDeptFeignController {

    @Autowired
    ISysDeptService sysDeptService;

    @ApiOperation("[Feign]-获取部门列表")
    @PostMapping("/deptList")
    public List<SysDeptVO> getDeptVOList(@RequestBody SysDeptFilterBizDTO dto) {
        // 转换 DTO
        SysDeptFilterDTO systemDto = new SysDeptFilterDTO();
        BeanUtils.copyProperties(dto, systemDto);
        
        // 直接调用平铺列表方法
        return sysDeptService.getDeptList(systemDto);
    }
}
