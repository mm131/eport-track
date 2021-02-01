package com.njeport.track.common.enums;

/**
 * @author kongming
 * @date 2019/12/27
 */
public enum DelFlagEnum implements IntValueEnum {
    NORMAL(0, "正常"),
    DELETED(1, "已删除");

    private int value;
    private String display;

    DelFlagEnum(int value, String display) {
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

    public static DelFlagEnum getByValue(int value) {
        for(DelFlagEnum e: DelFlagEnum.values()) {
            if(value == e.intValue()) {
                return e;
            }
        }
        return null;
    }
}
