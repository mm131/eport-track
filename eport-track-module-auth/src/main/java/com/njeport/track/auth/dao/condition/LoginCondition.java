package com.njeport.track.auth.dao.condition;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kongming
 * @date 2019/12/25
 */
@Getter
@Setter
public class LoginCondition {
    private String username;
    private String password;
    private String captchaValue;
    private String captchaId;
}
