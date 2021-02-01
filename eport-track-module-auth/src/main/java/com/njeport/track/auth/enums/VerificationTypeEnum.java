package com.njeport.track.auth.enums;


import com.njeport.track.common.enums.IntValueEnum;

/**
 * @author kongming
 * @date 2019/12/27
 */
public enum VerificationTypeEnum implements IntValueEnum {
    WEB(1, "网站验证码"),
    SMS(2, "短信验证码");

    private int value;
    private String display;

    VerificationTypeEnum(int value, String display) {
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
