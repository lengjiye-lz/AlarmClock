package com.lengjiye.clock.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式化类
 *
 * @author lz
 * @version 2014年12月9日
 */
public class DateFormatUtils {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_WEEK_AGO = "周前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2013-11-11 18:35:35");
        System.out.println(formatDate(date));
    }

    /**
     * 日期格式应该为：yyyy-MM-dd HH:m:s 格式化为：几天前 几分钟前等
     *
     * @param dateStr
     * @return
     */
    public static String formatDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
        long delta = new Date().getTime() - date.getTime();
        return formater(delta, date);
    }

    public static String formatDate(Date date) {
        long delta = new Date().getTime() - date.getTime();
        return formater(delta, date);
    }

    private static String formater(long delta, Date date) {
        // if (delta < 1L * ONE_MINUTE) {
        // long seconds = toSeconds(delta);
        // return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        // }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        // if (delta < 30L * ONE_DAY) {
        // long days = toDays(delta);
        // return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        // }
        if (delta < 1L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_DAY_AGO;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
            // long years = toYears(delta);
            // return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    /**
     * 得到当前日期：yyyy-MM-dd
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String time = format.format(new Date());
        return time;
    }

    /**
     * 得到当前日期及时间：yyyy-MM-dd HH:m:s
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String time = format.format(new Date());
        return time;
    }

    /**
     * 得到当前日期及时间：yyyy-MM-dd HH:m:s
     */
    public static String dateFormat(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return format.format(time);
    }

    /**
     * 得到当前日期及时间：yyyy-MM-dd HH:m:s
     */
    public static Date dateFormat(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return format.parse(time);
    }

    /**
     * 得到当前时间：HH:mm
     *
     * @return 毫秒
     */
    public static long dateFormatHHMM(String date) {
        if (date != null && !"".equals(date)) {
            String[] strings = date.split(":");
            return (Integer.valueOf(strings[0]) * 60 * 60 + Integer.valueOf(strings[1]) * 60) * 1000;
        }
        return 0;
    }

    /**
     * 根据毫秒数转换成时间
     *
     * @return HH:mm
     */
    public static String dateFormatHHMM(long time) {
        long hour = 0;
        long minute = 0;
        if (time != 0) {
            hour = (time / 1000) / 3600;
            minute = ((time / 1000) - hour * 3600) / 60;
            return String.format("%02d:%02d", hour,minute);
        }
        return "";
    }

    /**
     * 得到当前时间：HH:mm
     *
     * @return 毫秒
     */
    public static long getCurrentDateTimeHHMM() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);//获取当前的小时数
        int mMinute = c.get(Calendar.MINUTE);//获取当前的分钟数
        return (mHour * 60 * 60 + mMinute * 60) * 1000;
    }

    /**
     * 得到当前时间：ss
     *
     * @return 毫秒
     */
    public static long getCurrentDateTimeSS() {
        Calendar c = Calendar.getInstance();
        int mSecond = c.get(Calendar.SECOND);//获取当前的秒钟数
        return mSecond * 1000;
    }

    /**
     * 格式化时间：yy-MM-dd
     */
    public static String dateFormatYYMMDD(String date) {
        String time = date.substring(2);
        return time;
    }

    /**
     * 判断时间是否需要加“0”
     *
     * @param i
     * @return
     */
    public static String isDouble(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    /**
     * 时间差
     *
     * @param timestart 开始时间
     * @param timeend   开始时间
     */
    public static long timeDifference(String timestart, String timeend) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date d1 = df.parse(timestart);
            Date d2 = df.parse(timeend);
            long diff = d2.getTime() - d1.getTime();
            return diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 语音时长
     *
     * @return 时长
     */
    public static String voiceTimeSize(int time) {
        if (time > 0) {
            if (time < 60) {
                return time + "''";
            } else {
                return time / 60 + "'" + time % 60 + "''";
            }
        }
        return "";
    }
}
