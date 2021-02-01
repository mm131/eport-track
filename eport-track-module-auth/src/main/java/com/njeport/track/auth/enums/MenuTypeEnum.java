package com.njeport.track.auth.enums;

import com.njeport.track.common.enums.IntValueEnum;

/**
 * @author kongming
 * @date 2019/12/27
 */
public enum MenuTypeEnum implements IntValueEnum {
    ROOT_MENU(0, "一级菜单"),
    CHILD_MENU(1, "子菜单"),
    BUTTON(2, "按钮");

    private int value;
    private String display;

    MenuTypeEnum(int value, String display) {
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

    public static MenuTypeEnum getByValue(int value) {
        for(MenuTypeEnum e: MenuTypeEnum.values()) {
            if(value == e.intValue()) {
                return e;
            }
        }
        return null;
    }
}
