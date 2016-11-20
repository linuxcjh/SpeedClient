package com.rongfeng.speedclient.utils.calendar;

/**
 * Created by 唐磊 on 2015/11/11.
 */
public interface MonthFristDayListener {


    /**
     * 上个月第一天
     * @param lastMonthFristDay
     */
    public void setLastMonthFristDay(int lastMonthFristDay);

    /**
     * 下个月第一天
     * @param nextMonthFristDay
     */
    public void setNextMonthFristDay(int nextMonthFristDay);

}
