package com.njeport.track.common.utils;

import com.njeport.track.common.constants.StringConstants;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author kongming
 * @date 2020/02/27
 */
@Slf4j
public class DateUtils {
    private static final int MILLI_SECONDS_OF_DAY = 1000 * 60 * 60 * 24;
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateTimeFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final SimpleDateFormat dateTimeFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat dateTimeFormat4 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    /**
     * 获取日期+时间
     */
    public static Date parseDateTime(String dateStr) {
        try {
            if (StringUtils.isEmpty(dateStr)) {
                return null;
            }
            int strLen = dateStr.length();
            if (strLen > 16) {
                return (dateStr.contains(StringConstants.DASH)) ? dateTimeFormat.parse(dateStr) : dateTimeFormat2.parse(dateStr);
            } else {
                return (dateStr.contains(StringConstants.DASH)) ? dateTimeFormat3.parse(dateStr) : dateTimeFormat4.parse(dateStr);
            }
        } catch (ParseException e) {
            log.error("日期 {} 解析失败", dateStr);
            return null;
        }
    }

    private static Calendar buildCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取两个时间的自然天数间隔
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 自然天间隔
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = buildCalendar(startDate);
        Calendar toCalendar = buildCalendar(endDate);
        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / MILLI_SECONDS_OF_DAY);
    }
}
