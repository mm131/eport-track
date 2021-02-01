package com.njeport.track.auth.enums;

import com.njeport.track.common.enums.IntValueEnum;

/**
 * @author kongming
 * @date 2019/12/27
 */
public enum StatusEnum implements IntValueEnum {
    NORMAL(1, "正常"),
    FROZEN(2, "冻结");

    private int value;
    private String display;

    StatusEnum(int value, String display) {
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

    public static StatusEnum getByValue(int value) {
        for(StatusEnum e: StatusEnum.values()) {
            if(value == e.intValue()) {
                return e;
            }
        }
        return null;
    }
}
