package com.njeport.track.auth.service;

import com.alibaba.fastjson.JSON;
import com.njeport.track.auth.bean.VerificationCode;
import com.njeport.track.auth.enums.VerificationBusinessIdEnum;
import com.njeport.track.auth.enums.VerificationTypeEnum;
import com.njeport.track.test.AbstractTestCase;
import org.apache.shiro.util.Assert;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * @author kongming
 * @date 2020/02/17
 */
public class VerificationCodeServiceTest extends AbstractTestCase {

    @Resource
    private VerificationCodeService verificationCodeService;

    @Rollback(false)
    @Test
    public void testCreateKaptcha() {
        VerificationCode verificationCode = verificationCodeService.createKaptcha(VerificationBusinessIdEnum.LOGIN_ADMIN,
                VerificationTypeEnum.WEB, 1800);
        System.out.println(JSON.toJSONString(verificationCode));
        Assert.notNull(verificationCode);
    }

    @Test
    public void testCheckCode() {
        verificationCodeService.checkCode("66a227dca4f84cd8aef6e5cf8c0880e0", "FPDI");
    }
}
