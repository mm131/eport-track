package com.njeport.track.auth.enums;

import com.njeport.track.common.enums.IntValueEnum;

/**
 * @author kongming
 * @date 2019/12/27
 */
public enum GenderEnum implements IntValueEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    private int value;
    private String display;

    GenderEnum(int value, String display) {
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

    public static GenderEnum getByValue(int value) {
        for(GenderEnum e: GenderEnum.values()) {
            if(value == e.intValue()) {
                return e;
            }
        }
        return null;
    }
}
