package com.njeport.track.auth.service;

import com.njeport.track.auth.bean.VerificationCode;
import com.njeport.track.auth.enums.VerificationBusinessIdEnum;
import com.njeport.track.auth.enums.VerificationTypeEnum;

/**
 * @author kongming
 * @date 2020/02/17
 */
public interface VerificationCodeService {

    /**
     * 生成验证码图片
     *
     * @param businessId    业务ID
     * @param type          验证码类型
     * @param expireSeconds 验证码过期时间
     * @return 验证码信息
     */
    VerificationCode createKaptcha(VerificationBusinessIdEnum businessId, VerificationTypeEnum type, long expireSeconds);

    /**
     * 校验验证码是否有效
     *
     * @param id   验证码ID
     * @param code 验证码Code
     */
    void checkCode(String id, String code);
}
