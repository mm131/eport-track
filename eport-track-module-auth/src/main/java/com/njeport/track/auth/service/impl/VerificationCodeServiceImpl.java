package com.njeport.track.auth.service.impl;

import com.google.code.kaptcha.Producer;
import com.njeport.track.auth.bean.VerificationCode;
import com.njeport.track.auth.dao.mapper.track.SysVerificationCodeMapper;
import com.njeport.track.auth.dao.meta.track.SysVerificationCodeDO;
import com.njeport.track.auth.service.VerificationCodeService;
import com.njeport.track.common.constants.StringConstants;
import com.njeport.track.auth.enums.VerificationBusinessIdEnum;
import com.njeport.track.auth.enums.VerificationTypeEnum;
import com.njeport.track.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * @author kongming
 * @date 2020/02/17
 */
@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Resource
    private SysVerificationCodeMapper verificationCodeMapper;

    @Resource
    private Producer captchaProducer;

    /**
     * 生成验证码
     *
     * @param businessId    业务ID
     * @param type          验证码类型
     * @param expireSeconds 验证码过期时间
     * @return 验证码信息
     */
    @Override
    public VerificationCode createKaptcha(VerificationBusinessIdEnum businessId, VerificationTypeEnum type, long expireSeconds) {
        String code;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            code = captchaProducer.createText();
            ImageIO.write(captchaProducer.createImage(code), StringConstants.JPG, outputStream);
        } catch (IOException e) {
            log.error("生成验证码失败", e);
            return null;
        }
        byte[] byteArr = outputStream.toByteArray();
        String id = IdUtils.randomId();
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Date expireTime = Date.from(now.plusSeconds(expireSeconds).atZone(ZoneId.systemDefault()).toInstant());
        SysVerificationCodeDO sysVerificationCodeDO = new SysVerificationCodeDO(id, code, businessId, type, expireTime);
        verificationCodeMapper.insert(sysVerificationCodeDO);
        return new VerificationCode(id, Base64.getEncoder().encodeToString(byteArr), code);
    }

    /**
     * 校验验证码是否有效
     *
     * @param id   验证码ID
     * @param code 验证码Code
     */
    @Override
    public void checkCode(String id, String code) {
        Assert.hasText(id, "验证码ID不能为空");
        Assert.hasText(code, "验证码不能为空");
        SysVerificationCodeDO sysVerificationCodeDO = verificationCodeMapper.getById(id);
        Assert.notNull(sysVerificationCodeDO, "验证码不存在");
        Assert.isTrue(code.equalsIgnoreCase(sysVerificationCodeDO.getCode()), "验证码不正确");
        Assert.isTrue(new Date().before(sysVerificationCodeDO.getExpireTime()), "验证码已过期");
    }
}
