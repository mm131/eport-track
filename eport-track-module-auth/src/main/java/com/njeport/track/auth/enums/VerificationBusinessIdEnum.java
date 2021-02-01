package com.njeport.track.auth.enums;


import com.njeport.track.common.enums.IntValueEnum;

/**
 * @author kongming
 * @date 2019/12/27
 */
public enum VerificationBusinessIdEnum implements IntValueEnum {
    REGISTER(1, "注册"),
    LOGIN_SITE(2, "登录网站"),
    LOGIN_ADMIN(3, "登录管理后台"),
    FORGET_PWD(4, "忘记密码");

    private int value;
    private String display;

    VerificationBusinessIdEnum(int value, String display) {
        this.value = value;
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }

    @Override
    public int intValue() {
        return value;
    }
}
