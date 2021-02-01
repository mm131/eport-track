package com.njeport.track.auth.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author kongming
 * @date 2020/1/2
 */
@Getter
@Setter
public class UserPermissionVO {
    List<PermissionVO> menus;
    List<PermissionVO> buttons;
}
