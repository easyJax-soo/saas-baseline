package com.baseline.auth.service.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.sso.processor.SaSsoServerProcessor;
import com.baseline.auth.dto.AccountPasswordLoginDTO;
import com.baseline.auth.service.ISsoServerService;
import com.baseline.utils.security.SaTokenUtils;
import org.springframework.stereotype.Service;


@Service
public class SsoServerServiceImpl implements ISsoServerService {

    @Override
    public Object doLogin(AccountPasswordLoginDTO dto) {

        SaRequest req = SaHolder.getRequest();
        SaTokenUtils.ADMIN.login(10001);


        return SaSsoServerProcessor.instance.ssoDoLogin();
    }


}
