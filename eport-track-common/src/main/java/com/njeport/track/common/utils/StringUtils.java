package com.njeport.track.common.utils;

import com.njeport.track.common.constants.StringConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright: 2015 FireworkStudio
 * *
 * Author:  Daniel Kong
 * Date:    2015-11-15
 */
public class StringUtils {
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static boolean isEmpty(String str) {
        return (str == null || str.equals(StringConstants.EMPTY) || str.equalsIgnoreCase(StringConstants.NULL));
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getValidString(String str) {
        return (str == null) ? StringConstants.EMPTY : str;
    }

    public static String getDisplayString(String str){
        return (str == null) ? StringConstants.EMPTY : str.trim().replaceAll("\\p{C}", StringConstants.EMPTY);
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static String camelToUnderline(String str) {
        if (isEmpty(str)) {
            return null;
        }
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toLowerCase();
    }
}
