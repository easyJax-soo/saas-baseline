package com.baseline.system.controller.feign;

import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysMemberDetailBizDTO;
import com.baseline.common.dto.SysMemberFilterBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysMemberVO;
import com.baseline.system.dto.SysMemberFilterDTO;
import com.baseline.system.service.ISysMemberService;
import com.baseline.system.vo.SysMemberDetailVO;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * [Feign]-会员服务接口
 *
 * @author system
 */
@Api(tags = "[Feign]-会员接口")
@RestController
@RequestMapping("/member")
public class SysMemberFeignController {

    @Autowired
    private ISysMemberService sysMemberService;

    @ApiOperation("[Feign]-根据用户名获取会员登录信息")
    @PostMapping("/getLoginUserByUsername")
    public LoginUserBizVO getLoginUserByUsername(@RequestBody LoginUserBizDTO dto) {
        return sysMemberService.getLoginUserByUsername(dto);
    }

    @ApiOperation("[Feign]-获取会员详情")
    @PostMapping("/detail")
    public SysMemberVO detail(@RequestBody SysMemberDetailBizDTO dto) {
        // 调用会员详情方法
        SysMemberDetailVO detailVO = sysMemberService.getMemberDetail(dto.getId());
        
        // 转换为 common 包的 SysMemberVO
        SysMemberVO memberVO = new SysMemberVO();
        if (detailVO != null) {
            BeanUtils.copyProperties(detailVO, memberVO);
        }
        return memberVO;
    }

    @ApiOperation("[Feign]-获取会员简单列表")
    @PostMapping("/simpleList")
    public List<SysMemberVO> simpleList(@RequestBody SysMemberFilterBizDTO dto) {
        // 转换 DTO
        SysMemberFilterDTO systemDto = new SysMemberFilterDTO();
        BeanUtils.copyProperties(dto, systemDto);
        
        // 调用会员简单列表方法
        List<SysMemberDetailVO> memberList = sysMemberService.getSimpleList(systemDto);
        
        // 转换为 common 包的 SysMemberVO
        return BeanUtil.copyToList(memberList, SysMemberVO.class);
    }
}
