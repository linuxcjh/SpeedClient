package com.rongfeng.speedclient.common.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @file : DateUtil.java [V 1.0.0]
 * @author: 陈建辉
 * @time : CREAT AT 2013-11-18 下午2:01:32
 * @TODO : 【DateUtil】
 */
public class DateUtil {

    public static final long DAY_SECONDS = 24 * 60 * 60 * 1000;

    public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    public static String yyyy = "yyyy";

    public static String yyyy_MM = "yyyy-MM";

    public static String MM_dd = "MM月dd日";

    public static String yyyy_MM_dd = "yyyy-MM-dd";

    public static String yyyyMMdd = "yyyy.MM.dd";


    public static String MM_dd_yyyy = "MM-dd-yyyy";

    public static String yyyy_MM_dd_HH = "yyyy-MM-dd HH";


    public static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

    public static String yyyy_M_d_H_m = "yyyy年MM月dd日";

    public static String yyyy_M_d_H_m_HH_mm = "yyyy年MM月dd日 HH:mm";

    public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static String TIME_PATTERN = "HH:mm:ss";

    public static String HHmm = "HH:mm";

    public static String DD_HH_mm = "dd天 HH:mm";


    /**
     * 获取当前时间
     */
    public static String getTime(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }

    /**
     * Translate string of date with separactor '-' to
     * <code>java.util.Date</code> type
     *
     * @param str string of date .eg:"2006-01-01"
     * @return <code>java.util.Date</code>
     */
    public static Date strToDate(String str) {
        Date result = new Date();
        DateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        try {
            result = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date strToDate(String str, String pattern) {
        Date result = new Date();
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            result = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Date getDateNow() {
        return Calendar.getInstance().getTime();
    }

    public static final Date convertStringToDate(String aMask, String strDate) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            android.util.Log.e("ParseException", pe.getMessage());
        }

        return date;
    }

    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate == null) {
            android.util.Log.e("warn", "aDate is null!1");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return returnValue;
    }

    /**
     * Translate string of date with separator '-' to <code>java.sql.Date</code>
     * type
     *
     * @param str string of date .eg:"2006-01-01"
     * @return <code>java.sql.Date</code>
     */
    public static java.sql.Date strToSQLDate(String str) {
        Date temp = strToDate(str);
        java.sql.Date result = new java.sql.Date(temp.getTime());
        return result;
    }

    /**
     * Translate <code>java.util.Date</code> type to string of date with
     * separator '-'
     *
     * @param date The want translate <code>java.util.Date</code>
     * @return string of the date
     */
    public static String dateToStr(Date date) {
        DateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        return format.format(date);
    }

    public static String dateToStr(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * add number of days of the date
     *
     * @param orig   the date which want to add
     * @param addDay add number of days
     * @return
     */
    public static Date addDay(Date orig, int addDay) {
        Date result = new Date(orig.getTime() + addDay * DAY_SECONDS);
        return result;
    }

    /**
     * add number of month of the date
     *
     * @param orig     the date which want to add
     * @param addMonth add number of month
     * @return
     */
    public static Date addMonth(Date orig, int addMonth) {

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(orig);
        cal.add(Calendar.MONTH, addMonth);

        return cal.getTime();

    }

    /**
     * subtract number of days of the date
     *
     * @param orig   orig the date which want to subtract
     * @param subDay subtract number of days
     * @return
     */
    public static Date subDay(Date orig, int subDay) {
        Date result = new Date(orig.getTime() - subDay * DAY_SECONDS);
        return result;
    }

    /**
     * Get number of days which subtract of two date
     *
     * @param start Start date
     * @param end   End date
     * @return number of days
     */
    public static int getSubDay(Date end, Date start) {
        int result = (int) ((end.getTime() - start.getTime()) / DAY_SECONDS);
        return result;
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
        return (Date) calendar.getTime().clone();
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate) {
        Date aDate = null;
        aDate = convertStringToDate(getDatePattern(), strDate);
        return aDate;
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    public static int getYearInt() {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy);
        try {
            return Integer.parseInt(sdf.format(new Date()));
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getStringByDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定日期对应月份的最后一天
     *
     * @param date 指定日期
     * @return 月份的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期对应月份的第一天
     *
     * @param date 指定日期
     * @return 月份的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }


    public static final String convertDate2String(Date date, String format) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (date == null) {
            android.util.Log.e("warn", "Date is null!");
        } else {
            df = new SimpleDateFormat(format);
            returnValue = df.format(date);
        }
        return returnValue;
    }

    public static String getDatePattern() {
        return DEFAULT_DATE_PATTERN;
    }

    public static String getWeekByNow() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String resultStr = "";
        if (week == 2)
            resultStr = "一";
        if (week == 3)
            resultStr = "二";
        if (week == 4)
            resultStr = "三";
        if (week == 5)
            resultStr = "四";
        if (week == 6)
            resultStr = "五";
        if (week == 7)
            resultStr = "六";
        if (week == 1)
            resultStr = "日";
        return "星期" + resultStr;
    }

