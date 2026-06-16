package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.entity.SysUserPost;
import com.baseline.system.mapper.SysUserPostMapper;
import com.baseline.system.service.ISysUserPostService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和岗位关联表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {

}
