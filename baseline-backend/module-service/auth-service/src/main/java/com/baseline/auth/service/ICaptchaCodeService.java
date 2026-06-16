package com.baseline.auth.service;

import com.baseline.auth.vo.CaptchaCodeVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gzhc
 * @since 2024-12-02
 */
public interface ICaptchaCodeService {

    CaptchaCodeVO createCaptcha();

    void checkCaptcha(String code, String uuid);
}