    public static String getDayOfWeek(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int mydate = cd.get(Calendar.DAY_OF_WEEK);
        String showDate = " ";
        switch (mydate) {
            case 1:
                showDate = "日";
                break;
            case 2:
                showDate = "一";
                break;
            case 3:
                showDate = "二";
                break;
            case 4:
                showDate = "三";
                break;
            case 5:
                showDate = "四";
                break;
            case 6:
                showDate = "五";
                break;
            default:
                showDate = "六";
                break;
        }
        return showDate;
    }

    // MMddHHmmss

    public static long getMilliseconds(String dateStr) {
        String pattern = "MMddHHmmss";
        Date date = convertStringToDate(pattern, dateStr);
        long d1 = date.getTime();
        long d2 = convertStringToDate(pattern,
                convertDate2String(new Date(), pattern)).getTime();
        long d3 = d2 - d1;
        return d3;
    }

    public static String getRangeTime(long timeInSeconds) {
        long hours, minutes, seconds;
        // 1(hour)*60(minite)*60(seconds)*1000(millisecond)
        hours = timeInSeconds / 3600000;
        timeInSeconds = timeInSeconds - (hours * 3600 * 1000);
        // 1(seconds)*1000(millisecond)
        minutes = timeInSeconds / 60000;
        timeInSeconds = timeInSeconds - (minutes * 60 * 1000);
        // 1(seconds)*1000(millisecond)
        seconds = timeInSeconds / 1000;
        return hours + "小时" + minutes + "分钟" + seconds + "秒";
    }

    public static void main(String[] args) {
        long timeInSeconds = DateUtil.getMilliseconds(DateUtil
                .convertDate2String(new Date(108230133), "MMddHHmmss"));
        System.out.println(DateUtil.getRangeTime(timeInSeconds));
    }

    /**
     * 两个时期比较大小
     *
     * @param date1
     * @param date2
     * @return 1 dt1 在dt2前,-1 dt1在dt2后,0 相等
     */
    public static int compareDate(String date1, String date2, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 两个时间之间的天数之差
     *
     * @param start
     * @param end
     * @return
     */
    public static String getDays(String start, String end) {
        if (TextUtils.isEmpty(start))
            return "";
        if (TextUtils.isEmpty(end))
            return "";
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date startDate = null;
        java.util.Date endDate = null;
        try {
            startDate = myFormatter.parse(start);
            endDate = myFormatter.parse(end);
        } catch (Exception e) {
        }
        int day = (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));

        return day + "";
    }


    /**
     * 获取时间差
     *
     * @param start
     * @param end
     * @return
     */
    public static int getHours(String start, String end) {
        if (TextUtils.isEmpty(start))
            return 0;
        if (TextUtils.isEmpty(end))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("HH:mm");
        java.util.Date startDate = null;
        java.util.Date endDate = null;
        try {
            startDate = myFormatter.parse(start);
            endDate = myFormatter.parse(end);
        } catch (Exception e) {
        }
        int hours = (int) ((endDate.getTime() - startDate.getTime()) / (60 * 60 * 1000));

        return hours;
    }


