package com.baseline.auth.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import jakarta.annotation.Resource;
import javax.imageio.ImageIO;

import cn.hutool.core.codec.Base64;
import com.baseline.auth.config.captcha.properties.CaptchaProperties;
import com.baseline.auth.service.ICaptchaCodeService;
import com.baseline.auth.vo.CaptchaCodeVO;
import com.baseline.auth.vo.CaptchaEnabledVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.core.constant.CacheConstants;
import com.baseline.core.constant.Constants;
import com.baseline.core.utils.uuid.IdUtils;
import com.baseline.redis.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import com.google.code.kaptcha.Producer;

/**
 * 验证码实现处理
 *
 * @author ruoyi
 */
@Service
public class CaptchaCodeServiceImpl implements ICaptchaCodeService
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    private CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     */
    @Override
    public CaptchaCodeVO createCaptcha()
    {
        CaptchaEnabledVO captchaEnabledVo = isEnabled();
        if(!captchaEnabledVo.isEnabled()){
            throw new BusinessException("验证码未开启");
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String captchaType = captchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisUtil.set(verifyKey, code, Constants.CAPTCHA_EXPIRATION * 60);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            throw new BusinessException(e.getMessage());
        }

        CaptchaCodeVO captchaCodeVo = new CaptchaCodeVO();
        captchaCodeVo.setUuid(uuid);
        captchaCodeVo.setImage(Base64.encode(os.toByteArray()));
        return captchaCodeVo;
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCaptcha(String code, String uuid)
    {
        boolean captchaEnabled = captchaProperties.getEnabled();
        if(!captchaEnabled){
            return;
        }

        if (StringUtils.isEmpty(code))
        {
            throw new BusinessException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid))
        {
            throw new BusinessException("验证码已失效");
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = String.valueOf(redisUtil.get(verifyKey));
        redisUtil.del(verifyKey);

        if (!code.equalsIgnoreCase(captcha))
        {
            throw new BusinessException("验证码错误");
        }
    }


    public CaptchaEnabledVO isEnabled(){
        CaptchaEnabledVO vo = new CaptchaEnabledVO();
        vo.setEnabled(captchaProperties.getEnabled());
        return vo;
    }
}
