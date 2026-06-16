package com.baseline.auth.service;

import com.baseline.auth.dto.OauthClientDetailsSaveDTO;
import com.baseline.auth.entity.OauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.auth.vo.OauthClientDetailsDetailVO;
import com.baseline.auth.dto.OauthClientDetailsFilterDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.baseline.auth.vo.OauthClientDetailsSimpleVO;
import com.baseline.auth.vo.OauthClientDetailsVO;
import com.baseline.auth.vo.OauthClientGenerateVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gzhc
 * @since 2024-12-02
 */
public interface IOauthClientDetailsService extends IService<OauthClientDetails> {

    IPage<OauthClientDetailsVO> paging(Page<OauthClientDetailsVO> objectPage, OauthClientDetailsFilterDTO dto);

    List<OauthClientDetailsSimpleVO> simpleList(OauthClientDetailsFilterDTO dto);


    boolean saveOrUpdate(OauthClientDetailsSaveDTO dto);

    OauthClientDetailsDetailVO getDetail(Long id);

    OauthClientDetailsDetailVO getDetailByClientId(String clientId);

    boolean remove(List<Long> ids);

    OauthClientGenerateVO generate();
}
