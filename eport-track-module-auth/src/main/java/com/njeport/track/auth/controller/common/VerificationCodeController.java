package com.njeport.track.auth.controller.common;

import com.njeport.track.auth.service.VerificationCodeService;
import com.njeport.track.auth.enums.VerificationBusinessIdEnum;
import com.njeport.track.auth.enums.VerificationTypeEnum;
import com.njeport.track.common.vo.CommonReturnVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class VerificationCodeController {

    @Resource
    private VerificationCodeService verificationCodeService;

    @Value("${captcha.expire}")
    private long expireSeconds;

    @GetMapping("/sys/captcha")
    public CommonReturnVO getCaptchaImage(@RequestParam(name = "businessId") VerificationBusinessIdEnum businessId) {
        return CommonReturnVO.succ(verificationCodeService.createKaptcha(businessId, VerificationTypeEnum.WEB, expireSeconds));
    }
}
