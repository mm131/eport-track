package com.njeport.track.common.vo;

import com.njeport.track.common.constants.StringConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpStatus;

/**
 * @author kongming
 * @date 2019/12/16
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonReturnVO {
    private boolean success;
    private int code;
    private Object result;
    private String message;

    public static <T> CommonReturnVO succ(T result, String message) {
        return new CommonReturnVO(true, HttpStatus.SC_OK, result, message);
    }

    public static <T> CommonReturnVO succ(T result) {
        return succ(result, StringConstants.EMPTY);
    }

    public static <T> CommonReturnVO succ() {
        return succ(StringConstants.OK);
    }

    public static <T> CommonReturnVO fail(int code, Object result, String message) {
        return new CommonReturnVO(false, code, result, message);
    }

    public static <T> CommonReturnVO fail(Object result, String message) {
        return fail(HttpStatus.SC_BAD_REQUEST, result, message);
    }

    public static <T> CommonReturnVO fail(String message) {
        return fail(null, message);
    }
}
