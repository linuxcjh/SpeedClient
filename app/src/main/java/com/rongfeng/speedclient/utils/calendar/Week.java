package com.rongfeng.speedclient.utils.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.common.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 唐磊 on 15/12/8.
 */
public class Week {

    private GridView gridView;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mWeek;
    private int weekOfMonth;
    /**
     * 一年中的大月份
     */
    private List<Integer> listBigMonth = new ArrayList<Integer>();
    /**
     * 一年中的小月份
     */
    private List<Integer> listSmallMonth = new ArrayList<Integer>();
    /**
     * 阳历
     */
    private List<Integer> list = new ArrayList<Integer>();
    private HashMap<Integer, String> map = new HashMap<>();
    private WeekAdapter adapter;
    private WeekView weekView;
    public int size = 0;
    private String dateString;
    private boolean isNoFrist = false;
    private CalendarView calendarView;

    public Week(WeekView weekView) {
        super();
        this.weekView = weekView;
        init();
    }

    public View onCreateView() {
        list.clear();
        map.clear();
        gridView = (GridView) LayoutInflater.from(weekView.getContext()).inflate(R.layout.week_gridview, null);
        /** GridView 设置padding 去掉右边空隙过大  */
        gridView.setColumnWidth(Utils.getDeviceWidthPixels(weekView.getContext()) - Utils.dip2px(weekView.getContext(), 40));
        gridView.setAdapter(getCalendarAdapter());
        return gridView;
    }


    private void init() {
        listBigMonth.add(1);
        listBigMonth.add(3);
        listBigMonth.add(5);
        listBigMonth.add(7);
        listBigMonth.add(8);
        listBigMonth.add(10);
        listBigMonth.add(12);

        listSmallMonth.add(4);
        listSmallMonth.add(6);
        listSmallMonth.add(9);
        listSmallMonth.add(11);
    }


    private WeekAdapter getCalendarAdapter() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.setTime(DateUtil.strToDate(dateString));
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        mWeek = c.get(Calendar.DAY_OF_WEEK) - 1;// 获取当前日期是周期
        weekOfMonth=c.get(Calendar.WEEK_OF_MONTH);
        int day;
        for (int i = 0; i < 7; i++) {
            day = mDay + i - mWeek;

            if (i == 0) {
                if (day <= 1) {
                    weekView.setPreviousWeekLastDay(getDateString(mYear, mMonth - 1, getMonthDays(mMonth - 1 < 1 ? 12 : mMonth - 1)));
                } else {
                    weekView.setPreviousWeekLastDay(getDateString(mYear, mMonth, day - 1));
                }
            }
            if (i == 6) {
                if (day >= getMonthDays(mMonth)) {
                    weekView.setNextWeekFristDay(getDateString(mYear, mMonth + 1, 1));
                } else {
                    weekView.setNextWeekFristDay(getDateString(mYear, mMonth, day + 1));
                }
            }

            if (day < 1 || day > getMonthDays(mMonth)) {
                list.add(null);
            } else {
                list.add(day);
                map.put(day, ChinaDate.getday(mYear, mMonth, day));
            }
        }
        if (isNoFrist) mWeek = -1;
        adapter = new WeekAdapter(weekView.getContext(), list, map, mWeek, calendarView.getCalendart().getAdapter());
        adapter.setmYear(mYear);
        adapter.setmMonth(mMonth);
        adapter.setWeekOfMonth(weekOfMonth);
        return adapter;
    }

    private String getDateString(int y, int m, int d) {
        if (m < 1) {
            m = 12;
            y = y - 1;
        }
        if (m > 12) {
            m = 1;
            y = y + 1;
        }
        String month = m < 10 ? ("0" + m) : m + "";
        String day1 = d < 10 ? ("0" + d) : d + "";
        String dateString = y + "-" + month + "-" + day1;
        return dateString;
    }

    /**
     * @return 返回当月的天数
     */
    private int getMonthDays(int mMonth) {
        int days = 0;
        switch (mMonth) {
            case 2:
                if (mYear % 4 == 0) {
                    days = 29;
                    break;
                }
                days = 28;
                break;
            default:
                if (listBigMonth.contains(mMonth)) {
                    days = 31;
                }
                if (listSmallMonth.contains(mMonth)) {
                    days = 30;
                }
                break;
        }
        return days;

    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public WeekAdapter getAdapter() {
        return adapter;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setIsNoFrist(boolean isNoFrist) {
        this.isNoFrist = isNoFrist;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }
}
