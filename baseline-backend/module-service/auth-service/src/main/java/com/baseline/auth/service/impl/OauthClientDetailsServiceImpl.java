package com.baseline.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baseline.auth.dto.OauthClientDetailsSaveDTO;
import com.baseline.auth.entity.OauthClientDetails;
import com.baseline.auth.mapper.OauthClientDetailsMapper;
import com.baseline.auth.service.IOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.auth.vo.OauthClientDetailsDetailVO;
import com.baseline.auth.vo.OauthClientDetailsSimpleVO;
import com.baseline.auth.vo.OauthClientGenerateVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baseline.auth.vo.OauthClientDetailsVO;
import com.baseline.auth.dto.OauthClientDetailsFilterDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;

/**
 *  服务实现类
 *
 * @author gzhc
 * @since 2024-12-02
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

    @Value("${oauth.authorizeUrl:}")
    private String authorizeUrl;


    @Override
    public IPage<OauthClientDetailsVO> paging(Page<OauthClientDetailsVO> objectPage, OauthClientDetailsFilterDTO dto) {
        return baseMapper.paging(objectPage,dto).convert(vo -> {
            vo.setAuthorizeUrl(getAuthorizeUrl(vo));
            return vo;
        });
    }

    @Override
    public List<OauthClientDetailsSimpleVO> simpleList(OauthClientDetailsFilterDTO dto) {
        List<OauthClientDetailsVO> list = baseMapper.simpleList(dto);
        return BeanUtil.copyToList(list, OauthClientDetailsSimpleVO.class);
    }

    @Override
    public boolean saveOrUpdate(OauthClientDetailsSaveDTO dto) {
        LambdaQueryWrapper<OauthClientDetails> query = new LambdaQueryWrapper<>();
        query.ne(ObjectUtil.isNotNull(dto.getId()), OauthClientDetails::getId, dto.getId());
        query.eq(OauthClientDetails::getClientId, dto.getClientId());
        boolean exists = baseMapper.exists(query);
        if(exists){
            throw new BusinessException("客户端ID已存在");
        }

        OauthClientDetails saveEntity = BeanUtil.copyProperties(dto, OauthClientDetails.class);
        boolean result = baseMapper.saveOrUpdate(saveEntity);

        return result;
    }


    @Override
    public OauthClientDetailsDetailVO getDetail(Long id) {
        OauthClientDetailsVO vo = baseMapper.getDetail(id);
        vo.setAuthorizeUrl(getAuthorizeUrl(vo));
        OauthClientDetailsDetailVO detailVo = BeanUtil.copyProperties(vo, OauthClientDetailsDetailVO.class);
        return detailVo;
    }

    @Override
    public OauthClientDetailsDetailVO getDetailByClientId(String clientId) {
        OauthClientDetailsVO vo = baseMapper.getDetailByClientId(clientId);
//        vo.setAuthorizeUrl(getAuthorizeUrl(vo));
        OauthClientDetailsDetailVO detailVo = BeanUtil.copyProperties(vo, OauthClientDetailsDetailVO.class);
        return detailVo;
    }

    @Override
    public boolean remove(List<Long> ids) {
        LambdaQueryWrapper<OauthClientDetails> query = new LambdaQueryWrapper<>();
        query.in(OauthClientDetails::getId, ids);

        baseMapper.delete(query);
        return true;
    }

    @Override
    public OauthClientGenerateVO generate() {
        String keyStr = DigestUtil.md5Hex(RandomUtil.randomString(32) + SecurityUtils.getTenantId());

        LambdaQueryWrapper<OauthClientDetails> query = new LambdaQueryWrapper<>();
        query.eq(OauthClientDetails::getClientSecret, keyStr);
        OauthClientDetails result = baseMapper.selectOne(query);
        if(ObjectUtil.isNotNull(result)){
            throw new BusinessException("密钥生成失败，请重新生成");
        }

        OauthClientGenerateVO vo = new OauthClientGenerateVO();
        vo.setClientSecret(keyStr);
        return vo;
    }


    private String getAuthorizeUrl(OauthClientDetailsVO vo){
        String[] grantType = vo.getAuthorizedGrantTypes();
        if (ObjectUtil.isNotNull(grantType) && Arrays.asList(grantType).contains("authorization_code")) {
            return String.format("%s?client_id=%s&response_type=%s&redirect_uri=%s",
                    authorizeUrl,
                    vo.getClientId(),
                    "code",
                    vo.getWebServerRedirectUri()
            );
        }

        if (ObjectUtil.isNotNull(grantType) && Arrays.asList(grantType).contains("implicit")) {
            return String.format("%s?client_id=%s&response_type=%s&redirect_uri=%s",
                    authorizeUrl,
                    vo.getClientId(),
                    "token",
                    vo.getWebServerRedirectUri()
            );
        }
        return "";
    }
}
