package com.njeport.track.auth.dao.meta.track;

import com.njeport.track.auth.enums.VerificationBusinessIdEnum;
import com.njeport.track.auth.enums.VerificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysVerificationCodeDO {

    /**
     * 主键id
     */
    private String id;

    /**
     * 验证码
     */
    private String code;

    /**
     * 业务ID，1：注册，2：登录公服平台， 3：登录管理后台，4：忘记密码
     */
    private VerificationBusinessIdEnum businessId;

    /**
     * 类型， 1：网站验证码，2：短信验证码
     */
    private VerificationTypeEnum type;

    /**
     * 验证码过期时间
     */
    private Date expireTime;
}
