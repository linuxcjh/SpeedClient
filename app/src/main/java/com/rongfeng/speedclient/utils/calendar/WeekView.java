package com.rongfeng.speedclient.utils.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.components.MyViewFlipper;

import java.util.List;

/**
 * Created by 唐磊 on 2015/11/11.
 * 可以左右滑动的日历控件
 */
public class WeekView extends MyViewFlipper implements MyViewFlipper.OnViewFlipperListener {

    private CalendarView calendarView;
    public Week week;
    public String previousWeekLastDay;
    public String nextWeekFristDay;
    private List<CalendarModel> list;
    private WeekListener weekListener;

    public WeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnViewFlipperListener(this);
        week = new Week(this);
    }

    public WeekView(Context context) {
        super(context);
        setOnViewFlipperListener(this);
        week = new Week(this);
    }

    public void initData() {
        week.setDateString(DateUtil.getTime(DateUtil.DEFAULT_DATE_PATTERN));
        addView(week.onCreateView());
    }


    @Override
    public View getNextView() {
        if (week.getmMonth() != Integer.valueOf(nextWeekFristDay.split("-")[1])) {
            calendarView.flingToNext();
        }
        week.setDateString(nextWeekFristDay);
        week.setIsNoFrist(true);
        View view=week.onCreateView();
        week.getAdapter().setData(list);
        weekListener.weekFlipper(week.getList());
        return view;
    }

    @Override
    public View getPreviousView() {
        if (week.getmMonth() != Integer.valueOf(previousWeekLastDay.split("-")[1])) {
            calendarView.flingToPrevious();
        }
        week.setDateString(previousWeekLastDay);
        week.setIsNoFrist(true);
        View view=week.onCreateView();
        week.getAdapter().setData(list);
        weekListener.weekFlipper(week.getList());
        return view;
    }


    public void setNextWeekFristDay(String nextWeekFristDay) {
        this.nextWeekFristDay = nextWeekFristDay;
    }


    public void setPreviousWeekLastDay(String previousWeekLastDay) {
        this.previousWeekLastDay = previousWeekLastDay;
    }



    public void setDateString(String dateString, boolean isNoFrist) {
        removeAllViews();
        week.setIsNoFrist(isNoFrist);
        week.setDateString(dateString);
        View view=week.onCreateView();
        week.getAdapter().setData(list);
        addView(view);
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
        week.setCalendarView(calendarView);
    }

    public Week getWeek() {
        return week;
    }

    public void setData(List<CalendarModel> list) {
        this.list=list;
    }

    public void setWeekListener(WeekListener weekListener) {
        this.weekListener = weekListener;
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {}

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
