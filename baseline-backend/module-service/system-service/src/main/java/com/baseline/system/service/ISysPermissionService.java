package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.vo.UserPermissionBizVO;
import com.baseline.system.dto.SysPermissionFilterDTO;
import com.baseline.system.dto.SysPermissionSaveDTO;
import com.baseline.system.entity.SysPermission;
import com.baseline.system.vo.SysPermissionDetailVO;
import com.baseline.system.vo.SysPermissionNodeVO;

import java.util.List;


/**
 * 权限信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysPermissionService extends IService<SysPermission>
{



    List<SysPermissionNodeVO> getNodeList(SysPermissionFilterDTO dto);

    boolean saveOrUpdate(SysPermissionSaveDTO dto);

    /**
     * 安全删除权限（检查是否有子节点）
     *
     * @param ids 权限ID列表
     * @return 删除结果
     */
    boolean safeRemoveByIds(List<Long> ids);

    SysPermissionDetailVO getById(Long id);


    /**
     * 获取权限
     * 
     * @param userId 用户Id
     * @return 菜单权限信息
     */
    List<String> getPermissionByUserId(Long userId);


    /**
     * 通过用户 ID和角色 ID获取权限
     * @return
     */
    List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(UserPermissionBizDTO dto);
}
