package com.baseline.auth.provider;

import com.alibaba.fastjson2.JSONObject;
import com.baseline.auth.dto.AccountPasswordLoginDTO;
import com.baseline.auth.dto.LoginBashDTO;
import com.baseline.auth.provider.common.AuthenticationAbstractHandler;
import com.baseline.auth.vo.LoginTokenVO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

@Service
public class AccountPasswordAuthHandler extends AuthenticationAbstractHandler {
    @Autowired
    private Validator validator;

    @Override
    public String getAuthType() {
        return "accountPassword";
    }

    @Override
    public LoginTokenVO authenticate(LoginBashDTO<?> request) throws BusinessException {
        String loginType = request.getLoginType();
        Object credentials = request.getCredentials();

        AccountPasswordLoginDTO dto = JSONObject.parseObject(JSONObject.toJSONString(credentials), AccountPasswordLoginDTO.class);
        // 调用校验逻辑
        Set<ConstraintViolation<AccountPasswordLoginDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        LoginUserBizVO user = loadUserInfo(loginType, dto.getAccount());

        System.out.println(SecurityUtils.encryptPassword(dto.getPassword(), user.getSalt()));
        System.out.println(user.getSalt());
        // 验证密码（使用BCrypt+指定盐值方式）
        if (!SecurityUtils.matchesPassword(dto.getPassword(), user.getPassword(), user.getSalt())) {
            throw new BusinessException("密码错误");
        }
        
        return login(user, request.getLoginType());
    }

}