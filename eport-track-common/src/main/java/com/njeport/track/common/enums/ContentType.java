package com.njeport.track.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kongming
 * @date 2020/04/04
 */
@Getter
@AllArgsConstructor
public enum ContentType {
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded; charset=UTF-8"),
    APPLICATION_JSON("application/json; charset=UTF-8"),
    TEXT_HTML("text/html; charset=UTF-8"),
    TEXT_PLAIN("text/plain; charset=UTF-8"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png");

    private String value;
}
