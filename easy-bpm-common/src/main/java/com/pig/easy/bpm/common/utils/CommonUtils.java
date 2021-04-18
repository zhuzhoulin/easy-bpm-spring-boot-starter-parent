package com.pig.easy.bpm.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/9 16:44
 */
public class CommonUtils {

    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parseLocalDate(String localDateTimeStr, String pattern) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime parse = LocalDateTime.parse(localDateTimeStr, df);
            return parse;
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime parseLocalDateyyyyMMdd(String localDateTimeStr) {
        return  parseLocalDate(localDateTimeStr,yyyyMMdd);
    }

    public static LocalDateTime parseLocalDateyyyy_MM_ddHHmmss(String localDateTimeStr) {
        return  parseLocalDate(localDateTimeStr,yyyy_MM_ddHHmmss);
    }

    public static LocalDateTime formatDate(LocalDateTime localDateTime, String pattern) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime parse = LocalDateTime.parse(formatTime(localDateTime, pattern), df);
            return parse;
        } catch (Exception e) {
            return null;
        }
    }

    /* 获取一天的最后一秒 */
    public static LocalDateTime getDayEnd(LocalDateTime localDateTime) {
        try {
            return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(23, 59, 59));
        } catch (Exception e) {
            return null;
        }
    }

    /* 获取一天的开始时间 */
    public static LocalDateTime getDayStart(LocalDateTime localDateTime) {
        try {
            return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(0, 0, 0));
        } catch (Exception e) {
            return null;
        }
    }

    /* 格式化date 日期 */
    public static Date formatDate(Date date, String pattern) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = convertDateToLocalDateTime(date);
            LocalDateTime parse = LocalDateTime.parse(formatTime(localDateTime, pattern), df);
            return convertLDTToDate(parse);
        } catch (Exception e) {
            return null;
        }
    }

    /* 格式化date 日期 */
    public static Date formatDateyyyyMMddHHmmss(Date date) {
        return formatDate(date, yyyy_MM_ddHHmmss);
    }

    public static Date formatDateyyyyMMdd(Date date) {
        return formatDate(date, yyyyMMdd);
    }

    /* Date转换为LocalDateTime */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /*LocalDateTime转换为Date */
    public static Date convertLDTToDate(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    /* 获取指定日期的毫秒 */
    public static Long getMilliByTime(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /*获取指定日期的秒 */
    public static Long getSecondsByTime(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /* 获取指定时间的指定格式 */
    public static String formatTime(LocalDateTime time, String pattern) {
        if (time == null || pattern == null) {
            return null;
        }
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /* 获取当前时间的指定格式 */
    public static String formatNow(String pattern) {
        if (pattern == null) {
            return null;
        }
        return formatTime(LocalDateTime.now(), pattern);
    }

    /* 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*/
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        if (time == null) {
            return null;
        }
        return time.plus(number, field);
    }

    /* 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*/
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        if (time == null) {
            return null;
        }
        return time.minus(number, field);
    }

    /* 获取 当前月的第一天 */
    public static LocalDate firstToMonth(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    /* 获取 当前月的最后一天 */
    public static LocalDate lastDayOfMonth(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    /* 获取 下月的第一天 */
    public static LocalDate firstDayOfNextMonth(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.with(TemporalAdjusters.firstDayOfNextMonth());
    }

    /* 获取 本年最后一天 */
    public static LocalDate lastDayOfYear(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.with(TemporalAdjusters.lastDayOfYear());
    }

    /* 获取 本年第一天 */
    public static LocalDate firstToYear(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     *
     * @param startTime
     * @param endTime
     * @param field     单位(年月日时分秒)
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        if (startTime == null || endTime == null) {
            return -1;
        }
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    public static String evalToNull(Object obj) {
        if (obj == null) {
            return null;
        }
        return org.apache.commons.lang3.StringUtils.trimToNull(obj.toString());
    }

    public static String evalString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    public static int evalInt(Object obj) {
        if (obj == null) {
            return -1000;
        }
        return org.apache.commons.lang.math.NumberUtils.toInt(obj.toString().replaceAll(" ", ""), -1000);
    }

    public static int evalInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return org.apache.commons.lang.math.NumberUtils.toInt(obj.toString().replaceAll(" ", ""), defaultValue);
    }

    public static long evalLong(Object obj) {
        if (obj == null) {
            return -1000;
        }
        return org.apache.commons.lang.math.NumberUtils.toLong(obj.toString().replaceAll(" ", ""), -1000);
    }

    public static long evalLong(Object obj, long defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return org.apache.commons.lang.math.NumberUtils.toLong(obj.toString().replaceAll(" ", ""), defaultValue);
    }

    public static boolean evalBoolean(Object obj) {
        if (obj == null) {
            return false;
        }
        return (Boolean) obj;
    }

    public static boolean getBooleanFromStr(String str) {
        if (str == null) {
            return false;
        }
        return Boolean.valueOf(str);
    }

    public static Number evalNumber(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return (Number) obj;
        } catch (Exception e) {
            return null;
        }
    }

    public static double evalDouble(Object obj) {
        if (obj == null) {
            return 0d;
        } else if (StringUtils.isNotBlank(obj.toString())) {
            return Double.parseDouble(obj.toString().trim());
        }
        return 0d;
    }

    public static double evalDouble(Object obj, double defaultValue) {
        if (obj == null) {
            return defaultValue;
        } else if (StringUtils.isNotBlank(obj.toString())) {
            return Double.parseDouble(obj.toString().trim());
        }
        return 0d;
    }

    public static double evalFloat(Object obj) {
        if (obj == null) {
            return 0f;
        } else if (StringUtils.isNotBlank(obj.toString())) {
            return Float.parseFloat(obj.toString().trim());
        }
        return 0f;
    }

    public static float evalFloat(Object obj, float defaultValue) {
        if (obj == null) {
            return defaultValue;
        } else if (StringUtils.isNotBlank(obj.toString())) {
            return Float.parseFloat(obj.toString().trim());
        }
        return 0f;
    }

    public static Date evalDate(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return (Date) obj;
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] evalStrToArray(String str) {
        String[] a = null;
        if (str != null) {
            a = str.split(",");
        }
        return a;
    }

    public static List<Long> evalStrToLongArray(String str) {
        String[] a = null;
        if (str != null) {
            a = str.split(",");
        }
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; a != null && i < a.length; i++) {
            long l = evalLong(StringUtils.trim(a[i]));
            if (l != -1000) {
                list.add(l);
            }
        }
        return list;
    }

    public static List<Integer> evalStrToIntegerArray(String str) {
        String[] a = null;
        if (str != null) {
            a = str.split(",");
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; a != null && i < a.length; i++) {
            int l = evalInt(StringUtils.trim(a[i]));
            if (l != -1000) {
                list.add(l);
            }
        }
        return list;
    }

    public static List<String> evalStrToStringArray(String str) {
        String[] a = null;
        if (str != null) {
            a = str.split(",");
        }
        List<String> list = new ArrayList<String>();
        for (int i = 0; a != null && i < a.length; i++) {
            String l = evalString(StringUtils.trim(a[i]));
            if (l != null && !l.equals("")) {
                list.add(l);
            }
        }
        return list;
    }

    public static String listJoin(List<? extends Object> list, String symbol,
                                  Boolean quote) {
        quote = quote == null ? false : quote;
        String st = quote ? "'" : "";
        String str = "";
        if (list == null || list.isEmpty()) {
            return "";
        }
        int i = 0;
        for (; i < list.size() - 1; i++) {
            str += st + list.get(i).toString() + st + symbol;
        }
        str += st + list.get(i).toString() + st;
        return str;
    }
}