    /**
     * 星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String generationConstellation(int month, int day) {
        String star = "";
        if (month == 1 && day >= 20 || month == 2 && day <= 18) {
            star = "水瓶座";
        }
        if (month == 2 && day >= 19 || month == 3 && day <= 20) {
            star = "双鱼座";
        }
        if (month == 3 && day >= 21 || month == 4 && day <= 19) {
            star = "白羊座";
        }
        if (month == 4 && day >= 20 || month == 5 && day <= 20) {
            star = "金牛座";
        }
        if (month == 5 && day >= 21 || month == 6 && day <= 21) {
            star = "双子座";
        }
        if (month == 6 && day >= 22 || month == 7 && day <= 22) {
            star = "巨蟹座";
        }
        if (month == 7 && day >= 23 || month == 8 && day <= 22) {
            star = "狮子座";
        }
        if (month == 8 && day >= 23 || month == 9 && day <= 22) {
            star = "处女座";
        }
        if (month == 9 && day >= 23 || month == 10 && day <= 22) {
            star = "天秤座";
        }
        if (month == 10 && day >= 23 || month == 11 && day <= 21) {
            star = "天蝎座";
        }
        if (month == 11 && day >= 22 || month == 12 && day <= 21) {
            star = "射手座";
        }
        if (month == 12 && day >= 22 || month == 1 && day <= 19) {
            star = "摩羯座";
        }
        return star;
    }

    /**
     * 截取日期字符串的时分秒
     * author dq
     *
     * @return
     */
    public static String getHfm(String str) {
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm);
        Date result = null;
        try {
            result = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(HHmm);
        return sdf.format(result);

    }

    public static String getYMDFormat(String string) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(result);
    }

    public static String getYMDFormat2(String string) {
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm);
        Date result = null;
        try {
            result = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(result);
    }

    public static String getChinaYMR(String string) {
        DateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        Date result = null;
        try {
            result = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(result);
    }


    /**
     * 截取天
     *
     * @param str
     * @return
     */
    public static String getDay(String str) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(getYMD(str));
    }

    /**
     * 获取日期的年月日
     *
     * @param str
     * @return
     */
    public static Date getYMD(String str) {
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd);
        Date result = null;
        try {
            result = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 截取年
     *
     * @return
     */
    public static String getYear(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(getYMD(str));
    }

    /**
     * 截取月
     *
     * @return
     */
    public static String getMoth(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(getYMD(str));
    }

    /**
     * 获取农历
     *
     * @return
     */
    public static String getLongLi(String str) {
        int d = Integer.parseInt(getDay(str));
        int y = Integer.parseInt(getYear(str));
        int m = Integer.parseInt(getMoth(str));
        CalendarUtil calendarUtil = new CalendarUtil();
        return calendarUtil.getChineseDay(y, m, d);
    }

    /**
     * @param dateString
     * @param fromePattern
     * @param toPattern
     * @return
     */

    public static String getDate(String dateString, String fromePattern, String toPattern) {
        SimpleDateFormat format = new SimpleDateFormat(fromePattern);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToStr(date, toPattern);
    }

    /**
     * 获取星期几
     * author forest
     *
     * @return
     */
    public static String getWeek(String string) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        String weekStr = "";
        switch (dayForWeek) {
            case 1:
                weekStr = "星期一";
                break;
            case 2:
                weekStr = "星期二";
                break;
            case 3:
                weekStr = "星期三";
                break;
            case 4:
                weekStr = "星期四";
                break;
            case 5:
                weekStr = "星期五";
                break;
            case 6:
                weekStr = "星期六";
                break;
            case 7:
                weekStr = "星期日";
                break;
        }
        return weekStr;
    }

    public static String getYMRWeek(String string) {
        return getChinaYMR(string) + " " + getWeek(string);
    }


    /**
     * 获取前index个月
     *
     * @return
     */

    public static String obtainBeforeMonths(int index) {
        String date = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -index);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        date = year + "-" + month;
        if (month < 10) {
            date = year + "-" + "0" + month;
        }
        return date;
    }

    /**
     * 获取前index个月 包含日期
     *
     * @return
     */

    public static String obtainBeforeMonthsDays(int index, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -index);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(pattern);


        return format.format(calendar.getTime());
    }

    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        SimpleDateFormat format = new SimpleDateFormat();

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 1);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = format.parse(format.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        SimpleDateFormat format = new SimpleDateFormat();

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = format.parse(format.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }
}
