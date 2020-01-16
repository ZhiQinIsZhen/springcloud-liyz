package com.liyz.cloud.common.base.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 注释:时间处理工具类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/7/12 16:18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {

    /**
     * 年月日格式匹配符（预置），详情请参阅{@link SimpleDateFormat}
     */
    public static final String DATE_WITHOUT_TIME_AND_SEPARATOR = "yyyyMMdd";

    public static final String DATE_WITHOUT_TIME_AND_SEPARATOR_TWO = "M/dd";

    /**
     * 时间格式匹配符（预置），详情请参阅{@link SimpleDateFormat}
     */
    public static final String TIME_WITHOUT_MINUTE_AND_SECONDS = "HH";

    private static final String DATE_BEGIN = " 00:00:00";

    private static final String DATE_END = " 23:59:59";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    enum FormatEnum {
        BEIGN, END;
    }

    /**
     * 将日期延长到当前时间开始或者结束
     */
    private static Date formatDateBeginOrEnd(Date date, FormatEnum formatEnum) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String strd = sdf.format(date) + (formatEnum == FormatEnum.BEIGN ? DATE_BEGIN : DATE_END);
            sdf.applyPattern(DATE_PATTERN);
            try {
                date = sdf.parse(strd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    /**
     * 将日期延迟至当前日期00:00:00
     */
    public static Date formatDateBegin(Date date) {
        return formatDateBeginOrEnd(date, FormatEnum.BEIGN);
    }

    /**
     * 将日期设置为固定时间
     * @param date 指定日期
     * @param time 时间10:00:00
     * @return
     */
    public static Date formatDateOther(Date date, String time) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String strd = sdf.format(date) + " " + time;
            sdf.applyPattern(DATE_PATTERN);
            try {
                date = sdf.parse(strd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    /**
     * 将日期延迟至当前日期23:59:59
     */
    public static Date formatDateEnd(Date date) {
        return formatDateBeginOrEnd(date, FormatEnum.END);
    }

    public static Date formatDateDesign(Date date, String design) {
        if (date != null && design != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String strd = sdf.format(date) + design;
            sdf.applyPattern(DATE_PATTERN);
            try {
                date = sdf.parse(strd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    /**
     * 获得延长后的时间
     */
    public static Date addDayTime(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 格式化变更生效日期
     */
    public static Date formatDate(String date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date dt1 = null;
        try {
            dt1 = (Date) dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt1;
    }

    public static String formatdate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(date);
    }

    /**
     * 获取当前时间
     *
     * @param pattern 格式匹配符
     * @return 处理结果
     */
    public static String getCurrentDateTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 获取某日时间
     *
     * @param pattern 格式匹配符
     * @param amount 例如昨日则为-1   明日则为1
     * @return 处理结果
     */
    public static String getDateTime(String pattern, Integer amount) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        date = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    /**
     * date转换为LocalDateTime
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转date
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定时间的格式
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式
     */
    public static String formatNowTime(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 加日期
     */
    public static LocalDateTime plusTime(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 减日期
     */
    public static LocalDateTime minusTime(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * 获取两个时间差
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime,
                                      ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
}
