package com.baseline.auth.mapper;

import com.baseline.auth.entity.OauthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baseline.auth.vo.OauthClientDetailsVO;
import com.baseline.auth.dto.OauthClientDetailsFilterDTO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author gzhc
 * @since 2024-12-02
 */
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

    IPage<OauthClientDetailsVO> paging(Page<OauthClientDetailsVO> objectPage, OauthClientDetailsFilterDTO dto);

    List<OauthClientDetailsVO> simpleList(@Param("dto") OauthClientDetailsFilterDTO dto);

    OauthClientDetailsVO getDetail(Long id);

    OauthClientDetailsVO getDetailByClientId(String clientId);

    boolean saveOrUpdate(OauthClientDetails entity);
}