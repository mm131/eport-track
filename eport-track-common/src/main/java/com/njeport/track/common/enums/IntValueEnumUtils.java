package com.njeport.track.common.enums;

public class IntValueEnumUtils {

    public static <T extends IntValueEnum> T of(Class<T> enumType, int value) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            if (e.intValue() == value) {
                return e;
            }
        }
        throw new IllegalArgumentException(
                "No IntValueEnum: " + enumType.getCanonicalName() + " value of " + value);
    }
}
