package com.baseline.system.controller.feign;


import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.vo.DictBizVO;
import com.baseline.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * [Feign]-字典服务接口
 *
 * @author cascade
 * @date 2025/11/17
 */
@Api(tags = "[Feign]-字典接口")
@RestController
@RequestMapping("/dict")
public class SysDictFeignController {

    @Autowired
    ISysDictDataService dictDataService;

    @ApiOperation("[Feign]-获取字典数据")
    @PostMapping("/dictData")
    public List<DictBizVO> getDictDataByDictCode(@Valid @RequestBody DictBizDTO dto){
        return dictDataService.getDictDataByDictCode(dto);
    }


}
