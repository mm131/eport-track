package com.njeport.track.common.utils;

import com.google.common.collect.Lists;
import com.njeport.track.common.constants.StringConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.List;

/**
 * @author kongming
 * @date 2020/01/05
 */
public class HeaderUtils {

    public static List<String> buildCookie(Header[] sourceHeaders, List<String> additionItems) {
        List<String> cookieItems = Lists.newArrayList();

        if (additionItems != null) {
            cookieItems.addAll(additionItems);
        }
        for (Header header : sourceHeaders) {
            if (header.getName().equalsIgnoreCase(StringConstants.SET_COOKIE)) {
                String headerValue = header.getValue();
                if(headerValue.split(StringConstants.SEMICOLON)[0].endsWith("=null"))
                    continue;
                cookieItems.add(headerValue.split(StringConstants.SEMICOLON)[0]);
            }
        }
        return cookieItems;
    }

    public static Header buildCookieHeader(Header[] sourceHeaders, List<String> additionItems) {
        List<String> cookieItems = buildCookie(sourceHeaders, additionItems);
        return new BasicHeader("Cookie", StringUtils.join(cookieItems, StringConstants.SEMICOLON));
    }
}
