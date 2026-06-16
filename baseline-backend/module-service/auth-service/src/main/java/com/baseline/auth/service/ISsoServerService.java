package com.baseline.auth.service;

import com.baseline.auth.dto.AccountPasswordLoginDTO;

public interface ISsoServerService {

    Object doLogin(AccountPasswordLoginDTO dto);

}
