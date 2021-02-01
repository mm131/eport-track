package com.njeport.track.common.enums;

public interface IntValueEnum {

    int intValue();

    default <T extends IntValueEnum> boolean before(T that) {
        return this.intValue() < that.intValue();
    }

    default <T extends IntValueEnum> boolean after(T that) {
        return this.intValue() >= that.intValue();
    }
}
