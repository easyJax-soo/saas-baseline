package com.baseline.system.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.common.controller.AdminController;
import com.baseline.common.dto.SysMemberFilterBizDTO;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.common.vo.SysMemberVO;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysMemberDetailDTO;
import com.baseline.system.dto.SysMemberFilterDTO;
import com.baseline.system.dto.SysMemberResetPasswordDTO;
import com.baseline.system.dto.SysMemberSaveDTO;
import com.baseline.system.service.ISysMemberService;
import com.baseline.system.vo.PageSysMemberVO;
import com.baseline.system.vo.SysMemberDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@Api(tags = "[admin]-会员管理")
@RestController
@RequestMapping("/member")
public class SysMemberController extends AdminController {

    @Resource
    private ISysMemberService sysMemberService;

    @ApiOperation("会员分页查询")
    @Log(title = "会员管理-分页查询")
    @SaAdminCheckPermission("system:member:query")
    @PostMapping("/page")
    public IPage<PageSysMemberVO> page(@RequestBody SysMemberFilterDTO dto) {
        Page<PageSysMemberVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return sysMemberService.pageMember(page, dto);
    }

    @ApiOperation("新增或编辑会员")
    @Log(title = "会员管理-保存")
    @SaAdminCheckPermission("system:member:add")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysMemberSaveDTO dto) {
        return sysMemberService.saveOrUpdateMember(dto);
    }

    @ApiOperation("删除会员")
    @Log(title = "会员管理-删除")
    @SaAdminCheckPermission("system:member:remove")
    @PostMapping("/remove")
    public boolean remove(@ApiParam("会员ID列表") @RequestBody List<Long> ids) {
        return sysMemberService.removeMember(ids);
    }

    @ApiOperation("获取会员详情")
    @Log(title = "会员管理-详情")
    @SaAdminCheckPermission("system:member:query")
    @PostMapping("/detail")
    public SysMemberDetailVO detail(@Valid @RequestBody SysMemberDetailDTO dto) {
        return sysMemberService.getMemberDetail(dto.getId());
    }

    @ApiOperation("重置会员密码")
    @Log(title = "会员管理-重置密码")
    @SaAdminCheckPermission("system:member:edit")
    @PostMapping("/resetPw")
    public boolean resetPassword(@Valid @RequestBody SysMemberResetPasswordDTO dto) {
        return sysMemberService.resetPassword(dto.getMemberId(), dto.getNewPassword());
    }

    @ApiOperation("获取会员简单列表")
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
