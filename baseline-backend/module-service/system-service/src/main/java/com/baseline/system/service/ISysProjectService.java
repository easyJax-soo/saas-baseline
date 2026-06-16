package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysProjectFilterDTO;
import com.baseline.system.dto.SysProjectSaveDTO;
import com.baseline.system.entity.SysProject;
import com.baseline.system.vo.SysProjectPageVO;
import com.baseline.system.vo.SysProjectVO;
import com.baseline.system.vo.SysProjectTypeGroupVO;

import java.util.List;

/**
 * 系统项目表 服务类
 *
 * @author system
 */
public interface ISysProjectService extends IService<SysProject> {

    /**
     * 保存或更新项目
     * @param dto 项目信息
     * @return 是否成功
     */
    boolean saveOrUpdate(SysProjectSaveDTO dto);

    /**
     * 获取项目详情
     * @param id 项目ID
     * @return 项目详情
     */
    SysProjectVO getDetail(Long id);

    /**
     * 分页查询项目
     * @param dto 查询条件
     * @return 分页结果
     */
    IPage<SysProjectPageVO> pageProject(SysProjectFilterDTO dto);

    /**
     * 获取项目列表
     * @param dto 查询条件
     * @return 项目列表
     */
    List<SysProjectVO> getList(SysProjectFilterDTO dto);

    /**
     * 获取当前用户的项目列表
     * @return 项目列表
     */
    List<SysProjectVO> getMyProjectList();

    /**
     * 按项目类型分类获取项目列表
     * @return 按项目类型分组的项目列表
     */
    List<SysProjectTypeGroupVO> getProjectsByType();

    /**
     * 获取当前用户有权限访问的项目编码列表
     * @return 项目编码列表
     */
    List<String> getUserProjectCodes();
}