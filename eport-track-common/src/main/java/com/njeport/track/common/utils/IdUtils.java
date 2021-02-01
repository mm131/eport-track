package com.njeport.track.common.utils;

import com.njeport.track.common.constants.StringConstants;

import java.util.Random;

/**
 * @author kongming
 * @date 2019/12/27
 */
public class IdUtils {
    private static final String BASE = "qwertyuioplkjhgfdsazxcvbnmQAZWSXEDCRFVTGBYHNUJMIKLOP0123456789";

    public static String randomId() {
        return java.util.UUID.randomUUID().toString().replace(StringConstants.DASH, StringConstants.EMPTY);
    }

    public static String randomSalt(int place) {
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for (int i = 0; i < place; i++) {
            sb.append(BASE.charAt(rd.nextInt(BASE.length())));
        }
        return sb.toString();
    }
}
